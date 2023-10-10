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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.surfin.R
import com.example.surfin.databinding.FragmentAccountBinding
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.surfin.SurfinApplication
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import com.example.surfin.factory.AccountFactory
import com.google.android.material.chip.ChipGroup
import java.net.URI

private const val PICK_IMAGE_REQUEST = 1
lateinit var contentUri: Uri

class AccountFragment : Fragment() {


    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: FragmentAccountBinding
    private lateinit var repository: SurfinRepository
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater)
        repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this, AccountFactory(repository)
        ).get(AccountViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        contentUri = Uri.parse("content://abc")

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
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)

        }

        binding.btnEditName.setOnClickListener {
            showEditNameDialog()
        }

        sharedPreferences = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "Please Enter Your Name")
        binding.accountName.setText(userName)


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PICK_IMAGE_REQUEST
            )
        }


        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            it?.selfie.let { selfie ->
                if (selfie != null) {
                    contentUri = Uri.parse(selfie)
                }
            }
            Log.i("uri", "$contentUri")
            it?.selfie.let {
                try {
                    if (contentUri != null) {
                        binding.thumbnail.setImageURI(contentUri)
                    }
                } catch (e: Exception) {
                    Log.i("uri", "failed: ${e.message}")
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    private fun showRecommendDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_provide_spot)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Log.i("account provide btn", "clicked")

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
        Log.i("account provide btn", "clicked")

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
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            checkedId?.let {
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


    private fun showEditNameDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_edit_name)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Log.i("account provide btn", "clicked")

        val btnCancel = dialog.findViewById<ImageView>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        var inputContent = ""
        dialog.findViewById<EditText>(R.id.input_name)
            .doAfterTextChanged { inputContent = it.toString() }


        val btnSubmit = dialog.findViewById<Button>(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            sharedPreferences =
                requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user_name", inputContent)
            editor.apply()
            binding.accountName.setText(inputContent)
            Toast.makeText(requireContext(), "submitted", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data

            val imagePath = selectedImageUri?.path!!
            val userInfo = UserInfo(0L, selectedImageUri.toString(), "Gemma")
            viewModel.updateUserInfo(userInfo, repository)
            Log.d("Image Path", "Image Path: $imagePath")
        }
    }


}
