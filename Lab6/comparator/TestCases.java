import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      Comparator<Song> artistCompare = new ArtistComparator();
      int result1 = artistCompare.compare(songs[0], songs[1]);
      assertTrue(result1 < 0);
      int result2 = artistCompare.compare(songs[3], songs[7]);
      assertEquals(0, result2);
      int result3 = artistCompare.compare(songs[6], songs[2]);
      assertTrue(result3 > 0);
   }

   @Test
   public void testLambdaTitleComparator()
   {
      Comparator<Song> songComparator = (song1, song2) -> {return song1.getTitle().compareTo(song2.getTitle());};
      int result1 = songComparator.compare(songs[0], songs[1]);
      assertTrue(result1 > 0);
      int result2 = songComparator.compare(songs[5], songs[6]);
      assertTrue(result2 < 0);
      int result3 = songComparator.compare(songs[3], songs[7]);
      assertEquals(0, result3);
   }

   @Test
   public void testYearExtractorComparator()
   {
      Comparator<Song> yearComparator = (Comparator.comparingInt(Song::getYear)).reversed();
      int result1 = yearComparator.compare(songs[1], songs[2]);
      assertTrue(result1 > 0);
      int result2 = yearComparator.compare(songs[0], songs[1]);
      assertEquals(result2, 0);
      int result3 = yearComparator.compare(songs[4], songs[7]);
      assertTrue(result3 < 0);
   }

   @Test
   public void testComposedComparator()
   {
     Comparator<Song> artistComparator = new ArtistComparator();
      Comparator<Song> yearComparator = (Comparator.comparingInt(Song::getYear).reversed());
      Comparator<Song> composedComparator = new ComposedComparator(artistComparator, yearComparator);
      int result1 = composedComparator.compare(songs[3], songs[7]);
      assertTrue(result1 < 0);
      int result2 = composedComparator.compare(songs[1], songs[4]);
      assertTrue(result2 > 0);
      Song song1 = new Song("Smash Mouth", "All Star", 2001);
      Song song2 = new Song("Smash Mouth", "All Star", 2001);
      int result3 = composedComparator.compare(song1, song2);
      assertEquals(0, result3);
   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> comp1 = (song1, song2) -> {return song1.getTitle().compareTo(song2.getTitle());};
      Comparator<Song> comp2 = comp1.thenComparing(new ArtistComparator());
      int result1 = comp2.compare(songs[3], songs[5]);
      assertTrue(result1 > 0);
      int result2 = comp2.compare(songs[5], songs[7]);
      assertTrue(result2 < 0);
      int result3 = comp2.compare(songs[3], songs[7]);
      assertEquals(0, result3);
   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      Comparator<Song> comp1 = new ArtistComparator();
      Comparator<Song> comp2 = comp1.thenComparing((song1, song2) -> {return song1.getTitle().compareTo(song2.getTitle());});
      Comparator<Song> comp3 = comp2.thenComparing((Comparator.comparingInt(Song::getYear))
      songList.sort(comp3);

      assertEquals(songList, expectedList);
   }
}
