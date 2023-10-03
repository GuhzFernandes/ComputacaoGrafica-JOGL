import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Cena implements GLEventListener{
    public Cena(GLWindow window){
        this.window = window;
    }

    //Declaração de variaveis para janela
    GLU glu;
    GLWindow window;
    public float xMin, xMax, yMin, yMax, zMin, zMax;
    public int currentWidth, currentHeight;
    public boolean fullscreen = false;

    //Declaração de variaveis comuns para o projeto
    public float cursorX, cursorY;
    public int frame = 0;

    //Declaração de variaveis para o frame de menu
    public float[] menuButton1 = new float[] {-60,60,30,10};
    public boolean selectMenuButton1 = false;
    public float[] menuButton2 = new float[] {-60,60,0,-20};
    public boolean selectMenuButton2 = false;
    public float[] menuButton3 = new float[] {-60,60,-30,-50};
    public boolean selectMenuButton3 = false;


    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();

        currentWidth = window.getWidth();
        currentHeight = window.getHeight();

        xMin = currentWidth*-0.5f;
        xMax = currentWidth*0.5f;
        yMin = currentHeight*-0.5f;
        yMax = currentHeight*0.5f;
        zMin = -100;
        zMax = 100;

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        window.setFullscreen(fullscreen);
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);       
        gl.glLoadIdentity();

        //Objects

        switch (frame){

            case 0:
                menu(gl);
                cursor(gl);
                break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;
        }
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        currentWidth = width;
        currentHeight = height;

        // Calcula os novos valores para xMin, xMax, yMin e yMax com base no tamanho atual da janela
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

        if(selectMenuButton1){gl.glColor3f(0,0,1);}
        else{gl.glColor3f(1,1,1);}
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton1[0], this.menuButton1[2]);
            gl.glVertex2f(this.menuButton1[1], this.menuButton1[2]);
            gl.glVertex2f(this.menuButton1[1], this.menuButton1[3]);
            gl.glVertex2f(this.menuButton1[0], this.menuButton1[3]);
        gl.glEnd();

        if(selectMenuButton2){gl.glColor3f(0,0,1);}
        else{gl.glColor3f(1,1,1);}
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton2[0], this.menuButton2[2]);
            gl.glVertex2f(this.menuButton2[1], this.menuButton2[2]);
            gl.glVertex2f(this.menuButton2[1], this.menuButton2[3]);
            gl.glVertex2f(this.menuButton2[0], this.menuButton2[3]);
        gl.glEnd();

        if(selectMenuButton3){gl.glColor3f(0,0,1);}
        else{gl.glColor3f(1,1,1);}
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.menuButton3[0], this.menuButton3[2]);
            gl.glVertex2f(this.menuButton3[1], this.menuButton3[2]);
            gl.glVertex2f(this.menuButton3[1], this.menuButton3[3]);
            gl.glVertex2f(this.menuButton3[0], this.menuButton3[3]);
        gl.glEnd();

        gl.glPopMatrix();
    }

}
