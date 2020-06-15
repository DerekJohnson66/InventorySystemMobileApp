package com.example.inventorysystem.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorysystem.R;
import com.example.inventorysystem.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserHolder> {

        private List<User> users = new ArrayList<>();
        private OnItemClickListener listener;

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View userView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_item, parent, false);
            return new UserHolder(userView);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            User currentUser = users.get(position);
            holder.textViewName.setText(currentUser.getUserName());

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public void setUsers(List<User> users){
            this.users = users;
            notifyDataSetChanged();
        }

        public User getUserAt(int position){
            return users.get(position);
        }

        class UserHolder extends RecyclerView.ViewHolder{
            private TextView textViewName;


            public UserHolder(@NonNull View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.text_view_name);


                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION){
                            listener.onItemClick(users.get(position));
                        }

                    }

                });
            }
        }

        public interface OnItemClickListener {
            void onItemClick(User user);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }

