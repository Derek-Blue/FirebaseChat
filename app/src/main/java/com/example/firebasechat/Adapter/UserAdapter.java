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
import com.example.firebasechat.Model.User;
import com.example.firebasechat.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> users;

    private boolean ischat;

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
            holder.user_image.setImageResource(R.mipmap.frog);
        }else {
            Glide.with(context).load(user.getImageURL()).into(holder.user_image);
        }

         //判斷上線/離線
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            user_image = itemView.findViewById(R.id.user_image);
            status_on = itemView.findViewById(R.id.status_on);
            status_off = itemView.findViewById(R.id.status_off);
        }
    }

}
