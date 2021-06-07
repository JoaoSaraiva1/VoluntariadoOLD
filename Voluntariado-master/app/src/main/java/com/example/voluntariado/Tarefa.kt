package com.example.voluntariado

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Tarefa(var id: Long = -1, var nome: String, var estado: String)  {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaTarefas.CAMPO_NOME, nome)
        valores.put(TabelaTarefas.CAMPO_ESTADO, estado)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Tarefa {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaTarefas.CAMPO_NOME)
            val colEstado = cursor.getColumnIndex(TabelaTarefas.CAMPO_ESTADO)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val estado = cursor.getString(colEstado)


            return Tarefa(id, nome, estado)
        }
    }
}