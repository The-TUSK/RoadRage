package model;

import java.util.Map;

/**
 * A concrete class responsible for constructing Taxi type vehicles
 * */
public class Taxi extends AbstractVehicle{
    /**
     * The taxis timer at a red ligth
     * */
    private int redLightTimer;

    /**
     * If the vehicle is stopped
     * */
    private boolean stopped = false;

    /**
     * The main constructor of the Taxi class
     * @param theX the vehicle's starting X position
     * @param theY the vehicle's starting Y position
     * @param theDir the vehicle's starting Direction
     * */
    public Taxi(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir);

        this.setAlive(true);
        this.setDeathTime(15);
        this.setImageFileName("taxi.gif");

        //setting the initial values or states for later use when resetting the vehicle
        this.setInitials(theX, theY, theDir, 15, "taxi_dead.gif", "taxi.gif");
    }

    /**
     * Sets the {@link Taxi#stopped} field to true or false whenever the vehicle is stopped or not
     * */
    private void setStopped(boolean bool){
        this.stopped = bool;
    }

    /**
     * Returns a boolean value that checks if the vehicle is currently stopped
     * */
    private boolean getStopped(){
        return this.stopped;
    }

    /**
     * Sets the {@link Taxi#redLightTimer} field to some int value  that determines
     * how long the vehicle should stop when at a red light.
     * @param time an int value for how many pokes does it take for the vehicle to stop when at a red light and then move
     * */
    private void setRedLightTimer(int time){
        this.redLightTimer = time;
    }

    /**
     * Returns the int value of {@link Taxi#redLightTimer}
     * */
    private int getRedLightTimer(){
        return this.redLightTimer;
    }

    /**
     * Returns a boolean value that checks whether a Taxi can cross a certain terrain and/or light.
     * if the terrain is a STREET they will pass
     * if the terrain is a Light and theLight is GREEN or YELLOW they will pass
     * if the terrain is a CROSSWALK and theLight is GREEN they will pass
     * if the terrain is a CROSSWALK and theLight is YELLOW they will pass
     * if the terrain is CROSSWALK and theLight is Red they will wait for three pokes and then move.
     * @param terrain a Terrain object, the type of Terrain the vehicle is trying to go to.
     * @param theLight a Light object, if the terrain has a light, checks whether the vehicle will pass the terrain at a certain light.
     * */
    @Override
    public boolean canPass(Terrain terrain, Light theLight){
        //if the RedLightTimer is greater than zero then subtract 1 from it
        if (this.getRedLightTimer()> 0) {
            this.setRedLightTimer(this.getRedLightTimer()- 1);
        }
        if (terrain == Terrain.STREET) return true;
        else if (terrain == Terrain.LIGHT && theLight == Light.GREEN) return true;
        else if (terrain == Terrain.LIGHT && theLight == Light.YELLOW) return true;
        else if (terrain == Terrain.CROSSWALK && theLight == Light.GREEN){
            //sets the red light timer to zero
            this.setRedLightTimer(0);
            //sets the stopped value of the vehicle to false
            if (getStopped()) setStopped(false);
            //then return true
            return true;
        }
        else if (terrain == Terrain.CROSSWALK && theLight == Light.YELLOW) return true;
        //if the terrain is CROSSWALK AND theLight is red and the vehicle has not stopped yet
        else if (terrain == Terrain.CROSSWALK && theLight == Light.RED && !getStopped()){
            //then set the vehicle to stop
            this.setStopped(true);
            //start the red light timer
            this.setRedLightTimer(3);
            //then return false
            return false;
        }
        //if the terrain is a CROSSWALK and the red light timer is 0 (this is only executed when the vehicle is at a red light and is stopped)
        else if (terrain == Terrain.CROSSWALK && theLight == Light.RED && this.getRedLightTimer() == 0){
            //set stopped to false
            this.setStopped(false);
            //then return to true
            return true;
        }
        else return false;
    }

    /**
     * Returns a Direction object of where the Taxi prefers to go.
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
     * if the taxi is alive and the vehicle they are colliding with is alive, and it is a truck they will die
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
