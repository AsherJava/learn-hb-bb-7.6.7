/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.common.service.ImpSettings
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.checkdes.internal.ctxt;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpFailedInfo;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ImpContext {
    private final CommonUtil commonUtil;
    private final CKDImpPar impPar;
    private ImpSettings impSettings;
    private final IProviderStore providerStore;
    private CKDImpMes ckdImpMes;
    private FormSchemeDefine formSchemeDefine;
    private String userId;
    private String userNickName;
    private Date impDate;
    private List<String> fsAllForms;
    private Map<String, FormulaSchemeDefine> formulaSchemeDefines;
    private final Map<String, Map<String, IParsedExpression>> parsedExpressionMap = new HashMap<String, Map<String, IParsedExpression>>();
    private final Map<String, String> dimNameMap = new HashMap<String, String>();
    private final Map<String, TableModelRunInfo> tableModelRunInfoMap = new HashMap<String, TableModelRunInfo>();
    private final Map<String, String> dataFieldDimNameMap = new HashMap<String, String>();
    private ExecutorContext executorContext;

    public ImpContext(CommonUtil commonUtil, CKDImpPar impPar, IProviderStore providerStore) {
        this.commonUtil = commonUtil;
        this.impPar = impPar;
        this.providerStore = providerStore;
        this.init();
    }

    public ImpContext(CommonUtil commonUtil, CKDImpPar impPar, ImpSettings impSettings, IProviderStore providerStore) {
        this.commonUtil = commonUtil;
        this.impPar = impPar;
        this.impSettings = impSettings;
        this.providerStore = providerStore;
        this.init();
    }

    private void init() {
        NpContext context = NpContextHolder.getContext();
        this.userId = context.getUserId();
        this.userNickName = this.commonUtil.getUserNickNameById(this.userId);
        this.impDate = new Date();
        this.ckdImpMes = new CKDImpMes();
        ArrayList<ImpFailedInfo> failedInfos = new ArrayList<ImpFailedInfo>();
        this.ckdImpMes.setFailedInfos(failedInfos);
        this.executorContext = this.commonUtil.getHelper().getExecutorContext(this.getFormSchemeDefine());
    }

    public CKDImpPar getImpPar() {
        return this.impPar;
    }

    public CKDImpMes getCkdImpMes() {
        return this.ckdImpMes;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        if (this.formSchemeDefine == null) {
            this.formSchemeDefine = this.commonUtil.getFormSchemeDefine(this.impPar.getFormSchemeKey());
        }
        return this.formSchemeDefine;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public Date getImpDate() {
        return this.impDate;
    }

    public List<String> getFsAllForms() {
        if (this.fsAllForms == null) {
            this.fsAllForms = this.commonUtil.getFSAllForms(this.impPar.getFormSchemeKey());
        }
        return this.fsAllForms;
    }

    public Map<String, FormulaSchemeDefine> getFormulaSchemeDefines() {
        if (this.formulaSchemeDefines == null) {
            List<FormulaSchemeDefine> fsAllFLSs = this.commonUtil.getFSAllFLSs(this.impPar.getFormSchemeKey());
            this.formulaSchemeDefines = CollectionUtils.isEmpty(fsAllFLSs) ? Collections.emptyMap() : fsAllFLSs.stream().collect(Collectors.toMap(IBaseMetaItem::getTitle, Function.identity(), (o1, o2) -> o1));
        }
        return this.formulaSchemeDefines;
    }

    public IParsedExpression getParsedExpression(String formulaSchemeKey, String formulaCode, int globRow, int globCol) {
        if (this.parsedExpressionMap.containsKey(formulaSchemeKey)) {
            Map<String, IParsedExpression> stringIParsedExpressionMap = this.parsedExpressionMap.get(formulaSchemeKey);
            return stringIParsedExpressionMap.get(ImpContext.getPEKey(formulaCode, globRow, globCol));
        }
        List parsedExpressions = this.commonUtil.getFormulaRunTimeController().getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK);
        if (CollectionUtils.isEmpty(parsedExpressions)) {
            this.parsedExpressionMap.put(formulaSchemeKey, Collections.emptyMap());
            return null;
        }
        Map collect = parsedExpressions.stream().collect(Collectors.toMap(o -> ImpContext.getPEKey(o.getSource().getCode(), o.getRealExpression().getWildcardRow(), o.getRealExpression().getWildcardCol()), Function.identity(), (o1, o2) -> o1));
        this.parsedExpressionMap.put(formulaSchemeKey, collect);
        return (IParsedExpression)collect.get(ImpContext.getPEKey(formulaCode, globRow, globCol));
    }

    private static String getPEKey(String formulaCode, int globRow, int globCol) {
        return formulaCode + "-" + globRow + "-" + globCol;
    }

    public String getDimName(String entityId) {
        if (this.dimNameMap.containsKey(entityId)) {
            return this.dimNameMap.get(entityId);
        }
        String dimensionName = this.commonUtil.getEntityMetaService().getDimensionName(entityId);
        this.dimNameMap.put(entityId, dimensionName);
        return dimensionName;
    }

    public String getDimensionName(String dataTableCode, String dataFieldCode) {
        String dfDimNameKey = ImpContext.getDFDimNameKey(dataTableCode, dataFieldCode);
        if (this.dataFieldDimNameMap.containsKey(dfDimNameKey)) {
            return this.dataFieldDimNameMap.get(dfDimNameKey);
        }
        TableModelRunInfo tableModelRunInfo = null;
        if (this.tableModelRunInfoMap.containsKey(dataTableCode)) {
            tableModelRunInfo = this.tableModelRunInfoMap.get(dataTableCode);
        } else {
            tableModelRunInfo = this.commonUtil.getHelper().getTableModelRunInfo(dataTableCode, this.executorContext);
            this.tableModelRunInfoMap.put(dataTableCode, tableModelRunInfo);
        }
        if (tableModelRunInfo == null) {
            this.dataFieldDimNameMap.put(dfDimNameKey, null);
            return null;
        }
        ColumnModelDefine dataFieldColumn = this.commonUtil.getHelper().getDataFieldColumn(tableModelRunInfo, dataTableCode, dataFieldCode);
        String dimensionName = tableModelRunInfo.getDimensionName(dataFieldColumn.getCode());
        this.dataFieldDimNameMap.put(dfDimNameKey, dimensionName);
        return dimensionName;
    }

    private static String getDFDimNameKey(String dataTableCode, String dataFieldCode) {
        return dataTableCode + "-" + dataFieldCode;
    }

    public CommonUtil getCommonUtil() {
        return this.commonUtil;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public ImpSettings getImpSettings() {
        return this.impSettings;
    }
}

