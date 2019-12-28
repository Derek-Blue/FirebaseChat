package com.example.firebasechat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //登入頁
    EditText email,password;
    Button btn_login;

    FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //獲取狀態欄改變背景顏色 API Level21(5.0)以下可能會報錯
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorBlue500));

        //ToolBar設定
        Toolbar actbar = findViewById(R.id.actbar);
        setSupportActionBar(actbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Toolbar設定返回鍵
        actbar.setTitleTextColor(Color.WHITE);
        actbar.setBackgroundColor(getResources().getColor(R.color.colorBlue400));

        firebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyin_email = email.getText().toString();
                String keyin_password = password.getText().toString();

                if(TextUtils.isEmpty(keyin_email) || TextUtils.isEmpty(keyin_password)){
                    Toast.makeText(LoginActivity.this,"輸入錯誤/不可為空",Toast.LENGTH_SHORT).show();
                }else {

                    firebaseAuth.signInWithEmailAndPassword(keyin_email,keyin_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        //釋放掉目前所有Activity，僅保留此次intent的目標(MainActivity)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(LoginActivity.this,"登入失敗",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}
