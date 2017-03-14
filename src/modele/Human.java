﻿package modele;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import exception.ConstructionException;
import javafx.scene.paint.Color;
import modele.genome.Allele;
import modele.genome.Chromosome;
import modele.genome.DNA;
import modele.genome.Gene;
import modele.genome.SNP;
import modele.genome.TargetSNPs;
import modele.phenotype.Face;

public class Human {

	private DNA dna = null;
	private Face face = null;

	public Human() throws ConstructionException, IOException, URISyntaxException {
		this.dna = new DNA(chrSymByTargets());
		this.face = new Face();
	}

	public DNA getDna() {
		return dna;
	}

	public void setDna(DNA dna) {
		this.dna = dna;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	/**
	 * Retourne la liste des différents chromosomes à inspecter selon les snp à
	 * trouver
	 * 
	 * @param chrNbr
	 *            le numero du chromosome
	 * @return la liste des identifiants des SNPs
	 */
	private Set<String> chrSymByTargets() {
		TargetSNPs[] tgt = TargetSNPs.values();
		Set<String> chrSym = new HashSet<String>();

		for (TargetSNPs t : tgt) {
			chrSym.add(t.getChromosomeNbr());
		}

		return chrSym;
	}

	private void setBrownEyeGene() {
		if (getFace().getEye().getCouleurYeux().equals(Color.BROWN)) {

			for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS16891982.getChromosomeNbr())) {
				chr.getSNPByRS("rs" + TargetSNPs.RS16891982.getId()).setAllele(Allele.C);
			}

			for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS6119471.getChromosomeNbr())) {
				chr.getSNPByRS("rs" + TargetSNPs.RS6119471.getId()).setAllele(Allele.G);
			}

			setNotBlueEyeGene();

		}
	}

	private void setBlueEyeGene() {

		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS12203592.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS12203592.getId()).setAllele(Allele.T);
		}

		setNotBrownEyeGene();

	}

	private void setGreenEyeGene() {
		double rnd = Math.random();

		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS12203592.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS12203592.getId()).setAllele(Allele.T);
		}

		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS16891982.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS16891982.getId()).setAllele(Allele.C);
		}

		if (rnd <= 0.6) {
			setNotBrownEyeGene();
		} else {
			setNotBlueEyeGene();
		}

	}

	/**
	 * Utilise le pourentage des haplotypes dans la population européenne pour
	 * déterminer l'allèle. src:
	 * https://www.ncbi.nlm.nih.gov/projects/SNP/snp_ref.cgi?rs=12913832
	 */
	private void setNotBlueEyeGene() {
		double rnd = Math.random();

		if (rnd <= 0.9) {
			Chromosome[] chrPair = getDna().getChrPair(TargetSNPs.RS12913832.getChromosomeNbr());
			chrPair[0].getSNPByRS("rs" + TargetSNPs.RS12913832.getId()).setAllele(Allele.A);
			chrPair[1].getSNPByRS("rs" + TargetSNPs.RS12913832.getId()).setAllele(Allele.G);
		} else {
			for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS12913832.getChromosomeNbr())) {
				chr.getSNPByRS("rs" + TargetSNPs.RS12913832.getId()).setAllele(Allele.A);
			}
		}
	}
	
	//TODO certains SNPs servent pour les yeux et pour la peau en même temps.
	private void setNotBrownEyeGene() {
		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS12913832.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS12913832.getId()).setAllele(Allele.G);
		}
		
		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS16891982.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS16891982.getId()).setAllele(Allele.G);
		}
		
		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS1426654.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS1426654.getId()).setAllele(Allele.A);
		}

	}

	private void setLightSkinGene(){
		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS12913832.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS12913832.getId()).setAllele(Allele.G);
		}
	}

	private void setNonLightSkinGene(){
		for (Chromosome chr : getDna().getChrPair(TargetSNPs.RS6119471.getChromosomeNbr())) {
			chr.getSNPByRS("rs" + TargetSNPs.RS6119471.getId()).setAllele(Allele.G);
		}
	}

	/**
	 * Met à jour l'ADN selon l'aspect actuel du visage
	 */
	public void updateDNA() {

	}

}
