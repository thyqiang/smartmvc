package demo;

/**
 * 单例模式：采用懒汉式写法
 *      即在外界需要时，才会创建其实例。
 *      优点：不会造成资源的浪费。
 *      缺点：需要考虑线程安全问题。
 */
public class Well2 {

    private static Well2 well;

    private Well2(){
        System.out.println("Well2's constructor()");
    }

    public synchronized static Well2 getInstance(){
        if (well==null){
            well=new Well2();
        }
        return well;
    }
}
