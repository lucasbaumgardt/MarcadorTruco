package com.example.avaliacaotruco

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HistoricoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nomeJ1 = intent.getStringExtra("nomeJ1") ?: "Jogador 1"
        val vitoriasJ1 = intent.getIntExtra("vitoriasJ1", 0)

        val nomeJ2 = intent.getStringExtra("nomeJ2") ?: "Jogador 2"
        val vitoriasJ2 = intent.getIntExtra("vitoriasJ2", 0)

        findViewById<TextView>(R.id.tvHistoricoJ1).text = getString(R.string.historico_jogador, nomeJ1, vitoriasJ1)
        findViewById<TextView>(R.id.tvHistoricoJ2).text = getString(R.string.historico_jogador, nomeJ2, vitoriasJ2)
    }
}