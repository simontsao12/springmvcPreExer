package com.atsimoncc.service;

import com.atsimoncc.bean.Customer;

import java.util.List;

/**
 * ClassName: CustomerService
 * Package: com.atsimoncc.biz
 * Description:
 */
public interface CustomerService {
    // 獲取指定頁面的會員訊息
    List<Customer> getCustomers(String keyword, Long pageNo);
    // 新增會員訊息
    void addCustomer(Customer customer);
    // 根據 id 查看會員
    Customer getCustomerById(Integer cid);
    // 刪除特定會員
    void delCustomerById(Integer cid);
    // 獲取總頁數
    Long getPageCount(String keyword);
    // 修改
    void updateCustomer(Customer customer);
}
