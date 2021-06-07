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

    @Test
    private fun insereVoluntario(tabela: TabelaVoluntarios, voluntario: Voluntario): Long {
        val id = tabela.insert(voluntario.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    @Test
    private fun insereInstituicao(tabela: TabelaInstituicoes, instituicao: Instituicao): Long {
        val id = tabela.insert(instituicao.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    @Test
    private fun insereTarefa(tabela: TabelaTarefas, tarefas: Tarefas): Long {
        val id = tabela.insert(tarefas.toContentValues())
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
    private fun getInstituicaoBaseDados(tabela: TabelaInstituicoes, id: Long): Instituicao {
        val cursor = tabela.query(
            TabelaInstituicoes.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Instituicao.fromCursor(cursor)
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

        val voluntario = Voluntario(nome = "Manuel", data_nascimento = "10/02/1999", genero = "Masculino")

        voluntario.id = insereVoluntario(tabelaVoluntarios, voluntario)

        assertEquals(voluntario, getVoluntarioBaseDados(tabelaVoluntarios, voluntario.id))

        db.close()
    }

    @Test
    fun consegueAlterarVoluntarios() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaVoluntarios = TabelaVoluntarios(db)

        val voluntario = Voluntario(nome = "Manuel", data_nascimento = "10/02/1999", genero = "Masculino")
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

        val voluntario = Voluntario(nome = "Manuel", data_nascimento = "10/02/1999", genero = "Masculino")
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

        val voluntario = Voluntario(nome = "Manuel", data_nascimento = "10/02/1999", genero = "Masculino")
        voluntario.id = insereVoluntario(tabelaVoluntarios, voluntario)

        assertEquals(voluntario, getVoluntarioBaseDados(tabelaVoluntarios, voluntario.id))

        db.close()
    }

    /*
    @Test
    fun consegueInserirInstituicao() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase

        val tabelaInstituicoes = TabelaInstituicoes(db)
        val instituicao = Instituicao(nome = "Cruz Vermelha", telefone = 298765431, morada = "Av. Dr. Afonso Costa ", tarefas= "Distribuir vacinas")
        instituicao.id = insereInstituicao(TabelaInstituicoes, instituicao)

        assertEquals(instituicao, getInstituicaoBaseDados(tabelaInstituicoes, instituicao.id))

        db.close()
    }
     */

    @Test
    fun consegueInserirCategorias() {
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaTarefas = TabelaTarefas(db)

        val tarefas = Tarefas(nome = "Drama"    )
        tarefas.id = insereTarefa(tabelaTarefas, tarefas)

        db.close()
    }
}