import java.util.ArrayList;
import java.util.Objects;

public class Seat {
    private Film film;
    private Hall hall;
    private final int rowOfSeat;
    private final int columnOfSeat;
    private User user;
    private int price;
    private Boolean isEmpty;

    Seat(String[] commands, ArrayList<Film> films, ArrayList<Hall> halls, ArrayList<User> users) {
        for (Film i : films) {
            String filmName = String.valueOf(i);
            if (filmName.equals(commands[1])) {
                this.film = i;
                break;
            }
        }
        for (Hall i : halls) {
            String hallName = String.valueOf(i);
            if (hallName.equals(commands[2])) {
                this.hall = i;
                break;
            }
        }
        this.rowOfSeat = Integer.parseInt(commands[3]);
        this.columnOfSeat = Integer.parseInt(commands[4]);

        if (!Objects.equals(commands[5], "null")) {
            this.isEmpty = false;
            for (User i : users) {
                String userName = String.valueOf(i);
                if (userName.equals(commands[5])) {
                    this.user = i;
                    break;
                }
            }
        }
        else {
            this.isEmpty = true;
            this.user = null;
        }
        this.price = Integer.parseInt(commands[6]);
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public int getRowOfSeat() {
        return rowOfSeat;
    }

    public int getColumnOfSeat() {
        return columnOfSeat;
    }

    public int getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean isIfEmpty() {
        return isEmpty;
    }

    public void setIfEmpty (Boolean empty) {
        isEmpty = empty;
    }
}
