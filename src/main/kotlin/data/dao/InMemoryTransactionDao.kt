package data.dao

import data.model.Transaction

class InMemoryTransactionDao: TransactionDao {
    private val transactions = mutableListOf<Transaction>()

    override fun add(transaction: Transaction) {
        transactions.add(transaction)
    }

    override fun getAll(): List<Transaction> = transactions

    override fun getByAccountId(accountId: Int): List<Transaction> {
        return transactions.filter { it.accountId == accountId }
    }
}