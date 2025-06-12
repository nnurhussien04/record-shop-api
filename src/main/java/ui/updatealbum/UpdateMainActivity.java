package ui.updatealbum;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.recordsapp.R;
import com.northcoders.recordsapp.databinding.UpdateActivityLayoutBinding;

import model.Album;
import ui.mainactivity.MainActivity;
import ui.mainactivity.MainActivityClickHandler;
import ui.mainactivity.MainActivityViewModel;

public class UpdateMainActivity extends AppCompatActivity {
    private UpdateActivityLayoutBinding updateBinding;
    private UpdateAlbumClickHandlers handler;
    private Album album;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.update_activity_layout);
        Intent intent = getIntent();
        album = intent.getParcelableExtra("ALBUM_KEY");
        String originalArtistName = album.getArtist().getArtistName();
        updateBinding = DataBindingUtil.setContentView(
                this,R.layout.update_activity_layout
        );
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        Log.i("Album Object", "onCreate: " + album.getName() + " " + album.getId());
        handler = new UpdateAlbumClickHandlers(album,viewModel,this,originalArtistName);
        updateBinding.setAlbum(album);
        updateBinding.setHandler(handler);


    }
}