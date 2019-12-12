package aoc2019;

import common.IntCodeComputer;
import common.PermutationUtil;
import common.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Day7
{
    private static long getFinalOutput( int amplifierCount, long[] instructions, Integer[] phaseSettings, boolean hasFeedback )
    {
        try
        {
            List<LinkedBlockingQueue<Long>> queues = new ArrayList<>();
            for( int i=0; i < amplifierCount; i++ )
                queues.add( new LinkedBlockingQueue<>() );
            queues.add( hasFeedback ? queues.get( 0 ) : new LinkedBlockingQueue<>() );

            List<Thread> threads = new ArrayList<>();

            for( int i=0; i<amplifierCount; i++ )
            {
                LinkedBlockingQueue<Long> input = queues.get(i);
                LinkedBlockingQueue<Long> output = queues.get( i+1 );
                IntCodeComputer computer = new IntCodeComputer( instructions, input, output );
                computer.provideInput( phaseSettings[i] );
                threads.add( new Thread( computer::runProgram ) );
            }
            queues.get( 0 ).put( 0L );
            threads.forEach( Thread::start );

            for( Thread thread : threads )
                thread.join();

            return queues.get( queues.size() - 1 ).take();
        }
        catch (InterruptedException e ) { e.printStackTrace(); }
        return -1;
    }

    private static long findLargestThrusterSignal( int phaseStart, int phaseEnd, long[] instructions, boolean withFeedback )
    {
        int amplifierCount = phaseEnd - phaseStart;
        Integer[] permutations = IntStream.range(phaseStart, phaseEnd ).boxed().toArray(i -> new Integer[amplifierCount]);
        AtomicLong maxOutput = new AtomicLong( Long.MIN_VALUE );
        PermutationUtil.permute( permutations, (permutation) ->
        {
            long finalOutput = getFinalOutput( amplifierCount, instructions, permutation, withFeedback );
            if( finalOutput > maxOutput.get() )
                maxOutput.set( finalOutput );
        });
        return maxOutput.get();
    }

    public static void main(String[] args) throws IOException
    {
        String input = new String(Files.readAllBytes(Util.getInputFilePath()));

        assert getFinalOutput( 5,
                IntCodeComputer.parseInput("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"),
                new Integer[]{4,3,2,1,0}, false) == 43210;

        assert getFinalOutput( 5,
                IntCodeComputer.parseInput("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"),
                new Integer[]{0,1,2,3,4}, false) == 54321;

        assert getFinalOutput( 5,
                IntCodeComputer.parseInput("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"),
                new Integer[]{1,0,4,3,2}, false) == 65210;

        long result = findLargestThrusterSignal( 0, 5, IntCodeComputer.parseInput( input ), false);
        System.out.println( result );
        assert result == 272368;

        assert getFinalOutput(5,
                IntCodeComputer.parseInput("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"),
                new Integer[]{9,8,7,6,5}, true) == 139629729;

        assert getFinalOutput(5,
                IntCodeComputer.parseInput("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1," +
                        "55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"),
                new Integer[]{9,7,8,5,6}, true) == 18216;

        result = findLargestThrusterSignal(5, 10, IntCodeComputer.parseInput( input ), true );
        System.out.println( result );
        assert result == 19741286;
    }
}
