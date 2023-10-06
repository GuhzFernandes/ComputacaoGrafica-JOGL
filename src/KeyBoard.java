import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key "+e.getKeyChar()+", code: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_F11){
            cena.fullscreen = !cena.fullscreen;
        }
        if(e.getKeyCode() == 96){
            cena.frame = 0;
        }
        if(e.getKeyCode() == 9){

        }

    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
