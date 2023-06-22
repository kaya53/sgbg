package com.sgbg.service.interfaces;

import java.util.Map;

public interface IKakaoService {

    public Map<String, String> getKakaoTokenInfo(String code);

    public Map<String, String> getKakaoUserInfo(String access_token);

    public void logout(String access_token);

}
