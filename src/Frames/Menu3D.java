package Frames;

import Components.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Menu3D {
    Tools tools;

    public Tittle tittle = new Tittle(
            new int[] {-180,140},
            new float[] {0.9f, 0.4f,0.8f,1f},
            new float[] {1,1,1,0.1f},
            50,
            3,
            "Sample 3D"
    );

    public Button2D[] buttons = new Button2D[]{
            new Button2D(
                    new float[]{1,1,1,1},
                    new float[]{0.1f, 0.6f,0.9f,1f},
                    new float[] {-200,200,50,0},
                    30,
                    2,
                    new int[]{-50, 10},
                    "Simple 3D object",
                    false),
    };

    public float zoom = 1;


    public Menu3D(Tools tools){
        this.tools = tools;
    }

    public void run(GL2 gl, GLUT glut){
        gl.glPushMatrix();
        tools.manipulator3D(gl);
        gl.glColor3f(0.9f, 0.4f,0.8f);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
        //glut.glutSolidDodecahedron();
        glut.glutSolidCube(100);
        gl.glPopMatrix();
        tools.resetManipulator3D(gl);
        tools.resetPolygonMode(gl);
    }
}