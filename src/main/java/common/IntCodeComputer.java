package common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class IntCodeComputer
{
    private long[] instructions;
    private Map<Long, Long> memory;
    private LinkedBlockingQueue<Long> input;
    private LinkedBlockingQueue<Long> output;
    private int relativeBase;

    public IntCodeComputer( long[] instructions )
    {
        this( instructions, new LinkedBlockingQueue<>(), new LinkedBlockingQueue<>() );
    }

    public IntCodeComputer( long[] instructions, LinkedBlockingQueue<Long> input, LinkedBlockingQueue<Long> output )
    {
        this.instructions = instructions;
        this.input = input;
        this.output = output;
        initializeMemory( instructions );
    }

    public List<Long> getOutput()
    {
        return Arrays.asList( this.output.toArray( new Long[]{} ) );
    }

    public long[] getMemoryState()
    {
        List<Long> locations = memory.keySet().stream().sorted().collect(Collectors.toList());
        long[] state = new long[ locations.size() ];
        int i=0;
        for( Long location : locations )
            state[i++] = memory.get( location );
        return state;
    }

    public void provideInput( long value )
    {
        try
        {
            this.input.put( value );
        }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void runProgram()
    {
        long i=0;
        while( i < memory.size())
        {
            int[] ins = Util.getAsIntArray( read(i, 1) );
            int j=ins.length - 1;
            int opcode = j == 0 ? ins[j--] : ins[j--] + ins[j--] * 10;
            int mode1 = getParameterMode( ins, j-- );
            int mode2 = getParameterMode( ins, j-- );
            int mode3 = getParameterMode( ins, j-- );

            if( opcode == 99 )
                break;
            else if( opcode == 1 )
            {
                write( read( i+3, 1 ), read(i+1, mode1 ) + read(i+2, mode2 ), mode3 );
                i += 4;
            }
            else if( opcode == 2 )
            {
                write( read( i+3, 1 ), read(i+1, mode1 ) * read(i+2, mode2 ), mode3 );
                i += 4;
            }
            else if( opcode == 3 )
            {
                try
                {
                    write( read(i+1, 1), input.take(), mode1 );
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                i += 2;
            }
            else if( opcode == 4 )
            {
                try
                {
                    long parameter = read(i+1, mode1 );
                    output.put( parameter );
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                i += 2;
            }
            else if( opcode == 5 )
            {
                if( read(i+1, mode1 ) != 0)
                    i = read(i+2, mode2 );
                else
                    i += 3;
            }
            else if( opcode == 6 )
            {
                if( read(i+1, mode1 ) == 0)
                    i = read(i+2, mode2 );
                else
                    i += 3;
            }
            else if( opcode == 7 )
            {
                write( read( i+3, 1 ), read(i+1, mode1 ) < read(i+2, mode2 ) ? 1 : 0, mode3 );
                i += 4;
            }
            else if( opcode == 8 )
            {
                write( read( i+3, 1 ), read(i+1, mode1 ) == read(i+2, mode2 ) ? 1 : 0, mode3 );
                i += 4;
            }
            else if( opcode == 9 )
            {
                relativeBase += read(i+1, mode1 );
                i += 2;
            }
        }
    }

    private void write(long index, long value, int mode )
    {
        if( mode == 2 )
            index += relativeBase;
        this.memory.put( index, value );
    }

    private long getValueAt( long index )
    {
        return this.memory.computeIfAbsent( index, key -> 0L );
    }

    private long read(long index, int mode )
    {
        if( mode == 0 )
            return getValueAt( getValueAt( index));
        else if( mode == 1 )
            return getValueAt( index );
        else if( mode == 2 )
            return getValueAt( getValueAt(index) + relativeBase );

        throw new IllegalArgumentException("Invalid mode passed - " + mode );
    }

    private int getParameterMode( int[] ins, int j )
    {
        return j < 0 ? 0 : ins[j];
    }

    private void initializeMemory( long[] instructions )
    {
        this.memory = new HashMap<>();
        for( long i=0; i<instructions.length; i++ )
            this.memory.put(i, instructions[(int) i]);
    }

    public static long[] parseInput( String input )
    {
        return Arrays.stream( input.split(",") ).mapToLong( val -> Long.parseLong( val.trim() ) ).toArray();
    }
}
