package com.atsimoncc.myssm.basedao;
import com.atsimoncc.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * ClassName: CustomerDAO
 * Package: com.atsimoncc.myssm.basedao
 * Description:
 */
public interface CustomerDAO {
    /**
     * 將cust對象添加到資料庫中
     */
    void insert(Connection conn, Customer cust);

    /**
     * 針對指定的id, 刪除表中的一條紀錄.
     */
    void deleteById(Connection conn, int id);

    /**
     * 針對記憶體中的cust對象, 去修改資料表中指定的紀錄.
     */
    void update(Connection conn, Customer cust);
    /**
     * 針對指定的id查詢得到對樣的Customer對象
     */
    Customer getCustomerById(Connection conn, int id);

    /**
     * 查詢表中的所有紀錄構成的集合
     */
    List<Customer> getAll(Connection conn);

    /**
     * 查詢表中的所有紀錄, 每頁顯示5條
     */
    List<Customer> getAll(Connection conn, String keyword, Long pageNo);

    /**
     * 返回資料表中的資料的條數
     */
    Long getCount(Connection conn, String keyword);

    /**
     * 返回資料表中最大的生日
     */
    Date getMaxBirth(Connection conn);
}
