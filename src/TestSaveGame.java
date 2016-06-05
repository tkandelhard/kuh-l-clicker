import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Till Kandelhard
 * @version v0.1
 * 
 * modified: 06.06.2016
 * 
 * simple JUnit tests
 *
 */

public class TestSaveGame {
	
	/**
	 * Create a savegame object write values, loads them and tests them.
	 */
	
	@Test
	public void testSavegame() {
		Savegame savegame = new Savegame();
		savegame.writeSavegame(1, 2, 3);
		savegame.loadSavegame();
		int lvlMilkUpgrades = Integer.parseInt(savegame.getLvlMilkUpgrades());
		int lvlMpsUpgrades = Integer.parseInt(savegame.getLvlMpsUpgrades());
		int lvlMilk = Integer.parseInt(savegame.getMilk());
		assertTrue(lvlMilkUpgrades == 1 && lvlMpsUpgrades == 2 && lvlMilk == 3 );
	}

}
