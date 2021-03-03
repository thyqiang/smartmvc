package demo;

public class SingletonTest {
    public static void main(String[] args) {
//        Well well = Well.getInstance();
//        Well well1 = Well.getInstance();
//        System.out.println(well==well1);
        Well2 well = Well2.getInstance();
        Well2 well2 = Well2.getInstance();
        System.out.println(well==well2);
    }
}
