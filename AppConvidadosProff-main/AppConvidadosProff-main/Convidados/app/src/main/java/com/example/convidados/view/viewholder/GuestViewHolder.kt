package com.example.convidados.view.viewholder

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog.*
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.OnGuestListener

class GuestViewHolder(
    private val bind: RowGuestBinding,
    private val listener: OnGuestListener
) : RecyclerView.ViewHolder(bind.root) {

    @SuppressLint("SetTextI18n")
    fun bind(guest: GuestModel) {
        bind.textName.text = "Nome: ${guest.name}, cargo: ${guest.cargo}, idade: ${guest.idade}"

        bind.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        bind.textName.setOnLongClickListener {
            Builder(itemView.context)
                .setTitle("Remoção de convidado")
                .setMessage("Deseja remover?")
                .setPositiveButton("Sim"
                ) { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("Não", null)
                .create()
                .show()
            true
        }
    }

}