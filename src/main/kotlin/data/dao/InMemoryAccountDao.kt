package data.dao

import data.model.BankAccount

object InMemoryAccountDao: AccountDao {
    private val accounts = mutableListOf<BankAccount>()

    override fun createAccount(account: BankAccount): Boolean {
        return accounts.add(account)

    }

    override fun getAccountsByUserId(userId: Int): List<BankAccount> {
        return accounts.filter { it.userId == userId }

    }

    override fun getAccountById(accountId: Int): BankAccount? {
        return accounts.find { it.id == accountId }
    }

    override fun updateAccount(account: BankAccount): Boolean {
        val index = accounts.indexOfFirst { it.id == account.id }
        return if (index != -1){
            accounts[index] = account
            true
        }else false
    }
}