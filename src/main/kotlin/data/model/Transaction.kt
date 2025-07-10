package data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Transaction(
    val id: Int = generatedId(),
    val accountId: Int,
    val type: TransactionType,
    val amount: Double,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val description: String
){
    companion object{
        private var idCounter=1
        fun generatedId(): Int = idCounter++
    }
}