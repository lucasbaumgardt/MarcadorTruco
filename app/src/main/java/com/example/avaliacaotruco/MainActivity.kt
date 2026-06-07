package com.example.avaliacaotruco

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var nomeJ1 = "Jogador 1"
    private var pontosJ1 = 0
    private var vitoriasJ1 = 0

    private var nomeJ2 = "Jogador 2"
    private var pontosJ2 = 0
    private var vitoriasJ2 = 0

    private lateinit var tvJogador1: TextView
    private lateinit var tvJogador2: TextView

    private val mudarNomes = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == RESULT_OK) {
            nomeJ1 = result.data?.getStringExtra("novo_nomeJ1") ?: "Jogador 1"
            nomeJ2 = result.data?.getStringExtra("novo_nomeJ2") ?: "Jogador 2"
            atualizarTela()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvJogador1 = findViewById(R.id.tvJogador1)
        tvJogador2 = findViewById(R.id.tvJogador2)

        atualizarTela()

        findViewById<Button>(R.id.btnJ1_1).setOnClickListener {
            adicionarPontos(1, 1)
        }

        findViewById<Button>(R.id.btnJ1_3).setOnClickListener {
            adicionarPontos(1, 3)
        }

        findViewById<Button>(R.id.btnJ1_6).setOnClickListener {
            adicionarPontos(1, 6)
        }

        findViewById<Button>(R.id.btnJ1_9).setOnClickListener {
            adicionarPontos(1, 9)
        }

        findViewById<Button>(R.id.btnJ1_12).setOnClickListener {
            adicionarPontos(1, 12)
        }

        findViewById<Button>(R.id.btnJ2_1).setOnClickListener {
            adicionarPontos(2, 1)
        }

        findViewById<Button>(R.id.btnJ2_3).setOnClickListener {
            adicionarPontos(2, 3)
        }

        findViewById<Button>(R.id.btnJ2_6).setOnClickListener {
            adicionarPontos(2, 6)
        }

        findViewById<Button>(R.id.btnJ2_9).setOnClickListener {
            adicionarPontos(2, 9)
        }

        findViewById<Button>(R.id.btnJ2_12).setOnClickListener {
            adicionarPontos(2, 12)
        }

        findViewById<Button>(R.id.btnHistorico).setOnClickListener {
            val intent = Intent(this, HistoricoActivity::class.java).apply {
                putExtra("nomeJ1", nomeJ1)
                putExtra("vitoriasJ1", vitoriasJ1)
                putExtra("nomeJ2", nomeJ2)
                putExtra("vitoriasJ2", vitoriasJ2)
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnZerarHistorico).setOnClickListener {
            pontosJ1 = 0
            pontosJ2 = 0
            vitoriasJ1 = 0
            vitoriasJ2 = 0
            atualizarTela()
            Toast.makeText(this, getString(R.string.mensagem_historico), Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnInfNomes).setOnClickListener {
            val intent = Intent(this, EquipesActivity::class.java).apply {
                putExtra("atualJ1", nomeJ1)
                putExtra("atualJ2", nomeJ2)
            }
            mudarNomes.launch(intent)
        }
    }

    private fun adicionarPontos(jogador: Int, valor: Int) {
        if (jogador == 1) {
            pontosJ1 += valor
            if (pontosJ1 >= 12) {
                vitoriasJ1++
                exibirDialogVitoria(nomeJ1)
            }
        } else {
            pontosJ2 += valor
            if (pontosJ2 >= 12) {
                vitoriasJ2++
                exibirDialogVitoria(nomeJ2)
            }
        }
        atualizarTela()
    }

    private fun exibirDialogVitoria(nomeVencedor: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.fim_partida))
            .setMessage(getString(R.string.mensagem_vencedor, nomeVencedor))
            .setPositiveButton("OK") { _, _ ->
                pontosJ1 = 0
                pontosJ2 = 0
                atualizarTela()
            }
            .setCancelable(false)
            .show()
    }

    private fun atualizarTela() {
        tvJogador1.text = getString(R.string.placar_jogador, nomeJ1, pontosJ1)
        tvJogador2.text = getString(R.string.placar_jogador, nomeJ2, pontosJ2)
    }
}