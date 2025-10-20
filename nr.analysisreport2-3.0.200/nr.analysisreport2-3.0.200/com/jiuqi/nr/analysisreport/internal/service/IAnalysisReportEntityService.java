/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.analysisreport.internal.service;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.helper.GenerateContext;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAnalysisReportEntityService
extends IEntityUpgrader {
    public IEntityDefine queryEntityDefine(String var1) throws Exception;

    public IEntityDefine queryEntityDefineByCode(String var1) throws Exception;

    public IEntityDefine queryEntityDefineByEntityId(String var1) throws Exception;

    public IPeriodEntity queryPeriodEntityByEntityId(String var1) throws Exception;

    public IEntityModel queryEntityModel(String var1);

    public IEntityAttribute queryBizKeyField(String var1);

    public List<TaskDefine> getAllTaskDefines();

    public boolean isPeriodEntity(String var1);

    public List<FormSchemeDefine> queryFormSchemeByTask(String var1) throws Exception;

    public List<FormGroupDefine> queryRootGroupsByFormScheme(String var1);

    public List<FormDefine> getAllFormsInGroup(String var1) throws Exception;

    public FormDefine queryFormById(String var1);

    public List<FormSchemeDefine> queryFormSchemeByForm(String var1) throws Exception;

    public List<FormDefine> queryFormsById(List<String> var1);

    public TaskDefine queryTaskDefine(String var1);

    public List<TaskDefine> getAllRuntimeTaskDefines();

    public Set<Map<String, Object>> getEntityDefineByFormula(String var1) throws ParseException, Exception;

    public Set<DimensionObj> getDimensionByFormula(String var1) throws ParseException, Exception;

    public Set<String> getEntityViewKeyByFormula(String var1, GenerateContext var2) throws ParseException, Exception;

    public Map<String, Map<String, Object>> getDimensionDefine(String var1);

    public Map<String, String> queryEntityData(String var1, String var2, String var3, ExecutorContext var4);

    public List<DimensionObj> makeDimensionObject(String var1) throws Exception;

    public EntityViewDefine buildEntityViewDefine(String var1);

    public Set<String> getDataSchemeByFormula(String var1, Map<String, Object> var2);
}

