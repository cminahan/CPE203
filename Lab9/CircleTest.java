public class CircleTest
{
    public static void main(String[] args)
    {
        try
        {
            Circle c1 = new Circle(4);
            System.out.println(c1);
        }
        catch (ZeroRadiusException e) {
            System.out.println(e.getMessage());
            return;
        }
        catch(NegativeRadiusException e){
            System.out.println(e.getMessage() + ": " + e.radius());
            return;
        }
        finally {
            System.out.println("In finally.");
        }
        System.out.println("Done.");
    }
}