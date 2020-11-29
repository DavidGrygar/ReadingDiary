package android.example.readingdiary.sections

import android.example.readingdiary.databinding.EditNewSharedPartBinding
import android.example.readingdiary.utils.hideKeyboard
import android.example.readingdiary.utils.setupYearNumberPicker
import android.example.readingdiary.utils.setupYearPeriodNumberPicker
import android.widget.SeekBar
import androidx.fragment.app.Fragment

abstract class EditNewFragment : Fragment() {
    override fun onStop() {
        view?.hideKeyboard()
        super.onStop()
    }

    fun setupCommonParts(rootIncludeBinding : EditNewSharedPartBinding, viewModel: EditNewViewModel) {

        //region READING_YEAR
        setupYearNumberPicker(rootIncludeBinding.editNewBookReadingYearPicker)

        viewModel.book.observe(viewLifecycleOwner, {
            it?.let {
                if (it.READING_YEAR != rootIncludeBinding.editNewBookReadingYearPicker.value) {
                    rootIncludeBinding.editNewBookReadingYearPicker.value = it.READING_YEAR
                }
            }
        })

        rootIncludeBinding.editNewBookReadingYearPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            viewModel.book.value?.READING_YEAR = newVal
        }
        //endregion

        //region READING_YEAR_PERIOD
        setupYearPeriodNumberPicker(
            rootIncludeBinding.editNewBookReadingYearPeriodPicker,
            requireContext()
        )

        viewModel.book.observe(viewLifecycleOwner, {
            it?.let {
                if (it.READING_YEAR_PERIOD != rootIncludeBinding.editNewBookReadingYearPeriodPicker.value) {
                    rootIncludeBinding.editNewBookReadingYearPeriodPicker.value =
                        it.READING_YEAR_PERIOD
                }
            }
        })

        rootIncludeBinding.editNewBookReadingYearPeriodPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            viewModel.book.value?.READING_YEAR_PERIOD = newVal
        }
        //endregion

        //region READ_PERCENT
        viewModel.book.observe(viewLifecycleOwner, {
            it?.let {
                if (it.READ_PERCENT != rootIncludeBinding.editNewBookReadPercentSeekbar.progress) {
                    rootIncludeBinding.editNewBookReadPercentSeekbar.progress = it.READ_PERCENT
                }
            }
        })

        rootIncludeBinding.editNewBookReadPercentSeekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rootIncludeBinding.editNewBookReadPercentInfo.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let { viewModel.book.value?.READ_PERCENT = seekBar.progress }
            }
        })
        //endregion
    }
}