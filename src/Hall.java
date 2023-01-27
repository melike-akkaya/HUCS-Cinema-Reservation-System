import java.util.ArrayList;

public class Hall {
    private Film film;
    private final String hallName;
    private final int price;
    private final int row;
    private final int column;

    Hall(String[] commands, ArrayList<Film> films) {
        for (Film i : films) {
            String filmName = String.valueOf(i);
            if (filmName.equals(commands[1])) {
                this.film = i;
                break;
            }
        }
        this.hallName = commands[2];
        this.price = Integer.parseInt(commands[3]);
        this.row = Integer.parseInt(commands[4]);
        this.column = Integer.parseInt(commands[5]);
    }

    public Film getFilm() {
        return film;
    }

    public String getHallName() {
        return hallName;
    }

    public int getPrice() {
        return price;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return this.hallName;
    }

}
