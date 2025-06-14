package service;

import java.util.List;

import model.Album;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlbumApiService {

    @GET("album")
    Call<List<Album>> listAlbums();

    @POST("album")
    Call<Album> createAlbum(@Body Album album);

    @PUT("album/update/{id}")
    Call<Album> updateAlbum(@Path("id") long albumID, @Body Album album);

    @DELETE("album")
    Call<Boolean> deleteAlbum(@Query("id") long albumID);

}
