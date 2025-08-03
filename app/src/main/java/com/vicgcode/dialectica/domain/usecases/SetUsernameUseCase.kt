package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import javax.inject.Inject

class SetUsernameUseCase @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(username: String) {
        return sharedPrefsRepository.setUsername(username)
    }
}
