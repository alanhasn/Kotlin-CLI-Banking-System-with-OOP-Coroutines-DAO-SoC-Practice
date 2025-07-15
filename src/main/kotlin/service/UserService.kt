package service

import data.dao.InMemoryUserDao
import data.model.User

object UserService {
    private val savedUsers = InMemoryUserDao()
    private var currentUser: User? = null

    fun register(username: String , password: String): Result<User>{
        return try {
            val user = User(0,username , password)
            val registeredUser = savedUsers.register(user)
            if (registeredUser != null){
                currentUser = registeredUser
                Result.success(registeredUser)
            } else {
                Result.failure(Exception("The user with name $username already exists."))
            }
        } catch (e: Exception){
            Result.failure(Exception("An unexpected error occurred: ${e.message}"))
        }
    }

    fun login(username: String , password: String): Result<User>{
        return try {
            val loggedUser = savedUsers.login(username , password)
            if (loggedUser != null){
                currentUser = loggedUser
                Result.success(loggedUser)
            } else {
                Result.failure(Exception("Invalid username or password."))
            }
        } catch (e: Exception){
            Result.failure(Exception("An unexpected error occurred: ${e.message}"))
        }
    }

    fun logout(){
        currentUser = null
        println("Logged out successfully.")
    }

    fun getCurrentUser(): User? = currentUser

    fun getUserById(id: Int){
        try {
            val user=savedUsers.getUserById(id)
            if (user != null){
                println("User ID:${user.id}\nUsername:${user.name}")
            }else println("No user with this id found")

        }catch (e: Exception){
            println("Unexpected Error occur $e")
        }
    }
    fun showUsers(){
        val s = InMemoryUserDao()
        s.users.forEach { println(it.name) }
    }

}