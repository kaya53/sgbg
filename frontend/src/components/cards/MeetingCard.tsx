import { faMapMarkerAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { formatDate } from "../../util/room";


const MeetingCard = (props:any): JSX.Element => {
  const [meetingText, setMeetingText] = useState('모집 중')
  const [bgColor, setBgColor] = useState('')
  const [divBgColor, setDivBgcolor] = useState('')
  const navigate = useNavigate()
  // 인원이 다 찼을 때, 모집 마감일이 지났을 때 true이도록 
  const [ isDone, setIsDone ] = useState(false)

  const getMemberBadgeColor = () => {
    return 'bg-gray-100';
  }

  const getIsImminent = () =>{
    
    const today = new Date().getTime()
    const endDate = new Date(props.room.endDate).getTime()
    // console.log('into isimminent= ', endDate-today);
    
    // 모집 마감일과 오늘 날짜의 차이가 24시간 이내일때
    if ((endDate - today) <= 86400000 && (endDate - today) > 0) {
      setMeetingText('마감 임박')
      setBgColor('bg-red-100 text-white')
      setDivBgcolor('')
    } else if ((endDate - today) < 0) {
      setIsDone(true)
      setMeetingText('모집 마감')
      setBgColor('bg-gray-400')
      setDivBgcolor('bg-gray-300 opacity-50')
    } else{
      setMeetingText('모집 중')
      setBgColor('bg-yellow-100')
      setDivBgcolor('')
    }
    // 아니면 모집 중    
  }

  // onClick하면 navigate
  // isDone=== true이면 클릭 안되게 
  // prop 이름이 createRoom이면 클릭 안되게 
  const onClick = () => {
    if (props.name !== 'createRoom' && props.name !== 'readRoom') {
      navigate(`/meeting/${props.room.roomId}`)
    } else {
      console.log('unclickable');
    }
  } 

  useEffect(()=>{
    console.log('hi meetingcard');
    getIsImminent();
  }, [props.room.endDate])


  return (
    // w-per100 h-per100 
    <div className={`flex flex-row border rounded-lg p-2 mb-2 ${divBgColor}`} onClick={onClick}>
      {/* 모임 이미지, 뱃지 */}
      <div className="w-per45 mr-5 relative ">
        <div className={`absolute top-0 left-0 rounded ${bgColor} text-sm font-semibold px-2 py-1`}>{meetingText}</div>
        <img
          className="w-full h-full rounded"
          src={process.env.PUBLIC_URL + `/img/${props.room.parentCategory}_${props.room.childCategory.replaceAll('/', '')}.jpg`}
          alt="소분류 카테고리 사진"></img>

        <div className={`absolute bottom-0 right-0 rounded ${getMemberBadgeColor()} text-sm font-semibold px-2`}>
          {props.room.members? props.room.members.length : ''} / {props.room.maxUser? props.room.maxUser : ''}
        </div>
      </div>
      {/* 모임 설명 */}
      <div className="w-per55 flex flex-col justify-between"> {/* 설명 부분 전체를 묶는 div */}

        {/* 카테고리, 모임명, 위치 */}
        {/* 설명 1,2번째 줄을 묶는 div */}
        <div>
          <div className="flex justify-between">
            <span 
              className="text-xs text-blue-200 font-bold border border-blue-200 border-1 py-0.5 px-1.5 rounded-xl">
              {props.room.childCategory? props.room.childCategory : ''}
            </span>
            <span className="text-xs pt-1">
              <FontAwesomeIcon icon={faMapMarkerAlt} className="text-xs mr-1" />        
                {props.room.location.roadAddress? props.room.location.roadAddress : ''}
            </span>
          </div>
          <div className="text-sm font-semibold py-2">
            <span>
              {props.room.title? props.room.title : ''}
            </span>
          </div>
        </div>
        {/* 모임일자, 가격 */}
        <div className="flex justify-between">
          <div >
            <p className="text-sm">{props.room.reservationDate? formatDate(props.room.reservationDate).split('  ')[0] : '만나는 날'}</p>
            <p className="text-sm">{props.room.reservationDate? formatDate(props.room.reservationDate).split('  ')[1] : ''}</p>
          </div>

          <span className="text-sm font-semibold">{props.room.price? props.room.price : '0'}SB</span>
        </div>
      </div>
    </div>
  );
};

export default MeetingCard;
