package com.risfond.rnss.update;

import com.risfond.rnss.callback.ResponseCallBack;

import java.util.Map;

/**
 * Created by Abbott on 2017/4/20.
 * app更新检查接口
 */

public interface IAppUpdate {
    void update(Map<String, String> request, String token, ResponseCallBack callBack);
}
