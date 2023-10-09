package Frames;

import Components.*;
import com.jogamp.opengl.GL2;

public class Menu2D {
    Tools tools;

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

    public Menu2D(Tools tools){
        this.tools = tools;
    }

    public void run(GL2 gl){
        tools.renderText(tittle.text, tittle.textPosition, tittle.colour, tittle.fontSize, tittle.fontOutLineSize, tittle.outLineColour);
        tools.renderButtons(gl, buttons);
    }

}
