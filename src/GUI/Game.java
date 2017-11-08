package GUI;

import com.sun.opengl.util.GLUT;
import java.awt.Component;
import java.awt.PopupMenu;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import model.Cell;
import object.OBJModel;

public class Game implements GLEventListener, KeyListener, MouseWheelListener, MouseListener, MouseMotionListener {
	// define as constantes
	public final int PERSPECTIVE = 0;
	public final int FRUSTUM = 1;
	public final int ORTHO = 2;
	public final int LOOKAT = 3;
	public final int TRANSFORMATION = 4;
	public final int PROJECTION = 5;
	public final int TRANSLATION = 6;
	public final int ROTATION = 7;
	public final int SCALE = 8;
	public final int LIGHT = 9;
	public final int GAP = 23;

	// define as variáveis
	protected GL gl;
	protected GLU glu;
	protected GLUT glut;
	protected GLAutoDrawable glDrawable;
	// informa em que modo a camera esta
	protected int mode = PERSPECTIVE;
	// informa se será utilizado transformaçoes ou visões
	protected int type = PROJECTION;

	// armazena um ponteiro para o frame principal
	protected Frame frame;
	// armazena um ponteiro para o popup deste componente
	protected PopupMenu popup;
	// armazena o modelo utilizado
	protected OBJModel[] loader = new OBJModel[7];
	// informa qual objeto esta sendo mostrado na tela
	protected int indexOBJ = 5;
	// informa as dimensões da tela
	// protected int heigth = 612;// 256
	// protected int width = 586;// 256
	protected int x = 0;
	protected int y = 0;
	// informa se a tela foi redimensionada
	protected boolean redim = false;

	// define os vetores de commando
	protected Cell[] lookat = {
			new Cell(1, 180, 120, -5.0f, 5.0f, 0.00f, 0.1f, "Specifies the X position of the eye point."),
			new Cell(2, 240, 120, -5.0f, 5.0f, 0.00f, 0.1f, "Specifies the Y position of the eye point."),
			new Cell(3, 300, 120, -5.0f, 5.0f, 2.00f, 0.1f, "Specifies the Z position of the eye point."),
			new Cell(4, 180, 160, -5.0f, 5.0f, 0.00f, 0.1f, "Specifies the X position of the reference point."),
			new Cell(5, 240, 160, -5.0f, 5.0f, 0.00f, 0.1f, "Specifies the Y position of the reference point."),
			new Cell(6, 300, 160, -5.0f, 5.0f, 0.00f, 0.1f, "Specifies the Z position of the reference point."),
			new Cell(7, 180, 200, -2.0f, 2.0f, 0.00f, 0.1f, "Specifies the X direction of the up vector."),
			new Cell(8, 240, 200, -2.0f, 2.0f, 1.00f, 0.1f, "Specifies the Y direction of the up vector."),
			new Cell(9, 300, 200, -2.0f, 2.0f, 0.00f, 0.1f, "Specifies the Z direction of the up vector.") };

	protected Cell[] perspective = {
			new Cell(10, 180, 80, 1.0f, 179.0f, 60.0f, 1.00f,
					"Specifies field of view angle (in degrees) in y direction."),
			new Cell(11, 240, 80, -3.0f, 3.0f, 2.00f, 0.01f, "Specifies field of view in x direction (width/height)."),
			new Cell(12, 300, 80, 0.1f, 10.0f, 1.00f, 0.05f, "Specifies distance from viewer to near clipping plane."),
			new Cell(13, 360, 80, 0.1f, 10.0f, 10.0f, 0.05f, "Specifies distance from viewer to far clipping plane.") };

