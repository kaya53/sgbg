import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { auth } from "../store/auth";
import { login } from "../api/auth";
import { kakaoRedirect} from "../api/config";
import axios from "axios";
import Swal from "sweetalert2";

axios.defaults.withCredentials = true;

const Login = () => {
  const [islogining, setLogining] = useState<boolean>(false);
  const [userAuth, setUserAuth] = useRecoilState(auth);
  const navigate = useNavigate();
  useEffect(() => {
    if (userAuth.isLogined) {
      Swal.fire({
        toast: true,
        position: "center",
        showConfirmButton: true,
        title: `잘못된 접근입니다.`,
      });
      navigate("/");
      return;
    }
    const url = new URLSearchParams(window.location.search);
    const code = url.get("code");
    if (code) {
      try {
        setLogining(true);
        login(code).then(({ data }) => {
          setUserAuth({ isLogined: true, userId: data.user.kakaoId });
          setLogining(false);
          navigate("/");
        });
      } catch {
        setLogining(false);
        Swal.fire({
          toast: true,
          position: "center",
          showConfirmButton: true,
          title: `로그인 실패하였습니다.`,
        });
      }
    }
  }, []);
  return (
    <div className="w-full h-full">
      {/* 캐치프레이즈1 */}
      <div className="flex justify-center items-center my-10">
        <span className="font-bold text-3xl">happy together! sgbg,</span>
      </div>
      {/* 로고 */}
      <div className="w-per85 mx-auto">
        <img
          className="max-w-full"
          src={process.env.PUBLIC_URL + `/img/sgbg-logo.png`}
          alt="로고"
        />
      </div>
      {/* 캐치프레이즈2 */}
      <div className="flex flex-col justify-center items-center text-lg my-10">
        <span>혼자 하기 힘든,</span>
        <span>혼자 하기 싫은 사람들을 위한</span>
        <span>블록체인 기반 모임 서비스</span>
      </div>
      {/* 카카오 로그인 */}
      {islogining ? (
        <div className="flex flex-col justify-center items-center">
          <img
            className="w-8 h-8 animate-bounce"
            src={process.env.PUBLIC_URL + `/img/userBadge5.png`}
          />
          <span className="font-semibold text-xl">로그인 중...</span>
        </div>
      ) : (
        <a
          href={`https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${process.env.REACT_APP_KAKAO_API_KEY}&redirect_uri=${kakaoRedirect}`}
          className="flex justify-center items-center my-20"
        >
          <img
            className="max-w-full"
            src={process.env.PUBLIC_URL + `/img/kakao_login.png`}
            alt="로고"
          />
        </a>
      )}
    </div>
  );
};

export default Login;
