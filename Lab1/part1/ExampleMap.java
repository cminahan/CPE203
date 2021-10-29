
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap
{
    public static List<String> highEnrollmentStudents(
            Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
    {
        List<String> overEnrolledStudents = new LinkedList<>();

      /*
         Build a list of the names of students currently enrolled
         in a number of units strictly greater than the unitThreshold.
      */
        for (Map.Entry<String, List<Course>> entry : courseListsByStudentName.entrySet())
        {
            /*int units = 0;
            int num = entry.getValue().size();
            for(int x = 0; x < num; x++)
            {
                units += entry.getValue().get(1);
            }
            if (units > unitThreshold)
                overEnrolledStudents.add(entry.getKey());*/
            if (entry.getValue().size()*4 > unitThreshold)
            {
                overEnrolledStudents.add(entry.getKey());
            }
        }

        return overEnrolledStudents;
    }
}