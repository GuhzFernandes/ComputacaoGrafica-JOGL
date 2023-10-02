import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Cena implements GLEventListener{

    //Definições basicas
    public float xMin, xMax, yMin, yMax, zMin, zMax;
    public int currentWidth, currentHeight;
    public boolean fullscreen = false;
    public float cursorX, cursorY;
    public int frame = 0;
    //Definições menu
    public float[] button1 = new float[] {-40,40,30,10};
    public boolean selectButton1 = false;

    public float[] button2 = new float[] {-40,40,0,-20};
    public boolean selectButton2 = false;

    public float[] button3 = new float[] {-40,40,-30,-50};
    public boolean selectButton3 = false;
    //
    GLU glu;
    
    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
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
        GL2 gl = drawable.getGL().getGL2();
        if(height == 0) height = 1;
        float aspect = (float) width / height;
        //set de viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);
                
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //lê a matriz identidade

        if(width >= height)            
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
        this.currentWidth = width;
        this.currentHeight = height;
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

        if(selectButton1){gl.glColor3f(0,0,1);}
        else{gl.glColor3f(1,1,1);}
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.button1[0], this.button1[2]);
            gl.glVertex2f(this.button1[1], this.button1[2]);
            gl.glVertex2f(this.button1[1], this.button1[3]);
            gl.glVertex2f(this.button1[0], this.button1[3]);
        gl.glEnd();

        if(selectButton2){gl.glColor3f(0,0,1);}
        else{gl.glColor3f(1,1,1);}
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.button2[0], this.button2[2]);
            gl.glVertex2f(this.button2[1], this.button2[2]);
            gl.glVertex2f(this.button2[1], this.button2[3]);
            gl.glVertex2f(this.button2[0], this.button2[3]);
        gl.glEnd();

        if(selectButton3){gl.glColor3f(0,0,1);}
        else{gl.glColor3f(1,1,1);}
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(this.button3[0], this.button3[2]);
            gl.glVertex2f(this.button3[1], this.button3[2]);
            gl.glVertex2f(this.button3[1], this.button3[3]);
            gl.glVertex2f(this.button3[0], this.button3[3]);
        gl.glEnd();

        gl.glPopMatrix();
    }





}
