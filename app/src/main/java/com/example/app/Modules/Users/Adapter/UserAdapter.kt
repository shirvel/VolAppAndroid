package com.example.app.Modules.Users.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.UserRowBinding
import com.example.app.model.User

class UserAdapter(private var usersList: MutableList<User>?): RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserRowBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = usersList?.size ?: 0

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = usersList?.get(position)
        holder.bind(user)
    }
}
