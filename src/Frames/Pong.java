package Frames;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import Components.*;

public class Pong {
    Tools tools;
    Textures textures;
    
    public boolean gamePause = false;
    public boolean gameWin = true;


    public boolean gameBarAnimation = true;
    public float gameBarY = -250f;
    public float gameAnimationY = 0;
    public float[] gameDotPoints = new float[] {0,0};
    public float gameDotRotation = 0f;
    public boolean gameDotMovingX = true;
    public boolean gameDotMovingY = true;

    public int gameState = 0;
    public int playerHP = 5;
    public int playerScore = 0;
    public final int NextStageScore = 2000;
    public int UFOHP = 5;


    private Tittle rulesTitle = new Tittle(
            new int[] {130, 350},
            new float[] {0,0,0,1f},
            new float[] {1,1,1,0.2f},
            100,
            3,
            "Game Rules"
    );

    private Tittle pauseText = new Tittle(
            new int[] {-160,150},
            new float[] {0,0,0,1f},
            new float[] {1,1,1,0.2f},
            100,
            3,
            "Paused"
            );


    public Pong(Tools tools, Textures textures){
        this.tools = tools;
        this.textures = textures;
    }

    public void run(GL2 gl, GLUT glut) {
        switch (gameState){
            case 0: // Game Start
                start(gl);
                break;
            case 1: // Game lvl 1
                tools.lightOn(gl);

                character(gl);
                meteor(gl, glut);
                spaceship(gl);
                background(gl);
                if(gamePause){
                    gamePaused();
                }
                else{
                    gameRunning(gl);
                }
                tools.lightOff(gl);
                break;
            case 2: // Game lvl 2

                tools.lightOn(gl);
                //implementar logica diferente.
                UFO(gl);
                character(gl);
                meteor(gl, glut);
                spaceship(gl);
                background(gl);
                if(gamePause){
                    gamePaused();
                }
                else{
                    gameRunning(gl);
                }
                tools.lightOff(gl);
                break;
            case 3: // End Game
                if(gameWin){
                    win(gl);
                }
                else{
                    loose(gl);
                }
                break;
        }
    }


    private void gameAnimator(GL2 gl){
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
        gameDotRotation += 1f;
    }

    private void gameCollision(GL2 gl){
        // Logica Colisão do ponto no eixo X
        if(gameDotMovingX){
            if(gameDotPoints[0]< tools.axisX[1]){
                gameDotPoints[0]+=5f;
            }
            else {
                gameDotMovingX = false;
            }
        }
        else {
            if (gameDotPoints[0] > tools.axisX[0]) {
                gameDotPoints[0] -= 5f;
            } else {
                gameDotMovingX = true;
            }
        }

        // Logica Colisão do ponto no eixo Y
        if(gameDotMovingY){
            if(gameDotPoints[1]< tools.axisY[1]){
                gameDotPoints[1]+=5f;
            }
            else {
                gameDotMovingY = false;
            }
        }
        else{
            if (gameDotPoints[1]> tools.axisY[0] && !((gameDotPoints[1] >= gameBarY-10) && (gameDotPoints[1] <= gameBarY) && gameDotPoints[0]>=tools.cursorX-50 &&  gameDotPoints[0]<= tools.cursorX+50)){
                gameDotPoints[1]-=5f;
            }
            else {
                //apply damage -> if(gameDotPoints[1]<=yMin){}
                gameDotMovingY = true;

            }
        }
    }

    private void gameRunning(GL2 gl){
        gameAnimator(gl);
        gameCollision(gl);
    }

    private void gamePaused(){
        tools.renderText(pauseText.text, pauseText.textPosition, pauseText.colour, pauseText.fontSize, pauseText.fontOutLineSize, pauseText.outLineColour);
    }

    public void background(GL2 gl){
        textures.applySpriteQuad(gl,1,
                tools.axisX[0],tools.axisY[1],
                tools.axisX[1],tools.axisY[1],
                tools.axisX[1],tools.axisY[0],
                tools.axisX[0],tools.axisY[0]
                );
    }

    public void meteor(GL2 gl, GLUT glut) {
        gl.glPushMatrix();
            gl.glColor3f(1,1,1);
            gl.glTranslatef(gameDotPoints[0], gameDotPoints[1], 0);
            gl.glPushMatrix();
                gl.glRotatef(gameDotRotation, 1,1,0);
                gl.glPushMatrix();
                gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
                gl.glShadeModel(GL2.GL_SMOOTH);
                    gl.glScalef(20,20,20);
                    glut.glutSolidDodecahedron();
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();
        tools.resetPolygonMode(gl);
    }

