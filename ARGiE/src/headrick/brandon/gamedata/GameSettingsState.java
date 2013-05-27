package headrick.brandon.gamedata;

/**
 * Created by Brandon Headrick on 5/27/13.
 * Provides the state of various modifiers for Games
 */
public class GameSettingsState {

    private static GameSettingsState instance = null;
    private double globalRadiusThreshold = Constants.DEFAULT_RAD_METERS;

    private GameSettingsState(){
        //restrict instantiation
    }

    /**
     * returns the singleton object (instance) for the GameSettingsState class
     * @return the GameSettingsState singleton object
     */
    public static GameSettingsState getInstance(){
        if(instance == null){
            instance = new GameSettingsState();
        }
        return instance;
    }

    public double getGlobalRadiusThreshold() {
        return globalRadiusThreshold;
    }

    public void setGlobalRadiusThreshold(double globalRadiusThreshold) {
        this.globalRadiusThreshold = globalRadiusThreshold;
    }
}
