  
package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Painter extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<Protein> proteins;
	private LinkedList<Spore> spores;
	private LinkedList<Resource> resources;
	
	private LinkedList<MineralVent> mineralVents;
	
	private Object selectedObject;
	
	private InputControl inputCtrl;
	
	private boolean showGeneralStats;
	private boolean keepObjectSelected;
	
	private UISet UISystem;
	
	private long prevUpdateTime;
	
	protected int x;
	protected int y;
	
	public Painter(int width, int height) {
		setSize(width, height);
		inputCtrl = new InputControl();
		addMouseListener(inputCtrl);
		addKeyListener(inputCtrl);
		setFocusable(true);
		requestFocus();
		
		proteins = new LinkedList<Protein>();
		spores = new LinkedList<Spore>();
		resources = new LinkedList<Resource>();
		
		// lower rank predator
		for(int i = 0; i < 10; i++) {
			String gene3 = "PPNN AAPP ANPP ANPP ADAN NDNP NDNP ANPN NDNP ANPN NNPN NANP NNNN NANP AAPP";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()/2.0)), 0, genome3, 500000);
			proteins.add(protein3);
		}
		
		// fr synthesizer
		for(int i = 0; i < 10; i++) {
			String gene3 = "PPDN AAPP PNPP PNPP DAPP ADPP APPN ADPP ADPA PDAP";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 300000);
			proteins.add(protein3);
		}
		
		// photosynthesizer
		for(int i = 0; i < 100; i++) {
			String gene3 = "PAPN PAPP PAPP PAPP PAPP PAPP PPDP";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()/2.0)), 0, genome3, 200000);
			proteins.add(protein3);
		}
		
		// upper rank predator
		String gene2 = "PPPN ANPP ANPP NNNN NNNN DNNN NDNP ANPN NNNN NNNN NNNN NPNP NNNN NNNN NNNN NPNP NDNP ADAP ADNN NNAN NAPP ANPN ANPP ANNN ANPN NAAN ADPN NNAN NAPP NNAN NAPP ANPD";
		Genome genome2 = new Genome(gene2);
		Protein protein2 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()/2.0)), 0, genome2, 4000000);
		proteins.add(protein2);
		
		// trapper
		String gene3 = "PPPN DPPP DPPP ADAP APPP NPPN DDAP ANAN NPPP NPPP";
		Genome genome3 = new Genome(gene3);
		Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 4000000);
		proteins.add(protein3);
		Genome genome4 = new Genome(gene3);
		Protein protein4 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome4, 4000000);
		proteins.add(protein4);
		
		mineralVents = new LinkedList<MineralVent>();
		
		for(int i = 0; i < 3; i++) {
			int x = (int) (Math.random()*(getWidth()-200)+100);
			double s = Math.random() + 1;
			mineralVents.add(new MineralVent(x, getHeight(), 10/s, s, 50));
		}
		
		selectedObject = null;
		showGeneralStats = false;
		keepObjectSelected = false;
		
		x = 0;
		y = 0;
		
		UISystem = new UISet(new Point(10, 10), inputCtrl);
		
		prevUpdateTime = System.currentTimeMillis();
	}
	
	@Override
	public void paintComponent(Graphics G) {
		
		if(inputCtrl.terminateRequested()) System.exit(0);
		
		G.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		((Graphics2D) G).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		LinkedList<Protein> proteinRemoveList = new LinkedList<Protein>();
		LinkedList<Spore> sporeRemoveList = new LinkedList<Spore>();
		LinkedList<Resource> resourceRemoveList = new LinkedList<Resource>();
		
		if(inputCtrl.leftPressed()) keepObjectSelected = !keepObjectSelected;
		if(!keepObjectSelected) selectedObject = null;
		
		// Protein auras and counting acids
		for(Protein protein : proteins) {
			for(AminoAcid acid : protein.getAcids()) {
				if(protein.isHunting() && acid.typeEquals("ND"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) acid.getNDRadius(), new Color(255, 127, 0, 31), G);
				else if(protein.isHunting() && acid.typeEquals("NP"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) acid.getNPRadius(), new Color(255, 0, 63, 63), G);
				else if(protein.isGathering() && acid.typeEquals("AP"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) acid.getAPRadius(), new Color(255, 255, 0, 63), G);
				
				else if(acid.typeEquals("PA"))
					Environment.useLight();
			}
		}
		
		for(MineralVent mineralVent : mineralVents) {
			mineralVent.update(resources, getWidth(), getHeight(), prevUpdateTime);
		}
		
		// Updating resources and drawing them
		for(Resource resource : resources) {
			resource.update(resourceRemoveList, getWidth(), getHeight(), prevUpdateTime);
			drawCircle((int) resource.getPosition().x, (int) resource.getPosition().y, (int) resource.getRadius(), resource.getColor(), G);
			
			if((selectedObject != null && resource.equals(selectedObject)) || (selectedObject == null && 
					Utility.pointCircleCollision(mousePosition(), resource.getRadius()+1, resource.getPosition().x, resource.getPosition().y)))
				selectedObject = resource;
		}
		
		// Updating each acid
		for(Protein protein : proteins) {
			if(!protein.isAlive()) continue;
			for(AminoAcid acid : protein.getAcids()) {
				acid.update(proteins, proteinRemoveList, spores, resources, resourceRemoveList, getWidth(), getHeight(), prevUpdateTime);
				fillCircle((int) acid.getPosition().x, (int) acid.getPosition().y, AminoAcid.RADIUS, acid.getColor(), G);
			}
		}
		
		// Updating each spore and drawing them
		for(Spore spore : spores) {
			spore.update(sporeRemoveList, proteins, getWidth(), getHeight(), prevUpdateTime);
			Color c = spore.longIncubation() ? new Color(127, 0, 255) : Color.orange;
			fillCircle((int) spore.getPosition().x, (int) spore.getPosition().y, (int) spore.getRadius(), c, G);
			
			if((selectedObject != null && spore.equals(selectedObject)) || (selectedObject == null && 
					Utility.pointCircleCollision(mousePosition(), spore.getRadius()+1, spore.getPosition().x, spore.getPosition().y)))
				selectedObject = spore;
		}
		
		// Updating each protein
		for(Protein protein : proteins) {
			protein.update(proteins, proteinRemoveList, resources, resourceRemoveList, getWidth(), getHeight(), prevUpdateTime);
			
			if((selectedObject != null && protein.equals(selectedObject)) || (selectedObject == null && 
					Utility.pointCircleCollision(mousePosition(), protein.getRadius(), protein.getPosition().x, protein.getPosition().y)))
				selectedObject = protein;
		}
		
		if(selectedObject != null && selectedObject instanceof Protein) drawProteinStats((Protein)selectedObject, G);
		else if(selectedObject != null && selectedObject instanceof Spore) drawSporeStats((Spore)selectedObject, G);
		else if(selectedObject != null && selectedObject instanceof Resource) drawResourceStats((Resource)selectedObject, G);
		else keepObjectSelected = false;
		
		if(inputCtrl.rightPressed())
			showGeneralStats = !showGeneralStats;
		if(showGeneralStats) drawGeneralStats(G);
		
//		UISystem.update(mousePosition(), inputCtrl.leftPressed());
//		UISystem.draw(G);
				
		// Removing outdated objects
		for(Protein protein : proteinRemoveList) {
			if(protein.equals(selectedObject))
				selectedObject = null;
			proteins.remove(protein);
		}
		
		for(Spore spore : sporeRemoveList) {
			if(spore.equals(selectedObject))
				selectedObject = null;
			spores.remove(spore);
		}
		
		for(Resource resource : resourceRemoveList) {
			if(resource.equals(selectedObject))
				selectedObject = null;
			resources.remove(resource);
		}

		Environment.resetLight();
		inputCtrl.update();
		prevUpdateTime = System.currentTimeMillis();
	}
	
	private void drawCircle(int x, int y, int r, Color c, Graphics G) {
		G.setColor(c);
		G.drawOval(x-r, y-r, r*2, r*2);
	}
	
	private void fillCircle(int x, int y, int r, Color c, Graphics G) {
		double darknessMult = 0.4;
		G.setColor(new Color(
				(int)Math.max(c.getRed()*darknessMult, 0), 
				(int)Math.max(c.getGreen()*darknessMult, 0), 
				(int)Math.max(c.getBlue()*darknessMult, 0), 127));
		G.fillOval(x-r-AminoAcid.BORDER, y-r-AminoAcid.BORDER, (r+AminoAcid.BORDER)*2, (r+AminoAcid.BORDER)*2);
		G.setColor(c);
		G.fillOval(x-r, y-r, r*2, r*2);
	}
	
	private void fillAura(int x, int y, int r, Color c, Graphics G) {
		G.setColor(c);
		G.fillOval(x-r, y-r, r*2, r*2);
	}
	
	private void drawProteinStats(Protein protein, Graphics G) {
		G.setColor(Color.MAGENTA);
		G.drawString("PROTEIN ORGANISM", 10, 20);
		
		G.drawString("[Gene] " + protein.getGenome().getSequence(), 10, 45);
		G.drawString("[Generation] " + "#" + protein.getGeneration(), 10, 60);
		G.drawString("[Threat Level] " + (int)(protein.getInfo().getThreatLevel()*100)/100.0 + 
				" (" + (int)(protein.getPerceivedThreatLevel()*100)/100.0 + ")", 10, 75);
		G.drawString("[Mass] " + protein.getMass() + " n", 10, 90);
		G.drawString("[Speed] " + (int)(protein.getSpeed()*100)/100.0 + " p/t", 10, 105);
		
		G.drawString("[Age] " + protein.getAge() + " t", 10, 130);
		G.drawString("[Energy] " + protein.getStorage().getEnergy() + " / " + protein.getStorage().getMaxEnergy() + " J", 10, 145);
		G.drawString("[Energy Usage] " + protein.getEnergyUsage() + " J/t", 10, 160);
		G.drawString("[Temperature] " + (int)(protein.getTemperature()*100)/100.0 + " (" + (int)(protein.getPreferredTemp()*100)/100.0 + "±5) K", 10, 175);
		G.drawString("[Spores Dispersed] " + protein.getSporeCount(), 10, 190);
		
		G.drawString("[Mutation History] " + protein.getGenome().getMutationHistory(), 10, 215);
		G.drawString("[Status] " + protein.getState(), 10, 230);
		
		G.drawString("[N Stored] " + protein.getStorage().getN() + " / " + protein.getStorage().getMaxBase(), 10, 260);
		G.drawString("[A Stored] " + protein.getStorage().getA() + " / " + protein.getStorage().getMaxBase(), 10, 275);
		G.drawString("[D Stored] " + protein.getStorage().getD() + " / " + protein.getStorage().getMaxBase(), 10, 290);
		G.drawString("[P Stored] " + protein.getStorage().getP() + " / " + protein.getStorage().getMaxBase(), 10, 305);
		G.drawString("[Ph Stored] " + protein.getStorage().getPh() + " / " + protein.getStorage().getMaxMineral(), 10, 330);
		G.drawString("[Cr Stored] " + protein.getStorage().getCr() + " / " + protein.getStorage().getMaxMineral(), 10, 345);
		G.drawString("[Nc Stored] " + protein.getStorage().getNc() + " / " + protein.getStorage().getMaxMineral(), 10, 360);
		G.drawString("[Io Stored] " + protein.getStorage().getIo() + " / " + protein.getStorage().getMaxMineral(), 10, 375);
		G.drawString("[Fr Stored] " + protein.getStorage().getFr() + " / " + protein.getStorage().getMaxFr(), 10, 390);
		
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getRadius(), Color.MAGENTA, G);
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getPreyVision(), Color.RED, G);
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getPredatorVision(), Color.GREEN, G);
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getResourceVision(), Color.ORANGE, G);
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, Protein.INTERACTION_RADIUS, Color.LIGHT_GRAY, G);
	}
	
	private void drawSporeStats(Spore spore, Graphics G) {
		G.setColor(Color.MAGENTA);
		G.drawString("SPORE", 10, 20);
		
		G.drawString("[Gene] " + spore.getGenome().getSequence(), 10, 45);
		G.drawString("[Generation] " + "#" + spore.getGenome().getGeneration(), 10, 60);

		G.drawString("[Age] " + spore.getAge() + " / " + (spore.longIncubation() ? Spore.INCUBATION_TIME_LONG : Spore.INCUBATION_TIME), 10, 85);
		
		G.drawString("[Mutation History] " + spore.getGenome().getMutationHistory(), 10, 110);
		
		drawCircle((int) spore.getPosition().x, (int) spore.getPosition().y, (int) spore.getRadius()*4, Color.MAGENTA, G);
	}
	
	private void drawResourceStats(Resource resource, Graphics G) {
		G.setColor(Color.MAGENTA);
		G.drawString("RESOURCE", 10, 20);
		
		G.drawString("[Class] " + resource.getClassName(), 10, 45);
		G.drawString("[Variant] " + resource.getVariantName(), 10, 60);
		
		G.drawString("[Amount] " + resource.getAmount(), 10, 85);
		G.drawString("[Age] " + resource.getAge() + " / " + Resource.MAX_AGE, 10, 100);
		
		drawCircle((int) resource.getPosition().x, (int) resource.getPosition().y, (int) resource.getRadius()*2, Color.MAGENTA, G);
	}
	
	private void drawGeneralStats(Graphics G) {
		G.setColor(Color.getHSBColor(0.55f, 1f, 1f));
		String str = "[Proteins] " + proteins.size() + " / " + Microbiome.MAX_PROTEINS;
		G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str)-10, 20);
		
		str = "[Spores] " + spores.size() + " / " + Microbiome.MAX_SPORES;
		G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str)-10, 35);
		
		str = "[Resources] " + resources.size() + " / " + Microbiome.MAX_RESOURCES;
		G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str)-10, 50);
	}
	
	private Point mousePosition() {
		return new Point(inputCtrl.position().x - x, inputCtrl.position().y - y);
	}
}