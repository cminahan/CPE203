import java.util.Comparator;

public class ComposedComparator implements Comparator<Song> {
    Comparator<Song> c1;
    Comparator<Song> c2;

    public ComposedComparator(Comparator<Song> c1, Comparator<Song> c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public Comparator<Song> getC1() {return this.c1;}

    public Comparator<Song> getC2() {return this.c2;}

    public int compare(Song s1, Song s2) {
        if (c1.compare(s1, s2) != 0)
            return c1.compare(s1, s2);
        return c2.compare(s1, s2);
    }
}

