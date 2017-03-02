package modele.genome;

import java.util.List;

import exception.ConstructionException;

public class Chromosome {

	private List<Gene> genes = null;
	private String dataSrcPath = null;
	private String name = null;

	public Chromosome(String name, List<Gene> genes) throws ConstructionException {
		if (name != null && genes != null) {
			this.name = name;
			this.genes = genes;
			this.dataSrcPath = generatePath();
		} else {
			throw new ConstructionException("CHROMOSOME INVALIDE");
		}
	}

	/**
	 * Cr�e le chemin d'acc�s pour atteindre le fichier li� au chromosome *
	 * Format: chr_*.fas
	 * @return le chemin d'acc�s
	 */
	private String generatePath() {
		return "chr_" + getName().toUpperCase() + ".fas";
	}

	public String getName() {
		return this.name;
	}

}
