package model;

import java.util.Map;

/**
 * A concrete class responsible for constructing Atv type vehicles
 * */
public class Atv extends AbstractVehicle{

    /**
     * The main constructor of the Atv class
     * @param theX the vehicle's starting X position
     * @param theY the vehicle's starting Y position
     * @param theDir the vehicle's starting Direction
     * */
    public Atv(int theX, int theY, Direction theDir) {
        super(theX, theY, theDir);

        this.setAlive(true);
        this.setImageFileName("atv.gif");

        //setting the initial values or states for later use when resetting the vehicle
        this.setInitials(theX, theY, theDir, 25, "atv_dead.gif", "atv.gif");
    }

    /**
     * Returns a boolean value that checks whether an Atv can cross a certain terrain and/or light.
     * @param terrain a Terrain object, the type of Terrain the vehicle is trying to go to.
     * @param theLight a Light object, if the terrain has a light, checks whether the vehicle will pass the terrain at a certain light.
     * */
    @Override
    public boolean canPass(Terrain terrain, Light theLight){
        //the Atv can pass any terrain except walls
        return terrain != Terrain.WALL;
    }

    /**
     * Returns a Direction object of where the Atv prefers to go.
     * @param theNeighbors a HashMap of key Direction and value Terrain.
     * */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors){
        //the atv randomly picks any direction they would like to go, as long is it is not the reverse of its direction.
        Direction newDirection = Direction.random();
        while (newDirection == this.getDirection().reverse()) newDirection = Direction.random();
        return newDirection;
    }

    /**
     * Determines what happens if the vehicle collides with another vehicle.
     * if the vehicle is alive and collides with another alive vehicle that is a truck, car, or taxi it will die, and
     * it's image file name will be replaced with its death image file name.
     * @param theOther a Vehicle object, the Vehicle that this Vehicle collides with.
     * */
    @Override
    public void collide (Vehicle theOther){
        if (isAlive() && theOther.isAlive() && (theOther.getImageFileName().equals("truck.gif"))
        || theOther.getImageFileName().equals("car.gif") || theOther.getImageFileName().equals("taxi.gif")) {
            this.setAlive(false);
            this.setImageFileName(this.getDeathImageFileName());
        }
    }

}
