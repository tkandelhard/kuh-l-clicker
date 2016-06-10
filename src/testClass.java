
public class testClass {

	public static void main(String[] args) {
		Savegame savegame = new Savegame();
		//savegame.writeSavegame(1, 2, 3);
		savegame.loadSavegame();
		System.out.println(savegame.getMilk());
		
		savegame.sendCloudSave(99,99,99);
		

	}

}
