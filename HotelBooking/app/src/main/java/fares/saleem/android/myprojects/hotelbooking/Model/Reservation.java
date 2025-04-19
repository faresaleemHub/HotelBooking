package fares.saleem.android.myprojects.hotelbooking.Model;

public class Reservation {

    private String id,userID,hotelID;
    //columns
    public static String COL_ID = "id";
    public static String COL_ID_USER = "userID";
    public static String COL_ID_HOTEL = "hotelID";

    //table
    public static final String Tabel_Name = "Reservation";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + Tabel_Name + " (" +
                    COL_ID + " integer primary key autoincrement , " +
                    COL_ID_USER + " text , " +
                    COL_ID_HOTEL + " text );";

    //drop table
    public static final String DROP_TABLE = "drop table if exists " + Tabel_Name;

    public Reservation(String id, String userID, String hotelID) {
        this.id = id;
        this.userID = userID;
        this.hotelID = hotelID;
    }

    public Reservation(String userID, String hotelID) {
        this.userID = userID;
        this.hotelID = hotelID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }
}
