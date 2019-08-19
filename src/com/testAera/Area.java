package com.testAera;

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
     * @param interval
     * @return 二维数组
     */
    public Point[] getDiscretePoints(double interval);
}
