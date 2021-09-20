package id.roogry.cobafileinternal

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.roogry.cobafileinternal.databinding.ActivityMainBinding
import id.roogry.cobafileinternal.helper.FileHelper
import id.roogry.cobafileinternal.model.FileModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNew.setOnClickListener(this)
        binding.btnOpen.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnNew -> newFile()
            R.id.btnOpen -> showList()
            R.id.btnSave -> saveFile()
        }
    }

    private fun newFile() {
        binding.edtTitle.setText("")
        binding.edtFile.setText("")
        Toast.makeText(this, "Note cleared", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Open a note")
        builder.setItems(items) { dialog, item ->
            loadData(items[item].toString())
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.edtTitle.setText(fileModel.filename)
        binding.edtFile.setText(fileModel.data)
        Toast.makeText(
            this,
            "Load " + fileModel.filename + " data",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun saveFile() {
        when {
            binding.edtTitle.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Title cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
            binding.edtFile.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Content must have a value",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                val title = binding.edtTitle.text.toString()
                val text = binding.edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(
                    this,
                    "Note " + fileModel.filename + " has saved",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}