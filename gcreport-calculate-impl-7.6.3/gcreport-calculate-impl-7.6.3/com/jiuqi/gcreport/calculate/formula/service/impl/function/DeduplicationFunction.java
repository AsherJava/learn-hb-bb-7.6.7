/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.DataTableImpl
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.setting.FieldsInfoImpl
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeduplicationFunction
extends AdvanceFunction
implements INrFunction {
    @Autowired
    private transient IRunTimeViewController iRunTimeViewController;
    private static final Logger logger = LoggerFactory.getLogger(DeduplicationFunction.class);
    private static final long serialVersionUID = 1L;
    private static final String FUNCTION_NAME = "Deduplication";

    DeduplicationFunction() {
        this.parameters().add(new Parameter("duplicateKey", 6, "\u5224\u65ad\u91cd\u590d\u7684\u4e3b\u952e"));
        this.parameters().add(new Parameter("retentionCondition", 6, "\u4fdd\u7559\u6761\u4ef6"));
        this.parameters().add(new Parameter("duplicateConditon", 6, "\u8fc7\u6ee4\u6761\u4ef6"));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u6d6e\u52a8\u884c\u6c47\u603b\u53bb\u91cd";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    @Transactional
    public Object evalute(IContext context, List<IASTNode> parameters) {
        try {
            DimensionValueSet currentMasterKey = ((QueryContext)context).getCurrentMasterKey();
            ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)((QueryContext)context).getExeContext().getEnv();
            String formKey = ((QueryContext)context).getDefaultGroupName();
            FormDefine formDefine = this.iRunTimeViewController.queryFormByCodeInScheme(env.getFormSchemeDefine().getKey(), formKey);
            String filterConditon = (String)parameters.get(2).evaluate(context);
            DataRegionDefine dataRegionDefine = this.getCurrDataRegionDefine(formDefine.getKey());
            DataTableImpl iDataTable = (DataTableImpl)this.queryFloatLineData(currentMasterKey, env, dataRegionDefine.getKey(), filterConditon);
            FieldsInfoImpl fieldsInfo = (FieldsInfoImpl)iDataTable.getFieldsInfo();
            HashMap fieldsMap = fieldsInfo.getFieldsMap();
            List allDataRows = iDataTable.getAllDataRows();
            String groupZbCode = DeduplicationFunction.getZbCode((String)parameters.get(0).evaluate(context));
            Map<Object, List<DataRowImpl>> floatLineListOfGroup = allDataRows.stream().collect(Collectors.groupingBy(item -> item.getAsString((FieldDefine)fieldsMap.get(groupZbCode))));
            String sortZbCode = DeduplicationFunction.getZbCode((String)parameters.get(1).evaluate(context));
            ArrayList needDelRowKeysList = new ArrayList();
            for (Map.Entry<Object, List<DataRowImpl>> entry : floatLineListOfGroup.entrySet()) {
                List<DataRowImpl> dataRowList = entry.getValue();
                if (dataRowList.size() <= 1) continue;
                dataRowList.sort(Comparator.comparing(item -> item.getValue((FieldDefine)fieldsMap.get(sortZbCode))));
                dataRowList.remove(dataRowList.size() - 1);
                needDelRowKeysList.addAll(dataRowList.stream().map(item -> item.getRowKeys()).collect(Collectors.toList()));
            }
            for (DimensionValueSet rowkeys : needDelRowKeysList) {
                iDataTable.deleteRow(rowkeys);
            }
            iDataTable.commitChanges(true);
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u6c47\u603b\u53bb\u91cd\u516c\u5f0f\u65f6\u51fa\u73b0\u9519\u8bef\uff1a" + e.getMessage(), e);
        }
        return null;
    }

    private static String getZbCode(String zbCode) {
        int index = zbCode.indexOf("[");
        if (index == 0) {
            zbCode = zbCode.substring(1, zbCode.length() - 1);
        } else if (index > 0) {
            zbCode = zbCode.substring(index + 1, zbCode.length() - 1);
        }
        return zbCode;
    }

    private DataRegionDefine getCurrDataRegionDefine(String formDefinKey) {
        List buyerAllRegionsInForms = this.iRunTimeViewController.getAllRegionsInForm(formDefinKey);
        return buyerAllRegionsInForms.stream().filter(dataRegionDefine -> dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE).findFirst().orElse(null);
    }

    private IDataTable queryFloatLineData(DimensionValueSet dimensionValueSet, ReportFmlExecEnvironment env, String regionKey, String filterConditon) {
        IDataDefinitionRuntimeController iDataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext context = new ExecutorContext(iDataDefinitionRuntimeController);
        context.setUseDnaSql(false);
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        FormSchemeDefine formSchemeDefine = env.getFormSchemeDefine();
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(formSchemeDefine.getKey());
        queryEnvironment.setRegionKey(regionKey);
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setRowFilter(filterConditon);
        dataQuery.setMasterKeys(dimensionValueSet);
        try {
            List dataLinkDefines = this.iRunTimeViewController.getAllLinksInRegion(regionKey);
            dataLinkDefines.forEach(dataLinkDefine -> {
                try {
                    FieldDefine fieldDefine = iDataDefinitionRuntimeController.queryFieldDefine(dataLinkDefine.getLinkExpression());
                    dataQuery.addColumn(fieldDefine);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
            IDataTable dataTable = dataQuery.executeQuery(context);
            return dataTable;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u6d6e\u52a8\u884c\u6570\u636e\u5f02\u5e38\u3002", (Throwable)e);
        }
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6d6e\u52a8\u884c\u6c47\u603b\u53bb\u91cd").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6d6e\u52a8\u884c\u6c47\u603b\u53ef\u5b9e\u73b0\u6309\u4e3b\u952e\u53bb\u91cd\u6c47\u603b\uff1a\u5c06\u7968\u636e\u53f7\u76f8\u540c\u4e14\u7968\u636e\u72b6\u6001\u672a\u7ec8\u6b62\u786e\u8ba4\u7684\u624d\u53bb\u91cd\uff0c\u5e76\u4e14\u4fdd\u7559\u6536\u7968\u65e5\u671f\u6700\u65b0\u7684\u4e00\u6761").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("Deduplication(\"\u5355\u636e\u53f7\",\"\u6536\u7968\u65e5\u671f\", \"\u7968\u636e\u72b6\u6001=null\")").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

