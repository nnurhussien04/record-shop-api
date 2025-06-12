package ui.mainactivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.recordsapp.R;
import com.northcoders.recordsapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.AlbumRepository;
import ui.updatealbum.UpdateMainActivity;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    private RecyclerView recyclerView;
    private ArrayList<Album> albums;
    private AlbumAdapter albumAdapter;
    private MainActivityViewModel activityViewModel;
    private ActivityMainBinding activityMainBinding;
    public String identifier;
    private MainActivityClickHandler handler;
    public Album selectedAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        /*Application application = new Application();
        (AlbumRepository repository = new AlbumRepository(application);
        repository.getMutableLiveData();*/
        activityMainBinding = DataBindingUtil.setContentView(
                this,R.layout.activity_main
        );
        activityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        handler = new MainActivityClickHandler(this);
        activityMainBinding.setClickFunction(handler);
        getAllAlbums();
    }



    private void getAllAlbums(){
    activityViewModel.DataResult().observe(
            this,new Observer<List<Album>>() {

                @Override
                public void onChanged(List<Album> albumFromLiveData) {
                    albums = (ArrayList<Album>) albumFromLiveData;
                    if(albums==null){
                        albums = new ArrayList<>();
                    }

                    displayInRecyclerView();
                }

            }

    );
    }

    private void displayInRecyclerView(){
        recyclerView = activityMainBinding.recyclerview;
        albumAdapter = new AlbumAdapter(albums,this);
        recyclerView.setAdapter(albumAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        albumAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, UpdateMainActivity.class);
        //selectedAlbum = albums.get(position);
        intent.putExtra("ALBUM_KEY",albums.get(position));
        startActivity(intent);


    }


}