package com.testAera;

import com.testAera.Exception.AreaTypeException;

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
    @Override
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
     * 是否包含一点
     *
     * @param point 一点
     * @return 布尔
     */
    @Override
    public boolean isIncludePoint(Point point) {
        return point.distanceTo(this.getCenter()) < this.getRadius();
    }

    /**
     * 是否与另一个区域相交
     *
     * @param area 另一个区域
     * @return 布尔
     */
    @Override
    public boolean isIntersectWithArea(Area area) throws AreaTypeException{
        if (isMinRectangularApart(area)) return false; //如果矩形不相交或包含直接返回否

        //多边形*圆
        if (area instanceof Polygon){
            for (Vector v: ((Polygon)area).getVectors()
            ) {
                if (area.isIncludePoint(v.getStart()) ^ area.isIncludePoint(v.getEnd())) return true;  //遍历所有向量，只要穿过则说明相交
            }
            return false;
        }

        //yuan yuan
        if (area instanceof Circle){
            Circle circle = (Circle)area;
            return getCenter().distanceTo(circle.getCenter()) < getRadius() + circle.getRadius() && getCenter().distanceTo(circle.getCenter()) > Math.abs(getRadius() - circle.getRadius());
        }

        throw new AreaTypeException("error");
    }

    /**
     * 是否包围另一个区域
     *
     * @param area 另一个区域
     * @return 布尔
     */
    @Override
    public boolean isSurroundArea(Area area) throws AreaTypeException {
        if (!isMinRectangularSurround(area) || isIntersectWithArea(area)) return false;

        if (area instanceof Polygon){
            for (Point p: ((Polygon)area).getPoints()
                 ) {
                if (!this.isIncludePoint(p))return false;
            }
            return true;
        }

        if (area instanceof Circle){
            return getCenter().distanceTo(((Circle)area).getCenter()) < getRadius() - ((Circle)area).getRadius();
        }

        throw new AreaTypeException();
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
