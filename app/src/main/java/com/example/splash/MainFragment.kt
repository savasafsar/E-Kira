package com.example.splash

import android.os.Bundle
import android.view.*
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splash.api.models.IlanModel
import com.example.splash.databinding.FragmentMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainFragment : Fragment() {
    private lateinit var adventAdapter: RecyclerViewAdapter
    private lateinit var binding: FragmentMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adventArrayList: ArrayList<Advent>
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        db = Firebase.firestore
        adventArrayList = ArrayList<Advent>()
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adventAdapter = RecyclerViewAdapter(adventArrayList)
        binding.recyclerView.adapter = adventAdapter

        return binding.root


    }

    fun getData() {
        db.collection("Advents").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (value != null) {
                        if (!value.isEmpty) {

                            val documents = value.documents

                            adventArrayList.clear()
                            for (document in documents) {
                                val comment = document.get("Aciklama") as String
                                val baslik = document.get("Baslik") as String
                                val downloadUrl = document.get("downloadUrl") as String
                                val kirafiyati = document.get("KiraFiyati") as String
                                val periyot = document.get("Periyot") as String
                                val cities = document.get("cities") as String
                                val districts = document.get("districts") as String
                                val quarters = document.get("quarters") as String
                                val towns = document.get("towns") as String

                                val advent = Advent(
                                    comment, baslik, kirafiyati,
                                    periyot, cities, districts, downloadUrl,
                                    quarters, towns
                                )

                                adventArrayList.add(advent)


                            }
                            adventAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        val card = view.findViewById<TextView>(R.id.submit)

        //arguments?.let {
          //  val ilanPeriyodu = MainFragmentArgs.fromBundle(it).periyot
            //card.text = ilanPeriyodu

        //}


    }
}