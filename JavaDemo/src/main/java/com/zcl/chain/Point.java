package com.zcl.chain;


public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    public static void main(String[] args) {
        Point[] arr = new Point[2];
        arr[0] = new Point(10,20);
        for (Point p:arr) {
            if (p != null) {
                System.out.println(p.x + " =x | y =" +p.y);
            }
            System.out.println("hahah");
        }

    }
}

