import { atom } from "recoil";

const sessionStorageEffect =
  (key: string) =>
  ({ setSelf, onSet }: any) => {
    const savedValue = sessionStorage.getItem(key);

    if (savedValue !== null) {
      setSelf(JSON.parse(savedValue));
    }
    onSet((newValue: any, _: any, isReset: any) => {
      const confirm = newValue.length === 0;
      confirm
        ? sessionStorage.removeItem(key)
        : sessionStorage.setItem(key, JSON.stringify(newValue));
    });
  };

type Auth = {
  isLogined: boolean;
  userId: string;
};

export const auth = atom<Auth>({
  key: "auth",
  default: {
    isLogined: false,
    userId: "",
  },
  effects: [sessionStorageEffect("logined")],
});
