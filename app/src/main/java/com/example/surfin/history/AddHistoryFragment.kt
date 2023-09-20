package com.example.surfin.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surfin.SurfinApplication
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.databinding.FragmentAddHistoryBinding
import com.example.surfin.factory.AddHistoryFactory
import com.example.surfin.factory.WeatherFactory

class AddHistoryFragment : Fragment() {

    private lateinit var viewModel: AddHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddHistoryBinding.inflate(inflater)
        val historyTitle = binding.inputLocationTitle.text
        val historyDate = binding.inputDate.text
        val historyContent = binding.inputContent.text
        val historyPhoto = binding.inputPhoto.text


        //mockData
        val history = UserActivityHistory(
            activityId = 0L,
            locationTitle = "Jogging in the Park",
            date = System.currentTimeMillis(),
            content = "Enjoyed a morning jog in the park.",
            heartRate = "120 BPM",
            timeDuration = "30 minutes",
            calories = "300 kcal",
            photo = "https://example.com/jogging.jpg"
        )
        //mockDataEnd


        binding.btnSubmit.setOnClickListener {
            viewModel.addHistory(history)
        }

        binding.btnClear.setOnClickListener {
            viewModel.clearHistory()
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this,
            AddHistoryFactory(repository)
        ).get(AddHistoryViewModel::class.java)
    }
}