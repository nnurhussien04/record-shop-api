package ui.updatealbum;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.northcoders.recordsapp.R;

import model.Album;
import ui.mainactivity.MainActivity;
import ui.mainactivity.MainActivityViewModel;

public class UpdateAlbumClickHandlers {
    public Album album;
    public MainActivityViewModel viewModel;
    public Long albumID;
    public Context context;

    public String originalArtistName;

    public UpdateAlbumClickHandlers(Album album, MainActivityViewModel mainActivityViewModel, Context context, String originalArtistName) {
        this.album = album;
        this.viewModel = mainActivityViewModel;
        this.context = context;
        this.originalArtistName = originalArtistName;
    }

    public void updateButton(View view){
        Album newAlbum = new Album(album.getId(),album.getName(),album.getArtist(),album.getAlbum_year(),album.getPrice(),album.getStock(),album.getSales());
        if(newAlbum.getName() == null || newAlbum.getArtist() == null || newAlbum.getAlbum_year() < 0 || newAlbum.getSales() < 0 || newAlbum.getStock() < 0 || newAlbum.getPrice() < 0){
            Toast.makeText(context,"Album has no value please try again",Toast.LENGTH_SHORT).show();
        }
        else{
            if(newAlbum.getArtist().getArtistName() != originalArtistName){
                newAlbum.getArtist().setId(null);
                newAlbum.getArtist().setBirth_year(0);
                newAlbum.getArtist().setNationality(null);
                newAlbum.getArtist().setHitSong(null);
            }
            newAlbum.getArtist().getArtistName().trim();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            albumID = album.getId();
            intent.putExtra("ALBUM_KEY",newAlbum);
            viewModel.updateAlbum(albumID,newAlbum);
            context.startActivity(intent);
        }
    }

    public void deleteButton(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog dialog = builder.create();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        albumID = album.getId();
                        viewModel.deleteAlbum(albumID);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                })
        ;
        builder.show();


    }

    public void returnButton(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        context.startActivity(intent);
    }
}
