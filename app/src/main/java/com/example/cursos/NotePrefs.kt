package org.idnp.datastoresamplegra

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotePrefs(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveNoteBackgroundColor(noteBackgroundColor: String) {
        dataStore.edit { preferences ->
            preferences[BACKGROUND_COLOR] = noteBackgroundColor
        }
    }

    val backgroundColor: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[BACKGROUND_COLOR] ?: Cyan.toArgb().toString()
        }

    suspend fun saveNoteTextSize(noteTextSize: String) {
        dataStore.edit { preferences ->
            preferences[TEXT_SIZE] = noteTextSize
        }
    }

    val textSize: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[TEXT_SIZE] ?: "16"
        }


    suspend fun saveNoteTextStyle(noteTextStyle: String) {
        dataStore.edit { preferences ->
            preferences[TEXT_STYLE] = noteTextStyle
        }
    }

    val textStyle: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[TEXT_STYLE] ?: "Normal"
        }

    companion object {
        val PREFS_NAME = "PREFS_NAME"
        private val BACKGROUND_COLOR = stringPreferencesKey("key_app_background_color")
        private val TEXT_SIZE = stringPreferencesKey("key_app_text_size")
        private val TEXT_STYLE = stringPreferencesKey("key_app_text_style")

    }
}
