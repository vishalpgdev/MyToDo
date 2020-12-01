package com.dev.vishaltraining.mytodo.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.dev.vishaltraining.mytodo.R
import com.dev.vishaltraining.mytodo.data.database.DatabaseHelper
import com.dev.vishaltraining.mytodo.model.Tasks
import kotlinx.android.synthetic.main.rv_todo_add_update.*
import kotlinx.android.synthetic.main.rv_todo_item.*

class AddOrUpdate : AppCompatActivity() {
    var dbHelper : DatabaseHelper? = null
    var isEditMode = false
    var listTasks: List<Tasks> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_todo_add_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initDB()
        initOperations()
    }

    private fun initDB(){

        dbHelper = DatabaseHelper(this@AddOrUpdate)
        btnDeleted.visibility = View.INVISIBLE
        if (intent != null && intent.getStringExtra("Mode") == "E")
        {
            isEditMode = true
            val tasks : Tasks? = dbHelper?.getTask(intent.getIntExtra("Id",0))
            edtInName.setText(tasks?.name)
            edtInDesc.setText(tasks?.desc)
            chbAddComplete.isChecked = tasks?.completed == "Y"
            btnDeleted.visibility = View.VISIBLE
        }
    }



    private fun initOperations(){
        btnSave.setOnClickListener {
            var success = false
            if (!isEditMode){
                val task = Tasks()
                task.name = edtInName.text.toString()
                task.desc = edtInDesc.text.toString()
                if (chbAddComplete.isChecked) task.completed = "Y" else task.completed = "N"
                success = dbHelper?.addTask(task)as Boolean
            }else{
                val task = Tasks()
                task.id = intent.getIntExtra("Id",0)
                task.name = edtInName.text.toString()
                task.desc = edtInDesc.text.toString()
                if (chbAddComplete.isChecked) task.completed = "Y" else task.completed = "N"
                success = dbHelper?.updateTask(task) as Boolean
            }

            if (success)
                finish()
        }
        btnDeleted.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' Delete the Task.")
                .setPositiveButton("YES") { dialog, i ->
                    val success = dbHelper?.deleteTask(intent.getIntExtra("Id", 0)) as Boolean
                    if (success){
                        val i = Intent(this,TodoListActivity::class.java)
                        startActivity(i)
                        initDB()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("NO") { dialog, i ->
                    dialog.dismiss()
                }
            dialog.show()
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       val id =  item.itemId
        if (id == android.R.id.home)
        {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}