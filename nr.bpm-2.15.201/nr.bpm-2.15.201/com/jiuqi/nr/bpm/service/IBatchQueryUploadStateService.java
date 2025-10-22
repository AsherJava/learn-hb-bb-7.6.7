/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.bpm.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.bpm.common.UploadRecord;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UploadStateVO;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface IBatchQueryUploadStateService {
    public List<UploadRecord> queryUploadActions(String var1, String var2, String var3, FormSchemeDefine var4);

    public void deleteUploadRecord(BusinessKey var1);

    public void insertUploadRecord(BusinessKey var1, UploadRecordNew var2);

    public void deleteUploadState(BusinessKey var1);

    public void insertUploadState(BusinessKey var1, UploadStateNew var2);

    public List<UploadStateVO> queryUploadStates(FormSchemeDefine var1);

    public UploadSumNew queryUploadSumNew(DimensionValueSet var1, String var2, FormSchemeDefine var3, boolean var4, String var5, String var6, EntityViewDefine var7, IEntityTable var8, boolean var9, boolean var10, boolean var11, Calendar var12) throws Exception;

    public UploadSumNew queryVirtualUploadSumNew(DimensionValueSet var1, String var2, FormSchemeDefine var3, boolean var4, String var5, String var6, EntityViewDefine var7, IEntityTable var8, boolean var9, boolean var10, boolean var11, Calendar var12) throws Exception;

    public List<UploadAllFormSumInfo> queryAllFormState(DimensionValueSet var1, String var2, FormSchemeDefine var3, List<String> var4, WorkFlowType var5, Map<String, UploadAllFormSumInfo> var6);

    public List<UploadRecordNew> queryUploadActionsNew(DimensionValueSet var1, String var2, FormSchemeDefine var3);

    public List<UploadRecordNew> queryUploadActionsNew(DimensionValueSet var1, String var2, FormSchemeDefine var3, boolean var4);

    public UploadRecordNew queryLatestUploadAction(DimensionValueSet var1, String var2, String var3, FormSchemeDefine var4);

    public UploadRecordNew queryLatestUploadAction(DimensionValueSet var1, String var2, String var3, FormSchemeDefine var4, boolean var5);

    public List<UploadRecordNew> queryUploadActionsNew(FormSchemeDefine var1);

    public ActionStateBean queryUploadState(DimensionValueSet var1, String var2, String var3, FormSchemeDefine var4);

    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine var1);

    public UploadStateNew queryUploadStateNew(DimensionValueSet var1, String var2, FormSchemeDefine var3);

    public UploadStateNew queryUploadStateNew(DimensionValueSet var1, String var2, String var3, FormSchemeDefine var4);

    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine var1, DimensionValueSet var2, List<String> var3);

    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine var1, DimensionValueSet var2, List<String> var3, List<String> var4);

    public Map<DimensionValueSet, ActionStateBean> queryUploadStates(DimensionValueSet var1, String var2, String var3, FormSchemeDefine var4);

    public Map<DimensionValueSet, ActionStateBean> queryUploadStates(DimensionValueSet var1, List<String> var2, List<String> var3, FormSchemeDefine var4);

    public List<UploadStateNew> queryUploadDelay(FormSchemeDefine var1, DimensionValueSet var2, String var3, Calendar var4);

    public List<UploadRecordNew> queryUploadHisState(FormSchemeDefine var1, DimensionValueSet var2, List<String> var3);

    public List<UploadRecordNew> queryHisUploadStates(FormSchemeDefine var1, DimensionValueSet var2, String var3, String var4, String var5);

    public List<UploadRecordNew> queryHisUploadStates(FormSchemeDefine var1, DimensionValueSet var2, List<String> var3, List<String> var4, String var5);

    public Map<DimensionValueSet, ActionStateBean> queryUploadAfterState(DimensionValueSet var1, String var2, FormSchemeDefine var3);

    public void deleteHistoryStateData(FormSchemeDefine var1);

    public void deleteStateData(FormSchemeDefine var1);

    public int queryStateData(FormSchemeDefine var1);

    public int queryHistoryStateData(FormSchemeDefine var1);

    public List<UploadStateNew> queryUploadStateNew(FormSchemeDefine var1, String var2, String var3);

    public UploadStateNew queryUploadState(FormSchemeDefine var1, DimensionValueSet var2, String var3, String var4, String var5);

    public List<UploadStateNew> queryUploadState(FormSchemeDefine var1, DimensionValueSet var2, List<String> var3, List<String> var4, String var5);

    public List<UploadRecordNew> queryHisUploadStates(FormSchemeDefine var1, DimensionValueSet var2, String var3, List<String> var4);

    public UploadSumNew queryUploadSum(DimensionValueSet var1, String var2, FormSchemeDefine var3, boolean var4, String var5, String var6, EntityViewDefine var7, IEntityTable var8, boolean var9, boolean var10, boolean var11, Calendar var12, Map<String, List<String>> var13) throws Exception;

    public UploadSumNew queryVirtualUploadSumNew(DimensionValueSet var1, String var2, FormSchemeDefine var3, boolean var4, String var5, String var6, EntityViewDefine var7, IEntityTable var8, boolean var9, boolean var10, boolean var11, Calendar var12, Map<String, List<String>> var13) throws Exception;
}

