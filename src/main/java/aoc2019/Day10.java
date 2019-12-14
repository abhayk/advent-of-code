package aoc2019;

import common.Point;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day10
{
    private static List<Point> parseAsteroidLocations(List<String> input )
    {
        List<Point> points = new ArrayList<>();
        int i=0;
        for( String line : input )
        {
            int j=0;
            for( char c : line.toCharArray() )
            {
                if( c == '#')
                    points.add( new Point(j, i));
                j++;
            }
            i++;
        }
        return points;
    }

    private static double angle( Point p1, Point p2 )
    {
        double dy = p2.y - p1.y;
        double dx = p2.x - p1.x;
        double result = Math.toDegrees( Math.atan2( dy, dx ) );
        return result < 0 ? ( 360d + result ) : result;
    }

    private static double translatedAngle( Point p1, Point p2 )
    {
        double angle = angle( p1, p2 );
        double result = angle - 270;
        return result < 0 ? (360d + result ) : result;
    }

    private static boolean isCollinear( Point p1, Point p2, Point p3 )
    {
        return p1.x * ( p2.y - p3.y ) + p2.x * ( p3.y - p1.y ) + p3.x * ( p1.y - p2.y ) == 0;
    }

    private static boolean isBetween( Point p1, Point p2, Point p3 )
    {
        if( p1.x == p2.x && p2.x == p3.x )
            return (p1.y < p2.y && p2.y < p3.y) || ( p1.y > p2.y && p2.y > p3.y );
        return (p1.x < p2.x && p2.x < p3.x) || ( p1.x > p2.x && p2.x > p3.x );
    }

    private static List<Point> getPointsInLineOfSight( Point p, List<Point> points )
    {
        List<Point> pointsInLineOfSight = new ArrayList<>();
        for( Point p2 : points )
        {
            if( p == p2 )
                continue;
            boolean blocked = false;
            for( Point p3 : points )
            {
                if( p3 == p || p3 == p2 )
                    continue;
                if( isCollinear( p, p2, p3) && isBetween( p, p3, p2 ) )
                {
                    blocked = true;
                    break;
                }
            }
            if( !blocked )
                pointsInLineOfSight.add( p2 );
        }
        return pointsInLineOfSight;
    }

    private static Map<Point, List<Point>> getLineofSightMap( List<Point> points )
    {
        Map<Point, List<Point>> linesOfSight = new HashMap<>();
        for( Point p1 : points )
            linesOfSight.put( p1, getPointsInLineOfSight( p1, points ) );
        return linesOfSight;
    }

    private static Map.Entry<Point, List<Point>> getAsteroidWithMostLineOfSights( List<Point> points )
    {
        Map<Point, List<Point>> linesOfSight = getLineofSightMap( points );
        return Collections.max(linesOfSight.entrySet(), Comparator.comparingInt(e -> e.getValue().size()));
    }

    private static List<Point> getDestructionOrder( List<Point> points )
    {
        List<Point> destructionOrder = new ArrayList<>();
        Map.Entry<Point, List<Point>> linesOfSight = getAsteroidWithMostLineOfSights( points );
        Point p = linesOfSight.getKey();
        List<Point> pointsInLineOfSight = linesOfSight.getValue();
        while( pointsInLineOfSight.size() != 0 )
        {
            pointsInLineOfSight.sort(Comparator.comparingDouble( (Point e) -> translatedAngle(p, e)));
            destructionOrder.addAll( pointsInLineOfSight );
            points.removeAll( pointsInLineOfSight );
            pointsInLineOfSight = getPointsInLineOfSight( p, points );
        }
        return destructionOrder;
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add(".#..#");
        input.add(".....");
        input.add("#####");
        input.add("....#");
        input.add("...##");
        Map.Entry<Point, List<Point>> result = getAsteroidWithMostLineOfSights(parseAsteroidLocations( input ));
        assert result.getKey().equals( new Point(3,4) );
        assert result.getValue().size() == 8;

        input = new ArrayList<>();
        input.add("......#.#.");
        input.add("#..#.#....");
        input.add("..#######.");
        input.add(".#.#.###..");
        input.add(".#..#.....");
        input.add("..#....#.#");
        input.add("#..#....#.");
        input.add(".##.#..###");
        input.add("##...#..#.");
        input.add(".#....####");
        result = getAsteroidWithMostLineOfSights(parseAsteroidLocations( input ));
        assert result.getKey().equals( new Point(5,8) );
        assert result.getValue().size() == 33;

        input = new ArrayList<>();
        input.add("#.#...#.#.");
        input.add(".###....#.");
        input.add(".#....#...");
        input.add("##.#.#.#.#");
        input.add("....#.#.#.");
        input.add(".##..###.#");
        input.add("..#...##..");
        input.add("..##....##");
        input.add("......#...");
        input.add(".####.###.");
        result = getAsteroidWithMostLineOfSights(parseAsteroidLocations( input ));
        assert result.getKey().equals( new Point(1,2) );
        assert result.getValue().size() == 35;

        input = new ArrayList<>();
        input.add(".#..#..###");
        input.add("####.###.#");
        input.add("....###.#.");
        input.add("..###.##.#");
        input.add("##.##.#.#.");
        input.add("....###..#");
        input.add("..#.#..#.#");
        input.add("#..#.#.###");
        input.add(".##...##.#");
        input.add(".....#.#..");
        result = getAsteroidWithMostLineOfSights(parseAsteroidLocations( input ));
        assert result.getKey().equals( new Point(6,3) );
        assert result.getValue().size() == 41;

        input = new ArrayList<>();
        input.add(".#..##.###...#######");
        input.add("##.############..##.");
        input.add(".#.######.########.#");
        input.add(".###.#######.####.#.");
        input.add("#####.##.#.##.###.##");
        input.add("..#####..#.#########");
        input.add("####################");
        input.add("#.####....###.#.#.##");
        input.add("##.#################");
        input.add("#####.##.###..####..");
        input.add("..######..##.#######");
        input.add("####.##.####...##..#");
        input.add(".#####..#.######.###");
        input.add("##...#.##########...");
        input.add("#.##########.#######");
        input.add(".####.#.###.###.#.##");
        input.add("....##.##.###..#####");
        input.add(".#.#.###########.###");
        input.add("#.#.#.#####.####.###");
        input.add("###.##.####.##.#..##");
        result = getAsteroidWithMostLineOfSights(parseAsteroidLocations( input ));
        assert result.getKey().equals( new Point(11,13) );
        assert result.getValue().size() == 210;

        List<Point> points = getDestructionOrder( parseAsteroidLocations( input ) );
        assert points.get(0).equals( new Point(11,12));
        assert points.get(1).equals( new Point(12,1));
        assert points.get(2).equals( new Point(12,2));
        assert points.get(199).equals( new Point(8,2));

        input = Files.readAllLines( Util.getInputFilePath() );
        System.out.println( getAsteroidWithMostLineOfSights( parseAsteroidLocations( input ) ).getValue().size());

        List<Point> destructionOrder = getDestructionOrder( parseAsteroidLocations( input ) );
        Point p = destructionOrder.get(199);
        System.out.println( p.x * 100 + p.y );
    }
}
