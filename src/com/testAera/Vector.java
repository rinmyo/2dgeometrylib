package com.testAera;

/**
 * 向量类，不规则区域的边用向量来描述
 *  两个坐标定义一个向量
 */
public class Vector {
    private Point start;
    private Point end;

    Vector(Point start,Point end){
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
    public boolean isLeftThan(Point point){
        if (this.toFreeVector().getY() > 0){
            return !(this.getCrossProduct(new Vector(this.start, point)) > 0);
        }else
            return this.getCrossProduct(new Vector(this.start, point)) > 0;
    }

    /**
     * 向量积异号则不交叉
     * @param vector 第二个向量
     * @return 交叉
     */
    public boolean isIntersectWith(Vector vector){
         return (this.getCrossProduct(new Vector(this.start,vector.start)) > 0) ^ (this.getCrossProduct(new Vector(this.start,vector.end)) > 0);
    }

    /**
     * @return 将矢量标准化为从原点出发的自由向量
     */
    FreeVector toFreeVector(){
        return new FreeVector(this.end.getX()-this.getStart().getX(), this.end.getY()-this.getStart().getY());
    }

    /**
     * 二维向量求矢量积
     * @param vector 另一个向量
     * @return 矢积
     */
    private double getCrossProduct(Vector vector){
        return this.toFreeVector().getX() * vector.toFreeVector().getY() - vector.toFreeVector().getX() * this.toFreeVector().getY();
    }

    /**
     * 向量的数乘
     * @param scalar 倍数
     * @return 结果向量
     */
    Vector getScalarMultiplication(double scalar){
        return this.toFreeVector().getScalarMultiplication(scalar).toVectorFrom(this.start);
    }

    /**
     * 求向量和
     * @param vector 另一个向量
     * @return 向量和
     */
    Vector add(Vector vector){
        return this.start.getVectorTo(vector.toFreeVector().toVectorFrom(this.getEnd()).getEnd());
    }

    /**
     * 得到指向制定点的法向量
     * @param point 给定点
     * @return 法向量
     */
    public Vector getNormalVector(Point point){
            FreeVector freeVector = new FreeVector(point.distanceTo(this.start) * Math.cos(this.toFreeVector().getT() - point.getVectorFrom(this.start).toFreeVector().getT()), this.toFreeVector().getT(),true);  //用标准化坐标系描述向量
            return new Point(this.start.getX() + freeVector.getX(), this.start.getY() + freeVector.getY()).getVectorTo(point);  //坐标变换回原坐标，并做向量到指定点
    }

    /**
     *
     * @return 模长
     */
    public double getMagnitude(){
        return this.start.distanceTo(this.end);
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
