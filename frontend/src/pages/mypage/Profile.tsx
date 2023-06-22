import { Link, useNavigate, useParams } from "react-router-dom";
import ProfileCard from "../../components/cards/ProfileCard";
import Logo from "../../components/etc/Logo";
import MyPageTab from "../../components/tabs/MyPageTab";
import { auth } from "../../store/auth";
import { useRecoilState } from "recoil";
import { logout } from "../../api/auth";
import Swal from "sweetalert2";
import { useEffect, useState } from "react";
import { getMypage } from "../../api/profile";

type User = {
  kakaoId: string;
  name: string;
  email: string;
  hostScore: number;
  memberScore: number;
};

const Profile = () => {
  const [user, setUser] = useState<User>({
    kakaoId: "",
    name: "",
    email: "",
    hostScore: 0,
    memberScore: 0,
  });
  const { user_id } = useParams<{ user_id: string }>();
  const [userAuth, setUserAuth] = useRecoilState(auth);
  const navigator = useNavigate();

  useEffect(() => {
    if (userAuth.isLogined) {
      if (userAuth.userId !== user_id) {
        navigator("/");
        return;
      }
      getMypage(user_id)
        .then(({ data }) => {
          setUser({ ...data.user });
        })
        .catch(() => {});
    } else {
      navigator('/login')
    }
  }, []);

  const handleLogout = () => {
    logout()
      .then(({ data }) => {
        if (data.statusCode === 2000) {
          setUserAuth({ isLogined: false, userId: "" });
          navigator("/");
        } else {
          Swal.fire({
            toast: true,
            position: "center",
            icon: "error",
            showConfirmButton: true,
            title: `로그아웃 실패`,
          });
        }
      })
      .catch(() => {
        Swal.fire({
          toast: true,
          position: "center",
          icon: "error",
          showConfirmButton: true,
          title: `로그아웃 실패.`,
        });
      });
  };

  return (
    <div className="w-full h-full">
      {/* 로고 */}
      <Logo />
      {/* 유저 프로필(아이디, 매너온도, 모임 성공률) */}
      <div className="h-per25 flex flex-col px-2 mx-2 mb-1">
        {/* 유저 뱃지, 아이디, 완료 이력 보기 */}
        <div className="flex flex-col border-b border-gray-200 pb-2">
          <div className="flex flex-row justify-between items-end">
            <span className="font-bold text-xl leading-tight">
              {user?.name ? user?.name : ""}
            </span>
            {
              <button
                className="bg-slate-400 rounded p-1"
                onClick={handleLogout}
              >
                로그아웃
              </button>
            }
          </div>
          <Link
            className="font-light text-xs mt-2"
            to={`/profile/history/${user.kakaoId}`}
          >
            {" "}
            {"> 완료한 모임 이력 보기"}
          </Link>
        </div>

        <ProfileCard user={user} />
      </div>
      {/* 참여한 모임 탭 */}
      <MyPageTab />
    </div>
  );
};

export default Profile;
