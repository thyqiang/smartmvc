package core.common;

import core.annotation.RequestMapping;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 映射处理器类：负责提供请求路径预处理器实例及方法的对应关系。
 * 比如：请求路径为"/hello.do"，则该请求有HelloController的hello方法来处理。
 */
public class HandlerMapping {
    /*
        handlerMap用于存放请求路径与处理器实例及方法的对应关系。
        Handler对象里面封装有处理器实例及方法。
     */

    private Map<String,Handler> handlerMap = new HashMap<>();

    /**
     * 负责建立请求路径与处理器实例及方法的对应关系。
     * @param beans 处理器实例组成的集合。
     */
    public void process(List beans) {
        System.out.println("HandlerMapping's process()");
        //遍历处理器实例组成的集合
        for (Object obj : beans){
            //获得处理器的所有方法
            Method[] methods = obj.getClass().getDeclaredMethods();
            //遍历所有方法
            for (Method mh : methods){
                //获得加在方法前的注解@RequestMapping
                RequestMapping rm = mh.getAnnotation(RequestMapping.class);
                if (rm!=null) {
                    //获得请求路径
                    String path = rm.value();
                    System.out.println("path:"+path);
                    //将处理器实例及Method对象封装到Handler对象里面
                    Handler handler = new Handler();
                    handler.setObject(obj);
                    handler.setMh(mh);
                    //将请求路径和Handler对象的对应关系存放起来
                    handlerMap.put(path,handler);
                }
            }
        }
        System.out.println("handlerMap:"+handlerMap);
    }
}
