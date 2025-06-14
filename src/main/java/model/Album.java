package model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.northcoders.recordsapp.BR;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Album extends BaseObservable implements Parcelable {
    private Long id;
    private String album_name;
    private Integer album_year;
    private Integer price;
    private Integer stock;
    private Integer sales;
    private Artist artist;


    public Album(Long id,String name, Artist artist, Integer album_year, Integer price, Integer stock,Integer sales) {
        this.id = id;
        this.album_name = name;
        this.artist = artist;
        this.album_year = album_year;
        this.price = price;
        this.stock = stock;
        this.sales = sales;
    }

    public Album() {
    }

    protected Album(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        album_name = in.readString();
        if (in.readByte() == 0) {
            album_year = null;
        } else {
            album_year = in.readInt();
        }
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        if (in.readByte() == 0) {
            stock = null;
        } else {
            stock = in.readInt();
        }
        if (in.readByte() == 0) {
            sales = null;
        } else {
            sales = in.readInt();
        }
        artist = in.readParcelable(Artist.class.getClassLoader());
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Bindable
    public String getName() {
        return album_name;
    }


    public void setName(String name) {
        this.album_name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        notifyPropertyChanged(BR.artist);
    }

    @Bindable
    public Integer getAlbum_year() {
        return album_year;
    }

    public void setAlbum_year(Integer album_year) {
        this.album_year = album_year;
        notifyPropertyChanged(BR.album_year);
    }

    @Bindable
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
        notifyPropertyChanged(BR.stock);
    }

    @Bindable
    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
        notifyPropertyChanged(BR.sales);
    }

    @Bindable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @BindingAdapter("bindIntAsString")
    public static void bindIntAsString(TextView view, Integer value) {
        String currentText = view.getText().toString();
        if (value == null) {
            view.setText("");
        }
        else if (value == 0) {
            // Set "0" only if it's not already displayed
            if (!"0".equals(currentText)) {
                view.setText("0");
            }
        } else {
            // Set the value only if it's different from the current text
            String newText = String.valueOf(value);
            if (!newText.equals(currentText)) {
                view.setText(newText);
            }
        }
    }

    @InverseBindingAdapter(attribute = "bindIntAsString", event = "bindIntAsStringAttrChanged")
    public static Integer getIntFromString(TextView view) {
        String text = view.getText().toString();
        if (text.isEmpty()) {
            return null; // Return null for an empty input
        }
        try {
            return Integer.parseInt(view.getText().toString());
        } catch (NumberFormatException e) {
            return null; // Default value if parsing fails
        }
    }

    @BindingAdapter("bindIntAsStringAttrChanged")
    public static void setBindIntAsStringListener(TextView view, InverseBindingListener listener) {
        if (listener != null) {
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    listener.onChange();
                }
            });
        }
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
        parcel.writeString(album_name);
        if (album_year == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(album_year);
        }
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(price);
        }
        if (stock == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(stock);
        }
        if (sales == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sales);
        }
        parcel.writeParcelable(artist, i);
    }
}


