package run;

import org.jgap.*;
import org.jgap.impl.*;

public class Genetics {
	
	public static void main(String[] args) throws Exception{
	
	final int MAX_EVOLUTIONS = 200;
		
	Configuration conf = new DefaultConfiguration();
	
	FitnessFunction myFunc = new MyFitnessFunction();
	conf.setFitnessFunction(myFunc);
	
	Gene[] myGenes = new Gene[4];
	myGenes[0] = new IntegerGene(conf, 0, 905); // ViewDist
	myGenes[1] = new DoubleGene(conf, 0, 1); // probability
	myGenes[2] = new IntegerGene(conf, 0, 8); // range
	myGenes[3] = new IntegerGene(conf, 0, 8); // speed
	
	Chromosome sampleChromosome = new Chromosome(conf, myGenes);
	conf.setSampleChromosome(sampleChromosome);
	
	conf.setPopulationSize(12);
	Genotype Population=Genotype.randomInitialGenotype(conf);
	
	for (int i = 0; i < MAX_EVOLUTIONS; i++) {
		System.out.println("Generation: " + i);
		System.out.println(Population.toString());
		Population.evolve();
		System.out.println(Population.getFittestChromosome().toString());
	}
	
	IChromosome mostFitChromosome = Population.getFittestChromosome();		
}
	
}
