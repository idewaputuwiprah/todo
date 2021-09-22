package com.example.todo.presentation.main.fragment.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.todo.CreateTodoSealed
import com.example.todo.R
import com.example.todo.databinding.FragmentCreateTodoBinding
import com.example.todo.presentation.main.MainActivity
import com.example.todo.presentation.main.MainViewModel
import com.example.todo.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class TodoCreateFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mBinding: FragmentCreateTodoBinding
    private lateinit var mMaps: MapView
    private val args: TodoCreateFragmentArgs by navArgs()
    private var mTodoId: Int? = null
    private var currMarker: Marker? = null

    private val eventsReceiver: MapEventsReceiver by lazy {
        object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                p?.let {
                    mBinding.todo?.apply {
                        latitude = it.latitude
                        longitude = it.longitude
                    }
                    generateOverlay(it)
                }
                return false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mTodoId = args.todoId
        mBinding = FragmentCreateTodoBinding.inflate(layoutInflater, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.mainViewModel = mainViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.supportFragmentManager = (activity as MainActivity).supportFragmentManager
        fragmentLiveDataState()
        registerLiveData()
        initMap()
        getTodoById()
    }

    private fun registerLiveData() {
        mainViewModel.observerLatLng.observe(viewLifecycleOwner, {
            val geoPoint = GeoPoint(it.latitude, it.longitude)
            generateOverlay(geoPoint)
        })
        mainViewModel.observerDate.observe(viewLifecycleOwner, {
            mBinding.invalidateAll()
        })
    }

    private fun initMap() {
        mMaps = mBinding.osmdroid
        mMaps.controller.setZoom(14.0)
        mMaps.setBuiltInZoomControls(false)
        mMaps.setMultiTouchControls(true)
    }

    private fun generateOverlay(geoPoint: GeoPoint) {
        mMaps.overlays.remove(currMarker)
        currMarker = Marker(mMaps)
        currMarker?.apply {
            position = geoPoint
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_location)
            setAnchor(Marker.ANCHOR_BOTTOM, Marker.ANCHOR_CENTER)
        }
        mMaps.overlays.add(currMarker)
        mMaps.overlays.add(MapEventsOverlay(eventsReceiver))
        mMaps.invalidate()
    }

//    private fun changeMarkerLocation(geoPoint: GeoPoint) {
//        mMaps.overlays.remove(currMarker)
//        currMarker?.apply {
//            position = geoPoint
//        }
//        mMaps.overlays.add(currMarker)
//        mMaps.invalidate()
//    }

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