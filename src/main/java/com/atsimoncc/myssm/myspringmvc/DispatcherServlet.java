package com.atsimoncc.myssm.myspringmvc;

import com.atsimoncc.myssm.ioc.BeanFactory;
import com.atsimoncc.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static javax.swing.text.html.CSS.getAttribute;

/**
 * ClassName: DispatcherServlet
 * Package: com.atsimoncc.myssm.myspringmvc
 * Description:
 */
@WebServlet(value = {"*.do"}) // 接受所有 .do 結尾的請求
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    public DispatcherServlet() {

    }

    public  void init() throws ServletException {
        super.init();
        // beanFactory = new ClassPathXmlApplicationContext();
        // 改成於監聽器建立後在作用域設定 attribute 再取出
        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        if (beanFactoryObj != null) {
            beanFactory = (BeanFactory) beanFactoryObj;
            System.out.println("IOC 獲取成功");
        } else {
            throw new RuntimeException("IOC容器獲取失敗");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 已經在 filter 設置
        // request.setCharacterEncoding("utf-8");

        String servletPath = request.getServletPath();
        servletPath = servletPath.substring(1);
        int lastIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastIndex);
        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        // 這邊不用 request.getClass() 和 response.getClass() 是因為其真實類型與 HttpServletRequest 及 HttpServletResponse 有區別
        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (operate.equals(method.getName())) {
                    // 1. 統一獲取請求參數
                    // 1-1. 獲取當前方法的參數陣列
                    Parameter[] parameters = method.getParameters(); //
                    // 1-2. parameterValues 存放參數值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i]; //
                        String parameterName = parameter.getName(); // 在 pom.xml 設定 -parameters
                        // 若參數名為 request, response, session 表示不是通過請求獲取參數的方式
                        if ("request".equals(parameterName)) {
                            parameterValues[i] = request;
                        } else if ("response".equals(parameterName)) {
                            parameterValues[i] = response;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i] = request.getSession();
                        } else {
                            // 從請求中獲取參數值
                            String parameterValue = request.getParameter(parameterName);
                            // 這邊未考慮複選框的 input 如 checkbox, 若要使用可以用 request.getParameterValues()
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue;

                            if (parameterObj != null) {
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                } else if ("java.lang.Long".equals(typeName)) {
                                    parameterObj = Long.parseLong(parameterValue);
                                }
                            }
                            parameterValues[i] = parameterObj; // 要注意字串 "2" 不是 Integer 2
                        }
                    }
                    // 2. controller 組件中的方法呼叫
                    method.setAccessible(true);
                    // 先判斷 != null 再強轉會比較規範
                    Object methodReturnObj = method.invoke(controllerBeanObj, parameterValues);
                    // 3. 視圖處裡
                    String methodReturnStr = (String)methodReturnObj;
                    if (methodReturnStr.startsWith("redirect:")) {
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        response.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr, request, response);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.processTemplate("error", request, response);
            throw new DispatcherServletException("Dispatcher出問題");
        }
    }
}
