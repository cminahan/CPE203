import java.util.*;
import java.util.zip.CheckedOutputStream;

public class MyPass
{
    /**
     * openSectionsByProfessor - Creates a list of all the section numbers for a  
     *     given course that are open for a given professor.  An open section is one in
     *     which the enrollment is less than the cap.  Sections have a String professor,
     *     int enrollment, int cap, and String section number.  See Section.java. 
     *
     * @param sectionsListByCourseMap a map of course name to a list of sections for
     *                                the course
     * @param course the given course
     * @param professor the given professor
     * @return a list of String section numbers of open sections 
     */
    public static List<String> openSectionsByProfessor(Map<String, List<CourseSection>> sectionsListByCourseMap,
                                                       String course, String professor)
    {

        // Complete the method and write additional tests if you have time (5 pts)
        //Map<String, List<CourseSection>> checked = new HashMap<>();
        List<String> sectionNumbers = new LinkedList<>();
        boolean searching = true;
        while(searching){
            if(sectionsListByCourseMap.containsKey(course)) {
                List<CourseSection> courses = sectionsListByCourseMap.get(course);
                sectionsListByCourseMap.remove(course, courses);
                //checked.put(course, courses);
                for(CourseSection c : courses){
                    if(c.getEnrolled() < c.getCap() && c.getProfessor().equals(professor))
                        sectionNumbers.add(c.getSectionNum());
                }
            }
            else{
                searching = false;
            }
        }
        return sectionNumbers;


    }


}