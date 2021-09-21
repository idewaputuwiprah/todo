package com.example.todo.presentation.main.fragment.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.todo.CreateTodoSealed
import com.example.todo.R
import com.example.todo.databinding.FragmentCreateTodoBinding
import com.example.todo.presentation.main.MainViewModel
import com.example.todo.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@AndroidEntryPoint
class TodoCreateFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mBinding: FragmentCreateTodoBinding
    private lateinit var mMaps: MapView
    private val args: TodoCreateFragmentArgs by navArgs()
    private var mTodoId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mTodoId = args.todoId
        mBinding = FragmentCreateTodoBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLiveDataState()
        registerLiveData()
        initMap()
        getTodoById()
    }

    private fun registerLiveData() {
        mainViewModel.observerLatLng.observe(viewLifecycleOwner, {
            val geoPoint = GeoPoint(it.latitude, it.longitude)
//            generateOverlay(geoPoint)
        })
        mainViewModel.observerDate.observe(viewLifecycleOwner, {
            mBinding
                .invalidateAll()
        })
    }

    private fun initMap() {
        mMaps = mBinding.osmdroid
        mMaps.controller.setZoom(14.0)
        mMaps.setBuiltInZoomControls(false)
        mMaps.setMultiTouchControls(true)
    }

    private fun generateOverlay(geoPoint: GeoPoint) {
        TODO(
            "Buatlah marker untuk menampilkan koordinat todo pada maps," +
                    "Marker yang tampil di maps hanya boleh satu"
        )
    }

    private val eventsReceiver: MapEventsReceiver by lazy {
        TODO(
            "Buatlah interaksi user dengan maap, ketika user melakukan longpress." +
                    "Buatlah sebuah marker berdasarkan koordinat yang user tekan dan marker di maps hanya boleh satu"
        )
    }


    private fun fragmentLiveDataState() {
        mainViewModel.observerSave.observe(viewLifecycleOwner, {
            when (it) {
                is CreateTodoSealed.OnProgressGet -> mBinding.getOnProgress = true
                is CreateTodoSealed.OnProgressSave -> mBinding.saveOnProgress = true
                is CreateTodoSealed.OnSaveSuccess -> {
                    mBinding.saveOnProgress = false
                    mainViewModel.getTodoList()
                    Navigation.findNavController(mBinding.root)
                        .navigate(R.id.action_todoCreateFragment_to_todoListFragment)
                }
                is CreateTodoSealed.OnGetSuccess -> {
                    mBinding.getOnProgress = false
                    mBinding.todo = it.todo
                }
                is CreateTodoSealed.OnFailure -> onError(it.err)
            }
        })
    }

    private fun onError(e: Throwable) {
        mBinding.getOnProgress = false
        mBinding.saveOnProgress = false
        DialogUtils.alertDialogError(requireContext(), e)
    }

    private fun getTodoById() {
        mainViewModel.getTodoById(mTodoId!!)
    }

    override fun onPause() {
        super.onPause()
        mMaps.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMaps.onResume()
    }

    override fun onDetach() {
        super.onDetach()
        mMaps.onDetach()
    }
}