package com.example.voluntariado

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestesBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdVoluntariadoOpenHelper() = BdVoluntariadoOpenHelper(getAppContext())

    private fun insereVoluntario(tabela: TabelaVoluntarios, voluntario: Voluntario): Long {
        val id = tabela.insert(voluntario.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getVoluntarioBaseDados(tabela: TabelaVoluntarios, id: Long): Voluntario {
        val cursor = tabela.query(
            TabelaVoluntarios.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Voluntario.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdVoluntariadoOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdVoluntariadoOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirVoluntarios() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaVoluntarios = TabelaVoluntarios(db)

        val voluntario = Voluntario(nome = "Joao")
        voluntario.id = insereVoluntario(tabelaVoluntarios, voluntario)

        assertEquals(voluntario, getVoluntarioBaseDados(tabelaVoluntarios, voluntario.id))

        db.close()
    }

    @Test
    fun consegueAlterarVoluntarios() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaVoluntarios = TabelaVoluntarios(db)

        val voluntario = Voluntario(nome = "Joao")
        voluntario.id = insereVoluntario(tabelaVoluntarios, voluntario)

        voluntario.nome = "Manel"

        val registosAlterados = TabelaVoluntarios.update(
            voluntario.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(voluntario.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(voluntario, getVoluntarioBaseDados(tabelaVoluntarios, voluntario.id))

        db.close()
    }

    @Test
    fun consegueEliminarVoluntarios() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaVoluntarios = TabelaVoluntarios(db)

        val voluntario = Voluntario(nome = "teste")
        voluntario.id = insereVoluntario(tabelaVoluntarios, voluntario)

        val registosEliminados = tabelaVoluntarios.delete(
            "${BaseColumns._ID}=?",
            arrayOf(voluntario.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerVoluntarios() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaVoluntarios = TabelaVoluntarios(db)

        val voluntario = Voluntario(nome = "Aventura")
        voluntario.id = insereVoluntario(tabelaVoluntarios, voluntario)

        assertEquals(voluntario, getVoluntarioBaseDados(tabelaVoluntarios, voluntario.id))

        db.close()
    }
}