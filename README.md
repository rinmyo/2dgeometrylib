

> ## ⚠️ Deprecation warning:
> ### This Project has been abandoned.  
> ### 斯項目已廢止（拒絕造輪子）  

# 2DGeometry

## 1. What's this ? 概述
 This is a plane geometry library driven by 2D vector for the Minecraft server plugin

## 2. 釋詞
1. ### Coordinate System

    It is necessary to be located and defined by a coordinate system for any geometry element. this lib DO NOT define any coordinate system, therefore you MUST ensure all you input is defined by the same coordinate system. 
    **Minecraft Coordinate System**： the 3D coordinate system of minecraft.
    
    if the coordinate system you use is NOT the minecraft coordinate, Plz convert it to minecraft coordinate system before you use the computing result
2. ### 点(Point)

    使用两个double型坐标变量定义的一种数据类型，支持直角坐标和极坐标定义一个点，
    使用直角坐标你需要使用`Point(double x,double y)`，使用极坐标系你需要使用`Point(double r, double t, true)`, **极坐标的极轴是直角坐标系下的x正半轴**，r是 **极径(rho)** ，t是 **极角(theta)** .

    **点的数据结构是用直角坐标来定义的**
    
    但请注意，由于计算精度的原因，请不要将一个点连续转换两次，请尽可能的使用直角坐标.

3. ### 向量(Vector)

    请注意本库中向量指的是 **固定向量**
    
    两个点定义一个固定矢量，一个是起点(start)，一个是终点(end)，
    
    本库定义了最基本的向量运算，包括向量和，数乘，矢积，点积，取模长，取相角，求取负向量 等等; 也基于以上运算定义了一些常用的算法，例如判断两个向量是否相交，等等.
    
4. ### 自由向量(Free Vector)

    自由向量是起点为计算坐标系原点的向量，可以使用自由向量做向量运算，自由向量运算后仍然是自由向量.
    
    由于在数学上，自由向量可以用点坐标来表示，而本库中自由向量是向量的子类，
    因此为了方便~~套公式~~使用，本库可以直接对自由向量进行如下点的运算.
    1. 取横坐标 `getX()` 取纵坐标 `getY()`
    2. 取极角 `getT()` 取极径`getR()`
  
    **注意： 在数学中向量一般指自由向量，而本库一般指固定矢量**
    
    因此固定矢量取相角和模长请使用`getMagnitude()`同`getAngle()`，
    观察下两行代码：
    ```
   //取模长
    new Vector().getMagnitude(); 
   
   //将该固定矢量平移至自元点起的自由向量后，取其极坐标系下的极径
    new Vector().toFreeVector.getR(); 
    ```
   
   虽然 
   
   **向量的 模长 和 相角** 

   同 
   
   **将固定向量转为自由向量，再求其极坐标下的 极径 和 极角** 
   
   的代数值相同，但是他们的数学意义不同，请在何时的地方使用适合的方法，否则不利于理解算法

5. ### 多边形(Polygon)

    多边形实现了Shape接口
    
    一系列首尾相连的固定向量定义一个多边形，多边形的存储结构是一个由连接点依序排列的数组，运算时使用向量.
    
    请注意：本库会自动将最后一个点与首个点相连以形成闭环，例如依次输入(0,0)->(1,1)->(2,0)，本库将认为
        
        ┌─>(0,0)─>(1,1)─>(2,0)─┐
        └──────────────────────┘
        
6. ### 圆(Circle)

    圆实现了Shape接口
     
    圆通过 _圆心_ 和 _半径_ 来定义
     
## 3. 名词解释

區域是一個或多個Shape和這些shapes的集合關係定義的
創建一個Area
    
     Area area = new Area(Shape[] shapes, JudgeFunc judgeFunc);

第二個形參是判斷邏輯λ表達式，它的目的是定義怎樣才能算做**區域內**，他由使用者實現
    
例子：

    //定義兩個shape
    Shape triangle = new Polygon(new Point[]{new Point(0, 0), new Point(30, 0), new Point(15, 30)});
    Shape circle = new Circle(0.5, new Point(15, 15));

    //定義一個集合關係，s是shapes數組，p是待測點
    JudgeFunc judgeFunc = (s, p) -> s[0].isIncludePoint(p) ^ s[1].isIncludePoint(p);

    //用一個數組和judgefunc來定義一個區域
    Area area = new Area(new Shape[]{triangle, circle}, judgeFunc);
    
如上，judgeFunc定義了s[0]和s[1]二者有且僅有一個包含待測點時的集合，你可以使用lambda exp很方便的使用集合語言來定義何爲區域之內部
    
而 `Area()` 的首個參數 `new Shape[]{triangle, circle}` 則定義了s[0]是之前定義的三角形，s[1]是那個圓形
    
如此便可知，當三角包含圓不包含 或者 圓包含但三角不包含 的點，便是該Area所定義的區域內部

對於一個區域，可以用 `Area.isIncludePoint(point)` 來測定點是否在區域內部，Satanya建議使用者將所有的範圍定義爲區域而非幾何圖形
     
