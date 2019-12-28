package com.example.firebasechat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText send_email;
    Button btn_reset;

    FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //獲取狀態欄改變背景顏色 API Level21(5.0)以下可能會報錯
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorBlue500));

        //ToolBar設定
        Toolbar actbar = findViewById(R.id.actbar);
        setSupportActionBar(actbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Toolbar設定返回鍵
        actbar.setTitleTextColor(Color.WHITE);
        actbar.setBackgroundColor(getResources().getColor(R.color.colorBlue400));

        send_email = findViewById(R.id.send_email);
        btn_reset = findViewById(R.id.btn_reset);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = send_email.getText().toString();

                if (email.equals("")){
                    Toast.makeText(ResetPasswordActivity.this, "請輸入正確信箱地址",Toast.LENGTH_SHORT).show();
                }else  {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, "請檢查您的電子郵件",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPasswordActivity.this, error , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
