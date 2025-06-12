package ui.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.recordsapp.R;
import com.northcoders.recordsapp.databinding.ActivityMainBinding;
import com.northcoders.recordsapp.databinding.AlbumItemBinding;

import java.util.List;

import model.Album;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> albums;

    private RecyclerViewInterface recyclerViewInterface;

    public AlbumAdapter(List<Album> albums,RecyclerViewInterface recyclerViewInterface) {
        this.albums = albums;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlbumItemBinding albumItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.album_item,
                parent,
                false
        );
        return new AlbumViewHolder(albumItemBinding,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
       Album album = albums.get(position);
       holder.albumItemBinding.setAlbum(album);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder{
        private AlbumItemBinding albumItemBinding;

        private RecyclerViewInterface recyclerViewInterface;

        public AlbumViewHolder(AlbumItemBinding albumItemBinding,RecyclerViewInterface recyclerViewInterface) {
            super(albumItemBinding.getRoot());
            this.albumItemBinding = albumItemBinding;
            this.recyclerViewInterface = recyclerViewInterface;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

        }


    }

}
