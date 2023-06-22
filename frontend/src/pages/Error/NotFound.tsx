import { Link } from "react-router-dom";
const NotFound = () => {
  return (
    <div className="w-full h-full flex flex-col justify-center items-center font-bold text-3xl mt-10">
      <span>찾을 수 없는 페이지입니다.</span>
      <Link to="/" className=" bg-blue-300 text-white rounded-md m-4 p-4">
        메인으로 돌아가기
      </Link>
    </div>
  );
};

export default NotFound;
