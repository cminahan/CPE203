public class CourseSection
{
    private String professor;
    private int cap;
    private int enrolled;
    private String sectionNum;

    public CourseSection(String professor, int cap, int enrolled, String sectionNum)
    {
        this.professor = professor;
        this.cap = cap;
        this.enrolled = enrolled;
        this.sectionNum = sectionNum;
    }

    public String getProfessor() {return professor;}
    public int getCap() {return cap;}
    public int getEnrolled() {return enrolled;}
    public String getSectionNum() {return sectionNum;}

    public String toString()
    {
        return sectionNum + ": " + professor + " " + enrolled + "/" + cap;
    }
}
