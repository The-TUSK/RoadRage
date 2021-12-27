package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A concrete class responsible for constructing Human type vehicles
 * */
public class Human extends AbstractVehicle{

    /**
     * The main constructor of the Human class
     * @param theX the vehicle's starting X position
     * @param theY the vehicle's starting Y position
     * @param theDir the vehicle's starting Direction
     * */
    public Human(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir);

        this.setDeathTime(45);
        this.setAlive(true);
        this.setImageFileName("human.gif");

        //setting the initial values or states for later use when resetting the vehicle
        this.setInitials(theX, theY, theDir, 45, "human_dead.gif", "human.gif");
    }

    /**
     * Returns a boolean value that checks whether a Human can cross a certain terrain and/or light.
     * if the terrain they are trying to pass is GRASS they will pass
     * if the terrain they are passing is a CROSSWALK and theLight is RED OR YELLOW they will pass
     * otherwise they will not pass
     * @param terrain a Terrain object, the type of Terrain the vehicle is trying to go to.
     * @param theLight a Light object, if the terrain has a light, checks whether the vehicle will pass the terrain at a certain light.
     * */
    @Override
    public boolean canPass(Terrain terrain, Light theLight){
        //if the terrain they are trying to pass is GRASS they will pass
        //if the terrain they are passing is a CROSSWALK and theLight is RED OR YELLOW they will pass
        //otherwise they will not pass
        if (terrain == Terrain.GRASS) return true;
        else if (terrain == Terrain.CROSSWALK && theLight == Light.RED) return true;
        else return terrain == Terrain.CROSSWALK && theLight == Light.YELLOW;
    }


    /**
     * Returns a Direction object of where the Human prefers to go.
     * Humans will choose to walk to a CROSSWALK if it is in front, to the left, or to the right of the human.
     * If no cross walks it will randomly walk forwards, leftwards, or rightwards on any grass terrains
     * otherwise it will reverse its direction
     * @param theNeighbors a HashMap of key Direction and value Terrain.
     * */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors){
        //An arraylist responsible for keeping all the possible directions the Human can travel to
        final List<Direction> possibleDirections = new ArrayList<>();

        Direction newDirection;

        //If the human is close to any crosswalk in front, right, or left of them they will turn to it
        if (theNeighbors.get(getDirection().left()) == Terrain.CROSSWALK) return getDirection().left();
        else if (theNeighbors.get(getDirection().right()) == Terrain.CROSSWALK) return getDirection().right();
        else if (theNeighbors.get(getDirection()) == Terrain.CROSSWALK) return getDirection();

        //if any of the terrain in front, left, or right are grass, add it to possibleDirections
        if (theNeighbors.get(getDirection().left()) == Terrain.GRASS) {
            possibleDirections.add(getDirection().left());

        }

        if (theNeighbors.get(getDirection().right()) == Terrain.GRASS) {
            possibleDirections.add(getDirection().right());

        }

        if (theNeighbors.get(getDirection()) == Terrain.GRASS) {
            possibleDirections.add(getDirection());

        }

        //if none are crosswalks or grass go backwards.
        if (possibleDirections.isEmpty()) {
            newDirection = getDirection().reverse();

        }
        //for all the possible turns that a human can take, randomly pick one that they can turn to
        else {
            Random random = new Random();
            final int randomIndex = random.nextInt(possibleDirections.size());
            newDirection = possibleDirections.get(randomIndex);
        }

        return newDirection;
    }

    /**
     * Determines what happens if the vehicle collides with another vehicle.
     * Humans die when they collide with a truck, car, atc, or taxi
     * @param theOther a Vehicle object, the Vehicle that this Vehicle collides with.
     * */
    @Override
    public void collide(Vehicle theOther){
        //if the human is alive, and it collides with a vehicle that is a car, truck, atv, or taxi
        if (isAlive() && theOther.isAlive() && (theOther.toString().equals("car.gif")
                || theOther.getImageFileName().equals("truck.gif")
                || theOther.getImageFileName().equals("atv.gif")
                || theOther.getImageFileName().equals("taxi.gif"))) {
            //set Alive to false, and change their image file name to their death image file name
            this.setAlive(false);
            this.setImageFileName(this.getDeathImageFileName());
        }
    }

}
