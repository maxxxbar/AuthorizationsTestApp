package com.worldshine.authorizationstestapp.ui.mainactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.worldshine.authorizationstestapp.api.ApiService
import com.worldshine.authorizationstestapp.pojo.Response
import com.worldshine.authorizationstestapp.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var compositeDisposable = CompositeDisposable()
    private val data = SingleLiveEvent<Response>()

    val error = SingleLiveEvent<String>()

    fun getWeather(): LiveData<Response> {
        val disposable = ApiService.create.getForecast()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.let {
                    data.value = it
                }
            }, {
                error.value = it.localizedMessage
            })
        compositeDisposable.add(disposable)
        return data
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}