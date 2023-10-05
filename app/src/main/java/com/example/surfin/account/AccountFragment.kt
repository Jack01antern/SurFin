package com.example.surfin.account

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
import android.net.Uri
import android.provider.MediaStore
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.surfin.SurfinApplication
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import com.example.surfin.factory.AccountFactory

private const val PICK_IMAGE_REQUEST = 1

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


        binding.activityHistoryTitle.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_history_fragment)
        }
        binding.collectionTitle.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_collection_fragment)
        }

        binding.provideSpotsTitle.setOnClickListener {
            showRecommendDialog()
        }

        binding.contactUsTitle.setOnClickListener {
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


//        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
//            val contentUri = Uri.parse(it.selfie)
//            Log.i("uri", "$contentUri")
//            it.selfie.let {
//                try {
//                    if (contentUri != null) {
//                        binding.thumbnail.setImageURI(contentUri)
//                    }
//                } catch (e: Exception) {
//                    Log.i("uri", "failed: ${e.message}")
//                }
//            }
//        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    private fun showRecommendDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_recommend)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Log.i("account provide btn", "clicked")

        val btnCancel = dialog.findViewById<ImageView>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        var inputContent = ""
        dialog.findViewById<EditText>(R.id.input_spots).doAfterTextChanged { inputContent = it.toString() }

        val btnSubmit = dialog.findViewById<Button>(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            viewModel.provideSpots(inputContent)
            Toast.makeText(requireContext(), "submitted", Toast.LENGTH_SHORT).show()
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

        var inputContent = ""
        dialog.findViewById<EditText>(R.id.input_contact_us).doAfterTextChanged { inputContent = it.toString() }

        val btnSubmit = dialog.findViewById<Button>(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            Toast.makeText(requireContext(), "submitted", Toast.LENGTH_SHORT).show()
            viewModel.contactUs(inputContent)
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
        dialog.findViewById<EditText>(R.id.input_name).doAfterTextChanged { inputContent = it.toString() }



        val btnSubmit = dialog.findViewById<Button>(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            sharedPreferences = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user_name",inputContent)
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
