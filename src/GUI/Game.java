package GUI;

import com.sun.opengl.util.GLUT;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import model.ObjetoGrafico;
import model.Tiro;
import object.OBJModel;

public class Game implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	// define as constantes
	public final int PERSPECTIVE = 0;

	private static Game game = null;

	// define as vari√°veis
	protected GL gl;
	protected GLU glu;
	protected GLUT glut;
	protected GLAutoDrawable glDrawable;

	// armazena um ponteiro para o frame principal
	protected Frame frame;

	private List<ObjetoGrafico> objetos = new ArrayList<>();
	// private ObjetoGrafico tiroInicial = new ObjetoGrafico();

	private List<Tiro> tiros = new LinkedList<>();

	private int[][] matrixObjetosCena = { { 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 3, 3, 3, 3, 3, 3, 9 },
			{ 9, 3, 3, 3, 3, 3, 3, 9 }, { 9, 3, 3, 3, 3, 3, 3, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 },
			{ 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 },
			{ 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 },
			{ 9, 0, 2, 0, 2, 0, 2, 9 }, { 9, 1, 0, 0, 0, 0, 0, 9 } };

	//Original
//	private double xEye = 0.0;
//	private double yEye = 40.0;
//	private double zEye = 20.0;
//	private double xCenter = 0.0;
//	private double yCenter = 0.0;
//	private double zCenter = 0.0;

	//Teste 1
