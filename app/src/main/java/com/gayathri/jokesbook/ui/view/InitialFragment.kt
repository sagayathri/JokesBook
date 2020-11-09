package com.gayathri.jokesbook.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gayathri.jokesbook.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_initial.*


class InitialFragment: DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_initial, container, false)
    }

    override fun onResume() {
        super.onResume()

        home_button.setOnClickListener {
            findNavController().navigate(
                InitialFragmentDirections.actionInitialFragmentToTypesFragment()
            )
        }
    }
}