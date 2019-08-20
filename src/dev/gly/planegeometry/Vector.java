package dev.gly.planegeometry;

/**
 * 向量类，不规则区域的边用向量来描述
 *  两个坐标定义一个向量
 */
public class Vector {
    private Point start;
    private Point end;

    public Vector(Point start,Point end){
        this.start = start;
        this.end = end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    /**
     * 取反向的向量
     */
    public Vector getReversedVector(){
        return new Vector(this.end, this.start);
    }

    /**
     *
     * 判定向量是否与X轴平行
     */
    public boolean isXDirection(){
        return this.getStart().getY() == this.getEnd().getY();
    }

    /**
     * 利用矢积计算向量是否位于点的左边,
     * @param point 一点
     * @return true则说明向量在点的左边，反之则右
     */
    public boolean isPointRight(Point point){
        if (this.toFreeVector().getY() > 0){
            return isCW(point);
        }else
            return !isCW(point);
    }

    /**
     * 某点是否在向量的顺时针半周
     * @param point 某点
     * @return 某点
     */
    public boolean isCW(Point point){
        return this.getCrossProduct(new Vector(this.start, point)) < 0;
    }

    public boolean isCollinear(Point point){
        return point.getVectorTo(start).toFreeVector().getY() * point.getVectorTo(end).toFreeVector().getX() == point.getVectorTo(start).toFreeVector().getX() * point.getVectorTo(end).toFreeVector().getY();
    }

    public boolean isCollinear(Vector vector){
        return this.isCollinear(vector.start) && this.isCollinear(vector.end);
    }

    /**
     * 向量积异号则不交叉
     * @param vector 第二个向量
     * @return 交叉
     */
    public boolean isIntersectWith(Vector vector){
         return this.isCW(vector.getStart()) ^ this.isCW(vector.getEnd()) && vector.isCW(this.getStart()) ^ vector.isCW(this.getEnd());
    }

    /**
     * @return 将矢量标准化为从原点出发的自由向量
     */
    public FreeVector toFreeVector(){
        return new FreeVector(this.end.getX()-this.getStart().getX(), this.end.getY()-this.getStart().getY());
    }

    /**
     * 二维向量求矢量积
     * @param vector 另一个向量
     * @return 矢积
     */
    public double getCrossProduct(Vector vector){
        return this.toFreeVector().getX() * vector.toFreeVector().getY() - vector.toFreeVector().getX() * this.toFreeVector().getY();
    }

    /**
     * 取数量积
     * @param vector 另一个向量
     * @return 点积
     */
    public double getDotProduct(Vector vector){
        return this.toFreeVector().getX() * vector.toFreeVector().getX() + this.toFreeVector().getY() * vector.toFreeVector().getY();
    }
    /**
     * 向量的数乘
     * @param scalar 倍数
     * @return 结果向量
     */
    public Vector getScalarMultiplication(double scalar){
        return this.toFreeVector().getScalarMultiplication(scalar).toVectorFrom(this.start);
    }

    /**
     * 求向量和
     * @param vector 另一个向量
     * @return 向量和
     */
    public Vector add(Vector vector){
        return this.start.getVectorTo(vector.toFreeVector().toVectorFrom(this.getEnd()).getEnd());
    }

    /**
     * 得到指向制定点的法向量
     * @param point 给定点
     * @return 法向量
     */
    public Vector getNormalVector(Point point){
        if (this.isCollinear(point))return new Vector(point, point); //如果共线，则直接返回point本身的零向量

        Vector hypotenuse = point.getVectorFrom(point.distanceTo(start) < point.distanceTo(end) ? this.start : this.end); //弦向量, 选择小三角形的原因：位数一定，数据越小精度越高
        Vector leg = new FreeVector(
                hypotenuse.getMagnitude() * Math.abs(Math.cos(this.getAngle() - hypotenuse.getAngle())),
                (point.distanceTo(start) < point.distanceTo(end) ? this.getDotProduct(hypotenuse) > 0 : this.getDotProduct(hypotenuse) < 0) ? this.getAngle() : this.getReversedVector().getAngle(),
                true)
                .toVectorFrom(hypotenuse.start)
                .getReversedVector();
        // 点积为正说明夹角小于90deg
        return leg.add(hypotenuse) ;
    }

    /**
     *取模长
     * @return 模长
     */
    public double getMagnitude(){
        return this.start.distanceTo(this.end);
    }

    /**
     * 取相角/幅角
     * @return 相角
     */
    public double getAngle(){
        return this.toFreeVector().getT();
    }

    /**
     *分点
     * @param interval 间隔
     * @return 点集
     */
    public Point[] getDiscretePoints(double interval) {
        Point[] points = new Point[(int) (getMagnitude()/interval)];

        for (int i=0; i<points.length; i++){
            points[i] = this.getScalarMultiplication(i * interval / getMagnitude()).getEnd();
        }

        return points;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector){
            Vector vector = (Vector)obj;
            return this.getStart().equals(vector.getStart()) && this.getEnd().equals(vector.getEnd());
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
        String s = "";
        String e = "";
        if (getStart() != null) s = getStart().toString();
        if (getEnd() != null) e = getEnd().toString();
        return s+"->"+e;
    }
}
