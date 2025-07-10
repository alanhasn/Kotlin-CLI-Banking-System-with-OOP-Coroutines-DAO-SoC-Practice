package data.dao

import data.model.Transaction

interface TransactionDao {
    fun add(transaction: Transaction)
    fun getAll(): List<Transaction>
    fun getByAccountId(accountId: Int): List<Transaction>
}