package com.dev.vishaltraining.mytodo.screen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.vishaltraining.mytodo.R
import com.dev.vishaltraining.mytodo.data.database.DatabaseHelper
import com.dev.vishaltraining.mytodo.interaces.ActionListener
import com.dev.vishaltraining.mytodo.model.Tasks
import com.dev.vishaltraining.mytodo.screen.adapter.ToDoAdapter
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.rv_todo_item.*


class TodoListActivity : AppCompatActivity(),ActionListener {

    var dbHelper: DatabaseHelper? = null
    private lateinit var toDoAdapter : ToDoAdapter
    var listTasks: ArrayList<Tasks> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        dbHelper = DatabaseHelper(this@TodoListActivity)

        setData()

        //initViews()
        initOperations()


    }
private fun initDB(){
    dbHelper = DatabaseHelper(this@TodoListActivity)
}

    private fun setData(){

        listTasks = dbHelper!!.task()
        Toast.makeText(this, listTasks.size.toString(), Toast.LENGTH_SHORT).show()
        rvTodo.apply {
            toDoAdapter = ToDoAdapter(listTasks,context,this@TodoListActivity)
            adapter = toDoAdapter
            //adapter = ToDoAdapter(listTasks, context)
            layoutManager = LinearLayoutManager(this@TodoListActivity,RecyclerView.VERTICAL,false)
            adapter!!.notifyDataSetChanged()
        }

       // srlTodo?.setOnRefreshListener { refresh() }
    }


  /*  private fun initViews(){
    rvTodo.apply {
        layoutManager = LinearLayoutManager(applicationContext)
        adapter = ToDoAdapter(listTasks, context)

    }
    //get the latest data
        refresh()
    }
    private fun refresh(){
        initDB()

        listTasks = dbHelper!!.task()
        adpater?.notifyDataSetChanged()
        //srlTodo?.isRefreshing = false
    }*/
    private fun initOperations(){
        fab.setOnClickListener { view ->
            val i  = Intent(applicationContext, AddOrUpdate::class.java)
            i.putExtra("Mode", "A")
            startActivity(i)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            val dialog = AlertDialog.Builder(this)
                    .setTitle("Info")
                    .setMessage("Click 'YES' Delete All Tasks.")
                    .setPositiveButton("YES"){ dialog, i ->
                        dbHelper?.deleteAllTasks()


                        dialog.dismiss()

                    }
                    .setNegativeButton("NO"){ dialog, i ->
                        dialog.dismiss()
                    }
            dialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setData()
      //  toDoAdapter.UpdateData(listTasks)
    }

    override fun onPosClick(position: Int, extraData: Any) {
        val i = Intent(this,AddOrUpdate::class.java)
        i.putExtra("Mode","E")
        i.putExtra("Id",listTasks[position].id)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
        srlTodo.isRefreshing = false
    }


}