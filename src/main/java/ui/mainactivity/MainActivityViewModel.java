package ui.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import model.Album;
import model.AlbumRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private AlbumRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new AlbumRepository(application);
    }

    public LiveData<List<Album>> DataResult(){
        return repository.getMutableLiveData();
    }

    public void makeAlbum(Album album){
        repository.addAlbum(album);
    }

    public void updateAlbum(Long albumID,Album album){
        repository.updateAlbum(albumID,album);
    }

    public void deleteAlbum(Long albumID){
        repository.deleteAlbum(albumID);
    }


}
