package android.example.readingdiary.ui

import android.content.res.Resources
import android.example.readingdiary.R
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration: RecyclerView.ItemDecoration() {

    private var verticalSpaceHeight = R.dimen.vertical_list_space_height

    fun VerticalSpaceItemDecoration(verticalSpaceHeight: Int) {
        this.verticalSpaceHeight = verticalSpaceHeight
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = parent.resources.getDimensionPixelSize(verticalSpaceHeight)
    }
}