package com.example.aviatickets.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.entity.Offer
import com.example.aviatickets.model.network.ApiClient
import com.example.aviatickets.model.service.FakeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OfferListFragment : Fragment() {

    private lateinit var myAdapter: OfferListAdapter
    private lateinit var rvMain: RecyclerView
    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter(requireContext())
    }

    private val data: List<Offer> = emptyList() // Initialize 'data' if needed

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMain = binding.offerList

        rvMain.layoutManager = LinearLayoutManager(context)
        myAdapter = context?.let { OfferListAdapter(it) }!!
        rvMain.adapter = myAdapter
        getAllData()

        setupUI()
        adapter.updateItems(FakeService.offerList)
    }

    private fun setupUI() {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        adapter.sortByPrice()
                    }

                    R.id.sort_by_duration -> {
                        adapter.sortByDuration()
                    }
                }
            }
        }
    }
    private fun getAllData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/estharossa/fake-api-demo/offer_list/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)

        val retroData = retrofit.getData()

        retroData.enqueue(object : Callback<List<Offer>> {
            override fun onResponse(
                call: Call<List<Offer>>,
                response: Response<List<Offer>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data != null) {
                        myAdapter.updateItems(data)
                        Log.d("data", data.toString())
                    } else {
                        Log.e("API Response", "Response body is null")
                    }
                } else {
                    Log.e("API Response", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                Log.e("API Failure", "Failed to fetch data: ${t.message}")
            }
        })
    }
}