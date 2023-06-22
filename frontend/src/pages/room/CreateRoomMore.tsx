import BtnExitToHome from "../../components/buttons/BtnExitToHome";
import { useEffect, useState } from "react";
import { createRoom } from "../../api/room";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilState, useResetRecoilState } from "recoil";
import { roomMore } from "../../util/room";
import { inputRoomInfo } from "../../store/room";
import MeetingCard from "../../components/cards/MeetingCard";
import Swal from "sweetalert2";

const CreateRoomMore = () => {
  const [room, setRoom] = useRecoilState<roomMore>(inputRoomInfo);
  const [roomList, setRoomList] = useState([]);
  const resetRecoil = useResetRecoilState(inputRoomInfo);
  const [isloading, setLoading] = useState<boolean>(false);

  // created 될 때
  useEffect(() => {
    // console.log(room.endDate);
  });

  // recoil에 작성한 모임 정보 저장하기 0927 임지민
  const [isValidated, setIsValidated] = useState(false);
  const onChangeValidation = () => {
    if (room.price && room.minMemberScore) {
      setIsValidated((isValidated: boolean) => (isValidated = true));
    } else {
      setIsValidated((isValidated: boolean) => (isValidated = false));
      // console.log('else로 빠짐');
      // console.log(room.title.trim());
    }
  };

  const onChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;

    setRoom({
      ...room,
      [name]: value,
    });
    onChangeValidation();
    // console.log(room.endDate.split('T'));
  };

  const onChangeNumber = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;

    setRoom({
      ...room,
      [name]: Number(value),
    });
    // console.log(name, typeof(value));
    onChangeValidation();
  };

  const onChangeDate = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;

    // 초까지 붙여서 서버로 보내야 하기 때문에 뒤에 ':00" 붙임 0930 임지민
    setRoom({
      ...room,
      [name]: value + ":00",
    });
    onChangeValidation();
    // 초까지 정상적으로 잘 붙어 있는 것 확인 0930 임지민
    // console.log('백에 보내는 날짜---------------');
    // console.log(room.endDate, typeof(room.endDate));
  };

  // axios 보내기 0930 임지민
  const navigate = useNavigate();
  const onClicktoSubmit = () => {
    // params로 recoil에 저장된 room을 보냄 0930 임지민
    // console.log(room)
    //   createRoom(room).then(({data})=> {
    //     console.log(data);
    //     setRoomList(roomList.concat(data))
    //     // 상세 페이지로 리다이렉트
    //     navigate('/')
    //     // recoil 초기화
    //     resetRecoil();
    //   }).catch((err)=> {
    //     console.log(err);
    //     console.log(err.config.data);
    //     resetRecoil();
    //   })
    // }
    try {
      setLoading(true);
      createRoom(room)
        .then(({ data }) => {
          console.log(data);
          if (data.statusCode === 4010) {
            Swal.fire({
              icon: 'error',
              text: '잔액이 부족합니다. 충전 후에 모임을 생성해주세요'
            }).then(()=>{
              navigate('/wallet')
            })
          } else if (data.statusCode === 4020) {
            Swal.fire({
              icon: 'error',
              text: '지갑이 존재하지 않습니다.',
            }).then(()=>{
              navigate('/wallet/create')
            })
          } else if (data.statusCode === 2010) {
            setLoading(false);
            setRoomList(roomList.concat(data));
            // 상세 페이지로 리다이렉트
            navigate("/");
          }
          // recoil 초기화
          resetRecoil();
        })
        .catch((err) => {
          setLoading(false);
          // console.log(err);
          // console.log(err.config.data);
          
        });
    } catch {
      setLoading(false);
    }
  };

  return (
    // markup 0915 임지민
    <div>
      {isloading ? (
        <div className="w-full h-[100vh] flex flex-col justify-center items-center">
          <div className="flex flex-row">
            <img
              className="w-8 h-8 animate-gelatine mr-1"
              alt="userBadge5"
              src={process.env.PUBLIC_URL + `/img/userBadge5.png`}
            />
            <img
              className="w-8 h-8 animate-gelatine mr-1"
              alt="userBadge4"
              src={process.env.PUBLIC_URL + `/img/userBadge4.png`}
            />
            <img
              className="w-8 h-8 animate-gelatine mr-1"
              alt="userBadge3"
              src={process.env.PUBLIC_URL + `/img/userBadge3.png`}
            />
            <img
              className="w-8 h-8 animate-gelatine mr-1"
              alt="userBadge2"
              src={process.env.PUBLIC_URL + `/img/userBadge2.png`}
            />
            <img
              className="w-8 h-8 animate-gelatine"
              alt="userBadge1"
              src={process.env.PUBLIC_URL + `/img/userBadge1.png`}
            />
          </div>
          <span className="font-semibold text-xl animate-pulse">처리중...</span>
        </div>
      ) : (
        <div className="mt-8 mx-6">
          <div>
            {/* 
        카테고리
        - 아래 줄 자체를 클릭하면 카테고리 선택 페이지로 넘어가게
        - 클릭하면 회색 그림자?가 나오도록
      */}
            <BtnExitToHome></BtnExitToHome>
            <h2 className="inline-block text-xl font-black ml-5">
              모임 만들기
            </h2>
          </div>
          <div className="mt-5">
            <div className="flex justify-between">
              <p className="font-bold mt-3 mb-3 pl-2">모임 기본 정보</p>
              <Link to="/meeting/create">
                <div className="py-3">
                  {/* 
              다시 작성하기 클릭하면 기본 정보 작성 페이지로 넘어가는데 
              이 때 작성한 정보가 기본으로 떠 있도록 처리하기
              0920 임지민
              */}
                  <button
                    type="button"
                    className="text-white text-xs bg-gray-400 px-2 py-1 rounded-xl"
                  >
                    다시 작성하기
                  </button>
                </div>
              </Link>
            </div>
            <MeetingCard name="createRoom" room={room} />
          </div>

          <div className="my-8">
            {/* 기본 정보 작성 */}
            <h3 className="text-lg font-semibold">상세 정보</h3>
            <hr className="my-5" />

            {/* 경고 문구 */}
            <p className="text-red-600 text-sm">
              ⚠ 모집 마감일, 예약 날짜, 최소 태도 점수는(은) 수정이 불가하니
              신중하게 작성해주시기 바랍니다.{" "}
            </p>
            <hr className="my-5" />

            {/* 모집 마감일 */}
            <div className="flex justify-between">
              <label htmlFor="endDate" className="flex mr-2">
                모집 마감일{" "}
              </label>
              <input
                type="datetime-local"
                id="endDate"
                name="endDate"
                className="flex"
                onChange={onChangeDate}
              />
            </div>
            <hr className="my-5" />

            {/* 예약 날짜 */}
            <div className="flex justify-between">
              <label htmlFor="reservationDate" className="flex mr-2">
                만나는 날{" "}
              </label>
              <input
                type="datetime-local"
                id="reservationDate"
                name="reservationDate"
                className="flex"
                onChange={onChangeDate}
              />
            </div>
            {/* <p>{room.reservationDate.toDateString()}</p> */}
            <hr className="my-5" />

            {/* <div className="grid col-start-3 col-end- "> */}
            {/* 금액 */}
            <div className="grid grid-cols-6">
              <label htmlFor="price" className="mr-2 col-span-1 gap-1">
                금액{" "}
              </label>
              <input
                type="number"
                id="price"
                name="price"
                className="col-start-4 col-end-6"
                value={room.price ? room.price : 0}
                placeholder="0"
                onChange={onChangeNumber}
              />
              <p className="text-center">SB</p>
            </div>
            <hr className="my-5" />

            {/* 최소 태도 점수 */}
            <div className="grid grid-cols-6">
              <label htmlFor="minMemberScore" className="mr-2 col-span-3 gap-1">
                최소 태도 점수
              </label>
              <input
                type="number"
                id="minMemberScore"
                name="minMemberScore"
                className="col-start-4 col-end-6"
                value={room.minMemberScore}
                placeholder="50"
                onChange={onChange}
              />
              <p className="text-center">점</p>
            </div>
            {/* <p>{room.minMemberScore}</p> */}
          </div>

          {isValidated && (
            <Link to="/meeting/create/more">
              {/* <p>{}</p> */}
              <div className="grid grid-cols-1 mt-3">
                {/* 모든 칸이 추가된 경우 0927 임지민 */}
                <button
                  type="button"
                  onClick={onClicktoSubmit}
                  className="text-center text-white font-semibold bg-blue-200 rounded py-1"
                >
                  모임 만들기
                </button>
              </div>
            </Link>
          )}
          {/* 한 칸이라도 빈 경우 0927 임지민 */}
          {!isValidated && (
            <div className="grid grid-cols-1 mt-3">
              <button
                type="button"
                className="text-center bg-gray-200 rounded py-1"
              >
                모임 만들기
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default CreateRoomMore;
