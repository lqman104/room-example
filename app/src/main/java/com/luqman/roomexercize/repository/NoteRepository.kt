package com.luqman.roomexercize.repository

import com.luqman.roomexercize.database.AppDatabase
import com.luqman.roomexercize.database.NoteEntity
import com.luqman.roomexercize.repository.model.Note
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class NoteRepository : NoteDataSource {

    override suspend fun getAllNotes(): List<Note> {
        val list: MutableList<Note> = mutableListOf()

        withContext(IO) {
            val response = AppDatabase.instance?.noteDao()?.getAllData()

            response?.forEach { item ->
                val note = Note(
                    item.id,
                    item.note
                )

                list.add(note)
            }
        }

        return list
    }

    override suspend fun insertNotes(note: Note) {
        withContext(IO) {
            val request = NoteEntity(
                note = note.note
            )

            AppDatabase.instance?.noteDao()?.insert(request)
        }
    }

    override suspend fun deleteNotes(note: Note) {
        withContext(IO) {
            val request = NoteEntity(
                id = note.id,
                note = note.note
            )

            AppDatabase.instance?.noteDao()?.deleteItem(request)
        }
    }
}