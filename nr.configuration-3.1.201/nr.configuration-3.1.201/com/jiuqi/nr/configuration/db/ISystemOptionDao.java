/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.db;

import com.jiuqi.nr.configuration.facade.SystemOptionDefine;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Deprecated
public interface ISystemOptionDao {
    public void batchSetOptions(HashMap<String, Object> var1, boolean var2) throws SQLException;

    public void batchSetOptions(HashMap<String, Object> var1, String var2, boolean var3) throws SQLException;

    public void batchSetOptions(HashMap<String, Object> var1, String var2, String var3, boolean var4) throws SQLException;

    public void setSystemOption(String var1, Object var2) throws SQLException;

    public void setSystemOption(String var1, Object var2, String var3) throws SQLException;

    public void setSystemOption(String var1, Object var2, String var3, String var4) throws SQLException;

    public SystemOptionDefine getOptionByCode(String var1) throws SQLException;

    public SystemOptionDefine getOptionByCode(String var1, String var2) throws SQLException;

    public SystemOptionDefine getOptionByCode(String var1, String var2, String var3) throws SQLException;

    public HashMap<String, Object> batchGetOptions(List<String> var1) throws SQLException;

    public HashMap<String, Object> batchGetOptions(List<String> var1, String var2) throws SQLException;

    public HashMap<String, Object> batchGetOptions(List<String> var1, String var2, String var3) throws SQLException;

    public List<SystemOptionDefine> getOptions() throws SQLException;

    public List<SystemOptionDefine> getOptionsByTask(String var1) throws SQLException;

    public List<SystemOptionDefine> getOptionsByFormScheme(String var1, String var2) throws SQLException;

    public List<SystemOptionDefine> getAllOptions() throws SQLException;

    public void deleteSystemOption(String var1) throws SQLException;

    public void deleteSystemOption(String var1, String var2) throws SQLException;

    public void deleteSystemOption(String var1, String var2, String var3) throws SQLException;

    public void batchDeleteOptions(List<String> var1) throws SQLException;

    public void batchDeleteOptions(List<String> var1, String var2) throws SQLException;

    public void batchDeleteOptions(List<String> var1, String var2, String var3) throws SQLException;

    public List<SystemOptionDefine> getOptionsByByGroup(String var1) throws SQLException;
}

