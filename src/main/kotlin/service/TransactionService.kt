package service

import data.dao.AccountDao
import data.dao.InMemoryAccountDao
import data.dao.InMemoryTransactionDao
import data.dao.TransactionDao
import data.model.Transaction
import data.model.TransactionType

object TransactionService{
    private val accountDao = InMemoryAccountDao()

    private val transactionDao = InMemoryTransactionDao()

    fun deposit(accountId: Int , amount: Double){
        try {
            val account = accountDao.getAccountById(accountId)
            if (account != null){
                account.balance += amount
                accountDao.updateAccount(account)
                transactionDao.add(Transaction(
                    accountId=accountId,
                    type = TransactionType.DEPOSIT,
                    amount = amount,
                    description = "Deposited via CLI"
                ))
                println("Deposited $amount successfully. New Balance: ${account.balance}")
            }else{
                println("Account not found")
            }
        }catch (e: Exception){
            println("Error during deposit: ${e.message}")
        }
    }

    fun withdraw(accountId: Int , amount: Double){
        try {
            val account = accountDao.getAccountById(accountId)
            if (account != null){
                if (account.balance >= amount){
                    account.balance -= amount
                    accountDao.updateAccount(account)
                    transactionDao.add(Transaction(
                        accountId=accountId,
                        type = TransactionType.WITHDRAW,
                        amount = amount,
                        description = "Withdraw via CLI"
                    ))
                    println("Withdrawn $amount successfully. New Balance: ${account.balance}")
                }
                else{
                    println("Insufficient balance")
                }
            }else{
                println("Account not found")
            }
        }catch (e: Exception){
            println("Error during withdrawal: ${e.message}")
        }
    }

    fun transfer(fromId: Int , toId: Int , amount: Double){
        try {
            val fromAccount = accountDao.getAccountById(fromId)
            val toAccount = accountDao.getAccountById(toId)
            if (fromAccount == null || toAccount == null){
                println("One or both accounts not found.")
                return
            }
            if (fromAccount.balance < amount){
                println("Insufficient balance to transfer.")
                return
            }

            fromAccount.balance -= amount
            toAccount.balance += amount

            accountDao.updateAccount(fromAccount)
            accountDao.updateAccount(toAccount)

            transactionDao.add(Transaction(
                accountId = fromId,
                type = TransactionType.TRANSFER,
                amount = amount,
                description = "Transfer to account $toId"
            ))
            transactionDao.add(Transaction(
                accountId = toId,
                type = TransactionType.DEPOSIT,
                amount = amount,
                description = "Received from account $fromId"
            ))

            println("Transferred $amount from account $fromId to $toId successfully.")
        }catch (e: Exception){
            println("Error during transfer: ${e.message}")
        }
    }

}