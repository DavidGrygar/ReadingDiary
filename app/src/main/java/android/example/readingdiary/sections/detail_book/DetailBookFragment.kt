package android.example.readingdiary.sections.detail_book

import android.example.readingdiary.R
import android.example.readingdiary.data.AppDatabase
import android.example.readingdiary.databinding.DetailBookFragmentBinding
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class DetailBookFragment : Fragment() {

    companion object {
        fun newInstance() = DetailBookFragment()
    }

    private lateinit var viewModel: DetailBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: DetailBookFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.detail_book_fragment, container, false)
        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).bookDAO

        val arguments = DetailBookFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = DetailBookViewModelFactory(application, dataSource, arguments.bookID)
        this.viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailBookViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToMainFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController()
                    .navigate(DetailBookFragmentDirections.actionDetailBookFragmentToMainFragment())
                viewModel.doneNavigating()
            }
        })

        this.viewModel.navigateToEditBook.observe(viewLifecycleOwner, Observer { bookID ->
            bookID?.let {
                findNavController()
                    .navigate(DetailBookFragmentDirections.actionDetailBookFragmentToEditBookFragment(it))
                this.viewModel.doneEditBookNavigating()
            }
        })

        return binding.root
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_book_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_detail_book_delete -> {
                createDeleteDialog().show()
                true
            }
            R.id.action_detail_book_edit -> {
                viewModel.onMenuItemEditBookClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createDeleteDialog(): AlertDialog
    {
        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle(R.string.delete_item_dialog_title)
            setMessage(R.string.delete_item_dialog_message)
            setPositiveButton(R.string.yes) { _, _ ->
                viewModel.onMenuItemDeleteBookClick()
            }
            setNegativeButton(R.string.no) { dialogInterface, _ ->
                dialogInterface.dismiss()
                dialogInterface.cancel()
            }
        }
        return builder.create()
    }

}