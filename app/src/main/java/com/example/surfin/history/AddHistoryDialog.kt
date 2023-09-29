package com.example.surfin.history

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.surfin.SurfinApplication
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.databinding.DialogAddHistoryBinding
import com.example.surfin.factory.AddHistoryFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddHistoryDialog : BottomSheetDialogFragment() {

    private lateinit var viewModel: AddHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DialogAddHistoryBinding.inflate(inflater)
        var locationTitle = ""
        binding.inputLocationTitle.doAfterTextChanged { locationTitle = it.toString() }
        var content = ""
        binding.inputContent.doAfterTextChanged { content = it.toString() }
        var heartRate = ""
        binding.inputHeartRate.doAfterTextChanged { heartRate = it.toString() }
        var timeDuration = ""
        binding.inputTimeDuration.doAfterTextChanged { timeDuration = it.toString() }
        var calories = ""
        binding.inputCalories.doAfterTextChanged { calories = it.toString() }
        val photo = binding.inputPhoto.text.toString()
        //date picker
        var date = ""
        binding.inputDate.setOnClickListener {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = Calendar.getInstance().get(Calendar.MONTH)
            val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    date = ("" + mDay + "/" + mMonth + "/" + mYear)
                    binding.inputDate.setText(date)
                }, year, month, day
            )
            dpd.show()
        }


        binding.btnSubmit.setOnClickListener {
            viewModel.addHistory(
                UserActivityHistory(
                    0,
                    locationTitle,
                    date,
                    content,
                    heartRate,
                    timeDuration,
                    calories,
                    photo
                )
            )
            Log.i("history edit ", "$")
            findNavController().navigateUp()
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