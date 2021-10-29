import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   /*  Commands for running the test cases on the commandline:

       Put all your files in the same directory:

            javac *.java
            java org.junit.runner.JUnitCore TestCases

       Or in case you have not modified your bashrc, use the longer version

            javac -cp /home/akeen/public/junit-4.12.jar:/home/akeen/public/hamcrest-core-1.3.jar:. *.java
            java -cp /home/akeen/public/junit-4.12.jar:/home/akeen/public/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore TestCases
   */

   @Test
   public void testOpenSectionsByProfessor1()
   {
      Map<String, List<CourseSection>> sectionsListByCourseMap = new HashMap<>();

      sectionsListByCourseMap.put("cpe101",
         Arrays.asList(
            new CourseSection("Humer", 34, 27, "01"),
            new CourseSection("Einakian", 34, 34, "03"),
            new CourseSection("Einakian", 34, 32, "05"),
            new CourseSection("Kauffman", 34, 34, "07"),
            new CourseSection("Smith", 34, 34, "09"),
            new CourseSection("Workman", 34, 34, "11"),
            new CourseSection("Kauffman", 34, 34, "13"),
            new CourseSection("Einakian", 34, 28, "15"),
            new CourseSection("Workman", 34, 24, "17"),
            new CourseSection("Kauffman", 34, 18, "19"),
            new CourseSection("Humer", 34, 16, "21"),
            new CourseSection("Humer", 34, 0, "23"),
            new CourseSection("Mork", 34, 10, "25"),
            new CourseSection("Hatalsky", 34, 6, "27"),
            new CourseSection("Hatalsky", 34, 5, "29")));

      sectionsListByCourseMap.put("cpe203",
         Arrays.asList(
            new CourseSection("Wood", 36, 36, "01"),
            new CourseSection("Einakian", 32, 31, "03"),
            new CourseSection("Mork", 30, 30, "05"),
            new CourseSection("Mork", 36, 34, "07"),
            new CourseSection("Humer", 32, 32, "09"),
            new CourseSection("Workman", 30, 28, "11"),
            new CourseSection("Einakian", 36, 36, "13")));

      List<String> expected = Arrays.asList("05","15");
      
      assertEquals(new HashSet<>(expected),
         new HashSet<>(MyPass.openSectionsByProfessor(
            sectionsListByCourseMap, "cpe101", "Einakian")));
   }
}
