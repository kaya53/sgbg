import UserReviewResultCard from "./UserReviewResultCard";
import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { formatOnlyDate } from "../../util/room";

const FinishedMeetingCard = (props: any) => {
  const getSuccess = () => {
    if (props.room.members.length && props.room.hostReviewResult) {
      return Math.ceil((props.room.hostReviewResult / props.room.members.length) * 100);
    } else {
      return -1;
    }
  };
  return (
    <div className="border rounded-lg p-2">
      {/* 미팅 기본 정보, 성공률 */}
      {/* 미팅 기본 정보 */}
      <div className="flex flex-col">
        {/* 카테고리, 위치 */}
        <div className="flex justify-between">
          <div>
            <span className="text-xs text-blue-200 font-bold border border-blue-200 border-1 py-0.5 px-1.5 rounded-xl mr-1">
              {props.room.childCategory && props.room.childCategory}
            </span>
            <span className="font-light text-xs ml-1">
              <FontAwesomeIcon icon={faMapMarkerAlt} className="text-xs mr-1" />
              {props.room.location.roadAddress && props.room.location.roadAddress}
            </span>
          </div>
          <p className="font-light text-xs">
            {props.room.reservationDate && formatOnlyDate(props.room.reservationDate)}
          </p>
        </div>
        {/* 미팅 제목 */}
        <span className="font-semibold my-1">{props.room.title && props.room.title}</span>
      </div>

      {/* 평가 정보 */}
      <div className="">
        <UserReviewResultCard result={props.room.memberReviewResult} />
        <div className="text-end">
          <p className="text-sm font-semibold">
            {`성공률 ${getSuccess()}%`}
          </p>
          <p className="text-xs">{`${
            props.room.members.length && props.room.members.length
          }명 중 ${props.room.hostReviewResult && props.room.hostReviewResult}명이 성공을 선택했습니다.`}</p>
        </div>
      </div>

      {/* 성공률 */}
    </div>
  );
};

export default FinishedMeetingCard;
