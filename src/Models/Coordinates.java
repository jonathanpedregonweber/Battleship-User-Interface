package Models;

public class Coordinates
{
    public int XCoordinate;
    public int YCoordinate;

    public Coordinates(int xCoordinate, int yCoordinate)
    {
        XCoordinate = xCoordinate;
        YCoordinate = yCoordinate;
    }

    public boolean Equals(Coordinates other)
    {
        return this.XCoordinate == other.XCoordinate && this.YCoordinate == other.YCoordinate;
    }
}
