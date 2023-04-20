package com.example.ca4sp;

import java.util.List;

public interface ItemListContract {
    interface View {
        void showItems(List<Item> items);
        void showError(String errorMessage);
    }

    interface Presenter {
        void loadItems();
    }
}

