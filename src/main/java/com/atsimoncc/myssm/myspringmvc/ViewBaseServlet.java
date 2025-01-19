package com.atsimoncc.myssm.myspringmvc;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: ViewBaseServlet
 * Package: com.atsimoncc.myssm.myspringmvc
 * Description:
 */
public class ViewBaseServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        // 1. 獲取 ServletContext 物件
        ServletContext servletContext = this.getServletContext();
        // 2. 創建 Thymeleaf 解析器物件
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        // 3. 給解析器物件設定參數
        // HTML 是預設處理模式, 明確設定是為了 code 更容易理解
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // 設定前綴
        String viewPrefix = servletContext.getInitParameter("view-prefix");
        templateResolver.setPrefix(viewPrefix);
        // 設定後綴
        String viewSuffix = servletContext.getInitParameter("view-suffix");
        templateResolver.setSuffix(viewSuffix);
        // 設定緩存過期時間(毫秒)
        templateResolver.setCacheTTLMs(60000L);
        // 設定是否緩存
        templateResolver.setCacheable(true);
        // 設定伺服器編碼方式
        templateResolver.setCharacterEncoding("utf-8");
        // 4. 創建模板引擎物件
        templateEngine = new TemplateEngine();
        // 5. 給模板引擎物件設定模板解析器
        templateEngine.setTemplateResolver(templateResolver);
    }

    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 設定響應體內容類型和字符集
        response.setContentType("text/html;charset=UTF-8");
        // 2. 創建 WebContext 物件
        WebContext webContext = new WebContext(request, response, getServletContext());
        // 3. 處理模板資料
        templateEngine.process(templateName, webContext, response.getWriter());
    }

}
