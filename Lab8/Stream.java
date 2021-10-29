import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.*;


public class Stream {

    public static void main(String args[]) throws IOException {
        List<Point> points = new ArrayList<>();
        try {
            readPoints(points);
        } catch (Exception e) {
            System.out.println("nope");
        }
        writePoints(points);
    }

    public static void readPoints(List<Point> points){
        try {
            Scanner sc = new Scanner(new File("C:\\Users\\cmina\\IdeaProjects\\CPE203\\Lab8\\positions.txt"));
            while(sc.hasNextLine()){
                String[] line = sc.nextLine().split(",");
                double x = Double.parseDouble(line[0]);
                double y = Double.parseDouble(line[1]);
                double z = Double.parseDouble(line[2]);
                Point p = new Point(x, y, z);
                points.add(p);
            }
        }
        catch(Exception e){System.out.println("Can't open input file"); }
    }

    public static void writePoints(List<Point> points) throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\cmina\\IdeaProjects\\CPE203\\Lab8\\drawMe.txt");
        points = points.stream()
                .filter(p -> p.getZ() <= 2.0)
                .map(p -> new Point(p.getX()*0.5, p.getY()*0.5, p.getZ()*0.5))
                .map(p -> new Point(p.getX()-150.0, p.getY()-37, p.getZ()))
                .collect(Collectors.toList());
        for(Point item : points){
            String new_item = item.toString();
            writer.write(new_item);
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
}
