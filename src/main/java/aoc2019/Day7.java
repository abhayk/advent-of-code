package aoc2019;

import common.IntCodeComputer;
import common.PermutationUtil;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day7
{
    private static int[] parseInput( String input )
    {
        return Arrays.stream( input.split(",") ).mapToInt(val -> Integer.parseInt( val.trim() ) ).toArray();
    }

    private static int getFinalOutput(int amplifierCount, int[] instructions, Integer[] phaseSettings )
    {
        try
        {
            LinkedBlockingQueue<Integer> output = new LinkedBlockingQueue<>();
            output.put( 0 );
            for( int i=0; i<amplifierCount; i++ )
            {
                LinkedBlockingQueue<Integer> input = new LinkedBlockingQueue<>();
                input.put( phaseSettings[i] );
                input.add( output.take() );
                int[] ins = Arrays.copyOf( instructions, instructions.length );
                IntCodeComputer.runDiagnostic( input, output, ins );
            }
            return output.take();
        }
        catch (InterruptedException e ) { e.printStackTrace(); }
        return 0;
    }

    private static int getFinalOutputWithFeedback( int amplifierCount, int[] instructions, Integer[] phaseSettings )
    {
        try
        {
            List<LinkedBlockingQueue<Integer>> queues = new ArrayList<>();
            for( int i=0; i<amplifierCount; i++ )
            {
                LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
                queue.put( phaseSettings[i == amplifierCount - 1 ?  0 : i+1] );
                queues.add( queue );
            }

            List<Thread> threads = new ArrayList<>();
            for( int i=0; i<amplifierCount; i++ )
            {
                LinkedBlockingQueue<Integer> input = queues.get( i == 0 ? queues.size() - 1 : i - 1 );
                LinkedBlockingQueue<Integer> output = queues.get( i );
                int[] ins = Arrays.copyOf( instructions, instructions.length );
                threads.add( new Thread( () -> IntCodeComputer.runDiagnostic( input, output, ins)));
            }

            queues.get( queues.size() - 1 ).put( 0 );
            threads.forEach( Thread::start );
            for( Thread thread : threads )
                thread.join();
            return queues.get( queues.size() - 1 ).take();
        }
        catch (InterruptedException e) { e.printStackTrace(); }
        return 0;
    }

    private static int findLargestThrusterSignal( int phaseStart, int phaseEnd, int[] instructions, boolean withFeedback )
    {
        int amplifierCount = phaseEnd - phaseStart;
        Integer[] permutations = IntStream.range(phaseStart, phaseEnd ).boxed().toArray(i -> new Integer[amplifierCount]);
        AtomicInteger maxOutput = new AtomicInteger( Integer.MIN_VALUE );
        PermutationUtil.permute( permutations, (permutation) ->
        {
            int finalOutput = withFeedback ? getFinalOutputWithFeedback( amplifierCount, instructions, permutation ) :
                    getFinalOutput( amplifierCount, instructions, permutation );
            if( finalOutput > maxOutput.get() )
                maxOutput.set( finalOutput );
        });
        return maxOutput.get();
    }

    public static void main(String[] args) throws IOException
    {
        String input = new String(Files.readAllBytes(Util.getInputFilePath()));

        assert getFinalOutput( 5,
                parseInput("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"),
                new Integer[]{4,3,2,1,0}) == 43210;

        assert getFinalOutput( 5,
                parseInput("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"),
                new Integer[]{0,1,2,3,4}) == 54321;

        assert getFinalOutput( 5,
                parseInput("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"),
                new Integer[]{1,0,4,3,2}) == 65210;

        int result = findLargestThrusterSignal( 0, 5, parseInput( input ), false);
        System.out.println( result );
        assert result == 272368;

        assert getFinalOutputWithFeedback(5,
                parseInput("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"),
                new Integer[]{9,8,7,6,5}) == 139629729;

        assert getFinalOutputWithFeedback(5,
                parseInput("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1," +
                        "55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"),
                new Integer[]{9,7,8,5,6}) == 18216;

        result = findLargestThrusterSignal(5, 10, parseInput( input ), true );
        System.out.println( result );
        assert result == 19741286;
    }
}
