package com.lambdaschool.sprintchallenge13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.sprintchallenge13.adapter.MakeupRecyclerViewAdapter
import com.lambdaschool.sprintchallenge13.retrofit.MakeupApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

    private lateinit var disposable: Disposable

    @Inject
    lateinit var makeupService: MakeupApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.inject(this)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        button_search.setOnClickListener {
            val searchedFor = edit_text_search.text.toString()

            if (searchedFor.isNotEmpty()) {

                disposable = makeupService.getMakeup(searchedFor)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ brand ->
                        if (brand.isNotEmpty()) {
                            recycler_view.adapter = MakeupRecyclerViewAdapter(brand)
                        } else {
                            Toast.makeText(this, "Brand not found", Toast.LENGTH_SHORT).show()
                        }
                    }, { t ->
                        Log.i("Retrofit - ", "$t", t)
                    })
            } else {
                Toast.makeText(this, "Please enter a brand", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun showList() {

    }
}
