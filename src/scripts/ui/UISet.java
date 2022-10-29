package scripts.ui;

import scripts.objects.Block;
import scripts.objects.MineralVent;
import scripts.objects.Protein;
import scripts.objects.Resource;
import scripts.objects.Spore;
import scripts.util.Point;

import java.awt.Graphics;
import java.util.LinkedList;

public class UISet {
	
	private UI interfaceUI, interactionUI, configUI, graphicsConfigUI, dataDisplayConfigUI, mechanicsConfigUI, insertProteinsUI,
		viewSamplesUI, addSampleUI, deleteSampleUI, disposeProteinsUI, viewSavesUI, environmentConfigUI, advancedConfigUI,
		photosynthesisConfig, frynxMetabolismConfig, mutationConfig, mineralPairConfig, aminoAcidConfig, behaviorConfig,
		addVentUI, addBlockUI;
	private UI currUI;	
	
	private Point prevMousePos;
	private long prevMouseMovedTime;
	private final long MOUSE_MOVE_BUFFER = 2000l;
	private boolean configOpened;
	
	public UISet(Point pos, InputControl inputCtrl, int width, int height,
			LinkedList<Protein> proteins, LinkedList<Spore> spores, 
			LinkedList<Resource> resources, LinkedList<MineralVent> mineralVents, LinkedList<Block> blocks) {
		prevMouseMovedTime = 0;
		configOpened = false;
		
		graphicsConfigUI = GraphicsConfig.getNewGraphicsConfigUI(pos, inputCtrl);
		dataDisplayConfigUI = DataDisplayConfig.getNewDataDisplayConfigUI(pos, inputCtrl);
		mechanicsConfigUI = MechanicsConfig.getNewMechanicsConfigUI(pos, inputCtrl);
		configUI = Config.getNewConfigUI(pos, graphicsConfigUI, dataDisplayConfigUI, mechanicsConfigUI);
		
		addVentUI = AddVent.getNewAddVentUI(pos, inputCtrl, mineralVents);
		addBlockUI = AddBlock.getNewAddBlockUI(pos, inputCtrl, blocks, width, height);
		photosynthesisConfig = PhotosynthesisConfig.getNewPhotosynthesisConfigUI(pos, inputCtrl);
		frynxMetabolismConfig = FrynxMetabolismConfig.getNewFrynxMetabolismConfigUI(pos, inputCtrl);
		mutationConfig = MutationConfig.getNewMutationConfigUI(pos, inputCtrl);
		mineralPairConfig = MineralPairConfig.getNewMineralPairConfigUI(pos, inputCtrl);
		aminoAcidConfig = AminoAcidConfig.getNewAminoAcidConfigUI(pos, inputCtrl);
		behaviorConfig = BehaviorConfig.getNewBehaviorConfigUI(pos, inputCtrl);
		advancedConfigUI = AdvancedConfig.getNewAdvancedConfigUI(pos, photosynthesisConfig, frynxMetabolismConfig, mutationConfig, mineralPairConfig, aminoAcidConfig, behaviorConfig);
		environmentConfigUI = EnvironmentConfig.getNewEnvironmentConfigUI(pos, inputCtrl, advancedConfigUI);
		insertProteinsUI = InsertProteins.getNewInsertProteinsUI(pos, inputCtrl, proteins, spores, width, height);
		addSampleUI = AddSample.getNewAddSampleUI(pos, inputCtrl);
		deleteSampleUI = DeleteSample.getNewDeleteSampleUI(pos, inputCtrl);
		viewSamplesUI = ViewSamples.getNewViewSamplesUI(pos, inputCtrl, insertProteinsUI, addSampleUI, deleteSampleUI);
		disposeProteinsUI = DisposeObjects.getNewDisposeObjectsUI(pos, inputCtrl, proteins, spores, resources, mineralVents, blocks);
		viewSavesUI = ViewSaves.getNewViewSavesUI(pos, inputCtrl, proteins, spores, resources, mineralVents, blocks, height);
		interactionUI = Interaction.getNewInteractionUI(pos, environmentConfigUI, insertProteinsUI, viewSamplesUI, addBlockUI, disposeProteinsUI, addVentUI, viewSavesUI);
		
		interfaceUI = Interface.getNewInterfaceUI(pos, interactionUI, configUI);
		
		currUI = interfaceUI;
	}
	
	public void update(Point mousePos, boolean mousePressed) {
		if (configOpened || (prevMousePos != null && !prevMousePos.equals(mousePos)))
			prevMouseMovedTime = System.currentTimeMillis();
		
		if (prevMouseMovedTime + MOUSE_MOVE_BUFFER > System.currentTimeMillis()) {
			UI temp = currUI.update(mousePos, mousePressed);
			if (temp == null) prevMouseMovedTime = 0;
			else currUI = temp;
		}
		
		configOpened = !currUI.getTitle().equals(interfaceUI.getTitle());
		prevMousePos = mousePos;
	}
	
	public void draw(Graphics G) {
		if (prevMouseMovedTime + MOUSE_MOVE_BUFFER > System.currentTimeMillis()) currUI.draw(G);
	}
}