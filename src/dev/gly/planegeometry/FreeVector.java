package dev.gly.planegeometry;

/**
 * 自由向量没有起点，在绝对标准坐标系中起点为零的一个向量
 */
class FreeVector extends Vector {

    FreeVector(double x, double y) {
        super(new Point(0,0), new Point(x, y));
    }

    FreeVector(Point point){
        this(point.getX(), point.getY());
    }

    FreeVector(double r, double t, boolean isPC){
        this(new Point(r,t,isPC));
    }

    void setX(double x) {
        this.getEnd().setX(x);
    }

    void setY(double y) {
        this.getEnd().setY(y);
    }

    double getX(){
        return getEnd().getX();
    }

    double getY(){
        return getEnd().getY();
    }

    double getR(){
        return getEnd().getR();
    }

    double getT(){
        return getEnd().getT();
    }

    public Point toPoint(){
        return this.getEnd();
    }

    /**
     * 求向量和
     *
     * @param vector 另一个向量
     * @return 向量和
     */
    @Override
    public FreeVector add(Vector vector) {
        return super.add(vector).toFreeVector();
    }

    /**
     * 取反向量
     * @return 反向量
     */
    @Override
    public FreeVector getReversedVector(){
        return new FreeVector(-getX(),-getY());
    }

    /**
     * 向量的数乘
     *
     * @param scalar 倍数
     * @return 结果向量
     */
    @Override
    public FreeVector getScalarMultiplication(double scalar) {
        return new FreeVector(getX()*scalar, getY()*scalar);
    }

    /**
     * 转化为起点给定的向量
     * @return 转化为
     */
    Vector toVectorFrom(Point point){
        return new Vector(point, new Point(point.getX()+this.getX(), point.getY()+this.getY()));
    }
}
