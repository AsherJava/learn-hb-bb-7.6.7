/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.nr.data.engine.summary.parse;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseDefineFactory;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import com.jiuqi.nr.data.engine.summary.parse.SumTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

public class SumContext
implements IContext {
    private String mainTable;
    private final Map<String, SumTable> tables = new HashMap<String, SumTable>();
    private ISumBaseDefineFactory baseDefineFactory;
    private ExecutorContext executorContext;
    private final List<String> entityTableNames = new ArrayList<String>();
    private Object period;
    private Object conditionFieldValue;
    private Logger logger;
    private Map<String, Object> cache = new HashMap<String, Object>();
    private Connection connection;

    public Map<String, SumTable> getTables() {
        return this.tables;
    }

    public String getMainTable() {
        return this.mainTable;
    }

    public void setMainTable(String mainTable) {
        this.mainTable = mainTable;
    }

    public void addNode(SumNode node, String tableName) {
        SumTable table = this.tables.get(tableName);
        if (table == null) {
            table = new SumTable(tableName);
            String order = OrderGenerator.newOrder();
            table.setTableOrder(order);
            this.tables.put(tableName, table);
        }
        node.setTable(table);
        table.getNodes().add(node);
    }

    public ISumBaseDefineFactory getBaseDefineFactory() {
        return this.baseDefineFactory;
    }

    public void setBaseDefineFactory(ISumBaseDefineFactory baseDefineFactory) {
        this.baseDefineFactory = baseDefineFactory;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public List<String> getEntityTableNames() {
        return this.entityTableNames;
    }

    public Object getConditionFieldValue() {
        return this.conditionFieldValue;
    }

    public void setConditionFieldValue(Object conditionFieldValue) {
        this.conditionFieldValue = conditionFieldValue;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Map<String, Object> getCache() {
        return this.cache;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private DataFieldDeployInfo getDeployInfo(String dataFieldKey) {
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        List deployInfoByDataFieldKeys = dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataFieldKey});
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        return deployInfo;
    }
}

