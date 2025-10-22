/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.dataentry.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetEntityGroupCode
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -4410947143567681598L;
    private static final Logger logger = LoggerFactory.getLogger(GetEntityGroupCode.class);
    private static final String ENTITY_TABLE_CACHE_PREFIX = "GetEntityGroupCode";

    public GetEntityGroupCode() {
        this.parameters().add(new Parameter("dwdm", 6, "\u5355\u4f4d\u4ee3\u7801"));
        this.parameters().add(new Parameter("tableName", 6, "\u4e3b\u4f53\u8868\u540d"));
        this.parameters().add(new Parameter("fieldName", 6, "\u5b57\u6bb5\u540d"));
        this.parameters().add(new Parameter("fieldValue", 6, "\u5b57\u6bb5\u503c\uff0c\u591a\u4e2a\u503c\u7528\u5206\u53f7\u9694\u5f00"));
    }

    public String name() {
        return ENTITY_TABLE_CACHE_PREFIX;
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u5355\u4f4d\u53ca\u6240\u6709\u4e0a\u7ea7\u4e2d\u79bb\u81ea\u5df1\u6700\u8fd1\u7684\u7b26\u5408\u6761\u4ef6\u7684\u5355\u4f4d\u7684\u4ee3\u7801";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        IMonitor monitor = null;
        if (iContext instanceof QueryContext) {
            QueryContext context = (QueryContext)iContext;
            monitor = context.getMonitor();
            try {
                String[] fieldValueArr;
                String dwdm = (String)list.get(0).evaluate((IContext)context);
                String tableName = (String)list.get(1).evaluate((IContext)context);
                String fieldName = (String)list.get(2).evaluate((IContext)context);
                String fieldValue = (String)list.get(3).evaluate((IContext)context);
                this.buildParamDebugMsg(monitor, dwdm, tableName, fieldName, fieldValue);
                if (StringUtils.isNotEmpty((String)dwdm) && StringUtils.isNotEmpty((String)tableName) && StringUtils.isNotEmpty((String)fieldName) && StringUtils.isNotEmpty((String)fieldValue) && (fieldValueArr = fieldValue.split(";")).length > 0) {
                    IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanProvider.getBean(IEntityMetaService.class);
                    String entityId = entityMetaService.getEntityIdByCode(tableName);
                    IEntityModel entityModel = entityMetaService.getEntityModel(entityId);
                    String attributeCode = entityModel.getAttribute(fieldName).getCode();
                    IEntityTable resultSet = GetEntityGroupCode.cacheEntityTable(context, entityId);
                    IEntityRow entityRow = resultSet.findByCode(dwdm);
                    return this.getEntityGroupCodeFromParents(resultSet, entityRow, attributeCode, fieldValueArr, monitor);
                }
            }
            catch (Exception e) {
                throw new SyntaxException("\u5355\u4f4d\u4ee3\u7801\u83b7\u53d6\u5f02\u5e38", (Throwable)e);
            }
        }
        if (monitor != null && monitor.isDebug()) {
            monitor.message("\u7b26\u5408\u6761\u4ef6\u7684\u5355\u4f4d\u4ee3\u7801\uff1a\u7a7a", (Object)this);
        }
        return null;
    }

    private void buildParamDebugMsg(IMonitor monitor, String dwdm, String tableName, String fieldName, String fieldValue) {
        boolean debugMsg;
        boolean bl = debugMsg = monitor != null && monitor.isDebug();
        if (!debugMsg) {
            return;
        }
        monitor.message("\u5355\u4f4d\u4ee3\u7801\uff1a" + (StringUtils.isEmpty((String)dwdm) ? "\u7a7a" : dwdm), (Object)this);
        monitor.message("\u4e3b\u4f53\u8868\u540d\uff1a" + (StringUtils.isEmpty((String)tableName) ? "\u7a7a" : tableName), (Object)this);
        monitor.message("\u5b57\u6bb5\u540d\uff1a" + (StringUtils.isEmpty((String)fieldName) ? "\u7a7a" : fieldName), (Object)this);
        if (StringUtils.isEmpty((String)fieldValue)) {
            monitor.message("\u5b57\u6bb5\u503c\uff1a\u7a7a", (Object)this);
        } else {
            String[] fieldValueArr = fieldValue.split(";");
            if (fieldValueArr.length == 0) {
                monitor.message("\u5b57\u6bb5\u503c\uff1a\u7a7a", (Object)this);
            } else {
                StringBuilder msg = new StringBuilder("\u5b57\u6bb5\u503c\uff1a");
                for (int i = 0; i < fieldValueArr.length; ++i) {
                    if (i > 0) {
                        msg.append("\u6216\u8005");
                    }
                    msg.append(fieldValueArr[i]);
                }
                monitor.message(msg.toString(), (Object)this);
            }
        }
    }

    private static IEntityTable cacheEntityTable(QueryContext context, String entityId) throws Exception {
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)context.getExeContext().getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        String period = (String)context.getCurrentMasterKey().getValue("DATATIME");
        String entityTableCacheKey = GetEntityGroupCode.getEntityTableCacheKey(formSchemeKey, entityId, period);
        if (context.getCache().containsKey(entityTableCacheKey)) {
            return (IEntityTable)context.getCache().get(entityTableCacheKey);
        }
        IDataDefinitionRuntimeController runtimeController = env.getRuntimeController();
        IEntityViewRunTimeController entityViewRunTimeController = env.getEntityViewRunTimeController();
        IEntityDataService entityDataService = (IEntityDataService)SpringBeanProvider.getBean(IEntityDataService.class);
        EntityViewDefine entityViewDefine = entityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery query = entityDataService.newEntityQuery();
        query.setEntityView(entityViewDefine);
        Date date = null;
        try {
            date = context.getExeContext().getPeriodAdapter().getPeriodDateRegion(period)[1];
        }
        catch (Exception exception) {
            logger.debug("\u83b7\u53d6\u5f53\u524d\u7ef4\u5ea6\u4e3b\u4f53\u7248\u672c\u7ed3\u675f\u65f6\u95f4\u5f02\u5e38:{}", (Object)exception.getMessage(), (Object)exception);
        }
        query.setQueryVersionDate(date);
        ExecutorContext executorContext = new ExecutorContext(runtimeController);
        DataEntityFullService dataEntityFullService = (DataEntityFullService)SpringBeanProvider.getBean(DataEntityFullService.class);
        IEntityTable resultSet = dataEntityFullService.executeEntityFullBuild(query, executorContext, entityViewDefine, formSchemeKey).getEntityTable();
        context.getCache().put(entityTableCacheKey, resultSet);
        return resultSet;
    }

    private static String getEntityTableCacheKey(String formSchemeKey, String entityId, String period) {
        String suffix = formSchemeKey + ";" + entityId + ";" + period;
        return "GetEntityGroupCode:" + suffix;
    }

    private String getEntityGroupCodeFromParents(IEntityTable resultSet, IEntityRow entityRow, String attributeCode, String[] fieldValueArr, IMonitor monitor) {
        if (entityRow != null) {
            String currFieldValue = entityRow.getValue(attributeCode).getAsString();
            List<String> fieldValueList = Arrays.asList(fieldValueArr);
            if (fieldValueList.contains(currFieldValue)) {
                String code = entityRow.getCode();
                if (monitor != null && monitor.isDebug()) {
                    monitor.message("\u7b26\u5408\u6761\u4ef6\u7684\u672c\u7ea7\u4ee3\u7801\uff1a" + code, (Object)this);
                }
                return code;
            }
            String[] parentsEntityKeyDataPath = entityRow.getParentsEntityKeyDataPath();
            if (parentsEntityKeyDataPath.length > 0) {
                for (int i = parentsEntityKeyDataPath.length - 1; i >= 0; --i) {
                    String parFieldValue;
                    IEntityRow entityRowParent = resultSet.findByEntityKey(parentsEntityKeyDataPath[i]);
                    if (entityRowParent == null || !fieldValueList.contains(parFieldValue = entityRowParent.getValue(attributeCode).getAsString())) continue;
                    String code = entityRowParent.getCode();
                    if (monitor != null && monitor.isDebug()) {
                        monitor.message("\u7b26\u5408\u6761\u4ef6\u7684\u4e0a\u7ea7\u4ee3\u7801\uff1a" + code, (Object)this);
                    }
                    return code;
                }
            }
        }
        if (monitor != null && monitor.isDebug()) {
            monitor.message("\u7b26\u5408\u6761\u4ef6\u7684\u5355\u4f4d\u4ee3\u7801\uff1a\u7a7a", (Object)this);
        }
        return null;
    }

    public String[] aliases() {
        return new String[]{"GetGroupCodeGzw"};
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (!(context instanceof QueryContext)) {
            throw new IllegalArgumentException("\u4e0d\u652f\u6301\u7684context");
        }
        try {
            String dwdm = this.getParamValue(context, parameters.get(0));
            if (StringUtils.isEmpty((String)dwdm)) {
                throw new SyntaxException("\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
            }
            String tableName = this.getParamValue(context, parameters.get(1));
            if (StringUtils.isEmpty((String)tableName)) {
                throw new SyntaxException("\u4e3b\u4f53\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a");
            }
            String fieldName = this.getParamValue(context, parameters.get(2));
            if (StringUtils.isEmpty((String)fieldName)) {
                throw new SyntaxException("\u4e3b\u4f53\u5b57\u6bb5\u540d\u4e0d\u80fd\u4e3a\u7a7a");
            }
            IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanProvider.getBean(IEntityMetaService.class);
            IEntityDefine entityDefine = entityMetaService.queryEntityByCode(tableName);
            if (entityDefine == null) {
                throw new SyntaxException("\u4e3b\u4f53\u8868" + tableName + "\u4e0d\u5b58\u5728");
            }
            String entityId = entityDefine.getId();
            IEntityModel entityModel = entityMetaService.getEntityModel(entityId);
            if (entityModel == null) {
                throw new SyntaxException("\u4e3b\u4f53\u8868" + tableName + "\u7684\u53c2\u6570\u6a21\u578b\u4e0d\u5b58\u5728");
            }
            IEntityAttribute attribute = entityModel.getAttribute(fieldName);
            if (attribute == null) {
                throw new SyntaxException("\u4e3b\u4f53\u5c5e\u6027" + fieldName + "\u4e0d\u5b58\u5728");
            }
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return this.getResultType(context, parameters);
    }

    private String getParamValue(IContext context, IASTNode parameter) throws SyntaxException {
        String v = String.valueOf(parameter.evaluate(context));
        if (parameter instanceof VariableDataNode) {
            VariableDataNode varNode = (VariableDataNode)parameter;
            v = varNode.toString();
        } else if (parameter instanceof DynamicDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)parameter;
            v = dataNode.getQueryField().getFieldCode();
        } else if (parameter instanceof CellDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)parameter.getChild(0);
            v = dataNode.getQueryField().getFieldCode();
        }
        return v;
    }
}

