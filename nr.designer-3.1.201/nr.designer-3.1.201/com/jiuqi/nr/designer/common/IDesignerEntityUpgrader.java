/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.facade.analysis.DimensionInfo
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.designer.common;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.designer.web.facade.EntityObj;
import com.jiuqi.nr.designer.web.facade.SaveEntityVO;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.List;
import java.util.Map;

public interface IDesignerEntityUpgrader
extends IEntityUpgrader {
    public void saveEnum(SaveEntityVO var1) throws Exception;

    public EntityObj initEnum() throws JQException;

    public EntityObj getEnum(String var1) throws Exception;

    public void deleteEnum(String var1) throws Exception;

    public List<IEntityRow> queryEnumData(String var1) throws JQException, Exception;

    public DesignTableDefine getDesignEnumDefineByField(DesignFieldDefine var1) throws JQException;

    public TableDefine getRunTimeEnumDefineByField(FieldDefine var1) throws Exception;

    public IEntityTable getTableData(String var1);

    public IEntityTable getTableDataDefaultAllFields(String var1);

    public String getCaptionFieldsKeys(DesignTableDefine var1) throws JQException;

    public List<String> getEntityList(String[] var1);

    public DimensionInfo createDimension_Copy_Properties(TableDefine var1, String var2);

    public List<IEntityDefine> getAllEntityList() throws JQException;

    public List<IPeriodEntity> getAllPeriodList() throws JQException;

    public void queryData_Business_Period_Copy_Properties(Map<String, String> var1, Map<String, String> var2, DesignTableDefine var3) throws Exception;

    public boolean judgementEntity(TableKind var1);

    public boolean judgementDictionary(TableKind var1);

    public boolean judgementPeriod(String var1);

    public List<IPeriodRow> queryNoTimePeriodData(String var1) throws JQException;

    public String getFieldByTableID(String var1);
}

