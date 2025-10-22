/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PushFloatData
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -7250603929950910469L;

    public PushFloatData() {
        this.registerParameter();
    }

    private void registerParameter() {
        List parameters = this.parameters();
        parameters.add(new Parameter("DestTable", 6, "\u76ee\u6807\u8868\u6807\u8bc6\uff0c\u5fc5\u987b\u6709\u8868\u5185\u7ef4\u5ea6\u4e14\u4e0d\u53ef\u91cd\u7801"));
        parameters.add(new Parameter("SrcTable", 6, "\u6570\u636e\u6765\u6e90\u8868\u6807\u8bc6"));
        parameters.add(new Parameter("DimMapping", 6, "\u76ee\u6807\u8868\u7684\u8868\u5185\u7ef4\u5ea6\u5b57\u6bb5\u6620\u5c04\uff0c\u7528\u4e8e\u5bf9\u5e94\u6765\u6e90\u884c\u548c\u76ee\u6807\u884c"));
        parameters.add(new Parameter("FieldMapping", 6, "\u76ee\u6807\u8868\u7684\u975e\u7ef4\u5ea6\u5b57\u6bb5\u6620\u5c04\uff0c\u7528\u4e8e\u5bf9\u975e\u7ef4\u5ea6\u5b57\u6bb5\u8d4b\u503c"));
        parameters.add(new Parameter("ChildCodeField", 6, "\u76ee\u6807\u8868\u4e2d\u4e0b\u7ea7\u5355\u4f4d\u6807\u8bc6\u5b57\u6bb5\u540d\u3002\u53ef\u9009\u53c2\u6570\uff0c\u8bbe\u7f6e\u540e\u4f7f\u7528\u5168\u91cf\u8986\u76d6\u65b9\u5f0f", true));
    }

    public String name() {
        return "PushFloatData";
    }

    public String title() {
        return "\u4e0b\u7ea7\u6d6e\u52a8\u8868\u6570\u636e\u63a8\u9001\u7ed9\u76f4\u63a5\u4e0a\u7ea7\u7684\u6d6e\u52a8\u8868";
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return new PushFloatDataExcutor((QueryContext)context, parameters).excute();
    }

    public boolean isDeprecated() {
        return true;
    }

    private static class PushFloatDataExcutor {
        private QueryContext queryContext;
        private List<IASTNode> parameters;
        private TableDefine destTable;
        private DataTable dataTable;
        private TableDefine srcTable;
        private FieldDefine[] dimFields;
        private Object[] dimFieldMappings;
        private String[] dimNames;
        private FieldDefine[] pushFields;
        private Object[] pushFieldMappings;
        private Collection<FieldDefine> noPushFields;
        private IDataDefinitionRuntimeController dataDefinitionController;
        private IRuntimeDataSchemeService dataSchemeService;
        private IDataAccessProvider accessProvider;
        private FieldDefine childCodeField;
        private int floatOrderBase = 0;

        public PushFloatDataExcutor(QueryContext queryContext, List<IASTNode> parameters) {
            this.queryContext = queryContext;
            this.parameters = parameters;
            this.dataDefinitionController = queryContext.getExeContext().getRuntimeController();
            this.dataSchemeService = (IRuntimeDataSchemeService)SpringBeanProvider.getBean(IRuntimeDataSchemeService.class);
            this.accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        }

        public int excute() throws SyntaxException {
            this.parseTable();
            this.parseDimMapping();
            this.parseFieldMapping();
            this.parseChildCodeField();
            return this.pushData();
        }

        private void parseTable() throws SyntaxException {
            String destTableCode = (String)this.parameters.get(0).evaluate((IContext)this.queryContext);
            if (destTableCode == null || destTableCode.length() == 0) {
                throw new SyntaxException("\u76ee\u6807\u8868\u6807\u8bc6\u4e3a\u7a7a\u3002");
            }
            try {
                this.destTable = this.dataDefinitionController.queryTableDefineByCode(destTableCode);
            }
            catch (Exception e) {
                throw new SyntaxException("\u76ee\u6807\u8868" + destTableCode + "\u67e5\u8be2\u9519\u8bef\u3002", (Throwable)e);
            }
            if (this.destTable == null) {
                throw new SyntaxException("\u76ee\u6807\u8868" + destTableCode + "\u4e0d\u5b58\u5728\u3002");
            }
            this.dataTable = this.dataSchemeService.getDataTableByCode(this.destTable.getCode());
            if (this.dataTable.getRepeatCode().booleanValue()) {
                throw new SyntaxException("\u76ee\u6807\u8868\u5fc5\u987b\u662f\u4e0d\u53ef\u91cd\u7801\u7684\u8868.");
            }
            String srcTableCode = (String)this.parameters.get(1).evaluate((IContext)this.queryContext);
            if (srcTableCode == null || srcTableCode.length() == 0) {
                throw new SyntaxException("\u6765\u6e90\u8868\u6807\u8bc6\u4e3a\u7a7a\u3002");
            }
            try {
                this.srcTable = this.dataDefinitionController.queryTableDefineByCode(srcTableCode);
            }
            catch (Exception e) {
                throw new SyntaxException("\u6765\u6e90\u8868" + srcTableCode + "\u67e5\u8be2\u9519\u8bef\u3002", (Throwable)e);
            }
            if (this.srcTable == null) {
                throw new SyntaxException("\u6765\u6e90\u8868" + srcTableCode + "\u4e0d\u5b58\u5728\u3002");
            }
        }

        private void parseDimMapping() throws SyntaxException {
            int i;
            String mappingText = (String)this.parameters.get(2).evaluate((IContext)this.queryContext);
            HashMap<String, String> mappings = new HashMap<String, String>();
            if (mappingText != null && mappingText.length() > 0) {
                for (String item : mappingText.split(",")) {
                    String[] mapping = item.split("=");
                    if (mapping.length < 2) continue;
                    String fieldCode = mapping[0].trim();
                    String mappingExp = mapping[1].trim();
                    if (fieldCode == null || fieldCode.length() == 0 || mappingExp == null || mappingExp.length() == 0) continue;
                    mappings.put(fieldCode.toUpperCase(), mappingExp);
                }
            }
            List dimDataFields = this.dataSchemeService.getDataFieldByTableKeyAndKind(this.dataTable.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
            this.dimFields = new FieldDefine[dimDataFields.size()];
            this.dimFieldMappings = new Object[dimDataFields.size()];
            this.dimNames = new String[dimDataFields.size()];
            IDataAssist dataAssist = this.accessProvider.newDataAssist(this.queryContext.getExeContext());
            for (i = 0; i < dimDataFields.size(); ++i) {
                FieldDefine dimField;
                this.dimFields[i] = dimField = this.getFieldInDestTable(((DataField)dimDataFields.get(i)).getCode());
                this.dimNames[i] = dataAssist.getDimensionName(dimField);
            }
            for (i = 0; i < this.dimFields.length; ++i) {
                String mappingExp = (String)mappings.get(this.dimFields[i].getCode().toUpperCase());
                if (mappingExp == null) {
                    throw new SyntaxException("\u7ef4\u5ea6\u5b57\u6bb5" + this.dimFields[i].getCode() + "\u7f3a\u5c11\u6620\u5c04\u3002");
                }
                this.dimFieldMappings[i] = mappingExp;
            }
        }

        private void parseFieldMapping() throws SyntaxException {
            String mappingText = (String)this.parameters.get(3).evaluate((IContext)this.queryContext);
            HashMap<String, String> mappings = new HashMap<String, String>();
            if (mappingText != null && mappingText.length() > 0) {
                for (String item : mappingText.split(",")) {
                    String[] stringArray = item.split("=");
                    if (stringArray.length < 2) continue;
                    String fieldCode = stringArray[0].trim();
                    String mappingExp = stringArray[1].trim();
                    if (fieldCode == null || fieldCode.length() == 0 || mappingExp == null || mappingExp.length() == 0) continue;
                    mappings.put(fieldCode.toUpperCase(), mappingExp);
                }
            }
            List dataFields = this.dataSchemeService.getDataFieldByTableKeyAndKind(this.dataTable.getKey(), new DataFieldKind[]{DataFieldKind.FIELD});
            HashMap<String, FieldDefine> restFieldMap = new HashMap<String, FieldDefine>(dataFields.size());
            for (DataField dataField : dataFields) {
                restFieldMap.put(dataField.getCode(), this.getFieldInDestTable(dataField.getCode()));
            }
            this.pushFields = new FieldDefine[mappings.size()];
            this.pushFieldMappings = new Object[mappings.size()];
            int i = 0;
            for (Map.Entry entry : mappings.entrySet()) {
                FieldDefine field = (FieldDefine)restFieldMap.remove(entry.getKey());
                if (field == null) {
                    throw new SyntaxException("\u6620\u5c04\u914d\u7f6e\u9519\u8bef\uff0c\u5b57\u6bb5" + (String)entry.getKey() + "\u4e0d\u5b58\u5728\u3002");
                }
                this.pushFields[i] = field;
                this.pushFieldMappings[i] = entry.getValue();
                ++i;
            }
            this.noPushFields = restFieldMap.values();
        }

        private void parseChildCodeField() throws SyntaxException {
            if (this.parameters.size() < 5) {
                return;
            }
            String childCodeFiledCode = (String)this.parameters.get(4).evaluate((IContext)this.queryContext);
            if (childCodeFiledCode == null || childCodeFiledCode.length() == 0) {
                return;
            }
            try {
                this.childCodeField = this.dataDefinitionController.queryFieldByCodeInTable(childCodeFiledCode, this.srcTable.getKey());
            }
            catch (Exception e) {
                throw new SyntaxException("\u4e0b\u7ea7\u5355\u4f4d\u6807\u8bc6\u5b57\u6bb5" + childCodeFiledCode + "\u67e5\u8be2\u9519\u8bef\u3002");
            }
            if (this.childCodeField == null) {
                throw new SyntaxException("\u4e0b\u7ea7\u5355\u4f4d\u6807\u8bc6\u5b57\u6bb5" + childCodeFiledCode + "\u4e0d\u5b58\u5728\u3002");
            }
        }

        private int pushData() throws SyntaxException {
            IDataQuery srcDataQuery = this.accessProvider.newDataQuery();
            DimensionValueSet srcDVS = this.queryContext.getCurrentMasterKey();
            srcDataQuery.setMasterKeys(srcDVS);
            for (Object fieldMapping : this.dimFieldMappings) {
                if (fieldMapping instanceof String) {
                    srcDataQuery.addExpressionColumn((String)fieldMapping);
                    continue;
                }
                if (fieldMapping instanceof FieldDefine) {
                    srcDataQuery.addColumn((FieldDefine)fieldMapping);
                    continue;
                }
                throw new IllegalArgumentException("unrecognized fieldMapping\u3002");
            }
            for (Object fieldMapping : this.pushFieldMappings) {
                if (fieldMapping instanceof String) {
                    srcDataQuery.addExpressionColumn((String)fieldMapping);
                    continue;
                }
                if (fieldMapping instanceof FieldDefine) {
                    srcDataQuery.addColumn((FieldDefine)fieldMapping);
                    continue;
                }
                throw new IllegalArgumentException("unrecognized fieldMapping\u3002");
            }
            srcDataQuery.addColumn(this.getFieldInSrcTable("FLOATORDER"));
            int srcOrderFieldIndex = this.dimFields.length + this.pushFields.length;
            DimensionValueSet parentMainDimensions = this.getParentMainDemension();
            if (parentMainDimensions == null) {
                return 0;
            }
            DimensionValueSet destDVS = new DimensionValueSet(srcDVS);
            destDVS.combine(parentMainDimensions);
            IDataQuery destDataQuery = this.accessProvider.newDataQuery();
            destDataQuery.setMasterKeys(destDVS);
            for (FieldDefine field : this.dimFields) {
                destDataQuery.addColumn(field);
            }
            for (FieldDefine field : this.pushFields) {
                destDataQuery.addColumn(field);
            }
            destDataQuery.addColumn(this.getFieldInDestTable("FLOATORDER"));
            destDataQuery.addColumn(this.getFieldInDestTable("BIZKEYORDER"));
            int destOrderFieldIndex = this.dimFields.length + this.pushFields.length;
            int destBizkeyFieldIndex = destOrderFieldIndex + 1;
            for (FieldDefine field : this.noPushFields) {
                destDataQuery.addColumn(field);
            }
            if (this.childCodeField == null) {
                try {
                    IReadonlyTable srcDataTable = srcDataQuery.executeReader(this.queryContext.getExeContext());
                    IDataTable destDataTable = destDataQuery.executeQuery(this.queryContext.getExeContext());
                    int c = srcDataTable.getCount();
                    for (int i = 0; i < c; ++i) {
                        IDataRow srcRow = srcDataTable.getItem(i);
                        DimensionValueSet destRowKeys = new DimensionValueSet(destDVS);
                        for (int j = 0; j < this.dimFields.length; ++j) {
                            destRowKeys.setValue(this.dimNames[j], srcRow.getValue(j).getAsObject());
                        }
                        IDataRow destRow = destDataTable.findRow(destRowKeys);
                        if (destRow == null) {
                            destRow = destDataTable.appendRow(destRowKeys);
                            destRow.setValue(destBizkeyFieldIndex, (Object)UUID.randomUUID().toString());
                        }
                        for (int j = this.dimFields.length; j < this.dimFields.length + this.pushFields.length; ++j) {
                            destRow.setValue(j, srcRow.getValue(j).getAsObject());
                        }
                        destRow.setValue(destOrderFieldIndex, (Object)this.generateDestRowOrder(srcRow.getValue(srcOrderFieldIndex)));
                    }
                    destDataTable.commitChanges(true);
                    return srcDataTable.getCount();
                }
                catch (Exception e) {
                    throw new SyntaxException("\u6570\u636e\u63d0\u4ea4\u5931\u8d25.", (Throwable)e);
                }
            }
            IDataQuery destDataDeleteQuery = this.accessProvider.newDataQuery();
            destDataDeleteQuery.setMasterKeys(destDVS);
            destDataDeleteQuery.setRowFilter(this.srcTable.getCode() + "[" + this.childCodeField.getCode() + "]=\"" + this.getCurrentMainDemensionValue() + "\"");
            destDataDeleteQuery.addColumn(this.childCodeField);
            try {
                IDataUpdator dataUpdater = destDataDeleteQuery.openForUpdate(this.queryContext.getExeContext(), true);
                dataUpdater.deleteAll();
                dataUpdater.commitChanges(true);
            }
            catch (Exception e) {
                throw new SyntaxException("\u6570\u636e\u5220\u9664\u5931\u8d25.", (Throwable)e);
            }
            try {
                IReadonlyTable srcDataTable = srcDataQuery.executeReader(this.queryContext.getExeContext());
                IDataUpdator destDataUpdator = destDataQuery.openForUpdate(this.queryContext.getExeContext());
                int c = srcDataTable.getCount();
                for (int i = 0; i < c; ++i) {
                    IDataRow srcRow = srcDataTable.getItem(i);
                    DimensionValueSet destRowKeys = new DimensionValueSet(destDVS);
                    for (int j = 0; j < this.dimFields.length; ++j) {
                        destRowKeys.setValue(this.dimNames[j], srcRow.getValue(j).getAsObject());
                    }
                    IDataRow destRow = destDataUpdator.addInsertedRow(destRowKeys);
                    destRow.setValue(destBizkeyFieldIndex, (Object)UUID.randomUUID().toString());
                    for (int j = this.dimFields.length; j < this.dimFields.length + this.pushFields.length; ++j) {
                        destRow.setValue(j, srcRow.getValue(j).getAsObject());
                    }
                    destRow.setValue(destOrderFieldIndex, (Object)this.generateDestRowOrder(srcRow.getValue(srcOrderFieldIndex)));
                }
                destDataUpdator.commitChanges(true);
                return srcDataTable.getCount();
            }
            catch (Exception e) {
                throw new SyntaxException("\u6570\u636e\u63d0\u4ea4\u5931\u8d25.", (Throwable)e);
            }
        }

        private DimensionValueSet getParentMainDemension() throws SyntaxException {
            IEntityTable entityTable;
            ReportFmlExecEnvironment execEnv = (ReportFmlExecEnvironment)this.queryContext.getExeContext().getEnv();
            String mainDimName = execEnv.getUnitDimesion(this.queryContext.getExeContext());
            EntityViewDefine entityView = execEnv.getEntityViewDefine(this.queryContext.getExeContext(), mainDimName);
            IEntityDataService entityDataService = (IEntityDataService)SpringBeanProvider.getBean(IEntityDataService.class);
            DimensionValueSet periodDVS = new DimensionValueSet();
            periodDVS.setValue("DATATIME", this.queryContext.getCurrentMasterKey().getValue("DATATIME"));
            String currentEntityKey = (String)this.queryContext.getCurrentMasterKey().getValue(mainDimName);
            IEntityQuery entityQuery = entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityView);
            entityQuery.setMasterKeys(periodDVS);
            entityQuery.setAuthorityOperations(AuthorityType.None);
            ExecutorContext entityQueryCtx = new ExecutorContext(this.dataDefinitionController);
            entityQueryCtx.setPeriodView(this.getFormSchemeDefine().getDateTime());
            try {
                entityTable = entityQuery.executeReader((IContext)entityQueryCtx);
            }
            catch (Exception e) {
                throw new SyntaxException("\u5b9e\u4f53\u67e5\u8be2\u9519\u8bef\u3002", (Throwable)e);
            }
            IEntityRow entityRow = entityTable.findByEntityKey(currentEntityKey);
            if (entityRow == null) {
                throw new SyntaxException("\u5b9e\u4f53\u67e5\u8be2\u9519\u8bef\uff0c\u672a\u627e\u5230\u5f53\u524d\u5355\u4f4d\u3002");
            }
            String parentEntityKey = entityRow.getParentEntityKey();
            if (parentEntityKey == null || parentEntityKey.length() == 0 || parentEntityKey.equals("-")) {
                return null;
            }
            List childRows = entityTable.getChildRows(parentEntityKey);
            for (int i = 0; i < childRows.size(); ++i) {
                if (!((IEntityRow)childRows.get(i)).getEntityKeyData().equals(currentEntityKey)) continue;
                this.floatOrderBase = (i + 1) * 100000000;
                break;
            }
            DimensionValueSet dvs = new DimensionValueSet();
            dvs.setValue(mainDimName, (Object)parentEntityKey);
            return dvs;
        }

        private String getCurrentMainDemensionValue() throws SyntaxException {
            ReportFmlExecEnvironment exeContext = (ReportFmlExecEnvironment)this.queryContext.getExeContext().getEnv();
            String mainDimName = exeContext.getUnitDimesion(this.queryContext.getExeContext());
            return (String)this.queryContext.getCurrentMasterKey().getValue(mainDimName);
        }

        private double generateDestRowOrder(AbstractData srcRowOrder) {
            return (double)this.floatOrderBase + srcRowOrder.getAsFloat();
        }

        private FieldDefine getFieldInDestTable(String fieldCode) throws SyntaxException {
            try {
                return this.dataDefinitionController.queryFieldByCodeInTable(fieldCode, this.destTable.getKey());
            }
            catch (Exception e) {
                throw new SyntaxException("\u67e5\u8be2\u5b57\u6bb5" + fieldCode + "\u5931\u8d25\u3002", (Throwable)e);
            }
        }

        private FieldDefine getFieldInSrcTable(String fieldCode) throws SyntaxException {
            try {
                return this.dataDefinitionController.queryFieldByCodeInTable(fieldCode, this.srcTable.getKey());
            }
            catch (Exception e) {
                throw new SyntaxException("\u67e5\u8be2\u5b57\u6bb5" + fieldCode + "\u5931\u8d25\u3002", (Throwable)e);
            }
        }

        private FormSchemeDefine getFormSchemeDefine() throws SyntaxException {
            FormSchemeDefine formSchemeDefine;
            ReportFmlExecEnvironment execEnv = (ReportFmlExecEnvironment)this.queryContext.getExeContext().getEnv();
            try {
                formSchemeDefine = execEnv.getFormSchemeDefine();
            }
            catch (Exception e) {
                throw new SyntaxException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u9519\u8bef\u3002", (Throwable)e);
            }
            if (formSchemeDefine == null) {
                throw new SyntaxException("\u672a\u80fd\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u3002");
            }
            return formSchemeDefine;
        }
    }
}

