package com.dev.vishaltraining.mytodo.screen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev.vishaltraining.mytodo.R
import com.dev.vishaltraining.mytodo.data.database.DatabaseHelper
import com.dev.vishaltraining.mytodo.interaces.ActionListener
import com.dev.vishaltraining.mytodo.model.Tasks
import kotlinx.android.synthetic.main.rv_todo_add_update.view.*
import kotlinx.android.synthetic.main.rv_todo_item.*
import kotlinx.android.synthetic.main.rv_todo_item.view.*

class ToDoAdapter(private var taskArrayList: ArrayList<Tasks>, private var context: Context,private var actionListener: ActionListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    var dbHelper: DatabaseHelper? =   DatabaseHelper(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_todo_item,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        
        if (holder.adapterPosition >= 0)
        {
            when(holder)
            {
                is TaskViewHolder -> holder.setData()
            }
        }


    }

    override fun getItemCount(): Int  = taskArrayList.size

    inner class TaskViewHolder(view: View) :RecyclerView.ViewHolder(view){
        var name = view.tvName
        var desc :TextView = view.tvDesc
        var clRootLIstItem = view.clRootLIstItem
        val task : Tasks
        get() {
           return if (adapterPosition>=0) taskArrayList[adapterPosition] else Tasks()
        }
        
        fun setData()
        {
            name.text = task.name.toString()
            desc.text = task.desc.toString()

            if (task.completed == "Y")
                clRootLIstItem.background =  ContextCompat.getDrawable(context,R.color.colorSuccess)
            else
                clRootLIstItem.background =  ContextCompat.getDrawable(context,R.color.colorUnSuccess)

        }

        init {
            view.setOnClickListener {
                if (adapterPosition>=0)
              actionListener.onPosClick(adapterPosition)
            }
        }

    }

    fun UpdateData(taskArrayList: ArrayList<Tasks>)
    {
        this.taskArrayList = taskArrayList
        notifyDataSetChanged()
    }


}