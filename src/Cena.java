import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Font;

public class Cena implements GLEventListener{
    public Cena(GLWindow window){this.window = window;}

    //Declaração de variaveis para janela
    GLU glu;
    GLUT glut;
    TextRenderer textRenderer;
    TextRenderer[] fontSizeRenderer = new TextRenderer[5];

    GLWindow window;
    public float xMin, xMax, yMin, yMax, zMin, zMax;
    public int currentWidth, currentHeight;
    public boolean fullscreen = false;

    //Declaração de variaveis comuns para o projeto
    public float cursorX, cursorY;
    public int frame = 0;

    //Declaração de variaveis para o frame de menu
    public int[] menuTitleTextPosition = new int[] {-150,140};
    public float[] menuTitleTextColour = new float[] {0.1f, 0.6f,0.9f,1f},
            menuTitleOutLineTextColour = new float[] {1,1,1,0.1f};
    public int menuTitleFontSize = 50,
            menuTitleOutLineSize = 3;
    public String menuTitleText = "Java OpenGL";

    public float[] menuButtonsColour = new float[]{1,1,1,1},
            menuButtonsOutLineColour = new float[]{0.1f, 0.6f,0.9f,1f};
    public int menuButtonsFontSize = 30,
            menuButtonsOutLineSize = 2;
    public float[] menuButton1 = new float[] {-200,200,50,0};
    public int[] menuButton1TextPosition = new int[]{-100, 10};
    public String menuButton1Text = "Exercicios 2D";
    public boolean selectMenuButton1 = false;
    public float[] menuButton2 = new float[] {-200,200,-20,-70};
    public int[] menuButton2TextPosition = new int[]{-100,-60};
    public String menuButton2Text = "Exercicios 3D";
    public boolean selectMenuButton2 = false;
    public float[] menuButton3 = new float[] {-200,200,-90,-140};
    public int[] menuButton3TextPosition = new int[]{-100,-130};
    public String menuButton3Text = "None Yet";
    public boolean selectMenuButton3 = false;

    //Declaração de variaveis para o frame de Exercicios2D
    public int[] e2DTitleTextPosition = new int[] {-150,140};
    public float[] e2DTitleTextColour = new float[] {0.1f, 0.6f,0.9f,1f},
            e2DTitleOutLineTextColour = new float[] {1,1,1,0.1f};
    public int e2DTitleFontSize = 50,
            e2DTitleOutLineSize = 3;
    public String e2DTitleText = "Exercicios 2D";


    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        glut = new GLUT();

        currentWidth = window.getWidth();
        currentHeight = window.getHeight();

        xMin = currentWidth*-0.5f;
        xMax = currentWidth*0.5f;
        yMin = currentHeight*-0.5f;
        yMax = currentHeight*0.5f;
        zMin = -100;
        zMax = 100;

        for( int fontsize = 10; fontsize<= 50; fontsize += 10){
            fontSizeRenderer[Math.round(fontsize*0.1f)-1] = new TextRenderer(new Font("Consolas",Font.PLAIN,fontsize));
        }

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        window.setFullscreen(fullscreen);

        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        //Objects
        switch (frame){
            case 0:
                menu(gl);
                cursor(gl);
                break;
            case 1:
                exercicios2D(gl);
                break;
            case 2:
                break;
            case 3:
                break;
        }
        gl.glFlush();
        System.gc();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        currentWidth = width;
        currentHeight = height;

        // Calcula os novos valores do plano cartesiano para o tamanho atual da janela
        xMin = currentWidth * -0.5f;
        xMax = currentWidth * 0.5f;
        yMin = currentHeight * -0.5f;
        yMax = currentHeight * 0.5f;

        // Atualiza a matriz de projeção com os novos valores
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
       
    @Override
    public void dispose(GLAutoDrawable drawable) {
        textRenderer.dispose();
    }

