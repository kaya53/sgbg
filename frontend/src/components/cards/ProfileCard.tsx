import BtnMypageInfo from "../buttons/BtnMypageInfo";
const ProfileCard = (props: any): any => {
  return (
    <div className="grid grid-cols-2">
      <BtnMypageInfo type={"Participant"} user={props.user} />
      <BtnMypageInfo type={"Host"} user={props.user} />
    </div>
  );
};

export default ProfileCard;
