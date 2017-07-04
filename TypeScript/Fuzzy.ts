class Fuzzy {
    static _INFINITY: number = Number.MAX_VALUE;

    constructor() {}

    static min(l1: number, l2: number): number {
        if (l1 < l2) {
            return l1;
        } else {
            return l2;
        }
    }

    static minList(l: Array<number>): number {
        let m: number = Fuzzy._INFINITY;
        for (var i in l) {
            if (m > +i) {
                m = +i;
            }
        }
        return m;
    }

    static max(l1: number, l2: number): number {
        if (l1 > l2) {
            return l1;
        } else {
            return l2;
        }
    }

    static maxList(l: Array<number>): number {
        let m: number = 0;
        for (var i in l) {
            if (m < +i) {
                m = +i;
            }
        }
        return m;
    }

    static equals(l1: Array<number>, l2: Array<number>): Array<number> {
        let resp: Array<number>;
        for (let i = 0; i < l1.length; i++) {
            if(l1[i] == l2[i]) {
                resp.push(l1[i]);
            }
        }
        return resp;
    }

    static complement(l: Array<number>): Array<number> {
        let resp: Array<number>;
        for (let i = 0; i < l.length; i++) {
            resp.push(1 - l[i]);
        }
        return resp;
    }

    static isEmpty(l: Array<number>): boolean {
        if(l == null || l.length == 0) {
            return true;
        }
        return false;
    }
    
    static union(l1: Array<number>, l2: Array<number>): Array<number> {
        let resp: Array<number>;
        for (let i = 0; i < l1.length; i++) {
            resp.push( this.max(l1[i], l2[i]) );
        }
        return resp;
    }

    static intersection(l1: Array<number>, l2: Array<number>): Array<number> {
        let resp: Array<number>;
        for (let i = 0; i < l1.length; i++) {
            resp.push( this.min(l1[i], l2[i]) );
        }        
        return resp;
    }

    static subconjunct(l1: Array<number>, l2: Array<number>): Array<number> {
        let _l1 = l1;
        let _l2 = l2;
        let resp: Array<number>;
        for (let i = 0; i < _l1.length; i++) {
            for (let j = 0; j < _l2.length; j++) {
                if(_l1[i] == _l2[j]) {
                    resp.push(_l1[i]);
                    _l2 = _l2.splice(j, 1);
                }
            }
        }
        return resp;
    }

    static trunc(l: number): number {
        if ( l > 1 ) {
            return 1;
        } else if ( l < 1 ) {
            return 0;
        } else {
            return l;
        }
    }    

    static truncList(l: Array<number>): Array<number> {
        let resp: Array<number>;
        for (let i = 0; i < l.length; i++) {
            if ( l[i] > 1 ) {
                resp.push(1);
            } else if ( l[i] < 0 ) {
                resp.push(0);
            } else {
                resp.push(l[i]);
            }
        }
        return resp;
    }
}