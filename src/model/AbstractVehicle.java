package model;

import java.util.Map;

/**
 * An AbstractClass and the parent class for all types of vehicle
 * */
abstract class AbstractVehicle implements Vehicle {
    /**
     * The death time of the vehicle
     * */
    private int DeathTime;

    /**
     * The name of the vehicle's current image file
     * */
    private String ImageFileName;

    /**
     * If the vehicle is alive true, otherwise false
     * */
    private boolean Alive;

    /**
     * The initial X position of the vehicle
     * */
    private int initialX;

    /**
     * The initial Y position of the vehicle
     * */
    private int initialY;

    /**
     * The initial direction of the vehicle
     * */
    private Direction initialDir;

    /**
     * The initial death time of the vehicle
     * */
    private int initialDeathTime;

    /**
     * The name of the vehicle's death image file
     * */
    private String deathImageFileName;

    /**
     * The name of the vehicle's alive image file
     * */
    private String aliveImageFileName;


    /**
     * The vehicle's current X position
     * */
    private int X;

    /**
     * The vehicle's current Y position
     * */
    private int Y;

    /**
     * The vehicle's current Direction
     * */
    private Direction theDir;

    /**
     * A private constructor, to prevent external instantiation.
     */
    private AbstractVehicle(){}

    /**
     * The constructor for all types of vehicles
     * @param theX an int value that determines where the GUI should be displayed on the X axis.
     * @param theY an int value that determines where the GUI should be displayed on the Y axis.
     * @param theDir a Direction object that determines the initial direction of the vehicle
     * */
    protected AbstractVehicle(int theX, int theY, Direction theDir){
        this.X = theX;
        this.Y = theY;
        this.theDir = theDir;
    }

    /**
     * Sets the {@link AbstractVehicle#DeathTime} field for how long the vehicle stays dead
     * @param DeathTime an int value for how many pokes it takes for the vehicle to resurrect
     * */
    protected void setDeathTime(int DeathTime){
        this.DeathTime = DeathTime;
    }

    /**
     * Sets the {@link AbstractVehicle#Alive} field
     * @param bool a boolean value, true if they are alive, false if they are dead
     * */
    protected void setAlive(boolean bool){
        this.Alive = bool;
    }

    /**
     * Returns a string value of the vehicles image when it is alive
     * */
    protected String getAliveImageFileName(){
        return this.aliveImageFileName;
    }

    /**
     * Sets the vehicles current image
     * */
    protected void setImageFileName(String ImageFileName){
        this.ImageFileName = ImageFileName;
    }

    /**
     * Returns a string value of the vehicles image when it is dead
     * */
    protected String getDeathImageFileName(){
        return this.deathImageFileName;
    }

    /**
     * Returns the vehicle's DeathTime
     * */
    @Override
    public int getDeathTime() {
        return this.DeathTime;
    }

    /**
     * Returns a string value that contains the vehicle's current image file name
     * */
    @Override
    public String getImageFileName() {
        return this.ImageFileName;
    }

    /**
     * Returns the Direction that the Vehicle is currently is facing towards.
     * */
    @Override
    public Direction getDirection() {
        return this.theDir;
    }

    /**
     * Returns the Vehicle's current position in the X-axis.
     * */
    @Override
    public int getX() {
        return this.X;
    }

    /**
     * Returns the Vehicle's current position in the Y-axis.
     * */
    @Override
    public int getY() {
        return this.Y;
    }

    /**
     * Returns a boolean value, true if the vehicle is alive, and false otherwise.
     * */
    @Override
    public boolean isAlive() {
        return this.Alive;
    }

    /**
     * Sets the initial values of the vehicle
     * @param X an int value, the vehicle's initial position on the X-axis
     * @param Y an int value, the vehicle's initial position on the Y-axis
     * @param Dir a Direction object, the vehicle's initial direction.
     * @param DeathTime an int value, the vehicle's DeathTime
     * @param deathImageFileName a String value, the name of the vehicle's death image.
     * @param aliveImageFileName a String value, the name of the vehicle's alive image.
     * */
    protected void setInitials(int X, int Y, Direction Dir, int DeathTime, String deathImageFileName, String aliveImageFileName){
        this.initialX = X;
        this.initialY = Y;
        this.initialDir = Dir;
        this.initialDeathTime = DeathTime;
        this.deathImageFileName = deathImageFileName;
        this.aliveImageFileName = aliveImageFileName;
    }

    /**
     * Returns a boolean value that checks whether a vehicle can cross a certain terrain and/or light.
     * @param theTerrain a Terrain object, the type of Terrain the vehicle is trying to go to.
     * @param theLight a Light object, if the terrain has a light, checks whether the vehicle will pass the terrain at a certain light.
     * */
    @Override
    public boolean canPass(Terrain theTerrain, Light theLight) {
        return false;
    }


    /**
     * Returns a Direction object of where the Vehicle prefers to go.
     * @param theNeighbors a HashMap of key Direction and value Terrain.
     * */
    @Override
    public Direction chooseDirection(Map<Direction, Terrain> theNeighbors) {
        return null;
    }

    /**
     * Determines what happens if the vehicle collides with another vehicle.
     * @param theOther a Vehicle object, the Vehicle that this Vehicle collides with.
     * */
    @Override
    public void collide(Vehicle theOther) {
    }

    /**
     * Pokes the vehicle each GUI repainting.  Used to determine when the vehicle should revive when it is dead.
     * When the vehicle revives, they randomly pick a different direction to travel
     * */
    @Override
    public void poke() {
        if (!isAlive()){
            if (getDeathTime() > 0) {
                setDeathTime(getDeathTime() - 1);
            }
            else if (getDeathTime() <= 0 ) {
                this.setDeathTime(this.initialDeathTime);
                this.setAlive(true);
                this.setImageFileName(this.getAliveImageFileName());
                this.setDirection(Direction.random());
            }
        }
    }

    /**
     * Reset the vehicle to it's initial state (position, direction, alive, and image).
     * */
    @Override
    public void reset() {
        this.setX(this.initialX);
        this.setY(this.initialY);
        this.setDirection(this.initialDir);
        this.setAlive(true);
        this.setImageFileName(this.getAliveImageFileName());
    }

    /**
     * Set's the vehicle's current direction to theDir
     * @param theDir a Direction object
     * */
    @Override
    public void setDirection(Direction theDir) {
        this.theDir = theDir;
    }

    /**
     * Set's the vehicle's current X position to theX
     * @param theX an int value
     * */
    @Override
    public void setX(int theX) {
        this.X = theX;
    }

    /**
     * Set's the vehicle's current Y position to theY
     * @param theY an int value
     * */
    @Override
    public void setY(int theY) {
        this.Y = theY;
    }

    /**
     * Return's the vehicle's class type and current direction.
     * */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " Direction: " + getDirection();
    }
}
