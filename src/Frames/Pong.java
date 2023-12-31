package Frames;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import Components.*;

public class Pong {
    Tools tools;
    Textures textures;
    
    public boolean gamePause = false;
    public boolean gameWin = false;

    public boolean gameBarAnimation = true;
    public float gameBarY = -250f;
    public float gameAnimationY = 0;

    public float[] meteorDotPoints = new float[] {0,0};
    public float gameDotRotation = 0f;
    public boolean meteorDotMovingX = true;
    public boolean meteorDotMovingY = true;

    public int gameState = 0;
    public int playerHP = 5;
    public int playerScore = 0;
    public final int NextStageScore = 1000;
    private final int[] scoreTextPosition = new int[]{0,400};

    public int UFOHP = 5;
    public float[] UFODotPoints = new float[] {0,400};
    private boolean UFODotMovingX = true;
    private boolean UFODotMovingY = true;

    private float meteorSpeed;
    private float meteorSize;

    private Tittle rulesTitle = new Tittle(
            new int[] {130, 350},
            new float[] {0,0,0,1f},
            new float[] {1,1,1,0.2f},
            100,
            3,
            "Game Rules"
    );

    private String[] rulesText = new String[]{
            "* Protect your spaceship from the asteroid.",
            "* Your spaceship can endure 5 asteroid collisions.",
            "* Every time you protect the spaceship, you earn 100 points.",
            "* Upon reaching 1000 points, you will level up to the next stage.",

            "Controls:",
            "- Use the mouse to move the character.",
            "- Press the ' key to return to the menu.",
            "- Press the space key to pause.",
            "- Press 'ESC' to close the entire program.",

            "Press the space key to start the game!"
    };

    private Tittle winText = new Tittle(
            new int[] {-160,150},
            new float[] {1f,1f,1f,1f},
            new float[] {0f,1f,0f,0.2f},
            100,
            3,
            "Yeaaaah you win!"
    );

    private Tittle looseText = new Tittle(
            new int[] {-160,150},
            new float[] {1,1,1,1f},
            new float[] {1,0,0,0.2f},
            100,
            3,
            "Failed"
    );

    private String[] endText = new String[]{
            "Your score:",
            "Press the space key to continue"
    };

    private final float[] scoreTextColor = new float[]{0.96f, 0.77f, 0.25f,1f};
    private final int[] scoreEndTextPosition = new int[]{-110,90};
    private final float[] endTextColor = new float[]{1f, 1f, 1f,1f};
    private final int[] endTextPosition = new int[]{-110,-300};

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
                meteorSize = 20f;
                meteorSpeed = 5f;
                HP(gl);
                character(gl);
                tools.lightOn(gl);
                meteor(gl, glut);
                tools.lightOff(gl);
                spaceship(gl);
                background(gl);
                if(playerScore>=NextStageScore){
                    gameState = 2;
                }
                if(gamePause){gamePaused();}
                else{gameRunning(gl);}
                if(playerHP<1){
                    gameWin = false;
                    gameState = 3;
                }
                showScore();
                break;
            case 2: // Game lvl 2
                meteorSize = 10f;
                meteorSpeed = 10f;
                UFO(gl);
                HP(gl);
                showScore();
                character(gl);
                tools.lightOn(gl);
                meteor(gl, glut);
                tools.lightOff(gl);
                spaceship(gl);
                background(gl);
                if(gamePause){gamePaused();}
                else{gameRunning(gl);}
                if(playerHP<1){
                    gameWin = false;
                    gameState = 3;
                }
                if(UFOHP<0){
                    gameWin = true;
                    gameState = 3;
                }
                showScore();
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

    private void test(GL2 gl, float x1, float y1,float x2, float y2, float x3, float y3, float x4 ,float y4){
        gl.glPushMatrix();
        gl.glColor4f(0,1,1,0.68f);
        gl.glBegin(gl.GL_LINE_LOOP);
        gl.glVertex2f(x1,y1);
        gl.glVertex2f(x2,y2);
        gl.glVertex2f(x3,y3);
        gl.glVertex2f(x4,y4);
        gl.glEnd();
        gl.glPopMatrix();
    }

    private void showScore(){
        scoreTextPosition[0] = Math.round(tools.axisX[0]);
        tools.renderText(endText[0]+playerScore, scoreTextPosition,scoreTextColor ,30);
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

        if(UFODotMovingX){
            if(UFODotPoints[0]>=500){
                UFODotMovingX = false;
            }
            else{
                UFODotPoints[0]+=5f;
            }
        }
        else{
            if(UFODotPoints[0]<=-500){
                UFODotMovingX = true;
            }
            else{
                UFODotPoints[0]-=5f;
            }
        }

        if(UFODotMovingY){
            if(UFODotPoints[1]>=500){
                UFODotMovingY = false;
            }
            else{
                UFODotPoints[1]+=1f;
            }
        }
        else{
            if(UFODotPoints[1]<=300){
                UFODotMovingY = true;
            }
            else{
                UFODotPoints[1]-=1;
            }
        }
    }

