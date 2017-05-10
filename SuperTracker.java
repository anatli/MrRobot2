package GenAlg;

import robocode.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * SuperTracker - a Super Sample Robot by CrazyBassoonist based on the robot Tracker by Mathew Nelson and maintained by Flemming N. Larsen
 * <p/>
 * Locks onto a robot, moves close, fires when close.
 */
public class SuperTracker extends AdvancedRobot {
	int moveDirection=1;//which way to move
	/**
	 * run:  Tracker's main run function
	 */
	private Random rnd= new Random(0);
	private int closeDistanceToEnemy;
	private double probToChangeSpeed;
	private int speedRange;
	private int minSpeed;
	
	public void run() {
		setValues();
		setAdjustRadarForRobotTurn(true);//keep the radar still while we turn
		setBodyColor(new Color(128, 128, 50));
		setGunColor(new Color(50, 50, 20));
		setRadarColor(new Color(200, 200, 70));
		setScanColor(Color.white);
		setBulletColor(Color.blue);
		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		turnRadarRightRadians(Double.POSITIVE_INFINITY);//keep turning radar right
		}
	/**
	 * onScannedRobot:  Here's the good stuff
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double absBearing=e.getBearingRadians()+getHeadingRadians();//enemies absolute bearing
		
		double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//enemies later velocity
		
		double gunTurnAmt;//amount to turn our gun
		
		setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//lock on the radar
		
		if(Math.random()<=probToChangeSpeed){
			setMaxVelocity(minSpeed+rnd.nextDouble()*speedRange);//randomly change speed
		}
		
		if (e.getDistance() > closeDistanceToEnemy) {//if distance is greater than 150
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt); //turn our gun
			setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//drive towards the enemies predicted future location
			setAhead((e.getDistance() - 140)*moveDirection);//move forward
			setFire(3);//fire
		}
		
		else{//if we are close enough...
			gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//amount to turn our gun, lead just a little bit
			setTurnGunRightRadians(gunTurnAmt);//turn our gun
			setTurnLeft(-90-e.getBearing()); //turn perpendicular to the enemy
			setAhead((e.getDistance() - 140)*moveDirection);//move forward
			setFire(3);//fire
		}	
	}
	public void setValues(){
		try (Scanner reader = new Scanner(this.getDataFile("C:\\Users\\Asus\\workspace\\GeneticAlgorithm\\values.txt"))) {
			closeDistanceToEnemy = Integer.parseInt(reader.next());
			probToChangeSpeed = Double.parseDouble(reader.next());
			speedRange = Integer.parseInt(reader.next());
			minSpeed = Integer.parseInt(reader.next());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void onHitWall(HitWallEvent e){
		moveDirection=-moveDirection;//reverse direction upon hitting a wall
	}
	/**
	 * onWin:  Do a victory dance
	 */
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}
//
//	public void onBattleEnded(BattleEndedEvent event){
//		double score=event.getResults().getScore();
//		PrintWriter pw;
//		try {
//			String path="C:\\Users\\Asus\\workspace\\GeneticAlgorithm\\score.txt";
//			pw = new PrintWriter(new FileOutputStream(path,false));
//			pw.print(((Double)score).toString());
//			
//		pw.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
}