    public void character(GL2 gl){
        gl.glPushMatrix();
        //barra
        gl.glColor3f(1,1,1);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(tools.cursorX-50, gameBarY);
        gl.glVertex2f(tools.cursorX+50 , gameBarY);
        gl.glVertex2f(tools.cursorX+50, gameBarY-5);
        gl.glVertex2f(tools.cursorX-50, gameBarY-5);
        gl.glEnd();

        //mão esquerda
        gl.glColor3f(1,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-40, gameBarY-5);
        gl.glVertex2f(tools.cursorX-30, gameBarY-5);
        gl.glVertex2f(tools.cursorX-30, gameBarY-15);
        gl.glVertex2f(tools.cursorX-40, gameBarY-15);
        gl.glEnd();

        //mão direita
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX+40, gameBarY-5);
        gl.glVertex2f(tools.cursorX+30, gameBarY-5);
        gl.glVertex2f(tools.cursorX+30, gameBarY-15);
        gl.glVertex2f(tools.cursorX+40, gameBarY-15);
        gl.glEnd();

        //visor
        gl.glColor3f(1,1,1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-25, gameBarY-25);
        gl.glVertex2f(tools.cursorX, gameBarY-25);
        gl.glVertex2f(tools.cursorX, gameBarY-40);
        gl.glVertex2f(tools.cursorX-25, gameBarY-40);
        gl.glEnd();

        //corpo
        gl.glColor3f(1,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-20, gameBarY-15);
        gl.glVertex2f(tools.cursorX+20, gameBarY-15);
        gl.glVertex2f(tools.cursorX+20, gameBarY-55);
        gl.glVertex2f(tools.cursorX-20, gameBarY-55);
        gl.glEnd();

        //pé esquerdo
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-10, gameBarY-55+gameAnimationY);
        gl.glVertex2f(tools.cursorX-20, gameBarY-55+gameAnimationY);
        gl.glVertex2f(tools.cursorX-20, gameBarY-65+gameAnimationY);
        gl.glVertex2f(tools.cursorX-10, gameBarY-65+gameAnimationY);
        gl.glEnd();

        //pé direito
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX+10, gameBarY-55-gameAnimationY+4);
        gl.glVertex2f(tools.cursorX+20, gameBarY-55-gameAnimationY+4);
        gl.glVertex2f(tools.cursorX+20, gameBarY-65-gameAnimationY+4);
        gl.glVertex2f(tools.cursorX+10, gameBarY-65-gameAnimationY+4);
        gl.glEnd();

        //mochila
        gl.glColor3f(0.5f,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX+20, gameBarY-25);
        gl.glVertex2f(tools.cursorX+30, gameBarY-25);
        gl.glVertex2f(tools.cursorX+30, gameBarY-45);
        gl.glVertex2f(tools.cursorX+20, gameBarY-45);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public void spaceship(GL2 gl){
        //Corpo com sprite
        textures.applySpriteQuad(gl,2,
                tools.axisX[0], gameBarY-50,
                tools.axisX[1], gameBarY-50,
                tools.axisX[1], tools.axisY[0],
                tools.axisX[0], tools.axisY[0]);

        //sombra barbatana vertical
        gl.glColor3f(0.2f, 0, 0);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(tools.axisX[1], gameBarY-50); //ponto dir inferior
        gl.glVertex2f(tools.axisX[1]-180, gameBarY-50); //ponto esq inferior
        gl.glVertex2f(tools.axisX[1], gameBarY+60); //ponto superior
        gl.glEnd();

        //barbatana vertical
        gl.glColor3f(0.7f, 0, 0);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(tools.axisX[1], gameBarY-50);
        gl.glVertex2f(tools.axisX[1]-200, gameBarY-50);
        gl.glVertex2f(tools.axisX[1], gameBarY+50);
        gl.glEnd();
    }

    public void UFO(GL2 gl){

    }

    public void win(GL2 gl){
        textures.applySpriteQuad(gl, 4,
                tools.axisX[0]+50, tools.axisY[1]-209,
                tools.axisX[0]+800, tools.axisY[1]-209,
                tools.axisX[0]+800, tools.axisY[0]+209,
                tools.axisX[0]+50, tools.axisY[0]+209);
    }

    public void loose(GL2 gl){

    }

    public void start(GL2 gl){
        this.playerHP = 5;
        this.playerScore = 0;
        this.UFOHP = 5;

        textures.applySpriteQuad(gl,0,
                tools.axisX[0]+50, tools.axisY[1]-90,
                tools.axisX[0]+800, tools.axisY[1]-90,
                tools.axisX[0]+800, tools.axisY[0]+90,
                tools.axisX[0]+50, tools.axisY[0]+90);

        tools.renderText(rulesTitle.text, rulesTitle.textPosition, rulesTitle.colour, rulesTitle.fontSize, rulesTitle.fontOutLineSize, rulesTitle.outLineColour);

    }
}

