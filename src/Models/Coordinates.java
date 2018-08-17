package Models;

public class Coordinates
{
    public int XCoordinate;
    public int YCoordinate;

    public Coordinates(String actionCommand)
    {
        String[] coordinateStrings = actionCommand.split("_");
        this.XCoordinate = Integer.parseInt(coordinateStrings[0]);
        this.YCoordinate = Integer.parseInt(coordinateStrings[1]);
    }

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
