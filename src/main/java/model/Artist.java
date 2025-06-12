package model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.northcoders.recordsapp.BR;

public class Artist extends BaseObservable implements Parcelable {
    Long id;
    String artistName;
    int birth_year;
    String nationality;
    String hitSong;

    public Artist(Long id, String artistName, int birth_year, String nationality, String hitSong) {
        this.id = id;
        this.artistName = artistName;
        this.birth_year = birth_year;
        this.nationality = nationality;
        this.hitSong = hitSong;
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        artistName = in.readString();
        birth_year = in.readInt();
        nationality = in.readString();
        hitSong = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Bindable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
        notifyPropertyChanged(BR.artistName);
    }

    @Bindable
    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
        notifyPropertyChanged(BR.birth_year);
    }

    @Bindable
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
        notifyPropertyChanged(BR.nationality);
    }

    @Bindable
    public String getHitSong() {
        return hitSong;
    }

    public void setHitSong(String hitSong) {
        this.hitSong = hitSong;
        notifyPropertyChanged(BR.hitSong);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(artistName);
        parcel.writeInt(birth_year);
        parcel.writeString(nationality);
        parcel.writeString(hitSong);
    }
}
