package com.bmapps.bmnews.ui;

public interface BaseView {
    default void showLoadingWithMessage(String loadingMessge) {

    }

    default void showFetchingLayoutWithMessage(String message) {

    }

    default void hideFetchingLayout() {

    }

    default void showNetworkErrorMessage() {

    }

    default void showToast(String message) {

    }

    default void unauthorizedQuitApp() {

    }

    default void objectNotFound() {

    }

    default void hideLoader() {

    }

    default void showUserErrorDialog(String message) {

    }

    default void goBack() {

    }
}
