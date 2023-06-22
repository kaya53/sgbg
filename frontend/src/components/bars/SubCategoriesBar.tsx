import BtnSubCategory from "../buttons/BtnSubCategory";

const SubCategoriesBar = (props: any) => {
  
  return (
    <div className="w-full my-3">
      {props.childCategories.map((childCategory: string) => (
        <BtnSubCategory childCategory={childCategory} />
      ))}
    </div>
  );
};

export default SubCategoriesBar;
