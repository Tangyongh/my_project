package com.dingdingyijian.ddyj.wxapi;


import android.content.Intent;

import com.umeng.socialize.weixin.view.WXCallbackActivity;

@SuppressWarnings("all")
public class WXEntryActivity extends WXCallbackActivity  {

    @Override
    protected void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
        setIntent(paramIntent);
    }
}