import { api } from "./API";


// search result page GET 0929 임지민
// key가 parentCategory, childCategory
export const getSearchCategoryResult = (key:string, value: string) => {
  const url = `/room/${key}/${value}`;
  return api.get(url);
};

export const getSearchKeywordResult = (value: string) => {
  const url = `/room/search?keyword=${value}`
  return api.get(url)
}