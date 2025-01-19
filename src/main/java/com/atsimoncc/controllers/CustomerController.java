package com.atsimoncc.controllers;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.atsimoncc.bean.Customer;
import com.atsimoncc.service.CustomerService;
import com.atsimoncc.util.StringUtil;

/**
 * ClassName: CustomerServlet
 * Package: com.atsimoncc.servlets
 * Description:
 */

public class CustomerController {
    private CustomerService customerService = null;

    private String update(Integer cid, String name, String email, String birth) {
        Date birthDate = Date.valueOf(birth);
        Customer customer = new Customer(cid, name, email, birthDate);
        customerService.updateCustomer(customer);
        return "redirect:customer.do";
    }

    private String edit(Integer cid, HttpServletRequest request) {
        Customer customer = null;
        if (cid != null) {
            customer = customerService.getCustomerById(cid);
            request.setAttribute("customer", customer);
            return "edit";
        }
        return "error";
    }

    private String del(Integer cid) {
        if (cid != null) {
            customerService.delCustomerById(cid);
            return "redirect:customer.do";
        }
        return "error";
    }

    private String addView() {
    	return "add";
    }
    
    private String add(Integer cid, String name, String email, String birth) {
        birth = birth.replace("/", "-");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setBirth(Date.valueOf(birth));
        customerService.addCustomer(customer);
        return "redirect:customer.do";
    }

    private String index(String oper, String keyword, Long pageNo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (pageNo == null) {
            pageNo = 1L;
        }
        // 若 oper 為 null 表示不是從表單的查詢來
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            pageNo = 1L;
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            // 非從表單來, keyword 從 session 中獲取

            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        Long pageCount = customerService.getPageCount(keyword);
        if (pageNo > pageCount) {
            pageNo = pageCount;
        } else if (pageNo < 1L) {
            pageNo = 1L;
        }

        session.setAttribute("pageNo", pageNo);
        List<Customer> customerList = null;
        customerList = customerService.getCustomers(keyword, pageNo);
        session.setAttribute("customerList", customerList);
        session.setAttribute("pageCount", pageCount);
        return "index";
    }
}
