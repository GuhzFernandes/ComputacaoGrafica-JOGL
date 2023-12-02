package Components;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textures {
    private String[] textureFiles = new String[]{
            "src/Assets/Textures/Stone01.jpg",
            "src/Assets/Textures/Stone02.jpg",
            "src/Assets/Textures/Wood01.jpg",
            "src/Assets/Textures/Wood02.jpg",
            "src/Assets/Textures/Wood03.jpg"
    };

    private String[] spriteFiles = new String[]{
            "src/Assets/Sprites/GameCover.jpg",
            "src/Assets/Sprites/Space.jpg",
            "src/Assets/Sprites/Spaceship.png",
            "src/Assets/Sprites/OVNI.png"
    };

    public Texture[] texturePack = new Texture[textureFiles.length];
    public Texture[] spritePack = new Texture[spriteFiles.length];
    private float planoS[] = {1.0f, 0.0f, 0.0f, 0.0f};
    private float planoT[] = {0.0f, 0.0f, 1.0f, 0.0f};
    private int filter = GL2.GL_NEAREST;
    private int wrap = GL2.GL_REPEAT;
    private int mode = GL2.GL_DECAL;
    private int genMode = GL2.GL_SPHERE_MAP;

    public void init(GL2 gl){
        for (int i = 0; i < this.textureFiles.length; i++){
            //Carrega as texturas
            try {
                this.texturePack[i] = TextureIO.newTexture(new File(this.textureFiles[i]), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Gera as texturas
            this.texturePack[i].bind(gl);
            this.texturePack[i].enable(gl);
            this.texturePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER,filter);
            this.texturePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER,filter);
            this.texturePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, wrap);
            this.texturePack[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, wrap);
            gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, mode);
            //Habilita as configurações 3D
            generate(gl, texturePack[i]);
            //Gera Mipmap 2D
            gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
        }
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

    public void applyTexture(GL2 gl, int index){
        generate(gl, this.texturePack[index]);
    }

    public void applySpriteQuad(GL2 gl, int index, float v1X, float v1Y, float v2X, float v2Y, float v3X, float v3Y, float v4X, float v4Y) {
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        spritePack[index].bind(gl);
        spritePack[index].enable(gl);

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
    }
}