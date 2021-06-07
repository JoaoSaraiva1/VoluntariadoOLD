package com.example.voluntariado

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class TabelaInstituicoes(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_TELEFONE NUMBER NOT NULL, $CAMPO_MORADA TEXT NOT NULL, $CAMPO_ID_TAREFAS INTEGER NOT NULL, FOREIGN KEY($CAMPO_ID_TAREFAS) REFERENCES ${TabelaTarefas.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)

    }

    companion object {

        const val NOME_TABELA = "instituicoes"
        const val CAMPO_NOME = "nome"
        const val CAMPO_TELEFONE = "telefone"
        const val CAMPO_MORADA = "morada"
        const val CAMPO_ID_TAREFAS = "id_tarefas"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME,CAMPO_TELEFONE,CAMPO_MORADA,CAMPO_ID_TAREFAS)
    }
}

