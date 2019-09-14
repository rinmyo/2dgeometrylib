import dev.gly.planegeometry.*;

public class Main {
    public static void main(String[] args) {
        Shape triangle = new Polygon(new Point[]{new Point(0, 0), new Point(30, 0), new Point(15, 30)});
        Shape circle = new Circle(0.5, new Point(15, 15));

        JudgeFunc judgeFunc = (s, p) -> s[0].isIncludePoint(p) ^ s[1].isIncludePoint(p);


        Area area = new Area(new Shape[]{triangle, circle}, judgeFunc);

        System.out.println(area.judge(new Point(0,0)));

    }
}