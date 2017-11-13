package model;

import object.OBJModel;

public class ObjetoGrafico {

	private OBJModel objModelParser;
	
	private float[] translate;
	private float[] scale;
	private float[] rotate;

	private int[] matrixPosition;
	
	private boolean visible = true;
	
	public OBJModel getObjModelParser() {
		return objModelParser;
	}
	public void setObjModelParser(OBJModel objModelParser) {
		this.objModelParser = objModelParser;
	}
	
	public float[] getTranslate() {
		return translate;
	}
	public void setTranslate(float[] translate) {
		this.translate = translate;
	}
	
	public float[] getScale() {
		return scale;
	}
	public void setScale(float[] scale) {
		this.scale = scale;
	}
	
	public float[] getRotate() {
		return rotate;
	}
	public void setRotate(float[] rotate) {
		this.rotate = rotate;
	}
	
	public int[] getMatrixPosition() {
		return matrixPosition;
	}
	public void setMatrixPosition(int[] matrixPosition) {
		this.matrixPosition = matrixPosition;
	}
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
