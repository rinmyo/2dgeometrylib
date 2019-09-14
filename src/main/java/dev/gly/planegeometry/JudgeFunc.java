package dev.gly.planegeometry;

@FunctionalInterface
public interface JudgeFunc {
    boolean judge(Shape[] shapes, Point point);
}
