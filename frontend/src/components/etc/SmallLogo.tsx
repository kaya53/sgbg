import React from "react";


const SmallLogo = () => {
  return (
    <div className="w-full h-[55px] my-2">
      <img
        className="max-h-full mx-auto"
        src={process.env.PUBLIC_URL + `/img/sgbg-logo.png`}
        alt="SgBg 로고"
      />
    </div>
  );
};

export default SmallLogo;
