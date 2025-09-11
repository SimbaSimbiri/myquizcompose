package com.simbiri.myquiz.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.simbiri.myquiz.data.util.Constants.DATA_STORE_FILE

object DataStoreFactory {
    fun create(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(name= DATA_STORE_FILE)
        }
    }
}