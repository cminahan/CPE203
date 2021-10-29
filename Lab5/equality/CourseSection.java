import java.time.LocalTime;
import java.util.Objects;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   // additional likely methods not defined since they are not needed for testing

   public int hashCode(){
      int result = 1;

      result = result * 17 + ((prefix == null) ? 0 : prefix.hashCode());
      result = result * 17 + ((number == null) ? 0 : number.hashCode());
      result = result * 17 + enrollment;
      result = result * 17 + ((startTime == null) ? 0 : startTime.hashCode());
      result = result * 17 + ((endTime == null) ? 0 : endTime.hashCode());

      return result;
   }

   public boolean equals(Object o){
      if (o == null) return false;
      if(o.getClass() != this.getClass()) return false;

      CourseSection c = (CourseSection)o;

      if (prefix == null && c.prefix != null) return false;
      if (prefix != null && c.prefix == null) return false;
      if (number == null && c.number != null) return false;
      if (number != null && c.number == null) return false;
      if (startTime == null && c.startTime != null) return false;
      if (startTime != null && c.startTime == null) return false;
      if (endTime == null && c.endTime != null) return false;
      if (endTime != null && c.endTime == null) return false;

      if (prefix == null && number == null  && startTime == null && endTime == null && enrollment == c.enrollment)
         return true;

      return this.prefix.equals(c.prefix) && this.number.equals(c.number) && this.enrollment == c.enrollment
              && this.startTime.equals(c.startTime) && this.endTime.equals(c.endTime);

   }
}
