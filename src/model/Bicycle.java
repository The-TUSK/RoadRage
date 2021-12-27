package model;

import java.util.Map;

/**
 * A concrete class responsible for constructing Bicycle type vehicles
 * */
public class Bicycle extends AbstractVehicle{

    /**
     * The main constructor of the Bicycle class
     * @param theX the vehicle's starting X position
     * @param theY the vehicle's starting Y position
     * @param theDir the vehicle's starting Direction
     * */
    public Bicycle(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir);

        this.setAlive(true);
        this.setImageFileName("bicycle.gif");

        //setting the initial values or states for later use when resetting the vehicle
        this.setInitials(theX, theY, theDir, 35, "bicycle_dead.gif", "bicycle.gif");
    }

    /**
     * Returns a boolean value that checks whether the Bicycle can cross a certain terrain and/or light.
     * if the terrain is a STREET they will pass
     * if the terrain is a TRAIL they will pass
     * if the terrain is a LIGHT or CROSSWALK and theLight is GREEN they will pass
     * otherwise they will not pass
     * @param terrain a Terrain object, the type of Terrain the vehicle is trying to go to.
     * @param theLight a Light object, if the terrain has a light, checks whether the vehicle will pass the terrain at a certain light.
     * */
    @Override
    public boolean canPass(Terrain terrain, Light theLight){
        if (terrain == Terrain.STREET) return true;
        else if (terrain == Terrain.TRAIL) return true;
        else return terrain == Terrain.LIGHT || terrain == Terrain.CROSSWALK && theLight == Light.GREEN;
    }

    /**
     * Returns a Direction object of where the Bicycle prefers to go.
     * Bicycles prefer to turn towards, leftwards, or rightwards to a Trail if they can
     * Else if the terrain is a CROSSWALK/STREET/LIGHT they will turn to that direction.
     * They prefer to travel straight ahead, to the left, if they cannot go forwards, right if not forwards or leftwards.
     * It will reverse direction if none of the other directions are available
     * @param theNeighbors a HashMap of key Direction and value Terrain.
     * */
    @Override
    public Direction chooseDirection(Map<Direction,Terrain> theNeighbors){
        Direction newDirection;

        //if the terrain forwards, to the left, or to the right is a TRAIL they will choose to go to that direction.
        if (theNeighbors.get(getDirection()) == Terrain.TRAIL){
            newDirection = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == Terrain.TRAIL){
            newDirection = getDirection().left();
        } else if (theNeighbors.get(getDirection().right()) == Terrain.TRAIL) {
            newDirection = getDirection().right();
        }
        //else if the terrain forwards is a CROSSWALK/STREET/LIGHT they wil choose that direction
        //else if the terrain to the left is a CROSSWALK/STREET/LIGHT they will choose that direction
        //else if the terrain to the right is a CROSSWALK/STREET/LIGHT they will choose that direction
        //else they will reverse.
        else if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK
                || theNeighbors.get(getDirection()) == Terrain.STREET
                || theNeighbors.get(getDirection()) == Terrain.LIGHT) {
            newDirection = getDirection();
        } else if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK
                || theNeighbors.get(getDirection().left()) == Terrain.STREET
                || theNeighbors.get(getDirection().left()) == Terrain.LIGHT) {
            newDirection = getDirection().left();

        } else if ( theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK
                || theNeighbors.get(getDirection().right()) == Terrain.STREET
                || theNeighbors.get(getDirection().right()) == Terrain.LIGHT) {
            newDirection = getDirection().right();
        } else {
            newDirection = getDirection().reverse();
        }
        return newDirection;
    }

    /**
     * Determines what happens if the vehicle collides with another vehicle.
     * if the vehicle is alive and theOther vehicle is alive, and they are a truck/car/taxi/atv they will die
     * and their image file name is set to its death image file name.
     * @param theOther a Vehicle object, the Vehicle that this Vehicle collides with.
     * */
    @Override
    public void collide (Vehicle theOther){
        if (isAlive() && theOther.isAlive() && (theOther.getImageFileName().equals("truck.gif"))
                || theOther.getImageFileName().equals("car.gif")
                || theOther.getImageFileName().equals("taxi.gif")
                || theOther.getImageFileName().equals("atv.gif")) {
            this.setAlive(false);
            this.setImageFileName(this.getDeathImageFileName());
        }
    }
}
