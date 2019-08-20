package com.testAera;

import com.testAera.Exception.AreaTypeException;

import java.util.*;

/**
 * 本类将依次录入的坐标点转化为首尾相连的向量，终而形成闭环，以描述封闭图形
 */
public class Polygon implements Area{
    private Point[] points;
    private Point min =null;
    private Point max = null;

    public Polygon(Point[] points) {
        this.points = points;
    }

    /**
     * 得到向量集
     * @return 向量列表
     */
    public LinkedList<Vector> getVectors() {
        LinkedList<Vector> vectors = new LinkedList<>();
        Point pointer = null; //尾指针

        for (Point p: points
             ) {
            if (pointer == null){
                pointer = p;
                continue;
            }
            vectors.add(new Vector(pointer, p));
            pointer = p;
            }

        vectors.add(new Vector(points[points.length-1], points[0]));  //连接首尾
        return vectors;
    }

    public Point[] getPoints() {
        return points;
    }

    @Override
    public Point getMinimalDiagonalPointOfMinimalCoverableRectangular(){
        if (min == null){
            min = new Point(points[0].getX(), points[0].getY());
            for (Point p: points) {
                if (p.getX() < min.getX() ) min.setX(p.getX());
                if (p.getY() < min.getY() ) min.setY(p.getY());
            }
        }
        return min;
    }

    @Override
    public Point getMaximumDiagonalPointOfMinimalCoverableRectangular() {
        if (max == null){
            max = new Point(points[0].getX(), points[0].getY());
            for (Point p: points) {
                if (p.getX() > max.getX() ) max.setX(p.getX());
                if (p.getY() > max.getY() ) max.setY(p.getY());
            }
        }
        return max;
    }

    /**
     * 获取边界的离散点
     *
     * @param interval 离散点间隔
     * @return 离散点数组
     */
    public Point[] getDiscretePoints(double interval) {
        LinkedList<Point> list = new LinkedList<Point>();
        int i = 0;
        for (Vector v: getVectors()
             ) {
            list.addAll(Arrays.asList(v.getDiscretePoints(interval)));
        }
        Point[] points = new Point[list.size()];
        list.toArray(points);
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
        if (isOuterMinRectangular(point)) return false; //不在矩形内直接返回 false

        int count = 0; //用于记录穿越边界的次数
        for (Vector vector : this.getVectors()
        ) {
            if (
                    !vector.isXDirection()  //排除X方向上的向量
                            && !vector.isPointRight(point)
                            && (
                            (point.getY() >= vector.getStart().getY()) ^ (point.getY() >= vector.getEnd().getY())  // 应该注意当Yp == Ye 的情况，只得记一次
                    )
            ) {
                count++; //计数加一
//                   System.out.println("穿越了纵向边界向量 "+vector+"   第 "+count+" 次穿越边界 \n");
            }
        }
        //                System.out.println("共 奇数 次穿越了边界");
        //                System.out.println("共 偶数 次穿越了边界");
        return count % 2 != 0;
    }

    /**
     * 是否与另一个区域相交
     *
     * @param area 另一个区域
     * @return 布尔
     */
    @Override
    public boolean isIntersectWithArea(Area area) throws AreaTypeException {
        if (isMinRectangularApart(area)) return false; //如果矩形不相交或包含直接返回否

        //多边形*圆
        if (area instanceof Circle){
            for (Vector v: getVectors()
            ) {
                if (area.isIncludePoint(v.getStart()) ^ area.isIncludePoint(v.getEnd())) return true;  //遍历所有向量，只要穿过则说明相交
            }
            return false;
        }

        //多边形*多边形
        if (area instanceof Polygon){
            for (Vector v1: this.getVectors()
            ) {
                for (Vector v2: ((Polygon)area).getVectors()
                ) {
                    if (v1.isIntersectWith(v2))
                        return true;
                }
            }
            return false;
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
                if (!this.isIncludePoint(p)) return false;
            }
            return true;
        }

        if (area instanceof Circle){
            if (!this.isIncludePoint(((Circle)area).getCenter())) return false; //如果不包含圆心，更不可能包含整个圆
            for (Point p: this.getPoints()
                 ) {
                if (area.isIncludePoint(p)) return false;
            }
            return true;
        }

        throw new AreaTypeException();
    }

    /**
     * 移动一定的距离
     * @param vector 某向量
     */
    @Override
    public void move(Vector vector) {
        for (int i=0; i<points.length; ++i){
            points[i] = points[i].moveAlong(vector);
        }
    }
}

