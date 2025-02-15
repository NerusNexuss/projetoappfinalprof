package com.example.convidados.constants

class DataBaseConstants {

    object GUEST {
        const val ID = "guestid"
        const val TABLE_NAME = "Guest"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val IDADE = "idade"
            const val TIPO_EVENTO = "tipoEvento"
            const val LOCAL_EVENTO = "localEvento"
            const val IDADE_MINIMA_EVENTO = "idadeMinimaEvento"
            const val PRESENCE = "presence"
        }
    }
}