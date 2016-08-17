package com.lq.xingyun.inter;


public interface Callback<T> {
    void onSuccess(T data);

    void onFailed();
}
