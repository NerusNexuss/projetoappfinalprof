package com.example.convidados.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.convidados.constants.SuccessFailure
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application)

    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    private val _saveGuest = MutableLiveData<SuccessFailure>()
    val saveGuest: LiveData<SuccessFailure> = _saveGuest

    fun save(guest: GuestModel) {
        val successFailure = SuccessFailure(true, "")

        if (guest.name.isBlank()) {
            successFailure.success = false
            successFailure.message = "O nome do convidade não pode estar em branco."
            _saveGuest.value = successFailure
            return
        }

        try {
            if (guest.id == 0) {
                if (repository.insert(guest)) {
                    successFailure.success = true
                    successFailure.message = "Convidado inserido com sucesso!"
                } else {
                    successFailure.success = false
                    successFailure.message = "Erro ao inserir convidado."
                }
            } else {
                if (repository.update(guest)) {
                    successFailure.success = true
                    successFailure.message = "Convidado atualizado com sucesso!"
                } else {
                    successFailure.success = false
                    successFailure.message = "Erro ao atualizar convidado."
                }
            }
        } catch (e: Exception) {
            successFailure.success = false
            successFailure.message = "Erro inesperado: ${e.message}"
        }

        _saveGuest.value = successFailure
    }

    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }

}