class Fuzzy {
    static _INFINITY: number = 900000;

    constructor() {}

    min(l1: number, l2: number): number {
        if (l1 < l2) {
            return l1;
        } else {
            return l2;
        }
    }

    minList(l: Array<number>): number {
        let m: number = Fuzzy._INFINITY;
        for (var i in l) {
            if (m > +i) {
                m = +i;
            }
        }
        return m;
    }
}