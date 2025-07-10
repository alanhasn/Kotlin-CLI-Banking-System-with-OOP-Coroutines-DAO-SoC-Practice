package service

import data.dao.AccountDao
import data.dao.InMemoryAccountDao
import data.model.BankAccount

object BankService{
    private val accountDao: AccountDao = InMemoryAccountDao()

    fun createAccount(userId:Int , balance: Double){
        try {
            val account = BankAccount(userId = userId , balance = balance)
            val success = accountDao.createAccount(account)
            if (success){
                print("Account created successfully with ID: ${account.id}")
            }
            else{
                println("Failed to create account.")
            }
        }
        catch (e: Exception){
            println("Unexpected error: ${e.message}")
        }
    }

    fun getAccountsByUserId(userId: Int){
        try {
            val accounts = accountDao.getAccountsByUserId(userId)
            if (accounts.isEmpty()) {
                println("No accounts found for user $userId")
            } else {
                println("Accounts for user $userId:")
                for (account in accounts) {
                    println("Account ID: ${account.id}, Balance: ${account.balance}")
                }
            }
        } catch (e: Exception) {
            println("Error retrieving accounts: ${e.message}")
        }
    }

    fun getAccountById(accountId: Int){
        try {
            val account = accountDao.getAccountById(accountId)
            if (account != null) {
                println("Account ID: ${account.id}, Balance: ${account.balance}")
            } else {
                println("Account not found.")
            }
        } catch (e: Exception) {
            println("Error getting account: ${e.message}")
        }
    }
    fun updateAccount(account: BankAccount){
        try {
            val updatedAccount = accountDao.updateAccount(account)
            if (updatedAccount){
                println("The account with ID ${account.id} updated successfully.")
            }
            else{
                println("No account with ID ${account.id} found")
            }
        }catch (e: Exception){
            println("Unexpected Error occur $e")
        }
    }
}