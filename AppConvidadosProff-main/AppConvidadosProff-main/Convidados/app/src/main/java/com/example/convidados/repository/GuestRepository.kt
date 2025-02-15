package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    // inserir.
    fun insert(guest: GuestModel): Boolean {

        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.CARGO, guest.cargo)
            values.put(DataBaseConstants.GUEST.COLUMNS.IDADE, guest.idade)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val result = db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
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
    fun update(guest: GuestModel): Boolean {

        return try {
            val db = guestDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()

            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.CARGO, guest.cargo)
            values.put(DataBaseConstants.GUEST.COLUMNS.IDADE, guest.idade)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            val result = db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
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
            val db = guestDataBase.writableDatabase
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val result = db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
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
    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null
        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.CARGO,
                DataBaseConstants.GUEST.COLUMNS.IDADE,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args, null, null, null
            )

            if(cursor != null && cursor.count > 0){
                while(cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.NAME)))
                    val cargo =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.CARGO)))
                    val idade =
                        cursor.getFloat(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.IDADE)))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.PRESENCE)))
                    guest = GuestModel(id, name, cargo, idade,presence == 1)
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return guest
        }
        return guest
    }

    // obter todos.
    @SuppressLint("Recycle", "Range")
    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.CARGO,
                DataBaseConstants.GUEST.COLUMNS.IDADE,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
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
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.NAME)))
                    val cargo =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.CARGO)))
                    val idade =
                        cursor.getFloat(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.IDADE)))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.PRESENCE)))
                    val guest = GuestModel(id, name, cargo, idade,presence == 1)
                    list.add(guest)
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getPresence(): List<GuestModel>{

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.CARGO,
                DataBaseConstants.GUEST.COLUMNS.IDADE,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor =
                db.rawQuery("SELECT id, name, cargo, idade, presence FROM Guest WHERE presence == 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.NAME)))
                    val cargo =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.CARGO)))
                    val idade =
                        cursor.getFloat(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.IDADE)))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.PRESENCE)))
                    val guest = GuestModel(id, name, cargo, idade,presence == 1)
                    list.add(guest)
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getAbsent(): List<GuestModel>{

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.CARGO,
                DataBaseConstants.GUEST.COLUMNS.IDADE,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor =
                db.rawQuery("SELECT id, name, cargo, idade, presence FROM Guest WHERE presence == 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.NAME)))
                    val cargo =
                        cursor.getString(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.CARGO)))
                    val idade =
                        cursor.getFloat(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.IDADE)))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex((DataBaseConstants.GUEST.COLUMNS.PRESENCE)))
                    val guest = GuestModel(id, name, cargo, idade,presence == 1)
                    list.add(guest)
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }

}