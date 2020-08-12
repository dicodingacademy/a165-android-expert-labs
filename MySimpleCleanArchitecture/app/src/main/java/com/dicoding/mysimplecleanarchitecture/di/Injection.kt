package com.dicoding.mysimplecleanarchitecture.di

import com.dicoding.mysimplecleanarchitecture.data.IMessageDataSource
import com.dicoding.mysimplecleanarchitecture.data.MessageDataSource
import com.dicoding.mysimplecleanarchitecture.data.MessageRepository
import com.dicoding.mysimplecleanarchitecture.domain.IMessageRepository
import com.dicoding.mysimplecleanarchitecture.domain.MessageInteractor
import com.dicoding.mysimplecleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}
