import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import Swal from "sweetalert2";
import { checkWallet, makeWallet } from "../../api/wallet";
import BigLogo from "../../components/etc/BigLogo";
import { auth } from "../../store/auth";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faWallet } from "@fortawesome/free-solid-svg-icons";

const CreateWallet = () => {
  const navigator = useNavigate();
  const reg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8}/;
  const userAuth = useRecoilValue(auth);
  
  useEffect(() => {
    if (!userAuth.isLogined) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: '로그인이 필요합니다.',
        showConfirmButton: false,
        timer: 1500
      });
      navigator("/login");
      return;
    } else {
      checkWallet().then(({data}) => {
        if (data.statusCode === 2000) {
          navigator("/wallet");
          return;
        }
      })
    }
  },[]);

  const handleCreateWallet = () => {
    Swal.fire({
      title: "사용할 지갑 비밀번호를 정해주세요.",
      text:"영문,숫자를 합하여 8자가 되어야합니다.",
      input: "password",
      inputAttributes: {
        autocapitalize: "off",
      },
      showCancelButton: true,
      confirmButtonText: "제출",
      showLoaderOnConfirm: true,
      preConfirm: (pw) => {
        if (pw.match(reg)) {
          return makeWallet(pw)
          .then(({ data }) => {
            if (data.statusCode === 2010) {
              console.log('wallet data=', data);
              Swal.showValidationMessage(`지갑 생성에 성공했습니다.`);
              navigator("/wallet");
            } else {
              // throw new Error(data.message);
            }
          }).catch((error) => {
            console.log('wallet error= ', error);
            Swal.showValidationMessage(`지갑 생성에 실패했습니다.`);
          })
        } else {
          Swal.showValidationMessage(`비밀번호가 적절하지 않습니다.`);
        }
      },
      allowOutsideClick: false,
    });
  }
  return (<div>
  {/* 로고 */}
  <BigLogo/>
  {/* 캐치프레이즈2 */}
  {/* <div className="mb-10 mx-5 p-3 rounded-lg bg-red-50">
    <p className='text-xl mb-2'>
      <strong>⚠ warning</strong>
    </p>
    <div className='flex flex-col justify-start font-semibold'>
      {warning.map(item => 
        <div className='flex justify-start mb-1'>
          <p className='mr-2'>{item.order}.</p>
          <p>
            <strong className='text-red-100 underline'>{item.accent}</strong>
            {item.content}</p>
        </div>
      )}
    </div>
  </div> */}
  {/* 캐치프레이즈2 */}
  <div className="flex flex-col justify-center items-center text-lg font-semibold mt-5 mb-10">
        <span>혼자 하기 힘든,</span>
        <span>혼자 하기 싫은 사람들을 위한</span>
        <span>블록체인 기반 모임 서비스</span>
  </div>
  {/* 지갑 생성하기 버튼 */}
  <div className="my-15 flex justify-center mx-5 ">
    <button 
          className='rounded font-semibold text-xl bg-blue-200 py-2 px-10 mt-10 text-white animate-bounce'
        onClick={handleCreateWallet}>
        <FontAwesomeIcon icon={faWallet} className="text-white text-3xl mr-3" />
        지갑 생성하기</button>
  </div>    

  </div>);
};

export default CreateWallet;