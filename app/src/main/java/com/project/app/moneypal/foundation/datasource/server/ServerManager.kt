package com.project.app.moneypal.foundation.datasource.server

import com.project.app.moneypal.model.Credential
import com.project.app.moneypal.model.User
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerManager @Inject constructor() {

    fun fetchCredential(): Flow<Credential> {
        return flow { emit(Credential(token = UUID.randomUUID().toString())) }
    }

    fun fetchUser(email: String, password: String): Flow<User> {
        return flow { emit(User(email)) }
    }

}
