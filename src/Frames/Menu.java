package Frames;

import com.jogamp.opengl.GL2;

public class Menu {

    public GL2 gl;

    //Titulo
    public int[] titleTextPosition = new int[] {-150,140};
    public float[] titleTextColour = new float[] {0.1f, 0.6f,0.9f,1f},
            titleOutLineTextColour = new float[] {1,1,1,0.1f};
    public int titleFontSize = 50,
            titleOutLineSize = 3;
    public String titleText = "Java OpenGL";

    //Botões
    public float[] buttonsColour = new float[]{1,1,1,1},
            buttonsOutLineColour = new float[]{0.1f, 0.6f,0.9f,1f};
    public int buttonsFontSize = 30,
            buttonsOutLineSize = 2;
    public float[] button1 = new float[] {-200,200,50,0};
    public int[] button1TextPosition = new int[]{-100, 10};
    public String button1Text = "Exercicios 2D";
    public boolean selectButton1 = false;
    public float[] button2 = new float[] {-200,200,-20,-70};
    public int[] button2TextPosition = new int[]{-100,-60};
    public String button2Text = "Exercicios 3D";
    public boolean selectbutton2 = false;
    public float[] button3 = new float[] {-200,200,-90,-140};
    public int[] button3TextPosition = new int[]{-100,-130};
    public String button3Text = "Simulações";
    public boolean selectButton3 = false;

    public Menu(GL2 gl){
        this.gl = gl;
    }

    /*
    public void run(){
        gl.glPushMatrix();

        renderText(titleText, titleTextPosition, titleTextColour, titleFontSize,titleOutLineSize,titleOutLineTextColour);

            if(selectButton1){
            renderText(button1Text, button1TextPosition, buttonsColour, buttonsFontSize, buttonsOutLineSize,buttonsOutLineColour);
            gl.glColor3f(buttonsOutLineColour[0],buttonsOutLineColour[1],buttonsOutLineColour[2]);
            }
            else{
            renderText(button1Text, button1TextPosition, buttonsColour, buttonsFontSize);
            gl.glColor3f(buttonsColour[0],buttonsColour[1],buttonsColour[2]);
            }
            gl.glBegin(GL2.GL_LINE_LOOP);
                gl.glVertex2f(this.button1[0], this.button1[2]);
                gl.glVertex2f(this.button1[1], this.button1[2]);
                gl.glVertex2f(this.button1[1], this.button1[3]);
                gl.glVertex2f(this.button1[0], this.button1[3]);
            gl.glEnd();

            if(selectbutton2){
            renderText(button2Text, button2TextPosition, buttonsColour, buttonsFontSize, buttonsOutLineSize,buttonsOutLineColour);
            gl.glColor3f(buttonsOutLineColour[0],buttonsOutLineColour[1],buttonsOutLineColour[2]);
            }
            else{
            renderText(button2Text, button2TextPosition, buttonsColour, buttonsFontSize);
            gl.glColor3f(buttonsColour[0],buttonsColour[1],buttonsColour[2]);
            }
            gl.glBegin(GL2.GL_LINE_LOOP);
                gl.glVertex2f(this.button2[0], this.button2[2]);
                gl.glVertex2f(this.button2[1], this.button2[2]);
                gl.glVertex2f(this.button2[1], this.button2[3]);
                gl.glVertex2f(this.button2[0], this.button2[3]);
            gl.glEnd();

            if(selectButton3){
            renderText(button3Text, button3TextPosition, buttonsColour, buttonsFontSize, buttonsOutLineSize,buttonsOutLineColour);
            gl.glColor3f(buttonsOutLineColour[0],buttonsOutLineColour[1],buttonsOutLineColour[2]);
            }
            else{
            renderText(button3Text, button3TextPosition, buttonsColour, buttonsFontSize);
            gl.glColor3f(buttonsColour[0],buttonsColour[1],buttonsColour[2]);
            }
            gl.glBegin(GL2.GL_LINE_LOOP);
                gl.glVertex2f(this.button3[0], this.button3[2]);
                gl.glVertex2f(this.button3[1], this.button3[2]);
                gl.glVertex2f(this.button3[1], this.button3[3]);
                gl.glVertex2f(this.button3[0], this.button3[3]);
            gl.glEnd();
            gl.glPopMatrix();
        }
        */
}