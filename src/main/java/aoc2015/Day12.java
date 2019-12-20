package aoc2015;

import common.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Day12
{
    private static int getSum( String input )
    {
        int sum = 0;
        Scanner scanner = new Scanner( input ).useDelimiter("[^-?\\d]+");
        while (scanner.hasNext())
            sum += scanner.nextInt();
        return sum;
    }

    private static boolean redPresent( JSONObject jsonObject )
    {
        for( Object value : jsonObject.values() )
        {
            if( value instanceof String && value.equals("red"))
                return true;
        }
        return false;
    }

    private static int stripRedAndGetSum( Object object )
    {
        int sum = 0;
        if( object instanceof String )
            return 0;
        else if( object instanceof Long )
            sum += (Long)object;
        else if( object instanceof JSONObject )
        {
            JSONObject jsonObject = (JSONObject) object;
            if( !redPresent( jsonObject ) )
            {
                for( Object value : jsonObject.values() )
                    sum += stripRedAndGetSum( value );
            }
        }
        else if( object instanceof JSONArray )
        {
            for( Object value : (JSONArray) object)
                sum += stripRedAndGetSum( value );
        }
        return sum;
    }

    public static void main(String[] args) throws IOException, ParseException
    {
        assert getSum("[1,2,3]") == 6;
        assert getSum("{\"a\":2,\"b\":4}") == 6;
        assert getSum("[[[3]]]") == 3;
        assert getSum("{\"a\":{\"b\":4},\"c\":-1}") == 3;
        assert getSum("{\"a\":[-1,1]}") == 0;
        assert getSum("[-1,{\"a\":1}]") == 0;
        assert getSum("[]") == 0;
        assert getSum("{}") == 0;

        String input = Files.readString(Util.getInputFilePath() );
        System.out.println( getSum( input ));

        JSONParser parser = new JSONParser();
        Object object;

        object = parser.parse( "[1,{\"c\":\"red\",\"b\":2},3]" );
        assert stripRedAndGetSum( object ) == 4;

        object = parser.parse( "[1,2,3]" );
        assert stripRedAndGetSum( object ) == 6;

        object = parser.parse( "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}" );
        assert stripRedAndGetSum( object ) == 0;

        object = parser.parse( "[1,\"red\",5]" );
        assert stripRedAndGetSum( object ) == 6;

        object = parser.parse( input );
        System.out.println( stripRedAndGetSum( object ));
    }
}
