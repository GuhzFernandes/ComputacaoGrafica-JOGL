import Components.Button2D;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;


public class Mouse implements MouseListener {

    private Cena cena;
    private float[] mousePosition;
    private float[] draggerX = new float[2];
    private float[] draggerY = new float[2];

    public Mouse(Cena cena){
        this.cena = cena;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        cena.tools.cursorX = mousePosition[0];
        cena.tools.cursorY = mousePosition[1];
        System.out.println(mousePosition[0] + "  X  " +mousePosition[1]);
        switch (cena.frame){
            case 0:
                int nextFrame = 0;
                for (Button2D button: cena.menu.buttons) {
                    nextFrame++;
                    if((mousePosition[0]>=button.frameVertex[0] && mousePosition[0]<=button.frameVertex[1])
                    && mousePosition[1]>=button.frameVertex[3] && mousePosition[1] <= button.frameVertex[2])
                    {
                        cena.frame = nextFrame;
                    }
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
        cena.tools.cursorX = mousePosition[0];
        cena.tools.cursorY = mousePosition[1];
        switch (cena.frame){
            case 0:
                for (Button2D button: cena.menu.buttons) {
                    if((mousePosition[0]>=button.frameVertex[0] && mousePosition[0]<=button.frameVertex[1])
                            && mousePosition[1]>=button.frameVertex[3] && mousePosition[1] <= button.frameVertex[2])
                    {
                        button.select = true;
                    }
                    else {
                        button.select = false;
                    }
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
        draggerX[1] = mousePosition[0];
        draggerY[1] = mousePosition[1];
        mousePosition = calMousePosition(e.getX(),e.getY());
        draggerX[0] = mousePosition[0];
        draggerY[0] = mousePosition[1];

        cena.tools.axisControl[0] += draggerX[0]-draggerX[1];
        cena.tools.axisControl[1] += draggerY[0]-draggerY[1];

        System.out.println("Movimentação" +
                "\nX: "+ cena.tools.axisControl[0]+
                "\nY: "+ cena.tools.axisControl[1]+
                "\nZoom: "+ cena.tools.axisControl[2]);
    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {
        if(e.getRotation()[1]<0){
            System.out.println("Mouse wheel moved down");
            cena.tools.axisControl[2] -= 0.2f;
            if( cena.tools.axisControl[2] <= 1 &&  cena.tools.axisControl[2] > 0 ){
                cena.tools.axisControl[2] = 0;
            }
            if ( cena.tools.axisControl[2] < 0 &&  cena.tools.axisControl[2] > -1) {
                cena.tools.axisControl[2] = -1;
            }
        }
        else{
            System.out.println("Mouse wheel moved up");
            cena.tools.axisControl[2] += 0.2f;
            if( cena.tools.axisControl[2] >= -1 &&  cena.tools.axisControl[2] < 0 ){
                cena.tools.axisControl[2] = 0;
            }
            if ( cena.tools.axisControl[2] > 0 &&  cena.tools.axisControl[2] < 1) {
                cena.tools.axisControl[2] = 1;
            }
        }
        System.out.println("Movimentação" +
                "\nX: "+ cena.tools.axisControl[0]+
                "\nY: "+ cena.tools.axisControl[1]+
                "\nZoom: "+ cena.tools.axisControl[2]);
    }

    public float[] calMousePosition(int x, int y){
        float[] position = new float[2];
        if (x>=  cena.tools.currentResolution[0] *0.5f){position[0] = x-( cena.tools.currentResolution[0] *0.5f);}
        else{position[0] = x - ( cena.tools.currentResolution[0] *0.5f);};
        if (y>=  cena.tools.currentResolution[1] *0.5f){position[1] = ( cena.tools.currentResolution[1] *0.5f) - y;}
        else{position[1] = (y - ( cena.tools.currentResolution[1] *0.5f))*-1;};
        return position;
    }

}