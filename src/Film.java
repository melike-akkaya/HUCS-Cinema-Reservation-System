public class Film {
    private String filmName;
    private String trailer;
    private int duration;

    // for extra
    private String extraFilmText;
    private String extraFilmDay;

    Film (String[] commands) {
        filmName = commands[1];
        trailer = editTrailersPath(commands[2]);
        duration = Integer.parseInt(commands[3]);
    }

    // constructor for the extra feature films
    Film (int day, String name, int year, String path, int time) {
        this.filmName = name;
        int anniversary = 2022-year;
        this.extraFilmText = filmName + "\nwas in theaters " + anniversary + " years ago today.";
        this.trailer = editTrailersPath(path);
        this.duration = time;

        if (day % 7 == 2) {
            this.extraFilmDay = day + " May 2022\nMonday";
        }
        else if (day % 7 == 3) {
            this.extraFilmDay = day + " May 2022\nTuesday";
        }
        else if (day % 7 == 4) {
            this.extraFilmDay = day + " May 2022\nWednesday";
        }
        else if (day % 7 == 5) {
            this.extraFilmDay = day + " May 2022\nThursday";
        }
        else if (day % 7 == 6) {
            this.extraFilmDay = day + " May 2022\nFriday";
        }
        else if (day % 7 == 0) {
            this.extraFilmDay = day + " May 2022\nSaturday";
        }
        else if (day % 7 == 1) {
            this.extraFilmDay = day + " May 2022\nSunday";
        }
    }

    // to arrange the path of trailr
    // e
    public static String editTrailersPath(String path) {
        String truePath;
        String[] trailerControl = path.split("/");
        if (trailerControl[0].equals("assets"))
            truePath = path;
        else
            truePath = "assets/trailers/" + path;
        return truePath;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getTrailer() {
        return trailer;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return this.filmName;
    }

    // for extra feature
    public String getExtraFilmText() {
        return extraFilmText;
    }

    public String getExtraFilmDay() {
        return extraFilmDay;
    }
}