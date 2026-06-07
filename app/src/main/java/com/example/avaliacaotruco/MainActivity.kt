package com.example.avaliacaotruco

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    private var nomeJ1 = "Jogador 1"
    private var pontosJ1 = 0
    private var vitoriasJ1 = 0

    private var nomeJ2 = "Jogador 2"
    private var pontosJ2 = 0
    private var vitoriasJ2 = 0

    private lateinit var tvPontosJogador1: TextView
    private lateinit var tvPontosJogador2: TextView
    private lateinit var tvLabelJogador1: TextView
    private lateinit var tvLabelJogador2: TextView
    private lateinit var tvVantagemJogador1: TextView
    private lateinit var tvVantagemJogador2: TextView
    private lateinit var cardJogador1: MaterialCardView
    private lateinit var cardJogador2: MaterialCardView

    private val mudarNomes = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
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

        tvPontosJogador1 = findViewById(R.id.tvPontosJogador1)
        tvPontosJogador2 = findViewById(R.id.tvPontosJogador2)
        tvLabelJogador1 = findViewById(R.id.tvLabelJogador1)
        tvLabelJogador2 = findViewById(R.id.tvLabelJogador2)
        tvVantagemJogador1 = findViewById(R.id.tvVantagemJogador1)
        tvVantagemJogador2 = findViewById(R.id.tvVantagemJogador2)
        cardJogador1 = findViewById(R.id.cardJogador1)
        cardJogador2 = findViewById(R.id.cardJogador2)

        atualizarTela()
        setupButtons()

        findViewById<View>(R.id.btnLimparTab).setOnClickListener {
            pontosJ1 = 0
            pontosJ2 = 0
            vitoriasJ1 = 0
            vitoriasJ2 = 0
            atualizarTela()
            Toast.makeText(this, "Placar e histórico resetados", Toast.LENGTH_SHORT).show()
        }

        findViewById<View>(R.id.btnNomesTab).setOnClickListener {
            val intent = Intent(this, EquipesActivity::class.java).apply {
                putExtra("atualJ1", nomeJ1)
                putExtra("atualJ2", nomeJ2)
            }
            mudarNomes.launch(intent)
        }

        findViewById<View>(R.id.btnHistoricoTab).setOnClickListener {
            val intent = Intent(this, HistoricoActivity::class.java).apply {
                putExtra("nomeJ1", nomeJ1)
                putExtra("vitoriasJ1", vitoriasJ1)
                putExtra("nomeJ2", nomeJ2)
                putExtra("vitoriasJ2", vitoriasJ2)
            }
            startActivity(intent)
        }
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnJ1_1).setOnClickListener { adicionarPontos(1, 1) }
        findViewById<Button>(R.id.btnJ1_3).setOnClickListener { adicionarPontos(1, 3) }
        findViewById<Button>(R.id.btnJ1_6).setOnClickListener { adicionarPontos(1, 6) }
        findViewById<Button>(R.id.btnJ1_9).setOnClickListener { adicionarPontos(1, 9) }
        findViewById<Button>(R.id.btnJ1_12).setOnClickListener { adicionarPontos(1, 12) }

        findViewById<Button>(R.id.btnJ2_1).setOnClickListener { adicionarPontos(2, 1) }
        findViewById<Button>(R.id.btnJ2_3).setOnClickListener { adicionarPontos(2, 3) }
        findViewById<Button>(R.id.btnJ2_6).setOnClickListener { adicionarPontos(2, 6) }
        findViewById<Button>(R.id.btnJ2_9).setOnClickListener { adicionarPontos(2, 9) }
        findViewById<Button>(R.id.btnJ2_12).setOnClickListener { adicionarPontos(2, 12) }
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
        tvPontosJogador1.text = pontosJ1.toString()
        tvPontosJogador2.text = pontosJ2.toString()
        tvLabelJogador1.text = nomeJ1
        tvLabelJogador2.text = nomeJ2

        if (pontosJ1 > pontosJ2) {
            cardJogador1.strokeWidth = 4
            cardJogador1.strokeColor = ContextCompat.getColor(this, R.color.neon_green)
            tvPontosJogador1.setTextColor(ContextCompat.getColor(this, R.color.neon_green))
            tvVantagemJogador1.visibility = View.VISIBLE
            
            cardJogador2.strokeWidth = 0
            tvPontosJogador2.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
            tvVantagemJogador2.visibility = View.GONE
        } else if (pontosJ2 > pontosJ1) {
            cardJogador2.strokeWidth = 4
            cardJogador2.strokeColor = ContextCompat.getColor(this, R.color.neon_green)
            tvPontosJogador2.setTextColor(ContextCompat.getColor(this, R.color.neon_green))
            tvVantagemJogador2.visibility = View.VISIBLE
            
            cardJogador1.strokeWidth = 0
            tvPontosJogador1.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
            tvVantagemJogador1.visibility = View.GONE
        } else {
            cardJogador1.strokeWidth = 0
            cardJogador2.strokeWidth = 0
            tvPontosJogador1.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
            tvPontosJogador2.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
            tvVantagemJogador1.visibility = View.GONE
            tvVantagemJogador2.visibility = View.GONE
        }
    }
}