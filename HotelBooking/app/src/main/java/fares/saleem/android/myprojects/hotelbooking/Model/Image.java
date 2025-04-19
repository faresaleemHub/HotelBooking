package fares.saleem.android.myprojects.hotelbooking.Model;

import android.graphics.Bitmap;

public class Image {

    //columns
    public static String COL_ID = "id";
    public static String COL_HOTEL_FORIGN_ID = "hotel_forignkey_id";
    public static String COL_IMG = "image";

    //table
    public static final String Tabel_Name = "Image";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + Tabel_Name + " (" +
                    COL_ID + " integer primary key autoincrement , " +
                    COL_HOTEL_FORIGN_ID + " text , " +
                    COL_IMG + " BLOB );";

    //drop table
    public static final String DROP_TABLE = "drop table if exists " + Tabel_Name;


    private String hotel_forignkey_id;
    private Bitmap image;

    public Image(String hotel_forignkey_id, Bitmap image) {
        this.hotel_forignkey_id = hotel_forignkey_id;
        this.image = image;
    }

    public String gethotel_forignkey_id() {
        return hotel_forignkey_id;
    }

    public void sethotel_forignkey_id(String hotel_forignkey_id) {
        this.hotel_forignkey_id = hotel_forignkey_id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
