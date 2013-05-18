package sSorceressGarden;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.rsbuddy.event.listeners.PaintListener;
import com.rsbuddy.script.ActiveScript;
import com.rsbuddy.script.Manifest;
import com.rsbuddy.script.methods.Bank;
import com.rsbuddy.script.methods.Calculations;
import com.rsbuddy.script.methods.Game;
import com.rsbuddy.script.methods.Inventory;
import com.rsbuddy.script.methods.Mouse;
import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Skills;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.util.Timer;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.GameObject;
import com.rsbuddy.script.wrappers.Npc;
import com.rsbuddy.script.wrappers.Tile;
import com.rsbuddy.script.wrappers.TilePath;

@Manifest(authors={"Suchintan"}, name="sSorceressGarden", keywords={"money, making, suchintan, s, herblore, thieving"}, version=0.2, description="Start at Sorceress Garden or Shantay Pass bank.")
public class SSorceressGarden extends ActiveScript implements PaintListener{

	//Herbs id = 21669
	//Door id = 21687
	//Chest id = 2693
	//Fountain id = 7389
	//magic apprentice = 5532
	GameObject herbs;
	GameObject gate;
	GameObject chest;
	GameObject fountain;
	Npc apprentice;

	Timer timer;

	//Path from Garden House -> Shantay Pass
	public Tile[] houseShantay;



	private String status;

	private final Font font = new Font("Times new roman", Font.BOLD, 16); //$NON-NLS-1$
	private final Color colour = new Color(0,0,0);

	private enum Location {BANK, APPRENTICE_HOUSE, GARDEN, TOO_FAR}; 

	private enum SpriteToAvoid {FIRST_SPRITE, FIRST_SPRITE_SECOND_EVADE, SECOND_SPRITE, THIRD_SPRITE, FOURTH_SPRITE, FIFTH_SPRITE, PICK_HERBS};

	private Tile previousLocation = null;
	private Tile currentLocation = null;
	
	private int startXp;
	private int antiRandomTime = 0;
	Location location;
	@Override
	public boolean onStart(){
		status = "Starting Script"; //$NON-NLS-1$
		log("This is a beta version. If you find any bugs, don't hesitate to voice it."); //$NON-NLS-1$
		startXp = Skills.getCurrentExp(Skills.FARMING);
		timer = new Timer(1000l);
		timer.setEndIn(Long.MAX_VALUE);
		timer.reset();
		Tile[] houseShantay = {
				new Tile(3320, 3140), new Tile(3315, 3142),
				new Tile(3310, 3142), new Tile(3307, 3138),
				new Tile(3305, 3133), new Tile(3304, 3128),
				new Tile(3303, 3123), new Tile(3306, 3120)
		};
		this.houseShantay = houseShantay;
		return true;
	}

	@Override
	public int loop() {
		status = "Running script"; //$NON-NLS-1$
		if(!Game.isLoggedIn()){
			return 600;
		}
		if(Players.getLocal().isIdle()){
			location = getLocation();
			if(location == Location.TOO_FAR){
				log("You are too far. Please re-locate"); //$NON-NLS-1$
				return -1;
			}
			if(Walking.getEnergy() < 40){
				Walking.rest(100);
			}
			if(!Walking.isRunEnabled()){
				Walking.setRun(true);
			}
			switch(location){
			case BANK:
				if(Inventory.isFull()){
					bank();
				}else{
					goToGardenHouse();
				}
				break;
			case APPRENTICE_HOUSE:
				if(Inventory.isFull()){
					goToBank();
				}else{
					apprentice = Npcs.getNearest(5532);
					apprentice.interact("Teleport"); //$NON-NLS-1$
				}
				break;
			case GARDEN:
				if(Inventory.isFull()){
					leaveGarden();
				}else{
					fountain = Objects.getNearest(7389);
					gate = Objects.getNearest(21687);
					//5480
					if(fountain == null){
						System.out.println("Fountain is null right now..");
						System.out.println("Player location: " + Players.getLocal().getLocation());
						break;
					}
					if(gate == null){
						System.out.println("Gate is null right now..");
						System.out.println("Player location: " + Players.getLocal().getLocation());
						break;
					}
					if(Calculations.distanceBetween(fountain.getLocation(), gate.getLocation()) > Calculations.distanceTo(fountain.getLocation())){
						Walking.stepTowards(gate.getLocation());
						gate.interact("Open"); //$NON-NLS-1$
						sleep(1800);
					}else{
						if(Players.getLocal().getLocation().getY() <= 5482 && Players.getLocal().getLocation().getX() > 2908){
							Mouse.click(Calculations.tileToScreen(Safespots.tileBeforeFirstSprite[0]), true);
							sleep(2400);
						}
						SpriteToAvoid spriteToAvoid = determineSpriteToAvoid();
						if(spriteToAvoid != null){
							moveToTheNextStep(spriteToAvoid);
						}
					}
				}
				break;
			}
		}
		return 600;
	}

