package ui.addalbum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.northcoders.recordsapp.R;

import model.Album;
import model.Artist;
import ui.mainactivity.MainActivity;
import ui.mainactivity.MainActivityViewModel;

public class AddAlbumClickHandlers {
    private Album album;
    private Context context;
    private MainActivityViewModel viewModel;

    public AddAlbumClickHandlers(Album album, Context context, MainActivityViewModel viewModel) {
        this.album = album;
        this.context = context;
        this.viewModel = viewModel;
    }

    public void clickForNewAlbum(View view){
        if(album.getName() == null || album.getArtist().getArtistName() == null || album.getAlbum_year() < 0 || album.getSales() < 0 || album.getStock() < 0 || album.getPrice() < 0){
            Toast.makeText(context,"Album has no value please try again",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            Artist artist = new Artist();
            artist.setArtistName(album.getArtist().getArtistName().trim());
            album.setArtist(artist);
            viewModel.makeAlbum(album);
            Toast.makeText(context,"Album Added",Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        }
    }

    public void clickForHomepage(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        context.startActivity(intent);
    }

}
