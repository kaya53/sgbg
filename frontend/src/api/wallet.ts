import { api } from "./API";

export const makeWallet = (pw: string) => {
  const url = `/wallet/create`;
  return api.post(url, { password: pw });
};

export const postWallet = (pw: string) => {
  const url = `/wallet`;
  return api.post(url, { password: pw });
};

export const checkWallet = () => {
  const url = `/wallet`;
  return api.get(url);
};

export const getWalletHistory = () => {
  const url = `/wallet/history`;
  return api.get(url);
};

export const chargeWallet = (_money:number) => {
  const url = `/wallet/charge`;
  return api.post(url, { money: _money });
};
