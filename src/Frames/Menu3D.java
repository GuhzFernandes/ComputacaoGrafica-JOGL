package Frames;

import Components.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Menu3D {
    Tools tools;
    Textures textures;

    public Tittle tittle = new Tittle(
            new int[] {-180,140},
            new float[] {0.9f, 0.4f,0.8f,1f},
            new float[] {1,1,1,0.1f},
            50,
            3,
            "Sample 3D"
    );

    public float zoom = 1;


    public Menu3D(Tools tools, Textures textures){
        this.tools = tools;
        this.textures = textures;
    }

    public void run(GL2 gl, GLUT glut){
        tools.lightOn(gl);
        gl.glPushMatrix();
            tools.manipulator3D(gl);
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
            gl.glShadeModel(GL2.GL_SMOOTH);
            //gl.glScalef(100,100,100);
            //glut.glutSolidDodecahedron();
            gl.glColor3f(0.26f, 0.84f, 0.96f);
            glut.glutSolidCube(100);
            gl.glPopMatrix();
        tools.lightOff(gl);
        tools.resetManipulator3D(gl);
        tools.resetPolygonMode(gl);
    }
}
