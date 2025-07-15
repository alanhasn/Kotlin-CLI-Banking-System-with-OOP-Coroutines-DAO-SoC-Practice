package view

import service.BankService
import service.TransactionService
import service.UserService
import kotlin.system.exitProcess

class ConsoleUI {

    fun start() {
        while (true) {
            mainMenu()
        }
    }

    private fun printHeader(title: String) {
        println("\n${AnsiColor.BRIGHT_BLUE}╔════════════════════════════════════╗${AnsiColor.RESET}")
        println("${AnsiColor.BRIGHT_BLUE}║${AnsiColor.RESET} ${AnsiColor.BOLD}${AnsiColor.CYAN}$title${AnsiColor.RESET}")
        println("${AnsiColor.BRIGHT_BLUE}╚════════════════════════════════════╝${AnsiColor.RESET}")
    }

    private fun mainMenu() {
        printHeader("WELCOME TO CLI BANK SYSTEM 🏦")
        println(
            """
            ${AnsiColor.BRIGHT_CYAN}1️⃣  Login
            2️⃣  Register
            3️⃣  Exit${AnsiColor.RESET}
        """.trimIndent()
        )
        print("${AnsiColor.PURPLE}👉 Choose an option: ${AnsiColor.RESET}")

        try {
            when (readln().toInt()) {
                1 -> loginUser()
                2 -> registerUser()
                3 -> {
                    println("${AnsiColor.GREEN}👋 Thank you for using CLI Bank. Goodbye!${AnsiColor.RESET}")
                    exitProcess(0)
                }
                else -> println("${AnsiColor.RED}❌ Invalid option. Please try again.${AnsiColor.RESET}")
            }
        } catch (e: NumberFormatException) {
            println("${AnsiColor.RED}❌ Invalid input. Please enter a number.${AnsiColor.RESET}")
        }
    }

    private fun loginUser() {
        printHeader("🔐 Login")
        print("👤 Username: ")
        val username = readln()
        print("🔑 Password: ")
        val password = readln()

        if (username.isEmpty() || password.isEmpty()) {
            println("${AnsiColor.YELLOW}⚠️ Username and password cannot be empty.${AnsiColor.RESET}")
            return
        }

        val loginResult = UserService.login(username, password)
        loginResult.onSuccess {
            println("${AnsiColor.GREEN}✅ Welcome back, ${it.name}!${AnsiColor.RESET}")
            bankMenu()
        }.onFailure {
            println("${AnsiColor.RED}❌ ${it.message}${AnsiColor.RESET}")
        }
    }

    private fun registerUser() {
        printHeader("📝 Register")
        print("👤 Choose a Username: ")
        val username = readln()
        print("🔑 Choose a Password: ")
        val password = readln()

        if (username.isEmpty() || password.isEmpty()) {
            println("${AnsiColor.YELLOW}⚠️ Username and password cannot be empty.${AnsiColor.RESET}")
            return
        }

        val registerResult = UserService.register(username, password)
        registerResult.onSuccess {
            println("${AnsiColor.GREEN}🎉 Welcome ${it.name}! Your account has been created.${AnsiColor.RESET}")
            bankMenu()
        }.onFailure {
            println("${AnsiColor.RED}❌ ${it.message}${AnsiColor.RESET}")
        }
    }

    private fun transactionMenu() {
        val currentUser = UserService.getCurrentUser()
        if (currentUser == null) {
            println("${AnsiColor.YELLOW}⚠️ You must be logged in to access transactions.${AnsiColor.RESET}")
            return
        }

        while (true) {
            printHeader("💳 Transaction Menu")
            println(
                """
                ${AnsiColor.BRIGHT_CYAN}1️⃣  Withdraw
                2️⃣  Deposit
                3️⃣  Transfer
                4️⃣  Back${AnsiColor.RESET}
            """.trimIndent()
            )
            print("${AnsiColor.PURPLE}👉 Select an option: ${AnsiColor.RESET}")

            try {
                when (readln().toInt()) {
                    1 -> {
                        print("🏦 Account ID: ")
                        val accId = readln().toInt()
                        print("💸 Amount to withdraw: ")
                        val amount = readln().toDouble()
                        TransactionService.withdraw(accId, amount)
                    }
                    2 -> {
                        print("🏦 Account ID: ")
                        val accId = readln().toInt()
                        print("💰 Amount to deposit: ")
                        val amount = readln().toDouble()
                        TransactionService.deposit(accId, amount)
                    }
                    3 -> {
                        print("📤 From Account ID: ")
                        val fromId = readln().toInt()
                        print("📥 To Account ID: ")
                        val toId = readln().toInt()
                        print("💱 Amount to transfer: ")
                        val amount = readln().toDouble()
                        TransactionService.transfer(fromId, toId, amount)
                    }
                    4 -> return
                    else -> println("${AnsiColor.RED}❌ Invalid option. Please try again.${AnsiColor.RESET}")
                }
            } catch (e: NumberFormatException) {
                println("${AnsiColor.RED}❌ Invalid input. Please enter a number.${AnsiColor.RESET}")
            }
        }
    }

    private fun bankMenu() {
        val currentUser = UserService.getCurrentUser()
        if (currentUser == null) {
            println("${AnsiColor.YELLOW}⚠️ You must be logged in to access the bank menu.${AnsiColor.RESET}")
            return
        }

        while (true) {
            printHeader("🏛️ Bank Menu for ${currentUser.name}")
            println(
                """
                ${AnsiColor.BRIGHT_CYAN}1️⃣  Create Bank Account
                2️⃣  View My Accounts
                3️⃣  Get Account by ID
                4️⃣  Transactions
                5️⃣  Logout${AnsiColor.RESET}
            """.trimIndent()
            )
            print("${AnsiColor.PURPLE}👉 Choose an option: ${AnsiColor.RESET}")

            try {
                when (readln().toInt()) {
                    1 -> {
                        print("💵 Initial Balance (default 0): ")
                        val balanceInput = readln()
                        val balance = if (balanceInput.isBlank()) 0.0 else balanceInput.toDouble()
                        BankService.createAccount(currentUser.id, balance)
                    }
                    2 -> BankService.getAccountsByUserId(currentUser.id)
                    3 -> {
                        print("🔍 Enter Account ID: ")
                        val accId = readln().toInt()
                        BankService.getAccountById(accId)
                    }
                    4 -> transactionMenu()
                    5 -> {
                        UserService.logout()
                        println("${AnsiColor.GREEN}👋 You have been logged out.${AnsiColor.RESET}")
                        return
                    }
                    else -> println("${AnsiColor.RED}❌ Invalid option.${AnsiColor.RESET}")
                }
            } catch (e: NumberFormatException) {
                println("${AnsiColor.RED}❌ Invalid input. Please enter a number.${AnsiColor.RESET}")
            }
        }
    }
}
