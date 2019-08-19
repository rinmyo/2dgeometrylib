package com.testAera;

/*

 */
public class Circle implements Area {
    private double radius; //半径
    private Point center; //圆心

    public double getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public Circle(double radius, Point center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public Point getMinimalDiagonalPointOfMinimalCoverableRectangular() {
        return new Point(center.getX()-radius, center.getY()-radius);
    }

    @Override
    public Point getMaximumDiagonalPointOfMinimalCoverableRectangular() {
        return new Point(center.getX()+radius, center.getY()+radius);
    }

    /**
     * 获取边界的离散点
     *
     * @param interval 弧长
     * @return 有序点集
     */
    public Point[] getDiscretePoints(double interval) {
        double del_rad = interval/radius;  //间隔角度
        Point[] points = new Point[(int) (2*Math.PI/(del_rad))];  //舍弃小数 todo: 尝试四舍六入五留双能否更精确
        for (int i = 0; i < points.length; ++i){
 //           System.out.println(Math.toDegrees(i * del_rad));
            points[i] = center.toFreeVector().add(new FreeVector(radius, i * del_rad,true)).toFreeVector().toPoint();
        }
        return points;
    }

    /**
     * 移动一定的距离
     *
     * @param vector 移动向量
     */
    @Override
    public void move(Vector vector) {
        center = center.moveAlong(vector);
    }

    /**
     *半径扩大
     * @param delta_r 扩大多少
     */
    public void expand(double delta_r){
        radius+=delta_r;
    }
}
