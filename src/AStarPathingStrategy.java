import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedList;

class AStarPathingStrategy
        implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new LinkedList<>();
        PriorityQueue<Node> openSort = new PriorityQueue<>(20, new NodeComparator());
        HashMap<Point, Node> openLook = new HashMap<>();
        HashSet<Point> close = new HashSet<>();

        Node curr;
        double g;
        final Node startN = new Node(start, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, null);
        Node adj;

        List<Point> temp = new ArrayList<>();

        //Choose/know starting and ending points of the path
        //add start node to the open list and mark it as the current node
        openSort.add(startN);
        openLook.put(start, startN);
        curr = startN;

        while(!withinReach.test(curr.pos, end))
        {

            List<Point> validNeighbors = potentialNeighbors.apply(curr.pos)
                    .filter(p -> canPassThrough.test(p))
                    .filter(pt -> !pt.equals(start))
                    .collect(Collectors.toList());

            for (Point valid : validNeighbors) {
                //analyze all valid neighbors that are not on the closed list
                if (!close.contains(valid)) {
                    //add to open list if not already in it
                    if (openLook.get(valid) == null) {
                        adj = new Node(valid, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, curr);
                        openLook.put(valid, adj);
                    }
                    else {
                        adj = openLook.get(valid);
                    }

                    //determine distance from start node (g value)
                    //g = Distance of current node from start + distance from current node to adjacent node
                    g = Math.abs(curr.pos.x - start.x) + Math.abs(curr.pos.y - start.y)
                            + Math.abs(curr.pos.x - adj.pos.x) + Math.abs(curr.pos.y - adj.pos.y);

                    //if calculated g value is better than a previously calculated g value, replace the old g value with the new one...
                    //and if necessary estimate distance of adj node to the end point (h)
                    if (openLook.get(adj.pos).g == Double.POSITIVE_INFINITY || g < openLook.get(adj.pos).g) {
                        adj.g = g;
                        if (openLook.get(adj.pos).h == Double.POSITIVE_INFINITY)
                            adj.h = Math.abs(end.x - adj.pos.x) + Math.abs(end.y - adj.pos.y);

                        //add g and h to get an f value
                        adj.f = adj.g + adj.h;

                        //mark the adjacent node's prior vertex as the current node
                        adj.prior = curr;
                        openSort.add(adj);
                    }
                }
            }
            //move current node to closed list
            close.add(curr.pos);
            openLook.remove(curr.pos);

            //choose node from the open list with the smallest f value and make it the current node
            if (openSort.isEmpty())
                return path;

            curr = openSort.poll();
            if (!openLook.isEmpty()) {
                while (openLook.get(curr.pos) == null) {
                    curr = openSort.poll();
                }
            }
            //go to step 3
       }
        //add to path
        while (!curr.pos.equals(start)) {
            temp.add(curr.pos);
            curr = curr.prior;
        }

       //reverse path
       for (int i = temp.size()- 1; i >= 0; i--) {
           path.add(temp.get(i));
       }
        return path;
    }

    /*@Override
    public List<Point> findPath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors, WorldModel world) {
        return null;
    }*/

    private class Node{
        private Point pos;
        private double g;
        private double h;
        private double f;
        private Node prior;

        public Node(Point pos, double g, double h, double f, Node prior){
            this.pos = pos;
            this.g = g;
            this.h = h;
            this.f = f;
            this.prior = prior;
        }
    }
    private class NodeComparator implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.f > o2.f)
                return 1;
            else if (o1.f < o2.f)
                return -1;
            else if (o1.g < o2.g)
                return 1;
            else if (o1.g > o2.g)
                return -1;
            else
                return 0;
        }
    }
}
