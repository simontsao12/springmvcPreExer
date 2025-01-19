package com.atsimoncc.myssm.basedao.impl;
import com.atsimoncc.bean.Customer;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import com.atsimoncc.myssm.basedao.BaseDAO;
import com.atsimoncc.myssm.basedao.CustomerDAO;
import com.atsimoncc.myssm.util.JDBCUtils;

/**
 * ClassName: CustomerDAOImpl
 * Package: com.atsimoncc.myssm.basedao.impl
 * Description:
 */
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {
    private Connection conn = null;

    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "insert into customers(name, email, birth) values(?, ?, ?)";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customer cust) {
        String sql = "update customers set name = ?, email = ?, birth = ? where id = ?";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth(), cust.getId());
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "select id, name, email, birth from customers where id = ?";
        Customer customer = getInstance(conn, sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id, name, email, birth from customers";
        List<Customer> list = getForList(conn, sql);
        return list;
    }

    @Override
    public List<Customer> getAll(Connection conn, String keyword, Long pageNo) {
        String sql = "select id, name, email, birth from customers where name like ? or email like ? limit ?, 5";
        List<Customer> list = getForList(conn, sql, "%" + keyword + "%", "%" + keyword + "%", (pageNo - 1) * 5);
        return list;
    }

    @Override
    public Long getCount(Connection conn, String keyword) {
        String sql = "select count(*) from customers where name like ? or email like ?";
        return getValue(conn, sql, "%" + keyword + "%", "%" + keyword + "%");
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return getValue(conn, sql);
    }

}
