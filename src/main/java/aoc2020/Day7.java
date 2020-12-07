package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());
        Map<String, Node> nodes = parseNodes(input);
        Node target = nodes.get("shiny gold");

        // part 1
        System.out.println(nodes.values().stream().filter(node -> isConnected(node, target)).count());

        // part 2
        System.out.println(countBagsInside(target));
    }

    private static int countBagsInside(Node node) {
        int count = 0;
        for(Edge edge : node.outgoingEdges) {
            count += edge.count * (countBagsInside(edge.target) + 1); // +1 to include itself
        }
        return count;
    }

    private static boolean isConnected(Node source, Node target) {
        Set<String> visited = new HashSet<>();
        visited.add(source.name);
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(source);

        while(!queue.isEmpty()) {
            Node entry = queue.poll();
            for(Edge edge : entry.outgoingEdges) {
                if(edge.target.equals(target))
                    return true;
                if(visited.add(edge.target.name))
                    queue.add(edge.target);
            }
        }
        return false;
    }

    private static Map<String, Node> parseNodes(List<String> input) {
        Map<String, Node> nodes = new HashMap<>();
        for(String line : input) {
            String[] split = line.split("contain");
            String nodeName = split[0].trim().replace("bags", "").trim();
            Node node = nodes.computeIfAbsent(nodeName, Node::new);
            nodes.put(nodeName, node);

            Arrays.stream(split[1].trim().split(","))
                .map(s -> s
                    .replace("bags", "")
                    .replace("bag", "")
                    .replace(".", ""))
                .map(String::trim)
                .filter(s -> !s.trim().equals("no other"))
                .forEach(edgeStr -> {
                    int index = edgeStr.indexOf(" ");
                    int count = Integer.parseInt(edgeStr.substring(0, index));
                    String edgeNodeName = edgeStr.substring(index).trim();
                    Node edgeNode = nodes.computeIfAbsent(edgeNodeName, Node::new);
                    node.addOutgoingEdge(new Edge(edgeNode, count));
                });
        }
        return nodes;
    }

    static class Node {
        String name;
        List<Edge> outgoingEdges = new ArrayList<>();

        public Node(String name) {
            this.name = name;
        }

        public void addOutgoingEdge(Edge edge) {
            this.outgoingEdges.add(edge);
        }
    }

    static class Edge {
        Node target;
        int count;

        public Edge(Node target, int count) {
            this.target = target;
            this.count = count;
        }
    }
}
