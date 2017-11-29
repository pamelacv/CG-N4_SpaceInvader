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
import java.util.Random;

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

	// define as variaveis
	protected GL gl;
	protected GLU glu;
	protected GLUT glut;
	protected GLAutoDrawable glDrawable;

	// constante qtd aliens
	private final int qtdAliens = 6;

	//
	private boolean finalJogo = false;
	private boolean ganhouJogo = false;

	// armazena um ponteiro para o frame principal
	protected Frame frame;

	private List<ObjetoGrafico> objetos = new ArrayList<>();
	// private ObjetoGrafico nave = new ObjetoGrafico();

	private int contadorGame = 0;
	private int contadorMovimento = 0;

	private int cAlien = 0;
	private int cAlienTempo = 0;
	private int contadorAlien = 0;
	
	private List<Tiro> tiros = new LinkedList<>();
	private List<Tiro> tirosA = new LinkedList<>();

	private int[][] matrixObjetosCena = { { 9, 9, 9, 9, 9, 9, 9, 9 }, { 9, 3, 3, 3, 3, 3, 3, 9 },
			{ 9, 3, 3, 3, 3, 3, 3, 9 }, { 9, 3, 3, 3, 3, 3, 3, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 },
			{ 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 },
			{ 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 }, { 9, 0, 0, 0, 0, 0, 0, 9 },
			{ 9, 0, 2, 0, 2, 0, 2, 9 }, { 9, 1, 0, 0, 0, 0, 0, 9 }, { 9, 9, 9, 9, 9, 9, 9, 9 } };

	// Original
	// private double xEye = 0.0;
	// private double yEye = 40.0;
	// private double zEye = 20.0;
	// private double xCenter = 0.0;
	// private double yCenter = 0.0;
	// private double zCenter = 0.0;

	// Teste 1
	// private double xEye = 3.25;
	// private double yEye = 5.75;
	// private double zEye = 9.0;
	// private double xCenter = 3.25;
	// private double yCenter = 0.0;
	// private double zCenter = 5.75;

	private double xEye = 4;
	private double yEye = 8;
	private double zEye = 15.0;
	private double xCenter = 4;
	private double yCenter = 0.0;
	private double zCenter = 5.75;

	// informa em que modo a camera esta
	protected int mode = PERSPECTIVE;

	protected int x = 0;
	protected int y = 0;
	// informa se a tela foi redimensionada
	protected boolean redim = false;

	Thread updateThread;
	Thread updateThread2;

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

	private void criaAliens(int matrixPosition, String objName, float positionZ) {
		for (int i = 1; i <= qtdAliens; i++) {
			ObjetoGrafico alien = new ObjetoGrafico();
			alien.setObjModelParser(new OBJModel(objName, 1.0f, gl, true));
			alien.setScale(new float[] { 1.0f, 1.0f, 1.0f });
			alien.setTranslate(new float[] { 0.5f + i, 0.5f, positionZ });
			alien.setMatrixPosition(new int[] { matrixPosition, i });
			objetos.add(alien);
		}
	}

	private void criaNave() {
		ObjetoGrafico nave = new ObjetoGrafico();
		// nave.setObjModelParser(new OBJModel("data/nave", 1.0f, gl, true));
		nave.setObjModelParser(new OBJModel("data/nave1", 1.0f, gl, true));
		nave.setScale(new float[] { 1.0f, 1.0f, 1.0f });
		nave.setTranslate(new float[] { 1.5f, 0.5f, 11.5f });
		nave.setMatrixPosition(new int[] { 12, 1 });
		objetos.add(nave);
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

		// carrega os objetos - cria os aliens
		float positionZ = 0.5f;
		int posAlien = 3;
		for (int i = 1; i <= 3; i++) {
			criaAliens(i, "data/alien" + posAlien, positionZ);
			posAlien--;
			positionZ++;
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

		// cria a nave
		criaNave();

		this.updateThread = new Thread(new Runnable() {
			public void run() {
				try {
					for (;;) {
						glDrawable.display();
						Thread.sleep(200L);
						if (ganhouJogo) {

						}

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
		}
	}

	public void display(GLAutoDrawable arg0) {
		// desenhaGrade();

		// O que falta:
		// 1- Atualiza quando o alien � morto
		// -ok

		// 2- Arrumar o tiro (est� se movendo com a nave e quando h� muitos tiros de uma
		// vez ele buga) - pra solucionar talvez fazer uma lista de tiros (fiz a lista,
		// mas n�o resolveu muito)
		// -ok

		// 2.1. Definir limite de tela. Obs.: Podemos colocar na matriz paredes com
		// numeros diferentes e quando chegar nas paredes
		// -ok

		// 3- Ajustar a camera
		// -ok

		// 3.1. Ajustar os aliens para ficarem em p�. Obs.: Olhar no blender ou
		// rotacionar o objeto
		// -ok

		// 4- Movimento dos aliens
		
		// 5- Cria��o dos blocos
		
		// 6- Tiro dos aliens
		
		// 7- Destru��o dos blocos

		// 8- Win: quando a nave elimina todos os aliens (n�o tiver mais 3 na matriz de
		// objetos) ganhou!!!!
		// 9- Game over: quando o alien atingir a nave com um tiro � game over

		if (finalJogo) {
			if (ganhouJogo) {
				System.out.println("Ganhou!");
			
				for (ObjetoGrafico obj : objetos) {
					if (obj.isVisible()) {
						obj.setVisible(false);
					}
				}

				// TROFEU
				ObjetoGrafico trofeu = new ObjetoGrafico();
				trofeu.setObjModelParser(new OBJModel("data/trofeu", 1.0f, gl, true));
				trofeu.setScale(new float[] { 1.0f, 1.0f, 1.0f });
				trofeu.setTranslate(new float[] { 4.5f, 1.5f, -0.5f });
				trofeu.setMatrixPosition(new int[] { 0, 1 });

				gl.glPushMatrix();

				gl.glScalef(trofeu.getScale()[0], trofeu.getScale()[1], trofeu.getScale()[2]);
				gl.glTranslated(trofeu.getTranslate()[0], trofeu.getTranslate()[1], trofeu.getTranslate()[2]);
				trofeu.getObjModelParser().draw(gl);

				gl.glPopMatrix();
				//

				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateThread.interrupt();
			} else {
				System.out.println("Perdeu!");
				
				for (ObjetoGrafico obj : objetos) {
					if (obj.isVisible()) {
						obj.setVisible(false);
					}
				}				
				
				//nave quebrada 1
				ObjetoGrafico naveQuebrada = new ObjetoGrafico();
				naveQuebrada.setObjModelParser(new OBJModel("data/naveQuebrada", 1.0f, gl, true));
				naveQuebrada.setScale(new float[] { 1.0f, 1.0f, 1.0f });
				naveQuebrada.setTranslate(new float[] { 2.5f, 0.5f, 11.5f });
				naveQuebrada.setMatrixPosition(new int[] { 12, 1 });

				gl.glPushMatrix();

				gl.glScalef(naveQuebrada.getScale()[0], naveQuebrada.getScale()[1], naveQuebrada.getScale()[2]);
				gl.glTranslated(naveQuebrada.getTranslate()[0], naveQuebrada.getTranslate()[1], naveQuebrada.getTranslate()[2]);
				naveQuebrada.getObjModelParser().draw(gl);

				gl.glPopMatrix();
				
				//nave quebrada 2
				ObjetoGrafico naveQuebrada2 = new ObjetoGrafico();
				naveQuebrada2.setObjModelParser(new OBJModel("data/naveQuebrada", 1.0f, gl, true));
				naveQuebrada2.setScale(new float[] { 1.0f, 1.0f, 1.0f });
				naveQuebrada2.setTranslate(new float[] { 4.5f, 0.5f, 11.5f });
				naveQuebrada2.setMatrixPosition(new int[] { 12, 1 });

				gl.glPushMatrix();

				gl.glScalef(naveQuebrada2.getScale()[0], naveQuebrada2.getScale()[1], naveQuebrada2.getScale()[2]);
				gl.glTranslated(naveQuebrada2.getTranslate()[0], naveQuebrada2.getTranslate()[1], naveQuebrada2.getTranslate()[2]);
				naveQuebrada2.getObjModelParser().draw(gl);

				gl.glPopMatrix();
				
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateThread.interrupt();				
			}
		}else {
			try {						

				gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

				gl.glMatrixMode(GL.GL_MODELVIEW);
				gl.glLoadIdentity();
				glu.gluLookAt(xEye, yEye, zEye, xCenter, yCenter, zCenter, 0.0f, 1.0f, 0.0f);
				
				contadorGame++;
				if (contadorGame % 20 == 0) {
					contadorMovimento++;
				}
				
				cAlien++;
				if (cAlien % 10 == 0) {
					cAlienTempo++;
				}
				
				if (cAlienTempo == 2) {
					//Tiro randomico dos Aliens - necess�rio alguns ajustes. Est� realizando apenas para a primeira fileira
					Random gerador = new Random();
					int numT = gerador.nextInt(7);
					
					ObjetoGrafico alien = objetos.get(objetos.size() - numT);
					
					if(alien.isVisible()) {
						ObjetoGrafico tiroAlien = new ObjetoGrafico();
						tiroAlien.setObjModelParser(new OBJModel("data/tiro", 0.5f, gl, true));
						tiroAlien.setScale(new float[] { 1.0f, 1.0f, 1.0f });

						tiroAlien.setTranslate(new float[] { alien.getTranslate()[0], alien.getTranslate()[1], alien.getTranslate()[2] + 2 });

						tiroAlien.setMatrixPosition(new int[] { alien.getMatrixPosition()[0], alien.getMatrixPosition()[1] });

						Tiro tiroA = new Tiro(tiroAlien, 3, 2);
						tirosA.add(tiroA);

						cAlien = 0;
						cAlienTempo = 0;
					}else {
						cAlien = 0;
						cAlienTempo = 0;
					}
				}		

				finalJogo = true;
				ganhouJogo = true;
				// Busca todos os objetos e imprime na tela
				for (ObjetoGrafico obj : objetos) {

					if (obj.isVisible()) {

						gl.glPushMatrix();

						gl.glScalef(obj.getScale()[0], obj.getScale()[1], obj.getScale()[2]);
						gl.glTranslated(obj.getTranslate()[0], obj.getTranslate()[1], obj.getTranslate()[2]);
						obj.getObjModelParser().draw(gl);

						gl.glPopMatrix();
					}

					// Valida se existe algum Alien na tela
					if (matrixObjetosCena[obj.getMatrixPosition()[0] - 1][obj.getMatrixPosition()[1]] == 3) {
						finalJogo = false;
						ganhouJogo = false;
					}				
				}		
				
				// Coloca nave na tela
//				gl.glPushMatrix();
//
//				gl.glScalef(nave.getScale()[0], nave.getScale()[1], nave.getScale()[2]);
//				gl.glTranslated(nave.getTranslate()[0], nave.getTranslate()[1], nave.getTranslate()[2]);
//				nave.getObjModelParser().draw(gl);

//				gl.glPopMatrix();
								
				
				if (tirosA.size() > 0) {
					for (Tiro tiro : tirosA) {
						if (matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0] - 1][tiro.getObjeto().getMatrixPosition()[1]] != 9) {

							tiro.setContadorTiros(tiro.getContadorTiros() - 1);
							tiro.setLinhaProgressoMatriz(tiro.getLinhaProgressoMatriz() + 1);	
						
							tiro.getObjeto().setMatrixPosition(new int[] { tiro.getLinhaProgressoMatriz(), tiro.getObjeto().getMatrixPosition()[1] });
							//System.out.println("posicao atualizada: " + tiro.getObjeto().getMatrixPosition()[0] + "-" + tiro.getObjeto().getMatrixPosition()[1]);

							if (matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0]][tiro.getObjeto() .getMatrixPosition()[1]] == 1) {
								System.out.println("2 - Acertou nave");
								System.out.println("posicao: " + tiro.getObjeto().getMatrixPosition()[0] + "-" + tiro.getObjeto().getMatrixPosition()[1]);
								tirosA.remove(tiro);

								matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0]][tiro.getObjeto().getMatrixPosition()[1]] = 0;

								for (ObjetoGrafico obj : objetos) {
									if (obj.getMatrixPosition()[0] == tiro.getObjeto().getMatrixPosition()[0] && 
										obj.getMatrixPosition()[1] == tiro.getObjeto().getMatrixPosition()[1]) {

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

				// Verifica��es do tiro
				if (tiros.size() > 0) {
					for (Tiro tiro : tiros) {
						if (matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0] - 1][tiro.getObjeto().getMatrixPosition()[1]] != 9) {

							tiro.setContadorTiros(tiro.getContadorTiros() + 1);
							tiro.setLinhaProgressoMatriz(tiro.getLinhaProgressoMatriz() - 1);	
						
							tiro.getObjeto().setMatrixPosition(new int[] { tiro.getLinhaProgressoMatriz(), tiro.getObjeto().getMatrixPosition()[1] });

							if (matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0]][tiro.getObjeto() .getMatrixPosition()[1]] == 3) {
								// remove tiro da LinkedList
								System.out.println("1 - Acertou alien");
								System.out.println("posicao: " + tiro.getObjeto().getMatrixPosition()[0] + "-" + tiro.getObjeto().getMatrixPosition()[1]);
								tiros.remove(tiro);

								matrixObjetosCena[tiro.getObjeto().getMatrixPosition()[0]][tiro.getObjeto().getMatrixPosition()[1]] = 0;

								for (ObjetoGrafico obj : objetos) {
									if (obj.getMatrixPosition()[0] == tiro.getObjeto().getMatrixPosition()[0] && 
										obj.getMatrixPosition()[1] == tiro.getObjeto().getMatrixPosition()[1]) {

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
				//System.out.println("Erro: " + ex.getMessage() + " - StackTrace: " + ex.getStackTrace());
				ex.printStackTrace();
			}			
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

		// Aqui v�o ficar os atalhos para mudar de c�mera, atirar, mover e etc..
		ObjetoGrafico nave = objetos.get(objetos.size() - 1);

		ObjetoGrafico alien2 = objetos.get(objetos.size() - 6);
		ObjetoGrafico alien1 = objetos.get(objetos.size() - 7);

		switch (e.getKeyCode()) {

		// IMPORTANTE: ESTABELECER LIMITE PARA O MOVIMENTO
		case KeyEvent.VK_RIGHT:			
			if (matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] + 1] != 9) {

				// atualiza a matriz de objetos com a nova posi��o da nave
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1]] = 0;
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] + 1] = 1;

				// seta nova posi��o da nave
				nave.getMatrixPosition()[1] = nave.getMatrixPosition()[1] + 1;
				nave.getTranslate()[0] = nave.getTranslate()[0] + 1;
			}
			break;

		case KeyEvent.VK_LEFT:

			if (matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] - 1] != 9) {

				// atualiza a matriz de objetos com a nova posi��o da nave
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1]] = 0;
				matrixObjetosCena[nave.getMatrixPosition()[0]][nave.getMatrixPosition()[1] - 1] = 1;

				// seta nova posi��o da nave
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

		case KeyEvent.VK_1:
			ObjetoGrafico tiroAlien1 = new ObjetoGrafico();
			tiroAlien1.setObjModelParser(new OBJModel("data/tiro", 0.5f, gl, true));
			tiroAlien1.setScale(new float[] { 1.0f, 1.0f, 1.0f });

			tiroAlien1.setTranslate(
					new float[] { alien1.getTranslate()[0], alien1.getTranslate()[1], alien1.getTranslate()[2] + 2 });

			tiroAlien1.setMatrixPosition(new int[] { alien1.getMatrixPosition()[0], alien1.getMatrixPosition()[1] });

			Tiro tiro1 = new Tiro(tiroAlien1, 3, 2);

			tirosA.add(tiro1);
			break;

		case KeyEvent.VK_2:
			ObjetoGrafico tiroAlien2 = new ObjetoGrafico();
			tiroAlien2.setObjModelParser(new OBJModel("data/tiro", 0.5f, gl, true));
			tiroAlien2.setScale(new float[] { 1.0f, 1.0f, 1.0f });

			tiroAlien2.setTranslate(
					new float[] { alien2.getTranslate()[0], alien2.getTranslate()[1], alien2.getTranslate()[2] + 2 });

			tiroAlien2.setMatrixPosition(new int[] { alien2.getMatrixPosition()[0], alien2.getMatrixPosition()[1] });

			Tiro tiro2 = new Tiro(tiroAlien2, 3, 2);

			tirosA.add(tiro2);
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
	 * Retorna o mode de visão da camera.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * Atribui o endereço do modelo a ser redesenhado na tela.
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
	 * Altera o tipo de interação. Entre transformações e visões.
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
	 * Busca que tipo de interação esta sendo utilizada no momento.
	 */
	// public int getType() {
	// return type;
	// }
}