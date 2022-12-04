package com.example.cardapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cardapp.fragments.auth.SlideFragment
import com.example.cardapp.fragments.auth.SignFragment
import com.example.cardapp.models.Slide

class IntroductionViewPagerAdapter(fragment: SignFragment, val data: List<Slide>):  FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return SlideFragment.newInstance(data[position])
    }

}