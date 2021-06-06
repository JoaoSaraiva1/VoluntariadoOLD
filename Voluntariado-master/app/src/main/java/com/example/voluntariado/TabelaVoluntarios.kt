package com.example.voluntariado

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaVoluntarios(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO TEXT NOT NULL, $CAMPO_TELEFONE NUMBER NOT NULL, $CAMPO_GENERO TEXT NOT NULL)")
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
        fun update(toContentValues: ContentValues, s: String, arrayOf: Array<String>) {

        }

        const val NOME_TABELA = "voluntarios"
        const val CAMPO_NOME = "nome"
        const val CAMPO_DATA_NASCIMENTO = "data_nascimento"
        const val CAMPO_TELEFONE = "telefone"
        const val CAMPO_GENERO= "genero"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME,CAMPO_DATA_NASCIMENTO,CAMPO_TELEFONE,CAMPO_GENERO)
    }
}
