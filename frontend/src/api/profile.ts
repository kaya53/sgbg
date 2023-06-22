import { api } from "./API";


export const getMypage = (user_id: string) => {
    const url = `/user/${user_id}`;
    return api.get(url);
};

export const getMyPageParticipantList = () => {
    return api.get("/user/room?host=false");
}

export const getMyPageHostList = () => {
    return api.get("/user/room?host=true");
}

export const getFinishedParticipantList = (kakaoId:string) => {
    return api.get(`/user/${kakaoId}/room?host=false`);
}
 
export const getFinishedHostList = (kakaoId:string) => {
    return api.get(`/user/${kakaoId}/room?host=true`);
}

export const withdrawWallet = (roomId: number) => {
    return api.get(`/user/room/${roomId}/withdraw`)
  }