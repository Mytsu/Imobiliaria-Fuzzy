public class Linha {

    private float a,b;

    public Linha(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public float fuzzyfy(float x) {
        return (this.a * x) + this.b;
    }
}