package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.core.domain.SharedPrefsKeys
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository

class GetAuthorizeUseCase(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(): Boolean {
        return sharedPrefsRepository.getBoolean(SharedPrefsKeys.IS_AUTHORIZED_KEY)
    }
}
