/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.autotask.AutoTask
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bill.autoask;

import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class WriteBackBillDataAutoTask
implements AutoTask {
    private static final Logger logger = LoggerFactory.getLogger(WriteBackBillDataAutoTask.class);
    private static final String L_RESULT = "result";
    private static final String KEY_BIZ_CODE = "bizCode";
    private static final String KEY_TABLE_NAME = "tableName";
    private static final String WRITE_BACK_BILL_DATA_AUTO_TASK = "WriteBackBillDataAutoTask";
    private static final String WRITE_BACK_BILL_DATA_AUTO_TASK_TITLE = "\u81ea\u52a8\u56de\u5199\u5355\u636e\u6570\u636e\u81ea\u52a8\u4efb\u52a1";
    private static final String BILL = "bill";
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private CommonDao commonDao;

    public String getName() {
        return WRITE_BACK_BILL_DATA_AUTO_TASK;
    }

    public String getTitle() {
        return WRITE_BACK_BILL_DATA_AUTO_TASK_TITLE;
    }

    public String getAutoTaskModule() {
        return BILL;
    }

    public Boolean canRetract() {
        return false;
    }

    public R execute(Object params) {
        try {
            logger.info("\u89e6\u53d1\u81ea\u52a8\u56de\u5199\u4efb\u52a1");
            TenantDO tenantDO = null;
            if (params instanceof TenantDO) {
                tenantDO = (TenantDO)params;
            }
            if (ObjectUtils.isEmpty(tenantDO)) {
                return R.error((String)"\u5355\u636e\u56de\u5199\u81ea\u52a8\u4efb\u52a1\u53c2\u6570\u4e3a\u7a7a");
            }
            Map<String, Object> resultMap = this.getMap(tenantDO.getExtInfo(L_RESULT));
            String bizCode = (String)tenantDO.getExtInfo(KEY_BIZ_CODE);
            String tableName = (String)tenantDO.getExtInfo(KEY_TABLE_NAME);
            if (ObjectUtils.isEmpty(tableName) || ObjectUtils.isEmpty(resultMap)) {
                return R.error((String)"\u53c2\u6570\u4e2d\u8868\u4fe1\u606f\u7f3a\u5931\u6216\u53c2\u6570\u4e2d\u9700\u8981\u56de\u5199\u7684\u5b57\u6bb5\u7f3a\u5931");
            }
            DataModelDO dataModel = WriteBackBillDataAutoTask.getDataModel(tableName, this.dataModelClient);
            List columns = dataModel.getColumns();
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            block2: for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                String key = entry.getKey();
                Object object = entry.getValue();
                if (object == null) continue;
                for (DataModelColumn dataModelColumn : columns) {
                    Integer columnLength;
                    Integer[] lengths;
                    String columnName = dataModelColumn.getColumnName();
                    DataModelType.ColumnType columnType = dataModelColumn.getColumnType();
                    if (!key.equals(columnName)) continue;
                    if (DataModelType.ColumnType.TIMESTAMP == columnType && object instanceof String) {
                        entry.setValue(dateTimeFormat.parse(object.toString()));
                        continue block2;
                    }
                    if (DataModelType.ColumnType.TIMESTAMP == columnType && object instanceof Long) {
                        entry.setValue(new Date((Long)object));
                        continue block2;
                    }
                    if (DataModelType.ColumnType.DATE == columnType && object instanceof String) {
                        entry.setValue(dateFormat.parse(object.toString()));
                        continue block2;
                    }
                    if (DataModelType.ColumnType.INTEGER == columnType && object instanceof String) {
                        entry.setValue(Integer.parseInt(object.toString()));
                        continue block2;
                    }
                    if (DataModelType.ColumnType.NUMERIC == columnType && object instanceof String) {
                        entry.setValue(new BigDecimal(object.toString()));
                        continue block2;
                    }
                    if (DataModelType.ColumnType.NVARCHAR != columnType || !(object instanceof String) || (lengths = dataModelColumn.getLengths()).length <= 0 || (columnLength = lengths[0]) >= object.toString().length()) continue block2;
                    entry.setValue(object.toString().substring(0, columnLength));
                    continue block2;
                }
            }
            SQL sql = new SQL();
            sql.UPDATE(tableName);
            HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put(KEY_TABLE_NAME, tableName);
            paramMap.put("BILLCODE", bizCode);
            for (Map.Entry entry : resultMap.entrySet()) {
                String columnName = (String)entry.getKey();
                sql.SET(columnName + " =#{param." + columnName + "}");
                paramMap.put(columnName, (String)entry.getValue());
            }
            sql.WHERE(" BILLCODE =#{param.BILLCODE}");
            SqlDTO sqlDTO = new SqlDTO(ShiroUtil.getTenantName(), sql.toString());
            sqlDTO.setParam(paramMap);
            this.commonDao.executeBySql(sqlDTO);
            return R.ok();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public R retrack(Object params) {
        return R.ok();
    }

    private Map<String, Object> getMap(Object object) {
        if (object instanceof Map) {
            return (Map)object;
        }
        return Collections.emptyMap();
    }

    public static DataModelDO getDataModel(String tableName, DataModelClient dataModelClient) {
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setBiztype(DataModelType.BizType.BILL);
        dataModelDTO.setName(tableName);
        return dataModelClient.get(dataModelDTO);
    }
}

