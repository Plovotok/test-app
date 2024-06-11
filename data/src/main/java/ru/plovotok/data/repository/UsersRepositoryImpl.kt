package ru.plovotok.data.repository

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import ru.plovotok.data.models.GHUserDto
import ru.plovotok.data.network.ApiResponse
import ru.plovotok.data.network.safeRequest
import ru.plovotok.domain.models.GHUser
import ru.plovotok.domain.repository.UsersRepository
import javax.inject.Inject

internal class UsersRepositoryImpl @Inject constructor(
    private val client: HttpClient) : UsersRepository {
    override suspend fun getUser(username: String): Result<GHUser> {
        val response = client.safeRequest<GHUserDto>(
            host = "api.github.com",
            path = "users/$username",
            method = HttpMethod.Get
        )
        return when (response) {
            is ApiResponse.Error -> Result.failure(response.exception)
            is ApiResponse.Success -> Result.success(response.data.toGHUser())
        }
    }
}