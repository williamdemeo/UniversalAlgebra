package org.universalalgebra.transposition;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uacalc.alg.conlat.BasicBinaryRelation;
import org.uacalc.alg.conlat.BasicPartition;
import org.uacalc.alg.conlat.BinaryRelation;

public class TranspositionTest {

	private BasicPartition alpha;
	private BasicPartition beta;
	private BasicPartition gamma;
	private BasicPartition delta;
	private BasicPartition theta;
	private BasicBinaryRelation br1;
	private BasicBinaryRelation br_delta;
	private BasicBinaryRelation br_theta;
	

	@Test
	public void constructors() {
		// (just to be sure we are using Ralph's constructors properly)
		
		// alternative way to define the partitions
		BasicPartition test_alpha = new BasicPartition(new int[] {-2, 0, -2, 2, -2, 4});
		BasicPartition test_beta = new BasicPartition(new int[] {-2, 0, -4, 2, 2, 2});
		BasicPartition test_gamma = new BasicPartition(new int[] {-2, -2, 0, -2, 1, 3});

		setup();

		// TEST we get the same partitions
		assertEquals(test_alpha, alpha);
		assertEquals(test_beta, beta);
		assertEquals(test_gamma, gamma);
		
		// TEST toString gives what we expect
		assertEquals("|0,1|2,3|4,5|",test_alpha.toString());
		assertEquals("|0,1|2,3,4,5|",test_beta.toString());
		assertEquals("|0,2|1,4|3,5|",test_gamma.toString());
	}
	

	//// TEST composing two BasicPartition objects ////
	@Test
	public void composePartitions() {

		setup();
		// |0|1|2|3|
		BasicPartition zero= BasicPartition.zero(4);

		BasicBinaryRelation comp = Transposition.compose(zero,theta);
		System.out.print("|0|1|2|3| * |0,1|2,3| =  ");
		System.out.println(Transposition.toString(comp));
		assertTrue("This composition should be reflexive", comp.isReflexive());
		assertTrue("This composition should be symmetric", comp.isSymmetric());
		assertEquals(0, comp.compareTo(theta));
		
		comp = Transposition.compose(delta,theta);
		System.out.print("|0,2|1|3| * |0,1|2,3| =  ");
		System.out.println(Transposition.toString(comp));
		assertTrue("This composition should be reflexive", comp.isReflexive());
		assertFalse("composition should not be symmetric", comp.isSymmetric());
		
		BasicBinaryRelation comp1 = Transposition.compose(theta,delta);
		System.out.print("|0,1|2,3| * |0,2|1|3| =  ");
		System.out.println(Transposition.toString(comp1));
		assertTrue("This composition should be reflexive", comp1.isReflexive());
	}
	
	//// TEST composing two BasicBinaryRelation objects ////
	@Test
	public void composeRelations() {

		setup();
		br_delta = new BasicBinaryRelation(delta.getPairs(),4);
		br_theta = new BasicBinaryRelation(theta.getPairs(),4);
		BasicBinaryRelation comp2 = Transposition.compose(br_delta, br_theta);
		System.out.print("|0,2|1|3| * |0,1|2,3| =  ");
		System.out.println(Transposition.toString(comp2));
		assertTrue("This composition should be reflexive", comp2.isReflexive());
		assertFalse("composition should not be symmetric", comp2.isSymmetric());

		BasicBinaryRelation comp =Transposition.compose(delta,theta);
		assertEquals(0, comp2.compareTo(comp));
		
		
		// Test Freese's compose method
		BinaryRelation comp3 = br_delta.compose(br_theta);
		System.out.print("|0,2|1|3| * |0,1|2,3| =  ");
		System.out.println(Transposition.toString(comp3));
	}
	@Test
	public void counterExample() {
		// Check counterexample to:
		// \alpha \leq \beta implies 
		// \beta \cap (\gamma \circ^3 \alpha) \subseteq (\beta \cap \gamma) \circ^3 \alpha
		setup();
		BasicBinaryRelation br_alpha = new BasicBinaryRelation(alpha.getPairs(),6);
		BasicBinaryRelation br_beta = new BasicBinaryRelation(beta.getPairs(),6);
		BasicBinaryRelation br_gamma = new BasicBinaryRelation(gamma.getPairs(),6);
		System.out.println("alpha: " + alpha.toString());
		System.out.println("beta: " + beta.toString());
		System.out.println("gamma: " + gamma.toString());
		
		BinaryRelation gammaCircAlpha = br_gamma.compose(br_alpha);
		BinaryRelation gammaCircAlphaCircGamma = gammaCircAlpha.compose(br_gamma);
		BasicBinaryRelation leftHandSide = Transposition.intersection(br_beta, gammaCircAlphaCircGamma);
		System.out.println("beta cap (gamma circ alpha circ gamma): " + leftHandSide.toString());
		
		BinaryRelation betaCapGamma = Transposition.intersection(br_beta,  br_gamma);
		BinaryRelation bcgCircAlpha = betaCapGamma.compose(alpha);
		BinaryRelation RHS = bcgCircAlpha.compose(betaCapGamma);
		System.out.println("(beta cap gamma) circ alpha circ (beta cap gamma): " + RHS.toString());
		
	}
	
	
	public void setup() {
		
		// construct some basic partitions/relations
		alpha = new BasicPartition("|0,1|2,3|4,5|");
		beta = new BasicPartition("|0,1|2,3,4,5|");
		gamma = new BasicPartition("|0,2|1,4|3,5|");

		// |0,1|2,3|
		theta= new BasicPartition(new int[] {-2, 0, -2, 2});
		// |0,2|1|3|
		delta= new BasicPartition(new int[] {-2, -1, 0, -1});

		br1 = new BasicBinaryRelation(4);
		br1.add(0,0); br1.add(0,1); br1.add(1,0); br1.add(1,1); 
		br1.add(2,2); br1.add(2,3); br1.add(3,2); br1.add(3,3);
	}

}
