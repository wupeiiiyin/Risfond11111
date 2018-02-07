package com.risfond.rnss.login.modleImpl;


import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.LoginResponse;
import com.risfond.rnss.login.modleInterface.ILogin;
import com.risfond.rnss.login.modleInterface.IVerificationCode;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/12.
 * 登录接口实现
 */

public class VerificationCodeImpl implements IVerificationCode {
    private LoginResponse response;

    @Override
    public void VerificationCodeRequest(Map<String, String> request, String token, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(URLConstant.URL_SEND_BY_BLEND, request, token, new ResponseListener() {
            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, LoginResponse.class);
                    if (response != null) {
                        if (response.getStatus()) {//请求成功
                            callBack.onSuccess(response.getStatus());
                        } else {
                            callBack.onFailed(response.getMessage());
                        }
                    } else {
                        callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                    }
                } else {
                    callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
                }

            }

            @Override
            public void onFailed(Throwable ex) {
                callBack.onError(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }

}
