import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useRecoilValue } from "recoil";
import Swal from "sweetalert2";
import { postEvalMember } from "../api/eval";
import { readRoom } from "../api/room";
import UserReviewCard from "../components/cards/UserReviewCard";
import Logo from "../components/etc/Logo";
import { auth } from "../store/auth";

type EvalMember = {
  kakaoId: number;
  review: "BEST" | "GOOD" | "BAD";
};

type Member = {
  name: string;
  kakaoId: string;
  email: string;
  hostScore: number;
  memberScore: number;
};

type Room = {
  parentCategory: string;
  title: string;
};

const UserReview = () => {
  const navigator = useNavigate();
  const { meeting_id } = useParams<{ meeting_id: string }>();
  const userAuth = useRecoilValue(auth);
  const [room, setRoom] = useState<Room>({ parentCategory: "", title: "" });
  const [members, setMembers] = useState<Member[]>([]);
  const [evals, setEvals] = useState<EvalMember[]>([]);
  useEffect(() => {
    if (meeting_id) {
      readRoom(meeting_id).then(({ data }) => {
        setRoom({
          parentCategory: data.roomInfo.parentCategory,
          title: data.roomInfo.title,
        });
        setMembers(
          data.roomInfo.members.filter(
            (member: Member) => member.kakaoId === userAuth.userId
          )
        );
        setEvals(
          data.roomInfo.members.map((member: any) => {
            return { kakaoId: member.kakaoId, review: "GOOD" };
          })
        );
      });
    }
  }, []);

  const handleEvalMember = (
    kakaoId: number,
    value: "BEST" | "GOOD" | "BAD"
  ) => {
    const newEvals = evals.map((eva) => {
      if (eva.kakaoId === kakaoId) {
        return { kakaoId: kakaoId, review: value };
      } else {
        return eva;
      }
    });
    setEvals(newEvals);
  };

  const handleSubmit = () => {
    //axios
    if (meeting_id) {
      postEvalMember(meeting_id, evals).then(({ data }) => {
        if (data.statusCode === 2010) {
          Swal.fire({
            toast: true,
            position: "center",
            icon: "success",
            showConfirmButton: false,
            timer: 1000,
            title: `평가가 완료되었습니다.`,
          });
          navigator("/");
        }
      });
    }
  };

  return (
    <div className="w-full h-full">
      <div className="h-[25vh] overflow-hidden">
        {/* 로고 */}
        <Logo />
        {/* 멘트 */}
        <div className="flex flex-col justify-center items-center">
          <span>{`[${room.parentCategory}] ${room.title}에서`}</span>
          <span>{`다른 사람들과 즐거우셨나요?`} </span>
          <span>{`의견을 남겨주세요! 🙂`}</span>
        </div>
        {/* 제출 버튼 */}
        <div className="flex justify-end px-2 mb-1">
          <button
            className=" bg-blue-300 text-white text-sm rounded-xl py-1 px-3"
            onClick={handleSubmit}
          >
            제출하기
          </button>
        </div>
      </div>
      {/* 참여자 내역 */}
      <div className="w-per95 h-[75vh] m-auto">
        {members.map((member) => (
          <UserReviewCard
            key={member.kakaoId}
            member={member}
            handleEvalMember={handleEvalMember}
          />
        ))}
      </div>
    </div>
  );
};

export default UserReview;
