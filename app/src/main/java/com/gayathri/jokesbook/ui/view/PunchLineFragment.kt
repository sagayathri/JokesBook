package com.gayathri.jokesbook.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gayathri.jokesbook.R
import com.gayathri.jokesbook.utils.Utils
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_punchline.*
import kotlinx.android.synthetic.main.row_joke_types.view.*
import javax.inject.Inject

class PunchLineFragment: DaggerFragment() {

    private val args: PunchLineFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_punchline, container, false)
    }

    override fun onResume() {
        super.onResume()

        // Loads a GIF using Glide
        val drawable = Utils.getDrawable()[args.position]
        Glide.with(requireContext())
            .load(drawable)
            .into(punchLine_imageView)

        punchLine_textView.text = args.punchLine
    }
}