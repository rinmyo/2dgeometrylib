package dev.gly.planegeometry;

/**
 * an area is composed by some shapes and a logic relationship of these shapes, which is used to define where is 'inner' for an area
 */
public class Area {
    private Shape[] shapes;
    private JudgeFunc judgeFunc;

    public Area(Shape[] shapes, JudgeFunc judgeFunc) {
        this.shapes = shapes;
        this.judgeFunc = judgeFunc;
    }

    public boolean judge(Point point) {
        return judgeFunc.judge(shapes, point);
    }
}
