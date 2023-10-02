import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }

        if(e.getKeyChar() == 'a') {
            System.out.println("Pressionou tecla a");
        }

        if(e.getKeyCode() == KeyEvent.VK_F11){
            System.out.println("f11 apertado");
            cena.fullscreen = !cena.fullscreen;
            System.out.println(cena.fullscreen);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
