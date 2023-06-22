import { useEffect, useState } from "react";
import { getParticipantBadge } from "../../util/profile";

const UserReviewCard = (props: any) => {
  const [selected, setSelected] = useState<string>("GOOD");
  const handleClick = (name: string) => {
    props.handleEvalMember(props.member.kakaoId, name);
    setSelected(name);
  };
  return (
    <div className="w-full flex flex-row justify-between items-center border rounded-lg p-2 mb-1">
      {/* 피평가자 정보 */}
      <div className="flex flex-row">
        {/* 사용자 뱃지 */}
        <div className="w-[2rem] h-[2rem] mr-2">
          <img
            className="w-full h-full"
            src={
              process.env.PUBLIC_URL +
              `/img/userBadge` +
              getParticipantBadge(props.member.memberScore) +
              ".png"
            }
            alt="사용자 뱃지"
          />
        </div>
        <span className="font-light leading-loose">{props.member.name}</span>
      </div>
      {/* 점수 버튼 */}
      <div>
        <button
          name="BEST"
          className={`${
            selected === "BEST" ? "bg-yellow-100" : "bg-gray-300"
          } rounded-lg p-1 font-light text-xs mr-1`}
          onClick={() => handleClick("BEST")}
        >
          <span className="mr-1">😍</span>
          <span className="mr-1">최고에요!</span>
        </button>
        <button
          name="GOOD"
          className={`${
            selected === "GOOD" ? "bg-yellow-100" : "bg-gray-300"
          } rounded-lg p-1 font-light text-xs mr-1`}
          onClick={() => handleClick("GOOD")}
        >
          <span className="mr-1">😁</span>
          <span className="mr-1">좋아요!</span>
        </button>
        <button
          name="BAD"
          className={`${
            selected === "BAD" ? "bg-yellow-100" : "bg-gray-300"
          } rounded-lg p-1 font-light text-xs mr-1`}
          onClick={() => handleClick("BAD")}
        >
          <span className="mr-1">😑</span>
          <span className="mr-1">별로에요</span>
        </button>
      </div>
    </div>
  );
};

export default UserReviewCard;
