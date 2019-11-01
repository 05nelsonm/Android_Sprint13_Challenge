package com.lambdaschool.sprintchallenge13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.sprintchallenge13.adapter.MakeupRecyclerViewAdapter
import com.lambdaschool.sprintchallenge13.retrofit.MakeupApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable
    lateinit var makeupService: MakeupApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeupService = MakeupApi.Factory.create()

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        button_search.setOnClickListener {
            val searchedFor = edit_text_search.text.toString()

            disposable = makeupService.getMakeup(searchedFor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ brand ->
                    recycler_view.adapter = MakeupRecyclerViewAdapter(brand)
                }, { t ->
                    Log.i("Retrofit - ", "$t", t)
                })
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun showList() {

    }
}
