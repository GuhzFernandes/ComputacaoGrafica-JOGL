package Components;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Tools {
    //Declaração da coordenada do mouse
    public float cursorX, cursorY;

    //Declaração para variaveis para metodos de renderização
    public int[] currentResolution = new int[2];
    public float[] axisX = new float[2];
    public float[] axisY = new float[2];
    public float[] axisZ = new float[2];

    //Declaração de variaveis para as sub-areas.
    public float[][][] frameAreas = new float[2][2][3];

    TextRenderer textRenderer;
    TextRenderer[] fontSizeRenderer = new TextRenderer[10];

    public float[] axisControl = new float[] {0,0,0};
    public float zoom = 1;

    public void init(){
        for( int fontsize = 10; fontsize<= 100; fontsize += 10){
            fontSizeRenderer[Math.round(fontsize*0.1f)-1] = new TextRenderer(new Font("Consolas",Font.PLAIN,fontsize));
        }
    }

    public void update(int[] resolution, float[]x,float[]y,float[]z) {
        axisX = x;
        axisY = y;
        axisZ = z;
        currentResolution = resolution;
        frameAreasCalc();
    }

    public void frameAreasCalc(){
        //Calculo comum para ambas as areas
        float[] bothAreasX = new float[3];
        bothAreasX[0] = currentResolution[0]; // Largura total da area;
        bothAreasX[1] = axisX[0] + bothAreasX[0]*0.3f; // Ponto minimo da area - considerando marge
        bothAreasX[2] = bothAreasX[1]*-1; // Ponto Maximo da area - considerando marge
        //Calculo da area superior do frame
        float[] upperAreaY = new float[3];
        upperAreaY[0] = currentResolution[1]*0.33f; // Altura total da area;
        upperAreaY[2] = axisY[1] - upperAreaY[0]*0.2f; // Ponto maximo da area - considerando a margem
        upperAreaY[1] = axisY[1] - upperAreaY[0] + upperAreaY[2]; //Ponto minimo da area - considerando a margem
        //Calculo da area inferior do frame
        float[] underAreaY = new float[3];
        underAreaY[0] = currentResolution[1]-underAreaY[0]; // Altura total da area;
        underAreaY[1] = axisY[0] + underAreaY[0]*0.2f; // Ponto minimo da area - considerando a margem
        underAreaY[2] = axisY[0] + underAreaY[0] - upperAreaY[0]*0.2f; // Ponto maximo da area - considerando a margem

        this.frameAreas[0][0] = bothAreasX;
        this.frameAreas[0][1] = upperAreaY;
        this.frameAreas[1][0] = bothAreasX;
        this.frameAreas[1][1] = underAreaY;
    }

    public void renderText(String text, int[] textPosition, float[] fontColour, int fontSize){
        textRenderer = fontSizeRenderer[Math.round(fontSize*0.1f)-1];
        textRenderer.beginRendering(currentResolution[0],currentResolution[1]);
        textRenderer.setColor(fontColour[0],fontColour[1],fontColour[2],fontColour[3]);
        textRenderer.draw(text,(Math.round((currentResolution[0]*0.5f) + textPosition[0])),(Math.round((currentResolution[1]*0.5f) + textPosition[1])));
        textRenderer.endRendering();
    }

    public void renderText(String text, int[] textPositionIn, float[] fontColour, int fontSize,int outLine,float[] outLineFontColour){
        textRenderer = fontSizeRenderer[Math.round(fontSize*0.1f)-1];
        int[] textPosition = new int[2];
        textPosition[0] = Math.round((currentResolution[0]*0.5f) + textPositionIn[0]);
        textPosition[1] = Math.round((currentResolution[1]*0.5f) + textPositionIn[1]);
        textRenderer.beginRendering(currentResolution[0],currentResolution[1]);

        // Calcula e renderiza o outline do texto
        for (int outLineX = -outLine; outLineX <= outLine; outLineX++) {
            for (int outLineY = -outLine; outLineY <= outLine; outLineY++) {
                if (outLineX != 0 || outLineY != 0) {
                    textRenderer.setColor(outLineFontColour[0],outLineFontColour[1],outLineFontColour[2],outLineFontColour[3]);
                    textRenderer.draw(text,textPosition[0]+ outLineX,textPosition[1]+ outLineY);
                }
            }
        }
        // Efetivamente renderiza o texto
        textRenderer.setColor(fontColour[0],fontColour[1],fontColour[2],fontColour[3]);
        textRenderer.draw(text,textPosition[0],textPosition[1]);
        textRenderer.endRendering();
    }

    public void renderButtons(GL2 gl, Button2D[] buttons){
        gl.glPushMatrix();
        for (Button2D button:buttons) {
            if(button.select){
                renderText(button.text,
                        button.textPosition,
                        button.frameColour,
                        button.fontSize,
                        button.fontOutLineSize,
                        button.frameOutLineColour);
                gl.glColor3f(button.frameOutLineColour[0],button.frameOutLineColour[1],button.frameOutLineColour[2]);
            }
            else{
                renderText(button.text,
                        button.textPosition,
                        button.frameColour,
                        button.fontSize);
                gl.glColor3f(button.frameColour[0],button.frameColour[1],button.frameColour[2]);
            }
            gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(button.frameVertex[0], button.frameVertex[2]);
            gl.glVertex2f(button.frameVertex[1], button.frameVertex[2]);
            gl.glVertex2f(button.frameVertex[1], button.frameVertex[3]);
            gl.glVertex2f(button.frameVertex[0], button.frameVertex[3]);
            gl.glEnd();
        }
        gl.glPopMatrix();
    }

    public void cursor(GL2 gl){
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 1); //cor branca
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2f(cursorX,cursorY);
            gl.glVertex2f(cursorX + 5,cursorY );
            gl.glVertex2f(cursorX + 2.7f, cursorY-2.7f);
            gl.glVertex2f(cursorX + 4.2f, cursorY-3.8f);
            gl.glVertex2f(cursorX + 3.8f, cursorY-4.2f);
            gl.glVertex2f(cursorX + 2.7f, cursorY-2.7f);
            gl.glVertex2f(cursorX, cursorY-5);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public void manipulator3D(GL2 gl){
        if(axisControl[2] >= 1){
            zoom = axisControl[2];
            gl.glScaled(zoom,zoom,zoom);
        }else if(axisControl[2] == 0){
            gl.glScaled(1,1,1);
        }
        else{
            zoom = 1f / axisControl[2]*-1;
            gl.glScaled(zoom,zoom,zoom);
        }
        gl.glRotatef(axisControl[0],0,1,0);
        gl.glRotatef(axisControl[1],1,0,0);
    }
    public void resetManipulator3D(GL2 gl){
        gl.glScaled(1,1,1);
        gl.glRotatef(0,1,1,1);
        gl.glPolygonMode(gl.GL_FRONT,gl.GL_FILL);
    }

    public void resetPolygonMode(GL2 gl){
        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);
    }

    public void lightOn(GL2 gl){
        gl.glPushMatrix();
            gl.glEnable(GL2.GL_COLOR_MATERIAL);
            gl.glEnable(GL2.GL_LIGHTING);
            gl.glEnable(GL2.GL_NORMALIZE);
            gl.glEnable(GL2.GL_LIGHT0);
            gl.glLightfv(GL2.GL_LIGHT0,GL2.GL_POSITION, new float[]{0,axisY[1],axisZ[1],0},0);
        gl.glPopMatrix();
    }

    public void lightOff(GL2 gl){
        gl.glPushMatrix();
            gl.glDisable(GL2.GL_COLOR_MATERIAL);
            gl.glDisable(GL2.GL_LIGHTING);
            gl.glDisable(GL2.GL_NORMALIZE);
            gl.glDisable(GL2.GL_LIGHT0);
        gl.glPopMatrix();
    }

}