	private void moveToTheNextStep(SpriteToAvoid spriteToAvoid) {
		previousLocation = currentLocation;

		switch(spriteToAvoid){
		case FIRST_SPRITE:
			Sprites.updateSpriteLocation(1);
			if(Sprites.firstSprite == null){
				return;
			}
			currentLocation = Sprites.firstSprite.getLocation();

			if(currentLocation.getY()-1 == Players.getLocal().getLocation().getY()){
				Mouse.click(Calculations.tileToScreen(Safespots.tileAtSafespotForFirstSprite[0]), true);
				previousLocation = null;
			}
			return;
		case FIRST_SPRITE_SECOND_EVADE:
			Sprites.updateSpriteLocation(1);
			if(Sprites.firstSprite == null){
				return;
			}
			currentLocation = Sprites.firstSprite.getLocation();

			if(previousLocation == null){
				return;
			}

			if(currentLocation.getY() == Players.getLocal().getLocation().getY() || currentLocation.getY() == Players.getLocal().getLocation().getY()+1){
					Mouse.click(Calculations.tileToMap(Safespots.tileAtSafespotForSecondSprite[0]), true);
					previousLocation = null;
			}
			return;
		case SECOND_SPRITE:
			Sprites.updateSpriteLocation(2);
			if(Sprites.secondSprite == null){
				return;
			}
			currentLocation = Sprites.secondSprite.getLocation();


//			if(currentLocation.getY() < Players.getLocal().getLocation().getY() +2){
			if(currentLocation.getY() == Players.getLocal().getLocation().getY()+1){
				if(currentLocation.getY() < previousLocation.getY()){					
					Mouse.click(Calculations.tileToScreen(Safespots.tileAtSafespotForThirdSprite[0]), true);
					previousLocation = null;
				}
			}
			return;
		case THIRD_SPRITE:
			Sprites.updateSpriteLocation(3);
			if(Sprites.thirdSprite == null){
				return;
			}
			currentLocation = Sprites.thirdSprite.getLocation();

			if(currentLocation.getY() == Players.getLocal().getLocation().getY()){
				
				Mouse.click(Calculations.tileToMap(Safespots.tileAtSafespotForFourthSprite[0]), true);
				previousLocation = null;
			}
			return;
		case FOURTH_SPRITE:
			Sprites.updateSpriteLocation(4);
			if(Sprites.fourthSprite == null){
				return;
			}
			currentLocation = Sprites.fourthSprite.getLocation();

			if(currentLocation.getLocation().equals(new Tile(2915, 5484))){
				Mouse.click(Calculations.tileToMap(Safespots.tileAtSafespotForFifthSprite[0]), true);
				previousLocation = null;
			}
			return;
		case FIFTH_SPRITE:
			Sprites.updateSpriteLocation(5);
			if(Sprites.fifthSprite == null){
				return;
			}
			currentLocation = Sprites.fifthSprite.getLocation();

			if(previousLocation == null){
				return;
			}
			
			if(currentLocation.getLocation().getY() > Players.getLocal().getLocation().getY()+2){
				if(previousLocation.getLocation().getY() < currentLocation.getLocation().getY()){
						Mouse.click(Calculations.tileToMap(Safespots.tileAtHerbsSafespot[0]), true);
						previousLocation = null;
				}
			}
			
			return;
		case PICK_HERBS:
			herbs = Objects.getNearest(21669);
			herbs.interact("Pick");
			sleep(1000);
		}
	}

