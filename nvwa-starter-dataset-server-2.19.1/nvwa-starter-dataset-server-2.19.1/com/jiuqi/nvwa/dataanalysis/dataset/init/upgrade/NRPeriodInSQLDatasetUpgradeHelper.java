/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.ddl.IDDLExecutor
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.sql.SQLModel
 *  com.jiuqi.bi.sql.DBException
 *  com.jiuqi.bi.sql.SQLPretreatment
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.env.GetEnv
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.sql.function.OrderBy
 *  com.jiuqi.bi.util.patterns.Grammar
 *  com.jiuqi.bi.util.patterns.ITranslator
 *  com.jiuqi.bi.util.patterns.Patterns
 *  com.jiuqi.nvwa.dataanalysis.dataset.dao.DatasetDAO
 *  com.jiuqi.nvwa.dataanalysis.dataset.dto.DatasetInfo
 *  com.jiuqi.nvwa.dataanalysis.dataset.dto.ParameterInfo
 *  com.jiuqi.nvwa.dataanalysis.dataset.service.DatasetService
 *  com.jiuqi.nvwa.dataanalysis.dataset.sql.ToNrPeriod
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNode
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nvwa.dataanalysis.dataset.init.upgrade;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.ddl.IDDLExecutor;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.sql.DBException;
import com.jiuqi.bi.sql.SQLPretreatment;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.env.GetEnv;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.sql.function.OrderBy;
import com.jiuqi.bi.util.patterns.Grammar;
import com.jiuqi.bi.util.patterns.ITranslator;
import com.jiuqi.bi.util.patterns.Patterns;
import com.jiuqi.nvwa.dataanalysis.dataset.dao.DatasetDAO;
import com.jiuqi.nvwa.dataanalysis.dataset.dto.DatasetInfo;
import com.jiuqi.nvwa.dataanalysis.dataset.dto.ParameterInfo;
import com.jiuqi.nvwa.dataanalysis.dataset.service.DatasetService;
import com.jiuqi.nvwa.dataanalysis.dataset.sql.ToNrPeriod;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNode;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class NRPeriodInSQLDatasetUpgradeHelper {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DatasetDAO datasetDAO;
    @Autowired
    private DatasetService datasetService;
    private static final String BACKUP_TABLE_NAME = "NVWA_DS_BASEINFO_BACKUP";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean execute(Connection conn) throws SQLException {
        Map<String, DSModel> dsMap;
        try {
            dsMap = this.findSQLDatasetContainNRPeriod();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
        if (dsMap.isEmpty()) {
            return true;
        }
        try {
            this.createBackupTable(conn);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new SQLException(e.getMessage(), e);
        }
        this.backupSQLData(conn, dsMap.keySet());
        ArrayList<SQLModel> adjusted = new ArrayList<SQLModel>();
        try {
            for (Map.Entry<String, DSModel> entry : dsMap.entrySet()) {
                String sql = this.adjustSQL(entry.getKey(), entry.getValue());
                SQLModel sqlModel = (SQLModel)entry.getValue();
                StringBuilder b = new StringBuilder();
                b.append("\u66f4\u65b0\u6570\u636e\u96c6\u3010").append(entry.getKey()).append("\u3011\r\n \u539f\u59cbSQL->").append(sqlModel.getSql()).append("\r\n \u66f4\u65b0\u540eSQL->");
                b.append(sql).append("\r\n");
                this.logger.info(b.toString());
                sqlModel.setSql(sql);
                adjusted.add(sqlModel);
            }
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
        try {
            for (SQLModel sqlModel : adjusted) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                sqlModel.saveExt((OutputStream)bos);
                byte[] extData = bos.toByteArray();
                this.updateSQLData(sqlModel._getGuid(), extData);
            }
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
        return true;
    }

    private void updateSQLData(String dsGuid, byte[] extData) throws SQLException {
        this.datasetDAO.updateDatasetExtraData(dsGuid, extData);
    }

    private void backupSQLData(Connection conn, Set<String> dsName) {
        String sql = "INSERT INTO NVWA_DS_BASEINFO_BACKUP(DS_GUID, DS_EXTRA) SELECT DS_GUID, DS_EXTRA FROM NVWA_DS_BASEINFO WHERE DS_NAME=?";
        for (String name : dsName) {
            this.jdbcTemplate.update(sql, new Object[]{name});
        }
    }

    private void createBackupTable(Connection conn) throws Exception {
        this.logger.info("\u521b\u5efa\u5907\u4efd\u8868\uff1aNVWA_DS_BASEINFO_BACKUP");
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        LogicTable table = db.createMetadata(conn).getTableByName(BACKUP_TABLE_NAME);
        if (table != null) {
            return;
        }
        CreateTableStatement statement = new CreateTableStatement(null, BACKUP_TABLE_NAME);
        statement.setJudgeExists(true);
        statement.setPkName("pk_ds_baseinfo_bp");
        statement.getPrimaryKeys().add("DS_GUID");
        LogicField guidField = new LogicField();
        guidField.setDataType(6);
        guidField.setFieldName("DS_GUID");
        guidField.setSize(50);
        LogicField extField = new LogicField();
        extField.setDataType(9);
        extField.setFieldName("DS_EXTRA");
        statement.getColumns().add(guidField);
        statement.getColumns().add(extField);
        ISQLInterpretor interpretor = db.createSQLInterpretor(conn);
        List sqls = interpretor.createTable(statement);
        IDDLExecutor ddlExecutor = db.createDDLExcecutor(conn);
        for (String sql : sqls) {
            this.logger.info(sql);
            ddlExecutor.execute(sql);
        }
    }

    private Map<String, DSModel> findSQLDatasetContainNRPeriod() throws Exception {
        String sql = "SELECT DS_NAME FROM NVWA_DS_BASEINFO WHERE DS_TYPE = 'com.jiuqi.bi.dataset.sql'";
        List sqlDsNames = this.jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString(1));
        HashMap<String, DSModel> map = new HashMap<String, DSModel>();
        block0: for (String dsName : sqlDsNames) {
            List paramInfos = this.datasetService.getParameterInfos(dsName);
            if (paramInfos.isEmpty()) continue;
            for (ParameterInfo pinfo : paramInfos) {
                if (!this.isNRPeriodParam(pinfo.getName())) continue;
                SQLModel model = new SQLModel();
                model.setName(dsName);
                for (ParameterInfo p : paramInfos) {
                    JSONObject j;
                    JSONObject datasourceJson;
                    ParameterModel pm = new ParameterModel();
                    pm.setName(p.getName());
                    String config = this.queryParamConfigData(p.getName());
                    if (config != null && (datasourceJson = (j = new JSONObject(config)).optJSONObject("datasource")) != null) {
                        int dataType = datasourceJson.optInt("dataType");
                        pm.setDatasource((AbstractParameterDataSourceModel)new NonDataSourceModel(dataType));
                    }
                    model.getParameterModels().add(pm);
                }
                DatasetInfo dsInfo = this.datasetService.getDatasetInfo(dsName);
                model._setGuid(dsInfo.getGuid());
                model.setTitle(dsInfo.getTitle());
                byte[] extData = this.datasetService.getDatasetExtraData(dsInfo.getGuid());
                if (extData != null) {
                    ByteArrayInputStream is = new ByteArrayInputStream(extData);
                    model.loadExt((InputStream)is);
                }
                map.put(dsName, (DSModel)model);
                continue block0;
            }
        }
        return map;
    }

    private String queryParamConfigData(String paramName) {
        String sql = "select p_data from NVWA_PARAMETER where p_name=?";
        List data = this.jdbcTemplate.query(sql, (RowMapper)new RowMapper<String>(){

            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, new Object[]{paramName});
        return data.isEmpty() ? null : (String)data.get(0);
    }

    private boolean isNRPeriodParam(String paramName) {
        String data = this.queryParamConfigData(paramName);
        if (data == null) {
            return false;
        }
        JSONObject j = new JSONObject(data);
        JSONObject datasourceJson = j.optJSONObject("datasource");
        if (datasourceJson != null) {
            String datasourceType = datasourceJson.optString("type", null);
            if (datasourceType == null) {
                datasourceType = datasourceJson.optString("datasourceType");
            }
            if (datasourceType != null && datasourceType.equals("com.jiuqi.publicparam.datasource.date")) {
                return true;
            }
        }
        return false;
    }

    private String adjustSQL(String dsName, DSModel model) throws Exception {
        SQLModel sqlModel = (SQLModel)model;
        String sql = sqlModel.getSql();
        FormulaParser parser = FormulaParser.getInstance();
        FunctionProvider funcProvider = new FunctionProvider();
        OrderBy orderByFunc = new OrderBy();
        funcProvider.add((IFunction)orderByFunc);
        funcProvider.add((IFunction)new GetEnv());
        funcProvider.add((IFunction)new ToNrPeriod());
        parser.registerFunctionProvider((IFunctionProvider)funcProvider);
        ParameterEnv paramEnv = new ParameterEnv(null, sqlModel.getParameterModels());
        parser.registerDynamicNodeProvider((IDynamicNodeProvider)new ParamNodeProvider((IParameterEnv)paramEnv));
        sql = this.replaceMacros(parser, sql);
        sql = this.replaceParams(sql, model);
        return sql;
    }

    private String replaceMacros(final FormulaParser parser, String sql) throws Exception {
        Patterns patterns = Patterns.getInstance();
        patterns.parse(sql, Grammar.SQL);
        return patterns.repace(new ITranslator(){

            public String[] translate(String pattern, int length) {
                return this.translate(pattern, length, null);
            }

            public String[] translate(String pattern, int length, String prevText) {
                String formula = pattern.startsWith("=") ? pattern.substring(1) : pattern;
                try {
                    IExpression expr = parser.parseEval(formula, null);
                    IASTNode root = expr.getChild(0);
                    root = NRPeriodInSQLDatasetUpgradeHelper.this.adjustASTNode(root);
                    formula = root.interpret(null, Language.FORMULA, null);
                }
                catch (Exception e) {
                    NRPeriodInSQLDatasetUpgradeHelper.this.logger.error(e.getMessage(), e);
                }
                return new String[]{"${" + formula + "}"};
            }
        });
    }

    private IASTNode adjustASTNode(IASTNode node) {
        FunctionNode fnode;
        ParamNode pn;
        if (node instanceof ParamNode && this.isNRPeriodParam((pn = (ParamNode)node).getParam().getName())) {
            return this.createNRPeriodFunctionNode(node);
        }
        if (node instanceof FunctionNode && (fnode = (FunctionNode)node).getDefine().getClass() == ToNrPeriod.class) {
            return node;
        }
        int size = node.childrenSize();
        for (int i = 0; i < size; ++i) {
            IASTNode child = node.getChild(i);
            IASTNode adjust = this.adjustASTNode(child);
            node.setChild(i, adjust);
        }
        return node;
    }

    private FunctionNode createNRPeriodFunctionNode(IASTNode nrPeriodParamNode) {
        ArrayList<IASTNode> params = new ArrayList<IASTNode>();
        params.add(nrPeriodParamNode);
        return new FunctionNode(null, (IFunction)new ToNrPeriod(), params);
    }

    private String replaceParams(String sql, final DSModel model) throws Exception {
        SQLPretreatment stat = new SQLPretreatment(sql){

            protected String repaceParameter(String paramName) throws DBException {
                ParameterModel pm;
                String str = paramName.toUpperCase();
                String pname = paramName;
                if (str.endsWith(".MIN") || str.endsWith(".MAX")) {
                    pname = paramName.substring(0, paramName.length() - 4);
                }
                if ((pm = model.findParamemterModel(pname)) != null && NRPeriodInSQLDatasetUpgradeHelper.this.isNRPeriodParam(pm.getName())) {
                    return "'${ToNrPeriod(" + paramName + ")}'";
                }
                return "?" + paramName;
            }
        };
        stat.execute();
        return stat.getSQL();
    }
}

