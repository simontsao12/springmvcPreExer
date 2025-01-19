package com.atsimoncc.service.impl;

import java.util.List;

import com.atsimoncc.bean.Customer;
import com.atsimoncc.myssm.basedao.ConnUtil;
import com.atsimoncc.myssm.basedao.CustomerDAO;
import com.atsimoncc.myssm.basedao.exceptions.CustomerServiceException;
import com.atsimoncc.service.CustomerService;

/**
 * ClassName: CustomerImpl
 * Package: com.atsimoncc.biz.impl
 * Description:
 */
public class CustomerImpl implements CustomerService {

    private CustomerDAO customerDAO = null;

    @Override
    public List<Customer> getCustomers(String keyword, Long pageNo) {
        try {
            System.out.println("getCustomers->"+ConnUtil.getLocalThreadConn());
            return customerDAO.getAll(ConnUtil.getLocalThreadConn(), keyword, pageNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerServiceException("CustomerService getCustomers error");
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            customerDAO.insert(ConnUtil.getLocalThreadConn(), customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerServiceException("CustomerService addCustomer error");        
        }
    }

    @Override
    public Customer getCustomerById(Integer cid) {
        try {
            return customerDAO.getCustomerById(ConnUtil.getLocalThreadConn(), cid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerServiceException("CustomerService getCustomerById error");
        }
    }

    @Override
    public void delCustomerById(Integer cid) {
        try {
            customerDAO.deleteById(ConnUtil.getLocalThreadConn(), cid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerServiceException("CustomerService delCustomerById error");
        }
    }

    @Override
    public Long getPageCount(String keyword) {
        try {
            System.out.println("getPageCount->"+ConnUtil.getLocalThreadConn());
            Long count = customerDAO.getCount(ConnUtil.getLocalThreadConn(), keyword);
            Long pageCount = (count + 5L - 1) / 5;
            return pageCount;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerServiceException("CustomerService getPageCount error");
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            customerDAO.update(ConnUtil.getLocalThreadConn(), customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerServiceException("CustomerService updateCustomer error");
        }
    }
}
