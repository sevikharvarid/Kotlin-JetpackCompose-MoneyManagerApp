package com.project.app.moneypal.foundation.datasource.preference

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.project.app.moneypal.foundation.datasource.preference.model.LanguagePreference
import com.project.app.moneypal.model.Language
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object LanguagePreferenceSerializer : Serializer<LanguagePreference> {

    override val defaultValue: LanguagePreference = LanguagePreference(Language.ENGLISH.code)

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): LanguagePreference {
        try {
            return LanguagePreference.ADAPTER.decode(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: LanguagePreference, output: OutputStream) {
        LanguagePreference.ADAPTER.encode(output, t)
    }

}
