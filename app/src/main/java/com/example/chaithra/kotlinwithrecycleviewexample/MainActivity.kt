package com.example.chaithra.kotlinwithrecycleviewexample

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import java.util.prefs.Preferences


class MainActivity : AppCompatActivity() {


    val itmes: ArrayList<String> = ArrayList()
    var item = ""
    var prefs: SharedPreferences? = null
    val PREFS_FILENAME = "com.teamtreehouse.colorsarefun.prefs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        additems()
        rv_data_list.layoutManager = LinearLayoutManager(this)
        rv_data_list!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rv_data_list.adapter = DataAdpter(itmes, this)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        val item_name = prefs!!.getString("Item_name" ,"")
           itmes.add(item_name);
            rv_data_list.adapter!!.notifyDataSetChanged()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.add -> {
            onAddClick()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    fun additems() {
        itmes.add("book")
        itmes.add("pencil")
        itmes.add("Pen")
        itmes.add("Scale")
        itmes.add("bag")
        itmes.add("Lunchbox")
        itmes.add("motorcyle")

//
    }

    fun onAddClick() {

        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Add item")
        //show dialog
        val mAlertDialog = mBuilder.show()

        mDialogView.addButton.setOnClickListener {

            item = mDialogView.dialogNameEt.text.toString()


            val editor = prefs!!.edit()
            editor.putString("Item_name", item)
            editor.apply()

            itmes.add(item)

            rv_data_list.adapter!!.notifyDataSetChanged()

            mAlertDialog.dismiss();

        }
        mDialogView.cancelBtn.setOnClickListener {



            mAlertDialog.dismiss();

        }
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putCharSequence("savedText", item)

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
                // Log.i(TAG, "onRestoreInstanceState")

        val userText = savedInstanceState?.getCharSequence("savedText")
        itmes.add(""+userText)

        rv_data_list.adapter!!.notifyDataSetChanged()

    }
}

