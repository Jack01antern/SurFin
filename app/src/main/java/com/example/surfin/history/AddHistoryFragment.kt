package com.example.surfin.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surfin.SurfinApplication
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.databinding.DialogAddHistoryBinding
import com.example.surfin.factory.AddHistoryFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddHistoryFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: AddHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogAddHistoryBinding.inflate(inflater)
        val locationTitle = binding.inputLocationTitle.text.toString()
        val date = binding.inputDate.text.toString()
        val content = binding.inputContent.text.toString()
        val heartRate = binding.inputHeartRate.text.toString()
        val timeDuration = binding.inputTimeDuration.text.toString()
        val calories = binding.inputCalories.text.toString()
        val photo = binding.inputPhoto.text.toString()

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