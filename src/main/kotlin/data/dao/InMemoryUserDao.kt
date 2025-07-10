package data.dao

import data.model.User

class InMemoryUserDao: UserDao {
    // users list for store all User objects
    val users = mutableListOf<User>()
    private var nextId=1 // for create new copy of the User object with next ID

    override fun register(user: User): Boolean {
        if (users.any{it.name == user.name}) return false // if user already exist return false
        users.add(user.copy(id=nextId++)) // create copy from user object with unique ID
        return true
    }

    override fun login(username: String, password: String): User?{
        // return User object if the user is existed in the users list else return null
        return users.find { it.name == username && it.password == password}
    }

    override fun getUserById(id: Int): User? {
        // get the first user match the given id
        return users.find { it.id == id }
    }

}