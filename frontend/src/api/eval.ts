import { api } from "./API";

type Eval = {
  kakaoId: number;
  review: "BEST" | "GOOD" | "BAD";
};

export const postEvalHost = (roomId: string, value: boolean) => {
  const url = `/eval/host/${roomId}`;
  return api.post(url, { isSuccess: value });
};

export const postEvalMember = (roomId: string, values: Eval[]) => {
  const url = `/eval/member/${roomId}`;
  return api.post(url, { evaluations: values });
};
