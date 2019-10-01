package dev.gly.planegeometry;

/**
 *  Vector
 *  use 2 points to define a vector
 */
public class Vector {
    private Point start;
    private Point end;

    public Vector(Point start,Point end){
        this.start = start;
        this.end = end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    /**
     * get a reversed vector
     */
    public Vector getReversedVector(){
        return new Vector(this.end, this.start);
    }

    /**
     *
     * judge if a vector parallel the X axis
     */
    public boolean isXDirection(){
        return this.getStart().getY() == this.getEnd().getY();
    }

    /**
     * judge if the vector is left than a specific point
     * @param point the specific point
     */
    public boolean isPointRight(Point point){
        if (this.toFreeVector().getY() > 0){
            return isCW(point);
        }else
            return !isCW(point);
    }

    /**
     * judge if a specific point is in the half plot that rotates the vector 90deg clockwise
     * @param point a specific point
     */
    public boolean isCW(Point point){
        return this.getCrossProduct(new Vector(this.start, point)) < 0;
    }

    /**
     * judge if a specific point is collinear with the vector
     * @param point a specific point
     */
    public boolean isCollinear(Point point){
        return point.getVectorTo(start).toFreeVector().getY() * point.getVectorTo(end).toFreeVector().getX() == point.getVectorTo(start).toFreeVector().getX() * point.getVectorTo(end).toFreeVector().getY();
    }

    /**
     * judge if 2 vectors is collinear
     * @param vector a specific vector
     */
    public boolean isCollinear(Vector vector){
        return this.isCollinear(vector.start) && this.isCollinear(vector.end);
    }

    /**
     * judge if two vectors intersect each other
     * @param vector a specific vector
     */
    public boolean isIntersectedBy(Vector vector){
         return this.isCW(vector.getStart()) ^ this.isCW(vector.getEnd()) && vector.isCW(this.getStart()) ^ vector.isCW(this.getEnd());
    }

    /**
     * transfer the vector to a free vector
     */
    public FreeVector toFreeVector(){
        return new FreeVector(this.end.getX()-this.getStart().getX(), this.end.getY()-this.getStart().getY());
    }

    /**
     * get the cross product of the vector and another specific vector
     * @param vector a specific vector
     */
    public double getCrossProduct(Vector vector){
        return this.toFreeVector().getX() * vector.toFreeVector().getY() - vector.toFreeVector().getX() * this.toFreeVector().getY();
    }

    /**
     * get the cross product of the vector and another specific vector
     * @param vector a specific vector
     */
    public double getDotProduct(Vector vector){
        return this.toFreeVector().getX() * vector.toFreeVector().getX() + this.toFreeVector().getY() * vector.toFreeVector().getY();
    }
    /**
     * get the scalar multiplication with a specific scalar
     * @param scalar scalar
     */
    public Vector getScalarMultiplication(double scalar){
        return this.toFreeVector().getScalarMultiplication(scalar).toVectorFrom(this.start);
    }

    /**
     * vector addition
     * @param vector a specific vector
     */
    public Vector add(Vector vector){
        return this.start.getVectorTo(vector.toFreeVector().toVectorFrom(this.getEnd()).getEnd());
    }

    /**
     * get the normal vector that direct to a specific point
     * @param point a specific point
     */
    public Vector getNormalVector(Point point){
        if (this.isCollinear(point)) return new Vector(point, point); //如果共线，则直接返回point本身的零向量
        return new Vector(this.getScalarMultiplication(this.getDotProduct(new Vector(this.start, point))/(this.getMagnitude()*this.getMagnitude())).getEnd(), point);
    }

    /**
     * get the length of the vector
     * @return length
     */
    public double getMagnitude(){
        return this.start.distanceTo(this.end);
    }

    /**
     * get phase angle
     * @return angle
     */
    public double getAngle(){
        return this.toFreeVector().getT();
    }

    /**
     * get a array of discrete points which is sample by a specific interval
     * @param interval a specific interval
     */
    public Point[] getDiscretePoints(double interval) {
        Point[] points = new Point[(int) (getMagnitude()/interval)];

        for (int i=0; i<points.length; i++){
            points[i] = this.getScalarMultiplication(i * interval / getMagnitude()).getEnd();
        }

        return points;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector){
            Vector vector = (Vector)obj;
            return this.getStart().equals(vector.getStart()) && this.getEnd().equals(vector.getEnd());
        }else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        char[] charArr = sb.toString().toCharArray();
        int hash = 0;

        for(char c : charArr) {
            hash = hash * 131 + c;
        }
        return hash;
    }

    @Override
    public String toString() {
        String s = "";
        String e = "";
        if (getStart() != null) s = getStart().toString();
        if (getEnd() != null) e = getEnd().toString();
        return s+"->"+e;
    }
}
