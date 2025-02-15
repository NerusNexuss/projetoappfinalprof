package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.convidados.constants.DataBaseConstants

class GuestDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "guestdb"
        private const val VERSION = 3

    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            Log.d("GuestDataBase", "Criando banco de dados...")
            db.execSQL(
                "CREATE TABLE " +
                    DataBaseConstants.GUEST.TABLE_NAME + " (" +
                    DataBaseConstants.GUEST.COLUMNS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DataBaseConstants.GUEST.COLUMNS.NAME + " TEXT, " +
                    DataBaseConstants.GUEST.COLUMNS.CARGO + " TEXT, " +
                    DataBaseConstants.GUEST.COLUMNS.IDADE + " FLOAT, " +
                    DataBaseConstants.GUEST.COLUMNS.PRESENCE + " INTEGER);"
            )
        } catch (e: Exception) {
            Log.e("GuestDataBase", "Erro ao criar banco de dados: ${e.message}")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE Guest ADD COLUMN cargo TEXT")
            db?.execSQL("ALTER TABLE Guest ADD COLUMN idade FLOAT")
        }
    }
}