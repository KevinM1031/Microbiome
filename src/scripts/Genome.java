package scripts;

import java.util.Iterator;
import java.util.LinkedList;

public class Genome {
	
	public static double POINT_MUTATION_CHANCE = 0.001;
	public static double REGION_INSERTION_CHANCE = 0.00005;
	public static double REGION_DELETION_CHANCE = 0.00005;
	public static double REGION_INSERTION_CONNECTION_CHANCE = 0.002;

	private LinkedList<Base> sequence;
	
	private int N;
	private int A;
	private int D;
	private int P;
	
	private int Ph;
	private int Cr;
	private int Nc;
	private int Io;
	private int Fr;
	
	private String strSequence;
	private int generation;
	
	private int pMutations;
	private int iMutations;
	private int dMutations;
	private int cMutations;
	
	public Genome(String strSequence) {
		this(strSequence, 1, 0, 0, 0, 0);
	}
	
	public Genome(String strSequence, int generation, int pM, int iM, int dM, int cM) {
		this.strSequence = Utility.trim(strSequence);
		this.sequence = transcribe(this.strSequence);
		calcMaterials();
		
		this.generation = generation;
		pMutations = pM;
		iMutations = iM;
		dMutations = dM;
		cMutations = cM;
	}
	
	public Genome clone(boolean canMutate) {
		if(!canMutate)
			return new Genome(strSequence, generation, pMutations, iMutations, dMutations, cMutations);
		
		int newPM = pMutations;
		int newIM = iMutations;
		int newDM = dMutations;
		int newCM = cMutations;
		
		int i = 0;
		int di = 0;
		String clone = "";
		for(char base : strSequence.toCharArray()) {
			
			if(di > 0) {
				di--;
				continue;
			}
			
			if(Math.random() < POINT_MUTATION_CHANCE*Environment.RADIATION) {
				clone += Base.randomBaseChar();
				newPM++;
				
			} else if(i%4 == 0 && Math.random() < REGION_INSERTION_CHANCE*Environment.RADIATION) {
				clone += Base.randomBaseChar();
				clone += Base.randomBaseChar();
				clone += Base.randomBaseChar();
				clone += Base.randomBaseChar();
				newIM++;
				
			} else if(clone.length() > 0 && i%4 == 0 && Math.random() < REGION_INSERTION_CONNECTION_CHANCE*Environment.RADIATION) {
				clone = clone.substring(0, clone.length()-1) + "A";
				clone += Base.randomBaseChar();
				clone += Base.randomBaseChar();
				clone += Base.randomBaseChar();
				clone += Base.randomBaseChar();
				newCM++;
				
			} else if(i%4 == 0 && Math.random() < REGION_DELETION_CHANCE*Environment.RADIATION) {
				di = 4;
				newDM++;
				
			} else {
				clone += base;
				
			}
			
			i++;
		}
		
		return new Genome(clone, generation+1, newPM, newIM, newDM, newCM);
	}
	
	public boolean canReplicate(Storage storage) {
				
		return  N <= storage.getN() &&
				A <= storage.getA() &&
				D <= storage.getD() &&
				P <= storage.getP() &&
				Ph <= storage.getPh() &&
				Cr <= storage.getCr() &&
				Nc <= storage.getNc() &&
				Io <= storage.getIo() &&
				Fr <= storage.getFr() &&
				storage.getEnergy() >= storage.getMaxEnergy()*0.9;
	}
	
	public Spore replicate(Storage storage, Point position, int tier) {
		storage.removeN(N);
		storage.removeA(A);
		storage.removeD(D);
		storage.removeP(P);
		storage.removePh(Ph);
		storage.removeCr(Cr);
		storage.removeNc(Nc);
		storage.removeIo(Io);
		storage.removeFr(Fr);
		storage.removeEnergy(storage.getMaxEnergy()/2);

		boolean far = (tier%2 == 0);
		boolean longIncubation = (tier > 2);
		return new Spore(this.clone(true), storage.getMaxEnergy()/2, position, far, longIncubation);
	}
	
	public int length() {
		return sequence.size();
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public String getSequence() {
		return strSequence;
	}
	
	public String getMutationHistory() {
		return pMutations + "p " + iMutations + "i " + dMutations + "d " + cMutations + "c";
	}
	
	public PolypeptideInfo translate(Protein protein, LinkedList<AminoAcid> acids) {
		return translate(protein, acids, sequence.iterator(), new PolypeptideInfo(), 1, 1, protein.getPosition(), protein.getRotation(), true);
	}
	
	// WARNING: assumes that protein is at rotation = 0
	private PolypeptideInfo translate(Protein protein, LinkedList<AminoAcid> acids, Iterator<Base> seqIter,
			PolypeptideInfo info, int branchCount, int currBranch, Point relPos, double relRot, boolean isHead) {
		
		int currSegmentSize = 0;
		Base[] segment = new Base[3];
		Base b;
				
		while(seqIter.hasNext()) {
			b = seqIter.next();
			currSegmentSize++;
						
			if(currSegmentSize <= 3) {
				segment[currSegmentSize-1] = b;
				
			} else if(currSegmentSize == 4) {
				
				currSegmentSize = 0;
				
				Point position;
				double rotation;
				
				if(isHead) {
					position = new Point(relPos.getX(), relPos.getY());
					rotation = relRot;
				} else {
					position = new Point(relPos.getX(), relPos.getY()+AminoAcid.RADIUS*2+AminoAcid.BORDER);
					rotation = relRot - Math.PI + 2*Math.PI/branchCount * currBranch;
				}
				position.rotate(relPos, rotation);
				
				
				for(AminoAcid acid2 : acids) {
					if(acid2.getPosition().distanceTo(position) < AminoAcid.RADIUS*2) {
						return info;
					}
				}
				
				
				AminoAcid acid = new AminoAcid(protein, segment[0], segment[1], segment[2], position);
				acids.add(acid);
				info.addCount(acid);
				
				int nextBranchCount = acid.getBranchCount();
				int s = b.toInt();
				for(int i = s; s != 4 && i <= (isHead ? nextBranchCount : nextBranchCount-1); i++) {
					translate(protein, acids, seqIter, info, nextBranchCount, i, position, rotation, false);
				}
				
				return info;
			}
		}
		
		return info;
	}
	
	private LinkedList<Base> transcribe(String strSequence) {
		LinkedList<Base> sequence = new LinkedList<Base>();
		for(char c : strSequence.toCharArray())
			sequence.add(Base.translateChar(c));
		return sequence;
	}
	
	private void calcMaterials() {
		N = 0;
		A = 0;
		D = 0;
		P = 0;
		
		Ph = 0;
		Cr = 0;
		Nc = 0;
		Io = 0;
		Fr = 0;
		
		int i = 0;
		int m = 0;
		String first2Bases = "";
		for(Base b : sequence) {
			m = i%4;
			
			if(m == 2) {
				switch(AminoAcid.getMineralPair(first2Bases)) {
					case Mineral.Ph: Ph += b.toInt()-1; break;
					case Mineral.Cr: Cr += b.toInt()-1; break;
					case Mineral.Nc: Nc += b.toInt()-1; break;
					case Mineral.Io: Io += b.toInt()-1; break;
					case Mineral.Fr: Fr += b.toInt()-1; break;
				}
			} else if(m == 3)
				first2Bases = "";
			else first2Bases += b.toChar();
			
			switch(b.getBase()) {
				case Base.N: N++; break;
				case Base.A: A++; break;
				case Base.D: D++; break;
				case Base.P: P++; break;
			}
			i++;
		}
	}
}
