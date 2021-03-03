package core.web;

import core.common.HandlerMapping;
import core.common.ResourceManager;
import demo.HelloService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DispatcherServlet",urlPatterns = "*.do",loadOnStartup = 1)
/**
 * urlPatterns属性用于设置该Servlet对应哪些请求。
 * "*.do"表示所有以“.do”结尾的请求，都由该Servlet来处理。
 * 后缀匹配
 */
public class DispatcherServlet extends HttpServlet {
    private HandlerMapping handlerMapping;

    public DispatcherServlet() {
        System.out.println("DispatcherServlet's constructor()");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("DispatcherServlet's init()");
        SAXReader saxReader = new SAXReader();
        /*
            构造一个指向配置文件的输入流 Object
            getClass()方法来自于Object，作用是获得Class对象。
            getClassLoader()方法用于获得加载该类的类加载器。
            getResourceAsStream（）方法是类加载器的方法，其作用是依据
            classpath查找文件，然后返回一个指向该文件的输入流。

         */
        InputStream in =
                getClass().getClassLoader().getResourceAsStream("smartmvc.xml");
        try {
            Document doc = saxReader.read(in);
            //找到根节点
            Element root = doc.getRootElement();
            //找到根节点下面所有的子节点
            List<Element> elementList = root.elements();
            //beans集合用于存放处理器实例
            List beans = new ArrayList();
            //遍历所有子节点
            for (Element element:elementList){
                //获得处理器类名
                String className = element.attributeValue("class");
                System.out.println("className:"+className);
                //将处理器实例化
                Object obj = Class.forName(className).newInstance();
                //将处理器实例保存起来
                beans.add(obj);
            }
            System.out.println("beans:"+beans);
            handlerMapping = new HandlerMapping();
            //将处理器实例交给HandlerMapping来处理。
            handlerMapping.process(beans);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DispatcherServlet's service()");
        //获取请求路径
        String path = request.getServletPath();
        System.out.println("path"+path);
        //依据请求路径，调用相应的模型来处理
        if ("/hello.do".equals(path)){
            HelloService helloService = new HelloService();
            String msg = helloService.hello();
            //依据模型返回的处理结果，调用相应的视图来展现。

            TemplateEngine engine = ResourceManager.getInstance(
                    getServletContext()).getEngine();

            /*
                step4.调用模板引擎的方法来处理模板文件
             */
            //WebContext用来为模板引擎提供数据
            WebContext ctx = new WebContext(request,response,getServletContext());
            //将数据绑定到WebContext
            ctx.setVariable("msg",msg);
            //设置MIME类型
            response.setContentType("text/html;charset=utf-8");
            /*
                process方法会利用前缀+“hello”+后缀找到模板文件，
                然后利用ctx获得数据，生成相应的动态页面，最后将结果输出。
             */
            engine.process("hello",ctx,response.getWriter());


        }
    }
}
