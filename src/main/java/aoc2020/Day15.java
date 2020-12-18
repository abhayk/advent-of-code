package aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day15 {
    public static void main(String[] args) {
        assert getNumberSpoken(Arrays.asList(0,3,6), 2020) == 436;

        List<Integer> input = Arrays.asList(12,20,0,6,1,17,7);
        // part 1
        System.out.println(getNumberSpoken(input, 2020));
        // part 2
        System.out.println(getNumberSpoken(input, 30000000));
    }

    private static int getNumberSpoken(List<Integer> start, int endTurn) {
        List<Integer> chain = new ArrayList<>(start);
        Map<Integer, LinkedList<Integer>> lastSeenMap = new HashMap<>();
        IntStream.range(0, chain.size()).forEach(i -> lastSeenMap.computeIfAbsent(chain.get(i), k -> new LinkedList<>()).add(i));

        for(int i=chain.size(); i<endTurn; i++) {
            int last = chain.get(i - 1);
            if (lastSeenMap.containsKey(last)) {
                LinkedList<Integer> list = lastSeenMap.get(last);
                int cur = 0;
                if (!(list.size() == 1 && list.get(0) == i - 1)) {
                    cur = list.getLast() - list.get(list.size() - 2);
                }
                lastSeenMap.computeIfAbsent(cur, k -> new LinkedList<>()).add(i);
                chain.add(cur);
            }
        }
        return chain.get(chain.size() - 1);
    }
}
