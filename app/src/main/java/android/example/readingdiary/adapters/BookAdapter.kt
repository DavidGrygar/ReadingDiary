package android.example.readingdiary.adapters

import android.example.readingdiary.data.entities.Book
import android.example.readingdiary.databinding.BookListRowBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    val clickListener: BookListener,
    val swipeListener: SwipeListener
) : ListAdapter<Book, BookAdapter.ViewHolder>(BookDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    fun remove(positionToRemove: Int) {
        //viewModel.deleteBook(getItem(positionToRemove))
        swipeListener.remove(getItem(positionToRemove))
    }

    class ViewHolder private constructor(val binding: BookListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: BookListener, item: Book) {
            binding.book = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BookListRowBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.ID == newItem.ID
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}

class BookListener(val clickListener: (bookID: Int) -> Unit) {
    fun onClick(book: Book) = clickListener(book.ID)
}

class SwipeListener(val swipeListener: (book: Book) -> Unit) {
    fun remove(book: Book) = swipeListener(book)
}