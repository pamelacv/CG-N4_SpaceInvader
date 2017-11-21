package GUI;

import java.awt.MenuItem;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import javax.swing.WindowConstants;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	private GLCanvas canvasScreen;

//	private int widthScreen = 1000;
//	private int heightScreen = 800;
	
	private int widthScreen = 800;
	private int heightScreen = 600;	

	public int getWidthScreen() {
		return widthScreen;
	}

	public int getHeightScreen() {
		return heightScreen;
	}

	public Frame() {

		super("Space Invaders");
		setBounds(50, 50, widthScreen, heightScreen);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);

		/*
		 * Cria um objeto GLCapabilities para especificar o número de bits por pixel
		 * para RGBA
		 */
		GLCapabilities c = new GLCapabilities();
		c.setRedBits(8);
		c.setBlueBits(8);
		c.setGreenBits(8);
		c.setAlphaBits(8);

		Game gameScreen = Game.getInstance(this);
		
		canvasScreen = new GLCanvas(c);
		canvasScreen.setBounds(0, 0, widthScreen, heightScreen);
		add(canvasScreen);
		
		canvasScreen.addGLEventListener(gameScreen);
		canvasScreen.addKeyListener(gameScreen);
		canvasScreen.addMouseListener(gameScreen);
		canvasScreen.addMouseMotionListener(gameScreen);
		canvasScreen.requestFocus();
	}

	public static void main(String[] args) {
		new Frame().setVisible(true);
	}
}
