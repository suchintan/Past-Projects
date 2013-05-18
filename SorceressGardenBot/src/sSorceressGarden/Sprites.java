package sSorceressGarden;

import com.rsbuddy.script.methods.Npcs;
import com.rsbuddy.script.wrappers.Npc;

public class Sprites {
	public static Npc firstSprite = Npcs.getNearest(5547);
	public static Npc secondSprite = Npcs.getNearest(5548);
	public static Npc thirdSprite = Npcs.getNearest(5549);
	public static Npc fourthSprite = Npcs.getNearest(5550);
	public static Npc fifthSprite = Npcs.getNearest(5551);
	
	public static void updateSpriteLocation(final int spriteNum){
		switch(spriteNum){
		case 1:
			firstSprite = Npcs.getNearest(5547);
			return;
		case 2:
			secondSprite = Npcs.getNearest(5548);
			return;
		case 3:
			thirdSprite = Npcs.getNearest(5549);
			return;
		case 4:
			fourthSprite = Npcs.getNearest(5550);
			return;
		case 5:
			fifthSprite = Npcs.getNearest(5551);
			return;
		}
	}
	
}
