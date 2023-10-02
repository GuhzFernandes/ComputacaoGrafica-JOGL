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
        mousePosition = calMousePosition(e.getX(), e.getY());
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

                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        mousePosition = calMousePosition(e.getX(), e.getY());
        cena.pointX = mousePosition[0];
        cena.pointY = mousePosition[1];
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