	protected Cell[] frustum = {
			new Cell(14, 120, 80, -10.0f, 10.0f, -2.00f, 0.10f,
					"Specifies coordinate for left vertical clipping plane."),
			new Cell(15, 180, 80, -10.0f, 10.0f, 2.00f, 0.10f,
					"Specifies coordinate for right vertical clipping plane."),
			new Cell(16, 240, 80, -10.0f, 10.0f, -1.00f, 0.10f,
					"Specifies coordinate for bottom vertical clipping plane."),
			new Cell(17, 300, 80, -10.0f, 10.0f, 1.00f, 0.10f, "Specifies coordinate for top vertical clipping plane."),
			new Cell(18, 360, 80, 0.1f, 5.0f, 1.00f, 0.01f, "Specifies distance to near clipping plane."),
			new Cell(19, 420, 80, 0.1f, 5.0f, 3.50f, 0.01f, "Specifies distance to far clipping plane.") };

	protected Cell[] ortho = {
			new Cell(14, 120, 80, -10.0f, 10.0f, -2.00f, 0.10f,
					"Specifies coordinate for left vertical clipping plane."),
			new Cell(15, 180, 80, -10.0f, 10.0f, 2.00f, 0.10f,
					"Specifies coordinate for right vertical clipping plane."),
			new Cell(16, 240, 80, -10.0f, 10.0f, -1.00f, 0.10f,
					"Specifies coordinate for bottom vertical clipping plane."),
			new Cell(17, 300, 80, -10.0f, 10.0f, 1.00f, 0.10f, "Specifies coordinate for top vertical clipping plane."),
			new Cell(18, 360, 80, -5.0f, 5.0f, 1.00f, 0.01f, "Specifies distance to near clipping plane."),
			new Cell(19, 420, 80, 5.0f, 5.0f, 3.50f, 0.01f, "Specifies distance to far clipping plane.") };

	protected Cell[] translation = {
			new Cell(20, 120, 40, -5.0f, 5.0f, 0.00f, 0.01f, "Specifies X coordinate of translation vector."),
			new Cell(21, 180, 40, -5.0f, 5.0f, 0.00f, 0.01f, "Specifies Y coordinate of translation vector."),
			new Cell(22, 240, 40, -5.0f, 5.0f, 0.00f, 0.01f, "Specifies Z coordinate of translation vector.") };

	protected Cell[] rotation = {
			new Cell(23, 120, 80, -360.0f, 360.0f, 0.00f, 1.00f, "Specifies angle of rotation, in degrees."),
			new Cell(24, 180, 80, -1.0f, 1.0f, 0.00f, 0.01f, "Specifies X coordinate of vector to rotate about."),
			new Cell(25, 240, 80, -1.0f, 1.0f, 1.00f, 0.01f, "Specifies Y coordinate of vector to rotate about."),
			new Cell(26, 300, 80, -1.0f, 1.0f, 0.00f, 0.01f, "Specifies Z coordinate of vector to rotate about.") };

	protected Cell[] scale = {
			new Cell(27, 120, 120, -5.0f, 5.0f, 1.00f, 0.01f, "Specifies scale factor along X axis."),
			new Cell(28, 180, 120, -5.0f, 5.0f, 1.00f, 0.01f, "Specifies scale factor along Y axis."),
			new Cell(29, 240, 120, -5.0f, 5.0f, 1.00f, 0.01f, "Specifies scale factor along Z axis.") };

	protected Cell[] light = {
			new Cell(30, 180, 40, -5.0f, 5.0f, 1.5f, 0.1f, "Specifies X coordinate of light vector."),
			new Cell(31, 240, 40, -5.0f, 5.0f, 1.0f, 0.1f, "Specifies Y coordinate of light vector."),
			new Cell(32, 300, 40, -5.0f, 5.0f, 1.0f, 0.1f, "Specifies Z coordinate of light vector."),
			new Cell(33, 360, 40, 0.0f, 1.0f, 0.0f, 1.0f, "Specifies directional (0) or positional (1) light.") };

	public Game(Frame frame) {
		this.frame = frame;
	}

	/**
	 * Retorna o modelo.
	 */
	public OBJModel getModel() {
		return loader[indexOBJ];
	}

	public GL getGL() {
		return gl;
	}

	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glShadeModel(GL.GL_SMOOTH);

