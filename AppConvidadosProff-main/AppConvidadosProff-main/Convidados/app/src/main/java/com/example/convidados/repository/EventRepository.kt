package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.media.metrics.Event
import android.util.Log
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.EventModel
import com.example.convidados.model.GuestModel

class EventRepository private constructor(context: Context) {

    private val eventDataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: EventRepository

        fun getInstance(context: Context): EventRepository {
            if (!Companion::repository.isInitialized) {
                repository = EventRepository(context)
            }
            return repository
        }
    }

    // inserir.
    fun insert(event:EventModel): Boolean {

        return try {
            val db = eventDataBase.writableDatabase


            val values = ContentValues()
            values.put(DataBaseConstants.EVENTO.COLUMNS.NOME, event.eventoNome)
            values.put(DataBaseConstants.EVENTO.COLUMNS.ENDERECO, event.endereco)
            values.put(DataBaseConstants.EVENTO.COLUMNS.IDADE, event.idadeMinima)
            values.put(DataBaseConstants.EVENTO.COLUMNS.DATA,event.data)

            val result = db.insert(DataBaseConstants.EVENTO.TABLE_NAME, null, values)
            if (result != -1L) {
                Log.d("GuestRepository", "Convidado inserido com sucesso!")
                true
            } else {
                Log.e("GuestRepository", "Falha ao inserir convidado.")
                false
            }

        } catch (e: Exception) {
            false
        }
    }

    // atualizar.
    fun update(event: EventModel): Boolean {

        return try {
            val db = eventDataBase.writableDatabase



            val values = ContentValues()

            values.put(DataBaseConstants.EVENTO.COLUMNS.NOME, event.eventoNome)
            values.put(DataBaseConstants.EVENTO.COLUMNS.ENDERECO, event.endereco)
            values.put(DataBaseConstants.EVENTO.COLUMNS.IDADE, event.idadeMinima)
            values.put(DataBaseConstants.EVENTO.COLUMNS.DATA,event.data)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(event.id.toString())

            val result = db.update(DataBaseConstants.EVENTO.TABLE_NAME, values, selection, args)
            if (result != -1) {
                Log.d("GuestRepository", "Convidado atualizado com sucesso!")
                true
            } else {
                Log.e("GuestRepository", "Falha ao atualizar atualizar.")
                false
            }

        } catch (e: Exception) {
            false
        }

    }

    // deletar.
    fun delete(id: Int): Boolean {

        return try {
            val db = eventDataBase.writableDatabase
            val selection = DataBaseConstants.EVENTO.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val result = db.delete(DataBaseConstants.EVENTO.TABLE_NAME, selection, args)
            if (result != -1) {
                Log.d("GuestRepository", "Convidado deletado com sucesso!")
                true
            } else {
                Log.e("GuestRepository", "Falha ao deletar o convidado.")
                false
            }

        } catch (e: Exception) {
            false
        }

    }

    // obter 1.
    @SuppressLint("Recycle", "Range")
    fun get(id: Int): EventModel? {

        var event: EventModel? = null
        try {
            val db = eventDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.EVENTO.COLUMNS.ID,
                DataBaseConstants.EVENTO.COLUMNS.NOME,
                DataBaseConstants.EVENTO.COLUMNS.ENDERECO,
                DataBaseConstants.EVENTO.COLUMNS.IDADE
            )

            val selection = DataBaseConstants.EVENTO.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.EVENTO.TABLE_NAME, projection, selection, args, null, null, null
            )

            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()) {
                    val NOME =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.NOME)))
                    val ENDERECO =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.ENDERECO)))
                    val idade =
                        cursor.getFloat(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.IDADE)))
                    val DATA =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.DATA)))
                    event = EventModel(id,NOME, ENDERECO, idade,DATA)
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return event
        }
        return event
    }

    // obter todos.
    @SuppressLint("Recycle", "Range")
    fun getAll(): List<EventModel> {

        val list = mutableListOf<EventModel>()

        try {
            val db = eventDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.EVENTO.COLUMNS.ID,
                DataBaseConstants.EVENTO.COLUMNS.NOME,
                DataBaseConstants.EVENTO.COLUMNS.ENDERECO,
                DataBaseConstants.EVENTO.COLUMNS.IDADE,
                DataBaseConstants.EVENTO.COLUMNS.DATA
            )

            val cursor = db.query(
                DataBaseConstants.EVENTO.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.EVENTO.COLUMNS.ID))
                    val nome =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.NOME)))
                    val endereco =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.ENDERECO)))
                    val idade =
                        cursor.getFloat(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.IDADE)))
                    val data =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.EVENTO.COLUMNS.DATA)))
                    val event = EventModel(id, nome, endereco, idade,data)
                    list.add(event)
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }



}