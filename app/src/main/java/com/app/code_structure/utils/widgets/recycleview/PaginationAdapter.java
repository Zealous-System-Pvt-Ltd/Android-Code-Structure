package com.app.code_structure.utils.widgets.recycleview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.code_structure.R;
import com.app.code_structure.base.BaseRecycleViewAdapter;
import com.app.code_structure.base.interfaces.LoadMoreCallback;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;


public class PaginationAdapter implements LoadingListItemCreator {
    private BaseRecycleViewAdapter adapter;
    private String defaultLoadingText = "loading...";
    private String defaultErrorText = "server connection error.";
    private String noInternetConnectionErrorText = "No network connection.";
    private String noRecordsFoundText = "";
    private String messageText = defaultLoadingText;
    private String btnText = "retry";
    private boolean isMessageVisible = true;
    private boolean isProgressVisible = true;
    private boolean isButtonVisible = false;
    private LoadMoreCallback onLoadMoreListener;
    private int CURRUNT_STATUS = 0;
    public static final int STATE_LOADING = 0;
    public static final int STATE_LOADING_ERROR = 1;
    public static final int STATE_LOADING_SUCCESS = 2;
    public static final int STATE_LOADING_SUCCESS_END_OF_LISTS = 3;

    Paginate paginate;
    boolean isLoading = false;
    int pageNumber = 1;

    public PaginationAdapter(RecyclerView recycleView, LoadMoreCallback onLoadMore) {
        try {
            this.adapter = ((BaseRecycleViewAdapter) recycleView.getAdapter());
            paginate = Paginate.with(recycleView, new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    if (onLoadMoreListener != null) {

                        try {
                            setLoadingState(PaginationAdapter.STATE_LOADING);
                        } catch (Exception e) {

                        }

                        onLoadMoreListener.onLoadMore(pageNumber);

                    }
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            })
                    .setLoadingListItemCreator(this).build();
            this.onLoadMoreListener = onLoadMore;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_vertical_progress, parent, false);
        return new CustomViewHolder(view);
    }

    public BaseRecycleViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomViewHolder vh = (CustomViewHolder) holder;
        if (isMessageVisible) {
            vh.message.setText(messageText);
            vh.message.setVisibility(View.VISIBLE);
        } else {
            vh.message.setVisibility(View.GONE);
        }
        if (isButtonVisible) {
            vh.btnRetry.setText(btnText);
            vh.btnRetry.setVisibility(View.VISIBLE);
            vh.btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onLoadMoreListener != null) {
                        setLoadingState(PaginationAdapter.STATE_LOADING);
                        onLoadMoreListener.onLoadMore(pageNumber);
                    }
                }
            });
        } else {
            vh.btnRetry.setVisibility(View.GONE);
        }
        if (isProgressVisible) {
            vh.progressBar.setVisibility(View.VISIBLE);
        } else {
            vh.progressBar.setVisibility(View.GONE);
        }
    }

    public void setMessageText(String msg) {
        this.messageText = msg;
    }

    public void setMessageVisible(boolean isVisible) {
        this.isMessageVisible = isVisible;
    }

    public void setProgressVisible(boolean isVisible) {
        this.isProgressVisible = isVisible;
    }

    public void setButtonVisible(boolean isVisible) {
        this.isButtonVisible = isVisible;
    }

    public void setButtonText(String text) {
        this.btnText = text;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Paginate getPaginate() {
        return paginate;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void resetPagination() {
        this.pageNumber = 1;
        setLoading(false);
        adapter.getItems().clear();
        adapter.notifyDataSetChanged();
    }

    public boolean isLoading() {
        return isLoading;
    }

    boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setLoadingState(int loadingState) {
        this.CURRUNT_STATUS = loadingState;
        switch (CURRUNT_STATUS) {
            case STATE_LOADING:
                isLoading = true;
                setButtonVisible(false);
                setProgressVisible(true);
                setMessageVisible(true);
                setMessageText(defaultLoadingText);
                break;
            case STATE_LOADING_ERROR:
                isLoading = true;
                setButtonVisible(false);
                setProgressVisible(false);
                setMessageVisible(true);
                if (isNetworkConnected(adapter.activity)) {
                    setMessageText(defaultErrorText);
                } else {
                    setMessageText(noInternetConnectionErrorText);
                }
                break;
            case STATE_LOADING_SUCCESS:
                isLoading = false;
                setButtonVisible(false);
                setProgressVisible(true);
                setMessageVisible(true);
                setMessageText(defaultLoadingText);
                pageNumber = pageNumber + 1;
                break;
            case STATE_LOADING_SUCCESS_END_OF_LISTS:
                isLoading = true;
                setButtonVisible(false);
                setProgressVisible(false);
                Log.e("TAG", "setLoadingState:==== "+adapter.getItems().size() );
                if (adapter.getItems().size() <= 0) {
                    setMessageVisible(true);
                    setMessageText(noRecordsFoundText);
                } else {
                    setMessageVisible(false);
                    paginate.unbind();
                }
                break;
        }

        try {
            adapter.notifyDataSetChanged();
        } catch (Exception e) {

        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout progressContainer;
        private TextView message;
        private ProgressBar progressBar;
        private Button btnRetry;

        public CustomViewHolder(View view) {
            super(view);
            findViews(view);
        }

        private void findViews(View view) {
            progressContainer = (LinearLayout) view.findViewById(R.id.progress_container);
            message = (TextView) view.findViewById(R.id.message);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            btnRetry = (Button) view.findViewById(R.id.btn_retry);
        }
    }
}
