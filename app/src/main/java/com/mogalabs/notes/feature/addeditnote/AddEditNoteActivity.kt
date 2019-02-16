package com.mogalabs.notes.feature.addeditnote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mogalabs.notes.DEFAULT_ID
import com.mogalabs.notes.R
import com.mogalabs.notes.data.repository.NoteModel
import com.mogalabs.notes.databinding.ActivityAddNoteBinding
import com.mogalabs.notes.feature.addeditnote.viewmodel.NoteItemViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class AddEditNoteActivity : AppCompatActivity() {

    private val viewModel by viewModel<NoteItemViewModel>()
    lateinit var binding: ActivityAddNoteBinding

    companion object {
        private const val EXTRA_ID = "com.mogalabs.tagnotes.EXTRA_ID"

        fun getIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, AddEditNoteActivity::class.java)
            intent.putExtra(AddEditNoteActivity.EXTRA_ID, id)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        binding = activityAddNoteBinding()

        if (savedInstanceState == null) {
            viewModel.onCreate(intent.getIntExtra(EXTRA_ID, DEFAULT_ID))
        }

        val noteLiveData = viewModel.getNote()
        noteLiveData.observe(this, Observer<NoteModel> { binding.note = it })
        binding.numberPickerPriority.maxValue = 10
        binding.note = noteLiveData.value
        title = viewModel.getTitle()
    }

    private fun activityAddNoteBinding(): ActivityAddNoteBinding {
        val binding: ActivityAddNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        return binding
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        binding.note?.let {
            if (it.title.isBlank() || it.description.isBlank()) {
                Toast.makeText(this, "Can not insert empty note!", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insertOrUpdate(it)

                finish()
            }
        }
    }
}