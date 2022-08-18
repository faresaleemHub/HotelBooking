package fares.saleem.android.myprojects.hotelbooking.Model;

public class User {

    String id,username,email,Date,phone,gender,location,passowrd;

    //columns
    public static String COL_ID = "id";
    public static String COL_NAME = "name";
    public static String COL_Password = "pass";
    public static String COL_Email = "email";
    public static String COL_Phone = "phone";
    public static String COL_Gender = "gender";
    public static String COL_Location = "location";
    public static String COL_DOB = "date";

    //table
    public static final String Tabel_Name = "User";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + Tabel_Name + " (" +
                    COL_ID + " integer primary key autoincrement , " +
                    COL_NAME + " text , " +
                    COL_Password + " text , " +
                    COL_Email + " text , " +
                    COL_Phone + " text , " +
                    COL_Gender + " text , " +
                    COL_DOB + " text , " +
                    COL_Location + " text);";

    //drop table
    public static final String DROP_TABLE = "drop table if exists " + Tabel_Name;

    public User(){}

    public User(String email, String username, String Date, String phone, String gender, String location, String passowrd) {
        this.username = username;
        this.email = email;
        this.Date=Date;
        this.phone = phone;
        this.gender = gender;
        this.location = location;
        this.passowrd = passowrd;
    }
    public User(String id,String email, String username, String Date, String phone, String gender, String location, String passowrd) {
        this.username = username;
        this.email = email;
        this.Date=Date;
        this.phone = phone;
        this.gender = gender;
        this.location = location;
        this.passowrd = passowrd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return location;
    }

    public void setCountry(String country) {
        this.location = location;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd1) {
        this.passowrd = passowrd;
    }

}
