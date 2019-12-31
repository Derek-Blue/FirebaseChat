package com.example.firebasechat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasechat.MessageActivity;
import com.example.firebasechat.Model.Chat;
import com.example.firebasechat.Model.User;
import com.example.firebasechat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> users;

    private boolean ischat;

    String theLastMesagge;

    public UserAdapter(Context context, List<User> users ,boolean ischat) {
        this.context = context;
        this.users = users;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = users.get(position);
        holder.name.setText(user.getUsername());
        if (user.getImageURL().equals("default")){
            holder.user_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(user.getImageURL()).into(holder.user_image);
        }

        //顯示最後一則留言
        if (ischat){
            setLastMesagge(user.getId(), holder.last_mag);
        }else {
            holder.last_mag.setVisibility(View.GONE);
        }

         //上線/離線 監聽開關  此專案區分好友List(關)聊天List(開)
        if(ischat){
            if (user.getStatus().equals("online")){
                holder.status_on.setVisibility(View.VISIBLE);
                holder.status_off.setVisibility(View.GONE);
            }else {
                holder.status_on.setVisibility(View.GONE);
                holder.status_off.setVisibility(View.VISIBLE);
            }
        }else {
            holder.status_on.setVisibility(View.GONE);
            holder.status_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , MessageActivity.class);
                intent.putExtra("userID",user.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView user_image;
        private ImageView status_on;
        private ImageView status_off;
        private TextView last_mag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            user_image = itemView.findViewById(R.id.user_image);
            status_on = itemView.findViewById(R.id.status_on);
            status_off = itemView.findViewById(R.id.status_off);
            last_mag = itemView.findViewById(R.id.last_msg);

        }
    }

    private void setLastMesagge( final String userid , final TextView last_msg){
        //顯示最後一則留言
        theLastMesagge = "default";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //在尚無context的class下先必須告知 如果有FirebaseAuth 才做，否則登出時會報錯 on null object
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chat chat = snapshot.getValue(Chat.class);
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)) {
                            theLastMesagge =chat.getMessage();
                        }else if (chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())){
                            theLastMesagge ="已回覆: "+chat.getMessage();
                        }
                    }
                }

                switch (theLastMesagge){
                    case "default":
                        last_msg.setText("No Messager");
                        break;
                    default:
                        last_msg.setText(theLastMesagge);
                        break;
                }

                theLastMesagge = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