    public void renderText(String text, int[] textPositionIn, float[] fontColour, int fontSize){
        textRenderer = fontSizeRenderer[Math.round(fontSize*0.1f)-1];
        int[] textPosition = new int[2];
        textPosition[0] = Math.round((currentWidth*0.5f) + textPositionIn[0]);
        textPosition[1] = Math.round((currentHeight*0.5f) + textPositionIn[1]);
        textRenderer.beginRendering(currentWidth,currentHeight);
            textRenderer.setColor(fontColour[0],fontColour[1],fontColour[2],fontColour[3]);
            textRenderer.draw(text,textPosition[0],textPosition[1]);
        textRenderer.endRendering();
    }

    public void renderText(String text, int[] textPositionIn, float[] fontColour, int fontSize,int outLine,float[] outLineFontColour){
        textRenderer = fontSizeRenderer[Math.round(fontSize*0.1f)-1];
        int[] textPosition = new int[2];
        textPosition[0] = Math.round((currentWidth*0.5f) + textPositionIn[0]);
        textPosition[1] = Math.round((currentHeight*0.5f) + textPositionIn[1]);
        textRenderer.beginRendering(currentWidth,currentHeight);

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
    public void menu(GL2 gl){
        gl.glPushMatrix();

        renderText(menuTitleText, menuTitleTextPosition, menuTitleTextColour, menuTitleFontSize,menuTitleOutLineSize,menuTitleOutLineTextColour);

        if(selectMenuButton1){
            renderText(menuButton1Text, menuButton1TextPosition, menuButtonsColour, menuButtonsFontSize, menuButtonsOutLineSize,menuButtonsOutLineColour);
            gl.glColor3f(menuButtonsOutLineColour[0],menuButtonsOutLineColour[1],menuButtonsOutLineColour[2]);
        }
        else{
            renderText(menuButton1Text, menuButton1TextPosition, menuButtonsColour, menuButtonsFontSize);
            gl.glColor3f(menuButtonsColour[0],menuButtonsColour[1],menuButtonsColour[2]);
        }
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton1[0], this.menuButton1[2]);
            gl.glVertex2f(this.menuButton1[1], this.menuButton1[2]);
            gl.glVertex2f(this.menuButton1[1], this.menuButton1[3]);
            gl.glVertex2f(this.menuButton1[0], this.menuButton1[3]);
        gl.glEnd();

        if(selectMenuButton2){
            renderText(menuButton2Text, menuButton2TextPosition, menuButtonsColour, menuButtonsFontSize, menuButtonsOutLineSize,menuButtonsOutLineColour);
            gl.glColor3f(menuButtonsOutLineColour[0],menuButtonsOutLineColour[1],menuButtonsOutLineColour[2]);
        }
        else{
            renderText(menuButton2Text, menuButton2TextPosition, menuButtonsColour, menuButtonsFontSize);
            gl.glColor3f(menuButtonsColour[0],menuButtonsColour[1],menuButtonsColour[2]);
        }
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton2[0], this.menuButton2[2]);
            gl.glVertex2f(this.menuButton2[1], this.menuButton2[2]);
            gl.glVertex2f(this.menuButton2[1], this.menuButton2[3]);
            gl.glVertex2f(this.menuButton2[0], this.menuButton2[3]);
        gl.glEnd();

        if(selectMenuButton3){
            renderText(menuButton3Text, menuButton3TextPosition, menuButtonsColour, menuButtonsFontSize, menuButtonsOutLineSize,menuButtonsOutLineColour);
            gl.glColor3f(menuButtonsOutLineColour[0],menuButtonsOutLineColour[1],menuButtonsOutLineColour[2]);
        }
        else{
            renderText(menuButton3Text, menuButton3TextPosition, menuButtonsColour, menuButtonsFontSize);
            gl.glColor3f(menuButtonsColour[0],menuButtonsColour[1],menuButtonsColour[2]);
        }
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton3[0], this.menuButton3[2]);
            gl.glVertex2f(this.menuButton3[1], this.menuButton3[2]);
            gl.glVertex2f(this.menuButton3[1], this.menuButton3[3]);
            gl.glVertex2f(this.menuButton3[0], this.menuButton3[3]);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public void exercicios2D(GL2 gl){
        gl.glPushMatrix();

        renderText(e2DTitleText, e2DTitleTextPosition, e2DTitleTextColour, e2DTitleFontSize,e2DTitleOutLineSize,e2DTitleOutLineTextColour);


        gl.glPopMatrix();

    }

}
