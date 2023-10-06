import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;


public class Mouse implements MouseListener {

    private Cena cena;
    private float[] mousePosition;

    public Mouse(Cena cena){
        this.cena = cena;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        cena.cursorX = mousePosition[0];
        cena.cursorY = mousePosition[1];
        System.out.println("Click em X: " + mousePosition[0] + " Y: " + mousePosition[1]);
        switch (cena.frame){
            case 0:
                //seleção botão 1
                if((mousePosition[0]>=cena.menuButton1[0] && mousePosition[0]<=cena.menuButton1[1]) && (mousePosition[1]>=cena.menuButton1[3] && mousePosition[1]<=cena.menuButton1[2]))
                    cena.frame = 1;
                //selecão botão 2
                if((mousePosition[0]>=cena.menuButton2[0] && mousePosition[0]<=cena.menuButton2[1]) && (mousePosition[1]>=cena.menuButton2[3] && mousePosition[1]<=cena.menuButton2[2]))
                    cena.frame = 2;
                // seleção botão 3
                if((mousePosition[0]>=cena.menuButton3[0] && mousePosition[0]<=cena.menuButton3[1]) && (mousePosition[1]>=cena.menuButton3[3] && mousePosition[1]<=cena.menuButton3[2]))
                    cena.frame = 3;
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = calMousePosition(e.getX(), e.getY());
        cena.cursorX = mousePosition[0];
        cena.cursorY = mousePosition[1];
        switch (cena.frame){
            case 0:
                //seleção botão 1
                if ((mousePosition[0] >= cena.menuButton1[0] && mousePosition[0] <= cena.menuButton1[1]) && (mousePosition[1] >= cena.menuButton1[3] && mousePosition[1] <= cena.menuButton1[2])) {cena.selectMenuButton1 = true;}
                else{cena.selectMenuButton1 = false;}
                //selecão botão 2
                if ((mousePosition[0] >= cena.menuButton2[0] && mousePosition[0] <= cena.menuButton2[1]) && (mousePosition[1] >= cena.menuButton2[3] && mousePosition[1] <= cena.menuButton2[2])) {cena.selectMenuButton2 = true;}
                else {cena.selectMenuButton2 = false;}
                // seleção botão 3
                if ((mousePosition[0] >= cena.menuButton3[0] && mousePosition[0] <= cena.menuButton3[1]) && (mousePosition[1] >= cena.menuButton3[3] && mousePosition[1] <= cena.menuButton3[2])) {cena.selectMenuButton3 = true;}
                else{cena.selectMenuButton3 = false;}
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = calMousePosition(e.getX(),e.getY());
        System.out.println("Mouse Dragged: X: " + mousePosition[0]+" - Y: "+mousePosition[1]);
    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {
        if(e.getRotation()[1]>0){
            System.out.println("Mouse wheel moved up");
        }
        else{
            System.out.println("Mouse wheel moved down");
        }
    }

    public float[] calMousePosition(int x, int y){
        float[] position = new float[2];
        if (x>= cena.currentWidth*0.5f){position[0] = x-(cena.currentWidth*0.5f);}
        else{position[0] = x - (cena.currentWidth*0.5f);};
        if (y>= cena.currentHeight*0.5f){position[1] = (cena.currentHeight*0.5f) - y;}
        else{position[1] = (y - (cena.currentHeight*0.5f))*-1;};
        return position;
    }

}