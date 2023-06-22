import { faSearch, faWallet, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link, useNavigate } from "react-router-dom";
import { auth } from "../../store/auth";
import { useRecoilValue } from "recoil";
import { checkWallet } from "../../api/wallet";
import Swal from "sweetalert2";

const Navbar = () => {
  const navigator = useNavigate();
  const userAuth = useRecoilValue(auth);
  const handleWallet = () => {
    // navigator("/wallet/create");

    checkWallet()
      .then(({ data }) => {
        if (data.statusCode === 2000) {
          // console.log('there is wallet');
          navigator("/wallet");
        } else {
          navigator("/wallet/create");
        }
      })
      .catch((err) => {
        console.error(err.response.request.status);
        if(err.response.request.status === 500 ||err.response.request.status === 404) {
          Swal.fire({
            icon: 'error',
            text: '로그인 이후에 이용해주세요'
          }).then(()=>{ navigator('/login')})
        }
      });
  };
  return (
    <div className="w-full h-[60px] fixed bottom-0 flex flex-row justify-between items-center bg-gray-100 border-t border-gray-200 px-5">
      <Link to="/">
        <div className="w-[1.875rem]">
          <img src={process.env.PUBLIC_URL + `/img/home.png`} alt="홈아이콘" />
        </div>
      </Link>
      <Link to="search/">
        <FontAwesomeIcon icon={faSearch} className="text-blue-600 text-3xl" />
      </Link>
      <div onClick={handleWallet}>
        <FontAwesomeIcon icon={faWallet} className="text-blue-600 text-3xl" />
      </div>
      {userAuth.isLogined ? (
        <Link to={`profile/${userAuth.userId}`}>
          <FontAwesomeIcon icon={faUser} className="text-blue-600 text-3xl" />
        </Link>
      ) : (
        <Link to="login">
          <FontAwesomeIcon icon={faUser} className="text-blue-600 text-3xl" />
        </Link>
      )}
    </div>
  );
};

export default Navbar;
