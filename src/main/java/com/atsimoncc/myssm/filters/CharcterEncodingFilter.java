package com.atsimoncc.myssm.filters;

import com.atsimoncc.util.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ClassName: CharcterEncodingFilter
 * Package: com.atsimoncc.myssm.filters
 * Description:
 */
//@WebFilter("*.do")
@WebFilter(urlPatterns = {"*.do"}, initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class CharcterEncodingFilter implements Filter {

    // 默認情況下為 utf-8
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        // 若有初始化參數就會為 encoding 設值
        String encodingStr = filterConfig.getInitParameter("encoding");
        if (StringUtil.isNotEmpty(encodingStr)) {
            encoding = encodingStr;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletRequest)servletRequest).setCharacterEncoding(encoding);
        // 養成習慣可以先寫, 因為若不放行也不會出錯
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
