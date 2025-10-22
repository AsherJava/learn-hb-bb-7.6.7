/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.db;

import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import java.sql.SQLException;
import java.util.List;

public interface IBusinessConfigurationDao {
    public void insertConfiguration(BusinessConfigurationDefine var1) throws SQLException;

    public void updateConfiguration(BusinessConfigurationDefine var1) throws SQLException;

    public void updateConfiguration(String var1, String var2, String var3, String var4) throws SQLException;

    public void deleteConfiguration(String var1) throws SQLException;

    public void deleteConfigurationByCode(String var1, String var2) throws SQLException;

    public void deleteConfigurationByCode(String var1, String var2, String var3) throws SQLException;

    public BusinessConfigurationDefine getConfigurationByCode(String var1, String var2) throws SQLException;

    public BusinessConfigurationDefine getConfigurationByCode(String var1, String var2, String var3) throws SQLException;

    public List<BusinessConfigurationDefine> getConfigurationsByTask(String var1, boolean var2) throws SQLException;

    public List<BusinessConfigurationDefine> getConfigurationsByTask(String var1, String var2, boolean var3) throws SQLException;

    public List<BusinessConfigurationDefine> getConfigurationsByCategory(String var1, String var2, String var3, boolean var4) throws SQLException;
}

