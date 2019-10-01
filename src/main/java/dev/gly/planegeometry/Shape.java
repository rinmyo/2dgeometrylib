package dev.gly.planegeometry;

import dev.gly.planegeometry.Exception.ShapeTypeException;

public interface Shape {
    /**
     * get lower left diagonal point of the minimal rectangular which can cover the whole shape
     */
    Point getMinimalDiagonalPointOfMinimalCoverableRectangular();

    /**
     * get right upper diagonal point of the minimal rectangular which can cover the whole shape
     */
    Point getMaximumDiagonalPointOfMinimalCoverableRectangular();

    /**
     * move the vector along the direction of a specific vector
     * @param vector a specific vector
     */
    void move(Vector vector);

    /**
     * get a array of discrete points which is sample by a specific interval
     * @param interval interval
     */
    public Point[] getDiscretePoints(double interval);

    /**
     * judge if the shape includes a specific point
     * @param point a specific point
     */
    boolean isIncludePoint(Point point);

    /**
     * judge if the shape intersect with a specific shape
     * @param shape a specific shape
     */
    boolean isIntersectWithShape(Shape shape) throws ShapeTypeException;

    /**
     * judge if this shape surround a specific shape
     * @param shape a specific shape
     */
    boolean isSurroundShape(Shape shape) throws ShapeTypeException;

    /**
     * get Area
     */
    double getArea();

    /**
     * get perimeter
     */
    double getPerimeter();

    /**
     * judge if a specific point is in the outside of a minimal coverable rectangular
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
