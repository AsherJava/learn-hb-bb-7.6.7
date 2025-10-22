/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.controller.EntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.dataentry.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.controller.EntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class SwitchEnumShow
extends Function
implements Serializable {
    private static final long serialVersionUID = -720482645976232101L;

    public SwitchEnumShow() {
        this.parameters().add(new Parameter("fieldCode", 6, "\u5173\u8054\u679a\u4e3e\u7684\u6307\u6807\u4ee3\u7801\uff08\u683c\u5f0f\uff1a\u201d\u5b58\u50a8\u8868\u540d\u79f0[\u6307\u6807\u6807\u8bc6]\u201d\uff09\u6216\u201c\u7ec4\u7ec7\u7ed3\u6784\u6807\u8bc6[\u5b57\u6bb5\u5c5e\u6027\u6807\u8bc6]\u201d"));
        this.parameters().add(new Parameter("showPattern", 6, "\u8f6c\u6362\u540e\u663e\u793a\u683c\u5f0f\uff0c\u652f\u6301code\u548ctitle\u7528\u201c\uff1a\u201d\u3001\u201c:\u201d\u3001\u201c|\u201d\u3001\u201c\uff5c\u201d\u3001\u201c/\u201d\u5176\u4e2d\u4e4b\u4e00\u5206\u9694\uff0c\u5982\u201ccode:title\u201d\uff08\u9ed8\u8ba4\u662ftitle\uff09"));
        this.parameters().add(new Parameter("splitChar", 6, "\u8f6c\u6362\u540e\u663e\u793a\u591a\u4e2a\u679a\u4e3e\u4e4b\u95f4\u7684\u5206\u9694\u7b26\u53f7\uff08\u9ed8\u8ba4\u662f\u201c\uff1b\u201d\uff09"));
    }

    public String name() {
        return "SwitchEnumShow";
    }

    public String title() {
        return "\u8f6c\u6362\u679a\u4e3e\u663e\u793a\u5185\u5bb9\uff08\u652f\u6301\u591a\u9009\uff09";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        if (iContext instanceof QueryContext) {
            String[] filedInfo;
            QueryContext context = (QueryContext)iContext;
            String fieldCode = (String)list.get(0).evaluate((IContext)context);
            String showPattern = (String)list.get(1).evaluate((IContext)context);
            String splitChar = (String)list.get(2).evaluate((IContext)context);
            if (StringUtils.isNotEmpty((String)fieldCode) && (filedInfo = fieldCode.split("[\\[\\]]")).length == 2) {
                String var1 = filedInfo[0];
                String var2 = filedInfo[1];
                if (StringUtils.isEmpty((String)splitChar)) {
                    splitChar = "\uff1b";
                }
                String[] colNames = new String[1];
                String colSplitChar = "";
                if (StringUtils.isEmpty((String)showPattern)) {
                    colNames[0] = "title";
                } else {
                    if (showPattern.contains(":")) {
                        colSplitChar = ":";
                    } else if (showPattern.contains("\uff1a")) {
                        colSplitChar = "\uff1a";
                    } else if (showPattern.contains("/")) {
                        colSplitChar = "/";
                    } else if (showPattern.contains("|")) {
                        colSplitChar = "\\|";
                    } else if (showPattern.contains("\uff5c")) {
                        colSplitChar = "\uff5c";
                    }
                    if (StringUtils.isEmpty((String)colSplitChar)) {
                        colNames[0] = showPattern;
                    } else {
                        colNames = showPattern.split(colSplitChar);
                    }
                    if ("\\|".equals(colSplitChar)) {
                        colSplitChar = "|";
                    }
                }
                try {
                    IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
                    IExpressionEvaluator evaluator = dataAccessProvider.newExpressionEvaluator();
                    AbstractData abstractData = evaluator.eval(fieldCode, context.getExeContext(), context.getCurrentMasterKey());
                    if (!AbstractData.isNull((AbstractData)abstractData)) {
                        String asString = abstractData.getAsString();
                        String[] enumCode = asString.split(";");
                        String refDataEntityKey = SwitchEnumShow.getDFRefDataEntityKey(var1, var2);
                        if (StringUtils.isEmpty((String)refDataEntityKey)) {
                            refDataEntityKey = SwitchEnumShow.getOrgRefDataEntityKey(var1, var2);
                        }
                        if (StringUtils.isNotEmpty((String)refDataEntityKey)) {
                            return SwitchEnumShow.switchShow(context, splitChar, colSplitChar, colNames, enumCode, refDataEntityKey);
                        }
                    }
                }
                catch (Exception e) {
                    throw new SyntaxException("\u679a\u4e3e\u6570\u636e\u83b7\u53d6\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
                }
            }
        }
        return null;
    }

    private static String getDFRefDataEntityKey(String var1, String var2) {
        String dataTableKey;
        DataField dataField;
        String refDataEntityKey = null;
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanProvider.getBean(IRuntimeDataSchemeService.class);
        List deployInfoByTableName = dataSchemeService.getDeployInfoByTableName(var1);
        if (deployInfoByTableName != null && !deployInfoByTableName.isEmpty() && (dataField = dataSchemeService.getDataFieldByTableKeyAndCode(dataTableKey = ((DataFieldDeployInfo)deployInfoByTableName.get(0)).getDataTableKey(), var2)) != null) {
            refDataEntityKey = dataField.getRefDataEntityKey();
        }
        return refDataEntityKey;
    }

    private static String getOrgRefDataEntityKey(String var1, String var2) {
        String entityId;
        String refDataEntityKey = null;
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanProvider.getBean(IEntityMetaService.class);
        List entityRefer = entityMetaService.getEntityRefer(entityId = entityMetaService.getEntityIdByCode(var1));
        if (!CollectionUtils.isEmpty(entityRefer)) {
            for (IEntityRefer refer : entityRefer) {
                if (!var2.equals(refer.getOwnField())) continue;
                refDataEntityKey = refer.getReferEntityId();
                break;
            }
        }
        return refDataEntityKey;
    }

    private static String switchShow(QueryContext context, String splitChar, String colSplitChar, String[] colNames, String[] enumCodes, String refDataEntityKey) throws Exception {
        IEntityViewRunTimeController entityController = (IEntityViewRunTimeController)SpringBeanProvider.getBean(EntityViewRunTimeController.class);
        EntityViewDefine entityViewDefine = entityController.buildEntityView(refDataEntityKey);
        IEntityDataService entityDataService = (IEntityDataService)SpringBeanProvider.getBean(IEntityDataService.class);
        IEntityQuery query = entityDataService.newEntityQuery();
        query.setEntityView(entityViewDefine);
        String period = (String)context.getCurrentMasterKey().getValue("DATATIME");
        Date date = context.getExeContext().getPeriodAdapter().getPeriodDateRegion(period)[1];
        query.setQueryVersionDate(date);
        ExecutorContext executorContext = new ExecutorContext(context.getQueryParam().getRuntimeController());
        IEntityTable resultSet = query.executeReader((IContext)executorContext);
        StringBuilder result = new StringBuilder();
        for (String code : enumCodes) {
            for (String colName : colNames) {
                IEntityRow row = resultSet.findByCode(code);
                if (row == null) continue;
                if ("title".equalsIgnoreCase(colName)) {
                    result.append(row.getTitle());
                } else if ("code".equalsIgnoreCase(colName)) {
                    result.append(row.getCode());
                }
                result.append(colSplitChar);
            }
            result.setLength(result.length() - colSplitChar.length());
            result.append(splitChar);
        }
        result.setLength(result.length() - splitChar.length());
        return result.toString();
    }
}

