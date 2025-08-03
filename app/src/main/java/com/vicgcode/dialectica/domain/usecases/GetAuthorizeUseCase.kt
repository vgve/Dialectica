package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import javax.inject.Inject

class GetAuthorizeUseCase @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository
) {

    operator fun invoke(): Boolean {
        return sharedPrefsRepository.isAuthorize()
    }
}
