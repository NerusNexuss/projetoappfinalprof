package com.example.convidados.model

class GuestModel(
    val id: Int,
    var name: String,
    var idade: Int,
    var tipoEvento: String,
    var localEvento: String,
    val idadeMinimaEvento: Int,
    var presence: Boolean) {
}