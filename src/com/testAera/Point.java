package com.testAera;

public class Point {
    private double x;
    private double y;

    /**
     *
     * @param r 直角坐标x 极坐标ρ
     * @param t 直角坐标y 极坐标θ
     * @param isPC 是否为极坐标，true则极坐标 false则直角坐标
     */
    Point(double r, double t, boolean isPC){
        if (isPC){
            this.x = r * Math.cos(t);
            this.y = r * Math.sin(t);
        }else {
            this.x = r;
            this.y = t;
        }
    }

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 设定直角坐标系横坐标
     * @param x 横坐标
     */
    void setX(double x) {
        this.x = x;
    }

    /**
     * 设定直角坐标系纵坐标
     * @param y 纵坐标
     */
    void setY(double y) {
        this.y = y;
    }

    /**
     * 获得直角坐标系横坐标
     * @return 横坐标
     */
    public double getX() {
        return x;
    }

    /**
     * 获得直角坐标系纵坐标
     * @return 纵坐标
     */
    public double getY() {
        return y;
    }

    /**
     * 获得极坐标系模长
     * @return 模长
     */
    public double getR(){
        return Math.sqrt(x*x+y*y);
    }

    /**
     * 获得极坐标系幅角
     * @return 幅角
     */
    public double getT(){
        return Math.atan(y/x);
    }


    /**
     * 得到一个从该点引出到参数点的向量
     * @param point 终点
     * @return 向量
     */
    public Vector getVectorTo(Point point){
        return new Vector(this, point);
    }

    /**
     * 得到一个从参数点指向该点的向量
     * @param point 起点
     * @return 向量
     */
    public Vector getVectorFrom(Point point){
        return new Vector(point, this);
    }

    /**
     * 两点距离
     * @param point 另一个点
     * @return 距离
     */
    public double distanceTo(Point point){
        double deltaX = this.x-point.x;
        double deltaY = this.y-point.y;
        return Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    }

    /**
     * 沿着一个向量移动一个点
     * @param vector 方向向量
     * @return 另一个点
     */
    public Point moveAlong(Vector vector){
        return (this.toFreeVector().add(vector)).toPoint();     //以绝对坐标作为相对坐标
    }

    /**
     * 点转为自由向量是将点作为终点
     * @return 由原点指向该点的自由向量
     */
    public FreeVector toFreeVector(){
        return new FreeVector(this.getX(),this.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point){
        Point point = (Point)obj;
        return point.getX() == this.getX() && point.getY() == this.getY();
        }else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        char[] charArr = sb.toString().toCharArray();
        int hash = 0;

        for(char c : charArr) {
            hash = hash * 131 + c;
        }
        return hash;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
