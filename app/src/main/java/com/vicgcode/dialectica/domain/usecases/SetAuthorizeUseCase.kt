package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import javax.inject.Inject

class SetAuthorizeUseCase @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(isAuthorized: Boolean) {
        return sharedPrefsRepository.setAuthorize(isAuthorized)
    }
}
