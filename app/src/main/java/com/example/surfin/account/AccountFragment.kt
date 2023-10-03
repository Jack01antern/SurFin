package com.example.surfin.account

import android.app.Activity.RESULT_OK
import android.app.Dialog
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.surfin.MainActivity
import com.example.surfin.R
import com.example.surfin.databinding.FragmentAccountBinding
import java.util.Locale
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.surfin.SurfinApplication
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import com.example.surfin.detail.DetailViewModel
import com.example.surfin.factory.AccountFactory
import com.example.surfin.factory.DetailFactory
import java.io.ByteArrayOutputStream

private const val PICK_IMAGE_REQUEST = 1

class AccountFragment : Fragment() {


    private lateinit var viewModel: AccountViewModel
    private lateinit var locale: Locale
    private lateinit var binding: FragmentAccountBinding
    private lateinit var xx: ByteArray
    private lateinit var repository: SurfinRepository
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


        //select language
        var languageList = ArrayList<String>()
        languageList.add("")
        languageList.add("English")
        languageList.add("Chinese")
        var adapter = ArrayAdapter(
            requireContext(),
            com.bumptech.glide.R.layout.support_simple_spinner_dropdown_item,
            languageList
        )
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {
                    }

                    1 -> setLocale("en")
                    2 -> setLocale("cn")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        binding.btnActivityHistory.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_history_fragment)
        }
        binding.btnCollection.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_collection_fragment)
        }
        binding.btnLanguage.setOnClickListener {
            binding.spinner.visibility = View.VISIBLE
        }

        binding.btnProvideSpots.setOnClickListener {
            showRecommendDialog()
        }

        binding.btnContactUs.setOnClickListener {
            showContactUsDialog()
        }

        binding.btnReportProblem.setOnClickListener {
            showReportDialog()
        }

        binding.btnChangeThumbnail.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)

        }

        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            val contentUri = Uri.parse(it.selfie)
            Log.i("uri", "$contentUri")
            it.selfie.let {
                try {
                    if (contentUri != null) {
//                        val bitmap = loadBitmapFromUri(contentUri)
                        binding.thumbnail.setImageURI(contentUri)
//                        binding.thumbnail.setImageBitmap(bitmap)
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

    fun setLocale(language: String) {
        locale = Locale(language)
        var res = resources
        var dm = res.displayMetrics
        var conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)

        var refresh = Intent(requireContext(), MainActivity::class.java)
        startActivity(refresh)
        binding.spinner.visibility = View.GONE

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

    private fun showReportDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_report)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Log.i("account provide btn", "clicked")

        val btnCancel = dialog.findViewById<ImageView>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        var inputContent = ""
        dialog.findViewById<EditText>(R.id.input_report).doAfterTextChanged { inputContent = it.toString() }

        val btnSubmit = dialog.findViewById<Button>(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            Toast.makeText(requireContext(), "submitted", Toast.LENGTH_SHORT).show()
            viewModel.reportProblem(inputContent)
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

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        try {
            // Use a content resolver to open the input stream
            val inputStream = requireActivity().contentResolver.openInputStream(uri)
            if (inputStream != null) {
                // Decode the stream into a bitmap
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                return bitmap
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
