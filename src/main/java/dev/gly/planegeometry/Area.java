package dev.gly.planegeometry;

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
