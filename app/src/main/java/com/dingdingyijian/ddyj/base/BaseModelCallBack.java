package com.dingdingyijian.ddyj.base;


public interface BaseModelCallBack<T> {

    void onNext(T t);

    void onError(String errorMsg);


}
