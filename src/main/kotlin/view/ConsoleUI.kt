package view

import data.dao.InMemoryUserDao
import data.model.User
import service.UserService

class ConsoleUI(){
    fun startUI(){
        print("======= Welcome to Kotlin Bank System =======")
        while (true){
            try {
                println(">>> You should create account first")
                println("\n1.Login\n2.Register")
                val op = readln().toInt()
                when(op){
                    1 -> {
                        print("Enter username:")
                        val username = readln()
                        print("Enter password:")
                        val password = readln()
                        bankMenu()
                        UserService.register(username , password)
                    }
                    2 -> {
                        print("Enter username:")
                        val username = readln()
                        print("Enter password:")
                        val password = readln()
                        bankMenu()
                        UserService.login(username , password)
                    }
                    3 -> {
                        print("Enter the User ID:")
                        val id = readln().toInt()
                        UserService.getUserById(id)
                    }
                }

            }catch (e: Exception){
                print("Unexpected error occur $e")
            }
        }
    }
    fun bankMenu(){
        println("\n===== BANK MENU =====")
        println("1) Check Balance")
        println("2) Deposit")
        println("3) Withdraw")
        println("4) Transfer")
        println("5) View Transaction History")
        println("6) Logout")
        print("Choose an option: ")
    }
}