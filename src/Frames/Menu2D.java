package Frames;

import Components.*;
import com.jogamp.opengl.GL2;

public class Menu2D {
    Tools tools;
    Textures textures;

    public Tittle tittle = new Tittle(
            new int[] {-140,140},
            new float[] {0.1f, 0.8f,0.2f,1f},
            new float[] {1,1,1,0.1f},
            50,
            3,
            "Sample 2D"
    );

    public Button2D[] buttons = new Button2D[]{
            new Button2D(
                    new float[]{1,1,1,1},
                    new float[]{0.1f, 0.8f,0.2f,1f},
                    new float[]{-200,200,50,0},
                    30,
                    2,
                    new int[]{-90, 10},
                    "Astronaut",
                    false),
            new Button2D(
                    new float[]{1,1,1,1},
                    new float[]{0.1f, 0.8f,0.2f,1f},
                    new float[]{-200,200,-20,-70},
                    30,
                    2,
                    new int[]{-90,-60},
                    "",
                    false)
    };

    public Menu2D(Tools tools, Textures textures){
        this.tools = tools;
        this.textures = textures;
    }

    public void run(GL2 gl){
        tools.renderText(tittle.text, tittle.textPosition, tittle.colour, tittle.fontSize, tittle.fontOutLineSize, tittle.outLineColour);
        tools.renderButtons(gl, buttons);
    }

    public void astronaut(GL2 gl){
        boolean gameBarAnimation = true;
        float gameAnimationY = 0;

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

        //mão esquerda
        gl.glColor3f(1,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-40, tools.cursorY-5);
        gl.glVertex2f(tools.cursorX-30, tools.cursorY-5);
        gl.glVertex2f(tools.cursorX-30, tools.cursorY-15);
        gl.glVertex2f(tools.cursorX-40, tools.cursorY-15);
        gl.glEnd();

        //mão direita
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX+40, tools.cursorY-5);
        gl.glVertex2f(tools.cursorX+30, tools.cursorY-5);
        gl.glVertex2f(tools.cursorX+30, tools.cursorY-15);
        gl.glVertex2f(tools.cursorX+40, tools.cursorY-15);
        gl.glEnd();

        //visor
        gl.glColor3f(1,1,1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-25, tools.cursorY-25);
        gl.glVertex2f(tools.cursorX, tools.cursorY-25);
        gl.glVertex2f(tools.cursorX, tools.cursorY-40);
        gl.glVertex2f(tools.cursorX-25, tools.cursorY-40);
        gl.glEnd();

        //corpo
        gl.glColor3f(1,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-20, tools.cursorY-15);
        gl.glVertex2f(tools.cursorX+20, tools.cursorY-15);
        gl.glVertex2f(tools.cursorX+20, tools.cursorY-55);
        gl.glVertex2f(tools.cursorX-20, tools.cursorY-55);
        gl.glEnd();

        //pé esquerdo
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX-10, tools.cursorY-55+gameAnimationY);
        gl.glVertex2f(tools.cursorX-20, tools.cursorY-55+gameAnimationY);
        gl.glVertex2f(tools.cursorX-20, tools.cursorY-65+gameAnimationY);
        gl.glVertex2f(tools.cursorX-10, tools.cursorY-65+gameAnimationY);
        gl.glEnd();

        //pé direito
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX+10, tools.cursorY-55-gameAnimationY+4);
        gl.glVertex2f(tools.cursorX+20, tools.cursorY-55-gameAnimationY+4);
        gl.glVertex2f(tools.cursorX+20, tools.cursorY-65-gameAnimationY+4);
        gl.glVertex2f(tools.cursorX+10, tools.cursorY-65-gameAnimationY+4);
        gl.glEnd();

        //mochila
        gl.glColor3f(0.5f,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(tools.cursorX+20, tools.cursorY-25);
        gl.glVertex2f(tools.cursorX+30, tools.cursorY-25);
        gl.glVertex2f(tools.cursorX+30, tools.cursorY-45);
        gl.glVertex2f(tools.cursorX+20, tools.cursorY-45);
        gl.glEnd();
        gl.glPopMatrix();

    }

}
