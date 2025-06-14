package ui.addalbum;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.recordsapp.R;
import com.northcoders.recordsapp.databinding.ActivityAddNewAlbumBinding;

import model.Album;
import model.Artist;
import ui.mainactivity.MainActivityViewModel;

public class AddNewAlbumActivity extends AppCompatActivity {
    private ActivityAddNewAlbumBinding binding;
    private MainActivityViewModel viewModel;
    private AddAlbumClickHandlers handlers;
    private Album album;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_album);
        album = new Album();
        if (album.getArtist() == null) {
            Artist artist = new Artist();
            artist.setArtistName(null);
            artist.setNationality(null);
            artist.setBirth_year(0);
            artist.setHitSong(null);
            album.setArtist(artist);
        }
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_new_album);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        handlers = new AddAlbumClickHandlers(album,this,viewModel);
        binding.setAlbum(album);
        binding.setClickHandler(handlers);


    }

}