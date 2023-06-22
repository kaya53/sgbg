import { useEffect, useState } from "react";
// import FilterBar from "../../components/bars/FilterBar";
import SearchBar from "../../components/bars/SearchBar";
import SubCategoriesBar from "../../components/bars/SubCategoriesBar";
import MeetingCard from "../../components/cards/MeetingCard";
import { getSearchCategoryResult } from "../../api/search";
import data from "../../util/category";
import { getSearchKeywordResult } from "../../api/search";
import { roomMore } from "../../util/room";
import { useLocation } from 'react-router-dom'


const SearchResult = () => {
  const [key, setKey] = useState('')
  const [keyword, setKeyword] = useState<string>("");
  const [value, setValue] = useState('')
  const [isEmpty, setIsEmpty] = useState(false)
  const [childCategories, setChildCategories] = useState(Array<string>)
  const [results, setResults] = useState([])
  const location = useLocation()

  useEffect(() => {    
    console.log('changed');
    
    const params = window.location.href.split('?')[1].split('=')
    // console.log(params);
    setKey(params[0])
    // console.log(decodeURI(params[1]),'----', key);

    // axios 요청
    // decoding을 해주지 않으면 인코딩된 형태로 나옴
    const value = decodeURI(params[1])
    // 디코딩한 것을 저장
    setValue(value)

    // axios
    if (params[0] === 'parentcategory' || params[0] === 'childcategory') {
      getSearchCategoryResult(params[0], value).then((res)=>{
        // console.log(res);
        console.log('category search= ', res.data.roomListInfo);
        res.data.roomListInfo.length? setIsEmpty(true): setIsEmpty(false)
        setResults(res.data.roomListInfo)
      }).catch((err) => {
        console.log(err);
        // console.log(value); 
      })
    } else if (params[0] === 'keyword') {
      console.log('searchresult axios else if=', value);
      
      getSearchKeywordResult(value).then((res) => {
        console.log(res.data.roomListInfo);
        res.data.roomListInfo.length? setIsEmpty(true): setIsEmpty(false)
        setResults((res.data.roomListInfo))
      }).catch((err)=>{
        console.log(err);
        
      })
    }

    if (params[0] === 'parentcategory') {
      
      data.forEach((datum) => {
        if(value === datum.parent) {
          setChildCategories(datum.childs)
          // console.log('in if=', value, datum.childs);
        }
      })
    }
  }, [location])


  useEffect(() => {
    // console.log('search result = ', keyword);
    
    // handleSearch(keyword);
  }, [])
  // React Hook useEffect has missing dependencies: 'key' and 'results'. Either include them or remove the dependency array. You can also do a functional update 'setResults(r => ...)' if you only need 'results' in the 'setResults' call  react-hooks/exhaustive-deps
  // 위에 같은 에러가 떠서 key,results를 넣었더니 무한으로 업데이트가 되어서 지워줬더니 해결됨

  
  return (      
    <div>
      <SearchBar name={"searchResult"} handleKeyword={setKeyword}/>
      <p className="ml-3 my-2 text-lg"><strong className="mr-1">{value}</strong>에 대한 검색결과</p>
      {/* 세부 카테고리 바 */} 
      {key==='parentcategory'? <SubCategoriesBar childCategories={childCategories} />  : ''}

      {/* 검색 결과 리스트 */}
      <div className="w-per95 max-h-[80vh] m-auto grid grid-cols-1 gap-1 overflow-y-auto">
        {!isEmpty && (
          <p className="mt-10 text-lg text-center font-semibold">검색 결과가 없습니다.</p>
        )}
        {isEmpty && (
          <div>
            {/* <p>{results.length}</p> */}
            {results.map((room:roomMore) => (
              <MeetingCard key={room.roomId} room={room}/>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default SearchResult;
