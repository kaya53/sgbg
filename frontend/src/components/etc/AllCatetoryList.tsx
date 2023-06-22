import React from 'react';
import { useRecoilState } from 'recoil';
import { roomMore } from '../../util/room';
import { inputRoomInfo } from '../../store/room';
import { useNavigate } from 'react-router-dom';
import data from '../../util/category';

const AllCatetoryList = (props: any) => {
  const [room, setRoom] = useRecoilState<roomMore>(inputRoomInfo)
  const navigate = useNavigate();

  const onClickParent = (e: React.MouseEvent, parent:string) => {
    if (props.name !== 'createRoom' ) {
      navigate({
        pathname: 'result',
        search: `parentcategory=${parent}`
      })
    }
  }


  const onClickChild = (e:React.MouseEvent, parent:string, child: string) => {
    if(props.name === "createRoom") {
      const categoryTag = e.target as HTMLElement;
      const child = categoryTag.innerHTML
      // console.log(child);
      
      setRoom({
        ...room,
        parentCategory: parent,
        childCategory: child
      });
      
      navigate(-1)
    } else if (props.name === "search") {
      navigate({
        pathname: 'result',
        search: `childcategory=${child}`,
      })
    }
  };


  return (
    <div>
      {/* 
      아래 p 태그 요소를 클릭할 때마다 recoil에 저장됨(저장되는 것은 하나이기 때문에 새로 클릭하면 값이 갱신됨)
      클릭한 것은 배경이 바뀌던가, 밑줄을 긋던가 하는 방법으로 표시가 됨
    */}
      <div>
      {data.map((datum, index) => (
        // 출력할 요소를 한 div 안에 넣으니까 nested map func가 가능해짐 0928 임지민
        <div>
          {(index%2=== 1) && (
            <h4 className="py-1 pl-2 bg-blue-200 text-white font-semibold" 
              id={datum.parent}
              onClick={e => onClickParent(e, datum.parent)}>{datum.parent}</h4>
          )} 
          {(index%2===0) && (
            <h4 className="bg-yellow-100 py-1 pl-2 font-semibold" 
            id={datum.parent}
            onClick={e => onClickParent(e, datum.parent)}>{datum.parent}</h4>
          )}
          {datum.childs.map((child) => (
            <p className="pl-5 my-1" onClick={e => onClickChild(e, datum.parent, child)}>{child}</p>  
          ))}
        </div>
      ))}
      </div>
    </div>
  );
};

export default AllCatetoryList;