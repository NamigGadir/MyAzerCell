package com.azercell.myazercell.data.di

import com.azercell.myazercell.data.repository.AuthRepositoryImpl
import com.azercell.myazercell.data.repository.CardsRepositoryImpl
import com.azercell.myazercell.data.repository.TransfersRepositoryImpl
import com.azercell.myazercell.domain.repository.AuthRepository
import com.azercell.myazercell.domain.repository.CardsRepository
import com.azercell.myazercell.domain.repository.TransfersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindCardsRepository(cardsRepositoryImpl: CardsRepositoryImpl): CardsRepository

    @Binds
    @Singleton
    fun bindTransfersRepository(transfersRepositoryImpl: TransfersRepositoryImpl): TransfersRepository


}