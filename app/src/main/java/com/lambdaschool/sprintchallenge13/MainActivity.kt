package com.lambdaschool.sprintchallenge13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        disposable = makeupService.getMakeup("maybelline")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ makeup ->
                text_view.text = "${makeup[9].name}\n${makeup[3].name}\n${makeup[5].name}"
            }, {t ->
                Log.i("Retrofit - ", "$t", t)
            })
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
