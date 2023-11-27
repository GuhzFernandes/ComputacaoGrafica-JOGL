package Frames;

import com.jogamp.opengl.GL2;
import Components.Tools;

public class Pong {
    Tools tools;

    public boolean gameDecoration = false;
    public boolean gameBarAnimation = true;
    public float gameBarY = -200f;
    public float gameAnimationY = 0;
    public float[] gameDotPoints = new float[] {0,0};
    public float[] gameDotAceleration = new float[]{0,0};
    public boolean gamePause = false;
    public boolean gameDotMovingX = true;
    public boolean gameDotMovingY = true;

    public Pong(Tools tools){
        this.tools = tools;
    }

    public void run(GL2 gl){

       if(gamePause){
           // a realizar
       }
       else{
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
               if (gameDotPoints[1]> tools.axisY[0] && !((gameDotPoints[1] >= gameBarY-5) && (gameDotPoints[1] <= gameBarY) && gameDotPoints[0]>=tools.cursorX-50 &&  gameDotPoints[0]<= tools.cursorX+50)){
                   gameDotPoints[1]-=5f;
               }
               else {
                   //apply damage -> if(gameDotPoints[1]<=yMin){}
                   gameDotMovingY = true;
               }
           }
       }

        if(!gameDecoration){
            //Renderização do jogo
            gl.glPushMatrix();

            //barra
            gl.glColor3f(1,1,1);
            gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2f(tools.cursorX-50, gameBarY);
            gl.glVertex2f(tools.cursorX+50 , gameBarY);
            gl.glVertex2f(tools.cursorX+50, gameBarY-5);
            gl.glVertex2f(tools.cursorX-50, gameBarY-5);
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

            foguete(gl);
        }
    }

    public void foguete(GL2 gl){
        //corpo do foguete
        gl.glColor3f(0.5f,0.5f,0.5f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.axisX[0], tools.axisY[0]);
        gl.glVertex2f(tools.axisX[0], gameBarY-50);
        gl.glVertex2f(tools.axisX[1], gameBarY-50 );
        gl.glVertex2f(tools.axisX[1], tools.axisY[0]);
        gl.glEnd();

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
}
