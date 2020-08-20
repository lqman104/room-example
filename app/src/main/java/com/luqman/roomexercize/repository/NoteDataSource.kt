package com.luqman.roomexercize.repository

import com.luqman.roomexercize.repository.model.Note

interface NoteDataSource {

    suspend fun getAllNotes(): List<Note>

    suspend fun insertNotes(note: Note)

    suspend fun deleteNotes(note: Note)

}