/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.dataentry.func;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;

public class GetSecretLevel
extends Function {
    public String name() {
        return "GetSecretLevel";
    }

    public String title() {
        return "\u83b7\u53d6\u5bc6\u7ea7\u7ea7\u522b";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        String secretDesc = null;
        if (iContext instanceof QueryContext) {
            QueryContext context = (QueryContext)iContext;
            try {
                ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)context.getExeContext().getEnv();
                FormSchemeDefine formScheme = env.getFormSchemeDefine();
                if (formScheme == null) {
                    throw new SyntaxException("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\uff01");
                }
                DataSchemeService dataSchemeService = (DataSchemeService)SpringBeanProvider.getBean(DataSchemeService.class);
                DataSchemeDTO dataScheme = dataSchemeService.getDataScheme(env.getDataScehmeKey());
                String tableName = String.format("%s%s", "NR_SECRETLEVEL_", dataScheme.getBizCode());
                DimensionValueSet currentKey = context.getCurrentMasterKey();
                ISecretLevelService secretLevelService = (ISecretLevelService)SpringBeanProvider.getBean(ISecretLevelService.class);
                DataModelService dataModelService = (DataModelService)SpringBeanProvider.getBean(DataModelService.class);
                INvwaDataAccessProvider nvwaDataAccessProvider = (INvwaDataAccessProvider)SpringBeanProvider.getBean(INvwaDataAccessProvider.class);
                DataEngineAdapter dataEngineAdapter = (DataEngineAdapter)SpringBeanProvider.getBean(DataEngineAdapter.class);
                DataAccessContext dataAccessContext = new DataAccessContext(dataModelService);
                NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
                TableModelDefine tableModel = dataModelService.getTableModelDefineByName(tableName);
                List allColumns = dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                ColumnModelDefine secretField = allColumns.stream().filter(e -> e.getCode().equals("SL_LEVEL")).findFirst().get();
                INvwaDataAccess readOnlyDataAccess = nvwaDataAccessProvider.createReadOnlyDataAccess(nvwaQueryModel);
                DimensionChanger dimensionChanger = dataEngineAdapter.getDimensionChanger(tableName);
                allColumns.forEach(column -> {
                    Object value;
                    String dimensionName;
                    if (column.getCode().equals(secretField.getCode())) {
                        nvwaQueryModel.getColumns().add(new NvwaQueryColumn(column));
                    }
                    if ((dimensionName = dimensionChanger.getDimensionName(column)) != null && (value = currentKey.getValue(dimensionName)) != null) {
                        nvwaQueryModel.getColumnFilters().put(column, value);
                    }
                });
                MemoryDataSet table = readOnlyDataAccess.executeQuery(dataAccessContext);
                for (DataRow dataRow : table) {
                    String secretLevel = dataRow.getString(0);
                    secretDesc = secretLevelService.getSecretLevelItem(secretLevel).getTitle();
                }
                if (secretDesc == null) {
                    secretDesc = ((SecretLevelItem)secretLevelService.getSecretLevelItems().get(0)).getTitle();
                }
            }
            catch (Exception e2) {
                throw new SyntaxException("\u5bc6\u7ea7\u7ea7\u522b\u83b7\u53d6\u5f02\u5e38", (Throwable)e2);
            }
        }
        return secretDesc;
    }
}

