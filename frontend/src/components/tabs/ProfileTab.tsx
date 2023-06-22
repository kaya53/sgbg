import { useState } from "react";
import MeetingListHost from "../lists/MeetingListHost";
import MeetingListParticipant from "../lists/MeetingListParticipant";

const ProfileTab = () => {
  const data = [
    {
      id: 0,
      component: <MeetingListParticipant />,
    },
    {
      id: 1,
      component: <MeetingListHost />,
    },
  ];
  const [index, setIndex] = useState<number>(0);
  return (
    <div className="w-full">
      {/* 탭 제목 부분 */}
      <ul className="grid grid-cols-2 p-3">
        <li key={0}></li>
        <li key={1}></li>
      </ul>
      {/* 탭 내용 부분 */}
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

export default ProfileTab;
