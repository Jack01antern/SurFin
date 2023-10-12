package com.example.surfin.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.DialogDetailBinding
import com.example.surfin.factory.DetailFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailDialog : BottomSheetDialogFragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DialogDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = DetailDialogArgs.fromBundle(requireArguments()).spotInfo

        binding = DialogDetailBinding.inflate(inflater)
        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this,
            DetailFactory(repository, args)
        ).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        //photo recycler view
        val adapter = DetailAdapter(DetailAdapter.OnClickListener{
            findNavController().navigate(ZoomDialogDirections.actionNavigateToZoomDialog(it))
        })
        binding.detailRecyclerView.adapter = adapter
        viewModel.selectedDetail.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.photo)
            Log.i("selected detail args info","${it.photo}")
        })


        //star function
        val starredBtn = binding.btnStarred
        val unStarredBtn = binding.btnUnstarred

        viewModel.isStarred.observe(viewLifecycleOwner, Observer {
            Log.i("detailDialog", "isStarred $it")
            if (it) {
                unStarredBtn.visibility = View.GONE
                starredBtn.visibility = View.VISIBLE
            } else {
                unStarredBtn.visibility = View.VISIBLE
                starredBtn.visibility = View.GONE

            }
        })

        unStarredBtn.setOnClickListener {
            unStarredBtn.visibility = View.GONE
            starredBtn.visibility = View.VISIBLE
            viewModel.addToCollection(repository, args)
            Log.i("STARRED3", "${args}")
        }

        starredBtn.setOnClickListener {
            starredBtn.visibility = View.GONE
            unStarredBtn.visibility = View.VISIBLE
            viewModel.removeFromCollection(repository, args)
            Log.i("STARRED4", "${args}")
        }


        return binding.root
    }


}