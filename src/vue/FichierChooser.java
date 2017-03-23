package vue;

import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class FichierChooser {

	private File fichierChoisi = null;

	public FichierChooser(Window window) {
		DirectoryChooser fenetre = new DirectoryChooser();
		fenetre.setTitle("Choisir le dossier de destination");
		File repDebut = new File(System.getProperty("user.home"));
		fenetre.setInitialDirectory(repDebut);

		fichierChoisi = fenetre.showDialog(window);
	}

	public File getFichierChoisi() {
		return fichierChoisi;
	}

	
	
}
