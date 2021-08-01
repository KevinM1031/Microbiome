  
package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;

public class Painter extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<Protein> proteins;
	private LinkedList<Spore> spores;
	private LinkedList<Resource> resources;
	
	private Object selectedObject;
	
	private MouseControl mouse;
	
	private int tick;
	private boolean showGeneralStats;
	private boolean keepObjectSelected;
	
	private long prevUpdateTime;
	
	protected int x;
	protected int y;
	
	public Painter(int width, int height) {
		setSize(width, height);
		mouse = new MouseControl();
		addMouseListener(mouse);
		
		proteins = new LinkedList<Protein>();
		spores = new LinkedList<Spore>();
		resources = new LinkedList<Resource>();
		
		for(int i = 0; i < 5; i++) {
			String gene3 = "PPNA AAND ANND ANND ADNA NDND NDND ANNA NDND ANNA NNNA NAND NNNA NAND AAND";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 500000);
			proteins.add(protein3);
		}
		
		for(int i = 0; i < 20; i++) {
			String gene3 = "PAPA PAPD PAPD PAPD PAPD PAPD PPND";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 200000);
			proteins.add(protein3);
		}
		
		selectedObject = null;
		showGeneralStats = false;
		keepObjectSelected = false;
		
		x = 0;
		y = 0;
		tick = 0;
		
		prevUpdateTime = System.currentTimeMillis();
	}
	
	@Override
	public void paintComponent(Graphics G) {
		
		G.setColor(Color.cyan);
		G.drawLine(416, 0, 416, getHeight());
		G.drawLine(1250, 0, 1250, getHeight());
		
		((Graphics2D) G).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		LinkedList<Protein> proteinRemoveList = new LinkedList<Protein>();
		LinkedList<Spore> sporeRemoveList = new LinkedList<Spore>();
		LinkedList<Resource> resourceRemoveList = new LinkedList<Resource>();
		
		if(mouse.leftPressed()) keepObjectSelected = !keepObjectSelected;
		if(!keepObjectSelected) selectedObject = null;
		
		// Protein auras
		for(Protein protein : proteins) {
			double tempMod = Utility.tempMod(protein.getTemperature());
			for(AminoAcid acid : protein.getAcids()) {
				if(protein.isHunting() && acid.typeEquals("ND"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) (AminoAcid.ND_RADIUS*tempMod), new Color(255, 127, 0, 31), G);
				else if(protein.isHunting() && acid.typeEquals("NP"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) (AminoAcid.NP_RADIUS*tempMod), new Color(255, 0, 63, 63), G);
				else if(protein.isGathering() && acid.typeEquals("AP"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) (AminoAcid.AP_RADIUS*tempMod), new Color(255, 255, 0, 63), G);
			}
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
			fillCircle((int) spore.getPosition().x, (int) spore.getPosition().y, (int) spore.getRadius(), Color.ORANGE, G);
			
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
		
		if(mouse.rightPressed())
			showGeneralStats = !showGeneralStats;
		if(showGeneralStats) drawGeneralStats(G);
		
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
		
		// Adding proteins if necessary
		if(tick % 501 == 500) {
			String gene3 = "PAPA PAPD PAPD PAPD PAPD PAPD PPND";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 200000);
			proteins.add(protein3);
		}
		
		if(tick % 1001 == 1000) {
			String gene3 = "PPNA AAND ANND ANND ADNA NDND NDND ANNA NDND ANNA NNNA NAND NNNA NAND AAND";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 500000);
			proteins.add(protein3);
		}
		
		if(tick % 20001 == 20000) {
			String gene2 = "PPAA ANND ANND NNNA NNNA DNNA NDND ANNA NNNA NNNA NNNA NPAD NNNA NNNA NNNA NPAD NDND ADND ADNA NNAA NAAD ANNA ANND ANNA ANNA NAAA ADAA NNAA NAAD NNAA NAAD ANND";
			Genome genome2 = new Genome(gene2);
			Protein protein2 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome2, 4000000);
			proteins.add(protein2);
		}
		
		if(tick == 0) {
			String gene2 = "PPAA ANND ANND NNNA NNNA DNNA NDND ANNA NNNA NNNA NNNA NPAD NNNA NNNA NNNA NPAD NDND ADND ADNA NNAA NAAD ANNA ANND ANNA ANNA NAAA ADAA NNAA NAAD NNAA NAAD ANND";
			Genome genome2 = new Genome(gene2);
			Protein protein2 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome2, 4000000);
			proteins.add(protein2);
		}
		
		mouse.update();
		tick++;
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
		G.drawString("[Threat Level] " + protein.getInfo().getThreatLevel(), 10, 75);
		G.drawString("[Mass] " + protein.getMass() + " n", 10, 90);
		G.drawString("[Speed] " + protein.getSpeed() + " p/t", 10, 105);
		
		G.drawString("[Age] " + protein.getAge() + " t", 10, 130);
		G.drawString("[Energy] " + protein.getStorage().getEnergy() + " / " + protein.getStorage().getMaxEnergy() + " J", 10, 145);
		G.drawString("[Energy Usage] " + protein.getEnergyUsage() + " J/t", 10, 160);
		G.drawString("[Temperature] " + (int)(protein.getTemperature()*100)/100.0 + " (" + (int)(protein.getPreferredTemp()*100)/100.0 + "±5) K", 10, 175);
		G.drawString("[Spores Dispersed] " + protein.getSporeCount(), 10, 190);
		
		G.drawString("[Mutation History] " + protein.getGenome().getMutationHistory(), 10, 215);
		G.drawString("[Status] " + protein.getState(), 10, 230);
				
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

		G.drawString("[Age] " + spore.getAge() + " / " + Spore.INCUBATION_TIME, 10, 85);
		
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
		return new Point(mouse.position().x - x, mouse.position().y - y);
	}
}