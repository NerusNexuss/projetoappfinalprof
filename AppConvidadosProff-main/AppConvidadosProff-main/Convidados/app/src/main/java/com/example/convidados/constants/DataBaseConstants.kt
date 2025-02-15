package com.example.convidados.constants

class DataBaseConstants {

    object GUEST {
        const val ID = "guestid"
        const val TABLE_NAME = "Guest"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val CARGO = "cargo"
            const val IDADE = "idade"
            const val PRESENCE = "presence"
        }
    }
  object EVENTO {
      const val ID = "eventid"
      const val TABLE_NAME = "event"

      object COLUMNS {
          const val ID = "id"
          const val NOME = "NOME"
          const val ENDERECO = "ENDERECO"
          const val IDADE = "idade"
          const val DATA = "data"
      }

  }
}