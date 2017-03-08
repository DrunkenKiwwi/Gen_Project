package controleur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import modele.Human;
import modele.phenotype.Eye;
import utils.EnvironmentThreeD;

public class Controller {

	@FXML
	private Slider sliderHauteurVisage;

	@FXML
	private Slider sliderDistanceYeux;

	@FXML
	private Slider sliderDistanceSourcils;

	@FXML
	private Slider sliderFinesseBouche;

	@FXML
	private Slider sliderLargeurVisage;

	@FXML
	private Slider sliderHauteurYeux;

	@FXML
	private Slider sliderHauteurSourcils;

	@FXML
	private Slider sliderHauteurBouche;

	@FXML
	private Slider sliderGrosseurOreilles;

	@FXML
	private Slider sliderHauteurOreilles;

	@FXML
	private Slider sliderGrosseurNez;

	@FXML
	private Slider sliderHauteurNez;

	@FXML
	private Slider sliderEcartYeux;

	@FXML
	private ChoiceBox<String> choiceBoxLongueurCheveux;

	@FXML
	private ChoiceBox<String> choiceBoxCouleurCheveux;

	@FXML
	private ChoiceBox<String> choiceBoxYeux;

	@FXML
	private Pane pane3D;

	private Human human = null;
	
	private EnvironmentThreeD envirnm = new EnvironmentThreeD();

	@FXML
	public void initialize() {
		pane3D.getChildren().add(envirnm.buildWorld(pane3D, (int) pane3D.getPrefWidth(), (int) pane3D.getPrefHeight()));
	}

	// Fait les Binding et rempli les ChoiceBox
	public void bindingModif() {

		choiceBoxYeux.setItems(FXCollections.observableArrayList("Bleu", "Vert", "Brun"));
		choiceBoxLongueurCheveux.setItems(FXCollections.observableArrayList("Aucun", "Court", "Long"));
		choiceBoxCouleurCheveux.setItems(FXCollections.observableArrayList("Blond", "Brun", "Roux"));

		// TODO - LES BINDING FONT DES NULLPOINTEREXCEPTION
		/*
		 * // Binding du visage
		 * human.getFace().hauteurVisageProp.bind(sliderHauteurVisage.
		 * valueProperty());
		 * human.getFace().largeurVisageProp.bind(sliderLargeurVisage.
		 * valueProperty());
		 * 
		 * // Binding des Oreilles
		 * human.getFace().getEar().grosseurOreilleProp.bind(
		 * sliderGrosseurOreilles.valueProperty());
		 * human.getFace().getEar().hauteurOreilleProp.bind(
		 * sliderHauteurOreilles.valueProperty());
		 * 
		 * // Binding des Yeux
		 * human.getFace().getEye().distanceYeuxProp.bind(sliderDistanceYeux.
		 * valueProperty());
		 * human.getFace().getEye().ecartYeuxProp.bind(sliderEcartYeux.
		 * valueProperty());
		 * human.getFace().getEye().hauteurYeuxProp.bind(sliderHauteurYeux.
		 * valueProperty());
		 * 
		 * // Binding de la bouche
		 * human.getFace().getMouth().finesseBoucheProp.bind(sliderFinesseBouche
		 * .valueProperty());
		 * human.getFace().getMouth().hauteurBoucheProp.bind(sliderHauteurBouche
		 * .valueProperty());
		 * 
		 * // Binding du Nez
		 * human.getFace().getNose().grosseurNezProp.bind(sliderGrosseurNez.
		 * valueProperty());
		 * human.getFace().getNose().hauteurNezProp.bind(sliderHauteurNez.
		 * valueProperty());
		 * 
		 * // Binding des Sourcils
		 * human.getFace().getSourcils().distanceSourcilsProp.bind(
		 * sliderDistanceSourcils.valueProperty());
		 * human.getFace().getSourcils().hauteurSourcilsProp.bind(
		 * sliderHauteurSourcils.valueProperty());
		 */
	}

	// Va contenir les multiples écouteurs
	public void ajouterEcouteurs() {

	}

	public Slider getSliderHauteurVisage() {
		return sliderHauteurVisage;
	}

	public void setSliderHauteurVisage(Slider sliderHauteurVisage) {
		this.sliderHauteurVisage = sliderHauteurVisage;
	}

	public Slider getSliderDistanceYeux() {
		return sliderDistanceYeux;
	}

	public void setSliderDistanceYeux(Slider sliderDistanceYeux) {
		this.sliderDistanceYeux = sliderDistanceYeux;
	}

	public Slider getSliderDistanceSourcils() {
		return sliderDistanceSourcils;
	}

	public void setSliderDistanceSourcils(Slider sliderDistanceSourcils) {
		this.sliderDistanceSourcils = sliderDistanceSourcils;
	}

	public Slider getSliderFinesseBouche() {
		return sliderFinesseBouche;
	}

	public void setSliderFinesseBouche(Slider sliderFinesseBouche) {
		this.sliderFinesseBouche = sliderFinesseBouche;
	}

	public Slider getSliderLargeurVisage() {
		return sliderLargeurVisage;
	}

	public void setSliderLargeurVisage(Slider sliderLargeurVisage) {
		this.sliderLargeurVisage = sliderLargeurVisage;
	}

	public Slider getSliderHauteurYeux() {
		return sliderHauteurYeux;
	}

	public void setSliderHauteurYeux(Slider sliderHauteurYeux) {
		this.sliderHauteurYeux = sliderHauteurYeux;
	}

	public Slider getSliderHauteurSourcils() {
		return sliderHauteurSourcils;
	}

	public void setSliderHauteurSourcils(Slider sliderHauteurSourcils) {
		this.sliderHauteurSourcils = sliderHauteurSourcils;
	}

	public Slider getSliderHauteurBouche() {
		return sliderHauteurBouche;
	}

	public void setSliderHauteurBouche(Slider sliderHauteurBouche) {
		this.sliderHauteurBouche = sliderHauteurBouche;
	}
}
