import java.util.*;
import java.lang.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dijkstra implements PathingStrategy{

    public int calculateDistance(Point a, Point b){
        int h;
        h = Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
        return h;
    }

    public List<Point> pathFinder(List<Point> path, Node goal) {
        path.add(goal.getPosition());
        if (goal.getParent() != null)
            return pathFinder(path, goal.getParent());
        Collections.reverse(path);
        return path;
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        List<Point> path = new LinkedList<>();
        Queue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        openList.add(new Node(calculateDistance(start, end), 0,
                calculateDistance(start, end), null, start));
        Map<Point,Node> openMap = new HashMap<>();
        Map<Point,Node> closedMap = new HashMap<>();

        while (openList.size() != 0) {
            Node q = openList.remove();

            if (withinReach.test(q.getPosition(), end)){
                return pathFinder(path, q);
            }

            List<Point> neighborPoints = potentialNeighbors.apply(q.getPosition())
                    .filter(canPassThrough)
                    .filter(p -> !p.equals(start) && !p.equals(end)).collect(Collectors.toList());

            for (Point p: neighborPoints) {
                if (!closedMap.containsKey(p)) {
                    int new_g = q.getG() + 1;
                    Node n = new Node(calculateDistance(p, end), new_g,
                            calculateDistance(p, end) + new_g, q, p);
                    if(openMap.containsKey(p) && new_g < openMap.get(p).getG()) {
                        openList.add(n);
                        openList.remove(openMap.get(p));
                        openMap.replace(p,n);
                    }
                    else {
                        openList.add(n);
                        openMap.put(p,n);
                    }

                }
                closedMap.put(q.getPosition(),q);
            }


        }
        return path;
    }

    private class Node {
        private int g; //distance from start node
        private int h;
        private int f;
        private Node parentNode;
        private Point position;

        public Node(int g, int f, int h, Node parentNode, Point position){
            this.h = h;
            this.g = g;
            this.f = f;
            this.parentNode = parentNode;
            this.position = position;
        }

        public int getG(){return this.g;}
        public int getF(){return this.f;}
        public Node getParent(){return this.parentNode;}
        public Point getPosition(){return this.position;}
        public void setPosition(Point p){this.position = p;}
    }
}
