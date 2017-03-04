
package utils;

import java.io.*;
import java.util.*;

/**
 * This class will read first sequence from a Fasta format file
 * 
 * @author http://www.cs.utexas.edu/~mobios/cs329e/rosetta/src/FastaSequence.
 *         java M�J Les g�nies du g�nome
 */

public final class FastaSequenceReader {

	private String[] description;
	private String[] sequence;
	//TODO CHANGER POUR UNE LISTE DE TargetSNPs
	private List<String> targets = null;
	private Map<String, String> sequences = null;

	public FastaSequenceReader(File file, List<String> target) throws IOException {
		this.targets = ListTools.formatList(target);
		readSequenceFromFile(file);
		sequences = createMap();
	}

	/**
	 * Permet de lire les s�quences cibl�es dans un fischier au format FASTA standard
	 * @param file Le fichier � lire.
	 * @throws IOException si le fichier est non-conforme
	 */
	void readSequenceFromFile(File file) throws IOException {
		List<String> desc = new ArrayList<String>();
		List<String> seq = new ArrayList<String>();
		boolean read = false;
		//TODO V�RIFIER SI ON PEUT LENLEVER
		//int trgInitSize = targets.size();
		int index = targets.size() - 1;
		BufferedReader in = new BufferedReader(new FileReader(file));
		StringBuffer buffer = new StringBuffer();
		String line = in.readLine();

		// HEADER
		if (line == null) {
			throw new IOException(file.getName() + " is an empty file");
		}
		if (line.charAt(0) != '>') {
			throw new IOException("First line of " + file.getName() + " should start with '>'");
		} else if (line.contains(targets.get(index))) {
			desc.add(line);
			read = true;
			targets.remove(index);
		}

		// BODY
		for (line = in.readLine().trim(); line != null
				&& (!targets.isEmpty() || seq.size() != desc.size()); line = in.readLine()) {
			// LIGNE DE DESCRIPTION
			if (line.length() > 0 && line.charAt(0) == '>' && line.contains(targets.get(index))) {
				desc.add("rs"+targets.get(index));
				read = true;
				targets.remove(index);
				// LIGNE DE SEQUENCE
			} else if (read) {
				buffer.append(line.trim());
			}
			// LIGNE VIDE - FIN DE SEQUENCE
			if (line.isEmpty() && (targets.size() == index)) {
				seq.add(buffer.toString());
				buffer = new StringBuffer();
				read = false;
				index--;
			}
		}

		// CLOSING
		storeData(seq, desc);

	}

	/**
	 * Met les donn�es dans une structure de donn�e
	 * 
	 * @param seq
	 *            une liste de s�quences
	 * @param desc
	 *            une liste de description de s�quences
	 */
	private void storeData(List<String> seq, List<String> desc) {
		description = new String[desc.size()];
		sequence = new String[seq.size()];

		for (int i = 0; i < desc.size(); i++) {
			description[i] = (String) desc.get(i);
			sequence[i] = (String) seq.get(i);
		}
	}

	/**
	 * Cr�e la map qui contient les s�quences d'ADN et leur description
	 * @return la map
	 */
	private HashMap<String, String> createMap() {
		HashMap<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < description.length; i++) {
			map.put(description[i], sequence[i]);
		}

		return map;
	}

	public Map<String, String> getSequences() {
		return this.sequences;
	}
}