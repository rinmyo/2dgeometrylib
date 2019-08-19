package com.testAera;

import com.testAera.Exception.AreaTypeException;

import java.io.IOException;

public class Test {
    private Area test1 = new Polygon(
            new Point[]{
                    new Point(0,0),
                    new Point(8,0),
                    new Point(2,-4),
                    new Point(4,3),
                    new Point(6,-4),
            });
    private Area test2 = new Circle(2,new Point(0,0));

    private void run(){

        test1.move(new FreeVector(0, 0));


//        //扫描一片区域
//        for (double i=-5 ; i<4; i+=0.07*1){
//            for (double j=0; j<10; j+=0.025*1){
//               if ( new JudgePointInArea(new Point(j,i), test1).getJudgement() || new JudgePointInArea(new Point(j,i), test2).getJudgement()) System.out.print(".");
//               else System.out.print(" ");
//            }
//            System.out.println(); //换行
//        }
//        System.out.println(new JudgeAreaIntersectWithArea(test1, test2).getJudgement() ? "相交" : "不想交");


//        System.out.println("从(2,2)到(7,7)，每隔√2一个点");
//        for (Point p: new FreeVector(5,5).toVectorFrom(new Point(2,2)).getDiscretePoints(Math.sqrt(2))
//             ) {
//            System.out.print(p + " ");
//        }
//
//        System.out.println("从(0,6)到(-7,7)，每隔3一个点");
//        for (Point p: new Vector(new Point(0,6), new Point(-7,7)).getDiscretePoints(3)
//        ) {
//            System.out.println(p + " ");
//        }
//
//        // todo 有问题
//        System.out.println("圆心2,0，半径2 的圆，每π/6弧长打一个点, 极坐标显示");
//        for (Point p: test2.getDiscretePoints(Math.PI/3)
//             ) {
//            System.out.println("模长"+p.getR()+"，幅角"+Math.toDegrees(p.getT())+" ");
//        }

        System.out.println(((Polygon)test1).getVectors());
    }
    public static void main(String[] args) throws AreaTypeException, IOException {
         new Test().run();

    }
}
