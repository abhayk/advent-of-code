package aoc2019;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day6
{
    static class Node
    {
        String name;
        Node parent;
        Node( String name ){ this.name = name; }
    }

    private static Map<String, Node> parseInput( List<String> input )
    {
        Map<String, Node> orbitMap = new HashMap<>();
        for( String line : input )
        {
            String[] split = line.split("\\)");
            String parent = split[0];
            String child = split[1];
            orbitMap.computeIfAbsent( child, k -> new Node( child )).parent =
                    orbitMap.computeIfAbsent( parent, k -> new Node( parent ));
        }
        return orbitMap;
    }

    private static int getCountOfTotalOrbits( Map<String, Node> orbitMap )
    {
        int count = 0;
        for( Node node : orbitMap.values() )
        {
            Node tmpNode = node;
            while( tmpNode.parent != null )
            {
                tmpNode = tmpNode.parent;
                count++;
            }
        }
        return count;
    }

    private static Node findCommonAncestorNode( Node n1, Node n2 )
    {
        Set<Node> nodes = new HashSet<>();
        while( n1.parent != null && n2.parent != null )
        {
            n1 = n1.parent;
            n2 = n2.parent;
            if( !nodes.add( n1 ))
                return n1;
            if( !nodes.add( n2 ))
                return n2;
        }
        return n1.parent == null ? n1 : n2;
    }

    private static int findDistanceToAncestorNode( Node start, Node dest )
    {
        int distance = 0;
        while( start != dest )
        {
            start = start.parent;
            distance++;
        }
        return distance;
    }

    private static int getHopsRequired( Node start, Node dest )
    {
        Node ancestor = findCommonAncestorNode( start, dest );
        return findDistanceToAncestorNode( start, ancestor ) + findDistanceToAncestorNode( dest, ancestor );
    }

    public static void main(String[] args) throws IOException
    {
        List<String> input = new ArrayList<>();
        input.add("COM)B");
        input.add("B)C");
        input.add("C)D");
        input.add("D)E");
        input.add("E)F");
        input.add("B)G");
        input.add("G)H");
        input.add("D)I");
        input.add("E)J");
        input.add("J)K");
        input.add("K)L");

        assert getCountOfTotalOrbits( parseInput( input ) ) == 42;

        input = new ArrayList<>();
        input.add("COM)B");
        input.add("B)C");
        input.add("C)D");
        input.add("D)E");
        input.add("E)F");
        input.add("B)G");
        input.add("G)H");
        input.add("D)I");
        input.add("E)J");
        input.add("J)K");
        input.add("K)L");
        input.add("K)YOU");
        input.add("I)SAN");

        Map<String, Node> orbitMap = parseInput( input );
        assert getHopsRequired( orbitMap.get("YOU").parent, orbitMap.get("SAN").parent) == 4;

        input = Files.readAllLines(Util.getInputFilePath());
        orbitMap = parseInput( input );
        System.out.println( getCountOfTotalOrbits( orbitMap ));
        System.out.println( getHopsRequired( orbitMap.get("YOU").parent, orbitMap.get("SAN").parent));
    }
}
