package com.atsimoncc.myssm.filters;

import com.atsimoncc.myssm.trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * ClassName: OpenSessionInViewFilter
 * Package: com.atsimoncc.myssm.filters
 * Description:
 */
@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            TransactionManager.beginTrans();
            System.out.println("開啟交易...");
            filterChain.doFilter(servletRequest, servletResponse);
            TransactionManager.commit();
            System.out.println("提交交易...");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                TransactionManager.rollback();
                System.out.println("回滾交易...");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
