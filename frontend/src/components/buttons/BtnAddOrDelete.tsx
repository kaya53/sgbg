import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { auth } from '../../store/auth';
import Swal from 'sweetalert2';
import { intoRoom, outRoom } from '../../api/room';
import { members } from '../../util/room';


const BtnAddOrDelete = (props: any) => {
  const [islogining, setLogining] = useState<boolean>(false);
  const [isInThisRoom, setIsInThisRoom] = useState(false);
  const [userAuth] = useRecoilState(auth);
  const navigate = useNavigate()

  const getIsInThisRoom = () => {
    console.log('members= ', props.room.members);
    
    props.room.members.forEach((member: members) => {
      console.log(member);
      
      console.log('readroom kakaoid', member.kakaoId, 'readroom current user=', userAuth.userId);
      
      if (member.kakaoId === userAuth.userId) {
        setIsInThisRoom(true);
      } else {
        setIsInThisRoom(false);
      }
    });
  };


  const onClickInAndOut = () => {
    // 참여자 늘리는 axios 요청 보내기
    // 로그인이 안되어 있으면 로그인 페이지로 넘기기
    // 로그인이 되어 있으면 axios 요청
    // 로그인 막기
    if (!userAuth.isLogined) {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "로그인 후에 모임 참여가 가능합니다.",
        showConfirmButton: false,
        timer: 1500,
      }).then(() => {
        navigate("/login");
      });
    } // 퇴징: 로그인은 되어 있지만 이미 참여하고 있는 경우
    else if (userAuth.isLogined && isInThisRoom) {
      Swal.fire({
        position: "center",
        icon: "info",
        title: "이미 참여하고 있는 모임입니다. 퇴장하시겠습니까?",
        showConfirmButton: true,
        showCancelButton: true,
        cancelButtonText: "취소",
        timer: 1500,
      }).then(() => {
        //axios로 퇴장 시키기
        try {
          setLogining(true);
          outRoom(props.room.roomId).then(() => {
            setLogining(false);
            Swal.fire({
              position: "center",
              title: `${props.room.title}에서 퇴장하셨습니다.`,
              timer: 1500,
            }).then(() => navigate(0));
          });
        } catch {
          setLogining(false);
          Swal.fire({
            toast: true,
            position: "center",
            showConfirmButton: true,
            title: `입장 실패하였습니다.`,
          });
        }
      });
    }
    // 입장: 로그인이 되어 있는 데 현재 방에 참가하고 있지 않은 경우
    else if (userAuth.isLogined && !isInThisRoom) {
      if (props.room.roomId) {
        Swal.fire({
          title: `${props.room.title}에 참여하시겠습니까?`,
          showCancelButton: true,
          confirmButtonText: "참여하기",
          cancelButtonText: "취소",
        }).then((result) => {
          if (result.isConfirmed) {
            try {
              setLogining(true);
              intoRoom(props.room.roomId).then(() => {
                setLogining(false);
                navigate(0);
              });
            } catch {
              setLogining(false);
              Swal.fire({
                toast: true,
                position: "center",
                showConfirmButton: true,
                title: `퇴장 실패하였습니다.`,
              });
            }
          }
        });
      }
    }
  };


  useEffect(()=>{
    getIsInThisRoom();
  })


  return (
    <div>
      {islogining ? (
            <div className="w-full flex flex-col justify-center items-center">
              <div className="flex flex-row">
                <img
                  className="w-5 h-5 animate-gelatine mr-1"
                  alt="userBadge3"
                  src={process.env.PUBLIC_URL + `/img/userBadge3.png`}
                />
                <img
                  className="w-5 h-5 animate-gelatine mr-1"
                  alt="userBadge2"
                  src={process.env.PUBLIC_URL + `/img/userBadge2.png`}
                />
                <img
                  className="w-5 h-5 animate-gelatine"
                  alt="userBadge1"
                  src={process.env.PUBLIC_URL + `/img/userBadge1.png`}
                />
              </div>
              <span className="font-semibold text-xl">처리중...</span>
            </div>
          ) : isInThisRoom ? (
            <button
              type="button"
              onClick={onClickInAndOut}
              className="w-full text-center text-white font-bold bg-blue-500 rounded p-1">
              퇴장하기
            </button>
          ) : (
            <button
              type="button"
              onClick={onClickInAndOut}
              className="w-full text-center font-bold bg-yellow-100 rounded p-1">
              입장하기
            </button>
          )}
    </div>
  );
};

export default BtnAddOrDelete;