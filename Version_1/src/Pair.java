public class Pair<L,R> {
    private L l;
    private R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
    public boolean equals_float(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        Pair<Float,Float> p1 = new Pair<>((Float) l,(Float) r);
        Pair<Float,Float> p2 = new Pair((Float) pairo.l,(Float) pairo.r);
        float a1 = Math.abs(p1.getL()-p2.getL());
        float a2 = Math.abs(p1.getR()-p2.getR());
        return a1<=3&&a2<=3;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        Pair<Integer,Integer> p1 = new Pair<>((Integer) l,(Integer) r);
        Pair<Integer,Integer> p2 = new Pair((Integer) pairo.l,(Integer) pairo.r);
//        p1.setL((Integer) l);
//        p1.setR((Integer) r);
//        p2.setL((Integer) pairo.l);
//        p2.setR((Integer) pairo.r);
//        System.out.println(p1.l+","+p1.r+","+p2.l+","+p2.r+",");
        int a1 = Math.abs(p1.getL()-p2.getL());
        int a2 = Math.abs(p1.getR()-p2.getR());
        return a1<=3&&a2<=3;
//        return this.l.equals(pairo.getL()) &&
//               this.r.equals(pairo.getR());
    }
}