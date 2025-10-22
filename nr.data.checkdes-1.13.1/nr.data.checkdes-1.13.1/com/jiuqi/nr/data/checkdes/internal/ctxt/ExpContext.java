/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.checkdes.internal.ctxt;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.checkdes.internal.io.BnDimFieldInfo;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;
import com.jiuqi.nr.data.checkdes.util.CommonUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ExpContext {
    private final CommonUtil commonUtil;
    private final CKDExpPar ckdExpPar;
    private final IProviderStore providerStore;
    private FormSchemeDefine formSchemeDefine;
    private String mdDimName;
    private String periodDimName;
    private Map<String, String> dinNameEntityIdMap;
    private Map<String, FormulaSchemeDefine> formulaSchemeDefineMap;
    private final Map<String, String> formKeyCodeMap = new HashMap<String, String>();
    private final Map<String, Map<String, IParsedExpression>> parsedExpressionMap = new HashMap<String, Map<String, IParsedExpression>>();
    private final Map<String, Map<String, BnDimFieldInfo>> bnDimFieldInfoMap = new HashMap<String, Map<String, BnDimFieldInfo>>();
    private final Map<String, List<BnDimFieldInfo>> tableBnDimFieldInfoMap = new HashMap<String, List<BnDimFieldInfo>>();
    private final ExecutorContext executorContext;

    public ExpContext(CommonUtil commonUtil, CKDExpPar ckdExpPar, IProviderStore providerStore) {
        this.commonUtil = commonUtil;
        this.ckdExpPar = ckdExpPar;
        this.providerStore = providerStore;
        this.executorContext = commonUtil.getHelper().getExecutorContext(this.getFormSchemeDefine());
    }

    public FormSchemeDefine getFormSchemeDefine() {
        if (this.formSchemeDefine == null) {
            this.formSchemeDefine = this.commonUtil.getFormSchemeDefine(this.ckdExpPar.getFormSchemeKey());
        }
        return this.formSchemeDefine;
    }

    public CommonUtil getCommonUtil() {
        return this.commonUtil;
    }

    public CKDExpPar getCkdExpPar() {
        return this.ckdExpPar;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public String getMdDimName() {
        if (this.mdDimName == null) {
            this.mdDimName = this.commonUtil.getEntityMetaService().getDimensionName(this.getFormSchemeDefine().getDw());
        }
        return this.mdDimName;
    }

    public String getPeriodDimName() {
        if (this.periodDimName == null) {
            this.periodDimName = this.commonUtil.getHelper().getPeriodEngineService().getPeriodAdapter().getPeriodDimensionName(this.getFormSchemeDefine().getDateTime());
        }
        return this.periodDimName;
    }

    public Map<String, String> getDinNameEntityIdMap() {
        if (this.dinNameEntityIdMap == null) {
            this.dinNameEntityIdMap = new HashMap<String, String>();
            List<String> schemeDimEntityIds = this.commonUtil.getHelper().getSchemeDimEntityIds(this.getFormSchemeDefine());
            for (String schemeDimEntityId : schemeDimEntityIds) {
                if ("ADJUST".equals(schemeDimEntityId)) {
                    this.dinNameEntityIdMap.put("ADJUST", schemeDimEntityId);
                    continue;
                }
                String dimName = this.commonUtil.getEntityMetaService().getDimensionName(schemeDimEntityId);
                this.dinNameEntityIdMap.put(dimName, schemeDimEntityId);
            }
        }
        return this.dinNameEntityIdMap;
    }

    public FormulaSchemeDefine getFmlSchemeByKey(String key) {
        if (this.formulaSchemeDefineMap == null) {
            this.formulaSchemeDefineMap = new HashMap<String, FormulaSchemeDefine>();
            List allRPTFormulaSchemeDefinesByFormScheme = this.commonUtil.getFormulaRunTimeController().getAllRPTFormulaSchemeDefinesByFormScheme(this.getFormSchemeDefine().getKey());
            if (!CollectionUtils.isEmpty(allRPTFormulaSchemeDefinesByFormScheme)) {
                for (FormulaSchemeDefine formulaSchemeDefine : allRPTFormulaSchemeDefinesByFormScheme) {
                    this.formulaSchemeDefineMap.put(formulaSchemeDefine.getKey(), formulaSchemeDefine);
                }
            }
        }
        return this.formulaSchemeDefineMap.get(key);
    }

    public String getFormCodeByKey(String key) {
        if (null == key || "00000000-0000-0000-0000-000000000000".equals(key)) {
            return "FORMULA-MAPPING-BETWEEN";
        }
        if (this.formKeyCodeMap.containsKey(key)) {
            return this.formKeyCodeMap.get(key);
        }
        FormDefine formDefine = this.commonUtil.getHelper().getRunTimeViewController().queryFormById(key);
        String formCode = formDefine == null ? null : formDefine.getFormCode();
        this.formKeyCodeMap.put(key, formCode);
        return formCode;
    }

    public IParsedExpression getParsedExpression(String formulaSchemeKey, String formulaCode, int globRow, int globCol) {
        if (this.parsedExpressionMap.containsKey(formulaSchemeKey)) {
            Map<String, IParsedExpression> stringIParsedExpressionMap = this.parsedExpressionMap.get(formulaSchemeKey);
            return stringIParsedExpressionMap.get(ExpContext.getPEKey(formulaCode, globRow, globCol));
        }
        List parsedExpressions = this.commonUtil.getFormulaRunTimeController().getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK);
        if (CollectionUtils.isEmpty(parsedExpressions)) {
            this.parsedExpressionMap.put(formulaSchemeKey, Collections.emptyMap());
            return null;
        }
        Map collect = parsedExpressions.stream().collect(Collectors.toMap(o -> ExpContext.getPEKey(o.getSource().getCode(), o.getRealExpression().getWildcardRow(), o.getRealExpression().getWildcardCol()), Function.identity(), (o1, o2) -> o1));
        this.parsedExpressionMap.put(formulaSchemeKey, collect);
        return (IParsedExpression)collect.get(ExpContext.getPEKey(formulaCode, globRow, globCol));
    }

    private static String getPEKey(String formulaCode, int globRow, int globCol) {
        return formulaCode + "-" + globRow + "-" + globCol;
    }

    @Nullable
    public BnDimFieldInfo getBnDimFieldInfo(String dimName, IParsedExpression parsedExpression) {
        if (!StringUtils.hasText(dimName) || parsedExpression == null) {
            return null;
        }
        if (this.bnDimFieldInfoMap.containsKey(parsedExpression.getKey())) {
            return this.bnDimFieldInfoMap.get(parsedExpression.getKey()).get(dimName);
        }
        return this.loadBNDimFieldInfo(dimName, parsedExpression);
    }

    @Nullable
    private BnDimFieldInfo loadBNDimFieldInfo(String dimName, IParsedExpression parsedExpression) {
        BnDimFieldInfo bnDimFieldInfo = null;
        Map<String, BnDimFieldInfo> dimNameFieldInfoMap = Collections.emptyMap();
        QueryFields queryFields = parsedExpression.getQueryFields();
        if (queryFields != null) {
            dimNameFieldInfoMap = new HashMap();
            for (QueryField queryField : queryFields) {
                List<BnDimFieldInfo> bnDimFields = this.getBnDimFieldInfos(queryField);
                if (bnDimFields == null) continue;
                for (BnDimFieldInfo bnDimField : bnDimFields) {
                    if (dimNameFieldInfoMap.containsKey(bnDimField.getDimName())) continue;
                    dimNameFieldInfoMap.put(bnDimField.getDimName(), bnDimField);
                    if (bnDimFieldInfo != null || !bnDimField.getDimName().equals(dimName)) continue;
                    bnDimFieldInfo = bnDimField;
                }
            }
        }
        this.bnDimFieldInfoMap.put(parsedExpression.getKey(), dimNameFieldInfoMap);
        return bnDimFieldInfo;
    }

    @Nullable
    private List<BnDimFieldInfo> getBnDimFieldInfos(QueryField queryField) {
        QueryTable table = queryField.getTable();
        if (table == null) {
            return null;
        }
        List<BnDimFieldInfo> bnDimFields = this.getBnDimFields(table.getTableName());
        if (CollectionUtils.isEmpty(bnDimFields)) {
            return null;
        }
        return bnDimFields;
    }

    @NonNull
    private List<BnDimFieldInfo> getBnDimFields(String tableName) {
        if (this.tableBnDimFieldInfoMap.containsKey(tableName)) {
            return this.tableBnDimFieldInfoMap.get(tableName);
        }
        List<BnDimFieldInfo> r = this.queryBnDimFields(tableName);
        this.tableBnDimFieldInfoMap.put(tableName, r);
        return r;
    }

    @NonNull
    private List<BnDimFieldInfo> queryBnDimFields(String tableName) {
        TableModelRunInfo tableModelRunInfo = this.commonUtil.getHelper().getTableModelRunInfoByName(tableName, this.executorContext);
        if (tableModelRunInfo == null) {
            return Collections.emptyList();
        }
        String dataTableKey = this.commonUtil.getHelper().getDataSchemeService().getDataTableByTableModel(tableModelRunInfo.getTableModelDefine().getID());
        if (dataTableKey == null) {
            return Collections.emptyList();
        }
        DataTable dataTable = this.commonUtil.getHelper().getDataSchemeService().getDataTable(dataTableKey);
        if (dataTable == null) {
            return Collections.emptyList();
        }
        List innerDimensions = tableModelRunInfo.getInnerDimensions();
        if (CollectionUtils.isEmpty(innerDimensions)) {
            return Collections.emptyList();
        }
        ArrayList<BnDimFieldInfo> r = new ArrayList<BnDimFieldInfo>();
        for (String innerDimension : innerDimensions) {
            DataField dataField;
            ColumnModelDefine dimensionField = tableModelRunInfo.getDimensionField(innerDimension);
            if (dimensionField == null || (dataField = this.commonUtil.getHelper().getDataSchemeService().getDataFieldByColumnKey(dimensionField.getID())) == null) continue;
            BnDimFieldInfo oneFieldInfo = new BnDimFieldInfo(dataField.getKey(), dataTable.getCode(), dataField.getCode(), dataField.getRefDataEntityKey(), innerDimension);
            r.add(oneFieldInfo);
        }
        return r;
    }
}

