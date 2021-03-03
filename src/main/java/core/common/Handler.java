package core.common;

import java.lang.reflect.Method;

/**
 * 为了方便利用java反射机制去调用处理器的方法
 * 而设计的一个类。
 */
public class Handler {
    //obj用于存放处理器实例
    private Object object;
    //mh用于存放处理器方法对应的Method对象
    private Method mh;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMh() {
        return mh;
    }

    public void setMh(Method mh) {
        this.mh = mh;
    }
}
