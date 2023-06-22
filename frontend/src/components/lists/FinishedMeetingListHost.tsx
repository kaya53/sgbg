import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { getFinishedHostList } from "../../api/profile";
import { location } from "../../util/room";
import FinishedMeetingCard from "../cards/FinishedMeetingCard";

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
  members: {
    kakaoId: number;
    name: string;
    email: string;
    hostScore: number;
    memberScore: number;
  }[];
  hostReview: true;
  memberReview: true;
  hostReviewResult: number;
  memberReviewResult: {
    best: number;
    good: number;
    bad: number;
  };
};
const FinishedMeetingListHost = () => {
  const { user_id } = useParams<string>();
  const [rooms, setRooms] = useState<Room[]>([]);
  useEffect(() => {
    if (user_id) {
      getFinishedHostList(user_id).then(({ data }) => {
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

export default FinishedMeetingListHost;
