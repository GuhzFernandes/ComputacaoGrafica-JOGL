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
        //seleção botão 1
        if((mousePosition[0]>=cena.button1[0] && mousePosition[0]<=cena.button1[1]) && (mousePosition[1]>=cena.button1[3] && mousePosition[1]<=cena.button1[2]))
            cena.frame = 1;
        //selecão botão 2
        if((mousePosition[0]>=cena.button2[0] && mousePosition[0]<=cena.button2[1]) && (mousePosition[1]>=cena.button2[3] && mousePosition[1]<=cena.button2[2]))
            cena.frame = 2;
        // seleção botão 3
        if((mousePosition[0]>=cena.button3[0] && mousePosition[0]<=cena.button3[1]) && (mousePosition[1]>=cena.button3[3] && mousePosition[1]<=cena.button3[2]))
            cena.frame = 3;
        System.out.println("Click em X: " + mousePosition[0] + " Y: " + mousePosition[1]);
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
        switch (cena.frame){
            case 0:
                cena.cursorX = mousePosition[0];
                cena.cursorY = mousePosition[1];
                //seleção botão 1
                if((mousePosition[0]>=cena.button1[0] && mousePosition[0]<=cena.button1[1]) && (mousePosition[1]>=cena.button1[3] && mousePosition[1]<=cena.button1[2]))
                    cena.selectButton1 = true;
                else{
                    cena.selectButton1 = false;
                }
                //selecão botão 2
                if((mousePosition[0]>=cena.button2[0] && mousePosition[0]<=cena.button2[1]) && (mousePosition[1]>=cena.button2[3] && mousePosition[1]<=cena.button2[2]))
                    cena.selectButton2 = true;
                else{
                    cena.selectButton2 = false;
                }
                // seleção botão 3
                if((mousePosition[0]>=cena.button3[0] && mousePosition[0]<=cena.button3[1]) && (mousePosition[1]>=cena.button3[3] && mousePosition[1]<=cena.button3[2]))
                    cena.selectButton3 = true;
                else{
                    cena.selectButton3 = false;
                }

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
        float coefficientX = 200f/cena.currentWidth;
        float coefficientY = -200f/cena.currentHeight;
        position[0] = (x * coefficientX) - 100;
        position[1] = (y * coefficientY) + 100;
        return position;
    }

}