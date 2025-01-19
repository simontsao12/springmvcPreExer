package com.atsimoncc.myssm.trans;

import com.atsimoncc.myssm.basedao.ConnUtil;
import com.atsimoncc.myssm.util.JDBCUtils;

import javax.swing.text.Utilities;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * ClassName: TransactionManager
 * Package: com.atsimoncc.myssm.trans
 * Description:
 */
public class TransactionManager {
    // 這邊尚未考慮交易的傳播機制
    // 開啟交易
    public static void beginTrans() throws Exception {
        ConnUtil.getLocalThreadConn().setAutoCommit(false);
    }

    // 提交
    public static void commit() throws Exception {
        Connection conn = ConnUtil.getLocalThreadConn();
        conn.commit();
        ConnUtil.closeConn();
    }

    // 回滾
    public static void rollback() throws Exception {
        Connection conn = ConnUtil.getLocalThreadConn();
        conn.rollback();
        ConnUtil.closeConn();
    }
}
