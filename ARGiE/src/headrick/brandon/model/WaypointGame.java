package headrick.brandon.model;

/**
 * The simpliest of game types.  This type of game has quests
 * that are to be done in a sequential order.  They can be as
 * simple as going to location X then going to location Y, or
 * as complex as going to location X to retrieve item A and then
 * go to location Y with item A to get item B.
 * 
 * The means by which items are accepted can be as simple as 
 * arriving at the location or (at the moment) as complicated
 * as answering a question once the user has arrived at that
 * location.
 * @author Brandon Headrick
 */
public class WaypointGame extends BaseGame {
	private String type = "Waypoint";
	private QuestNode quest;
	
}
