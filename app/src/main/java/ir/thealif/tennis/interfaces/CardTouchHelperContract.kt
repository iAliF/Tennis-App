package ir.thealif.tennis.interfaces

import ir.thealif.tennis.adapters.MyRecyclerViewAdapter

interface CardTouchHelperContract {
    fun onRowMoved(fromPosition: Int, toPosition: Int)
    fun onRowSelected(viewHolder: MyRecyclerViewAdapter.ViewHolder)
    fun onRowClear(viewHolder: MyRecyclerViewAdapter.ViewHolder)
}