import { inputRoomInfo } from "../../store/room";
import { roomMore } from "../../util/room";
import { Link } from "react-router-dom";
import { useRecoilState } from "recoil";
import BtnExitToHome from "../../components/buttons/BtnExitToHome";
import React, { useEffect, useState } from "react";



const CreateRoom = (): JSX.Element => {
  const [room, setRoom] = useRecoilState<roomMore>(inputRoomInfo)
  // 유효성 검사 0927 임지민
  const [isValidated, setIsValidated] = useState(false)

  const onChangeValidation = () => {
    if (room.title.trim() && room.childCategory.trim() && room.location.name.trim() && room.minUser !== 0 && room.maxUser !== 0 && room.description.trim() ){
      setIsValidated((isValidated:boolean) => isValidated = true)
      
    } else {
      setIsValidated((isValidated:boolean) => isValidated = false)
      // console.log('else로 빠짐');
      // console.log(room.title.trim());
    }
  }

  // 상세 정보 작성란에서 기본 정보 작성란으로 돌아왔을 때도 다 채워져있으면 유효성 검사 통과하도록 처리 0930 임지민
  useEffect(() => {
    onChangeValidation();
  })

  // recoil에 작성한 모임 정보 저장하기 0927 임지민
  const onChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    // console.log(name, typeof(value));
    
    setRoom({
      ...room,
      [name]: value
    });
    onChangeValidation();
  };

  const onChangeNumber = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    
    setRoom({
      ...room,
      [name]: Number(value)
    });
    // console.log(name, typeof(value));
    onChangeValidation();
  };

  const onChangeNumberMin = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { value } = e.target;
    
    setRoom({
      ...room,
      minUser: Number(value),
      maxUser: Number(value)
    });
    // console.log(name, typeof(value));
    onChangeValidation();
  };

  const onChangeText = (e: React.ChangeEvent<HTMLTextAreaElement>): void => {
    const { name, value } = e.target;
    console.log(value);
    console.log(value.split('\n'));
    console.log(value.split('\n').join('\n'));
    const newValue = value.split('\n').join('\n')

    setRoom({
      ...room,
      [name]: newValue
    });
    onChangeValidation();
  };
  

  return ( 
    // markup 0915 임지민
    /*
      필수 항목 입력 안하면 못 넘어가도록 유효성 검사하기 0920 임지민
    */
  <div className="mt-8 mx-6">
    <div>
      <BtnExitToHome/>
      <h2 className="inline-block text-xl font-black ml-5">모임 만들기</h2>
    </div>

    <div className="mt-8">
      {/* 기본 정보 작성 */}
      <h3 className="text-lg font-semibold">기본 정보 작성</h3>
      <hr className="my-5" />

      {/* 방 제목 작성 */}
      <label htmlFor="meetingName" className="mr-5">방 제목 </label>
      <input type="text" id="meetingName" onChange={onChange} name="title" value={room.title}/>
      {/* <p>{room.title}</p> */}
      <hr className="my-5" />
      
      {/* 경고 문구 */}
      <p className="text-red-600 text-sm">⚠ 카테고리, 모집인원, 모임위치는(은) 수정이 불가하니 신중하게 작성해주시기 바랍니다. </p>
      <hr className="my-5" />

      {/* 
        카테고리
        - 아래 줄 자체를 클릭하면 카테고리 선택 페이지로 넘어가게
        - 클릭하면 회색 그림자?가 나오도록
      */}
    <Link to="/meeting/create/category">
      <div className="flex justify-between">
          <p className="flex">카테고리</p>
          {/* category 선택 유무에 따라 버튼 or 선택된 카테고리 띄우기 */}
          {room.childCategory==='' && (
            <button className="flex mr-4">&gt;</button>
          )} 
          {room.childCategory && (
            <p className="mr-4 font-semibold">{room.parentCategory} &gt; {room.childCategory}</p>
          )}
      </div>
    </Link>
      {/*  */}
    </div>
    <hr className="my-5" />
    

    <form>
      <div className="grid grid-cols-6">
        <p className="col-span-2">모집인원</p>
        {/* grid grid-cols-3 */}
        <div className="col-span-4 grid grid-cols-5">
          <input type="number" id="minUser" onChange={onChangeNumberMin} name="minUser" value={room.minUser}/>
          <label htmlFor="minUser" className="text-center">명</label>
          <span className="text-center">~</span>
          <input type="number" id="maxUser" onChange={onChangeNumber} name="maxUser" value={room.maxUser}/>
          <label htmlFor="maxUser" className="text-center">명 </label>
        </div>
      </div>
      {/* <p>{room.minUser} ~ {room.maxUser}</p> */}
      <hr className="my-5" />

      <Link to="/meeting/create/location">
        <div className="flex justify-between">
          <p className="flex">모임 위치</p>
          {room.location.name === '' && (
            <button className="flex mr-4">&gt;</button>
          )}
          {room.location.name && (
            <p className="mr-4 font-semibold">{room.location.name}</p>
          )}
        </div>
      </Link>
      <hr className="my-5" />

      <div className="grid">
        <label htmlFor="roomdescription" className="mb-3">모임 설명</label>
        <textarea name="description" id="roomdescription" cols={30} rows={8}
          style={{resize: "none"}}
          placeholder="모임에 대한 설명을 입력하세요"
          onChange={onChangeText}>{room.description}</textarea>
        {/* <p>{room.description}</p> */}
      </div>
      
      {isValidated && (
      <Link to="/meeting/create/more">
        {/* <p>{}</p> */}
        <div className="grid grid-cols-1 mt-3">
          {/* 모든 칸이 추가된 경우 0927 임지민 */}
            <button type="button"
            className="text-center text-white font-semibold bg-blue-200 rounded py-1">다음으로</button>
        </div>
      </Link>)}
      {/* 한 칸이라도 빈 경우 0927 임지민 */}
      {!isValidated && (
        <div className="grid grid-cols-1 mt-3">
          <button type="button"
          className="text-center bg-gray-200 rounded py-1">다음으로</button>
        </div> )}
    </form>

  </div>
  );
};

export default CreateRoom;
