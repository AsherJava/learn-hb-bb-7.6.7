/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datawarning.dao;

import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import java.util.List;

public interface IDataWarningDao {
    public Boolean Insert(DataWarningDefine var1);

    public Boolean Update(DataWarningDefine var1);

    public Boolean Update(List<DataWarningDefine> var1);

    public Boolean Delete(String var1);

    public Boolean Delete(String var1, Boolean var2);

    public DataWarningDefine QueryById(String var1);

    public List<DataWarningDefine> QueryDataWarnigs(String var1);

    public List<DataWarningDefine> QueryDataWarnigs(String var1, DataWarningIdentify var2);

    public List<DataWarningDefine> QueryDataWarnigs(String var1, DataWarningType var2, DataWarningIdentify var3);

    public List<DataWarningDefine> QueryDataWarnigs(DataWarningIdentify var1);

    public List<DataWarningDefine> QueryDataWarnigs(DataWarningType var1, DataWarningIdentify var2);

    public List<DataWarningDefine> QueryDataWarnigsByKeyAndFieldCode(String var1, String var2);

    public List<DataWarningDefine> QueryDataWarnigsByKeyAndFieldSettingCode(String var1, String var2);
}

