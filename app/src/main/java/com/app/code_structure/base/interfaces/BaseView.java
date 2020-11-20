package com.app.code_structure.base.interfaces;


public interface BaseView {
    /**
     * show error message
     * @param msg  msg to display
     */
    void showErrorMsg(String msg);

    /**
     * show error message
     * @param msg  msg string resource id
     */
    void showErrorMsg(int msg);

    /**
     * show success message
     * @param msg  msg to display
     */
    void showSuccessMsg(String msg);

    /**
     * show error message
     * @param msg  msg string resource id
     */
    void showSuccessMsg(int msg);

    /**
     * show loading indicator
     */
    void showLoading();

    /**
     * hide loading indicator
     */
    void hideLoading();

    /**
     * Check whether we have required permission or not.
     * if not it will ask for permission or display rational msg box if user restricted permission
     * by selecting "do not ask again"
     * @see android.Manifest.permission
     * @param permission @{@link android.Manifest.permission} permission string
     * @param msg  msg string resource id
     * @param permissionListener  listener will be called on permission result
     * */
    //void askPermissionIfRequire(String permission, String msg, Permission.PermissionListener permissionListener);
}
