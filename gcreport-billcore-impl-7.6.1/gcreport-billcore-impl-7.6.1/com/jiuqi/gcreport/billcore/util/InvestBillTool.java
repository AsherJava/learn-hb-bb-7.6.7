/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.util.BaseDataUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserManagerService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.va.billcode.common.BillCodeUtils
 *  com.jiuqi.va.billcode.service.IBillCodeFlowService
 *  com.jiuqi.va.billcode.service.IBillCodeRuleService
 *  com.jiuqi.va.billcode.service.impl.BillCodeFlowService
 *  com.jiuqi.va.domain.billcode.BillCodeDTO
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.feign.client.DataModelMaintainClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 */
package com.jiuqi.gcreport.billcore.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.common.GcBillConsts;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.util.BaseDataUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserManagerService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.va.billcode.common.BillCodeUtils;
import com.jiuqi.va.billcode.service.IBillCodeFlowService;
import com.jiuqi.va.billcode.service.IBillCodeRuleService;
import com.jiuqi.va.billcode.service.impl.BillCodeFlowService;
import com.jiuqi.va.domain.billcode.BillCodeDTO;
import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.feign.client.DataModelMaintainClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvestBillTool {
    private static final Logger logger = LoggerFactory.getLogger(InvestBillTool.class);

    public static List<DefaultTableEntity> listByUnitCode(String tableName, Collection<String> unitCodes) {
        return InvestBillTool.listByUnitCode(tableName, unitCodes, null);
    }

    public static List<DefaultTableEntity> listByUnitCode(String tableName, Collection<String> unitCodes, Integer year) {
        String sql = "select %1$s  from " + tableName + " t  where %2$s";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"t");
        String whereSql = SqlUtils.getConditionOfIdsUseOr(unitCodes, (String)"UNITCODE");
        if (null != year) {
            whereSql = whereSql + " and acctYear=" + year;
        }
        String formatSQL = String.format(sql, columns, whereSql);
        return InvestBillTool.queryBySql(formatSQL, new Object[0]);
    }

    public static List<DefaultTableEntity> listItemByMasterId(Collection<String> masteIds, String tableName) {
        String sql = "select %1$s  from " + tableName + " t  where %2$s";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"t");
        String whereSql = SqlUtils.getConditionOfIdsUseOr(masteIds, (String)"masterId");
        String formatSQL = String.format(sql, columns, whereSql);
        return InvestBillTool.queryBySql(formatSQL, new Object[0]);
    }

    public static DefaultTableEntity getEntityById(String id, String tableName) {
        String sql = "select %1$s  from " + tableName + " t  where id = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"t");
        String formatSQL = String.format(sql, columns);
        List<DefaultTableEntity> result = InvestBillTool.queryBySql(formatSQL, id);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    public static DefaultTableEntity getEntityByBillCode(String billCode, String tableName) {
        String sql = "select %1$s  from " + tableName + " t  where billcode = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"t");
        String formatSQL = String.format(sql, columns);
        List<DefaultTableEntity> result = InvestBillTool.queryBySql(formatSQL, billCode);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    public static int deleteItemByUnitCode(String masterTableName, String itemTableName, Collection<String> unitCodes, Integer year) {
        String sql = "delete from " + itemTableName + "   where masterId in (select id from " + masterTableName + " where %1$s )";
        String whereSql = SqlUtils.getConditionOfIdsUseOr(unitCodes, (String)"UNITCODE");
        if (null != year) {
            whereSql = whereSql + " and acctYear=" + year;
        }
        String formatSQL = String.format(sql, whereSql);
        return EntNativeSqlDefaultDao.getInstance().execute(formatSQL);
    }

    public static int deleteMasterByUnitCode(String masterTableName, Collection<String> unitCodes, Integer year) {
        String sql = "delete from " + masterTableName + " where %1$s ";
        String whereSql = SqlUtils.getConditionOfIdsUseOr(unitCodes, (String)"UNITCODE");
        if (null != year) {
            whereSql = whereSql + " and acctYear=" + year;
        }
        String formatSQL = String.format(sql, whereSql);
        System.out.println(formatSQL);
        return EntNativeSqlDefaultDao.getInstance().execute(formatSQL);
    }

    public static List<DefaultTableEntity> queryBySql(String sql, Object ... params) {
        ArrayList<DefaultTableEntity> result = new ArrayList<DefaultTableEntity>();
        List fields = EntNativeSqlDefaultDao.getInstance().selectMap(sql, params);
        for (Map field : fields) {
            DefaultTableEntity entity = new DefaultTableEntity();
            entity.setId(String.valueOf(field.get("ID")));
            entity.resetFields(field);
            result.add(entity);
        }
        return result;
    }

    public static List<DefaultTableEntity> queryBySql(String sql, List<Object> param) {
        ArrayList<DefaultTableEntity> result = new ArrayList<DefaultTableEntity>();
        List fields = EntNativeSqlDefaultDao.getInstance().selectMap(sql, param);
        for (Map field : fields) {
            DefaultTableEntity entity = new DefaultTableEntity();
            entity.setId(String.valueOf(field.get("ID")));
            entity.resetFields(field);
            result.add(entity);
        }
        return result;
    }

    public static List<Map<String, Object>> listByWhere(String[] columnNamesInDB, Object[] values, String tableName) {
        StringBuffer conditionSql = new StringBuffer(128);
        for (int i = 0; i < values.length; ++i) {
            conditionSql.append(SqlUtils.getConditionOfObject((Object)values[i], (String)(" i." + columnNamesInDB[i]))).append(" and");
        }
        String whereSql = "";
        if (conditionSql.length() > 0) {
            conditionSql.setLength(conditionSql.length() - 4);
            whereSql = " where " + conditionSql;
        }
        String sql = "select %s from " + tableName + "  i  %s \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"i"), whereSql);
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    public static List<Map<String, Object>> getBillItemByBillCode(String billCode, String tableName) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"i") + " from " + tableName + "  i\n  where i.billCode=?\n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{billCode});
    }

    public static List<Map<String, Object>> getBillItemByMasterId(String masteId, String tableName) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"i") + " from " + tableName + "  i\n  where i.masterId=?\n";
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[]{masteId});
    }

    public static List<Map<String, Object>> listBillsByIds(List<String> ids, String tableName) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"i.ID");
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"i") + " from " + tableName + " i where " + inSql;
        return EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
    }

    public static Date getDateValue(DefaultTableEntity item, String field) {
        Object value = item.getFieldValue(field);
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date)value;
        }
        return null;
    }

    public static String getStringValue(DefaultTableEntity item, String field) {
        Object value = item.getFieldValue(field);
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String)value;
        }
        return String.valueOf(value);
    }

    public static double getDoubleValue(DefaultTableEntity item, String field) {
        return NumberUtils.parseDouble((Object)item.getFieldValue(field));
    }

    public static int getIntValue(DefaultTableEntity item, String field) {
        Object value = item.getFieldValue(field);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (Integer)value;
        }
        return ConverterUtils.getAsInteger((Object)value);
    }

    public static String getFilterWhere(Map<String, Object> filterParam, String alias, List<Object> whereParam) {
        if (null == filterParam) {
            return "";
        }
        StringBuilder whereSql = new StringBuilder(256);
        for (Map.Entry<String, Object> entry : filterParam.entrySet()) {
            String key;
            Object tempValue = entry.getValue();
            if (tempValue == null || "mergeRange".equals(key = entry.getKey())) continue;
            if (tempValue instanceof List) {
                List values = (List)tempValue;
                if (key.endsWith("_number")) {
                    String biggerValue;
                    key = key.replace("_number", "");
                    if (CollectionUtils.isEmpty((Collection)values)) continue;
                    String smallerValue = (String)values.get(0);
                    if (!StringUtils.isEmpty((String)smallerValue) && smallerValue.matches("-?\\d+\\.?\\d*")) {
                        whereSql.append(" and ").append(alias).append(key).append(">=").append(smallerValue);
                    }
                    if (values.size() < 2 || StringUtils.isEmpty((String)(biggerValue = (String)values.get(1))) || !biggerValue.matches("-?\\d+\\.?\\d*")) continue;
                    whereSql.append(" and ").append(alias).append(key).append("<=").append(biggerValue).append("\n");
                    continue;
                }
                if (key.endsWith("_date")) {
                    String endDate;
                    key = key.replace("_date", "");
                    if (CollectionUtils.isEmpty((Collection)values)) continue;
                    String startDate = (String)values.get(0);
                    if (!StringUtils.isEmpty((String)startDate)) {
                        whereSql.append(" and ").append(alias).append(key).append(">= ? ").append("\n");
                        whereParam.add(InvestBillTool.formatDate(startDate));
                    }
                    if (values.size() < 2 || StringUtils.isEmpty((String)(endDate = (String)values.get(1)))) continue;
                    whereSql.append(" and ").append(alias).append(key).append("<= ? ").append("\n");
                    whereParam.add(InvestBillTool.formatDate(endDate));
                    continue;
                }
                if (CollectionUtils.isEmpty((Collection)values)) continue;
                whereSql.append(" and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)values, (String)(alias + key)));
                continue;
            }
            if (key.endsWith("_integer")) {
                key = key.replace("_integer", "");
                whereSql.append(" and ").append(alias).append(key).append("=").append(tempValue).append(" ");
                continue;
            }
            if (key.endsWith("_boolean")) {
                key = key.replace("_boolean", "");
                if (tempValue == null || tempValue.toString().equals("0")) {
                    whereSql.append(" and (").append(alias).append(key).append(" <> 1 or ").append(alias).append(key).append(" is null )");
                    continue;
                }
                whereSql.append(" and ").append(alias).append(key).append("=").append(tempValue).append(" ");
                continue;
            }
            whereSql.append(" and ").append(alias).append(key).append("='").append(tempValue).append("'");
        }
        return whereSql.toString();
    }

    public static void formatBillContent(List<Map<String, Object>> records, Map<String, Object> params, String tableDefineCode) {
        InvestBillTool.formatBillContent(records, params, tableDefineCode, true);
    }

    public static void formatBillContent(List<Map<String, Object>> records, Map<String, Object> params, String tableDefineCode, boolean formatNumber) {
        String periodStr = (String)params.get("periodStr");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        String orgType = (String)MapUtils.getVal(params, (Object)"orgType", (Object)"MD_ORG_CORPORATE");
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(tableDefineCode);
        DataModelDO dataModelDO = ((DataModelMaintainClient)SpringContextUtils.getBean(DataModelMaintainClient.class)).get(dataModelDTO);
        if (dataModelDO == null) {
            return;
        }
        List dataModelColumns = dataModelDO.getColumns();
        for (DataModelColumn fieldDefine : dataModelColumns) {
            String fieldCode2 = fieldDefine.getColumnName();
            if ("CREATEUSER".equals(fieldCode2)) {
                InvestBillTool.formatBillUserName(records, fieldCode2);
                continue;
            }
            if (!StringUtils.isEmpty((String)fieldDefine.getMapping())) {
                InvestBillTool.formatBillMappingField(records, orgCenterTool, fieldDefine, fieldCode2);
                continue;
            }
            if (formatNumber && DataModelType.ColumnType.NUMERIC.equals((Object)fieldDefine.getColumnType())) {
                InvestBillTool.formatBillNumericField(records, fieldDefine, fieldCode2);
                continue;
            }
            if (!DataModelType.ColumnType.DATE.equals((Object)fieldDefine.getColumnType()) && !DataModelType.ColumnType.TIMESTAMP.equals((Object)fieldDefine.getColumnType())) continue;
            InvestBillTool.formatBillDateField(records, fieldCode2);
        }
        List<String> fieldCodeList = Arrays.asList("FAIRVALUEADJUSTFLAG", "DISPOSEFLAG", "OFFSETINITFLAG", "FAIRVALUEOFFSETFLAG");
        for (Map<String, Object> record : records) {
            Integer srcType = ConverterUtils.getAsInteger((Object)record.get("SRCTYPE"));
            if (srcType != null) {
                if (srcType == 0) {
                    record.put("SRCTYPE", null);
                } else {
                    record.put("SRCTYPE", OffSetSrcTypeEnum.getNameByValue((int)srcType));
                }
            }
            fieldCodeList.forEach(fieldCode -> {
                Integer value = ConverterUtils.getAsInteger(record.get(fieldCode));
                if (null != value && value == 1) {
                    record.put((String)fieldCode, GcBillConsts.FINISHStatus.DONE.getContent());
                    if (fieldCode.equals("DISPOSEFLAG")) {
                        record.put((String)fieldCode, "\u5df2\u5904\u7f6e");
                    }
                } else {
                    record.put((String)fieldCode, GcBillConsts.FINISHStatus.UN_DO.getContent());
                }
            });
        }
    }

    private static Date formatDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static void formatBillDateField(List<Map<String, Object>> records, String fieldCode) {
        for (Map<String, Object> record : records) {
            Object value = record.get(fieldCode);
            try {
                if (null == value) continue;
                record.put(fieldCode, DateUtils.format((Date)((Date)value)));
            }
            catch (Exception e) {
                logger.error("\u65f6\u671f\u5b57\u6bb5\u8f6c\u6362\u5f02\u5e38\uff0c\u5b57\u6bb5:" + fieldCode + "\u5bf9\u5e94\u7684\u503c\u4e3a\uff1a" + value);
            }
        }
    }

    private static void formatBillNumericField(List<Map<String, Object>> records, DataModelColumn fieldDefine, String fieldCode) {
        int fractionDigits = fieldDefine.getLengths().length == 2 ? fieldDefine.getLengths()[1] : 0;
        for (Map<String, Object> record : records) {
            Object value = record.get(fieldCode);
            if (null == value) continue;
            String newValue = NumberUtils.doubleToString((Double)ConverterUtils.getAsDouble((Object)value), (int)fractionDigits);
            record.put(fieldCode, newValue);
        }
    }

    private static void formatBillMappingField(List<Map<String, Object>> records, GcOrgCenterService orgCenterTool, DataModelColumn fieldDefine, String fieldCode) {
        String dictTableName = fieldDefine.getMapping().split("\\.")[0];
        for (Map<String, Object> record : records) {
            String value = ConverterUtils.getAsString((Object)record.get(fieldCode));
            if (dictTableName.startsWith("MD_ORG")) {
                GcOrgCacheVO cacheVO = orgCenterTool.getOrgByCode(value);
                if (null == cacheVO) {
                    cacheVO = orgCenterTool.getBaseOrgByCode(value);
                }
                if (null == cacheVO) {
                    throw new BusinessRuntimeException("\u5728\u5355\u4f4d\u6811\u5f62\u4e2d\u672a\u627e\u5230\u5355\u4f4d\uff1a" + value);
                }
                record.put(fieldCode, cacheVO.getTitle());
                record.put(fieldCode + "_ID", cacheVO.getId());
                record.put(fieldCode + "_CODE", cacheVO.getCode());
                continue;
            }
            if (dictTableName.startsWith("MD_")) {
                String dictTitle = BaseDataUtils.getDictTitle((String)dictTableName, (String)value);
                if (StringUtils.isEmpty((String)dictTitle)) continue;
                record.put(fieldCode, dictTitle);
                record.put(fieldCode + "_CODE", value);
                continue;
            }
            if (!dictTableName.startsWith("EM_")) continue;
            EnumDataDTO enumDataDTO = new EnumDataDTO();
            enumDataDTO.setBiztype(dictTableName);
            enumDataDTO.setVal(value);
            List enumDataDOList = ((EnumDataClient)SpringContextUtils.getBean(EnumDataClient.class)).list(enumDataDTO);
            if (CollectionUtils.isEmpty((Collection)enumDataDOList)) continue;
            record.put(fieldCode, ((EnumDataDO)enumDataDOList.get(0)).getTitle());
            record.put(fieldCode + "_CODE", value);
        }
    }

    private static void formatBillUserName(List<Map<String, Object>> records, String fieldCode) {
        UserService userService = (UserService)SpringContextUtils.getBean(UserManagerService.class);
        for (Map<String, Object> record : records) {
            String value = (String)record.get(fieldCode);
            if (StringUtils.isEmpty((String)value)) continue;
            User user = userService.get(value);
            record.put(fieldCode, null == user ? "" : user.getFullname());
        }
    }

    public static boolean isDirectInvest(String unitCode, String oppUnitCode, GcOrgCenterService orgCenterTool) {
        GcOrgCacheVO unit = orgCenterTool.getOrgByCode(unitCode);
        GcOrgCacheVO oppUnit = orgCenterTool.getUnionUnitByGrade(oppUnitCode);
        if (!unit.getParentStr().contains(oppUnit.getParentId())) {
            return false;
        }
        GcOrgCacheVO parentUnitCacheVO = orgCenterTool.getOrgByCode(oppUnit.getParentId());
        if (null == parentUnitCacheVO) {
            return false;
        }
        String baseUnitId = parentUnitCacheVO.getBaseUnitId();
        if (StringUtils.isEmpty((String)baseUnitId)) {
            return false;
        }
        return unitCode.equals(baseUnitId) || unit.getParentStr().contains(baseUnitId);
    }

    public static boolean validateUserId(String userId) {
        int uuidLen = 36;
        return null == userId || userId.length() == 36;
    }

    public static String getBillCode(String uniqueCode, String unitCode) {
        BillCodeRuleDTO codeRuleDTO;
        try {
            IBillCodeRuleService billCodeRuleService = (IBillCodeRuleService)SpringContextUtils.getBean(IBillCodeRuleService.class);
            codeRuleDTO = billCodeRuleService.getRuleByUniqueCodeUnCheck(uniqueCode, false);
        }
        catch (Exception e) {
            throw new RuntimeException("\u751f\u6210\u5355\u636e\u7f16\u53f7\u5931\u8d25");
        }
        BillCodeDTO billCodeDTO = new BillCodeDTO();
        billCodeDTO.setUnitCode(unitCode);
        billCodeDTO.setCreateTime(new Date());
        billCodeDTO.setDimFormulaValue("");
        return BillCodeUtils.createBillCode((BillCodeRuleDTO)codeRuleDTO, (BillCodeDTO)billCodeDTO, (IBillCodeFlowService)((IBillCodeFlowService)SpringContextUtils.getBean(BillCodeFlowService.class)));
    }
}

