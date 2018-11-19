//package com.example.mdbook;
//
//import android.support.v4.app.ListFragment;
//import android.view.View;
//import android.widget.PopupMenu;
//
//public class CustomFragment extends ListFragment implements View.OnClickListener{
//
//
//    @Override
//    public void onClick(final View v) {
//        v.post(new Runnable() {
//            @Override
//            public void run() {
//                showPopupMenu(v);
//            }
//        });
//    }
//
//    private void showPopupMenu(View view) {
//
//        PopupMenu popup = new PopupMenu(getActivity(), view);
//
//        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
//
//        popup.show();
//    }