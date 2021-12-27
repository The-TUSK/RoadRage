package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A concrete class responsible for constructing of Truck type vehicles
 * */
public class Truck extends AbstractVehicle{

    /**
     * The main constructor of the Human class
     * @param theX the vehicle's starting X position
     * @param theY the vehicle's starting Y position
     * @param theDir the vehicle's starting Direction
     * */
    public Truck(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir);

        this.setAlive(true);
        this.setImageFileName("truck.gif");

        //setting the initial values or states for later use when resetting the vehicle
        this.setInitials(theX, theY, theDir,0, "truck_dead.gif", "truck.gif");
    }

    /**
     * Returns a boolean value that checks whether a Truck can cross a certain terrain and/or light.
     * if the terrain is a STREET they will pass
     * if the terrain is a Light they will pass
     * if the terrain is a CROSSWALK and theLight is GREEN or YELLOW they will pass
     * otherwise they will not pass
     * @param terrain a Terrain object, the type of Terrain the vehicle is trying to go to.
     * @param theLight a Light object, if the terrain has a light, checks whether the vehicle will pass the terrain at a certain light.
     * */
    @Override
    public boolean canPass(Terrain terrain, Light theLight){
        if (terrain == Terrain.STREET) return true;
        else if (terrain == Terrain.LIGHT) return true;
        else if (terrain == Terrain.CROSSWALK && theLight == Light.GREEN) return true;
        else return terrain == Terrain.CROSSWALK && theLight == Light.YELLOW;
    }

    /**
     * Returns a Direction object of where the Truck prefers to go.
     * The truck randomly picks to travel forwards, leftwards, or rightwards if the terrain is a STREET, LIGHT, OR CROSSWALK
     * reverses direction otherwise.
     * @param theNeighbors a HashMap of key Direction and value Terrain.
     * */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors){
        //the truck determines if it can go left, right, or forwards
        final List<Direction> possibleDirections = new ArrayList<>();

        Direction newDirection;
        if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK || theNeighbors.get(getDirection().left()) == Terrain.STREET
                || theNeighbors.get(getDirection().left()) == Terrain.LIGHT) {
            possibleDirections.add(getDirection().left());

        }

        if ( theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK || theNeighbors.get(getDirection().right()) == Terrain.STREET
                || theNeighbors.get(getDirection().right()) == Terrain.LIGHT) {
            possibleDirections.add(getDirection().right());

        }

        if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK || theNeighbors.get(getDirection()) == Terrain.STREET
                || theNeighbors.get(getDirection()) == Terrain.LIGHT) {
            possibleDirections.add(getDirection());

        }

        //if it cannot turn forward, left, or right, then it reverses.
        if (possibleDirections.isEmpty()) {
            newDirection = getDirection().reverse();

        }
        //randomly selects any of the possible directions to go to.
        else {
            Random random = new Random();
            final int randomIndex = random.nextInt(possibleDirections.size());
            newDirection = possibleDirections.get(randomIndex);
        }

        return newDirection;
    }
}
