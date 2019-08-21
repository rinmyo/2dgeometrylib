package dev.gly.planegeometry;

import dev.gly.planegeometry.Exception.ShapeTypeException;

public interface Shape {
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
     * @param shape 另一个区域
     * @return 布尔
     */
    boolean isIntersectWithShape(Shape shape) throws ShapeTypeException;

    /**
     * 是否包围另一个区域
     * @param shape 另一个区域
     * @return 布尔
     */
    boolean isSurroundShape(Shape shape) throws ShapeTypeException;

    /**
     * 获取面积
     * @return 面积
     */
    double getArea();

    /**
     * 获取周长
     * @return 周长
     */
    double getPerimeter();

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
    default boolean isMinRectangularApart(Shape shape){
        return Math.abs(shape.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getX()) > getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() + shape.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() - shape.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() || Math.abs(shape.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getY()) > getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() + shape.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() - shape.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY();
    }

    /**
     * 判断最小覆盖矩形是否包围另一个最小矩形
     * @return 最小覆盖矩形是否包围另一个最小矩形
     */
    default boolean isMinRectangularSurround(Shape shape){
        return shape.getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() >= getMinimalDiagonalPointOfMinimalCoverableRectangular().getX() && shape.getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() >= getMinimalDiagonalPointOfMinimalCoverableRectangular().getY() && shape.getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() <= getMaximumDiagonalPointOfMinimalCoverableRectangular().getX() && shape.getMaximumDiagonalPointOfMinimalCoverableRectangular().getY() <= getMaximumDiagonalPointOfMinimalCoverableRectangular().getY();
    }
}
