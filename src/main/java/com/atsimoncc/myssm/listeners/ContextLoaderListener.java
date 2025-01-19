package com.atsimoncc.myssm.listeners;

import com.atsimoncc.myssm.ioc.BeanFactory;
import com.atsimoncc.myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ClassName: ContextLoaderListener
 * Package: com.atsimoncc.myssm.listeners
 * Description:
 */
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("創建IOC容器");
        // 1. 獲取 ServletContext 對象
        ServletContext application = servletContextEvent.getServletContext();
        // 2. 獲取上下文的初始化參數
        String path = application.getInitParameter("contextConfigLocation");
        // 3. 創建 IOC 容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);
        // 4. 將 IOC 容器保存到 application 作用域
        application.setAttribute("beanFactory", beanFactory);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
