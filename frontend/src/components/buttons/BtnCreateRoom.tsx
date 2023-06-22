import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useNavigate } from "react-router-dom";
import { auth } from "../../store/auth";
import { useRecoilState } from "recoil";
import Swal from "sweetalert2";


const BtnCreateRoom = () => {
  const [userAuth] = useRecoilState(auth)
  const navigate = useNavigate()


  const onClick = () =>{
    navigate('/meeting/create')    

    // if (userAuth.isLogined) {
    //   navigate('/meeting/create')    
    // } else if (!userAuth.isLogined) {
    //   Swal.fire({
    //     text: '로그인 후 이용 가능합니다.',
    //     icon: 'error',
    //   }).then(()=> navigate('/login'))
    // }
  }

  return (
    <div className="fixed bottom-[65px] right-[5px] w-14 h-14 bg-yellow-100 rounded-full flex justify-center items-center">
      <FontAwesomeIcon icon={faPlus} className="text-4xl text-blue-200" onClick={onClick}/>
    </div>
  );
};

export default BtnCreateRoom;
