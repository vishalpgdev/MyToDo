package com.dev.vishaltraining.mytodo.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.dev.vishaltraining.mytodo.model.Tasks

/*

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {
    override fun onCreate(db: SQLiteDatabase?) {
    val CREATE_TABLE  = "CREATE TABLE $TABLE_NAME("+
            ID+" INTEGER PRIMARY KEY,"+
            NAME + " TEXT," +
            DESC+ " TEXT," +
            COMPLETED + " TEXT);"
        db?.execSQL(CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    */
/***below method is used for add data to the database. *//*

    fun addTask(tasks: Tasks):Boolean{
        val db = this@DatabaseHelper.writableDatabase
        val values = ContentValues()
        values.put(NAME, tasks.name)
        values.put(DESC, tasks.desc)
        values.put(COMPLETED, tasks.completed)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success")!= -1)
    }
    */
/**below method is user to read all or one data from table.*//*

    */
/**Here, Database RETURNS THE DATA RESPECTIVE TI THE ID.*//*

    fun getTask(_id: Int):Tasks{
        val tasks =Tasks()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor !=null)
        {
            cursor.moveToFirst()
            while (cursor.moveToNext()){
                tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                tasks.name = cursor.getString(cursor.getColumnIndex(NAME))
                tasks.desc = cursor.getString(cursor.getColumnIndex(DESC))
                tasks.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
            }
        }
        cursor.close()
        return tasks
    }
    */
/**Here, the DB returns the all the data.Cursor is used to retrieve the data from SQLite.*//*


    val tasks  : List<Tasks>
    get() {
        val taskList = ArrayList<Tasks>()
        val db = writableDatabase
        val selectQuery = " SELECT * FROM $TABLE_NAME"
        val cusr = db.rawQuery(selectQuery, null)
        if (cusr != null){
            cusr.moveToFirst()
            while (cusr.moveToNext()){
                val task = Tasks()
                task.id = Integer.parseInt(cusr.getString(cusr.getColumnIndex(ID)))
                task.name = cusr.getString(cusr.getColumnIndex(NAME))
                task.desc = cusr.getString(cusr.getColumnIndex(DESC))
                task.completed = cusr.getString(cusr.getColumnIndex(COMPLETED))
                taskList.add(task)
            }
        }
        cusr.close()
        return taskList
    }

    */
/**Update data and Here, the DB returns the ID of the last updated data as the output of the update method.*//*

    fun updateTask(tasks: Tasks):Boolean{
        val db = writableDatabase
        val v = ContentValues()
        v.put(NAME, tasks.name)
        v.put(DESC, tasks.desc)
        v.put(COMPLETED, tasks.completed)
        val _success = db.update(TABLE_NAME, v, "$ID=?", arrayOf(tasks.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
    */
/***Here, the DB returns the ID of the last deleted data as the output of the delete method.*//*

    fun deleteTask(_id: Int):Boolean{
        val db = writableDatabase
        val _success = db.delete(TABLE_NAME, "$ID=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
    */
/**delete all task.*//*

    fun deleteAllTask():Boolean{
        val db = writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
    companion object{

        var actionListener: WifiP2pManager.ActionListener?= null
        private const val DB_NAME = "${Constant.APP_NAME_SIMPLE}.db"
        private const val DB_VER= 1
        private const val TABLE_NAME = "Tasks"
        private const val ID = "Id"
        private const val NAME  = "Name"
        private const val DESC = "Desc"
        private const val COMPLETED = "Completed"
    }

}*/
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT, $DESCR TEXT,$COMPLETED TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addTask(tasks: Tasks): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, tasks.name)
        values.put(DESCR, tasks.desc)
        values.put(COMPLETED, tasks.completed)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedId", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getTask(_id: Int): Tasks {
        val tasks = Tasks()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        tasks.name = cursor.getString(cursor.getColumnIndex(NAME))
        tasks.desc = cursor.getString(cursor.getColumnIndex(DESCR))
        tasks.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
        cursor.close()
        return tasks
    }

    fun task(): ArrayList<Tasks> {
        val taskList = ArrayList<Tasks>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val tasks = Tasks()
                    tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    tasks.name = cursor.getString(cursor.getColumnIndex(NAME))
                    tasks.desc = cursor.getString(cursor.getColumnIndex(DESCR))
                    tasks.completed = cursor.getString(cursor.getColumnIndex(COMPLETED))
                    taskList.add(tasks)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return taskList
    }

    fun updateTask(tasks: Tasks): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, tasks.name)
        values.put(DESCR, tasks.desc)
        values.put(COMPLETED, tasks.completed)
        val _success = db.update(TABLE_NAME, values, "$ID=?", arrayOf(tasks.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteTask(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, "$ID=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllTasks(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "MyTasks"
        private const val TABLE_NAME = "Tasks"
        private const val ID = "Id"
        private const val NAME = "Name"
        private const val DESCR = "Descr"
        private const val COMPLETED = "Completed"
    }
}