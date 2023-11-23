package Components;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textures {
    private String[] textureFiles = new String[]{
            "src/Assets/Textures/Space01.jpg",
            "src/Assets/Textures/Stone01.jpg",
            "src/Assets/Textures/Stone02.jpg",
            "src/Assets/Textures/Wood01.jpg",
            "src/Assets/Textures/Wood02.jpg",
            "src/Assets/Textures/Wood03.jpg"
    };
    public Texture[] texturePack = new Texture[6];

    public void init(GL2 gl){

        for (int i = 0; i < this.textureFiles.length; i++){
            try {
                TextureData textureData = TextureIO.newTextureData(GLProfile.getDefault(), new File(this.textureFiles[i]), false, "jpg");
                this.texturePack[i] = TextureIO.newTexture(textureData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void generateTexture(GL2 gl,int texture,int filter, int wrap, int mode, int generateMode){
        //int genModo = GL2.GL_OBJECT_LINEAR; //GL.GL_EYE_LINEAR ou GL_OBJECT_LINEAR ou GL_SPHERE_MAP
        int genModo = gl.GL_SPHERE_MAP;

        float planoS[] = {1.0f, 0.0f, 0.0f, 0.0f};
        float planoT[] = {0.0f, 0.0f, 1.0f, 0.0f};

        this.texturePack[texture].bind(gl);
        this.texturePack[texture].enable(gl);

        this.texturePack[texture].setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER,filter);
        this.texturePack[texture].setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER,filter);
        this.texturePack[texture].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, wrap);
        this.texturePack[texture].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, wrap);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, mode);

        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, generateMode);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, generateMode);

        gl.glTexGenfv(GL2.GL_S, GL2.GL_OBJECT_PLANE, planoS, 0);
        gl.glTexGenfv(GL2.GL_T, GL2.GL_OBJECT_PLANE, planoT, 0);
    }


    public void removeTexture(GL2 gl){
        
    }
}