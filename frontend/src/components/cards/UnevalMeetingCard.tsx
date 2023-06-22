import MeetingCard from "./MeetingCard";

const UnevalMeetingCard = (props: any) => {
  const handleClick = () => {
    props.handleReview(props.room.hostReview, props.room.roomId);
  }
  return (
    <div className="relative">
      <MeetingCard room={props.room} />
      <button className="absolute bottom-1 right-1 rounded bg-red-100 p-1"
        onClick={handleClick}>리뷰하러 가기</button>
    </div>
  );
};

export default UnevalMeetingCard;
