package com.example.splash

import android.content.Intent
import android.provider.Settings.Secure.putString
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.example.splash.databinding.IlanCardViewBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val adventList: ArrayList<Advent>) : RecyclerView.Adapter<RecyclerViewAdapter.AdventHolder>() {
    class AdventHolder(val binding: IlanCardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventHolder {
        val binding = IlanCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdventHolder(binding)
    }

    override fun getItemCount(): Int {
        return adventList.size
    }

    override fun onBindViewHolder(holder: AdventHolder, position: Int) {
        holder.binding.ilanBaslik.text = adventList.get(position).Baslik
        holder.binding.ilanFiyat.text = adventList.get(position).Kirafiyati
        holder.binding.ilanKonum.text = adventList.get(position).cities
        holder.binding.ilanPeriyot.text = adventList.get(position).Periyot
      Picasso.get().load(adventList.get(position).downloadUrl).into(holder.binding.ilanGorseli)


        holder.binding.satirCardView.setOnClickListener {
         val gecis = MainFragmentDirections.ilanDetayGecis(position, adventList.get(position).Baslik,
             adventList.get(position).Kirafiyati.toInt(),adventList.get(position).Periyot,
             adventList.get(position).cities,adventList.get(position).districts,
             adventList.get(position).quarters,
             adventList.get(position).towns,adventList.get(position).Aciklama,
             Picasso.get().load(adventList.get(position).downloadUrl).into(holder.binding.ilanGorseli)
             )
            Navigation.findNavController(it).navigate(gecis)




        }

    }

}

