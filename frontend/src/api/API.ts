import axios from "axios";
import { defaultURL, mapURL } from "./config";

export const api = axios.create({
  baseURL: defaultURL,
});

export const mapApi = axios.create({
  baseURL: mapURL,
  headers: { Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO_API_KEY}` },
});
