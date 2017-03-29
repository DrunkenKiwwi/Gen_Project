package modele.phenotype;

import javafx.collections.ObservableFloatArray;

public class Eye {

	private EyeColor color;
	private float largeur = 0;
	private float height = 0;
	private ObservableFloatArray iniPoints = null;
	private ObservableFloatArray pointsUpdater = null;

	public Eye(EyeColor color, float largeur, float height) {
		this.color = color;
		setLargeur(largeur);
		setHeight(height);
	}

	public EyeColor getCouleurYeux() {
		return color;
	}

	public void setColor(EyeColor eyeColor) {
		this.color = eyeColor;
	}

	public float getLargeur() {
		return this.largeur;
	}

	public void setLargeur(float distance) {
		this.largeur = distance;
	}

	public float getHeight() {
		return this.height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public ObservableFloatArray getIniPoints() {
		return iniPoints;
	}

	public void setIniPoints(ObservableFloatArray iniPoints) {
		this.iniPoints = iniPoints;
		this.pointsUpdater = iniPoints;
	}

	public ObservableFloatArray getPointsUpdater() {
		return pointsUpdater;
	}

	public void updateDistanceNez(float distance) {
		for (int i = 0; i < pointsUpdater.size(); i++) {
			pointsUpdater.set(i, iniPoints.get(i) + distance);
		}
	}

}
