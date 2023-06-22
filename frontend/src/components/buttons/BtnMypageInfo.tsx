import {
  getParticipantNickname,
  getHostNickname,
  getParticipantBadge,
  getHostBadge,
} from "../../util/profile";

type BtnMypageInfoProps = {
  type: "Participant" | "Host";
  user: {
    email: string,
    memberScore: number;
    hostScore: number;
    name: string;
  };
};

const BtnMypageInfo = ({ type, user }: BtnMypageInfoProps) => {
  return (
    <div className={`flex flex-col justify-center items-center p-2`}>
      <span className="flex flex-row">
        {type === "Participant" ? (
          <img
            className="w-6 mr-1"
            src={
              process.env.PUBLIC_URL +
              `/img/userBadge${getParticipantBadge(user.memberScore)}.png`
            }
            alt="참여자 뱃지"
          />
        ) : (
          <span className="text-lg mr-1">{getHostBadge(user.hostScore)}</span>
        )}
        <span>
          {type === "Participant"
            ? getParticipantNickname(user.memberScore)
            : getHostNickname(user.hostScore)}
        </span>
      </span>
      <span>{type === "Participant" ? user.memberScore : user.hostScore}%</span>
    </div>
  );
};

export default BtnMypageInfo;