//	 private double xEye = 3.25;
//	 private double yEye = 5.75;
//	 private double zEye = 9.0;
//	 private double xCenter = 3.25;
//	 private double yCenter = 0.0;
//	 private double zCenter = 5.75;
	
	 private double xEye = 3.25;
	 private double yEye = 5.75;
	 private double zEye = 15.0;
	 private double xCenter = 3.25;
	 private double yCenter = 0.0;
	 private double zCenter = 5.75;

	// informa em que modo a camera esta
	protected int mode = PERSPECTIVE;

	protected int x = 0;
	protected int y = 0;
	// informa se a tela foi redimensionada
	protected boolean redim = false;

	Thread updateThread;

	private Game(Frame frame) {
		this.frame = frame;
	}

	public static Game getInstance(Frame frame) {

		if (game == null) {
			game = new Game(frame);
		}
		return game;
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

		// carrega os objetos

		for (int i = 1; i <= 6; i++) {

			ObjetoGrafico alien3 = new ObjetoGrafico();
			alien3.setObjModelParser(new OBJModel("data/alien3", 1.0f, gl, true));
			alien3.setScale(new float[] { 1.0f, 1.0f, 1.0f });
			alien3.setTranslate(new float[] { 0.5f + i, 0.5f, 0.5f });
			alien3.setMatrixPosition(new int[] { 1, i });
			objetos.add(alien3);
		}

		for (int i = 1; i <= 6; i++) {

			ObjetoGrafico alien2 = new ObjetoGrafico();
			alien2.setObjModelParser(new OBJModel("data/alien2", 1.0f, gl, true));
			alien2.setScale(new float[] { 1.0f, 1.0f, 1.0f });
			alien2.setTranslate(new float[] { 0.5f + i, 0.5f, 1.5f });
			alien2.setMatrixPosition(new int[] { 2, i });
			objetos.add(alien2);
		}

		for (int i = 1; i <= 6; i++) {

			ObjetoGrafico alien1 = new ObjetoGrafico();
			alien1.setObjModelParser(new OBJModel("data/alien1", 1.0f, gl, true));
			alien1.setScale(new float[] { 1.0f, 1.0f, 1.0f });
			alien1.setTranslate(new float[] { 0.5f + i, 0.5f, 2.5f });
			alien1.setMatrixPosition(new int[] { 3, i });
			objetos.add(alien1);
		}

		// ObjetoGrafico bloco1 = new ObjetoGrafico();
		// bloco1.setObjModelParser(new OBJModel("data/untitled", 1.5f, gl, true));
		// bloco1.setScale(new float[] { 1.0f, 1.0f, 1.0f });
		// bloco1.setTranslate(new float[] { 1.5f, 0.5f, 3.5f });
		// bloco1.setMatrixPosition(new int[] {3,2});
		// objetos.add(bloco1);
		//
		// ObjetoGrafico bloco2 = new ObjetoGrafico();
		// bloco2.setObjModelParser(new OBJModel("data/untitled", 1.5f, gl, true));
		// bloco2.setScale(new float[] { 1.0f, 1.0f, 1.0f });
		// bloco2.setTranslate(new float[] { 3.5f, 0.5f, 3.5f });
		// bloco2.setMatrixPosition(new int[] {3,2});
		// objetos.add(bloco2);

		ObjetoGrafico nave = new ObjetoGrafico();
		nave.setObjModelParser(new OBJModel("data/nave", 1.0f, gl, true));
		nave.setScale(new float[] { 1.0f, 1.0f, 1.0f });
		nave.setTranslate(new float[] { 1.5f, 0.5f, 11.5f });
		nave.setMatrixPosition(new int[] { 12, 1 });
		objetos.add(nave);

		this.updateThread = new Thread(new Runnable() {
			public void run() {
				try {
					for (;;) {
						glDrawable.display();
						Thread.sleep(200L);
					}
				} catch (Exception localException) {
				}
			}
		});

		this.updateThread.start();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		if (gl != null) {
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glViewport(0, 0, width, height);
			glu.gluPerspective(60, width / height, 0.1, 100);

			// VisEdu
			// primeiro par‚metro: FOV
			// segundo par‚metro: largura da tela dividido pela altura
			// terceiro par‚metro: near
			// quarto par‚metro: far

			// gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, new double[16], 1);
			// gl.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
			// gl.glEnable(GL.GL_DEPTH_TEST);
			// gl.glEnable(GL.GL_LIGHTING);
			// gl.glEnable(GL.GL_LIGHT0);
		}
	}

	public void display(GLAutoDrawable arg0) {
		// desenhaGrade();

		// O que falta:
		// 1- Atualiza quando o alien È morto
		// -ok

		// 2- Arrumar o tiro (est· se movendo com a nave e quando h· muitos tiros de uma
		// vez ele buga) - pra solucionar talvez fazer uma lista de tiros (fiz a lista,
		// mas n„o resolveu muito)
		// -ok

		// 2.1. Definir limite de tela. Obs.: Podemos colocar na matriz paredes com
		// numeros diferentes e quando chegar nas paredes
		//-ok

		// 3- Ajustar a camera
		//-ok
		
		//3.1. Ajustar os aliens para ficarem em pÈ. Obs.: Olhar no blender ou rotacionar o objeto
		//-
		
		// 4- Movimento dos aliens
		// 5- CriaÁ„o dos blocos
		// 6- Tiro dos aliens
		// 7- DestruÁ„o dos blocos
		
		// 8- Win: quando a nave elimina todos os aliens (n„o tiver mais 3 na matriz de
		// objetos) ganhou!!!!
		// 9- Game over: quando o alien atingir a nave com um tiro È game over

		try {

			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
			glu.gluLookAt(xEye, yEye, zEye, xCenter, yCenter, zCenter, 0.0f, 1.0f, 0.0f);

			// Busca todos os objetos e imprime na tela
			for (ObjetoGrafico obj : objetos) {

				if (obj.isVisible()) {

					gl.glPushMatrix();

					gl.glScalef(obj.getScale()[0], obj.getScale()[1], obj.getScale()[2]);
					gl.glTranslated(obj.getTranslate()[0], obj.getTranslate()[1], obj.getTranslate()[2]);
					obj.getObjModelParser().draw(gl);

					gl.glPopMatrix();
				}
			}

			// VerificaÁıes do tiro
			if (tiros.size() > 0) {

				for (Tiro tiro : tiros) {
					
					if (matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0] - 1][tiro.getObjeto()
							.getMatrixPosition()[1]] != 9) {
						
						tiro.setContadorTiros(tiro.getContadorTiros() + 1);
						tiro.setLinhaProgressoMatriz(tiro.getLinhaProgressoMatriz() - 1);

						tiro.getObjeto().setMatrixPosition(
								new int[] { tiro.getLinhaProgressoMatriz(), tiro.getObjeto().getMatrixPosition()[1] });
						
						if (matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0]][tiro.getObjeto()
								.getMatrixPosition()[1]] == 3) {

							// remove tiro da LinkedList
							System.out.println("1 - Aqui");
							tiros.remove(tiro);

							matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0]][tiro.getObjeto()
									.getMatrixPosition()[1]] = 0;

							for (ObjetoGrafico obj : objetos) {

								if (obj.getMatrixPosition()[0] == tiro.getObjeto().getMatrixPosition()[0]
										&& obj.getMatrixPosition()[1] == tiro.getObjeto().getMatrixPosition()[1]) {

									obj.setVisible(false);
								}
							}
						} else {

							gl.glPushMatrix();

							tiro.getObjeto().setObjModelParser(new OBJModel("data/tiro", 0.5f, gl, true));

							gl.glTranslated(tiro.getObjeto().getTranslate()[0], tiro.getObjeto().getTranslate()[1],
									tiro.getObjeto().getTranslate()[2] - tiro.getContadorTiros());

							tiro.getObjeto().getObjModelParser().draw(gl);

							gl.glPopMatrix();

						}
					}
				}
			}

			gl.glFlush();

		} catch (Exception ex) {
			System.out.println("Erro: " + ex.getMessage() + " - StackTrace: " + ex.getStackTrace());
		}

	}

	private void desenhaGrade() {
		double xEye = 0.0;
		double yEye = 40.0;
		double zEye = 20.0;
		double xCenter = 0.0;
		double yCenter = 0.0;
		double zCenter = 0.0;

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(xEye, yEye, zEye, xCenter, yCenter, zCenter, 0.0f, 1.0f, 0.0f);

		// eixo X - Chao
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glEnd();

		// eixo X - Teto
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glVertex3f(10.0f, 10.0f, 0.0f);
		gl.glEnd();

		// eixo Y - Chao
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glEnd();

		// eixo Z - Chao
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 0.0f, 10.0f);
		gl.glEnd();

		// eixo Z - Teto
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glVertex3f(0.0f, 10.0f, 10.0f);
		gl.glEnd();

		for (float f = (float) 1; f <= 10.0; f++) // Linhas Chao Vertical
		{
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(f, 0.0f, 10.0f);
			gl.glVertex3f(f, 0.0f, 0.0f);
			gl.glEnd();
		}

		for (float f = (float) 1.0; f <= 10.0; f++) // Linhas Chao Horizontal
		{
			gl.glColor3f(0.0f, 1.0f, 0.0f);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, f);
			gl.glVertex3f(10.0f, 0.0f, f);
			gl.glEnd();
		}

		for (float f = (float) 1.0; f <= 10.0; f++) // Linhas Parede Esquerda - Vertical
		{
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, f);
			gl.glVertex3f(0.0f, 10.0f, f);
			gl.glEnd();
		}

		for (float f = (float) 1.0; f <= 10.0; f++) // Linhas Parede Esquerda - Horizontal
		{
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, f, 0.0f);
			gl.glVertex3f(0.0f, f, 10.0f);
			gl.glEnd();
		}

		for (float f = (float) 1.0; f <= 10.0; f++) // Linhas Parede Direita - Vertical
		{
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(f, 0.0f, 0.0f);
			gl.glVertex3f(f, 10.0f, 0.0f);
			gl.glEnd();
		}

		for (float f = (float) 1.0; f <= 10.0; f++) // Linhas Parede Direita - Vertical
		{
			gl.glColor3f(0.0f, 0.0f, 1.0f);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, f, 0.0f);
			gl.glVertex3f(10.0f, f, 0.0f);
			gl.glEnd();
		}
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		// Aqui v„o ficar os atalhos para mudar de c‚mera, atirar, mover e etc..

		ObjetoGrafico nave = objetos.get(objetos.size() - 1);

		switch (e.getKeyCode()) {

		// IMPORTANTE: ESTABELECER LIMITE PARA O MOVIMENTO
		case KeyEvent.VK_RIGHT:

			if (matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] + 1] != 9) {

				// atualiza a matriz de objetos com a nova posiÁ„o da nave
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1]] = 0;
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] + 1] = 1;

				// seta nova posiÁ„o da nave
				nave.getMatrixPosition()[1] = nave.getMatrixPosition()[1] + 1;
				nave.getTranslate()[0] = nave.getTranslate()[0] + 1;
			}
			break;

		case KeyEvent.VK_LEFT:

			if (matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] - 1] != 9) {

				// atualiza a matriz de objetos com a nova posiÁ„o da nave
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1]] = 0;
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] - 1] = 1;

				// seta nova posiÁ„o da nave
				nave.getMatrixPosition()[1] = nave.getMatrixPosition()[1] - 1;
				nave.getTranslate()[0] = nave.getTranslate()[0] - 1;
			}
			break;

		case KeyEvent.VK_SPACE:
			// Atirar

			ObjetoGrafico tiroInicial = new ObjetoGrafico();
			tiroInicial.setObjModelParser(new OBJModel("data/tiro", 0.5f, gl, true));
			tiroInicial.setScale(new float[] { 1.0f, 1.0f, 1.0f });

			tiroInicial.setTranslate(
					new float[] { nave.getTranslate()[0], nave.getTranslate()[1], nave.getTranslate()[2] });

			tiroInicial.setMatrixPosition(new int[] { nave.getMatrixPosition()[0], nave.getMatrixPosition()[1] });

			Tiro tiro = new Tiro(tiroInicial, 12, 1);

			tiros.add(tiro);
			break;
		}
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
	 * Retorna o mode de vis√£o da camera.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * Atribui o endere√ßo do modelo a ser redesenhado na tela.
	 */
	// public void setIndex(int index) {
	// indexOBJ = index;
	//
	// glDrawable.display();
	// }

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
	 * Altera o tipo de intera√ß√£o. Entre transforma√ß√µes e vis√µes.
	 */
	// public void changeType() {
	// if (type == PROJECTION)
	// type = TRANSFORMATION;
	// else
	// type = PROJECTION;
	//
	// glDrawable.display();
	// frame.getScreen().getGLDrawable().display();
	// // frame.getWorld().getGLDrawable().display();
	// }

	/**
	 * Busca que tipo de intera√ß√£o esta sendo utilizada no momento.
	 */
	// public int getType() {
	// return type;
	// }
}
