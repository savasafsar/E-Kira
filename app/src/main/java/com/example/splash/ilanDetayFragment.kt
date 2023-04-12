package com.example.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.splash.databinding.FragmentAddBinding
import com.example.splash.databinding.FragmentIlanDetayBinding
import com.example.splash.databinding.IlanCardViewBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import io.grpc.NameResolver.Args
import java.util.*


class ilanDetayFragment : Fragment() {

    private var _binding: FragmentIlanDetayBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage : FirebaseStorage



    override fun onCreateView( inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIlanDetayBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        firestore = Firebase.firestore
        storage = Firebase.storage
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle: ilanDetayFragmentArgs by navArgs()
        val gelenkira = bundle.kirafiyati
        binding.gelenFiyattextView.text = gelenkira.toString()
        val gelenCities = bundle.cities
        binding.gelencitestextView.text = gelenCities
        val gelenBaslik = bundle.baslik
        binding.gelenbasliktextView.text = gelenBaslik
        val gelenPeriyot = bundle.periyot
        binding.gelenperiyottextView.text = gelenPeriyot
        val gelenDistricts = bundle.districts
        binding.gelenilcetextView.text = gelenDistricts
        val gelenquarters = bundle.quarters
        binding.gelenmahallebirtextView.text = gelenquarters
        val gelenTowns = bundle.towns
        binding.gelenmahalleikitextView.text = gelenTowns
        val gelenAciklama = bundle.aciklama
        binding.gelenaciklamatextView.text = gelenAciklama
        val gelenFoto = bundle.foto
        println(gelenFoto)
       // binding.ilanDetayimage.setImageURI()


        ///

        }

    }

