package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.convidados.constants.DataBaseConstants

class EventDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "eventdb"
        private const val VERSION = 3

    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            Log.d("EventDataBase", "Criando banco de dados...")
            db.execSQL(
                "CREATE TABLE " +
                        DataBaseConstants.EVENTO.TABLE_NAME + " (" +
                        DataBaseConstants.EVENTO.COLUMNS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DataBaseConstants.EVENTO.COLUMNS.NOME + " TEXT, " +
                        DataBaseConstants.EVENTO.COLUMNS.ENDERECO + " TEXT, " +
                        DataBaseConstants.EVENTO.COLUMNS.IDADE + " INTEGER, " +
                        DataBaseConstants.EVENTO.COLUMNS.DATA + " TEXT);"
            )
        } catch (e: Exception) {
            Log.e("EventDataBase", "Erro ao criar banco de dados: ${e.message}")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }
}
