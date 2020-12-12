package aoc2020;

import common.Point;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

public class Day12 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Util.getInputFilePath());
        // part 1
        System.out.println(navigate(input).manhattanDistance(new Point(0, 0)));
        // part 2
        System.out.println(navigateV2(input).manhattanDistance(new Point(0, 0)));
    }

    private static Point navigate(List<String> input) {
        char facing = 'E';
        Point p = new Point(0, 0);
        for(String line : input) {
            char dir = line.charAt(0);
            int val = Integer.parseInt(line.substring(1));
            if(dir == 'F')
                dir = facing;
            switch (dir) {
                case 'N': p.y += val;break;
                case 'S': p.y -= val;break;
                case 'E': p.x += val;break;
                case 'W': p.x -= val;break;
                case 'L':
                case 'R':
                    facing = getNextDirection(facing, dir, val);
                    break;
            }
        }
        return p;
    }

    private static Point navigateV2(List<String> input) {
        Point p = new Point(0, 0);
        Point w = new Point(10, 1);
        for(String line : input) {
            char dir = line.charAt(0);
            int val = Integer.parseInt(line.substring(1));
            switch (dir) {
                case 'N': w.y += val; break;
                case 'S': w.y -= val; break;
                case 'E': w.x += val; break;
                case 'W': w.x -= val; break;
                case 'L': rotateClockwise(w, 360 - val); break;
                case 'R': rotateClockwise(w, val); break;
                case 'F':
                    p.x += w.x * val;
                    p.y += w.y * val;
                    break;
            }
        }
        return p;
    }

    private static void rotateClockwise(Point p, int degrees) {
        int steps = degrees / 90;
        for(int i=0; i<steps; i++) {
            int tmp = p.x;
            p.x = p.y;
            p.y = tmp * -1;
        }
    }

    private static char getNextDirection(char facing, char dir, int degrees) {
        String directions = "NESW";
        int index = directions.indexOf(facing);
        int steps = degrees / 90;
        switch (dir) {
            case 'R': index = (index + steps) % 4;break;
            case 'L': index = (index + 4 - steps) % 4; break;
        }
        return directions.charAt(index);
    }
}
