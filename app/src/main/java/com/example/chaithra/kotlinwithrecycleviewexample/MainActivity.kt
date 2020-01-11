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
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.example.chaithra.kotlinwithrecycleviewexample.BookApi.BookApiFetch
import com.example.chaithra.kotlinwithrecycleviewexample.Model.Book
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import java.util.prefs.Preferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Retrofit

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import org.json.JSONException

class MainActivity : AppCompatActivity() {


    val itmes: ArrayList<String> = ArrayList()
    var item = ""
    var prefs: SharedPreferences? = null
    val PREFS_FILENAME = "com.teamtreehouse.colorsarefun.prefs"
    private var myCompositeDisposable: CompositeDisposable? = null
    private var BookList: ArrayList<Book>? = null
    private val BASE_URL = "https://www.googleapis.com/books/v1/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myCompositeDisposable = CompositeDisposable()
       // additems()
        rv_data_list.layoutManager = LinearLayoutManager(this)
        rv_data_list!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rv_data_list.adapter = DataAdpter(itmes, this)
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        val item_name = prefs!!.getString("Item_name", "")
        //itmes.add(item_name);
        rv_data_list.adapter!!.notifyDataSetChanged()
        loadData()

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


//    fun additems() {
//        itmes.add("book")
//        itmes.add("pencil")
//        itmes.add("Pen")
//        itmes.add("Scale")
//        itmes.add("bag")
//        itmes.add("Lunchbox")
//        itmes.add("motorcyle")
//
////
//    }

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

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putCharSequence("savedText", item)

    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Log.i(TAG, "onRestoreInstanceState")

        val userText = savedInstanceState?.getCharSequence("savedText")
        itmes.add("" + userText)

        rv_data_list.adapter!!.notifyDataSetChanged()

    }

    private fun loadData() {

//Define the Retrofit request

        val requestInterface = Retrofit.Builder()

//Set the API’s base URL//

                .baseUrl(BASE_URL)

//Specify the converter factory to use for serialization and deserialization//

                .addConverterFactory(GsonConverterFactory.create())

//Add a call adapter factory to support RxJava return types

                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

//Build the Retrofit instance

                .build().create(BookApiFetch::class.java)

//Add all RxJava disposables to a CompositeDisposable

        myCompositeDisposable?.add(requestInterface.searchBook()

//Send the Observable’s notifications to the main UI thread

                .observeOn(AndroidSchedulers.mainThread())

//Subscribe to the Observer away from the main UI thread

                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse))

    }

    private fun handleResponse(jsonObject: JsonObject) {



        var bookarraylist: java.util.ArrayList<Book>? = java.util.ArrayList()


        try {
            // val bookjsonObject = JsonObject(jsonObject)
            if (jsonObject.has("items")) {
                var bookjsonObject = jsonObject.get("items").asJsonArray
                        //val bookjsonarray = bookjsonObject.get("items")
//
                for (i in 0 until bookjsonObject.size()) {


                    val itemJsonObject = bookjsonObject.get(i).asJsonObject
                    val a=0;
                   val volumeInfoObject = itemJsonObject.get("volumeInfo").asJsonObject

                    val title = volumeInfoObject.get("title").asString

                   var authors = arrayOf("No Authors")

//                    if (!volumeInfoObject.isJsonNull("authors")) {
////                        val authorsArray = volumeInfoObject.getJSONArray("authors")
////                      //  Log.d(LOG_TAG, "authors #" + authorsArray.length())
////                        authors = arrayOfNulls(authorsArray.length())
////                        for (j in 0 until authorsArray.length()) {
////                            authors[j] = authorsArray.getString(j)
////                        }
////                    }
                    bookarraylist!!.add(Book(title, ""))
                    itmes.add(title)
                    rv_data_list.adapter!!.notifyDataSetChanged()
                }
            } else {
                bookarraylist = null
            }

        } catch (e: JSONException) {
            //  Log.d(LOG_TAG, e.toString())
        }


//          return getBookNumber.addBookNumber(bookarraylist)
//         return bookarraylist;

    }


    override fun onDestroy() {
        super.onDestroy()

//Clear  your disposables

        myCompositeDisposable?.clear()

    }
}