		//RETIRAR TODOS ESSES OBJETOS DAQUI
		// carrega os objetos
		loader[0] = new OBJModel("data/soccerball", 1.5f, gl, true);
		loader[1] = new OBJModel("data/al", 1.5f, gl, true);
		loader[2] = new OBJModel("data/f-16", 1.5f, gl, true);
		loader[3] = new OBJModel("data/dolphins", 1.5f, gl, true);
		loader[4] = new OBJModel("data/flowers", 1.5f, gl, true);
		loader[5] = new OBJModel("data/untitled", 1.5f, gl, true);
		loader[6] = new OBJModel("data/rose+vase", 1.5f, gl, true);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		if (gl != null) {
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glViewport(0, 0, width, height);
			glu.gluPerspective(60, width / height, 0.1, 100);

			// VisEdu
			// primeiro par�metro: FOV
			// segundo par�metro: largura da tela dividido pela altura
			// terceiro par�metro: near
			// quarto par�metro: far

			// gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, new double[16], 1);
			// gl.glMatrixMode(GL.GL_MODELVIEW);
			// gl.glLoadIdentity();
			//
			// // atualiza visão da camera
			// glu.gluLookAt(lookat[0].getValue(), lookat[1].getValue(),
			// lookat[2].getValue(), lookat[3].getValue(),
			// lookat[4].getValue(), lookat[5].getValue(), lookat[6].getValue(),
			// lookat[7].getValue(),
			// lookat[8].getValue());
			//
			// gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, new double[16], 1);
			// gl.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
			// gl.glEnable(GL.GL_DEPTH_TEST);
			// gl.glEnable(GL.GL_LIGHTING);
			// gl.glEnable(GL.GL_LIGHT0);
		}
	}

	public void display(GLAutoDrawable arg0) {
		Cell aux[] = perspective, look[] = lookat, trans[] = translation, rot[] = rotation, sca[] = scale;

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		glu.gluLookAt(0, 5, 2, 0, 0, 0, 0, 1, 0);

		int c = -2;
		for (int i = 0; i < 3; i++) {
			gl.glTranslatef(trans[0].getValue() + c, trans[1].getValue(), trans[2].getValue());
			gl.glRotatef(rot[0].getValue(), rot[1].getValue(), rot[2].getValue(), rot[3].getValue());
			gl.glScalef(sca[0].getValue(), sca[1].getValue(), sca[2].getValue());
			c = 2;
			loader[indexOBJ].draw(gl);

			gl.glFlush();
		}
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		// Aqui v�o ficar os atalhos para mudar de c�mera, atirar, mover e etc..
	}

	public void keyReleased(KeyEvent e) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void mouseEntered(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void mouseExited(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void mouseDragged(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	public void mouseMoved(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Retorna glDrawable.
	 */
	public GLAutoDrawable getGLDrawable() {
		return glDrawable;
	}

	/**
	 * Retorna o mode de visão da camera.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * Atribui o endereço do modelo a ser redesenhado na tela.
	 */
	public void setIndex(int index) {
		indexOBJ = index;

		glDrawable.display();
	}

	/**
	 * Ativa display dos canvas.
	 * 
	 * public void activeDisplay() { displayAll = true;
	 * 
	 * glDrawable.display(); }
	 */

	/**
	 * Atribui mensagem de redimensionamento da tela.
	 */
	public void redimensionar(boolean first) {
		if (!first) {
			redim = true;

			reshape(glDrawable, x, y, frame.getWidthScreen(), frame.getHeightScreen());
			if (glDrawable != null)
				glDrawable.display();
		}
	}

	/**
	 * Altera o tipo de interação. Entre transformações e visões.
	 */
	public void changeType() {
		if (type == PROJECTION)
			type = TRANSFORMATION;
		else
			type = PROJECTION;

		glDrawable.display();
		frame.getScreen().getGLDrawable().display();
		// frame.getWorld().getGLDrawable().display();
	}

	/**
	 * Busca que tipo de interação esta sendo utilizada no momento.
	 */
	public int getType() {
		return type;
	}
}
