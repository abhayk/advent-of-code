package aoc2020;

import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class Day4
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Util.getInputFilePath());
        List<Map<String, String>> passports = parse(input);
        System.out.println(passports.stream().filter(Day4::isValid).count());
        System.out.println(passports.stream().filter(Day4::isValid2).count());
    }

    private static boolean isValid(Map<String, String> passport)
    {
        if(passport.size() == 8)
            return true;
        return passport.size() == 7 && !passport.containsKey("cid");
    }

    private static boolean isValid2(Map<String, String> passport)
    {
        if(!isValid(passport))
            return false;

        Map<String, Predicate<String>> rules = getRules();
        return passport.entrySet().stream().allMatch(entry -> rules.get(entry.getKey()).test(entry.getValue()));
    }

    private static Map<String, Predicate<String>> getRules()
    {
        Map<String, Predicate<String>> rules = new HashMap<>();

        rules.put("byr", s -> {int v = parseInt(s); return v >= 1920 && v <= 2002; });
        rules.put("iyr", s -> {int v = parseInt(s); return v >= 2010 && v <= 2020; });
        rules.put("eyr", s -> {int v = parseInt(s); return v >= 2020 && v <= 2030; });
        rules.put("hcl", s -> s.length() == 7 && s.startsWith("#") && s.substring(1).matches("^[a-f0-9]*$"));
        rules.put("ecl", s -> Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(s));
        rules.put("pid", s -> s.length() == 9 && s.matches("^[0-9]*$"));
        rules.put("cid", s -> true);
        rules.put("hgt", s ->
        {
            int v = parseInt(s.substring(0, s.length()-2));
            return s.endsWith("cm") ? (v >= 150 && v <= 193) : ( v >= 59 && v <= 76);
        });
        return rules;
    }

    private static int parseInt(String s)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e){ return -1; }
    }

    private static List<Map<String, String>> parse(List<String> input)
    {
        List<Map<String, String>> passports = new ArrayList<>();
        Iterator<String> iterator = input.iterator();
        while(iterator.hasNext())
        {
            Map<String, String> passport = new HashMap<>();
            String line;
            while(iterator.hasNext() && !(line = iterator.next()).isEmpty())
            {
                for(String detail : line.split(" "))
                {
                    String[] split = detail.split(":");
                    passport.put(split[0], split[1]);
                }
            }
            passports.add(passport);
        }
        return passports;
    }
}
