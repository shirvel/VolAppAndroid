package com.example.app.model

class UserListModel private constructor() {
    val users: MutableList<User> = ArrayList()

    companion object {
        val instance: UserListModel = UserListModel()
    }

    init {
        // Populate the list with sample users
        for (i in 1..20) {
            val user = User(
                email = "user$i@example.com",
                password = "password$i",
                name = "$i"
            )
            users.add(user)
        }
    }
}
