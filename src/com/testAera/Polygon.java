package com.testAera;

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

