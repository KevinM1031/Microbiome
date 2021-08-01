package scripts;

public class PolypeptideInfo {

	private int acidCount;
	
	// Motion
	private int p01; // DA_	Io	simple movement
	private int p02; // NN_	Io	rapid movement - part a
	private int p03; // NA_	Nc	rapid movement - part b
	
	// Detection
	private int p04; // AN_	Ph	prey detection
	private int p05; // AA_	Ph	predator detection
	private int p06; // AD_	Ph	resource detection
	
	// Production
	private int p07; // PP_	Nc	spore production
	
	// Interaction
	private int p08; // AP_	Fr	resource attraction
	
	// Generation
	private int p09; // PA_	Ph	photosynthesis
	private int p10; // PN_	Fr	fr generator
	
	// Relocation
	private int p11; // PD_	Fr	temperature zone seeker
	
	// Dissolution
	private int p12; // ND_	Cr	short range versatile dissolver
	private int p13; // NP_	Cr	wide range specialized dissolver
	
	// Regulation
	private int p14; // DN_	Nc	heater
	private int p15; // DD_	Nc	cooler
	
	// Distraction
	private int p16; // DP_	Io	apparent hostility reducer
	
	public PolypeptideInfo() {
		acidCount = 0;
		p01 = 0;
		p02 = 0;
		p03 = 0;
		p04 = 0;
		p05 = 0;
		p06 = 0;
		p07 = 0;
		p08 = 0;
		p09 = 0;
		p10 = 0;
		p11 = 0;
		p12 = 0;
		p13 = 0;
		p14 = 0;
		p15 = 0;
		p16 = 0;
	}
	
	public void addCount(AminoAcid acid) {
		switch(acid.getCode().substring(0, 2)) {
			case "DA": p01++; break;
			case "NN": p02++; break;
			case "NA": p03++; break;
			case "AN": p04++; break;
			case "AA": p05++; break;
			case "AD": p06++; break;
			case "PP": p07++; break;
			case "AP": p08++; break;
			case "PA": p09++; break;
			case "PN": p10++; break;
			case "PD": p11++; break;
			case "ND": p12++; break;
			case "NP": p13++; break;
			case "DN": p14++; break;
			case "DD": p15++; break;
			case "DP": p16++; break;
		}
		acidCount++;
	}
	
	public int length() {
		return acidCount;
	}
	
	public double getThreatLevel() {
		if(p04 == 0) return 0; 
		return (p12+4*p13) * Math.log(acidCount);
	}
	
	public double getPreyVision() {
		return Math.sqrt(p04)*40;
	}
	
	public double getPredatorVision() {
		return Math.sqrt(p05)*41;
	}
	
	public double getResourceVision() {
		return Math.sqrt(p06)*45;
	}
	
}
