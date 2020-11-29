package android.example.readingdiary.sections.main_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.example.readingdiary.R
import android.example.readingdiary.adapters.BookAdapter
import android.example.readingdiary.adapters.BookListener
import android.example.readingdiary.adapters.SwipeListener
import android.example.readingdiary.data.AppDatabase
import android.example.readingdiary.data.entities.Book
import android.example.readingdiary.databinding.MainFragmentBinding
import android.example.readingdiary.ui.VerticalSpaceItemDecoration
import android.example.readingdiary.utils.setupMainList
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MainFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).bookDAO
        val viewModelFactory = MainViewModelFactory(dataSource, application)
        this.viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        val adapter = BookAdapter(BookListener { bookID ->
            viewModel.onBookClicked(bookID)
        }, SwipeListener{book ->
            //createDeleteDialog(book).show()
            viewModel.deleteBook(book)
        })
        val itemDecoration = VerticalSpaceItemDecoration()

        binding.mainViewModel = this.viewModel
        binding.lifecycleOwner = this
        binding.bookList.adapter = adapter

        binding.bookList.addItemDecoration(itemDecoration)

        setupMainList(binding.bookList, requireContext())

        this.viewModel.books.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        this.viewModel.navigateToDeatilBook.observe(viewLifecycleOwner, Observer { bookID ->
            bookID?.let {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToDetailBookFragment(it))
                this.viewModel.doneDeatilBookNavigating()
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_add -> {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToNewBookFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createDeleteDialog(book: Book): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle(R.string.delete_item_dialog_title)
            setMessage(R.string.delete_item_dialog_message)
            setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteBook(book)
            }
            setNegativeButton(R.string.no) { dialogInterface, _ ->
                dialogInterface.dismiss()
                dialogInterface.cancel()
            }
        }
        return builder.create()
    }
}