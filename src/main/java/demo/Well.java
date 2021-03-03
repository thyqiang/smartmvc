package demo;

/**
 * 单例模式：采用饿汉式写法。
 *       即该类的实例是在类加载期间创建的。
 *       优点：没有线程安全问题。
 *       缺点：有可能造成资源的浪费。
 */
public class Well {

    private static Well well = new Well();

    private Well(){
        System.out.println("Well's constructor");
    }

    public static Well getInstance(){
        return well;
    }
}
