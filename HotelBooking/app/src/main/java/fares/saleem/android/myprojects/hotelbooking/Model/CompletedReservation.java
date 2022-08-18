package fares.saleem.android.myprojects.hotelbooking.Model;

public class CompletedReservation {

    private String id, hotel_id, user_id;
    /*
        hotel_id: the id of hotel that reserved and the reservation completed
        uder_id: the id of that user who completed his reservation
    */

    //columns
    public static String COL_ID = "id";
    public static String COL_HOTEL_ID = "hotel_id ";
    public static String COL_USER_ID = "user_id";

    //table
    public static final String Tabel_Name = "CompletedReservations";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + Tabel_Name + " (" +
                    COL_ID + " integer primary key autoincrement , " +
                    COL_USER_ID + " text , " +
                    COL_HOTEL_ID + " text );";

    //drop table
    public static final String DROP_TABLE = "drop table if exists " + Tabel_Name;

    public CompletedReservation(String id, String hotel_id, String user_id) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.user_id = user_id;
    }
    public CompletedReservation(String hotel_id, String user_id) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.user_id = user_id;
    }
    public CompletedReservation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static void setColId(String colId) {
        COL_ID = colId;
    }
}
