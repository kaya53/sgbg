import { api } from "./API";

// type params = {
//   page? : number;
//   size? : number;
//   sort? : string;
// }

// 메인 페이지에서 모임 전체 
export const getRoomList = (sort: string) => {
  const url = `/room?sort=${sort}`;
  return api.get(url);
};
