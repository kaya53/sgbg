import {
  getParticipantBadge,
  getProgressColor,
} from "../../util/profile";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { readRoom } from "../../api/room";
import { useParams } from "react-router-dom";
import Swal from "sweetalert2";

// host가 무조건 0번째에 오도록 하고, 나머지는 뒤에 concat하기
const ParticipantListTab = (props: any) => {
  const [members, setMembers] = useState([{
    name: '',
    kakaoId: 0,
    email: '',
    hostScore: 0,
    memberScore: 0
  }])
  const [hostName, setHostName] = useState('')
  const { meeting_id } = useParams<{ meeting_id: string }>();
  const navigator = useNavigate()


  useEffect(() => {
    //axios
    if(meeting_id) {
      readRoom(meeting_id)
        .then(({ data }) => {
          setMembers(data.roomInfo.members);
          setHostName(data.roomInfo.hostName)
          console.log(data.roomInfo.members);
        })
        .catch((e) => {
          Swal.fire({
            position: "center",
            icon: "error",
            title: "참여자 목록을 불러올 수 없습니다.",
            showConfirmButton: false,
            timer: 1500,
          }).then(() => {
            navigator(-1);
          });
        });
      }
  }, []);

  return (  
    <div className="ml-3">
      <p className="text-lg font-semibold mb-4">참여자 ({members.length})</p>

        {members.map((member) => 
          <div className="my-1 pb-2 border-b border-gray-300 ">

            {/* 
              유저 뱃지, 아이디 
              - 클릭하면 마이페이지로 이동할 수 있도록 연결해두었음 0922 임지민
            */}
            {members.length && (
              <div className="grid grid-cols-2">
                <Link to={`/profile/history/${member.kakaoId}`}>
                  <div className="flex flex-row justify-start pb-1">
                    <div className="w-[25px] h-[25px] mr-2">
                      <img
                        className="w-full h-full"
                        src={
                          process.env.PUBLIC_URL + `/img/userBadge` + getParticipantBadge(member.memberScore) + ".png"
                        }
                        alt="사용자 뱃지"
                      />
                    </div>
                    {(member.name === hostName) && (
                      <span className="font-semibold leading-tightl">{member.name} 👑</span>
                    )}
                    {(member.name !== hostName) && (
                      <span className="font-semibold leading-tightl">{member.name}</span>
                    )} 
                  </div>
                </Link>

                {/* 
                  태도 점수 바 
                  - progress bar가 차오르는 효과 넣기(priority low) 0922 임지민
                */}
                <div className="">
                  <div className="w-full bg-gray-300 rounded-full">
                    <div
                      className={`${getProgressColor(member.memberScore)} text-xs font-medium text-white text-right p-0.5 leading-none rounded-full py-1`}
                      style={{ width: `${member.memberScore}%` }}>
                      {member.memberScore}%
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>
        )}


    </div>);
};

export default ParticipantListTab;