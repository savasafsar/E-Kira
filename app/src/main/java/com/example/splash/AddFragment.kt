package com.example.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.splash.api.RestApiService
import com.example.splash.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.IOException
import java.util.UUID


class AddFragment : Fragment() {
    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val periyot = ArrayList<String>()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage : FirebaseStorage
    private lateinit var veriAdaptoru : ArrayAdapter<String>

    val Cities : MutableMap<String, Int> = mutableMapOf()
    val Towns : MutableMap<String, Int> = mutableMapOf()
    val Districts : MutableMap<String, Int> = mutableMapOf()
    val Quarters : MutableMap<String, Int> = mutableMapOf()
    var selectedQuarter : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = Firebase.firestore
        storage = Firebase.storage
        registerLauncher()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectImage.setOnClickListener { selectImage(view) }
        val apiService = this@AddFragment.context?.let { RestApiService(it) }

        UpdateCitySpinner(true)

        periyot.add("Günlük")
        periyot.add("Haftalık")
        periyot.add("Aylık")
        periyot.add("Yıllık")

        veriAdaptoru = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,android.R.id.text1,periyot)
        binding.periyotSpinner.adapter = veriAdaptoru

        veriAdaptoru.insert("Kira Periyodu Seçin",0)
        binding.periyotSpinner.adapter = veriAdaptoru
        binding.periyotSpinner.setSelection(0)

        binding.periyotSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.submit.setOnClickListener {
            val action = AddFragmentDirections.actionAddFragmentToMainFragment(veriAdaptoru.toString())
            Navigation.findNavController(it).navigate(action)
            val uuid = UUID.randomUUID()
            val imageName = "$uuid.jpg"
           val reference = storage.reference
            val imageReference = reference.child("images").child(imageName)

            if (selectedPicture!=null) {
                imageReference.putFile(selectedPicture!!).addOnSuccessListener {
                    //download url -> firestore

                    val uploadPictureReference = storage.reference.child("images").child(imageName)
                    uploadPictureReference.downloadUrl.addOnSuccessListener {
                        val downloadUrl = it.toString()
                        val adventMap = hashMapOf<String,Any>()
                        adventMap.put("downloadUrl",downloadUrl)
                        adventMap.put("Baslik",binding.commentHeader.text.toString())
                        adventMap.put("Aciklama",binding.commentText.text.toString())
                        adventMap.put("KiraFiyati",binding.kiraFiyati.text.toString())
                        adventMap.put("Periyot",binding.periyotSpinner.selectedItem.toString())
                        adventMap.put("cities",binding.cities.selectedItem.toString())
                        adventMap.put("towns",binding.towns.selectedItem.toString())
                        adventMap.put("districts",binding.districts.selectedItem.toString())
                        adventMap.put("quarters",binding.quarters.selectedItem.toString())
                        adventMap.put("date", Timestamp.now())
                       firestore.collection("Advents").add(adventMap).addOnSuccessListener {

                       }.addOnFailureListener {
                           Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                       }
                    }


                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }







        }



        apiService?.getCities() {
            it?.result?.forEach {
                Cities.set(it.name, it.id)
            }
            UpdateCitySpinner()
        }

        var citySpinner : Spinner = binding.cities
        var townSpinner : Spinner = binding.towns
        var districtSpinner : Spinner = binding.districts
        var quarterSpinner : Spinner = binding.quarters
        citySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Cities.get(selectedItem)
                if(position > 0 && id != null && id > 0) {
                    Towns.clear()
                    Districts.clear()
                    Quarters.clear()
                    UpdateDistrictSpinner()
                    UpdateQuarterSpinner()
                    UpdateTownSpinner(true)
                    apiService?.getTowns(id) {
                        it?.result?.forEach {
                            Towns.set(it.name, it.id)
                        }
                        UpdateTownSpinner()
                    }
                } else {
                    Towns.clear()
                    Quarters.clear()
                    Districts.clear()
                    UpdateQuarterSpinner()
                    UpdateDistrictSpinner()
                    UpdateTownSpinner()
                }
                binding.submit.visibility = View.INVISIBLE
            }
        }
        townSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Towns.get(selectedItem)
                if(position > 0 && id != null && id > 0) {
                    println(id)
                    Districts.clear()
                    Quarters.clear()
                    UpdateQuarterSpinner()
                    UpdateDistrictSpinner(true)
                    apiService?.getDistricts(id) {
                        it?.result?.forEach {
                            Districts.set(it.name, it.id)
                        }
                        UpdateDistrictSpinner()
                    }
                } else {
                    Quarters.clear()
                    Districts.clear()
                    UpdateQuarterSpinner()
                    UpdateDistrictSpinner()
                }
                binding.submit.visibility = View.INVISIBLE
            }
        }
        districtSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Districts.get(selectedItem)
                if(position > 0 && id != null && id > 0) {
                    println(id)
                    Quarters.clear()
                    UpdateQuarterSpinner(true)
                    apiService?.getQuarters(id) {
                        it?.result?.forEach {
                            Quarters.set(it.name, it.id)
                        }
                        UpdateQuarterSpinner()
                    }
                } else {
                    Quarters.clear()
                    UpdateQuarterSpinner()
                }
                binding.submit.visibility = View.INVISIBLE
            }
        }
        quarterSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedItem = parent?.getItemAtPosition(position).toString()
                var id : Int? = Quarters.get(selectedItem)
                if(position > 0 && id != null && id > 0) {
                    binding.submit.visibility = View.VISIBLE
                    selectedQuarter = id
                }
            }
        }

    }


    fun selectImage(view: View) {

        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    requireActivity().applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Give Permission",
                            View.OnClickListener {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
               // val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // activityResultLauncher.launch(intentToGallery)

            }

        }

    }

    private fun registerLauncher() {
        var activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    selectedPicture = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                selectedPicture!!
                            )
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.selectImage.setImageBitmap(selectedBitmap)
                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                selectedPicture
                            )
                            binding.selectImage.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                activityResultLauncher.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(requireContext(), "Permisson needed!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun UpdateCitySpinner(loading : Boolean = false) {
        var citySpinner : Spinner = binding.cities
        var cities = ArrayList<String>()
        if(loading) {
            cities.add("Yükleniyor...")
            citySpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    cities
                )
            }
            citySpinner.visibility = View.VISIBLE
            return
        }
        cities.add("İl Seçin")
        Cities.forEach { (name) ->
            cities.add(name)
        }
        citySpinner.adapter = this@AddFragment.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                cities
            )
        }
    }

    fun UpdateTownSpinner(loading : Boolean = false) {
        var townSpinner : Spinner = binding.towns
        var towns = ArrayList<String>()
        if(loading) {
            towns.add("Yükleniyor...")
            townSpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    towns
                )
            }
            townSpinner.visibility = View.VISIBLE
            return
        }
        towns.add("İlçe Seçin")
        if(Towns.isEmpty()) townSpinner.visibility = View.INVISIBLE
        else townSpinner.visibility = View.VISIBLE
        Towns.forEach { (name) ->
            towns.add(name)
        }
        townSpinner.adapter = this@AddFragment.context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, towns) }
    }

    fun UpdateDistrictSpinner(loading : Boolean = false) {
        var districtSpinner : Spinner = binding.districts
        var districts = ArrayList<String>()
        if(loading) {
            districts.add("Yükleniyor...")
            districtSpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    districts
                )
            }
            districtSpinner.visibility = View.VISIBLE
            return
        }
        districts.add("Semt Seçin")
        if(Districts.isEmpty()) districtSpinner.visibility = View.INVISIBLE
        else districtSpinner.visibility = View.VISIBLE
        Districts.forEach { (name) ->
            districts.add(name)
        }
        districtSpinner.adapter = this@AddFragment.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                districts
            )
        }
    }

    fun UpdateQuarterSpinner(loading : Boolean = false) {
        var quarterSpinner : Spinner = binding.quarters
        var quarters = ArrayList<String>()
        if(loading) {
            quarters.add("Yükleniyor...")
            quarterSpinner.adapter = this@AddFragment.context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    quarters
                )
            }
            quarterSpinner.visibility = View.VISIBLE
            return
        }
        quarters.add("Mahalle Seçin")
        if(Quarters.isEmpty()) quarterSpinner.visibility = View.INVISIBLE
        else quarterSpinner.visibility = View.VISIBLE
        Quarters.forEach { (name) ->
            quarters.add(name)
        }
        quarterSpinner.adapter = this@AddFragment.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                quarters
            )
        }
    }

}