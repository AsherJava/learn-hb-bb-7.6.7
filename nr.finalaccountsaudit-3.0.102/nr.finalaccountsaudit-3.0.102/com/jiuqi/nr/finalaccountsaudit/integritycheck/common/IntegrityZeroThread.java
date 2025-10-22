/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.common;

import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.EmptyZeroCheckHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

public class IntegrityZeroThread
implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityZeroThread.class);
    private final int x;
    private final JdbcTemplate jdbcTpl;
    private final IRuntimeDataSchemeService runtimeDataSchemeService;
    private final EmptyZeroCheckHelper emptyZeroCheckHelper;
    private final String taskKey;
    private final String formKey;
    private final String period;
    private final String tempTableName;
    private final String masterDimName;
    private final HashMap<String, HashMap<String, List<DataField>>> dicTablesByForm;
    private final Map<String, Map<String, String>> formUnits;

    public IntegrityZeroThread(int x, JdbcTemplate jdbcTpl, IRuntimeDataSchemeService runtimeDataSchemeService, EmptyZeroCheckHelper emptyZeroCheckHelper, String taskKey, String formKey, String period, String tempTableName, String masterDimName, HashMap<String, HashMap<String, List<DataField>>> dicTablesByForm, Map<String, Map<String, String>> formUnits) {
        this.x = x;
        this.jdbcTpl = new JdbcTemplate(jdbcTpl.getDataSource());
        this.emptyZeroCheckHelper = emptyZeroCheckHelper;
        this.taskKey = taskKey;
        this.formKey = formKey;
        this.period = period;
        this.tempTableName = tempTableName;
        this.masterDimName = masterDimName;
        this.dicTablesByForm = dicTablesByForm;
        this.formUnits = formUnits;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    @Override
    public void run() {
        try {
            HashMap<String, String> dicEntityState = new HashMap<String, String>();
            HashMap<String, List<DataField>> dicTablesInForm = this.dicTablesByForm.get(this.formKey);
            ArrayList<DataField> groupFdList = new ArrayList<DataField>();
            for (String tableKey : dicTablesInForm.keySet()) {
                List<DataField> fdList = dicTablesInForm.get(tableKey);
                if (groupFdList.size() + fdList.size() > 500) {
                    groupFdList.addAll(fdList);
                    this.getEntityState(dicEntityState, this.period, this.tempTableName, groupFdList, this.masterDimName);
                    groupFdList.clear();
                    continue;
                }
                groupFdList.addAll(fdList);
            }
            this.getEntityState(dicEntityState, this.period, this.tempTableName, groupFdList, this.masterDimName);
            if (this.formUnits != null && !this.formUnits.containsKey(this.formKey)) {
                HashMap<String, String> entityIsEmptyZero = new HashMap<String, String>();
                for (String key : dicEntityState.keySet()) {
                    String state = (String)dicEntityState.get(key);
                    if (state.isEmpty()) continue;
                    entityIsEmptyZero.put(key, state);
                }
                this.formUnits.put(this.formKey, entityIsEmptyZero);
            }
            if (!this.formUnits.containsKey(this.formKey)) {
                this.formUnits.put(this.formKey, new HashMap());
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public void getEntityState(final Map<String, String> dicEntityState, String period, String tempTableName, List<DataField> groupFdList, String masterDimName) throws Exception {
        try {
            this.validatePeriod(period);
        }
        catch (Exception e) {
            logger.error("\u65e0\u6548\u7684period\u53c2\u6570\uff1a{}", (Object)period);
            throw e;
        }
        StringBuffer sqlCheckFormAllofselect = new StringBuffer();
        StringBuffer sqlCheckFormAlloffrom = new StringBuffer();
        sqlCheckFormAlloffrom.append(" from ").append(tempTableName);
        sqlCheckFormAllofselect.append(" select ").append(tempTableName).append(".code  as FMID ");
        HashMap<String, List<DataField>> tableFields = new HashMap<String, List<DataField>>();
        for (DataField fieldDefine : groupFdList) {
            List<DataField> tableField;
            DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()}).get(0);
            String tableName = deployInfo.getTableName();
            if (!tableFields.containsKey(tableName)) {
                tableField = new ArrayList();
                tableFields.put(tableName, tableField);
            } else {
                tableField = (List)tableFields.get(tableName);
            }
            tableField.add(fieldDefine);
        }
        if (!tableFields.isEmpty()) {
            String fc = "FLAG";
            String querySql = this.emptyZeroCheckHelper.getEmptyZeroCheckSql(this.taskKey, period, tempTableName, tableFields, masterDimName);
            sqlCheckFormAllofselect.append(String.format(", temp_%s.flag as %s", fc, fc));
            sqlCheckFormAlloffrom.append(String.format(" left join (%s)  temp_%s on  temp_%s.FMID = %s.code ", querySql, fc, fc, tempTableName));
            sqlCheckFormAllofselect.append(sqlCheckFormAlloffrom);
            this.jdbcTpl.query(sqlCheckFormAllofselect.toString(), new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    String entityKey = rs.getString("FMID");
                    String flagStr = rs.getString("FLAG") == null ? "2" : rs.getString("FLAG");
                    int flag = Convert.toInt((String)flagStr);
                    if (!dicEntityState.containsKey(entityKey)) {
                        dicEntityState.put(entityKey, "\u7a7a\u8868");
                    }
                    switch (flag) {
                        case 0: {
                            if (dicEntityState.get(entityKey) == "") break;
                            dicEntityState.replace(entityKey, "\u96f6\u8868");
                            break;
                        }
                        case 1: {
                            dicEntityState.replace(entityKey, "");
                        }
                    }
                }
            });
        }
    }

    public void validatePeriod(String period) throws IllegalArgumentException {
        if (period == null || period.isEmpty()) {
            throw new IllegalArgumentException("period\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String pattern = "^[A-Za-z0-9]{9}$";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(period);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("period\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u5e94\u4e3a9\u4f4d\u6570\u5b57\u548c\u5b57\u6bcd\u7ec4\u5408");
        }
    }
}

