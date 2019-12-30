package com.example.firebasechat.Notifications;

import com.example.firebasechat.Notifications.MyRespones;
import com.example.firebasechat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAdanzI10:APA91bFoQ_R-_P4BQwKvWrm7C3zLXce3A6n7Iffa9maIKT1WxPKIEeTsbJK-ZFx0Eoew_e0JHsDlH08eCYC51JMPDGwu9UqOkdZgbh3FBaZktfDh5qQFuaZGo1Q1kteZW0_PsId8XdtV"
            }
    )

    @POST("fcm/send")
    Call<MyRespones> sendNotification(@Body Sender body);
}
