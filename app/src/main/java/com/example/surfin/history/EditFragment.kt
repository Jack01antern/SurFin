package com.example.surfin.history

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.surfin.SurfinApplication
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.databinding.FragmentEditBinding
import com.example.surfin.factory.EditFactory
import java.util.Calendar

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



        var locationTitle = ""
        binding.inputLocationTitle.doAfterTextChanged {locationTitle  = it.toString() }
        var content =""
        binding.inputContent.doAfterTextChanged { content =it.toString() }
        var heartRate = ""
        binding.inputHeartRate.doAfterTextChanged { heartRate = it.toString() }
        var timeDuration = ""
        binding.inputTimeDuration.doAfterTextChanged { timeDuration = it.toString() }
        var calories = ""
        binding.inputCalories.doAfterTextChanged { calories = it.toString() }
        val photo = binding.inputPhoto.text.toString()
        //date picker
        var date = ""


//        val history = UserActivityHistory(
//            0,locationTitle,date,content,heartRate,timeDuration,calories,photo
//        )
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


        binding.btnSubmit.setOnClickListener {
            val history = UserActivityHistory(
                args.historyInfo.activityId,locationTitle,date,content,heartRate,timeDuration,calories,photo
            )
            viewModel.updateHistory(history,repository)
            Log.i("edit fragment", "${args.historyInfo},$repository")
            findNavController().navigateUp()
        }
        return binding.root
    }

}