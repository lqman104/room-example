package com.luqman.roomexercize.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity)

    @Query("SELECT * FROM notes_table")
    fun getAllData(): List<NoteEntity>

    @Delete(entity = NoteEntity::class)
    fun deleteItem(note: NoteEntity)

}