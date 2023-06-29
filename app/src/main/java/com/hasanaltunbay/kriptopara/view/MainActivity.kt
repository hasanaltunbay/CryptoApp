package com.hasanaltunbay.kriptopara.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hasanaltunbay.kriptopara.adapter.Adapter
import com.hasanaltunbay.kriptopara.databinding.ActivityMainBinding
import com.hasanaltunbay.kriptopara.model.KriptoModel
import com.hasanaltunbay.kriptopara.service.KriptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),Adapter.Listener {

    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var kritoModel: ArrayList<KriptoModel>? = null
    private var recyclerViewAdapter : Adapter? = null

    //Disposable
    private var compositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.title="Kripta Paralar"

        compositeDisposable = CompositeDisposable()

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()

    }

    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(KriptoAPI::class.java)


        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))


    }

    private fun handleResponse(kriptoList : List<KriptoModel>){
        kritoModel = ArrayList(kriptoList)

        kritoModel?.let {
            recyclerViewAdapter = Adapter(it,this@MainActivity)
            binding.recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(cryptoModel: KriptoModel) {
        Toast.makeText(this,"Tıklandı : ${cryptoModel.currency}",Toast.LENGTH_SHORT ).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }

}