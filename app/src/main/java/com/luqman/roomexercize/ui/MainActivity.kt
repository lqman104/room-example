package com.luqman.roomexercize.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.luqman.roomexercize.R
import com.luqman.roomexercize.repository.NoteDataSource
import com.luqman.roomexercize.repository.NoteRepository
import com.luqman.roomexercize.repository.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NoteFormDialogFragment.NoteCallback {

    private lateinit var repository: NoteDataSource
    private var recyclerView: RecyclerView? = null
    private var fab: FloatingActionButton? = null
    private var adapter: NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        init()
        initData()
        initListener()
    }

    private fun init() {
        repository = NoteRepository()

        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)

        adapter = NoteAdapter(object : NoteAdapter.OnItemClicked {
            override fun onDelete(note: Note) {
                deleteData(note)
            }
        })
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        setToolbarTitle()
    }

    private fun initData() {
        getData()
    }

    private fun initListener() {
        fab?.setOnClickListener {
            showDialogAdd()
        }
    }

    private fun setToolbarTitle() {
        supportActionBar?.title = getString(R.string.note_caption)
    }

    private fun showDialogAdd() {
        NoteFormDialogFragment().apply {
            isCancelable = false
            show(supportFragmentManager, NoteFormDialogFragment::class.java.simpleName)
        }
    }

    override fun onSave() {
        getData()
    }

    private fun getData() {
        CoroutineScope(Dispatchers.Main).launch {
            val list = repository.getAllNotes()
            adapter?.submitList(list)
        }
    }

    private fun deleteData(note: Note) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.deleteNotes(note)

            getData()
        }
    }


}