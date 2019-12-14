package common;

import java.util.Objects;

public class Point
{
    public int x;
    public int y;
    public int z;

    public Point( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    public Point( int x, int y, int z )
    {
        this( x, y );
        this.z = z;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y &&
                z == point.z;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, z);
    }
}
