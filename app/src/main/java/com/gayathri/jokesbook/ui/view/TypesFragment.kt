package com.gayathri.jokesbook.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gayathri.jokesbook.R
import com.gayathri.jokesbook.data.model.Joke
import com.gayathri.jokesbook.data.model.Jokes
import com.gayathri.jokesbook.ui.adapter.JokesTypesAdapter
import com.gayathri.jokesbook.ui.viewmodel.JokesViewModel
import com.gayathri.jokesbook.utils.Constants.GENERAL
import com.gayathri.jokesbook.utils.Constants.JOKES_LIST
import com.gayathri.jokesbook.utils.Constants.JOKE_TYPE
import com.gayathri.jokesbook.utils.Constants.KNOCK_KNOCK
import com.gayathri.jokesbook.utils.Constants.PREFS_NAME
import com.gayathri.jokesbook.utils.Constants.PROGRAMMING
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_types.*
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TypesFragment: DaggerFragment(), JokesTypesAdapter.JokeSelectionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var viewModel: JokesViewModel
    private var jokesList: List<Joke> = listOf()
    private lateinit var jokesAdapter: JokesTypesAdapter
    private var isOnline: Boolean = false
    private lateinit var button: Button
    private var makeCall = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_types, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()

        shimmerViewContainer.startShimmer()
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(JokesViewModel::class.java)

        // Initialize recyclerView adapter
        jokesAdapter = JokesTypesAdapter()
        jokesAdapter.listener = this@TypesFragment
        types_recyclerView.apply {
            adapter = jokesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        types_general_button.setOnClickListener {
            makeCall = true
            changeButtonColour(types_general_button, GENERAL)
        }

        types_knock_button.setOnClickListener {
            makeCall = true
            changeButtonColour(types_knock_button, KNOCK_KNOCK)
        }

        types_programming_button.setOnClickListener {
            makeCall = true
            changeButtonColour(types_programming_button, PROGRAMMING)
        }

        // General is selected by default
        var jokeType = sharedPreferences.getString(JOKE_TYPE, GENERAL) ?: GENERAL
        button = when(jokeType) {
            KNOCK_KNOCK -> types_knock_button
            PROGRAMMING -> types_programming_button
            else -> types_general_button
        }
        // Populate UI based on the selected joke type
        if (jokeType.isEmpty()) {
            jokeType = GENERAL
        }
        changeButtonColour(button, jokeType)
    }

    private fun changeButtonColour(button: Button, jokeType: String) {
        resetButtonUI()
        button.setBackgroundColor(resources.getColor(R.color.blue_dark, null))
        button.setTextColor(resources.getColor(R.color.white, null))

        sharedPreferences.edit {
            putString(JOKE_TYPE, jokeType)
        }
        if (makeCall) {
            sharedPreferences.edit {
                putString(JOKES_LIST, "")
            }
            makeCall = false
            findNavController().navigate(
                TypesFragmentDirections.actionTypesFragmentSelf()
            )
        }

        // Checks of the jokes are already downloadedS
        val savedJokes = sharedPreferences.getString(JOKES_LIST, "") ?: ""
        if (savedJokes.isNotEmpty()) {
            val jokes = Json.parse(Jokes.serializer(), savedJokes)
            populateList(jokes)
        } else {
            fetchJokes(jokeType)
        }
    }

    // Make a network call
    private fun fetchJokes(jokeType: String) {
        isOnline = isNetworkAvailable(requireContext())
        if (isOnline){
            types_no_internet_View.isVisible = false
            types_recyclerView.isVisible = true
            shimmerViewContainer.isVisible = true
            viewModel.jokesLiveData.removeObservers(this)
            viewModel.getJokes(jokeType)
            viewModel.jokesLiveData.observe(this, Observer {
                if (it.contains("ERROR")) {
                    types_no_internet_View.text = resources.getString(R.string.try_again_later)
                    offlineLayout()
                } else {
                    val jokes = Json.parse(Jokes.serializer(), it)
                    if (jokes.jokes?.first()?.type.equals(jokeType)) {
                        sharedPreferences.edit {
                            putString(JOKES_LIST, it)
                        }
                    }
                    populateList(jokes)
                }
            })
        } else {
            offlineLayout()
        }
    }

    // Populates jokes list
    private fun populateList(jokes: Jokes) {
        jokesList = jokes.jokes ?: listOf()
        jokesAdapter.apply {
            list = jokesList
            notifyDataSetChanged()
        }
        shimmerViewContainer.isVisible = false
    }

    private fun resetButtonUI() {
        types_general_button.setBackgroundColor(resources.getColor(R.color.white, null))
        types_general_button.setTextColor(resources.getColor(R.color.black, null))

        types_knock_button.setBackgroundColor(resources.getColor(R.color.white, null))
        types_knock_button.setTextColor(resources.getColor(R.color.black, null))

        types_programming_button.setBackgroundColor(resources.getColor(R.color.white, null))
        types_programming_button.setTextColor(resources.getColor(R.color.black, null))
    }

    override fun onJokeSelected(joke: Joke, position: Int) {
        findNavController().navigate(
            TypesFragmentDirections.actionTypesFragmentToPunchLineFragment(
                punchLine = joke.punchline,
                position = position
            )
        )
    }

    // Populates offline layout if there is no internet connection or any network issue
    private fun offlineLayout() {
        types_no_internet_View.isVisible = true
        types_recyclerView.isVisible = false
        shimmerViewContainer.isVisible = false
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onDetach() {
        super.onDetach()

        sharedPreferences.edit{
            putString(JOKES_LIST, "")
            putString(JOKE_TYPE, "")
        }
    }
}