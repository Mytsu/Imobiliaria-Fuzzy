public class Trapezio {
    
    private float a, b, c, d;


    public Trapezio(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }   
    
    public float fuzzyfy(float x) {
        if (x < this.a) {
            return 0;
        } else if (this.a <= x || x < this.b) {
            return (x - this.a) / (this.b - this.a);
        } else if (this.b <= x || x < this.c) {
            return 1;
        } else if (this.c <= x || x < this.d) {
               return (this.d - x) / (this.d - this.c);
        } else if (this.d <= x) {
            return 0;
        }
        return 0;
    }
}