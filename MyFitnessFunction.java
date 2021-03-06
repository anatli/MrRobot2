package run;

import robocode.control.*;
import robocode.control.events.*;
import sun.print.resources.serviceui_de;
import java.util.Random;
import java.util.Scanner;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.*;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
import org.jgap.impl.IntegerGene;
import robocode.BattleEndedEvent;
import robocode.BattleResults;

import java.io.*;

public class MyFitnessFunction extends FitnessFunction{
	
	public static final int MAX_TIMES = 400;
	public MyFitnessFunction(){
		
	}
	@Override
	protected double evaluate(IChromosome a_subject) {
		double fitness=0;
		PrintWriter pw;
		try {
			String path="C:\\Users\\Asus\\workspace\\MyRobots\\bin\\atl\\SuperTracker.data\\values.txt";
			pw = new PrintWriter(path);
			for (int i = 0; i < a_subject.size(); i++) {
			pw.write(a_subject.getGene(i).getAllele().toString()+" ");
			}
			pw.close();
			fitness=battle(a_subject);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fitness;
	}	
	
	public double battle(IChromosome chromosome) throws FileNotFoundException{
		 
		 // Create the RobocodeEngine
		 RobocodeEngine engine =
		 new RobocodeEngine(new java.io.File("C:/robocode"));
		 // Run from C:/Robocode
		 // Show the Robocode battle view
		 engine.setVisible(true);
		 // Create the battlefield
		 int NumPixelRows=64*10; // 10 tiles of 64 pixels
		 int NumPixelCols=64*10;
		 BattlefieldSpecification battlefield = new BattlefieldSpecification(NumPixelRows, NumPixelCols);
		 // 800x600
		 // Setup battle parameters
		 int numberOfRounds = 5;
		 long inactivityTime = 5000;
		 double gunCoolingRate = 1.0;
		 int sentryBorderSize = 50;
		 boolean hideEnemyNames = false;
		 
		 
		 RobotSpecification[] modelRobots =engine.getLocalRepository("atl.SuperTracker*,atl.SuperRamFire*");
		RobotSetup[] robotSetups= new RobotSetup[2];
		robotSetups[0] = new RobotSetup(0.0,0.0,0.0);
		robotSetups[1] = new RobotSetup(600.0,500.0,0.0);
		
		 /* Create and run the battle */
		 BattleSpecification battleSpec =
		 new BattleSpecification(battlefield,
		 numberOfRounds,
		 inactivityTime,
		 gunCoolingRate,
		 sentryBorderSize,
		 hideEnemyNames,
		 modelRobots,
		 robotSetups);
		 engine.addBattleListener(new BattleObserver());
		 // Run our specified battle and let it run till it is over
		 engine.runBattle(battleSpec, true); // waits till the battle finishes
		 // Cleanup our RobocodeEngine
		 engine.close();
		 // Make sure that the Java VM is shut down properly
		 double scoree =BattleObserver.score;
		 System.out.println(scoree);
		return scoree;
	}
	static class BattleObserver extends BattleAdaptor{
		public static double score;
		public void onBattleCompleted(BattleCompletedEvent e){
			score=e.getIndexedResults()[0].getScore();
		}
	}
}

