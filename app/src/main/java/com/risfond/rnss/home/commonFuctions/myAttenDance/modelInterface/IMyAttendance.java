package com.risfond.rnss.home.commonFuctions.myAttenDance.modelInterface;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/7/26.
 * 推荐管理接口
 */

public interface IMyAttendance {
    /**
     * 客户搜索请求
     *
     * @param callBack
     */
    void positionSearchRequest(String token, Map<String, String> request, String url, ResponseCallBack callBack);
}
