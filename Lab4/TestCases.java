import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import javax.crypto.spec.DESKeySpec;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /* some sample tests but you must write more! see lab write up */

   @Test
   public void testCircleGetArea()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      assertEquals(101.2839543, c.getArea(), DELTA);
   }

   @Test
   public void testCircleGetPerimeter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      assertEquals(35.6759261, c.getPerimeter(), DELTA);
   }

   @Test
   public void testWorkSpaceAreaOfAllShapes()
   {
      WorkSpace ws = new WorkSpace();

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0), 
                 Color.BLACK));

      assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
   }

   @Test
   public void testWorkSpaceGetCircles()
   {
      WorkSpace ws = new WorkSpace();
      List<Circle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

      ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
      ws.add(c1);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                 Color.BLACK));
      ws.add(c2);

      expected.add(c1);
      expected.add(c2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getCircles());
   }

   /* HINT - comment out implementation tests for the classes that you have not 
    * yet implemented */
   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, 
         new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC", "equals");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class, boolean.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0], new Class[] {Object.class});

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
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

   // More test cases
   //Circle Tests
   @Test
   public void testGetAndSetRadius()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      c.setRadius(5.4);
      assertEquals(5.4, c.getRadius(), DELTA);
   }

   @Test
   public void testGetCenter()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      assertEquals(2, c.getCenter().x, DELTA);
      assertEquals(3, c.getCenter().y, DELTA);
   }

   @Test
   public void testCircleTranslate()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Point p = new Point(1, 1);
      c.translate(p);
      assertEquals(3, c.getCenter().x, DELTA);
      assertEquals(4, c.getCenter().y, DELTA);
   }

   @Test
   public void testCircleEquals()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      Circle x = new Circle(5.678, new Point(2, 3), Color.BLACK);
      assertTrue(c.equals(x));
   }

   @Test
   public void testCirlceColor()
   {
      Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);
      assertTrue(c.getColor().equals(Color.BLACK));
   }

   //Rectangle Tests
   @Test
   public void testRectangleGetArea()
   {
      Rectangle r = new Rectangle(3.0, 5.0, new Point(1, 2), Color.BLACK);
      assertEquals(15.0, r.getArea(), DELTA);
   }

   @Test
   public void testRectangleGetPerimeter()
   {
      Rectangle r = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      assertEquals(14, r.getPerimeter(), DELTA);
   }

   @Test
   public void testWidthAndHeight()
   {
      Rectangle r = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      r.setHeight(4.5);
      r.setWidth(1.9);
      assertEquals(4.5, r.getHeight(), DELTA);
      assertEquals(1.9, r.getWidth(), DELTA);
      assertEquals(0, r.getTopLeft().x, DELTA);
      assertEquals(0, r.getTopLeft().y, DELTA);
   }

   @Test
   public void testRectangleTranslate()
   {
      Rectangle r = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      Point p = new Point(1,2);
      r.translate(p);
      assertEquals(1, r.getTopLeft().x, DELTA);
      assertEquals(2, r.getTopLeft().y, DELTA);
   }

   @Test
   public void testRectangleEquals()
   {
      Rectangle r = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      Rectangle x = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      assertTrue(r.equals(x));
   }

   @Test
   public void testRectangleGetColor()
   {
      Rectangle r = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      assertTrue(r.getColor().equals(Color.RED));
   }

   //Triangle
   @Test
   public void testTriangleGetVertices()
   {
      Triangle t = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      assertEquals(1, t.getVertexA().x, DELTA);
      assertEquals(4, t.getVertexA().y, DELTA);
      assertEquals(0, t.getVertexB().x, DELTA);
      assertEquals(7, t.getVertexB().y, DELTA);
      assertEquals(3, t.getVertexC().x, DELTA);
      assertEquals(6, t.getVertexC().y, DELTA);
   }

   @Test
   public void testTriangleGetArea()
   {
      Triangle t = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      assertEquals(4.0, t.getArea(), DELTA);
   }

   @Test
   public void testTriangleGetPerimeter()
   {
      Triangle t = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      assertEquals(9.15298244508295, t.getPerimeter(), DELTA);
   }

   @Test
   public void testTriangleTranslate()
   {
      Triangle t = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      Point p = new Point(1, 1);
      t.translate(p);
      assertEquals(2, t.getVertexA().x, DELTA);
      assertEquals(5, t.getVertexA().y, DELTA);
      assertEquals(1, t.getVertexB().x, DELTA);
      assertEquals(8, t.getVertexB().y, DELTA);
      assertEquals(4, t.getVertexC().x, DELTA);
      assertEquals(7, t.getVertexC().y, DELTA);
   }

   @Test
   public void testTriangleEquals()
   {
      Triangle t = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      Triangle x = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      assertTrue(t.equals(x));
   }

   @Test
   public void testTriangleGetColor()
   {
      Triangle t = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      assertTrue(t.getColor().equals(Color.BLUE));
   }

   //WorkSpace
   @Test
   public void testWorkSpaceGetRectangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Rectangle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Rectangle r = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      Rectangle r2 = new Rectangle(4.0,8.6, new Point(1,5), Color.BLUE);

      ws.add(new Circle(2.0, new Point(0,0), Color.RED));
      ws.add(r);
      ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
              Color.BLACK));
      ws.add(r2);

      expected.add(r);
      expected.add(r2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getRectangles());
   }

   @Test
   public void testWorkSpaceGetTriangles()
   {
      WorkSpace ws = new WorkSpace();
      List<Triangle> expected = new LinkedList<>();

      // Have to make sure the same objects go into the WorkSpace as
      // into the expected List since we haven't overriden equals in Circle.
      Triangle t = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      Triangle t2 = new Triangle(new Point(0,9), new Point(2,1), new Point(7,5), Color.YELLOW);

      ws.add(new Circle(2.0, new Point(0,0), Color.RED));
      ws.add(t);
      ws.add(new Rectangle(4.0, 5.6, new Point(0,0), Color.GREEN));
      ws.add(t2);

      expected.add(t);
      expected.add(t2);

      // Doesn't matter if the "type" of lists are different (e.g Linked vs
      // Array).  List equals only looks at the objects in the List.
      assertEquals(expected, ws.getTriangles());
   }

   @Test
   public void testWorkSpaceGetShapeByColor()
   {
      WorkSpace ws = new WorkSpace();
      List<Shape> expected = new LinkedList<>();

      Triangle t1 = new Triangle(new Point(1,4), new Point(0,7), new Point(3,6), Color.BLUE);
      Triangle t2 = new Triangle(new Point(0,9), new Point(2,1), new Point(7,5), Color.YELLOW);
      Rectangle r1 = new Rectangle(3.0,4.0, new Point(0,0), Color.RED);
      Rectangle r2 = new Rectangle(4.0,8.6, new Point(1,5), Color.ORANGE);
      Circle c1 = new Circle(5.678, new Point(2, 3), Color.GREEN);
      Circle c2 = new Circle(1.11, new Point(-5, -3), Color.ORANGE);
      ws.add(t1);
      ws.add(t2);
      ws.add(r1);
      ws.add(r2);
      ws.add(c1);
      ws.add(c2);

      expected.add(r2);
      expected.add(c2);

      assertEquals(expected, ws.getShapesByColor(Color.ORANGE));

   }


}
