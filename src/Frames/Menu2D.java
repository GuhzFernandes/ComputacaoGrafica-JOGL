package Frames;

import Components.*;
import com.jogamp.opengl.GL2;

public class Menu2D {
    Tools tools;
    Textures textures;
    private final String returnInfoText = "Press the ' key to return to the menu";
    private final float[] returnInfoColor = new float[]{1f, 1f, 1f,1f};
    private final int[] returnInfoPosition = new int[]{-380,-280};

    public Menu2D(Tools tools, Textures textures){
        this.tools = tools;
        this.textures = textures;
    }

    public void run(GL2 gl){
        drawHeart(gl);
        tools.renderText(returnInfoText,returnInfoPosition,returnInfoColor,20);

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
}