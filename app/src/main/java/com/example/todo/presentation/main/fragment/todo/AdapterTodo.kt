package com.example.todo.presentation.main.fragment.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.ItemTodoBinding
import com.example.todo.model.TodoCache
import javax.inject.Inject

class AdapterTodo : RecyclerView.Adapter<AdapterTodo.ViewHolder>() {
    private val mItemList: MutableList<TodoCache> = ArrayList()

    class ViewHolder(private val itemTodoBinding: ItemTodoBinding) :
        RecyclerView.ViewHolder(itemTodoBinding.root) {
        fun setTodo(todoCache: TodoCache) {
            itemTodoBinding.todo = todoCache
            itemTodoBinding.executePendingBindings()
        }
    }

    fun addNewList(list: List<TodoCache>) {
        if (mItemList.isNotEmpty()) {
            mItemList.clear()
        }
        mItemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_todo, parent, false)
        val itemTodoBinding: ItemTodoBinding = DataBindingUtil.bind(view)!!
        return ViewHolder(itemTodoBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mItemList[position]
        holder.setTodo(model)
        holder.itemView.setOnClickListener {
            val action =
                TodoListFragmentDirections.actionTodoListFragmentToTodoCreateFragment(model.id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int = mItemList.size
}