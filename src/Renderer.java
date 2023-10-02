import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

public class Renderer {

    private static GLWindow window = null;
    public static int screenWidth = 640;  //1280
    public static int screenHeight = 480; //960

    public static void init(){        
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);        
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);

        Cena cena = new Cena();

        window.addGLEventListener(cena);
        window.addKeyListener(new KeyBoard(cena));
        window.addMouseListener(new Mouse(cena));
        window.requestFocus();
        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start();
        
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });

        window.setPointerVisible(false);
        //window.setResizable(false);
        window.setVisible(true);
    }
  
    public static void main(String[] args) {
        init();
    }
}
