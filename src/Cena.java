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

    GLWindow window;
    public float[] axisX = new float[2];
    public float[] axisY = new float[2];
    public float[] axisZ = new float[2];
    public int[] currentResolution = new int[2];
    public boolean fullscreen = false;
    public boolean mouseLock = false;

    //Instancia das frames
    Menu menu = new Menu(gl,tools);

    //Declaração de variaveis comuns para o projeto
    public int frame = 0;
    public int subFrame = 0;
    public boolean toggleMiniMenu = false;

    //Declaração de variaveis para o frame de Exercicios2D
    public int[] e2DTitleTextPosition = new int[] {-180,140};
    public float[] e2DTitleTextColour = new float[] {0.1f, 0.8f,0.2f,1f},
            e2DTitleOutLineTextColour = new float[] {1,1,1,0.1f};
    public int e2DTitleFontSize = 50,
            e2DTitleOutLineSize = 3;
    public String e2DTitleText = "Exercicios 2D";

    //Declaração de variaveis para o frame e Exercicios3D
    public int[] e3DTitleTextPosition = new int[] {-180,140};
    public float[] e3DTitleTextColour = new float[] {0.9f, 0.4f,0.8f,1f},
            e3DTitleOutLineTextColour = new float[] {1,1,1,0.1f};
    public int e3DTitleFontSize = 50,
            e3DTitleOutLineSize = 3;
    public String e3DTitleText = "Exercicios 3D";

    public float zoom = 1;

    //Declaração de variaveis para frames de Simulação
    public boolean gameDecoration = false;
    public boolean gameBarAnimation = true;
    public float gameBarY = -150f;
    public float gameAnimationY = 0;
    public float[] gameDotPoints = new float[] {0,0};
    public float[] gameDotAceleration = new float[]{0,0};
    public boolean gameDotMovingX = true;
    public boolean gameDotMovingY = true;


    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        glut = new GLUT();

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
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        window.setFullscreen(fullscreen);

        gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();
        tools.resetPolygonMode(gl);

        //Objects
        switch (frame){
            case 0:
                subFrame = 0;
                tools.axisControl[0] = 0;
                tools.axisControl[1] = 0;
                tools.axisControl[2] = 0;
                menu.run(gl);
                tools.cursor(gl);
                break;
            case 1:
                //exercicios2D(gl);
                tools.cursor(gl);
                break;
            case 2:
                //exercicios3D(gl);
                gl.glPushMatrix();
                tools.manipulator3D(gl);
                gl.glColor3f(0.9f, 0.4f,0.8f);
                //gl.glScaled(100,100,100);
                gl.glPolygonMode(GL2.GL_FRONT,GL2.GL_LINE);
                //glut.glutSolidDodecahedron();
                glut.glutSolidCube(100);
                gl.glPopMatrix();
                tools.resetManipulator3D(gl);
                tools.resetPolygonMode(gl);
                tools.cursor(gl);
                break;
            case 3:
                //gameAnimated(gl);
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

    /*

    public void menu(GL2 gl){
        gl.glPushMatrix();

        renderText(menuTitleText, menuTitleTextPosition, menuTitleTextColour, menuTitleFontSize,menuTitleOutLineSize,menuTitleOutLineTextColour);

        if(selectMenuButton1){
            renderText(menuButton1Text, menuButton1TextPosition, menuButtonsColour, menuButtonsFontSize, menuButtonsOutLineSize,menuButtonsOutLineColour);
            gl.glColor3f(menuButtonsOutLineColour[0],menuButtonsOutLineColour[1],menuButtonsOutLineColour[2]);
        }
        else{
            renderText(menuButton1Text, menuButton1TextPosition, menuButtonsColour, menuButtonsFontSize);
            gl.glColor3f(menuButtonsColour[0],menuButtonsColour[1],menuButtonsColour[2]);
        }
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton1[0], this.menuButton1[2]);
            gl.glVertex2f(this.menuButton1[1], this.menuButton1[2]);
            gl.glVertex2f(this.menuButton1[1], this.menuButton1[3]);
            gl.glVertex2f(this.menuButton1[0], this.menuButton1[3]);
        gl.glEnd();

        if(selectMenuButton2){
            renderText(menuButton2Text, menuButton2TextPosition, menuButtonsColour, menuButtonsFontSize, menuButtonsOutLineSize,menuButtonsOutLineColour);
            gl.glColor3f(menuButtonsOutLineColour[0],menuButtonsOutLineColour[1],menuButtonsOutLineColour[2]);
        }
        else{
            renderText(menuButton2Text, menuButton2TextPosition, menuButtonsColour, menuButtonsFontSize);
            gl.glColor3f(menuButtonsColour[0],menuButtonsColour[1],menuButtonsColour[2]);
        }
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton2[0], this.menuButton2[2]);
            gl.glVertex2f(this.menuButton2[1], this.menuButton2[2]);
            gl.glVertex2f(this.menuButton2[1], this.menuButton2[3]);
            gl.glVertex2f(this.menuButton2[0], this.menuButton2[3]);
        gl.glEnd();

        if(selectMenuButton3){
            renderText(menuButton3Text, menuButton3TextPosition, menuButtonsColour, menuButtonsFontSize, menuButtonsOutLineSize,menuButtonsOutLineColour);
            gl.glColor3f(menuButtonsOutLineColour[0],menuButtonsOutLineColour[1],menuButtonsOutLineColour[2]);
        }
        else{
            renderText(menuButton3Text, menuButton3TextPosition, menuButtonsColour, menuButtonsFontSize);
            gl.glColor3f(menuButtonsColour[0],menuButtonsColour[1],menuButtonsColour[2]);
        }
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton3[0], this.menuButton3[2]);
            gl.glVertex2f(this.menuButton3[1], this.menuButton3[2]);
            gl.glVertex2f(this.menuButton3[1], this.menuButton3[3]);
            gl.glVertex2f(this.menuButton3[0], this.menuButton3[3]);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public void exercicios2D(GL2 gl){
        renderText(e2DTitleText, e2DTitleTextPosition, e2DTitleTextColour, e2DTitleFontSize,e2DTitleOutLineSize,e2DTitleOutLineTextColour);

    }

    public void exercicios3D(GL2 gl){
        renderText(e3DTitleText, e3DTitleTextPosition, e3DTitleTextColour, e3DTitleFontSize,e3DTitleOutLineSize,e3DTitleOutLineTextColour);

        gl.glPushMatrix();

        gl.glPopMatrix();
    }

    public void gameAnimated(GL2 gl){

        // Logica Colisão do ponto no eixo X
        if(gameDotMovingX){
            if(gameDotPoints[0]< axisX[1]){
                gameDotPoints[0]+=5f;
            }
            else {
                gameDotMovingX = false;
            }
        }
        else {
            if (gameDotPoints[0] > axisX[0]) {
                gameDotPoints[0] -= 5f;
            } else {
                gameDotMovingX = true;
            }
        }

        // Logica Colisão do ponto no eixo Y
        if(gameDotMovingY){
            if(gameDotPoints[1]< axisY[1]){
                gameDotPoints[1]+=5f;
            }
            else {
                gameDotMovingY = false;
            }
        }
        else{
            if (gameDotPoints[1]> axisY[0] && !((gameDotPoints[1] >= gameBarY-5) && (gameDotPoints[1] <= gameBarY) && gameDotPoints[0]>=cursorX-50 &&  gameDotPoints[0]<= cursorX+50)){
                gameDotPoints[1]-=5f;
            }
            else {
                //apply damage -> if(gameDotPoints[1]<=yMin){}
                gameDotMovingY = true;
            }
        }

        if(!gameDecoration){
            //Renderização do jogo
            gl.glPushMatrix();

            //barra
            gl.glColor3f(1,1,1);
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2f(cursorX-50, gameBarY);
                gl.glVertex2f(cursorX+50 , gameBarY);
                gl.glVertex2f(cursorX+50, gameBarY-5);
                gl.glVertex2f(cursorX-50, gameBarY-5);
            gl.glEnd();

            //ponto
            gl.glColor3f(1,1,1);
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(gameDotPoints[0]-2, gameDotPoints[1]+2);
            gl.glVertex2f(gameDotPoints[0]+2, gameDotPoints[1]+2);
            gl.glVertex2f(gameDotPoints[0]+2, gameDotPoints[1]-2);
            gl.glVertex2f(gameDotPoints[0]-2, gameDotPoints[1]-2);
            gl.glEnd();

            gl.glPopMatrix();
        }
        else{
            //Logica animação do personagem
            if(gameBarAnimation){
                gameAnimationY += 0.2f;
                if(gameAnimationY>= 4f){
                    gameBarAnimation =false;}
            }
            else{
                gameAnimationY -= 0.2f;
                if(gameAnimationY<=0){
                    gameBarAnimation =true;}
            }

            //Renderização do jogo
            gl.glPushMatrix();

            //barra
            gl.glColor3f(1,1,1);
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2f(cursorX-50, gameBarY);
                gl.glVertex2f(cursorX+50 , gameBarY);
                gl.glVertex2f(cursorX+50, gameBarY-5);
                gl.glVertex2f(cursorX-50, gameBarY-5);
            gl.glEnd();

            //mão esquerda
            gl.glColor3f(1,0,0);
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(cursorX-40, gameBarY-5);
                gl.glVertex2f(cursorX-30, gameBarY-5);
                gl.glVertex2f(cursorX-30, gameBarY-15);
                gl.glVertex2f(cursorX-40, gameBarY-15);
            gl.glEnd();

            //mão direita
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(cursorX+40, gameBarY-5);
                gl.glVertex2f(cursorX+30, gameBarY-5);
                gl.glVertex2f(cursorX+30, gameBarY-15);
                gl.glVertex2f(cursorX+40, gameBarY-15);
            gl.glEnd();

            //visor
            gl.glColor3f(1,1,1);
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(cursorX-25, gameBarY-25);
                gl.glVertex2f(cursorX, gameBarY-25);
                gl.glVertex2f(cursorX, gameBarY-40);
                gl.glVertex2f(cursorX-25, gameBarY-40);
            gl.glEnd();

            //corpo
            gl.glColor3f(1,0,0);
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(cursorX-20, gameBarY-15);
                gl.glVertex2f(cursorX+20, gameBarY-15);
                gl.glVertex2f(cursorX+20, gameBarY-55);
                gl.glVertex2f(cursorX-20, gameBarY-55);
            gl.glEnd();

            //pé esquerdo
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(cursorX-10, gameBarY-55+gameAnimationY);
                gl.glVertex2f(cursorX-20, gameBarY-55+gameAnimationY);
                gl.glVertex2f(cursorX-20, gameBarY-65+gameAnimationY);
                gl.glVertex2f(cursorX-10, gameBarY-65+gameAnimationY);
            gl.glEnd();

            //pé direito
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(cursorX+10, gameBarY-55-gameAnimationY+4);
                gl.glVertex2f(cursorX+20, gameBarY-55-gameAnimationY+4);
                gl.glVertex2f(cursorX+20, gameBarY-65-gameAnimationY+4);
                gl.glVertex2f(cursorX+10, gameBarY-65-gameAnimationY+4);
            gl.glEnd();

            //mochila
            gl.glColor3f(0.5f,0,0);
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(cursorX+20, gameBarY-25);
                gl.glVertex2f(cursorX+30, gameBarY-25);
                gl.glVertex2f(cursorX+30, gameBarY-45);
                gl.glVertex2f(cursorX+20, gameBarY-45);
            gl.glEnd();
            gl.glPopMatrix();

            //ponto - meteorito
            gl.glPushMatrix();
            gl.glColor3f(1,1,1);
            gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(gameDotPoints[0]-2, gameDotPoints[1]+2);
                gl.glVertex2f(gameDotPoints[0]+2, gameDotPoints[1]+2);
                gl.glVertex2f(gameDotPoints[0]+2, gameDotPoints[1]-2);
                gl.glVertex2f(gameDotPoints[0]-2, gameDotPoints[1]-2);
            gl.glEnd();
            gl.glPopMatrix();
        }
    }
    */



}
