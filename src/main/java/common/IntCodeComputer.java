package common;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class IntCodeComputer
{
    public static List<Integer> runDiagnostic( int[] instructions )
    {
        return runDiagnostic( 0, instructions );
    }

    public static List<Integer> runDiagnostic( int input, int[] instructions )
    {
        try
        {
            LinkedBlockingQueue<Integer> inputQueue = new LinkedBlockingQueue<>();
            inputQueue.put( input );
            LinkedBlockingQueue<Integer> outputQueue = new LinkedBlockingQueue<>();
            runDiagnostic(inputQueue, outputQueue, instructions );
            return Arrays.asList( outputQueue.toArray( new Integer[]{} ) );
        }
        catch (InterruptedException e ) { e.printStackTrace(); }
        return null;
    }

    public static void runDiagnostic( LinkedBlockingQueue<Integer> input,
                                      LinkedBlockingQueue<Integer> output,
                                      int[] instructions )
    {
        int i=0;
        while( i < instructions.length)
        {
            int[] ins = Util.getAsIntArray( instructions[i] );
            int j=ins.length - 1;
            int opcode = j == 0 ? ins[j--] : ins[j--] + ins[j--] * 10;
            int mode1 = getParameterMode( ins, j-- );
            int mode2 = getParameterMode( ins, j-- );

            if( opcode == 99 )
                break;
            else if( opcode == 1 )
            {
                instructions[instructions[i + 3]] = getParameter(i, 1, mode1, instructions) +
                        getParameter(i, 2, mode2, instructions);
                i += 4;
            }
            else if( opcode == 2 )
            {
                instructions[instructions[i + 3]] = getParameter(i, 1, mode1, instructions) *
                        getParameter(i, 2, mode2, instructions);
                i += 4;
            }
            else if( opcode == 3 )
            {
                try
                {
                    instructions[instructions[i+1]] = input.take();
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                i += 2;
            }
            else if( opcode == 4 )
            {
                try
                {
                    int parameter = getParameter(i, 1, mode1, instructions );
                    //System.out.println(parameter);
                    output.put( parameter );
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                i += 2;
            }
            else if( opcode == 5 )
            {
                if( getParameter(i, 1, mode1, instructions ) != 0)
                    i = getParameter(i, 2, mode2, instructions );
                else
                    i += 3;
            }
            else if( opcode == 6 )
            {
                if( getParameter(i, 1, mode1, instructions ) == 0)
                    i = getParameter(i, 2, mode2, instructions );
                else
                    i += 3;
            }
            else if( opcode == 7 )
            {
                instructions[instructions[i+3]] =
                        getParameter(i, 1, mode1, instructions ) < getParameter(i, 2, mode2, instructions ) ? 1 : 0;
                i += 4;
            }
            else if( opcode == 8 )
            {
                instructions[instructions[i+3]] =
                        getParameter(i, 1, mode1, instructions ) == getParameter(i, 2, mode2, instructions ) ? 1 : 0;
                i += 4;
            }
        }
    }

    private static int getParameter( int i, int offset, int mode, int[] instructions )
    {
        return mode == 1 ? instructions[i+offset] : instructions[instructions[i+offset]];
    }

    private static int getParameterMode( int[] ins, int j )
    {
        return j < 0 ? 0 : ins[j];
    }
}
