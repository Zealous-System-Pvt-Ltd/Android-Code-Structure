package com.app.code_structure.base;

import android.app.Activity;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.app.code_structure.base.interfaces.OnRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This activity must be parent activity of all the activities in app.
 * this activity contains common functionality that is common between all the child activities.
 */
public class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter {

    protected ArrayList<T> dataList;
    public Activity activity;
    public OnRecycleViewItemClickListener<T> onClickListener;
    private boolean allowMultiSelect = false;
    private int lastSelectedPosition = -1;
    private HashSet<Integer> selectedPositions;

    public BaseRecycleViewAdapter(Activity activity) {
        this.dataList = new ArrayList<>();
        this.activity = activity;
        selectedPositions = new HashSet<>();
        onClickListener = (position, itemData) -> {

        };
    }

    public void setOnClickListener(OnRecycleViewItemClickListener<T> onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelectionMode(boolean allowMultiSelect) {
        this.allowMultiSelect = allowMultiSelect;
    }

    public HashSet<Integer> getSelectedPositions() {
        return selectedPositions;
    }
    public int getSelectedPosition() {
        return lastSelectedPosition;
    }
    public List<T> getSelectedPositionItems() {
        List<T> selectedItems = new ArrayList<>();
        for (Integer selectedPosition : selectedPositions) {
            selectedItems.add(dataList.get(selectedPosition));
        }
        return selectedItems;
    }
    public void setItemSelected(int position) {
        if (allowMultiSelect) {
            selectedPositions.add(position);
        } else {
            setItemUnselected(lastSelectedPosition);
            selectedPositions.add(position);
            lastSelectedPosition = position;
        }
    }
    public void unselectAllItems() {
        if (allowMultiSelect) {
            selectedPositions.clear();
        } else {
            setItemUnselected(lastSelectedPosition);
            selectedPositions.clear();
            lastSelectedPosition = -1;
        }
    }

    public boolean isItemSelected(int position) {
        return selectedPositions.contains(position);
    }

    public void setItemUnselected(int position) {
        selectedPositions.remove(position);
    }

    public ArrayList<T> getItems() {
        return this.dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder_type, final int pos) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
