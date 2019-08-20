package com.testAera;

import com.testAera.Exception.AreaTypeException;

public interface Area {
    /**
     * 获得最小覆盖矩形的最小对角点
     * @return 最小对角点
     */
    Point getMinimalDiagonalPointOfMinimalCoverableRectangular();

    /**
     * 获得最小覆盖矩形的最大对角点
     * @return 最大对角点
     */
    Point getMaximumDiagonalPointOfMinimalCoverableRectangular();

    /**
     * 沿着某一向量移动
     * @param vector 某一向量
     * */
    void move(Vector vector);

    /**
     * 获取边界的离散点
     * @param interval 距离
     * @return 二维数组
     */
    public Point[] getDiscretePoints(double interval);

    /**
     * 是否包含一点
     * @param point 一点
     * @return 布尔
     */
    boolean isIncludePoint(Point point);

    /**
     * 是否与另一个区域相交
     * @param area 另一个区域
     * @return 布尔
     */
    boolean isIntersectWithArea(Area area) throws AreaTypeException;

    /**
     * 是否包围另一个区域
     * @param area 另一个区域
     * @return 布尔
     */
    boolean isSurroundArea(Area area) throws AreaTypeException;

    /**
     * 判断点是否处在可覆盖区域所有面积的最小矩形区域中
     * @return 是否在最小矩形内
     */
    default boolean isOuterMinRectangular(Point point) {
        return point.getX() < this.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() || point.getX() > this.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() || point.getY() < this.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() || point.getY() > this.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY();
    }

    /**
     * 判断最小覆盖矩形是否相离
     * @return 两个区域的最小可覆盖矩形是否相交或包含
     */
    default boolean isMinRectangularApart(Area area){
        return Math.abs(area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getX()) > getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() + area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - area.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() || Math.abs(area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getY()) > getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() + area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - area.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY();
    }

    /**
     * 判断最小覆盖矩形是否包围另一个最小矩形
     * @return 最小覆盖矩形是否包围另一个最小矩形
     */
    default boolean isMinRectangularSurround(Area area){
        return area.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() >= getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() && area.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() >= getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() && area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() <= getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() && area.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() <= getMaximumDiagonalPointOfMinimalCoverableRectangular().getY();
    }
}
