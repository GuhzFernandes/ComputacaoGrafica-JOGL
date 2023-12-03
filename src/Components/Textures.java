package Components;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textures {

    private String[] spriteFiles = new String[]{
            "src/Assets/Sprites/GameCover.jpg",
            "src/Assets/Sprites/Space.png",
            "src/Assets/Sprites/SpaceshipBody.jpg",
            "src/Assets/Sprites/UFO.png",
            "src/Assets/Sprites/Trophy.png",
            "src/Assets/Sprites/Dead.png"
    };

    public Texture[] spritePack = new Texture[spriteFiles.length];
    private float planoS[] = {1.0f, 0.0f, 0.0f, 0.0f};
    private float planoT[] = {0.0f, 0.0f, 1.0f, 0.0f};
    private int filter = GL2.GL_NEAREST;
    private int wrap = GL2.GL_REPEAT;
    private int mode = GL2.GL_DECAL;
    private int genMode = GL2.GL_SPHERE_MAP;

    public void init(GL2 gl){
        for (int i = 0; i < this.spriteFiles.length; i++){
            //Carrega as texturas
            try {
                this.spritePack[i] = TextureIO.newTexture(new File(this.spriteFiles[i]), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Gera as sprites como textura
            this.spritePack[i].bind(gl);
            this.spritePack[i].enable(gl);
            this.spritePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER,filter);
            this.spritePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER,filter);
            this.spritePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, wrap);
            this.spritePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, wrap);
            gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, mode);
            //Habilita as configurações 3D
            generate(gl, spritePack[i]);
            //Gera Mipmap 2D
            gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
        }
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

    }

    private void generate(GL2 gl, Texture texture){
        //int genModo = GL2.GL_OBJECT_LINEAR; //GL.GL_EYE_LINEAR ou GL_OBJECT_LINEAR ou GL_SPHERE_MAP
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);
        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, genMode);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, genMode);
        gl.glTexGenfv(GL2.GL_S, GL2.GL_OBJECT_PLANE, planoS, 0);
        gl.glTexGenfv(GL2.GL_T, GL2.GL_OBJECT_PLANE, planoT, 0);

        texture.bind(gl);
        texture.enable(gl);
    }

    public void disable(GL2 gl){
        gl.glDisable(GL2.GL_TEXTURE_GEN_S);
        gl.glDisable(GL2.GL_TEXTURE_GEN_T);
    }

    public void applySpriteQuad(GL2 gl, int index, float v1X, float v1Y, float v2X, float v2Y, float v3X, float v3Y, float v4X, float v4Y) {
        gl.glPushMatrix();

        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        spritePack[index].bind(gl);
        spritePack[index].enable(gl);

        gl.glColor4f(0f,0f,0f,1f);

        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0f, 1f);
        gl.glVertex2f(v1X, v1Y);
        gl.glTexCoord2f(1f, 1f);
        gl.glVertex2f(v2X, v2Y);
        gl.glTexCoord2f(1f, 0f);
        gl.glVertex2f(v3X, v3Y);
        gl.glTexCoord2f(0f, 0f);
        gl.glVertex2f(v4X, v4Y);
        gl.glEnd();

        spritePack[index].disable(gl);

        gl.glDisable(GL2.GL_BLEND);
        gl.glPopMatrix();
    }

    public void applySpitePolygonUFO(GL2 gl, int index, float v1X, float v1Y, float v2X, float v2Y, float v3X, float v3Y, float v4X, float v4Y,float v5X, float v5Y, float v6X, float v6Y,float v7X, float v7Y,float v8X, float v8Y) {
        gl.glPushMatrix();

        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        spritePack[index].bind(gl);
        spritePack[index].enable(gl);

        gl.glColor4f(0f,0f,0f,1f);

        gl.glBegin(GL2.GL_POLYGON);
        gl.glTexCoord2f(0f, 0.5f);
        gl.glVertex2f(v1X, v1Y);
        gl.glTexCoord2f(0.25f, 0.5f);
        gl.glVertex2f(v2X, v2Y);
        gl.glTexCoord2f(0.25f, 1f);
        gl.glVertex2f(v3X, v3Y);
        gl.glTexCoord2f(0.75f, 1f);
        gl.glVertex2f(v4X, v4Y);
        gl.glTexCoord2f(0.75f, 0.5f);
        gl.glVertex2f(v5X, v5Y);
        gl.glTexCoord2f(1f, 0.5f);
        gl.glVertex2f(v6X, v6Y);
        gl.glTexCoord2f(1f, 0f);
        gl.glVertex2f(v7X, v7Y);
        gl.glTexCoord2f(0f, 0f);
        gl.glVertex2f(v8X, v8Y);

        gl.glEnd();

        spritePack[index].disable(gl);

        gl.glDisable(GL2.GL_BLEND);
        gl.glPopMatrix();
    }

}