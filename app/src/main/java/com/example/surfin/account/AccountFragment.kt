package com.example.surfin.account

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.surfin.R
import com.example.surfin.databinding.FragmentAccountBinding
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.surfin.SurfinApplication
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import com.example.surfin.factory.AccountFactory
import com.google.android.material.chip.ChipGroup

private const val PICK_IMAGE_REQUEST = 0x00

class AccountFragment : Fragment() {

    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: FragmentAccountBinding
    private lateinit var repository: SurfinRepository
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater)
        repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this, AccountFactory(repository)
        ).get(AccountViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        sharedPreferences = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "Please Enter Your Name")
        binding.accountName.text = userName

        binding.activityHistoryLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_history_fragment)
        }
        binding.collectionLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_collection_fragment)
        }

        binding.provideSpotLayout.setOnClickListener {
            showRecommendDialog()
        }

        binding.contactUsLayout.setOnClickListener {
            showContactUsDialog()
        }

        binding.btnChangeThumbnail.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PICK_IMAGE_REQUEST
                )
            } else {
                openImagePicker()
            }
        }


        viewModel.isEditing.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnFinishEditName.visibility = View.VISIBLE
                binding.accountEditName.visibility = View.VISIBLE
                binding.accountName.visibility = View.GONE
                binding.btnEditName.visibility = View.GONE
            } else {
                binding.btnFinishEditName.visibility = View.GONE
                binding.accountEditName.visibility = View.GONE
                binding.accountName.visibility = View.VISIBLE
                binding.btnEditName.visibility = View.VISIBLE
            }
        }


        binding.btnEditName.setOnClickListener {
            viewModel.isEditing.value = true
            val latestName = sharedPreferences.getString("user_name", "Please enter your name")
            if (latestName == "Please enter your name") {
                binding.accountEditName.setText("")
            } else {
                binding.accountEditName.setText(latestName)
            }
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }


        var inputContent = userName
        binding.accountEditName.doAfterTextChanged { inputContent = it.toString() }
        binding.btnFinishEditName.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("user_name", inputContent)
            editor.apply()
            binding.accountName.text = inputContent
            viewModel.isEditing.value = false
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
        }



        viewModel.userInfo.observe(viewLifecycleOwner) {

            it?.let {
                try {
                    val bitmap = BitmapFactory.decodeByteArray(
                        viewModel.userInfo.value?.userPhoto,
                        0,
                        viewModel.userInfo.value?.userPhoto!!.size
                    )
                    binding.userPhoto.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.i("Account picture", "error:${e.message}")
                }
            }
        }

        return binding.root
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun showRecommendDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_provide_spot)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val window = dialog.window
        val layoutParams = window?.attributes
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val width = (displayWidth * 0.95).toInt()
        layoutParams?.width = width
        window?.attributes = layoutParams

        val btnCancel = dialog.findViewById<ImageView>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        var inputAddress = ""
        dialog.findViewById<EditText>(R.id.input_address)
            .doAfterTextChanged { inputAddress = it.toString() }

        var inputContent = ""
        dialog.findViewById<EditText>(R.id.input_content)
            .doAfterTextChanged { inputContent = it.toString() }

        val btnSubmit = dialog.findViewById<Button>(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            viewModel.provideSpots(inputAddress, inputContent)
            binding.submitAnim.visibility = View.VISIBLE
            binding.submitAnim.playAnimation()
            binding.submitAnim.postDelayed({
                binding.submitAnim.visibility = View.GONE
            }, 2000)
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun showContactUsDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_contact_us)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val window = dialog.window
        val layoutParams = window?.attributes
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val width = (displayWidth * 0.95).toInt()
        layoutParams?.width = width
        window?.attributes = layoutParams

        val btnCancel = dialog.findViewById<ImageView>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        var inputUserName = ""
        dialog.findViewById<EditText>(R.id.input_user_name)
            .doAfterTextChanged { inputUserName = it.toString() }

        var inputUserEmail = ""
        dialog.findViewById<EditText>(R.id.input_user_email)
            .doAfterTextChanged { inputUserEmail = it.toString() }

        var inputContent = ""
        dialog.findViewById<EditText>(R.id.input_contact_us)
            .doAfterTextChanged { inputContent = it.toString() }


        val chipGroup = dialog.findViewById<ChipGroup>(R.id.chip_group)
        var category = ""
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            checkedId.let {
                when (it) {
                    R.id.option_1 -> category = "recommendation"
                    R.id.option_2 -> category = "issue report"
                    R.id.option_3 -> category = "other"
                }
            }
        }

        val btnSubmit = dialog.findViewById<Button>(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            viewModel.contactUs(category, inputUserName, inputUserEmail, inputContent)
            binding.submitAnim.visibility = View.VISIBLE
            binding.submitAnim.playAnimation()
            binding.submitAnim.postDelayed({
                binding.submitAnim.visibility = View.GONE
            }, 2000)
            dialog.dismiss()
        }


        dialog.show()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && intent != null) {
            val selectedImageUri = intent.data
            binding.userPhoto.setImageURI(selectedImageUri)
            val inputStream = requireActivity().contentResolver.openInputStream(selectedImageUri!!)
            val byteArray = inputStream?.readBytes()
            val userInfo = UserInfo(1, byteArray!!)
            viewModel.updateUserInfo(userInfo, repository)
        }
    }


}
