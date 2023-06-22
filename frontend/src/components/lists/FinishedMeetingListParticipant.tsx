import { useEffect, useState } from "react";
import FinishedMeetingCard from "../cards/FinishedMeetingCard";
import { location } from "../../util/room";
import { Link, useParams } from "react-router-dom";
import { getFinishedParticipantList } from "../../api/profile";

type Room = {
  roomId: number;
  hostId: number;
  hostName: string;
  parentCategory: string;
  childCategory: string;
  title: string;
  maxUser: number;
  minUser: number;
  price: number;
  location: location;
  reservationDate: string;
  endDate: string;
  minMemberScore: number;
  description: string;
  members: 
    {
      kakaoId: number;
      name: string;
      email: string;
      hostScore: number;
      memberScore: number;
    }[]
  ;
  hostReview: true;
  memberReview: true;
  hostReviewResult: number;
  memberReviewResult: {
    best: number;
    good: number;
    bad: number;
  };
};

const FinishedMeetingListParticipant = () => {
  const { user_id } = useParams<string>();
  const [rooms, setRooms] = useState<Room[]>([]);
  useEffect(() => {
    if (user_id) {
      getFinishedParticipantList(user_id).then(({ data }) => {
        if (data.statusCode === 2000) {
          setRooms([...data.roomListInfo]);
        }
      });
    }
  }, []);
  return (
    <div className="w-full">
      {rooms.map((room) => (
        <Link to={`/meeting/${room.roomId}`}>
           <FinishedMeetingCard key={room.roomId} room={room} />
        </Link>
      ))}
    </div>
  );
};

export default FinishedMeetingListParticipant;
