package com.TrustFlow_Backend_Auth.usecases

//class RegisterUserUseCase(private val userRepository: UserRepository) {
//    fun execute(user: User): User? {
//        val hashedPassword = BCrypt.withDefaults().hashToString(12, user.password.toCharArray())
//        val newUser = user.copy(password = hashedPassword)
//        return userRepository.addUser(newUser)
//    }
//}