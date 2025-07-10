package service

import data.dao.InMemoryUserDao
import data.model.User

object UserService {
    private val savedUsers = InMemoryUserDao()

    fun register(username: String , password: String){
        try {
            val user = User(0,username , password)
            val registeredUser = savedUsers.register(user)
            if (registeredUser){
                println("The User registered Successfully.")
            }
            else{
                println("the user with name $username is already existed. ")
            }

        }
        catch (e: Exception){
            println("Unexpected Error occur $e")
        }
    }

    fun login(username: String , password: String){
        try {
            val loggedUser = savedUsers.login(username , password)

            if (loggedUser != null){
                println("Hello $username welcome back")
            }
            else{
                println("Invalid Username and password")
            }

        }catch (e: Exception){
            println("Unexpected error occur $e")
        }
    }
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