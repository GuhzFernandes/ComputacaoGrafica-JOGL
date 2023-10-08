package Components;

public class Button2D {
    public float[] frameColour = new float[4],
            frameOutLineColour = new float[4],
            frameVertex = new float[4];
    public int fontSize, fontOutLineSize;
    public int[] textPosition = new int[2];
    public String text;
    public boolean select;

    public Button2D(float[] frameColour,
                    float[] frameOutLineColour,
                    float[] frameVertex,
                    int fontSize,
                    int fontOutLineSize,
                    int[] textPosition,
                    String text,
                    boolean select)
    {
    this.frameColour = frameColour;
    this.frameOutLineColour = frameOutLineColour;
    this.frameVertex = frameVertex;
    this.fontSize = fontSize;
    this.fontOutLineSize = fontOutLineSize;
    this.textPosition = textPosition;
    this.text = text;
    this.select = select;
    }
}


