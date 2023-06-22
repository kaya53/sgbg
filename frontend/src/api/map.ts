import { mapApi } from "./API"

export const searchMap = (keyword: string, page:number) => {
    let url = `/keyword.json?query=${keyword}&size=5&page=${page}`;
    return mapApi.get(url);
  };