class Fuzzy {
    static _INFINITY: number = Number.MAX_VALUE;

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

    max(l1: number, l2: number): number {
        if (l1 > l2) {
            return l1;
        } else {
            return l2;
        }
    }

    maxList(l: Array<number>): number {
        let m: number = 0;
        for (var i in l) {
            if (m < +i) {
                m = +i;
            }
        }
        return m;
    }

    equals(l1: Array<number>, l2: Array<number>): Array<number> {
        let resp: Array<number>;
        for (let i = 0; i < l1.length; i++) {
            if(l1[i] == l2[i]) {
                resp.push(l1[i]);
            }
        }
        return resp;
    }

    complement(l: Array<number>): Array<number> {
        let resp: Array<number>;
        for (let i = 0; i < l.length; i++) {
            resp.push(1 - l[i]);
        }
        return resp;
    }

    
}