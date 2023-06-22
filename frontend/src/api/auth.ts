import { api } from "./API";

//로그인 Post
export const login = (_params: string) => {
  const url = `auth/login?code=${_params}`;
  return api.get(url);
};

export const logout = () => {
  const url = `auth/logout`;
  return api.get(url);
};
