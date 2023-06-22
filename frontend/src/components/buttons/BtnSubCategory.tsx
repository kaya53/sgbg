import React from "react";
import { useNavigate } from "react-router-dom";


const BtnSubCategory = (props: any) => {
  
  const navigate = useNavigate()
  const handleSearch = (e:React.MouseEvent<HTMLButtonElement>, category: string) =>{
      navigate(`/search/result?childcategory=${category}`)
  }

  return (
    <button onClick={e=> handleSearch(e, props.childCategory)}
    className="border border-gray-300 rounded-full font-light text-sm py-1 px-2 ml-2">
      {props.childCategory}
    </button>
  );
};

export default BtnSubCategory;
