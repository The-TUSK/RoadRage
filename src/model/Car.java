package model;

import java.util.Map;

/**
 * A concrete class responsible for constructing Car type vehicles
 * */
public class Car extends AbstractVehicle{
    /**
     * The main constructor of the Car class
     * @param theX the vehicle's starting X position
     * @param theY the vehicle's starting Y position
     * @param theDir the vehicle's starting Direction
     * */
    public Car(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir);

        this.setDeathTime(15);
        this.setAlive(true);
        this.setImageFileName("car.gif");

        //setting the initial values or states for later use when resetting the vehicle
        this.setInitials(theX, theY, theDir, 15, "car_dead.gif", "car.gif");
    }

    /**
     * Returns a boolean value that checks whether a Car can cross a certain terrain and/or light.
     * if the terrain the car would like to go to is STREET they will pass
     * if the terrain is LIGHT and theLight is GREEN OR YELLOW they will pass
     * if the terrain is CROSSWALK and theLight is GREEN they will pass
     * otherwise they will not pass
     * @param terrain a Terrain object, the type of Terrain the vehicle is trying to go to.
     * @param theLight a Light object, if the terrain has a light, checks whether the vehicle will pass the terrain at a certain light.
     * */
    @Override
    public boolean canPass(Terrain terrain, Light theLight){
        if (terrain == Terrain.STREET) return true;
        else if (terrain == Terrain.LIGHT && theLight == Light.GREEN) return true;
        else if (terrain == Terrain.LIGHT && theLight == Light.YELLOW) return true;
        else return terrain == Terrain.CROSSWALK && theLight == Light.GREEN;
    }

    /**
     * Returns a Direction object of where the Car prefers to go.
     * They prefer to drive straight if they can, left, if not forwards, right not forwards or leftwards
     * otherwise they reverse direction
     * @param theNeighbors a HashMap of key Direction and value Terrain.
     * */
    @Override
    public Direction chooseDirection(Map<Direction,Terrain> theNeighbors){
        //if the terrain in front of them is a CROSSWALK/STREET/LIGHT they will pass
        //else they will turn left if the terrain on the left is a CROSSWALK/STREET/LIGHT they will pass
        //else they will turn right if the terrain on the right is a CROSSWALK/STREET/LIGHT they will pass
        //else they will reverse.
        Direction newDirection;
        if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK || theNeighbors.get(getDirection()) == Terrain.STREET
                || theNeighbors.get(getDirection()) == Terrain.LIGHT) {
            newDirection = getDirection();

        } else if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK || theNeighbors.get(getDirection().left()) == Terrain.STREET
                || theNeighbors.get(getDirection().left()) == Terrain.LIGHT) {
            newDirection = getDirection().left();

        } else if ( theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK || theNeighbors.get(getDirection().right()) == Terrain.STREET
                || theNeighbors.get(getDirection().right()) == Terrain.LIGHT) {
            newDirection = getDirection().right();
        } else {
            newDirection = getDirection().reverse();
        }
        return newDirection;
    }

    /**
     * Determines what happens if the vehicle collides with another vehicle.
     * if the Car is alive and the vehicle they are colliding with is alive, and it is a truck they will die
     * and their current image file name turns into their death image file name
     * @param theOther a Vehicle object, the Vehicle that this Vehicle collides with.
     * */
    @Override
    public void collide (Vehicle theOther){
        if (isAlive() && theOther.isAlive() && (theOther.getImageFileName().equals("truck.gif"))) {
            this.setAlive(false);
            this.setImageFileName(this.getDeathImageFileName());
        }
    }
}
