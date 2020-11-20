package com.app.code_structure.utils.widgets.recycleview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.code_structure.R;
import com.app.code_structure.base.BaseRecycleViewAdapter;
import com.app.code_structure.base.interfaces.LoadMoreCallback;
import com.app.code_structure.base.interfaces.OnRecycleViewItemClickListener;


/**
 * Created by admin on 2020.
 */


public class PaginateRecycleView extends RecyclerView {

    PaginationAdapter paginationAdapter;
    private int mMaxHeight = 250, emptyResourceId = 0;
    View emptyView = null;

    public PaginateRecycleView(Context context) {
        super(context);
    }

    public PaginateRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.PaginateRecycleView);
        emptyResourceId = t.getResourceId(R.styleable.PaginateRecycleView_EmptyViewID, 0);
    }

    public PaginateRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void OnItemClickListener(OnRecycleViewItemClickListener listener) {
        ((BaseRecycleViewAdapter) getAdapter()).setOnClickListener(listener);
    }

    public PaginationAdapter getPaginationAdapter() {
        return paginationAdapter;
    }

    public void setLoadingState(int loadingState) {
        setVisibility(VISIBLE);
        paginationAdapter.setLoadingState(loadingState);
        if (loadingState == PaginationAdapter.STATE_LOADING_SUCCESS_END_OF_LISTS && paginationAdapter.getAdapter().getItems().size() == 0 && emptyView != null) {
            emptyView.setVisibility(VISIBLE);
            setVisibility(GONE);
            paginationAdapter.setMessageVisible(false);
        }

    }

    boolean isFirstTime = true;
    LoadMoreCallback onLoadingMore = null;

    public void setOnLoadMoreListener(LoadMoreCallback onLoadingMore) {
        this.onLoadingMore = onLoadingMore;
        initPaginationAdapter(onLoadingMore);
    }

    public void resetPagination() {
        if (onLoadingMore != null && paginationAdapter != null) {
            if (getAdapter() instanceof BaseRecycleViewAdapter) {
                ((BaseRecycleViewAdapter) getAdapter()).getItems().clear();
                initPaginationAdapter(onLoadingMore);
            } else {
                paginationAdapter.resetPagination();
            }
        }
    }

    public int getMaxHeight() {
        return mMaxHeight;
    }

    public void setMaxHeight(int mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
    }

    int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (!isInEditMode()) {
            if (sholudSetMaxHeight()) {
                heightSpec = MeasureSpec.makeMeasureSpec(dpToPx(160), MeasureSpec.AT_MOST);
            }
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    private boolean sholudSetMaxHeight() {
        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
                if (((LinearLayout.LayoutParams) getLayoutParams()).weight == 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            if (emptyResourceId != 0) {
                emptyView = ((View) getParent()).findViewById(emptyResourceId);
                emptyView.setVisibility(GONE);
            }
        }
    }

    private void initPaginationAdapter(final LoadMoreCallback onLoadingMore) {
        paginationAdapter = new PaginationAdapter(this, new LoadMoreCallback() {
            @Override
            public void onLoadMore(final int page) {
                if (isFirstTime) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onLoadingMore.onLoadMore(page);
                        }
                    }, 300);
                    isFirstTime = false;
                } else {
                    onLoadingMore.onLoadMore(page);
                }
            }
        });
    }


}