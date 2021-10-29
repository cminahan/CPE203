import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }

   public int hashCode(){
      return Objects.hash(surname, givenName, age, currentCourses);
   }

   public boolean equals(Object o){
      if (o == null) return false;
      if (o.getClass() != this.getClass()) return false;

      Student s = (Student)o;

      if (Objects.equals(surname, s.surname) && Objects.equals(givenName, s.givenName) &&
            age == s.age && Objects.equals(currentCourses, s.currentCourses)) return true;
      return false;
   }
}
