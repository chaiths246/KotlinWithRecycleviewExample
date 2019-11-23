package com.example.chaithra.kotlinwithrecycleviewexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.view.*

class MainActivity : AppCompatActivity() {


    val itmes: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        additems()


        rv_animal_list.layoutManager = LinearLayoutManager(this)


        rv_animal_list!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rv_animal_list.adapter = DataAdpter(itmes, this)

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

            val item = mDialogView.dialogNameEt.text.toString()
            itmes.add(item)

            rv_animal_list.adapter!!.notifyDataSetChanged()

            mAlertDialog.dismiss();

        }
        mDialogView.cancelBtn.setOnClickListener {



            mAlertDialog.dismiss();

        }
    }
}

