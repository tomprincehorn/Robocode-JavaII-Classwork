package csci2469_001;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Fury - a robot by Team Fury
 */
public class Fury extends Robot{
		int count = 0;
		double gunTurnAmt;
		String trackName;
		int turnDirection = 1;
	public void run() {
		trackName = null;
		setAdjustGunForRobotTurn(true);
		gunTurnAmt = 10;
		while(true) {
			turnGunRight(gunTurnAmt);
			count++;
			if (count > 2) {
				gunTurnAmt = -10;
			}
			if (count > 3) {
				trackName = null;
			} 
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
		//if (e.getBearing() >= 0) {
		//	turnDirection = 1;
		//} else {
		//	turnDirection = -1;
		//}
		trackName = e.getName();
		if 	(e.getDistance() > 150 && Math.abs(bearingFromGun) <= 3) {
		//if (bearingFromGun >=0){
		//}else{
		//turnGunLeft(bearingFromGun);
		//}
		turnGunRight(bearingFromGun);
		turnRight(e.getBearing());
		if (trackName.equals("SittingDuck")){
		fire(3);
		}
		ahead(e.getDistance() * 0.9);
	}else if (e.getDistance() > 100 && Math.abs(bearingFromGun) <= 3){
			turnGunRight(bearingFromGun);
			turnRight(e.getBearing());
			ahead(e.getDistance() * 0.6);
			if (trackName.equals("SittingDuck")){
			fire(3);
			}else{ fire(1); 
			}
			scan();
		}else if (e.getDistance() > 50 && Math.abs(bearingFromGun) <= 3){
			turnGunRight(bearingFromGun);
			turnRight(e.getBearing());
			fire(3);
			scan();
		}else{ 
			turnGunRight(bearingFromGun);
			turnRight(e.getBearing());
			fire(3);
			scan();
		}
	}
	
	// In the event of getting hit by a bullet, the robot will determine how many other bots are left
	// If more than one bot is left, the robot will stutter by turning 90 degrees right, moving forward 
	// 100 pixels, turning left 25 degrees, then moving backwards 50 pixels
	public void onHitByBullet(HitByBulletEvent e) {
		int players = getOthers();
		if(players != 1){
			turnRight(90);
			ahead(100);
			turnLeft(25);
			back(50);
			
		}
		else if(getOthers() == 1){
			scan();
		}
	
		//back(10);
		// Should do a call command to see how many robots there are, if there's 1
		// then immediately move the radar to where we were shot from
	}
	
	//In the event of hitting a wall, the robot shall back up 200 pixels, stall by moving forward 
	//then back 1 pixel, move forward 200 pixels, then turn left 90 degrees
	public void onHitWall(HitWallEvent e) {
		back(200);
		ahead(1);
		back(1);
		ahead(200);
		turnLeft(90);
	}	

	
	public void onHitRobot(HitRobotEvent e) {
		//if (e.getBearing() >= 0) {
		//	turnDirection = 1;
		//} else {
		//	turnDirection = -1;
		//}
		double gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		turnRight(e.getBearing());
		fire(3);
	}
	// Importing a method to normalize angles so the tank reads it from -180 to 180 degrees
	// Ex, instead of reading 270 degrees, it'll read -90 and turn left rather than right
	// Here is the link to the Github:
	//https://github.com/robo-code/robocode/blob/master/robocode.api/src/main/java/robocode/util/Utils.jav
	public static double normalRelativeAngleDegrees(double angle) {
		return (angle %= 360) >= 0 ? (angle < 180) ? angle : angle - 360 : (angle >= -180) ? angle : angle + 360;
	}
	
	public void onWin(WinEvent e){
		turnLeft(360);
		turnRight(360);
	}
	

}