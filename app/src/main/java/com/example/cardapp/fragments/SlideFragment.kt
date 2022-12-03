package com.example.cardapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.cardapp.databinding.FragmentMainBinding
import com.example.cardapp.databinding.FragmentRegisterBinding
import com.example.cardapp.databinding.FragmentSlideBinding
import com.example.cardapp.utils.FragmentKeys

class SlideFragment(): Fragment(){

    private var _binding: FragmentSlideBinding? = null
    private val binding
        get() = _binding!!

    companion object{
        fun newInstance(image: Int): SlideFragment{
            val args = Bundle()
            args.putInt(FragmentKeys.IMAGE_KEY, image)
            val fragment = SlideFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideBinding.inflate(layoutInflater, container, false)
        binding.testImage.text = arguments?.getInt(FragmentKeys.IMAGE_KEY).toString()
        return binding.root
    }
}