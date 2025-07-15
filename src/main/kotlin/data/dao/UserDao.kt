package data.dao

import data.model.User

// Interface for User Data access object layer
interface UserDao{
    fun register(user: User): User?
    fun login(username: String , password: String ): User ?
    fun getUserById(id: Int): User?
}