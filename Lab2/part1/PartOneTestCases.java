import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class PartOneTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getCenter", "getRadius");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   // Circle test cases
   Point p = new Point(9.0, 3.2);
   @Test
   public void testGetCenterX() { assertEquals(p.getX(), new Circle(new Point(9.0, 3.2), 4.0).getCenter().getX(), DELTA);}
   @Test
   public void testGetCenterY() { assertEquals(p.getY(), new Circle(new Point(9.0, 3.2), 4.0).getCenter().getY(), DELTA);}
   @Test
   public void testGetRadius() { assertEquals(4.0, new Circle(p, 4.0).getRadius(), DELTA);}

   //Rectangle test cases
   Point l = new Point(8.4, 3.2);
   Point r = new Point(-2.9, 1.1);
   @Test
   public void testGetTopLeftX() {assertEquals(l.getX(), new Rectangle(new Point(8.4, 3.2), new Point(-2.9, 1.1)).getTopLeft().getX(), DELTA);}
   @Test
   public void testGetTopLeftY() {assertEquals(l.getY(), new Rectangle(new Point(8.4, 3.2), new Point(-2.9, 1.1)).getTopLeft().getY(), DELTA);}
   @Test
   public void testGetBottomRightX() {assertEquals(r.getX(), new Rectangle(new Point(8.4, 3.2), new Point(-2.9, 1.1)).getBottomRight().getX(), DELTA);}
   @Test
   public void testGetBottomRightY() {assertEquals(r.getY(), new Rectangle(new Point(8.4, 3.2), new Point(-2.9, 1.1)).getBottomRight().getY(), DELTA);}

   // Polygon test case
   Point a = new Point(1.0, 2.0);
   Point b = new Point(1.5, 2.4);
   Point c = new Point(3.5, 4.0);
   @Test
   public void testGetPoints() {assertEquals(1.0, new Polygon(new ArrayList<>(Arrays.asList(a, b, c))).getPoints().get(0).getX(), DELTA);}
   @Test
   public void testGetPoints2() {assertEquals(1.5, new Polygon(new ArrayList<>(Arrays.asList(a, b, c))).getPoints().get(1).getX(), DELTA);}
   @Test
   public void testGetPoints3() {assertEquals(3.5, new Polygon(new ArrayList<>(Arrays.asList(a, b, c))).getPoints().get(2).getX(), DELTA);}



   // Util test cases
   Circle circle = new Circle(new Point(2.0, 9.8), 4);
   double cp = 2*Math.PI*4;
   @Test
   public void testCirclePerimeter() {assertEquals(cp, Util.perimeter(circle), DELTA);}
   Rectangle rectangle = new Rectangle(new Point(0.0, 0.0), new Point(5.0, 9.0));
   @Test
   public void testRectanglePerimeter() {assertEquals(28.0, Util.perimeter(rectangle), DELTA);}
   Point p1 = new Point(0.0, 0.0);
   Point p2 = new Point(3.0, 0.0);
   Point p3 = new Point(0.0, 4.0);
   ArrayList<Point> points = new ArrayList<>(Arrays.asList(p1, p2, p3));
   Polygon polygon = new Polygon(points);
   @Test
   public void testPolygonPerimeter() {assertEquals(12.0, Util.perimeter(polygon), DELTA);}

   // whichIsBigger test case
   @Test
   public void testWhichIsBigger(){
      Circle circle = new Circle(new Point(1.0, 1.0), 2.0);
      Rectangle rectangle = new Rectangle(new Point(-1.0, 2.0), new Point(1.0, -1.6));
      Point p1 = new Point(0.0, 0.0);
      Point p2 = new Point(3.0, 1.0);
      Point p3 = new Point(1.0, 4.0);
      Point p4 = new Point(-1.0, 4.0);
      Polygon polygon = new Polygon(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)));
      assertEquals(Util.perimeter(polygon), Bigger.whichIsBigger(circle, rectangle, polygon), DELTA);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getTopLeft", "getBottomRight");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getPoints");

      final List<Class> expectedMethodReturns = Arrays.asList(
         List.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[][] {new Class[0]});

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testUtilImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "perimeter", "perimeter", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         double.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[] {Circle.class},
         new Class[] {Polygon.class},
         new Class[] {Rectangle.class});

      verifyImplSpecifics(Util.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}
