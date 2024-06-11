package ru.plovotok.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import ru.plovotok.data.network.client
import ru.plovotok.data.repository.SearchRepositoryImpl
import ru.plovotok.data.repository.UsersRepositoryImpl
import ru.plovotok.domain.repository.SearchRepository
import ru.plovotok.domain.repository.UsersRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun bindGHRepositoryRepository(client: HttpClient): SearchRepository {
        return SearchRepositoryImpl(client)
    }

    @Provides
    fun bindUsersRepository(client: HttpClient): UsersRepository {
        return UsersRepositoryImpl(client)
    }

    @Provides
    fun provideClient(): HttpClient = client
}