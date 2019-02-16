package com.mogalabs.notes.feature.notelist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mogalabs.notes.DEFAULT_ID
import com.mogalabs.notes.R
import com.mogalabs.notes.data.repository.NoteModel
import com.mogalabs.notes.databinding.ActivityMainBinding
import com.mogalabs.notes.feature.addeditnote.AddEditNoteActivity
import com.mogalabs.notes.feature.notelist.viewmodel.NoteListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class NoteListActivity : AppCompatActivity() {

    private val noteListViewModel by viewModel<NoteListViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.adapter = NoteAdapter()
        binding.layoutManager = LinearLayoutManager(this)

        binding.buttonAddNote.setOnClickListener {
            startActivity(AddEditNoteActivity.getIntent(baseContext, DEFAULT_ID))
        }

        binding.recyclerView.setHasFixedSize(true)

        noteListViewModel.getAllNotes().observe(this, Observer<List<NoteModel>> { binding.adapter!!.submitList(it) })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteListViewModel.delete(binding.adapter!!.getNoteAt(viewHolder.adapterPosition))
            }
        }
        ).attachToRecyclerView(recycler_view)

        binding.adapter!!.setOnItemClickListener(object : OnNoteClickListener {
            override fun onNoteClick(note: NoteModel) {
                startActivity(AddEditNoteActivity.getIntent(baseContext, note.id))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            R.id.delete_all_notes -> {
                noteListViewModel.deleteAllNotes()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}