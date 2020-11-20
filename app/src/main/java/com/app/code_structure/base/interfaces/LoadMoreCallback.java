package com.app.code_structure.base.interfaces;

public interface LoadMoreCallback {
        /**
         * Called when next page of data needs to be loaded.
         */
        void onLoadMore(int page);
}