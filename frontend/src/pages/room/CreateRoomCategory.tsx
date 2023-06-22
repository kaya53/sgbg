import { Link } from "react-router-dom";
import AllCatetoryList from "../../components/etc/AllCatetoryList";

const CreateRoomCategory = () => {
  return (
  <div className="mt-8">
    <div className="mx-3 grid grid-cols-6">
      <Link to="/meeting/create/">
        <p>&lt;</p>
      </Link>
      <h2 className="text-lg font-bold text-center mb-5 col-span-4">카테고리</h2>
    </div>
    <AllCatetoryList name="createRoom" />

  </div>);
};

export default CreateRoomCategory;
