import { useEffect, useState } from "react";
import BtnProfileInfo from "../../components/buttons/BtnProfileInfo";
import Logo from "../../components/etc/Logo";
import FinishedMeetingListHost from "../../components/lists/FinishedMeetingListHost";
import FinishedMeetingListParticipant from "../../components/lists/FinishedMeetingListParticipant";
import { getMypage } from "../../api/profile";
import { useParams } from "react-router-dom";

const ProfileHistory = () => {
  const { user_id } = useParams<string>();
  const [user, setUser] = useState<{
    kakaoId: string;
    name: string;
    email: string;
    hostScore: number;
    memberScore: number;
  }>({ kakaoId: "", name: "", email: "", hostScore: 0, memberScore: 0 });

  useEffect(() => {
    if (user_id) {
      getMypage(user_id).then(({ data }) => {
        if (data.statusCode === 2000) {
          setUser({ ...data.user });
          console.log(data.user);
          
        }
      });
    }
  }, []);
  const data = [
    {
      id: 0,
      component: <FinishedMeetingListParticipant />,
    },
    {
      id: 1,
      component: <FinishedMeetingListHost />,
    },
  ];
  const [index, setIndex] = useState<number>(0);
  return (
    <div className="w-full h-full">
      {/* 로고 */}
      <Logo />
      {/* 유저 프로필(아이디, 매너온도, 모임 성공률) */}
      <div className="h-per25 flex flex-col px-2 mx-2 mb-1">
        <span className="border-b border-gray-200 pb-2 font-bold text-lg leading-tight">
          {user.name}님의 완료한 모임 이력
        </span>
        <ul className="grid grid-cols-2 gap-2">
          <li onClick={() => setIndex(0)}>
            <BtnProfileInfo
              type={"Participant"}
              user={user}
              active={index === 0}
            />
          </li>
          <li onClick={() => setIndex(1)}>
            <BtnProfileInfo type={"Host"} user={user} active={index === 1} />
          </li>
        </ul>
      </div>
      {/* 완료 모임 내역 카드 */}
      <div className="w-full max-h-[70vh] overflow-scroll">
        {data
          .filter((item) => index === item.id)
          .map((item) => (
            <div className="mx-3">{item.component}</div>
          ))}
      </div>
    </div>
  );
};

export default ProfileHistory;
