/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.efdc.extract.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.extract.ExtractDataRegion;
import com.jiuqi.nr.efdc.extract.ExtractDataRow;
import com.jiuqi.nr.efdc.extract.IExtractDataUpdator;
import com.jiuqi.nr.efdc.extract.IExtractRequest;
import com.jiuqi.nr.efdc.extract.IExtractResult;
import com.jiuqi.nr.efdc.extract.IRegionParser;
import com.jiuqi.nr.efdc.extract.exception.ExtractException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExtractRequest
implements IExtractRequest {
    protected final Logger logger = LoggerFactory.getLogger(IExtractRequest.class);
    protected FormulaSchemeDefine formulaScheme;
    protected FormDefine form;
    protected String formSchemeKey;
    protected List<ExtractDataRegion> regions = new ArrayList<ExtractDataRegion>();
    private List<String> formulas = null;

    public AbstractExtractRequest(List<String> formulas, String formSchemeKey, FormDefine form) {
        this.formulas = formulas;
        this.formSchemeKey = formSchemeKey;
        this.form = form;
    }

    public AbstractExtractRequest(FormulaSchemeDefine formulaScheme, FormDefine form) {
        this.formulaScheme = formulaScheme;
        if (formulaScheme != null) {
            this.formSchemeKey = formulaScheme.getFormSchemeKey();
        }
        this.form = form;
    }

    @Override
    public List<ExtractDataRegion> doPrepare(ExecutorContext executorContext, Map<String, Object> paras, IFormulaRunTimeController formulaRunTimeController) throws ExtractException {
        if (this.form == null) {
            this.logger.error("\u62a5\u8868\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        ArrayList<DataLinkColumn> allDataLinkColumns = new ArrayList<DataLinkColumn>();
        ArrayList<String> allExpressions = new ArrayList<String>();
        ArrayList<String> formulaKeys = new ArrayList<String>();
        try {
            ReportFormulaParser parser = executorContext.getCache().getFormulaParser(executorContext.isJQReportModel());
            executorContext.setDefaultGroupName(this.form.getFormCode());
            QueryContext qContext = new QueryContext(executorContext, null);
            if (this.formulas != null) {
                for (String formulaExpression : this.formulas) {
                    this.parseFormula(allDataLinkColumns, allExpressions, formulaKeys, "", parser, qContext, formulaExpression);
                }
            } else {
                List allFormulasDefines;
                if (this.formulaScheme == null) {
                    this.logger.error("\u53d6\u6570\u516c\u5f0f\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a\uff01");
                }
                if ((allFormulasDefines = formulaRunTimeController.getAllFormulasInForm(this.formulaScheme.getKey(), this.form.getKey())) == null) {
                    throw new ExtractException("\u6ca1\u6709\u627e\u5230\u5f53\u524d\u516c\u5f0f\u65b9\u6848\u4e2d\u7684\u53d6\u6570\u516c\u5f0f");
                }
                List queryPublicFormulaDefineByScheme = formulaRunTimeController.queryPublicFormulaDefineByScheme(this.formulaScheme.getKey(), this.form.getKey());
                allFormulasDefines.addAll(queryPublicFormulaDefineByScheme);
                for (FormulaDefine formulaDefine : allFormulasDefines) {
                    String formulaExpression = formulaDefine.getExpression();
                    this.parseFormula(allDataLinkColumns, allExpressions, formulaKeys, formulaDefine.getKey(), parser, qContext, formulaExpression);
                }
            }
            if (allExpressions.size() == 0) {
                return null;
            }
            ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)executorContext.getEnv();
            IRunTimeViewController controller = env.getController();
            for (ExtractDataRegion region : this.regions) {
                for (int i = allDataLinkColumns.size() - 1; i >= 0; --i) {
                    DataLinkColumn dataLinkColumn = (DataLinkColumn)allDataLinkColumns.get(i);
                    Position position = dataLinkColumn.getDataPosition();
                    boolean hasFind = false;
                    if (position == null) {
                        if (!StringUtils.isEmpty((String)dataLinkColumn.getRegion())) {
                            List linkDefines = controller.getAllLinksInRegion(dataLinkColumn.getRegion());
                            hasFind = linkDefines.stream().anyMatch(t -> t.getRowNum() == region.getRowIndex() || t.getColNum() == region.getColIndex());
                        }
                    } else if (position.row() == region.getRowIndex() || position.col() == region.getColIndex()) {
                        hasFind = true;
                    }
                    if (!hasFind) continue;
                    region.addColumn((String)allExpressions.get(i), dataLinkColumn, (String)formulaKeys.get(i));
                    allDataLinkColumns.remove(i);
                    allExpressions.remove(i);
                    formulaKeys.remove(i);
                    if (region.getRegiondefine() != null) continue;
                    DataRegionDefine regionDefine = env.getController().queryDataRegionDefine(dataLinkColumn.getRegion());
                    region.setRegiondefine(regionDefine);
                }
            }
            if (allDataLinkColumns.size() > 0) {
                ExtractDataRegion region = new ExtractDataRegion();
                for (int i = 0; i < allDataLinkColumns.size(); ++i) {
                    DataLinkColumn dataLinkColumn = (DataLinkColumn)allDataLinkColumns.get(i);
                    region.addColumn((String)allExpressions.get(i), dataLinkColumn, (String)formulaKeys.get(i));
                }
                this.regions.add(region);
            }
        }
        catch (ExtractException e) {
            this.logger.error("EFDC\u53d6\u6570\uff1a\u89e3\u6790\u51fa\u9519", (Throwable)((Object)e));
            throw e;
        }
        catch (Exception e) {
            this.logger.error("EFDC\u53d6\u6570\uff1a\u89e3\u6790\u51fa\u9519", e);
            throw new ExtractException(e.getMessage(), e);
        }
        return this.regions;
    }

    public void parseFormula(List<DataLinkColumn> allDataLinkColumns, List<String> allExpressions, List<String> formulaKeys, String formulaKey, ReportFormulaParser parser, QueryContext qContext, String formulaExpression) {
        block5: {
            try {
                if (formulaExpression.startsWith("//")) {
                    return;
                }
                int equalIndex = formulaExpression.indexOf("=");
                if (0 >= equalIndex) break block5;
                String assginExp = formulaExpression.substring(0, equalIndex);
                String efdcExp = formulaExpression.substring(equalIndex + 1);
                if (assginExp.indexOf("*") >= 0) {
                    IRegionParser regionParser = this.createRegionParser();
                    ExtractDataRegion region = regionParser.parsre(assginExp);
                    region.setFloatExpression(efdcExp);
                    this.regions.add(region);
                    break block5;
                }
                IExpression fieldExpression = parser.parseEval(assginExp, (IContext)qContext);
                for (IASTNode child : fieldExpression) {
                    if (!(child instanceof DynamicDataNode)) continue;
                    DynamicDataNode dataNode = (DynamicDataNode)child;
                    allDataLinkColumns.add(dataNode.getDataLink());
                    allExpressions.add(efdcExp);
                    formulaKeys.add(formulaKey);
                    break;
                }
            }
            catch (Exception e) {
                this.logger.debug(e.getMessage());
            }
        }
    }

    @Override
    public FormulaSchemeDefine getFormulaSchemeDefine() {
        return this.formulaScheme;
    }

    @Override
    public FormDefine getCurrentReport() {
        return this.form;
    }

    @Override
    public void doExtract(ExecutorContext executorContext, IDataAccessProvider dataAccessProvider, DimensionValueSet dimensionValueSet, Map<String, Object> paras, IExtractDataUpdator dataUpdator) throws ExtractException {
        ArrayList batchDimList = null;
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            Object dimValue = dimensionValueSet.getValue(i);
            if (!(dimValue instanceof List)) continue;
            List valueList = (List)dimValue;
            batchDimList = new ArrayList(valueList.size());
            for (Object value : valueList) {
                DimensionValueSet newDim = new DimensionValueSet(dimensionValueSet);
                newDim.setValue(dimensionValueSet.getName(i), value);
            }
        }
        if (batchDimList != null && batchDimList.size() > 0) {
            for (DimensionValueSet dim : batchDimList) {
                this.extractByDimension(executorContext, dataAccessProvider, paras, dim, dataUpdator);
            }
        } else {
            this.extractByDimension(executorContext, dataAccessProvider, paras, dimensionValueSet, dataUpdator);
        }
    }

    public void extractByDimension(ExecutorContext executorContext, IDataAccessProvider dataAccessProvider, Map<String, Object> paras, DimensionValueSet dim, IExtractDataUpdator dataUpdator) throws ExtractException {
        for (ExtractDataRegion region : this.regions) {
            if (region.getColmumCount() == 0) continue;
            IJtableEntityService entityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
            IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
            try {
                int i;
                IExtractResult result = this.doExtractRegion(executorContext, region, dim, paras);
                this.logger.debug("EFDC\u53d6\u6570\uff1a\u53d6\u5230EFDC\u7ed3\u679c\u96c6");
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                queryEnvironment.setFormSchemeKey(this.formSchemeKey);
                if (paras.containsKey("calSchemeKey")) {
                    queryEnvironment.setFormulaSchemeKey(paras.get("calSchemeKey").toString());
                }
                queryEnvironment.setRegionKey(region.getRegionKey());
                queryEnvironment.setFormKey(this.form.getKey());
                queryEnvironment.setFormCode(this.form.getFormCode());
                StringBuilder buf = new StringBuilder("EFDC\u53d6\u6570\uff1a\u5f00\u59cb\u4fdd\u5b58EFDC\u7ed3\u679c\u96c6\n");
                IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
                dataQuery.setMasterKeys(dim);
                buf.append("\u4e3b\u7ef4\u5ea6\uff1a").append(dim).append("\n");
                if (null != region.getRegiondefine()) {
                    String filterCondition = region.getRegiondefine().getFilterCondition();
                    dataQuery.setRowFilter(filterCondition);
                    if (filterCondition != null && filterCondition.length() > 0) {
                        buf.append("\u6d6e\u52a8\u8fc7\u6ee4\u6761\u4ef6\uff1a").append(filterCondition).append("\n");
                    }
                }
                this.logger.debug(buf.toString());
                for (int i2 = 0; i2 < region.getColmumCount(); ++i2) {
                    FieldDefine fieldDefine = region.getColmumField(i2);
                    dataQuery.addColumn(fieldDefine);
                }
                if (region.isFloat()) {
                    FieldDefine inputOrderField = region.getInputOrderField(executorContext);
                    if (inputOrderField != null) {
                        dataQuery.addColumn(inputOrderField);
                    }
                    IDataTable dataTable = dataQuery.executeQuery(executorContext);
                    boolean updatorResult = false;
                    if (dataUpdator != null) {
                        updatorResult = dataUpdator.changeData(dataTable, dataQuery, executorContext, result, dim, region.getRegiondefine().getFormKey(), region.getRegionKey());
                    }
                    if (updatorResult) {
                        dataTable.commitChanges(true);
                    } else {
                        dataTable.deleteAll();
                        dataTable.commitChanges(false);
                        for (i = 0; i < result.size(); ++i) {
                            ExtractDataRow srcDataRow = result.getRow(i);
                            DimensionValueSet rowKey = new DimensionValueSet(dim);
                            rowKey.setValue("RECORDKEY", (Object)UUID.randomUUID().toString());
                            IDataRow destDataRow = dataTable.appendRow(rowKey);
                            for (int j = 0; j < srcDataRow.getFieldSize(); ++j) {
                                IEntityDefine entityDefine;
                                Object value = srcDataRow.getValue(j);
                                FieldDefine fieldDefine = destDataRow.getFieldsInfo().getFieldDefine(j);
                                if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey()) && 0 != (entityDefine = entityMetaService.queryEntity(fieldDefine.getEntityKey())).getIsolation()) {
                                    String valueStr = value.toString();
                                    String entityKey = fieldDefine.getEntityKey();
                                    EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                                    entityQueryByKeyInfo.setEntityViewKey(entityKey);
                                    JtableContext context = new JtableContext();
                                    ReportFmlExecEnvironment reportFmlExecEnvironment = (ReportFmlExecEnvironment)executorContext.getEnv();
                                    String taskKey = reportFmlExecEnvironment.getTaskDefine().getKey();
                                    context.setTaskKey(taskKey);
                                    context.setFormSchemeKey(this.formSchemeKey);
                                    Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dim);
                                    context.setDimensionSet(dimensionSet);
                                    entityQueryByKeyInfo.setContext(context);
                                    entityQueryByKeyInfo.setEntityKey(valueStr);
                                    EntityByKeyReturnInfo returnInfo = entityService.queryEntityDataByKey(entityQueryByKeyInfo);
                                    EntityData entity = returnInfo.getEntity();
                                    value = entity.getId();
                                }
                                destDataRow.setValue(j, value);
                            }
                            if (inputOrderField == null) continue;
                            int floatorder = (i + 1) * 1000;
                            destDataRow.setValue(inputOrderField, (Object)floatorder);
                        }
                        dataTable.commitChanges(true);
                    }
                } else {
                    IDataTable dataTable = dataQuery.executeQuery(executorContext);
                    IDataRow destDataRow = null;
                    destDataRow = dataTable.getCount() == 0 ? dataTable.appendRow(dim) : dataTable.getItem(0);
                    ExtractDataRow srcDataRow = result.getRow(0);
                    for (i = 0; i < srcDataRow.getFieldSize(); ++i) {
                        destDataRow.setValue(i, srcDataRow.getValue(i));
                    }
                    dataTable.commitChanges(true);
                }
                this.logger.debug("EFDC\u53d6\u6570\uff1aEFDC\u7ed3\u679c\u96c6\u4fdd\u5b58\u6210\u529f");
            }
            catch (ExtractException e) {
                this.logger.error("EFDC\u53d6\u6570\uff1a\u6267\u884c\u51fa\u9519", (Throwable)((Object)e));
                throw e;
            }
            catch (Exception e) {
                this.logger.error("EFDC\u53d6\u6570\uff1a\u6267\u884c\u51fa\u9519", e);
                throw new ExtractException(e.getMessage(), e);
            }
        }
    }

    protected abstract IExtractResult doExtractRegion(ExecutorContext var1, ExtractDataRegion var2, DimensionValueSet var3, Map<String, Object> var4) throws ExtractException;

    protected abstract IRegionParser createRegionParser();

    protected abstract String getType();
}

