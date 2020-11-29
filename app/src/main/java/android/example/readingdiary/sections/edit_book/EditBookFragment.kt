package android.example.readingdiary.sections.edit_book

import android.example.readingdiary.sections.EditNewFragment
import android.example.readingdiary.R
import android.example.readingdiary.data.AppDatabase
import android.example.readingdiary.databinding.EditBookFragmentBinding
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class EditBookFragment : EditNewFragment() {

    companion object {
        fun newInstance() = EditBookFragment()
    }

    private lateinit var viewModel: EditBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //region ViewModel and Binding setup
        val binding: EditBookFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.edit_book_fragment, container, false)
        val application = requireNotNull(this.activity).application

        val dataSource = AppDatabase.getInstance(application).bookDAO

        val arguments = EditBookFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = EditBookViewModelFactory(application, dataSource, arguments.bookID)
        this.viewModel =
            ViewModelProvider(this, viewModelFactory).get(EditBookViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //endregion

        viewModel.navigateBack.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(EditBookFragmentDirections.actionEditBookFragmentToDetailBookFragment(it) )
                viewModel.doneNavigatingBack()
            }
        })

        super.setupCommonParts(binding.rootInclude, viewModel)

        return binding.root
    }

    //region OptionsMenu
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_book_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_edit_book_delete -> {
                createDeleteDialog().show()
                true
            }
            R.id.action_edit_book_save -> {
                viewModel.onMenuItemSaveBook()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    private fun createDeleteDialog(): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle(R.string.delete_item_dialog_title)
            setMessage(R.string.delete_item_dialog_message)
            setPositiveButton(R.string.yes) { _, _ ->
                viewModel.onMenuItemDeleteBook()
            }
            setNegativeButton(R.string.no) { dialogInterface, _ ->
                dialogInterface.dismiss()
                dialogInterface.cancel()
            }
        }
        return builder.create()
    }

}