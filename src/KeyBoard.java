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

        //tab
        if(e.getKeyCode() == 9){
        }

        switch (cena.frame){
            case 0:
                break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                if(e.getKeyCode() == 32){
                    switch(cena.pong.gameState){
                        case 0:
                            cena.pong.gameState = 1;
                            break;
                        case 1:
                            cena.pong.gamePause = !cena.pong.gamePause;
                            break;
                        case 2:
                            cena.pong.gamePause = !cena.pong.gamePause;
                            break;
                        case 3:
                            cena.pong.gameState = 0;
                            break;
                    }
                }
                break;
        }





    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
