import SearchBar from "../../components/bars/SearchBar";
import AllCatetoryList from "../../components/etc/AllCatetoryList";



const Search = () => {
  

  return (<div className="mt-5">
    <SearchBar name="search" handleKeyword={()=>{}}/>
    <div className="mx-2 mt-5">
      <AllCatetoryList name="search"/>
    </div>
  </div>);
};

export default Search;