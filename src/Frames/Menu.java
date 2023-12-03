package Frames;
import Components.*;
import com.jogamp.opengl.GL2;

public class Menu {
    Tools tools;
    GL2 gl;

    //Titulo
    public Tittle tittle = new Tittle(
            new int[]{-150,140},
            new float[]{0.1f, 0.6f,0.9f,1f},
            new float[]{1,1,1,0.1f},
            50,
            3,
            "Java OpenGL"
    );

    //Buttons
    public Button2D[] buttons = new Button2D[]{
            new Button2D(
                    new float[]{1,1,1,1},
                    new float[]{0.1f, 0.6f,0.9f,1f},
                    new float[] {-200,200,50,0},
                    30,
                    2,
                    new int[]{-80, 10},
                    "Sample 2D",
                    false),
            new Button2D(
                    new float[]{1,1,1,1},
                    new float[]{0.1f, 0.6f,0.9f,1f},
                    new float[] {-200,200,-20,-70},
                    30,
                    2,
                    new int[]{-80,-60},
                    "Sample 3D",
                    false),
            new Button2D(
                    new float[]{1,1,1,1},
                    new float[]{0.1f, 0.6f,0.9f,1f},
                    new float[] {-200,200,-90,-140},
                    30,
                    2,
                    new int[]{-40,-130},
                    "Game",
                    false)
    };

    public Menu(GL2 gl, Tools tools){
        this.tools = tools;
        this.gl = gl;
    }

    public void run(GL2 gl){
        gl.glPushMatrix();
        tools.renderText(tittle.text, tittle.textPosition, tittle.colour, tittle.fontSize,tittle.fontOutLineSize,tittle.outLineColour);
        tools.renderButtons(gl,this.buttons);
        gl.glPopMatrix();
    }
}