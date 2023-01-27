import java.util.ArrayList;

public class SystemRequirements {
    private static int maximumErrorWithoutGettingBlocked, discountPercentage, blockTime, wrongLogInCounter;
    private static String title;
    private static ArrayList<User> users;
    private static ArrayList<Hall> halls, currentHalls;
    private static ArrayList<Seat> seats;
    private static ArrayList<Film> films, extraFilms;
    private static boolean blockLogIn;
    private static User currentUser;
    private static Film currentFilm;
    public static Hall currentHall;

    public static void removeFilmFromList (Film f) {
        int removeIndex = 0;
        for (int i = 0; i < films.size(); i++) {
            if ((films.get(i)).equals(f)) {
                removeIndex = i;
                break;
            }
        }
        films.remove(removeIndex);
    }

    public static void removeHallFromList (Hall h, ArrayList<Hall> list) {
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getHallName()).equals(h.getHallName())) {
                list.remove(i);
                break;
            }
        }
    }

    public static void setMaximumErrorWithoutGettingBlocked(int maximumErrorWithoutGettingBlocked) {
        SystemRequirements.maximumErrorWithoutGettingBlocked = maximumErrorWithoutGettingBlocked;
    }

    public static void setDiscountPercentage(int discountPercentage) {
        SystemRequirements.discountPercentage = discountPercentage;
    }

    public static void setBlockTime(int blockTime) {
        SystemRequirements.blockTime = blockTime;
    }

    public static void setTitle(String title) {
        SystemRequirements.title = title;
    }

    public static void setUsers(ArrayList<User> users) {
        SystemRequirements.users = users;
    }

    public static void setHalls(ArrayList<Hall> halls) {
        SystemRequirements.halls = halls;
    }

    public static void setSeats(ArrayList<Seat> seats) {
        SystemRequirements.seats = seats;
    }

    public static void setFilms(ArrayList<Film> films) {
        SystemRequirements.films = films;
    }

    public static void setBlockLogIn(boolean b) {
        blockLogIn = b;
    }

    public static void setCurrentUser(User currentUser) {
        SystemRequirements.currentUser = currentUser;
    }

    public static void setCurrentFilm(Film c){
        currentFilm = c;
    }

    public static void setCurrentHalls(ArrayList<Hall> currentHalls) {
        SystemRequirements.currentHalls = currentHalls;
    }

    public static void setCurrentHall(Hall currentHall) {
        SystemRequirements.currentHall = currentHall;
    }

    public static void increaseWrongLogInCounter () {
        wrongLogInCounter ++;
    }

    public static ArrayList<Hall> getCurrentHalls() {
        return currentHalls;
    }

    public static Film getCurrentFilm() {
        return currentFilm;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getWrongLogInCounter() {
        return wrongLogInCounter;
    }

    public static boolean isBlockLogIn() {
        return blockLogIn;
    }

    public static int getMaximumErrorWithoutGettingBlocked() {
        return maximumErrorWithoutGettingBlocked;
    }

    public static int getDiscountPercentage() {
        return discountPercentage;
    }

    public static int getBlockTime() {
        return blockTime;
    }

    public static String getTitle() {
        return title;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<Hall> getHalls() {
        return halls;
    }

    public static ArrayList<Seat> getSeats() {
        return seats;
    }

    public static ArrayList<Film> getFilms() {
        return films;
    }

    public static Hall getCurrentHall() {
        return currentHall;
    }

    public static void addUserToUsers(User x) {
        users.add(x);
    }

    public static void addFilmToFilms(Film x) {
        films.add(x);
    }

    public static void addHallToHalls(Hall x) {
        halls.add(x);
    }

    public static void addSeatToSeats(Seat x) {
        seats.add(x);
    }

    public static ArrayList<Film> getExtraFilms() {
        return extraFilms;
    }

    public static void setExtraFilms(ArrayList<Film> extraFilms) {
        SystemRequirements.extraFilms = extraFilms;
    }

    public static void setWrongLogInCounter(int wrongLogInCounter) {
        SystemRequirements.wrongLogInCounter = wrongLogInCounter;
    }
}
