package common;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class IntCodeComputer
{
    private long[] instructions;
    private long[] memory;
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
        this.memory = Arrays.copyOf( instructions, instructions.length );
        this.input = input;
        this.output = output;
    }

    public List<Long> getOutput()
    {
        return Arrays.asList( this.output.toArray( new Long[]{} ) );
    }

    public long[] getMemoryState()
    {
        return this.memory;
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
        while( i < memory.length)
        {
            int[] ins = Util.getAsIntArray( getValueAt( i ) );
            int j=ins.length - 1;
            int opcode = j == 0 ? ins[j--] : ins[j--] + ins[j--] * 10;
            int mode1 = getParameterMode( ins, j-- );
            int mode2 = getParameterMode( ins, j-- );
            int mode3 = getParameterMode( ins, j-- );

            if( opcode == 99 )
                break;
            else if( opcode == 1 )
            {
                setValueAt( getValueAt( i+3 ), getParameter(i+1, mode1 ) + getParameter(i+2, mode2 ), mode3 );
                i += 4;
            }
            else if( opcode == 2 )
            {
                setValueAt( getValueAt( i+3 ), getParameter(i+1, mode1 ) * getParameter(i+2, mode2 ), mode3 );
                i += 4;
            }
            else if( opcode == 3 )
            {
                try
                {
                    setValueAt( getValueAt(i+1), input.take(), mode1 );
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                i += 2;
            }
            else if( opcode == 4 )
            {
                try
                {
                    long parameter = getParameter(i+1, mode1 );
                    output.put( parameter );
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                i += 2;
            }
            else if( opcode == 5 )
            {
                if( getParameter(i+1, mode1 ) != 0)
                    i = getParameter(i+2, mode2 );
                else
                    i += 3;
            }
            else if( opcode == 6 )
            {
                if( getParameter(i+1, mode1 ) == 0)
                    i = getParameter(i+2, mode2 );
                else
                    i += 3;
            }
            else if( opcode == 7 )
            {
                setValueAt( getValueAt( i+3 ), getParameter(i+1, mode1 ) < getParameter(i+2, mode2 ) ? 1 : 0, mode3 );
                i += 4;
            }
            else if( opcode == 8 )
            {
                setValueAt( getValueAt( i+3), getParameter(i+1, mode1 ) == getParameter(i+2, mode2 ) ? 1 : 0, mode3 );
                i += 4;
            }
            else if( opcode == 9 )
            {
                relativeBase += getParameter(i+1, mode1 );
                i += 2;
            }
        }
    }

    private void setValueAt( long index, long value, int mode )
    {
        if( mode == 2 )
            index += relativeBase;
        if( index >= this.memory.length )
            resizeMemory( index + 1 );
        this.memory[(int)index] = value;
    }

    private long getValueAt( long index )
    {
        if( index >= this.memory.length )
            resizeMemory( index + 1 );
        return this.memory[(int)index];
    }

    private void resizeMemory( long newSize )
    {
        this.memory = Arrays.copyOf( this.memory, (int)newSize );
    }

    private long getParameter( long index, int mode )
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

    public static long[] parseInput( String input )
    {
        return Arrays.stream( input.split(",") ).mapToLong( val -> Long.parseLong( val.trim() ) ).toArray();
    }
}
