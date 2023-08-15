package com.project.app.moneypal.foundation.extension

import com.project.app.moneypal.model.Credential

fun Credential.isLoggedIn() = token.isNotBlank()
