
Section 2 - Custom Units of Measurement

tk			Number of elapsed frames (therefore does not represent a constant span of time)
t			Standard measurement of time within code (=1000/(12*speed) seconds)
lm*			Standard measurement of available sunlight (1 lm* is consumed per action of photosynthesis)
px			Standard measurement of distance (pixel)

=======================================================================================================================================

Section 3 - Protein Organisms and Genome Translation

A protein organism (a.k.a. protein) consists of amino acids folded in a specific manner.
Similar to proteins in the real world, these proteins are defined by their genome.

A genome acts as the blueprint of a protein, which is a list consisted of the following bases:
		Niadine (N)
		Altanine (A)
		Dotranine (D)
		Piranine (P)

During genome translation (in other words, creating a protein), a chain of amino acids are created.
The first amino acid in the genome is called the "head", and it is the center of mass and rotation for the protein.
The following acids connect to each other in a DFS-like manner.

If acid B connects to acid A, the relationship between A and B is described as: "A attaches B".
The above relationship can also be represented in reverse as follows: "B is attached to A".

Each amino acid is represented by a sequence of four bases (which means, if len(genome)%4 != 0, the tail end is ignored).
The following four pieces of information derived from these four bases will determine the resulting amino acid:

		1. The first base - determines the geometry of the amino acid (N=2, A=3, D=4, P=6).
			
				*			*			*		  . * .
				O			O		  * O *		  . O .
			N	*		A *   *		D	*		P	* 		
			
			(O represents amino acid)
			(* and . equally represent open connections)
			
		2. The first two bases - determine the property of the amino acid (see Section 4 for details).
			
			NN - Propulsion IIa
			NA - Propulsion IIb
			ND - Dissolver I
			NP - Dissolver II
			AN - Attraction/P
			AA - Repulsion/P
			AD - Attraction/R
			AP - Absorption
			DN - Temperature
			DA - Propulsion I
			DD - Buoyancy
			DP - Buffer/P
			PN - Production/F
			PA - Production/S
			PD - Attraction/T
			PP - Reproduction
		
		3. The third base -  determines the tier or modifier of the amino acid (see Section 4 for details).
		
			N - 1
			A - 2
			D - 3
			P - 4
			
			(the above values represent tiers; when used as modifiers by some amino acids, values may vary)
		
		4. The fourth base - determines the connection offset (shifted clockwise / N=0, A=1, D=2, P="no more connections").
		
				3			2			1		
			  2 O 4		  1 O 3			O 2			O
			N	1		A			D			P	 	
		
			(O represents amino acid)
			(numbers represent the order in which next acids are attached)
			(for all of the above examples, the first base of the amino acid is assumed to be D)
			(if offset >= open connections-1, the base acts as P)

=======================================================================================================================================

Section 4 - Amino Acids

NN - Propulsion IIa
		Rapid propulsion part A (must attach NN).
		"Adds significant speed / axle of NA"
		speed = n*tier + attachedNA.speed (only the first NA has an effect)
		
NA - Propulsion IIb
		Rapid propulsion part B (must be attached to NN / must not attach any other amino acids)
		"Adds significant speed / wheel part of NN"
		speed = tier

ND - Dissolver I
NP - Dissolver II
AN - Attraction/P
AA - Repulsion/P
AD - Attraction/R
AP - Absorption
DN - Temperature
DA - Propulsion I
DD - Buoyancy
DP - Buffer/P
PN - Production/F
PA - Production/S
PD - Attraction/T
PP - Reproduction




