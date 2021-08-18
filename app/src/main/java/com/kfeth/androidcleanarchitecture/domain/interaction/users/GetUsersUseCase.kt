package com.kfeth.androidcleanarchitecture.domain.interaction.users

import com.kfeth.androidcleanarchitecture.data.UsersRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend operator fun invoke() = usersRepository.getRandomUsers()
}