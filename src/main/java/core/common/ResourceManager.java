package core.common;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

/**
 * 资源管理器类：
 *  负责对系统的资源进行管理，采用了单例模式
 */
public class ResourceManager {
    private static ResourceManager resourceManager;
    //engine就是需要管理的资源
    private TemplateEngine engine;
    //获得资源的方法
    public TemplateEngine getEngine() {
        return engine;
    }

    /**
     * 系统资源都可以在构造器当中创建。
     * 因为该构造器只会调用一次，所以这些系统资源肯定也只会创建一次。
     */
    private ResourceManager(ServletContext sctx){
        System.out.println("ResourceManager's constructor()");
        /*
                step1. 创建模板解析器
                getServletContext方法来自GenericServlet(该类是HttpServlet的父类)
                该方法的作用是获得ServletContext。
             */
        ServletContextTemplateResolver sctr = new ServletContextTemplateResolver(sctx);
            /*
                step2. 给解析器设置一些特性
             */
        //设置处理文件的类型
        sctr.setTemplateMode(TemplateMode.HTML);
        //设置前缀
        sctr.setPrefix("/WEB-INF/");
        //设置后缀
        sctr.setSuffix(".html");
        //设置编码
        sctr.setCharacterEncoding("utf-8");
        //在开发阶段，最好不使用缓存，在系统上线后，打开缓存，提高速度
        sctr.setCacheable(false);
            /*
                step3.创建模板引擎
             */
        engine = new TemplateEngine();
        engine.setTemplateResolver(sctr);
    }
     public synchronized static ResourceManager getInstance(ServletContext sctx){
        if (resourceManager==null){
            resourceManager = new ResourceManager(sctx);
        }
        return resourceManager;
     }
}
