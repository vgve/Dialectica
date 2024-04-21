package com.example.dialectica.domain.usecases

import com.example.dialectica.core.domain.SharedPrefsKeys
import com.example.dialectica.core.domain.repositories.SharedPrefsRepository

class GetAuthorizeUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(): Boolean {
        return sharedPrefsRepository.getBoolean(SharedPrefsKeys.IS_AUTHORIZED_KEY)
    }
}
