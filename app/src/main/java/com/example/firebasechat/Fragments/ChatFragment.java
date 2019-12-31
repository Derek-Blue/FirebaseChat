package com.example.firebasechat.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasechat.Adapter.UserAdapter;
import com.example.firebasechat.Model.Chat;
import com.example.firebasechat.Model.Chatlist;
import com.example.firebasechat.Model.User;
import com.example.firebasechat.Notifications.Token;
import com.example.firebasechat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    //聊天列表
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUser;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List<Chatlist> list_sender;

    private List<String> userlist, getUserlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recycleview_chats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //list_sender = new ArrayList<>();

//        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list_sender.clear();
//                //先取得有過聊天記錄的ID
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
//                    list_sender.add(chatlist);
//                }
//
//                chatListDisplay();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        userlist = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                        //交叉比對 將有共同聊天記錄的ID集合到一陣列
                        if (chat.getReceiver().equals(firebaseUser.getUid())) {
                            userlist.add(chat.getSender());
                        }

                        if (chat.getSender().equals(firebaseUser.getUid())) {
                            userlist.add(chat.getReceiver());
                        }

                }

                //取另一陣列排除重複的ID  避免相同item(聊天室連結)產生
                getUserlist = new ArrayList<>();
                int count = userlist.size();
                for (int i = 0; i < count; i++) {
                    if (!getUserlist.contains(userlist.get(i))) {
                        getUserlist.add(userlist.get(i));
                        //Log.d("G200=",getUserlist.get(i));
                    }
                }
                chatlist();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void chatlist() {
        //與對像使用者有過聊天紀錄後 顯示與該使用者的聊天室連結
        mUser = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (String id : getUserlist){
                        if (user.getId().equals(id)){
                            mUser.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(),mUser,true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }


    //與對像使用者有過聊天紀錄後 顯示與該使用者的聊天室連結
//    private void chatListDisplay() {
//        mUser = new ArrayList<>();
//
//        reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mUser.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    User user = snapshot.getValue(User.class);
//                    for (Chatlist chatlist : list_sender){
//                        if (user.getId().equals(chatlist.getChatwith())){
//                            mUser.add(user);
//                        }
//                    }
//                }
//                userAdapter = new UserAdapter(getContext(), mUser, true);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


}
