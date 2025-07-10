package data.model

data class BankAccount(
    var id: Int=generateId(),
    var userId: Int,
    var balance: Double = 0.0
){
    companion object{
        private var idCounter=1000
            fun generateId(): Int = idCounter++
    }
}