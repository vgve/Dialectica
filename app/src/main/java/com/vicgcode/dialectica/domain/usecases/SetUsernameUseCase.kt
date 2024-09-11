package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository

class SetUsernameUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(username: String) {
        return sharedPrefsRepository.setUsername(username)
    }
}
