package com.example.surfin.history

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentEditBinding
import com.example.surfin.factory.EditFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditFragment : DialogFragment() {

    private lateinit var viewModel: EditViewModel
    private lateinit var binding: FragmentEditBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = EditFragmentArgs.fromBundle(requireArguments())
        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        binding = FragmentEditBinding.inflate(inflater)
        viewModel =
            ViewModelProvider(this, EditFactory(args, repository)).get(EditViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.inputLocationTitle.setText(args.historyInfo.locationTitle)
        binding.inputContent.setText(args.historyInfo.content)
        binding.inputHeartRate.setText(args.historyInfo.heartRate)
        binding.inputTimeDuration.setText(args.historyInfo.timeDuration)
        binding.inputCalories.setText(args.historyInfo.calories)
        binding.inputDate.setOnClickListener {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = Calendar.getInstance().get(Calendar.MONTH)
            val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener{view, mYear,mMonth,mDay->
                binding.inputDate.setText(""+mDay+"/"+mMonth+"/"+mYear)
            },year,month,day)
            dpd.show()
        }
        Log.i("edit", "args: $args")




        return binding.root
    }

}