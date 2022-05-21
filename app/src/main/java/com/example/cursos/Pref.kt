package com.example.cursos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.idnp.datastoresamplegra.NotePrefs

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NotePrefs.PREFS_NAME)