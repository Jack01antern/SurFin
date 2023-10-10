package com.example.surfin.history

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
        var heartRate = ""
        binding.inputHeartRate.doAfterTextChanged { heartRate = it.toString() }
        var timeDuration = ""
        binding.inputTimeDuration.doAfterTextChanged { timeDuration = it.toString() }
        var calories = ""
        binding.inputCalories.doAfterTextChanged { calories = it.toString() }
        var content = ""
        binding.inputContent.doAfterTextChanged { content = it.toString() }
        //date picker
        var date = ""
        binding.btnSelectDate.setOnClickListener {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = Calendar.getInstance().get(Calendar.MONTH)
            val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    val correctedMonth = mMonth + 1
                    date = ("$mDay/$correctedMonth/$mYear")
                    binding.inputDate.setText(date)
                }, year, month, day
            )
            dpd.show()
        }


        binding.btnSubmit.setOnClickListener {
            viewModel.addHistory(
                UserActivityHistory(
                    0,
                    date,
                    locationTitle,
                    heartRate,
                    timeDuration,
                    calories,
                    content
                )
            )
            Log.i("history edit ", "$")
            findNavController().navigateUp()
        }


        //setup background
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params = dialog?.window?.attributes
        params?.dimAmount = 0.5f
        dialog?.window?.attributes = params as WindowManager.LayoutParams

//        binding.btnClear.setOnClickListener {
//            viewModel.clearHistory()
//        }

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