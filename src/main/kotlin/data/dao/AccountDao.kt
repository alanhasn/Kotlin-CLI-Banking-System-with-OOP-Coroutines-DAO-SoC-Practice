package data.dao

import data.model.BankAccount

interface AccountDao {
    fun createAccount(account: BankAccount): Boolean
    fun getAccountsByUserId(userId: Int): List<BankAccount>
    fun getAccountById(accountId: Int): BankAccount?
    fun updateAccount(account: BankAccount): Boolean
}
