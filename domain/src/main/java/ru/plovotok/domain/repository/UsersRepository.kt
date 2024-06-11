package ru.plovotok.domain.repository

import ru.plovotok.domain.models.GHUser

interface UsersRepository {

    suspend fun getUser(username: String): Result<GHUser>
}