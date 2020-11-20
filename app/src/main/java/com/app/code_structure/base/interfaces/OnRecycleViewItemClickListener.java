package com.app.code_structure.base.interfaces;
/**
 * Listener used to dispatch on recycle view item click events.
 */
public interface OnRecycleViewItemClickListener<T> {
    /**
     *@param position index of selected item
     *@param itemData index item data
     */
    public void onItemClick(int position, T itemData);
}