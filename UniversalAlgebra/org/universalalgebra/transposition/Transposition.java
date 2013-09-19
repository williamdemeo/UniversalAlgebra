package org.universalalgebra.transposition;

import org.uacalc.alg.conlat.BasicBinaryRelation;
import org.uacalc.alg.conlat.BasicPartition;
import org.uacalc.alg.conlat.BinaryRelation;
import org.uacalc.util.IntArray;

public class Transposition {

	// compose two binary relations (that are not necessarily equiv relations)	
	public static BasicBinaryRelation compose(BasicBinaryRelation alpha, BasicBinaryRelation beta) {
		int uniSize = alpha.universeSize();
		
		// Should we allow composing relations on different size universes?
		if (uniSize !=beta.universeSize()) {
			System.err.println("Partitions have different size universes... aborting");
			System.exit(0);
		}
	
		BasicBinaryRelation comp = new BasicBinaryRelation(uniSize);
		
		for (int i=0; i<uniSize; i++ ){
			for (int j=0; j<uniSize; j++ ){
				for (int k=0; k<uniSize; k++ ){
					if (alpha.isRelated(i,j) && beta.isRelated(j,k)){
						comp.add(i,k);
					}
				}
			}
		}
		return comp;
	}
		
		
	// compose two partitions
	public static BasicBinaryRelation compose(BasicPartition alpha, BasicPartition beta) {
		int uniSize = alpha.universeSize();
		
		// Should we allow composing partitions on different size universes?
		if (uniSize !=beta.universeSize()) {
			System.err.println("Partitions have different size universes... aborting");
			System.exit(0);
		}
	
		BasicBinaryRelation comp = new BasicBinaryRelation(uniSize);
		
		for (int i=0; i<uniSize; i++ ){
			for (int j=0; j<uniSize; j++ ){
				int permlevel = BasicPartition.permutabilityLevel(i,j,alpha,beta);
				if (permlevel>0 && permlevel<3){
					comp.add(i,j);
				}
			}
		}
		return comp;
	}

	// return intersection of two BinaryRelation objects
	public static BasicBinaryRelation intersection(BinaryRelation alpha, BinaryRelation beta){
		int uniSize = alpha.universeSize();
		BasicBinaryRelation ans = new BasicBinaryRelation(uniSize);
		for (int j = 0; j < uniSize; j++) {
			for (int k = 0; k < uniSize; k++) {
		        if ( alpha.isRelated(j,k) && beta.isRelated(j,k) ){
		        	ans.add(j,k);
		        }
		      }
		}
		return ans;
		
	}
	
	public static String toString(BinaryRelation rel){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		String sep = "";
		for (IntArray intArray : rel.getPairs()) {
			sb.append(sep);
			sb.append(intArray.toString());
			sep = ",";
		}
		sb.append("}");
		return sb.toString();
	}

}
