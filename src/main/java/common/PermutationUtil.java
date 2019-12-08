package common;

import java.util.function.Consumer;

public class PermutationUtil
{
    public static <T> void permute( T[] arr, Consumer<T[]> onPermutation )
    {
        permute( arr.length, arr, onPermutation );
    }

    //Heap's algorithm
    private static <T> void permute(int n, T[] arr, Consumer<T[]> onPermutation )
    {
        if( n == 1 )
        {
            onPermutation.accept( arr );
            return;
        }
        for( int i=0; i<n-1; i++ )
        {
            permute( n-1, arr, onPermutation );
            int elementToSwap = n % 2 == 0 ? i : 0;
            swap( arr, elementToSwap, n-1 );
        }
        permute( n-1, arr, onPermutation );
    }

    private static <T> void swap( T[] arr, int a, int b )
    {
        T tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
