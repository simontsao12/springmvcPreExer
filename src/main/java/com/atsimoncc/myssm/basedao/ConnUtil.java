package com.atsimoncc.myssm.basedao;

import com.atsimoncc.myssm.util.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ClassName: ConnUtil
 * Package: com.atsimoncc.myssm.basedao
 * Description:
 */
public class ConnUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    public static Connection getLocalThreadConn() throws Exception {
        Connection conn = threadLocal.get();
        if (conn == null){
            conn = JDBCUtils.getConnection();
            threadLocal.set(conn);
        }
        return threadLocal.get(); // 再獲取一次
    }

    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null){
            return;
        }
        if (!conn.isClosed()) {
            conn.close();
            threadLocal.set(null);
        }
    }
}
