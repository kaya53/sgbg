import CategoriesBar from "../components/bars/CategoriesBar";
import FilterBar from "../components/bars/FilterBar";
import BtnCreateRoom from "../components/buttons/BtnCreateRoom";
import MeetingCard from "../components/cards/MeetingCard";
import Logo from "../components/etc/Logo";
import { useEffect, useState } from "react";
import { getRoomList } from "../api/main";
import { roomMore } from "../util/room";


// const PAGE_SIZE = 10 * Math.ceil(visualViewport.width / 100)

const Main = () => {
  // axios 통신 0929 임지민
  const [mainRoomList, setMainRoomList] = useState<roomMore[]>([]);
  const [ isOpen, setIsOpen ] = useState(false)
  const [ selected, setSelected] = useState('')

  const fetchMainRoomList = async () => {
    // setFetching(true)

    await getRoomList("roomId, DESC").then(({ data }) => {
      console.log(data.roomListInfo);
      setMainRoomList(data.roomListInfo);
    });
  };


  // 화면에 띄우기 위한 임시 리스트
  useEffect(() => {
    setIsOpen(false)
    fetchMainRoomList();
  }, []);

  // useEffect(()=>{
    // const today = new Date().getTime()
    // const endDate = new Date(room.endDate).getTime()

  //   if(selected === 'ongoing'){
  //     getRoomList("roomId, DESC").then(({data})=>{

  //     })
  //     const result = mainRoomList.filter(room => (new Date(room.endDate).getTime() - new Date().getTime() < 0))
  //     setMainRoomList(result)
  //   } else if (selected === 'imminent') {
  //     const result = mainRoomList.filter(room => ((new Date(room.endDate).getTime() - new Date().getTime()) <= 86400000 ) && (new Date(room.endDate).getTime() - new Date().getTime()) > 0)
  //     setMainRoomList(result)
  //   } else if (selected === 'done' ) {
  //     const result = mainRoomList.filter(room => ((new Date(room.endDate).getTime() - new Date().getTime()) <= 86400000 ) && (new Date(room.endDate).getTime() - new Date().getTime()) > 86400000)
  //     setMainRoomList(result)
  //   } else {
  //     setMainRoomList(mainRoomList)
  //   }
  // },[selected])

  return (
    <div>
      {/* 로고 */}
      <div>
        <Logo />
      </div>
      {/* 카테고리 */}
      <CategoriesBar />
      {/* 필터바 */}
      {/* <FilterBar setSelected={setSelected}/> */}
      {/* 모임리스트 */}
      <div className="w-per95 m-auto grid grid-cols-1 gap-1">
        {mainRoomList.map((room: roomMore) => (
          <div key={room.roomId}>
            {/* <p>{room.title}</p> */}
            <MeetingCard name="main" room={room}></MeetingCard>
          </div>
        ))}
      </div>

      {/* 모임버튼 */}
      <div>
        <BtnCreateRoom />
      </div>

      {/* <MeetingReviewModal /> */}
    </div>
  );
};

export default Main;
