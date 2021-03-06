package headrick.brandon.gamedata;

/**
 * Provides common constants that may be used repeatedly in the application
 * !May remove
 * @author Brandon Headrick
 *
 */
public final class Constants {
	
	private Constants(){
		//restrict instantiation
	}
	
	//gametypes
	public static final String WAYPOINT = "waypoint";
	public static final int LABEL_X_OFFSET = 45;
	public static final int LABEL_Y_OFFSET = 40;
	
	//debug field for labeling quests on the map
	public static final char INITIAL_ALPHA_LABEL_VAL = 'A';
    public static final int INITIAL_NUM_LABEL_VAL = 1;

    //Default threshold for GPS coordinates
    public static final double DEFAULT_RAD_METERS = 100.0;

}
