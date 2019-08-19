package com.testAera.judge;

import com.testAera.*;
import com.testAera.Exception.AreaTypeException;

/**
 * 本类用于判断两区域是否相交与包含
 */
public class JudgeAreaIntersectWithArea implements JudgeBase{
    private Area area1;  //区域一
    private Area area2;  //区域2

    public JudgeAreaIntersectWithArea(Area area1, Area area2) {
        this.area1 = area1;
        this.area2 = area2;
    }

    /**
     * 多边形-多边形 判断相交
     */
    private Judgement PolygonPolygonJudgement = () -> {
        Polygon polygon1 = (Polygon) area1;
        Polygon polygon2 = (Polygon) area2;

        for (Vector v1: polygon1.getVectors()
        ) {
            for (Vector v2: polygon2.getVectors()
                 ) {
                if (v1.isIntersectWith(v2)) return true;
            }
        }
        return false;
    };

    /**
     * 多边形-圆 判断
     */
    private Judgement PolygonCircleJudgement = () -> {
        Circle circle;
        Polygon polygon;
        if (area1 instanceof Polygon){
            polygon = (Polygon) area1;
            circle = (Circle) area2;
        }else {
            polygon = (Polygon) area2;
            circle = (Circle) area1;
        }

        for (Vector v: polygon.getVectors()
        ) {
//            System.out.println(v.getNormalVector(circle.getCenter()).getDotProduct(v));
            if (v.getNormalVector(circle.getCenter()).getMagnitude() < circle.getRadius()) return true;
        }

        return false;
    };

    /**
     * 圆-圆 判断
     */
    private Judgement CircleCircleJudgement = () -> {
        Circle circle1 = (Circle) area1;
        Circle circle2 = (Circle) area2;

        return circle1.getCenter().distanceTo(circle2.getCenter()) < circle1.getRadius() + circle2.getRadius();
    };

    /**
     * 判断最小覆盖矩形是否相交
     * @return 两个区域的最小可覆盖矩形是否相交
     */
    private boolean isMinRectangularIntersect(){
        return Math.abs(area2.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - area1.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX()) < area1.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - area1.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() + area2.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - area2.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() && Math.abs(area2.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - area1.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY()) < area1.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - area1.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() + area2.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - area2.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY();
    }

    @Override
    public boolean getJudgement() throws AreaTypeException {
        if (!isMinRectangularIntersect()) return false; //如果矩形不相交或包含直接返回否

        if (area1 instanceof Polygon && area2 instanceof Polygon) return PolygonPolygonJudgement.judge();
        if (area1 instanceof Circle && area2 instanceof Circle) return CircleCircleJudgement.judge();
        if (area1 instanceof Polygon && area2 instanceof Circle || area1 instanceof Circle && area2 instanceof Polygon) return PolygonCircleJudgement.judge();
        throw new AreaTypeException("error");
    }
}
