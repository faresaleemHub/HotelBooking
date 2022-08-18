package fares.saleem.android.myprojects.hotelbooking.Model;

public class Hotel {

    String id, HotelName, ownerName, ownerPhone, location,
            description,
            price,
            AvailableDay;

    boolean reserved, hasInternet, hasGarage, hasGym, hasSwimmingPool, hasWeddingHall;
    int img;

    //columns =>13
    public static String COL_ID = "id";
    public static String COL_HOTEL_NAME = "name";
    public static String COL_OWNER_NAME = "owner_name";
    public static String COL_OWNER_PHONE = "phone";
    public static String COL_LOCATION = "location";
    public static String COL_DESCRIPTION = "description";
    public static String COL_RESERVE = "reserve";
    public static String COL_INTERNET = "internet";
    public static String COL_GARAGE = "garage";
    public static String COL_GYM = "gym";
    public static String COL_SWIMMING_POOL = "swimming_pool";
    public static String COL_WEDDING_Hall = "wedding_pol";
    public static String COL_PRICE = "price";
    public static String COL_AvailableDay = "AvailableDay";
    public static String COL_IMG = "hotel_img";

    //table
    public static final String Tabel_Name = "Hotel";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + Tabel_Name + " (" +
                    COL_ID + " integer primary key autoincrement , " +
                    COL_HOTEL_NAME + " text , " +
                    COL_OWNER_NAME + " text , " +
                    COL_OWNER_PHONE + " text , " +
                    COL_LOCATION + " text , " +
                    COL_DESCRIPTION + " text , " +
                    COL_RESERVE + " text , " +
                    COL_INTERNET + " text , " +
                    COL_GARAGE + " text , " +
                    COL_GYM + " text , " +
                    COL_SWIMMING_POOL + " text , " +
                    COL_PRICE + " text , " +
                    COL_AvailableDay + " text , " +
                    COL_IMG + " text , " +
                    COL_WEDDING_Hall + " text);";

    public Hotel(String id,int img, String hotelName, String ownerName, String ownerPhone, String location,String AvailableDay, String description, String price, boolean reserved, boolean hasInternet, boolean hasGarage, boolean hasGym, boolean hasSwimmingPool, boolean hasWeddingHall) {
        this.id = id;
        HotelName = hotelName;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.location = location;
        this.description = description;
        this.price = price;
        this.reserved = reserved;
        this.hasInternet = hasInternet;
        this.hasGarage = hasGarage;
        this.hasGym = hasGym;
        this.hasSwimmingPool = hasSwimmingPool;
        this.hasWeddingHall = hasWeddingHall;
        this.AvailableDay=AvailableDay;
        this.img=img;
    }
    public Hotel(String id, int img, String HotelName, String location,String AvailableDay, String price){
        this.id=id;
        this.HotelName=HotelName;
        this.img=img;
        this.location=location;
        this.AvailableDay=AvailableDay;
        this.price=price;
    }
    public Hotel( int img,String hotelName, String ownerName, String ownerPhone, String location,String AvailableDay, String description, String price, boolean reserved, boolean hasInternet, boolean hasGarage, boolean hasGym, boolean hasSwimmingPool, boolean hasWeddingHall) {
        HotelName = hotelName;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.location = location;
        this.description = description;
        this.price = price;
        this.reserved = reserved;
        this.hasInternet = hasInternet;
        this.hasGarage = hasGarage;
        this.hasGym = hasGym;
        this.hasSwimmingPool = hasSwimmingPool;
        this.hasWeddingHall = hasWeddingHall;
        this.AvailableDay=AvailableDay;
        this.img=img;
    }

    //drop table
    public static final String DROP_TABLE = "drop table if exists " + Tabel_Name;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isHasInternet() {
        return hasInternet;
    }

    public void setHasInternet(boolean hasInternet) {
        this.hasInternet = hasInternet;
    }

    public boolean isHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public boolean isHasGym() {
        return hasGym;
    }

    public void setHasGym(boolean hasGym) {
        this.hasGym = hasGym;
    }

    public boolean isHasSwimmingPool() {
        return hasSwimmingPool;
    }

    public void setHasSwimmingPool(boolean hasSwimmingPool) {
        this.hasSwimmingPool = hasSwimmingPool;
    }

    public boolean isHasWeddingHall() {
        return hasWeddingHall;
    }

    public void setHasWeddingHall(boolean hasWeddingHall) {
        this.hasWeddingHall = hasWeddingHall;
    }

    public static String getTabel_Name() {
        return Tabel_Name;
    }

    public String getAvailableDay() {
        return AvailableDay;
    }

    public void setAvailableDay(String availableDay) {
        AvailableDay = availableDay;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