    private void gameCollision(GL2 gl){
        // Logica Colisão do ponto no eixo X
        if(meteorDotMovingX){
            if(meteorDotPoints[0]- meteorSize /2< tools.axisX[1]){
                meteorDotPoints[0]+=meteorSpeed;
            }
            else {
                meteorDotMovingX = false;
            }

            if(gameState==2){ //colisão na lateral direita do ufo
                if((meteorDotPoints[0]-meteorSize/2<=UFODotPoints[0]+75 && meteorDotPoints[0]+meteorSize/2>=UFODotPoints[0]-75)&&
                        (meteorDotPoints[1]- meteorSize /2<UFODotPoints[1]+50 && meteorDotPoints[1]+ meteorSize /2>=UFODotPoints[1]-50)){
                    UFOHP--;
                    meteorDotMovingX = false;
                }
            }

        }
        else {
            if (meteorDotPoints[0]+ meteorSize /2 > tools.axisX[0]) {
                meteorDotPoints[0] -= meteorSpeed;
            } else {
                meteorDotMovingX = true;
            }

            if(gameState==2){
                //Colisão na lateral esquerda do ufo
                if((meteorDotPoints[0]+meteorSize/2<=UFODotPoints[0]+75 && meteorDotPoints[0]-meteorSize/2>=UFODotPoints[0]-75)&&(meteorDotPoints[1]- meteorSize /2<UFODotPoints[1]+50 && meteorDotPoints[1]+ meteorSize /2>=UFODotPoints[1]-50)){
                    UFOHP--;
                    meteorDotMovingX = true;
                }
            }
        }
        // Logica Colisão do ponto no eixo Y
        if(meteorDotMovingY){
            if(meteorDotPoints[1]+ meteorSize /2< tools.axisY[1]){
                meteorDotPoints[1]+=meteorSpeed;
            }
            else {
                meteorDotMovingY = false;
            }

            if(gameState==2){ //Colisão na parta inferior do ufo
                if((meteorDotPoints[1]+ meteorSize /2>=UFODotPoints[1]-50)&&(meteorDotPoints[0]- meteorSize /2<=UFODotPoints[0]+75 && meteorDotPoints[0]+ meteorSize /2>=UFODotPoints[0]-75)){
                    UFOHP--;
                    meteorDotMovingY = false;
                }
            }
        }
        else{
            //colisões do meteoro descendo
            if((meteorDotPoints[1]- meteorSize /2 >= gameBarY-10) && (meteorDotPoints[1] <= gameBarY) && ((meteorDotPoints[0]>=tools.cursorX-50 &&  meteorDotPoints[0]<= tools.cursorX+50))){
                playerScore+=100;
                meteorDotMovingY = true;
            }

            if (meteorDotPoints[1]- meteorSize /2 <= tools.axisY[0]){ //colisão no canto inferior
                meteorDotMovingY = true;
                playerHP--;
            }
            else {
                meteorDotPoints[1]-=meteorSpeed;
            }

            if(gameState==2) { //Colisão na parta superior do ufo
                if ((meteorDotPoints[1] - meteorSize / 2 >= UFODotPoints[1] + 50) && (meteorDotPoints[0] - meteorSize / 2 <= UFODotPoints[0] + 75 && meteorDotPoints[0] + meteorSize / 2 >= UFODotPoints[0] - 75)) {
                    UFOHP--;
                    meteorDotMovingY = true;
                }
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
            gl.glTranslatef(meteorDotPoints[0], meteorDotPoints[1], 0);
            gl.glPushMatrix();
                gl.glRotatef(gameDotRotation, 1,1,0);
                gl.glPushMatrix();
                gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
                gl.glShadeModel(GL2.GL_SMOOTH);
                    gl.glColor3f(0.6f,0.4f,0.2f);
                    gl.glScalef(meteorSize, meteorSize, meteorSize);
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
        gl.glPushMatrix();
        gl.glColor3f(0.2f, 0, 0);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(tools.axisX[1], gameBarY-50); //ponto dir inferior
        gl.glVertex2f(tools.axisX[1]-180, gameBarY-50); //ponto esq inferior
        gl.glVertex2f(tools.axisX[1], gameBarY+60); //ponto superior
        gl.glEnd();
        gl.glPopMatrix();

        //barbatana vertical
        gl.glPushMatrix();
        gl.glColor3f(0.7f, 0, 0);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(tools.axisX[1], gameBarY-50);
        gl.glVertex2f(tools.axisX[1]-200, gameBarY-50);
        gl.glVertex2f(tools.axisX[1], gameBarY+50);
        gl.glEnd();
        gl.glPopMatrix();

    }

    private void HP(GL2 gl){
        for (int currentHP = 1; currentHP<=playerHP; currentHP++ ){
            gl.glPushMatrix();
            gl.glTranslated(tools.axisX[0] + currentHP*40, tools.axisY[1],0);
            gl.glScalef(0.6f,0.6f,1);
            drawHeart(gl);
            gl.glPopMatrix();
        }
    }

    private void drawHeart(GL2 gl) {
        gl.glPushMatrix();
        int[][] pixelHeart = new int[][]{
                {0,0,1,1,1,0,0,0,1,1,1,0,0},
                {0,1,2,2,2,1,0,1,2,2,2,1,0},
                {1,2,2,2,2,2,1,2,2,2,2,2,1},
                {1,2,2,2,2,2,2,2,2,2,2,2,1},
                {1,2,2,2,2,2,2,2,2,2,2,2,1},
                {0,1,2,2,2,2,2,2,2,2,2,1,0},
                {0,0,1,2,2,2,2,2,2,2,1,0,0},
                {0,0,0,1,2,2,2,2,2,1,0,0,0},
                {0,0,0,0,1,2,2,2,1,0,0,0,0},
                {0,0,0,0,0,1,2,1,0,0,0,0,0},
                {0,0,0,0,0,0,1,0,0,0,0,0,0}
        };
        int lineIndex =0;
        for (int[] line:pixelHeart) {
            int pixelIndex = 0;
            for(int pixel:line){
                int x = pixelIndex*10;
                int y = lineIndex*10;
                switch (pixel){
                    case 0:
                        break;
                    case 1:
                        gl.glPushMatrix();
                        gl.glColor3f(0f,0f,0f);
                        gl.glBegin(GL2.GL_QUADS);
                        gl.glVertex2f(x, y);
                        gl.glVertex2f(x + 10, y);
                        gl.glVertex2f(x + 10, y -10);
                        gl.glVertex2f(x, y -10);
                        gl.glEnd();
                        gl.glPopMatrix();
                        break;
                    case 2:
                        gl.glPushMatrix();
                        gl.glColor3f(1f,0f,0f);
                        gl.glBegin(GL2.GL_QUADS);
                        gl.glVertex2f(x, y);
                        gl.glVertex2f(x + 10, y);
                        gl.glVertex2f(x + 10, y -10);
                        gl.glVertex2f(x, y -10);
                        gl.glEnd();
                        gl.glPopMatrix();
                        break;
                }
                pixelIndex++;
            }
            lineIndex--;
        }
        gl.glPopMatrix();
    }

    public void UFO(GL2 gl){
        textures.applySpitePolygonUFO(gl,3,
                UFODotPoints[0]-75,UFODotPoints[1],
                UFODotPoints[0]-25,UFODotPoints[1],
                UFODotPoints[0]-25,UFODotPoints[1]+50,
                UFODotPoints[0]+25,UFODotPoints[1]+50,
                UFODotPoints[0]+25,UFODotPoints[1],
                UFODotPoints[0]+75,UFODotPoints[1],
                UFODotPoints[0]+75,UFODotPoints[1]-50,
                UFODotPoints[0]-75,UFODotPoints[1]-50);
    }

    public void win(GL2 gl){
        textures.applySpriteQuad(gl, 4,
                tools.axisX[0]+50, tools.axisY[1]-209,
                tools.axisX[0]+800, tools.axisY[1]-209,
                tools.axisX[0]+800, tools.axisY[0]+209,
                tools.axisX[0]+50, tools.axisY[0]+209);

        tools.renderText(winText.text, winText.textPosition, winText.colour, winText.fontSize, winText.fontOutLineSize, winText.outLineColour);

        tools.renderText(endText[0]+playerScore, scoreEndTextPosition,scoreTextColor ,30);
        tools.renderText(endText[1],endTextPosition,endTextColor ,30);
    }

    public void loose(GL2 gl){
        textures.applySpriteQuad(gl, 5,
                tools.axisX[0]+50, tools.axisY[1]-209,
                tools.axisX[0]+800, tools.axisY[1]-209,
                tools.axisX[0]+800, tools.axisY[0]+209,
                tools.axisX[0]+50, tools.axisY[0]+209);

        tools.renderText(looseText.text, looseText.textPosition, looseText.colour, looseText.fontSize, looseText.fontOutLineSize, looseText.outLineColour);

        tools.renderText(endText[0]+playerScore, scoreEndTextPosition,scoreTextColor ,30);
        tools.renderText(endText[1],endTextPosition,endTextColor ,30);
    }

    public void start(GL2 gl){
        this.playerHP = 5;
        this.playerScore = 0;
        this.UFOHP = 5;
        this.gameWin = false;

        textures.applySpriteQuad(gl,0,
                tools.axisX[0]+50, tools.axisY[1]-90,
                tools.axisX[0]+800, tools.axisY[1]-90,
                tools.axisX[0]+800, tools.axisY[0]+90,
                tools.axisX[0]+50, tools.axisY[0]+90);

        tools.renderText(rulesTitle.text, rulesTitle.textPosition, rulesTitle.colour, rulesTitle.fontSize, rulesTitle.fontOutLineSize, rulesTitle.outLineColour);

        //game rules
        int lineIndex = 0;
        int[] linePosition = new int[]{-110,0};
        float[] lineColor = new float[]{1f,1f,1f,1f};
        for (String line:rulesText){
            linePosition[1] = 130 - lineIndex*40;
            tools.renderText(line,linePosition, lineColor, 30);
            lineIndex++;
        }
    }
}

