/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.datawarning.defines;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import com.jiuqi.nr.datawarning.defines.DataWarningIdentify;
import com.jiuqi.nr.datawarning.defines.DataWarningType;
import com.jiuqi.nr.datawarning.defines.TransUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DataWarningDefineDao
extends BaseDao {
    private static String dwn_Key = "Key";
    private static String dwn_identify = "identify";
    private static String dwn_warnType = "warnType";
    private static String dwn_fieldCode = "fieldCode";
    private static String dwn_fieldSettingCode = "fieldSettingCode";
    private static String dwn_isSave = "isSave";
    private Class<DataWarningDefine> implClass = DataWarningDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtils.class;
    }

    public int insertDefine(DataWarningDefine dataWarningDefine) throws Exception {
        return this.insert(dataWarningDefine);
    }

    public int updateDefine(DataWarningDefine dataWarningDefine) throws Exception {
        return this.update(dataWarningDefine);
    }

    public int[] updateDefineByList(List<DataWarningDefine> dataWarningDefines) throws Exception {
        return this.update(dataWarningDefines.toArray());
    }

    public int deleteDefineById(String id) throws Exception {
        return this.delete(id);
    }

    public int deleteDefineByKeyAndIsSave(String key, Boolean isSave) throws Exception {
        return this.deleteBy(new String[]{dwn_Key, dwn_isSave}, new Object[]{key, isSave});
    }

    public DataWarningDefine getDefineById(String id) throws Exception {
        return (DataWarningDefine)this.getByKey(id, this.implClass);
    }

    public List<DataWarningDefine> getDataWarningslist() throws Exception {
        return this.list(this.implClass);
    }

    public List<DataWarningDefine> getDefinesByKey(String key) throws Exception {
        return this.list(new String[]{dwn_Key}, new Object[]{key}, this.implClass);
    }

    public List<DataWarningDefine> getDefinesByKeyAndIdentify(String key, DataWarningIdentify identify) throws Exception {
        return this.list(new String[]{dwn_Key, dwn_identify}, new Object[]{key, identify}, this.implClass);
    }

    public List<DataWarningDefine> getDefinesByKeyAndFieldCode(String key, String fieldCode) throws Exception {
        return this.list(new String[]{dwn_Key, dwn_fieldCode}, new Object[]{key, fieldCode}, this.implClass);
    }

    public List<DataWarningDefine> getDefinesByKeyAndFieldSettingCode(String key, String fieldSettingCode) throws Exception {
        return this.list(new String[]{dwn_Key, dwn_fieldSettingCode}, new Object[]{key, fieldSettingCode}, this.implClass);
    }

    public List<DataWarningDefine> getDefinesByKeyAndTypeAndIden(String key, DataWarningType type, DataWarningIdentify identify) throws Exception {
        return this.list(new String[]{dwn_Key, dwn_warnType, dwn_identify}, new Object[]{key, type, identify}, this.implClass);
    }

    public List<DataWarningDefine> getDefinesByIdentify(DataWarningIdentify identify) throws Exception {
        return this.list(new String[]{dwn_identify}, new Object[]{identify}, this.implClass);
    }

    public List<DataWarningDefine> getDefinesByTypeAndIdentify(DataWarningType type, DataWarningIdentify identify) throws Exception {
        return this.list(new String[]{dwn_warnType, dwn_identify}, new Object[]{type, identify}, this.implClass);
    }
}

