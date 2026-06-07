package com.example.avaliacaotruco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EquipesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_equipes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNomeJ1 = findViewById<EditText>(R.id.etNomeJ1)
        val etNomeJ2 = findViewById<EditText>(R.id.etNomeJ2)

        etNomeJ1.setText(intent.getStringExtra("atualJ1"))
        etNomeJ2.setText(intent.getStringExtra("atualJ2"))

        findViewById<Button>(R.id.btnConfirmar).setOnClickListener {
            val novoNomeJ1 = etNomeJ1.text.toString().ifEmpty { "Jogador 1" }
            val novoNomeJ2 = etNomeJ2.text.toString().ifEmpty { "Jogador 2" }

            val resultadoIntent = Intent().apply {
                putExtra("novo_nomeJ1", novoNomeJ1)
                putExtra("novo_nomeJ2", novoNomeJ2)
            }

            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }
}