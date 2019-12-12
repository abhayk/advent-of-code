package aoc2019;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day3
{
    static class Point
    {
        int x;
        int y;
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }
    }

    private static Map<Point, Integer> getPoints(String input )
    {
        Map<Point, Integer> points = new HashMap<>();
        int x = 0, y = 0, steps = 0;
        String[] split = input.split(",");
        for( String ins : split )
        {
            char direction = ins.charAt(0);
            int distance = Integer.parseInt( ins.substring( 1 ));

            if( direction == 'R')
            {
                for( int i=x; i<x+distance; i++ )
                    points.put( new Point(i, y), steps++);
                x += distance;
            }
            else if( direction == 'L')
            {
                for( int i=x; i>x-distance; i-- )
                    points.put( new Point(i, y), steps++);
                x -= distance;
            }
            else if( direction == 'U')
            {
                for( int i=y; i<y+distance; i++ )
                    points.put( new Point(x, i), steps++);
                y += distance;
            }
            else if( direction == 'D')
            {
                for( int i=y; i>y-distance; i-- )
                    points.put( new Point(x, i), steps++);
                y -= distance;
            }
        }
        return points;
    }

    private static List<Point> getIntersections( Set<Point> p1, Set<Point> p2 )
    {
        Point start = new Point(0, 0);
        List<Point> intersections = new ArrayList<>();
        for( Point p : p1 )
        {
            if( !p.equals( start ) && p2.contains( p ) )
                intersections.add( p );
        }
        return intersections;
    }

    private static int getDistanceOfClosestIntersection( String wire1, String wire2 )
    {
        Point start = new Point(0, 0);
        List<Point> intersections = getIntersections( getPoints( wire1 ).keySet(), getPoints( wire2 ).keySet() );
        intersections.sort(Comparator.comparingInt(p -> getManhattanDistance(p, start)));
        return getManhattanDistance( intersections.get( 0 ), start );
    }

    private static int getFewestCombinedStepsToIntersection( String wire1, String wire2 )
    {
        Map<Point, Integer> points1 = getPoints( wire1 );
        Map<Point, Integer> points2 = getPoints( wire2 );
        List<Point> intersections = getIntersections( points1.keySet(), points2.keySet() );
        intersections.sort(Comparator.comparingInt(p -> (points1.get(p) + points2.get(p))));
        return points1.get(intersections.get( 0 )) + points2.get( intersections.get( 0 ));
    }

    private static int getManhattanDistance(Point p1, Point p2 )
    {
        return Math.abs( p1.x - p2.x ) + Math.abs( p1.y - p2.y );
    }

    public static void main(String[] args) throws IOException
    {
        assert getDistanceOfClosestIntersection("R8,U5,L5,D3", "U7,R6,D4,L4") == 6;
        assert getDistanceOfClosestIntersection("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83") == 159;
        assert getDistanceOfClosestIntersection("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7") == 135;

        List<String> input = Files.readAllLines(Util.getInputFilePath());
        System.out.println( getDistanceOfClosestIntersection( input.get(0), input.get(1)));

        assert getFewestCombinedStepsToIntersection("R8,U5,L5,D3", "U7,R6,D4,L4") == 30;
        assert getFewestCombinedStepsToIntersection("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83") == 610;
        assert getFewestCombinedStepsToIntersection("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7") == 410;

        System.out.println( getFewestCombinedStepsToIntersection( input.get(0), input.get(1)));
    }
}
