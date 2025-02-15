package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewModel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel

    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityGuestFormBinding.inflate(layoutInflater)
            setContentView(binding.root)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            viewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

            binding.buttonEnviar.setOnClickListener(this)
            binding.radioPresent.isChecked = true

            observe()

            // Configura o botão de voltar
            binding.buttonBack3.setOnClickListener {
                 startActivity(Intent(this, MainActivity::class.java))
            }

            loadData()
        }

        override fun onClick(view: View) {
            if (view.id == R.id.button_enviar) {
                val name = binding.editTextName.text.toString()
                val idade = (binding.editTextAge.text.toString()).toInt()
                val tipoEvento = binding.editTextTipoEvento.text.toString()
                val localEvento = binding.editTextLocalEvento.text.toString()
                val idadeMinimaEvento = (binding.editTextIdadeEvento.text.toString()).toInt()
                val presence = binding.radioPresent.isChecked

                val model = GuestModel(guestId, name, idade, tipoEvento, localEvento, idadeMinimaEvento, presence)
                viewModel.save(model)
            }
        }

        private fun observe(){
            viewModel.guest.observe(this, Observer {
                binding.editTextName.setText(it.name)
                binding.editTextAge.setText(it.idade)
                binding.editTextTipoEvento.setText(it.tipoEvento)
                binding.editTextLocalEvento.setText(it.localEvento)
                binding.editTextIdadeEvento.setText(it.idadeMinimaEvento)

                if(it.presence){
                    binding.radioPresent.isChecked = true
                } else {
                    binding.radioAbsent.isChecked = true
                }
            })

            viewModel.saveGuest.observe(this, Observer {
                if(it.success){
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            })

        }

        private fun loadData() {
            val bundle = intent.extras
            if(bundle != null){
                guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
                viewModel.get(guestId)
            }
        }
    }