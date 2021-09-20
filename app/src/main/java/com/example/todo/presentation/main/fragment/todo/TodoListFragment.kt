package com.example.todo.presentation.main.fragment.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.todo.ListTodoSealed
import com.example.todo.R
import com.example.todo.databinding.FragmentTodoListBinding
import com.example.todo.model.TodoCache
import com.example.todo.presentation.main.MainViewModel
import com.example.todo.utils.DialogUtils

class TodoListFragment : Fragment() {

    private lateinit var mBinding: FragmentTodoListBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapterTodo: AdapterTodo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        TODO("Buatlah viewBinding yang diperlukan oleh fragment")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getTodoList()
        mBinding.fragmentTodo = this
        mainViewModel.observerList.observe(viewLifecycleOwner, {
            when (it) {
                is ListTodoSealed.Progress -> mBinding.isOnProgress = true
                is ListTodoSealed.OnSuccess -> checkList(it.data)
                is ListTodoSealed.OnFailure -> error(it.err)
            }
        })
    }

    private fun checkList(list: List<TodoCache>) {
        mBinding.isOnProgress = false
        mBinding.isEmpty = list.isEmpty()
        mAdapterTodo.addNewList(list)
    }

    private fun error(e: Throwable) {
        DialogUtils.alertDialogError(requireContext(), e)
    }

    fun gotoCreateTodo(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_todoListFragment_to_todoCreateFragment)
    }

}