package aoc2015;

public class Day10
{
    private static String getLookAndSaySequence( String input )
    {
        StringBuilder sb = new StringBuilder();
        char[] arr = input.toCharArray();
        int i=0;
        while( i < arr.length )
        {
            char start = arr[i];
            int j = 1;
            while( i+j < arr.length && arr[i+j] == start )
                j++;
            sb.append(j).append(start);
            i+=j;
        }
        return sb.toString();
    }

    public static void main(String[] args)
    {
        assert getLookAndSaySequence("1").equals("11");
        assert getLookAndSaySequence("11").equals("21");
        assert getLookAndSaySequence("21").equals("1211");
        assert getLookAndSaySequence("1211").equals("111221");
        assert getLookAndSaySequence("111221").equals("312211");

        String input = "1113222113";
        for( int i=0; i<40; i++ )
            input = getLookAndSaySequence( input );

        System.out.println(input.length());

        input = "1113222113";
        for( int i=0; i<50; i++ )
            input = getLookAndSaySequence( input );

        System.out.println(input.length());
    }
}
