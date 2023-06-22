import { useNavigate } from "react-router-dom";


const CategoriesBar = () => {


  const navigate = useNavigate()
  const onClick = (e: React.MouseEvent<HTMLDivElement>, category:string) => {
    navigate({
      pathname: 'search/result',
      search: `parentcategory=${category}`,
    })
  }


  return (
    <div className="grid grid-cols-6 gap-1 my-3 py-5 bg-gray-100">
      {/*이색놀거리 */}
      <div className="w-full flex flex-col justify-center items-center" onClick={e => onClick(e, '이색놀거리')}>
        <div className="bg-gray-300 w-10 h-10 rounded-full">
          <img className="" src={process.env.PUBLIC_URL + `/img/이색.png`} alt="이색놀거리" />
        </div>
        <span className="font-light text-[10px] mt-1 mx-auto">이색놀거리</span>
      </div>

      {/*야외활동*/}
      <div className="flex flex-col justify-center items-center" onClick={e => onClick(e, '야외활동')}>
        <div className="bg-gray-300 w-10 h-10 rounded-full">
          <img className="" src={process.env.PUBLIC_URL + `/img/레져.png`} alt="레져" />
        </div>
        <span className="font-light text-[10px] mt-1 mx-auto">야외활동</span>
      </div>

      {/*원데이클래스 */}
      <div className="flex flex-col justify-center items-center" onClick={e => onClick(e, '원데이클래스')}>
        <div className="bg-gray-300 w-10 h-10 rounded-full">
          <img className="" src={process.env.PUBLIC_URL + `/img/공예.png`} alt="원데이클래스" />
        </div>
        <span className="font-light text-[10px] mt-1 mx-auto">원데이클래스</span>
      </div>

      {/*OTT공유 */}
      <div className="flex flex-col justify-center items-center" onClick={e => onClick(e, 'OTT공유')}>
        <div className="bg-gray-300 w-10 h-10 rounded-full">
          <img className="" src={process.env.PUBLIC_URL + `/img/ott.png`} alt="OTT공유" />
        </div>
        <span className="font-light text-[10px] mt-1 mx-auto">OTT공유</span>
      </div>

      {/*봉사활동 */}
      <div className="flex flex-col justify-center items-center" onClick={e => onClick(e, '봉사활동')}>
        <div className="bg-gray-300 w-10 h-10 rounded-full">
          <img className="" src={process.env.PUBLIC_URL + `/img/봉사.png`} alt="봉사활동" />
        </div>
        <span className="font-light text-[10px] mt-1 mx-auto">봉사활동</span>
      </div>

      {/*기타*/}
      <div className="flex flex-col justify-center items-center" onClick={e => onClick(e, '기타')}>
        <div className="bg-gray-300 w-10 h-10 rounded-full">
          <img className="" src={process.env.PUBLIC_URL + `/img/기타.png`} alt="기타" />
        </div>
        <span className="font-light text-[10px] mt-1 mx-auto">기타</span>
      </div>
    </div>
  );
};

export default CategoriesBar;
