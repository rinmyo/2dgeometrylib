package com.testAera.judge;

import com.testAera.Exception.AreaTypeException;

import com.testAera.*;

public class JudgePointInArea implements JudgeBase {

    private Point point;
    private Area area;

    public JudgePointInArea(Point point, Area area){
        this.point = point;
        this.area = area;
    }

    /**
     *  多边形-点 判断
     */
    private Judgement PolygonPointJudgement = () -> {
        int count = 0; //用于记录穿越边界的次数
        for (Vector vector : ((Polygon)area).getVectors()
        ) {
            if (
                    !vector.isXDirection()  //排除X方向上的向量
                            && !vector.isPointRight(point)
                            && (
                            vector.getStart().getY() > point.getY() && vector.getEnd().getY() < point.getY() ||
                                    vector.getStart().getY() < point.getY() && vector.getEnd().getY() > point.getY()
                    )
            ) {
                count++; //计数加一
//                   System.out.println("穿越了纵向边界向量 "+vector+"   第 "+count+" 次穿越边界 \n");
            }
        }
        //                System.out.println("共 奇数 次穿越了边界");
        //                System.out.println("共 偶数 次穿越了边界");
        return count % 2 != 0;
    };

    /**
     *  圆-点 判断
     */
    private Judgement CirclePointJudgement = () -> point.distanceTo(((Circle) area).getCenter()) < ((Circle) area).getRadius();


    /**
     * 判断点是否处在可覆盖区域所有面积的最小矩形区域中
     * @return 是否在最小矩形内
     */
    private boolean isInnerMinRectangular() {
        return point.getX() > area.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() && point.getX() < area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() && point.getY() > area.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() && point.getY() < area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY();
    }

    @Override
    public boolean getJudgement() throws AreaTypeException{
        if (!isInnerMinRectangular()) return false; //不在矩形内直接返回 false

        if (area instanceof Polygon) return PolygonPointJudgement.judge();
        if (area instanceof Circle) return CirclePointJudgement.judge();
        throw new AreaTypeException("wrong Area Type :"+ area.getClass().getName());
    }
}
