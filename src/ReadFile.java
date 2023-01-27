import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    // the class where I read files and update the backup.dat file

    public static void readBackup(String fileName) throws FileNotFoundException {
        ArrayList<User> userArrayList = new ArrayList<>();
        ArrayList<Hall> hallArrayList = new ArrayList<>();
        ArrayList<Seat> seatArrayList = new ArrayList<>();
        ArrayList<Film> filmArrayList = new ArrayList<>();

        File file = new File (fileName);
        Scanner scan = new Scanner(file);
        while(scan.hasNext()) {
            String [] commands = scan.nextLine().split("\t");


            // to control if it is not an empty line
            if (commands.length < 1) {
                continue;
            }

            switch (commands[0]) {
                case "user": {
                    User x = new User(commands);
                    userArrayList.add(x);
                    break;
                }
                case "film": {
                    Film x = new Film(commands);
                    filmArrayList.add(x);
                    break;
                }
                case "hall": {
                    Hall x = new Hall(commands, filmArrayList);
                    hallArrayList.add(x);
                    break;
                }
                case "seat": {
                    Seat x = new Seat(commands, filmArrayList, hallArrayList, userArrayList);
                    seatArrayList.add(x);
                    break;
                }
            }
        }
        SystemRequirements.setFilms(filmArrayList);
        SystemRequirements.setSeats(seatArrayList);
        SystemRequirements.setHalls(hallArrayList);
        SystemRequirements.setUsers(userArrayList);
    }

    public static void readProperties (String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        while(scan.hasNext()) {
            String [] property = scan.nextLine().split("=");
            switch ((property[0])) {
                case "maximum-error-without-getting-blocked":
                    SystemRequirements.setMaximumErrorWithoutGettingBlocked(Integer.parseInt(property[1]));
                    break;
                case "title":
                    SystemRequirements.setTitle(property[1]);
                    break;
                case "discount-percentage":
                    SystemRequirements.setDiscountPercentage((Integer.parseInt(property[1])));
                    break;
                case "block-time":
                    SystemRequirements.setBlockTime((Integer.parseInt(property[1])));
                    break;
            }
        }
    }

    public static void readExtra (String fileName) throws  FileNotFoundException {
        File file = new File (fileName);
        Scanner scan = new Scanner(file);
        ArrayList<Film> extraFilms = new ArrayList<>();
        while(scan.hasNext()) {
            String [] filmFeature = scan.nextLine().split(",");
            Film extra = new Film(Integer.parseInt(filmFeature[0]), filmFeature[1], Integer.parseInt(filmFeature[2]), filmFeature[3], Integer.parseInt(filmFeature[4]));
            extraFilms.add(extra);
        }
        SystemRequirements.setExtraFilms(extraFilms);
    }

    public static void updateBackUpFile(String fileName) throws IOException {
        // to empty the file
        File file = new File (fileName);
        FileWriter fw = new FileWriter(file);
        fw.write(   "");
        fw.close();

        // to refill the file
        FileWriter writer = new FileWriter(file, true);
        // to write users
        for (User i : SystemRequirements.getUsers()) {
            writer.write("user\t" + i.getUsername() + "\t" + i.getPassword() + "\t" + i.isIfClubMember() + "\t" + i.isIfAdmin() + "\n");
        }
        // in order to write the film, its halls and its seats (for each film)
        for (Film film : SystemRequirements.getFilms()) {
            String [] trailerPath = film.getTrailer().split("/");
            writer.write ("film\t" + film.getFilmName() + "\t" + trailerPath[trailerPath.length - 1] + "\t" + film.getDuration() + "\n");
            for (Hall hall : SystemRequirements.getHalls()) {
                if ((hall.getFilm().getFilmName()).equals(film.getFilmName())) {
                    writer.write("hall\t" + hall.getFilm().getFilmName() + "\t" + hall.getHallName() + "\t" + hall.getPrice() + "\t" + hall.getRow() + "\t" + hall.getColumn() + "\n");
                    for (Seat seat : SystemRequirements.getSeats()) {
                        if ((seat.getHall().getHallName()).equals(hall.getHallName())) {
                            if (seat.getUser() != null) {
                                writer.write("seat\t" + seat.getFilm().getFilmName() + "\t" + seat.getHall().getHallName() + "\t" + seat.getRowOfSeat() +
                                        "\t" + seat.getColumnOfSeat() + "\t" + seat.getUser().getUsername() + "\t" + seat.getPrice() + "\n");
                            } else {
                                writer.write("seat\t" + seat.getFilm().getFilmName() + "\t" + seat.getHall().getHallName() + "\t" + seat.getRowOfSeat() +
                                        "\t" + seat.getColumnOfSeat() + "\tnull\t" + seat.getPrice() + "\n");
                            }
                        }
                    }
                }
            }
        }
        writer.close();
    }
}