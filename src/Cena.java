import Components.Textures;
import Components.Tools;
import Frames.*;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cena implements GLEventListener{
    public Cena(GLWindow window){this.window = window;}

    //Declaração de variaveis para janela
    GLU glu;
    GLUT glut;
    GL2 gl;
    Tools tools = new Tools();
    Textures textures = new Textures();

    GLWindow window;
    public float[] axisX = new float[2];
    public float[] axisY = new float[2];
    public float[] axisZ = new float[2];
    public int[] currentResolution = new int[2];
    public boolean fullscreen = false;
    public boolean mouseLock = false;

    //Instancia das frames
    Menu menu = new Menu(gl,tools); //Atualmente em testes
    Menu2D menu2D = new Menu2D(tools, textures);
    Menu3D menu3D = new Menu3D(tools, textures);
    Pong pong = new Pong(tools, textures);

    //Declaração de variaveis comuns para o projeto
    public int frame = 0;
    public int subFrame = 0;
    public boolean toggleMiniMenu = false;

    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        glut = new GLUT();
        gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);

        currentResolution[0] = window.getWidth();
        currentResolution[1] = window.getHeight();

        axisX[0] = currentResolution[0] *-0.5f;
        axisX[1] = currentResolution[0] *0.5f;
        axisY[0] = currentResolution[1] *-0.5f;
        axisY[1] = currentResolution[1] *0.5f;
        axisZ[0] = -1000;
        axisZ[1] = 1000;

        tools.init();
        tools.update(currentResolution,axisX,axisY,axisZ);

        textures.init(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        window.setFullscreen(fullscreen);
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();
        tools.resetPolygonMode(gl);
        textures.disable(gl);

        //Frames com os devidos objetos
        //Cada menu corresponde a uma dezena, possibilitando 9 submenus
        switch (frame){
            case 0:
                subFrame = 0;
                tools.axisControl[0] = 0;
                tools.axisControl[1] = 0;
                tools.axisControl[2] = 0;
                pong.gameState = 0;
                pong.gamePause = false;
                menu.run(gl);
                tools.cursor(gl);
                break;
            case 1:
                menu2D.run(gl);
                tools.cursor(gl);
                break;
            case 2: // frame correta, conteudo ainda em teste
                menu3D.run(gl, glut);
                tools.cursor(gl);
                break;
            case 3: //Frame correta: 31
                pong.run(gl, glut);
                break;
        }
        gl.glFlush();
        System.gc();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        currentResolution[0] = width;
        currentResolution[1] = height;

        // Calcula os novos valores do plano cartesiano para o tamanho atual da janela
        axisX[0] = currentResolution[0] * -0.5f;
        axisX[1] = currentResolution[0] * 0.5f;
        axisY[0] = currentResolution[1] * -0.5f;
        axisY[1] = currentResolution[1] * 0.5f;

        tools.update(currentResolution,axisX,axisY,axisZ);

        // Atualiza a matriz de projeção com os novos valores
        gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(axisX[0], axisX[1], axisY[0], axisY[1], axisZ[0], axisZ[1]);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
       
    @Override
    public void dispose(GLAutoDrawable drawable) {

    }
}

