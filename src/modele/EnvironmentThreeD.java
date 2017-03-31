package modele;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import utils.ToolsThreeD;
import utils.importerLib.importers.obj.ObjImporter;
import vue.MessageAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableFloatArray;
import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import modele.phenotype.Eye;
import modele.phenotype.Face;

/*
 * TODO JAVADOC
 * Classe créant une scène movable 3D avec un OBJ dedans
 */
public class EnvironmentThreeD {

	/**
	 * World étant notre Group supérieur, contenant les caméras et l'OBJ
	 */
	private final ToolsThreeD world = new ToolsThreeD();

	/**
	 * Caméra et ses 3 dimensions de vue permettant de regarder dans l'espace
	 */
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
	private final ToolsThreeD cameraX = new ToolsThreeD(), cameraY = new ToolsThreeD(), cameraZ = new ToolsThreeD(),
			axisGroup = new ToolsThreeD();

	private static final double CAMERA_INITIAL_DISTANCE = -15, CAMERA_INITIAL_X_ANGLE = 70.0,
			CAMERA_INITIAL_Y_ANGLE = 320.0;
	private static final double CAMERA_NEAR_CLIP = 0.1, CAMERA_FAR_CLIP = 10000.0;
	private static final double CONTROL_MULTIPLIER = 0.1, SHIFT_MULTIPLIER = 10.0;
	private static final double MOUSE_SPEED = 0.1, MOUSE_WHEEL_SPEED = 0.02, ROTATION_SPEED = 1.0, TRACK_SPEED = 0.3;
	private static final String URL = "/obj/face.obj";

	/**
	 * Variables pour le MouseEvent concernant les positions de la souris
	 */
	private double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY, modifier = 1.0;

	private ObjImporter reader = null;

	/**
	 * Group contenant notre OBJ 3D
	 */
	private ToolsThreeD objGroup;
	private Face face = null;

	public SubScene buildWorld(Pane root, int width, int height) {
		SubScene scene = new SubScene(world, width, height - 10);
		objGroup = new ToolsThreeD();
		face = new Face();
		scene.setFill(Color.GREY);
		handleControls(root);
		scene.setCamera(camera);
		buildImporter();
		buildCamera();
		buildAxes();
		buildObj(true);
		return scene;

	}

	public void changementWorld() {
		world.getChildren().remove(objGroup);
		objGroup.getChildren().clear();
		buildObj(false);
	}

	public Face getFace() {
		return this.face;
	}

	private void buildAxes() {
		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		final Box xAxis = new Box(8.0, 0.03, 0.03);
		final Box yAxis = new Box(0.03, 8.0, 0.03);
		final Box zAxis = new Box(0.03, 0.03, 8.0);

		xAxis.setMaterial(redMaterial);
		yAxis.setMaterial(greenMaterial);
		zAxis.setMaterial(blueMaterial);

		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		world.getChildren().addAll(axisGroup);
	}

	private void buildImporter() {
		try {
			reader = new ObjImporter(getClass().getResource(URL).toExternalForm());
		} catch (IOException e) {
			new MessageAlert("Error loading model " + e.toString());
		}
	}

	/**
	 * Méthode permettant d'importer les .obj et de les mettre dans notre scène
	 * world
	 */

	private void buildObj(boolean firstBuild) {
		Set<String> physionomyGroups = reader.getMeshes();
		Map<String, MeshView> groupMeshes = new HashMap<>();

		final Affine affineIni = new Affine();
		affineIni.prepend(new Rotate(-90, Rotate.X_AXIS));
		affineIni.prepend(new Rotate(90, Rotate.Z_AXIS));
		physionomyGroups.stream().forEach(s -> {
			MeshView genomicPart = reader.buildMeshView(s);
			// TODO Transparent stuff
			genomicPart.setStyle("-fx-opacity: 1;");
			// every part of the obj is transformed with both rotations:
			genomicPart.getTransforms().add(affineIni);
			ObservableFloatArray points3DGroup = ((TriangleMesh) genomicPart.getMesh()).getPoints();

			if (s.contains("Oeil gauche")) {
				Eye eye = getFace().getLEye();
				genomicPart.setMaterial(updateEye(points3DGroup, eye, firstBuild));
			}
			if (s.contains("Oeil droit")) {
				Eye eye = getFace().getREye();
				genomicPart.setMaterial(updateEye(points3DGroup, eye, firstBuild));
			}

			groupMeshes.put(s, genomicPart);
		});

		objGroup.getChildren().addAll(groupMeshes.values());
		world.getChildren().add(objGroup);

	}

	private PhongMaterial updateEye(ObservableFloatArray points, Eye eye, boolean firstBuild) {
		if (firstBuild) {
			eye.setIniPoints(createArrayCopy(points), points);
		}
		points = eye.getPointsUpdater();

		final PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(eye.getCouleurYeux().getColor());
		material.setSpecularColor(Color.BLACK);
		return material;
	}

	private ObservableFloatArray createArrayCopy(ObservableFloatArray original) {
		ObservableFloatArray pTemp = FXCollections.observableFloatArray();
		pTemp.addAll(original);
		return pTemp;
	}

	private void buildCamera() {
		world.getChildren().add(cameraX);
		cameraX.getChildren().add(cameraY);
		cameraY.getChildren().add(cameraZ);
		cameraZ.getChildren().add(camera);
		cameraZ.setRotateZ(180.0);

		camera.setNearClip(CAMERA_NEAR_CLIP);
		camera.setFarClip(CAMERA_FAR_CLIP);
		camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
		cameraX.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
		cameraX.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
	}

	private void handleControls(Pane pane) {

		pane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseOldX = me.getSceneX();
				mouseOldY = me.getSceneY();
			}

		});
		pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				mouseOldX = mousePosX;
				mouseOldY = mousePosY;
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseDeltaX = (mousePosX - mouseOldX);
				mouseDeltaY = (mousePosY - mouseOldY);

				if (me.isControlDown()) {
					modifier = CONTROL_MULTIPLIER;
				}
				if (me.isShiftDown()) {
					modifier = SHIFT_MULTIPLIER;
				}
				if (me.isPrimaryButtonDown()) {
					cameraX.ry.setAngle(cameraX.ry.getAngle() - mouseDeltaX * modifier * ROTATION_SPEED);
					cameraX.rx.setAngle(cameraX.rx.getAngle() - mouseDeltaY * modifier * ROTATION_SPEED);
				} else if (me.isSecondaryButtonDown()) {
					cameraY.t.setX(cameraY.t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
					cameraY.t.setY(cameraY.t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
				}
			}
		});

		pane.setOnScroll(new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent me) {
				double z = camera.getTranslateZ();
				double newZ = z + me.getDeltaY() * MOUSE_WHEEL_SPEED * modifier;
				camera.setTranslateZ(newZ);
			}
		});
		
		//TODO contrôles du clavier 
		pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent me) {
				System.out.println("coq roti");
			}
		});
	}
}
