package android.example.readingdiary.sections.new_book

import android.example.readingdiary.sections.EditNewFragment
import android.example.readingdiary.R
import android.example.readingdiary.data.AppDatabase
import android.example.readingdiary.databinding.NewBookFragmentBinding
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class NewBookFragment : EditNewFragment() {

    companion object {
        fun newInstance() = NewBookFragment()
    }

    private lateinit var viewModel: NewBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //region ViewModel and Pinding setup
        val binding: NewBookFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.new_book_fragment, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).bookDAO
        val viewModelFactory = NewBookViewModelFactory(application, dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewBookViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //endregion

        viewModel.navigateAfterSave.observe(viewLifecycleOwner, Observer {
            if(it == true)
            {
                this.findNavController().navigate(NewBookFragmentDirections.actionNewBookFragmentToMainFragment())
                viewModel.doneNavigateAfterSave()
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
        inflater.inflate(R.menu.new_book_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_new_book_save -> {
                this.viewModel.onMenuItemSaveBook()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion
}