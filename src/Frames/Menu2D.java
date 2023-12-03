package Frames;

import Components.*;
import com.jogamp.opengl.GL2;

public class Menu2D {
    Tools tools;
    Textures textures;

    public Menu2D(Tools tools, Textures textures){
        this.tools = tools;
        this.textures = textures;
    }

    public void run(GL2 gl){
        //gl.glColor3f(0.0f, 0.0f, 0.0f); // Cor preta para a borda
        //drawHeart(gl, -200.0f, 0.0f); // Desenha um coração na posição x=-200.0, y=0.0
        //gl.glColor3f(1.0f, 0.75f, 0.75f); // Cor rosa clarinho
        drawHeart(gl);

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