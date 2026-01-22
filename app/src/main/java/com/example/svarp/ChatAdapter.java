package com.example.svarp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<ChatMessage> messageList;

    public ChatAdapter(List<ChatMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage chatMessage = messageList.get(position);
        holder.tvMessage.setText(chatMessage.getMessage());

        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) holder.tvMessage.getLayoutParams();

        // IMPORTANT: reset constraints for RecyclerView reuse
        params.startToStart = ConstraintLayout.LayoutParams.UNSET;
        params.endToEnd = ConstraintLayout.LayoutParams.UNSET;

        if (chatMessage.isUser()) {
            // USER → RIGHT
            holder.tvMessage.setBackgroundResource(R.drawable.bubble_user);
            holder.tvMessage.setTextColor(Color.WHITE);
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        } else {
            // AI → LEFT
            holder.tvMessage.setBackgroundResource(R.drawable.bubble_ai);
            holder.tvMessage.setTextColor(Color.BLACK);
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        }

        holder.tvMessage.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // MUST be public because it is returned by a public method
    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
