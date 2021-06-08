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
    private fun insereTarefa(tabela: TabelaTarefas, tarefa: Tarefa): Long {
        val id = tabela.insert(tarefa.toContentValues())
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

    private fun getTarefasBaseDados(tabela: TabelaTarefas, id: Long): Tarefa {
        val cursor = tabela.query(
            TabelaTarefas.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Tarefa.fromCursor(cursor)
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


    @Test
    fun consegueInserirInstituicao() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase

        val tabelaTarefas = TabelaTarefas(db)
        val tarefa = Tarefa(nome = "limpeza", estado = "realizado")
        tarefa.id = insereTarefa(tabelaTarefas, tarefa)

        val tabelaInstituicoes = TabelaInstituicoes(db)
        val instituicao = Instituicao(nome = "Cruz Vermelha", telefone = 298765431, morada = "Av. Dr. Afonso Costa ", idTarefa = tarefa.id)
        instituicao.id = insereInstituicao(tabelaInstituicoes, instituicao)

        assertEquals(instituicao, getInstituicaoBaseDados(tabelaInstituicoes, instituicao.id))

        db.close()
    }

    @Test
    fun consegueAlterarInstituicao() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase

        val tabelaTarefas = TabelaTarefas(db)

        val tarefaVacinar = Tarefa(nome = "Vacinar", estado ="por realizar")
        tarefaVacinar.id = insereTarefa(tabelaTarefas, tarefaVacinar)

        val tarefaHorarios = Tarefa(nome = "Criacao de horarios de vacinacao", estado = "realizada")
        tarefaHorarios.id = insereTarefa(tabelaTarefas, tarefaVacinar)

        val tabelaInstituicoes = TabelaInstituicoes(db)

        val instituicao = Instituicao(nome = "?", telefone = 0, morada = "", idTarefa = tarefaVacinar.id)
        instituicao.id = insereInstituicao(tabelaInstituicoes, instituicao)

        instituicao.nome = "ADIC"
        instituicao.telefone = 230978465
        instituicao.morada = "Rua direita"
        instituicao.idTarefa = tarefaVacinar.id

        val registosAlterados = tabelaInstituicoes.update(
            instituicao.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(instituicao.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(instituicao, getInstituicaoBaseDados(tabelaInstituicoes, instituicao.id))

        db.close()
    }

    @Test
    fun consegueEliminarInstituicao() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase

        val tabelaTarefas = TabelaTarefas(db)
        val tarefa = Tarefa(nome = "Notificar pessoas a vacinar", estado = "Em curso")
        tarefa.id = insereTarefa(tabelaTarefas, tarefa)

        val tabelaInstituicao = TabelaInstituicoes(db)
        val instituicao = Instituicao(nome = "?", telefone = 0, morada ="?", idTarefa = tarefa.id)
        instituicao.id = insereInstituicao(tabelaInstituicao, instituicao)

        val registosEliminados = tabelaInstituicao.delete(
            "${BaseColumns._ID}=?",
            arrayOf(instituicao.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerInstituicao() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase

        val tabelaTarefas = TabelaTarefas(db)
        val tarefa = Tarefa(nome = "Culinária", estado = "")
        tarefa.id = insereTarefa(tabelaTarefas, tarefa)

        val tabelaInstituicoes = TabelaInstituicoes(db)
        val instituicao = Instituicao(nome = "IVL", telefone = 987654321,morada= "Rua esquerda", idTarefa = tarefa.id)
        instituicao.id = insereInstituicao(tabelaInstituicoes, instituicao)

        assertEquals(instituicao, getInstituicaoBaseDados(tabelaInstituicoes, instituicao.id))

        db.close()
    }

    @Test
    fun consegueInserirTarefas() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaTarefas = TabelaTarefas(db)

        val tarefa = Tarefa(nome = "Limpar", estado = "Acabada")
        tarefa.id = insereTarefa(tabelaTarefas, tarefa)

        assertEquals(tarefa, getTarefasBaseDados(tabelaTarefas, tarefa.id))

        db.close()
    }

    @Test
    fun consegueAlterarTarefas() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaTarefas = TabelaTarefas(db)

        val tarefa = Tarefa(nome = "Arrumar", estado = "Por realizar")
        tarefa.id = insereTarefa(tabelaTarefas, tarefa)

        tarefa.nome = "Organizar"
        tarefa.estado ="Realizada"

        val registosAlterados = tabelaTarefas.update(
            tarefa.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(tarefa.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(tarefa, getTarefasBaseDados(tabelaTarefas, tarefa.id))

        db.close()
    }

    @Test
    fun consegueEliminarTarefas() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaTarefas = TabelaTarefas(db)

        val tarefa = Tarefa(nome = "teste", estado = "teste")
        tarefa.id = insereTarefa(tabelaTarefas, tarefa)

        val registosEliminados = tabelaTarefas.delete(
            "${BaseColumns._ID}=?",
            arrayOf(tarefa.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerTarefas() {
        val db = getBdVoluntariadoOpenHelper().writableDatabase
        val tabelaTarefas = TabelaTarefas(db)

        val tarefa = Tarefa(nome = "Limpar", estado = "Acabada")
        tarefa.id = insereTarefa(tabelaTarefas, tarefa)

        assertEquals(tarefa, getTarefasBaseDados(tabelaTarefas, tarefa.id))

        db.close()
    }

}