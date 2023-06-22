import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import BigLogo from "./BigLogo";
import SmallLogo from "./SmallLogo";


const Logo = () => {
  const [ isOpen, setIsOpen ] = useState(false)
  const navigate = useNavigate()

  const onClickLogo = () => {
    const url = window.location.href
    // console.log(url);
    //url === 'http://localhost:3000/' || 
    if (url === 'http://localhost:3000/' || url === 'https://j7a707.p.ssafy.io/') {
      console.log('open drawer');
      !isOpen? setIsOpen(true) : setIsOpen(false)
    } else {
      navigate('/')
    }
  }

  useEffect(()=>{
    // setIsOpen(false)
  })

  return (
    <div onClick={onClickLogo}>
      {isOpen? <BigLogo/> : <SmallLogo />}
    </div>
  );
};

export default Logo;
