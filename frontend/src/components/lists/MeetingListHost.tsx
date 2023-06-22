import MeetingCard from "../cards/MeetingCard";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getMyPageHostList } from "../../api/profile";
import UnevalMeetingCard from "../cards/UnevalMeetingCard";
import MeetingReviewModal from "../modals/MeetingReviewModal";

type room = {
  roomId: number;
  hostId: number;
  hostName: string;
  parentCategory: string;
  childCategory: string;
  title: string;
  maxUser: number;
  minUser: number;
  price: number;
  location: {
    locationId: string;
    name: string;
    latitude: string;
    hardness: string;
    roadAddress: string;
  };
  reservationDate: string;
  endDate: string;
  minMemberScore: number;
  description: string;
  members: [
    {
      kakaoId: string;
      name: string;
      email: string;
      hostScore: number;
      memberScore: number;
    }
  ];
  hostReview: boolean;
  memberReview: boolean;
};

const MeetingListHost = () => {
  const navigator = useNavigate();
  const [isVisibleModal, setIsVisibleModal] = useState<boolean>(false);
  const [selectedRoomId, setSelectedRoomId] = useState<number>(-1);
  const [roomList, setRoomList] = useState<room[]>([]);
  const [ isEmpty, setIsEmpty ] = useState<boolean>(false)


  useEffect(() => {
    getMyPageHostList()
      .then(({ data }) => {
        console.log(data);
        setRoomList([...data.roomListInfo]);
        data.roomListInfo.length === 0? setIsEmpty(true):setIsEmpty(false)
      })
      .catch();
  }, []);

  const handleReview = (host: boolean, roomId: number) => {
    if (host) {
      navigator(`/eval/${roomId}`);
    } else {
      setSelectedRoomId(roomId);
      setIsVisibleModal(true);
    }
  };

  return (
    <div className="w-full">
      {isEmpty && (
          <p className="mt-10 text-lg text-center font-semibold">참여한 모임이 없습니다.</p>
        )}
      {roomList.map((room) => {
        /* 
        hostReview == null && memberReview == null : review하는 날이 아닌 경우
        hostReview == false && memberReview == false : review하는 날이고, review를 해야 하는 경우
        hostReview == true && memberReview == false : hostReview만 한 경우
        hostReview == true && memberReview == true : hostReview, memberReview 모두 한 경우 
        */
        if (room.hostReview === null) {
          return (
            <MeetingCard
              key={room.roomId}
              name="meetingListParticipant"
              room={room}
            />
          );
        } else {
          return room.hostReview && room.memberReview ? (
            <MeetingCard
              key={room.roomId}
              name="meetingListParticipant"
              room={room}
            />
          ) : (
            <UnevalMeetingCard
              key={room.roomId}
              name="meetingListParticipant"
              room={room}
              handleReview={handleReview}
            />
          );
        }
      })}
      <MeetingReviewModal
        isVisible={isVisibleModal}
        setIsVisible={setIsVisibleModal}
        roomId={selectedRoomId}
      />
    </div>
  );
};

export default MeetingListHost;
