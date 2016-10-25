package com.emijit.lighteningtalktimer.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Interface for {@link RecyclerView.Adapter} subclass to implement, and
 * {@link ItemTouchHelper.Callback} sublcass to call in it's super overrides
 */
public interface  ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}