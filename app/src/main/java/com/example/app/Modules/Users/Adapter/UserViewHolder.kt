package com.example.app.Modules.Users.Adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.UserRowBinding
import com.example.app.model.User

class UserViewHolder(private val binding: UserRowBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User?) {
        binding.user = user
        binding.executePendingBindings()
    }
}