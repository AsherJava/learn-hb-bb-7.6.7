/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 */
package com.jiuqi.nvwa.sf.adapter.spring.util.envcheck.impl;

import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.nvwa.sf.adapter.spring.util.envcheck.AbstractLaunchEnvChecker;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MysqlCaseSensitiveCheck
extends AbstractLaunchEnvChecker {
    private final Logger logger = LoggerFactory.getLogger(MysqlCaseSensitiveCheck.class);

    @Override
    public void doCheck() {
        block68: {
            try (Connection connection = GlobalConnectionProviderManager.getConnection();){
                Throwable throwable;
                ResultSet rs;
                if (!connection.getMetaData().getDriverName().contains("MySQL")) break block68;
                try (Statement stmt = connection.createStatement();){
                    rs = stmt.executeQuery("SHOW VARIABLES LIKE 'lower_case_table_names'");
                    throwable = null;
                    try {
                        if (rs.next()) {
                            int value = rs.getInt("Value");
                            if (value == 0) {
                                this.logger.error("MYSQL\u6570\u636e\u5e93\u5927\u5c0f\u5199\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a\u5f53\u524d\u6570\u636e\u5e93\u5927\u5c0f\u5199\u654f\u611f");
                            } else {
                                this.logger.info("MYSQL\u6570\u636e\u5e93\u5927\u5c0f\u5199\u6821\u9a8c\u3010\u901a\u8fc7\u3011\uff1a\u5f53\u524d\u6570\u636e\u5e93\u5927\u5c0f\u5199\u4e0d\u654f\u611f");
                            }
                        }
                    }
                    catch (Throwable value) {
                        throwable = value;
                        throw value;
                    }
                    finally {
                        if (rs != null) {
                            if (throwable != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable value) {
                                    throwable.addSuppressed(value);
                                }
                            } else {
                                rs.close();
                            }
                        }
                    }
                }
                stmt = connection.createStatement();
                var4_7 = null;
                try {
                    rs = stmt.executeQuery("SHOW VARIABLES LIKE 'character_set_server'");
                    throwable = null;
                    try {
                        if (rs.next()) {
                            String value = rs.getString("Value");
                            if (!"utf8mb4".equalsIgnoreCase(value)) {
                                this.logger.error("MYSQL\u6570\u636e\u5e93\u5b57\u7b26\u96c6\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a\u5f53\u524d\u6570\u636e\u5e93\u5b57\u7b26\u96c6\uff1a" + value);
                            } else {
                                this.logger.info("MYSQL\u6570\u636e\u5e93\u5b57\u7b26\u96c6\u6821\u9a8c\u3010\u901a\u8fc7\u3011\uff1a\u5f53\u524d\u6570\u636e\u5e93\u5b57\u7b26\u96c6\uff1a" + value);
                            }
                        }
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (rs != null) {
                            if (throwable != null) {
                                try {
                                    rs.close();
                                }
                                catch (Throwable throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                            } else {
                                rs.close();
                            }
                        }
                    }
                }
                catch (Throwable throwable4) {
                    var4_7 = throwable4;
                    throw throwable4;
                }
                finally {
                    if (stmt != null) {
                        if (var4_7 != null) {
                            try {
                                stmt.close();
                            }
                            catch (Throwable throwable5) {
                                var4_7.addSuppressed(throwable5);
                            }
                        } else {
                            stmt.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("MYSQL\u6570\u636e\u5e93\u5927\u5c0f\u5199\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a" + e.getMessage(), e);
            }
        }
    }

    @Override
    public String timeoutMessage() {
        return "MYSQL\u6570\u636e\u5e93\u5927\u5c0f\u5199\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a\u6570\u636e\u5e93\u8fde\u63a5\u8d85\u65f6\uff0c\u8bf7\u68c0\u67e5\u670d\u52a1\u5668\u4e0e\u6570\u636e\u5e93\u8fde\u63a5\u72b6\u6001";
    }
}

