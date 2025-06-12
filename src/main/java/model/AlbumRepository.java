package model;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.AlbumApiService;
import service.RetrofitInstance;


public class AlbumRepository {
    private MutableLiveData<List<Album>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public AlbumRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Album>> getMutableLiveData() {
        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<List<Album>> call = albumApiService.listAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                List<Album> albums = response.body();
                mutableLiveData.setValue(albums);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.i("GET Request",t.getMessage());
            }
        });
        return mutableLiveData;
    }

    public void addAlbum(Album album){
        Log.i("Access", "addAlbum: Method accessed ");
        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<Album> albumCall = albumApiService.createAlbum(album);
        albumCall.enqueue(
                new Callback<Album>() {
                    @Override
                    public void onResponse(Call<Album> call, Response<Album> response) {
                        Toast.makeText(application.getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Album> call, Throwable t) {
                        Toast.makeText(application.getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void updateAlbum(long albumID, Album album){
        Log.i("Album Object", "updateAlbum2: " + album.getName() + " ID: " + String.valueOf(albumID));
        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<Album> updatedAlbumCall = albumApiService.updateAlbum(albumID,album);
        updatedAlbumCall.enqueue(
                new Callback<Album>() {
                    @Override
                    public void onResponse(Call<Album> call, Response<Album> response) {
                        Toast.makeText(application.getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Album> call, Throwable t) {
                        Toast.makeText(application.getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void deleteAlbum(long albumID){
        AlbumApiService albumApiService = RetrofitInstance.getService();
        Call<Album> deletedAlbumCall = albumApiService.deleteAlbum(albumID);
        deletedAlbumCall.enqueue(
                new Callback<Album>() {
                    @Override
                    public void onResponse(Call<Album> call, Response<Album> response) {
                        Toast.makeText(application.getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Album> call, Throwable t) {
                        Toast.makeText(application.getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


}
