package model;

import object.OBJModel;

public class ObjetoGrafico {

	private OBJModel objModelParser;
	
	private float[] translate;
	private float[] scale;
	private float[] rotate;

//	private float translateX = 0;
//	private float translateY = 0;
//	private float translateZ = 0;
//
//	private float rotateX = 0;
//	private float rotateY = 0;
//	private float rotateZ = 0;
//	private float rotateAngle = 0;
//
//	private float scaleX = 0;
//	private float scaleY = 0;
//	private float scaleZ = 0;
	
	private int[] matrixPosition;
	
	public OBJModel getObjModelParser() {
		return objModelParser;
	}
	public void setObjModelParser(OBJModel objModelParser) {
		this.objModelParser = objModelParser;
	}
	
	
	
//	public float getTranslateX() {
//		return translateX;
//	}
//	public void setTranslateX(float translateX) {
//		this.translateX = translateX;
//	}
//	
//	public float getTranslateY() {
//		return translateY;
//	}
//	public void setTranslateY(float translateY) {
//		this.translateY = translateY;
//	}
//	
//	public float getTranslateZ() {
//		return translateZ;
//	}
//	public void setTranslateZ(float translateZ) {
//		this.translateZ = translateZ;
//	}
//	
//	public float getRotateX() {
//		return rotateX;
//	}
//	public void setRotateX(float rotateX) {
//		this.rotateX = rotateX;
//	}
//	
//	public float getRotateY() {
//		return rotateY;
//	}
//	public void setRotateY(float rotateY) {
//		this.rotateY = rotateY;
//	}
//	
//	public float getRotateZ() {
//		return rotateZ;
//	}
//	public void setRotateZ(float rotateZ) {
//		this.rotateZ = rotateZ;
//	}
//	
//	public float getRotateAngle() {
//		return rotateAngle;
//	}
//	public void setRotateAngle(float rotateAngle) {
//		this.rotateAngle = rotateAngle;
//	}
//	
//	public float getScaleX() {
//		return scaleX;
//	}
//	public void setScaleX(float scaleX) {
//		this.scaleX = scaleX;
//	}
//	
//	public float getScaleY() {
//		return scaleY;
//	}
//	public void setScaleY(float scaleY) {
//		this.scaleY = scaleY;
//	}
//	
//	public float getScaleZ() {
//		return scaleZ;
//	}
//	public void setScaleZ(float scaleZ) {
//		this.scaleZ = scaleZ;
//	}
	
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
}
