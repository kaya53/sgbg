import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCommentMedical } from '@fortawesome/free-solid-svg-icons'
import { Link, useNavigate, useParams } from "react-router-dom";
import { getParticipantBadge } from "../../util/profile";
import { createComment, readComment, deleteComment, updateComment } from "../../api/community";
import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { auth } from "../../store/auth";
import Swal from "sweetalert2";
import { read } from "fs";


const CommunityTab = (props: any) => {

  const navigate = useNavigate()
  let {meeting_id} = useParams()

  const [commentList, setCommentList] = useState([{
    content: '',
    commentId: 0,
    createdAt: '',
    hostScore: 0,
    kakaoNumber: 0,
    username: '',
    userScore: 0,
    }])

  const [ comment, setComment ] = useState('')
  const [userAuth] = useRecoilState(auth);
  const [ isEmpty, setIsEmpty] = useState(false)


  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const {value} = e.target
    setComment(value)
  };

  
  const onClickSubmit = (e: React.MouseEvent) => {
    const textArea:any = document.querySelector('#community')
    // 버튼을 누르면 usestate content에 작성한 내용이 저장됨
    // const value = e.target
    // console.log(comment); // ok
    
    // 버튼을 누르면 axios 요청 with content and room_id
    // console.log(meeting_id, comment); ok
    createComment({
      content: comment,
      roomId: Number(meeting_id)
    }).then((res)=>{
      console.log('create comment res=', res);
      textArea.value = ''
      // 다 되면 현재 페이지 리다이렉트
      readCommentList();
    })    
  }

  // 삭제하기
  const onClickDelete = (e:React.MouseEvent<HTMLButtonElement>, commentId: number):any => {
    console.log('hello delete');
    
    deleteComment(commentId)
    .then(({data})=> {
      console.log(data);
      // 다 되면 현재 페이지 리다이렉트
      readCommentList();
    })
  };


  const readCommentList = () => {
    readComment(Number(meeting_id))
    .then(({data})=> {
      console.log('read comment res.data= ', data);
      data.length!== 0? setIsEmpty(false) : setIsEmpty(true)
      // 저장해주고
      setCommentList(data)
      // navigate(0)
    })
    console.log('read comment length=', commentList.length);
  }

  // 컴포넌트 create될 때 axios
  useEffect(()=>{
    // 만약 로그인이 안되어 있거나, 이 모임의 참여자가 아니면 리다이렉트 시키기
    console.log('useeffect userauth=', userAuth);
    if (!userAuth.isLogined ){
      Swal.fire({
        title: '로그인 후 이용해주세요.',
        icon: 'error',
        timer: 5000,
      }).then(()=>{
        navigate('/login');
      }) 
      // || (!props.isInThisRoom) || (!props.isHost) 
    } else {
      if (props.isHost || props.isInThisRoom){
        console.log('host다=', props.isHost);
        console.log('이 방에 있다=', props.isInThisRoom);
        
        readCommentList();
      } else {
        Swal.fire({
          text: '모임에 참가한 사용자만 볼 수 있습니다.',
          icon: 'error',
          timer: 5000,
        }).then(()=>{
          navigate(0);
        }) 
      }
    }
    
    // 전체 커뮤니티 글 읽어오기
  }, [])

  // const onClickUpdate = (commentId: number): any => {

  // }

  return (
    <div>
        <div>
          <div className="grid grid-cols-12">
            <textarea 
                name="community" 
                id="community" 
                onChange={onChange}
                style={{resize: "none", border: "5px solid #5280C0"}}
                className="rounded-l col-start-1 col-end-12"
                placeholder="글을 작성해주세요"
                cols={40} 
                rows={3}></textarea> 
            <button type="submit" onClick={onClickSubmit}
              className="bg-blue-200 rounded-r text-white text-lg border-none">
              <FontAwesomeIcon icon={faCommentMedical}/>
            </button>
          </div>
          {/* 작성된 게시글 */}
        {!isEmpty && (
          <div className="h-[75vh]">
            {commentList.map(comment=>
              <div className="my-5 border rounded p-2">
                <div className="flex justify-between">
                  <div>
                    <Link to={`/profile/${comment.username}`}>
                      <div className="flex flex-row justify-start border-b border-gray-300 pb-1">
                        <div className="w-[25px] h-[25px] mr-2">
                          <img
                            className="w-full h-full"
                            src={
                              process.env.PUBLIC_URL + `/img/userBadge` + getParticipantBadge(comment.userScore) + ".png"
                            }
                            alt="사용자 뱃지"
                          />
                        </div>
                        <span className="font-semibold leading-tightl">{comment.username}</span>
                      </div>
                    </Link>
                  </div>
                  {/* 현재 유저와 작성한 유저가 같으면 삭제되도록 */}
                  <div>
                    {Number(userAuth.userId) === comment.kakaoNumber && (
                      <div>
                        {/* <button onClick={onClickUpdate(comment.commentId)}>수정하기</button> */}
                        <button onClick={e=> onClickDelete(e, comment.commentId)}
                          className="bg-yellow-100 font-semibold text-sm px-1 rounded"
                        >삭제하기</button>
                      </div>
                    )}
                  </div>
                </div>

                <div className="rounded p-2">
                  <p>{comment.content}</p>
                </div>
                <div className="font-semibold text-xs text-right mt-2 mr-2">
                  <p>{comment.createdAt}</p>
                </div>
              </div>
            )}
          </div>
        )}
        </div>
      {!commentList.length && (
        <p className="mt-5 text-lg text-center font-semibold">아직 작성된 글이 없습니다!</p>
      )}
    </div>
  );
};

export default CommunityTab;