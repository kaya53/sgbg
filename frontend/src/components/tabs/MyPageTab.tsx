import { useState } from "react";
import MeetingListHost from "../lists/MeetingListHost";
import MeetingListParticipant from "../lists/MeetingListParticipant";

const MyPageTab = () => {
  const data = [
    {
      id: 0,
      title: "참여한 모임",
      component: <MeetingListParticipant />,
    },
    {
      id: 1,
      title: "만든 모임",
      component: <MeetingListHost />,
    },
  ];
  const [index, setIndex] = useState<number>(0);
  return (
    <div className="w-full">
      {/* 탭 제목 부분 */}
      <ul className="grid grid-cols-2 p-3">
        {data.map((item) => (
          <li
            key={item.id}
            onClick={() => setIndex(item.id)}
            className={`${
              index === item.id ? "bg-blue-200 text-white" : "text-black"
            } text-center font-semibold rounded p-1`}>
            {" "}
            {item.title}
          </li>
        ))}
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

export default MyPageTab;