	private SpriteToAvoid determineSpriteToAvoid() {
		
		Area areaBeforeFirstSprite = new Area(new Tile(2910, 5482), new Tile(2908-2, 5482));
		Area firstSafeSpot = new Area(new Tile(2906, 5485), new Tile(2906, 5486));
		Area secondSafeSpot = new Area(new Tile(2906, 5488), new Tile(2906, 5492));
		Area thirdSafeSpot = new Area(new Tile(2908, 5495), new Tile(2909, 5490-1));
		Area fourthSafeSpot = new Area(new Tile(2909, 5488), new Tile(2912, 5484-1));
		Area fifthSafeSpot = new Area(new Tile(2919, 5484), new Tile(2921, 5485));
		Area herbArea = new Area(new Tile(2925,5487), new Tile(2923-1,5483-1));
		
		if(areaBeforeFirstSprite.contains(Players.getLocal().getLocation())){
			if(!Players.getLocal().getLocation().equals(Safespots.tileBeforeFirstSprite[0])){				
				Mouse.click(Calculations.tileToScreen(Safespots.tileBeforeFirstSprite[0]), true);
			}
			Mouse.move(Calculations.tileToScreen(Safespots.tileAtSafespotForFirstSprite[0]));
			return SpriteToAvoid.FIRST_SPRITE;
		}else if(firstSafeSpot.contains(Players.getLocal().getLocation())){
			if(!Players.getLocal().getLocation().equals(Safespots.tileAtSafespotForFirstSprite[0])){				
				Mouse.click(Calculations.tileToScreen(Safespots.tileAtSafespotForFirstSprite[0]), true);
			}
			Mouse.move(Calculations.tileToMap(Safespots.tileAtSafespotForSecondSprite[0]));
			return SpriteToAvoid.FIRST_SPRITE_SECOND_EVADE;
		}else if(secondSafeSpot.contains(Players.getLocal().getLocation())){
			if(!Players.getLocal().getLocation().equals(Safespots.tileAtSafespotForSecondSprite[0])){				
				Mouse.click(Calculations.tileToScreen(Safespots.tileAtSafespotForSecondSprite[0]), true);
			}
			Mouse.move(Calculations.tileToScreen(Safespots.tileAtSafespotForThirdSprite[0]));
			return SpriteToAvoid.SECOND_SPRITE;
		}else if(thirdSafeSpot.contains(Players.getLocal().getLocation())){
			if(!Players.getLocal().getLocation().equals(Safespots.tileAtSafespotForThirdSprite[0])){				
				Mouse.click(Calculations.tileToScreen(Safespots.tileAtSafespotForThirdSprite[0]), true);
			}
			Mouse.move(Calculations.tileToMap(Safespots.tileAtSafespotForFourthSprite[0]));
			return SpriteToAvoid.THIRD_SPRITE;
		}else if(fourthSafeSpot.contains(Players.getLocal().getLocation())){
			if(!Players.getLocal().getLocation().equals(Safespots.tileAtSafespotForFourthSprite[0])){				
				Mouse.click(Calculations.tileToScreen(Safespots.tileAtSafespotForFourthSprite[0]), true);
			}
			Mouse.move(Calculations.tileToMap(Safespots.tileAtSafespotForFifthSprite[0]));
			return SpriteToAvoid.FOURTH_SPRITE;
		}else if(fifthSafeSpot.contains(Players.getLocal().getLocation())){
			if(!Players.getLocal().getLocation().equals(Safespots.tileAtSafespotForFifthSprite[0])){				
				Mouse.click(Calculations.tileToScreen(Safespots.tileAtSafespotForFifthSprite[0]), true);
			}
			Mouse.move(Calculations.tileToMap(Safespots.tileAtHerbsSafespot[0]));
			return SpriteToAvoid.FIFTH_SPRITE;
		}else if(herbArea.contains(Players.getLocal().getLocation())){
			return SpriteToAvoid.PICK_HERBS;
		}
		
		return null;
	}

	private void leaveGarden() {
		fountain = Objects.getNearest(7389);
		if(fountain != null){
			fountain.interact("Drink-from"); //$NON-NLS-1$
		}
	}

	private void bank() {
		chest = Objects.getNearest(2693);
		if(!Players.getLocal().getLocation().equals(new Tile(3307, 3120))){
			Mouse.click(Calculations.tileToMap(new Tile(3307, 3120)), true);
		}
		chest.interact("Open"); //$NON-NLS-1$
		Bank.depositAll();
		sleep(500);
		Bank.close();
		sleep(500);
	}

	private Location getLocation() {

		Area gardenArea = new Area(new Tile(2905, 5465), new Tile(2930,5497));
		Area shantayArea = new Area(new Tile(3314,3116), new Tile(3298,3133));
		Area apprenticeArea = new Area(new Tile(3326, 3149), new Tile(3299, 3134));
		
		if(gardenArea.contains(Players.getLocal().getLocation())){
			return Location.GARDEN;
		}else if(shantayArea.contains(Players.getLocal().getLocation())){
			return Location.BANK;
		}else if(apprenticeArea.contains(Players.getLocal().getLocation())){
			return Location.APPRENTICE_HOUSE;
		}

		return Location.TOO_FAR;
	}

	private void goToGardenHouse() {
		TilePath path = new TilePath(houseShantay).reverse();	
		while(Calculations.distanceTo(path.getEnd()) > 3){			
			path.randomize(3,3).traverse();
			sleep(1000);
		}
	}

	private void goToBank(){
		TilePath path = (new TilePath(houseShantay));
		while(Calculations.distanceTo(path.getEnd()) > 3){			
			path.randomize(3,3).traverse();
			sleep(1000);
		}
	}

	@Override
	public void onFinish(){
		status = "Closing script"; //$NON-NLS-1$
		log("Thanks for using this bot!"); //$NON-NLS-1$
	}

	@Override
	public void onRepaint(Graphics g) {
		if(timer.getElapsed() - antiRandomTime > 50000){
			antiRandomTime = (int) timer.getElapsed();
			doAntiBan();
		}
		g.setFont(font);
		g.fillRect(10, 350, 500, 125);
		g.setColor(colour);
		g.drawString("Status: " + status, 15, 365); //$NON-NLS-1$
		g.drawString("Current Farming Level: " + Skills.getRealLevel(Skills.FARMING), 15, 390); //$NON-NLS-1$
		g.drawString("Time elapsed: " + Timer.format(timer.getElapsed()), 15, 415); //$NON-NLS-1$
		g.drawString("Exp gained: " + (Skills.getCurrentExp(Skills.FARMING) - startXp), 15, 440); //$NON-NLS-1$
		g.drawString("Herbs picked: " + (Skills.getCurrentExp(Skills.FARMING) - startXp)/25, 15, 465);
	}

	private void doAntiBan() {
//		int randomNum = (int)Math.random()*10+1;
//		Camera.moveRandomly(((int)(Math.random()*3000+2000)));
	}

}
