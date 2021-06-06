package com.example.voluntariado

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Voluntario(var id: Long = -1, var nome: String, var data_nascimento: String , var telefone: Long = 0, var genero: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaVoluntarios.CAMPO_NOME, nome)
        valores.put(TabelaVoluntarios.CAMPO_DATA_NASCIMENTO, data_nascimento)
        valores.put(TabelaVoluntarios.CAMPO_TELEFONE, telefone)
        valores.put(TabelaVoluntarios.CAMPO_GENERO, genero)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Voluntario {
            val volId = cursor.getColumnIndex(BaseColumns._ID)
            val volNome = cursor.getColumnIndex(TabelaVoluntarios.CAMPO_NOME)
            val volDataNascimento = cursor.getColumnIndex(TabelaVoluntarios.CAMPO_DATA_NASCIMENTO)
            val volTelefone = cursor.getColumnIndex(TabelaVoluntarios.CAMPO_TELEFONE)
            val volGenero = cursor.getColumnIndex(TabelaVoluntarios.CAMPO_GENERO)

            val id = cursor.getLong(volId)
            val nome = cursor.getString(volNome)
            val data_nascimento = cursor.getString(volDataNascimento)
            val telefone = cursor.getLong(volTelefone)
            val genero = cursor.getString(volGenero)

            return Voluntario(id, nome, data_nascimento, telefone, genero)
        }
    }
}