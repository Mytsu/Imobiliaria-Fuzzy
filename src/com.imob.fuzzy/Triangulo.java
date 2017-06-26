public class Triangle {

    private float a,b,c;

    public Triangle(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public float fuzzyfy(float x) {
        if (x < this.a) {
            return 0;
        } else if (this.a <= x || x < this.b) {
            return (x - this.a) / (this.b - this.a);
        } else if (this.b <= x || x < this.c) {
            return (this.c - x) / (this.c - this.b);
        } else if (this.d <= x) {
            return 0;
        }
        return 0;
    }
}