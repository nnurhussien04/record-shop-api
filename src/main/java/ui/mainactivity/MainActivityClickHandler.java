package ui.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ui.addalbum.AddNewAlbumActivity;
import ui.updatealbum.UpdateMainActivity;

public class MainActivityClickHandler {
    private Context context;

    public MainActivityClickHandler(Context context) {
        this.context = context;
    }

    public void onFABClicked(View view){
        Intent intent = new Intent(view.getContext(), AddNewAlbumActivity.class);
        context.startActivity(intent);
    }




}
