package com.risfond.rnss.home.commonFuctions.dynamics.modelimpl;

import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.ResponseListener;
import com.risfond.rnss.entry.ClientApplicationResponse;
import com.risfond.rnss.entry.DynamicsResponse;
import com.risfond.rnss.home.commonFuctions.dynamics.modelinterface.IDynamics;
import com.risfond.rnss.home.commonFuctions.publicCustomer.modelInterface.IClientApplication;

import java.util.Map;

/**
 * Created by chh on 2017/7/26.
 */

public class DynamicsImpl implements IDynamics {
    DynamicsResponse response;

    @Override
    public void positionSearchRequest(String token, Map<String, String> request, String url, final ResponseCallBack callBack) {

        HttpUtil.getInstance().requestService(url, request, token, new ResponseListener() {

            @Override
            public void onSuccess(String str) {
                if (JsonUtil.isJson(str)) {
                    response = JsonUtil.fromJson(str, DynamicsResponse.class);
                    if (response != null) {
                        if (response.isSuccess()) {//请求成功
                            callBack.onSuccess(response);
                        } else {
                            callBack.onFailed(response.getMessage()+"");
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
                callBack.onFailed(PropertiesUtil.getMessageTextByCode("Error"));
            }
        });
    }
}
