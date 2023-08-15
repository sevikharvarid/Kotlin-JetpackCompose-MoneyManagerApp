package com.project.app.moneypal.foundation.datasource.preference

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.project.app.moneypal.foundation.datasource.preference.model.ThemePreference
import com.project.app.moneypal.model.Theme
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object ThemePreferenceSerializer : Serializer<ThemePreference> {

    override val defaultValue: ThemePreference = ThemePreference(Theme.SYSTEM.value)

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): ThemePreference {
        try {
            return ThemePreference.ADAPTER.decode(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: ThemePreference, output: OutputStream) {
        ThemePreference.ADAPTER.encode(output, t)
    }

}
