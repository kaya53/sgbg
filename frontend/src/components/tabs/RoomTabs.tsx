import { useEffect, useState } from "react";
import RoomInfoTab from "./RoomInfoTab";
import ParticipantListTab from "./ParticipantListTab";
import CommunityTab from "./CommunityTab";


const RoomTabs = (props: any) => {
  const data = [
    {
      id: 0,
      title: "모임 정보",
      component: <RoomInfoTab />,
    },
    {
      id: 1,
      title: "참여자 목록",
      component: <ParticipantListTab room={props.room}/>,
    },
    {
      id: 2,
      title: "커뮤니티",
      component: <CommunityTab room={props.room} isInThisRoom={props.isInThisRoom} isHost={props.isHost}/>,
    },
  ];
  useEffect(()=> {
    // console.log('create room tabs=', props.room.description.split('\n'));
    
  })
  const [index, setIndex] = useState(0);

  return (
    <div className="mt-6">
      {/* 탭 제목 부분 */}
      <ul className="grid grid-cols-3 mb-5">
        {data.map((item) => (
          <li
            key={item.id}
            onClick={() => setIndex(item.id)}
            className="text-center font-semibold"
          >
            {" "}
            {item.title}
          </li>
        ))}
      </ul>
      {/* 탭 내용 부분 */}
      <div className="overflow-scroll max-h-[70vh]">
        {data
          .filter((item) => index === item.id)
          .map((item) => (
            <div key={item.id} className="mx-5">
              {item.component}
            </div>
          ))}
      </div>
    </div>
  );
};

export default RoomTabs;
