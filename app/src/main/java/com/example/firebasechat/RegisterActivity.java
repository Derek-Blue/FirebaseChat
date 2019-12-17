package com.example.firebasechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password;
    Button btn_register;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar actbar = findViewById(R.id.actbar);
        setSupportActionBar(actbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyin_username = username.getText().toString();
                String keyin_email = email.getText().toString();
                String keyin_password = password.getText().toString();

                if(TextUtils.isEmpty(keyin_username) || TextUtils.isEmpty(keyin_email) || TextUtils.isEmpty(keyin_password) ){
                    Toast.makeText(RegisterActivity.this, "輸入錯誤/不可空白",Toast.LENGTH_SHORT).show();
                }else if (keyin_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "密碼必須6字元以上",Toast.LENGTH_SHORT).show();
                }else {
                    register(keyin_username,keyin_email,keyin_password);
                }

            }
        });
    }

    private void register(final String username, String email, String password){

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userID = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                            HashMap<String , String> hashMap = new HashMap<>();
                            hashMap.put("id", userID);
                            hashMap.put("username" , username);
                            hashMap.put("imageURL" , "default");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    });

                        }else {
                            Toast.makeText(RegisterActivity.this, "錯誤的信箱 or 密碼",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
