package Components;

public class Tittle {
    public int[] textPosition = new int[2];
    public float[] colour = new float[2],
            outLineColour = new float[2];
    public int fontSize,
            fontOutLineSize;
    public String text;

    public Tittle(int[] textPosition,
                  float[] colour,
                  float[] outLineColour,
                  int fontSize,
                  int fontOutLineSize,
                  String text)
    {
        this.textPosition = textPosition;
        this.colour = colour;
        this.outLineColour = outLineColour;
        this.fontSize = fontSize;
        this.fontOutLineSize = fontOutLineSize;
        this.text = text;
    }
}
