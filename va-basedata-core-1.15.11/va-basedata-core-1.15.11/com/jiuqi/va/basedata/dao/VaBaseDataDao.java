/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$SearchType
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSearchDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Options
 *  org.apache.ibatis.annotations.Options$FlushCachePolicy
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 *  org.apache.ibatis.mapping.ResultSetType
 */
package com.jiuqi.va.basedata.dao;

import com.jiuqi.va.basedata.common.FormatValidationUtil;
import com.jiuqi.va.basedata.domain.BaseDataDummyDTO;
import com.jiuqi.va.basedata.domain.BaseDataUniqueDO;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataSearchDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataSortDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import com.jiuqi.va.mapper.common.TenantUtil;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.ResultSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@Mapper
public interface VaBaseDataDao
extends CustomOptMapper {
    @InsertProvider(type=BaseDataSqlProvider.class, method="add")
    public int add(BaseDataDTO var1);

    @UpdateProvider(type=BaseDataSqlProvider.class, method="update")
    public int update(BaseDataDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=5000)
    @SelectProvider(type=BaseDataSqlProvider.class, method="list")
    public LinkedList<BaseDataCacheDO> list(BaseDataDTO var1);

    @SelectProvider(type=BaseDataSqlProvider.class, method="listDummy")
    public LinkedList<BaseDataDO> listDummy(BaseDataDummyDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=5000)
    @SelectProvider(type=BaseDataSqlProvider.class, method="selectByStartVer")
    public LinkedList<BaseDataCacheDO> selectByStartVer(BaseDataDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=5000)
    @SelectProvider(type=BaseDataSqlProvider.class, method="selectGreaterVer")
    public LinkedList<BaseDataCacheDO> selectGreaterVer(BaseDataDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=5000)
    @SelectProvider(type=BaseDataSqlProvider.class, method="selectAllId")
    public Set<UUID> selectAllId(BaseDataDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=5000)
    @SelectProvider(type=BaseDataSqlProvider.class, method="selectUniqueInfo")
    public List<BaseDataUniqueDO> selectUniqueInfo(BaseDataDTO var1);

    @DeleteProvider(type=BaseDataSqlProvider.class, method="removeSub")
    public int removeSub(BaseDataDTO var1);

    public static class BaseDataSqlProvider {
        private static Logger logger = LoggerFactory.getLogger(BaseDataSqlProvider.class);

        public String list(BaseDataDTO param) {
            DataModelDO datamodel = this.getDataModel((BaseDataDO)param);
            SQL sql = new SQL();
            sql.SELECT(this.getSelectField(datamodel));
            sql.FROM(datamodel.getName());
            this.setCondition(sql, param, datamodel);
            this.setOrderCondition(sql, param);
            return sql.toString();
        }

        private void setCondition(SQL sql, BaseDataDTO basedata, DataModelDO datamodel) {
            String colNameLower = null;
            String colNameUpper = null;
            Object value = null;
            if (basedata.getId() != null) {
                sql.WHERE("ID = #{id, jdbcType=VARCHAR}");
                return;
            }
            List cols = datamodel.getColumns();
            String localColName = "name_" + basedata.getLanguage();
            boolean hasLocalCol = false;
            for (DataModelColumn column : cols) {
                colNameLower = column.getColumnName().toLowerCase();
                colNameUpper = column.getColumnName().toUpperCase();
                if (!hasLocalCol && localColName.equalsIgnoreCase(colNameLower)) {
                    hasLocalCol = true;
                }
                if ("parents".equals(colNameLower) || "code".equals(colNameLower) && basedata.getQueryChildrenType() != null || "unitcode".equals(colNameLower) && basedata.getUnitcode() != null && basedata.containsKey((Object)"defineSharetype") && (Integer)basedata.get((Object)"defineSharetype") == 0) continue;
                if ("stopflag".equals(colNameLower)) {
                    if (basedata.getStopflag() == null) {
                        sql.WHERE(colNameUpper + "=0");
                        continue;
                    }
                    if (basedata.getStopflag() != -1) {
                        sql.WHERE(colNameUpper + "=" + basedata.getStopflag());
                        continue;
                    }
                    sql.WHERE(colNameUpper + " in(0,1)");
                    continue;
                }
                if ("recoveryflag".equals(colNameLower)) {
                    if (basedata.getRecoveryflag() == null) {
                        sql.WHERE(colNameUpper + "=0");
                        continue;
                    }
                    if (basedata.getRecoveryflag() != -1) {
                        sql.WHERE(colNameUpper + "=" + basedata.getRecoveryflag());
                        continue;
                    }
                    sql.WHERE(colNameUpper + " in(0,1)");
                    continue;
                }
                if (!basedata.containsKey((Object)colNameLower) || "ver".equalsIgnoreCase(colNameLower) || (value = basedata.get((Object)colNameLower)) == null || "".equals(value)) continue;
                sql.WHERE(colNameUpper + "=" + this.getSqlValStr(colNameLower, column.getColumnType(), basedata));
            }
            if (basedata.getDeepSearch() != null) {
                this.deepSearch(sql, basedata);
            } else if (StringUtils.hasText(basedata.getSearchKey())) {
                if (basedata.getSearchKey().trim().contains(" ")) {
                    this.multiKeywordSearch(sql, basedata.getSearchKey().trim());
                } else {
                    String likeSql = "like '%" + FormatValidationUtil.checkInjection(basedata.getSearchKey().toUpperCase()) + "%'";
                    if (hasLocalCol) {
                        sql.WHERE("(upper(CODE) " + likeSql + " or upper(NAME) " + likeSql + " or upper(SHORTNAME) " + likeSql + " or upper(" + localColName + ") " + likeSql + ")");
                    } else {
                        sql.WHERE("(upper(CODE) " + likeSql + " or upper(NAME) " + likeSql + " or upper(SHORTNAME) " + likeSql + ")");
                    }
                }
            }
            String groupFieldName = basedata.getGroupFieldName();
            List groupNames = basedata.getGroupNames();
            if (groupFieldName != null && groupNames != null && !groupNames.isEmpty()) {
                sql.WHERE("(" + this.getCollectionSql(basedata, groupFieldName, groupNames) + ")");
            }
            if (basedata.getQueryStartVer() != null) {
                sql.WHERE("VER > #{queryStartVer, jdbcType=NUMERIC}");
            } else if (basedata.getVer() != null) {
                sql.WHERE("VER > #{ver, jdbcType=NUMERIC}");
            }
            if (basedata.getQueryEndVer() != null) {
                sql.WHERE("VER <= #{queryEndVer, jdbcType=NUMERIC}");
            }
            if (basedata.getVersionDate() != null) {
                sql.WHERE("VALIDTIME <= #{versionDate, jdbcType=TIMESTAMP}");
                sql.WHERE("INVALIDTIME > #{versionDate, jdbcType=TIMESTAMP}");
            }
            if (basedata.get((Object)"filterSqlCondition") != null) {
                sql.WHERE("(" + basedata.get((Object)"filterSqlCondition").toString() + ")");
            }
            this.setCollectionQueryCondition(sql, basedata);
            if (basedata.getBaseDataCodes() == null && basedata.getBaseDataObjectcodes() == null) {
                this.setChildrenCondition(sql, basedata);
            }
        }

        private void deepSearch(SQL sql, BaseDataDTO basedata) {
            List deepSearch = basedata.getDeepSearch();
            String colNameLower = null;
            String colNameUpper = null;
            for (BaseDataSearchDTO searchDTO : deepSearch) {
                StringBuilder sb;
                colNameLower = FormatValidationUtil.checkInjection(searchDTO.getColumn().toLowerCase());
                colNameUpper = FormatValidationUtil.checkInjection(searchDTO.getColumn().toUpperCase());
                if (searchDTO.getSearchType() == BaseDataOption.SearchType.EQUALS) {
                    sql.WHERE(colNameUpper + " = '" + FormatValidationUtil.checkInjection(searchDTO.getValue()) + "'");
                    continue;
                }
                if (searchDTO.getSearchType() == BaseDataOption.SearchType.LIKE) {
                    sql.WHERE("upper(" + colNameUpper + ") like '%" + FormatValidationUtil.checkInjection(searchDTO.getValue().toUpperCase()) + "%'");
                    continue;
                }
                if (searchDTO.getSearchType() == BaseDataOption.SearchType.LIKE_IN) {
                    sb = new StringBuilder();
                    for (String key : searchDTO.getValues()) {
                        sb.append("upper(" + colNameUpper + ") like '%" + FormatValidationUtil.checkInjection(key.toUpperCase()) + "%' and ");
                    }
                    sql.WHERE("(" + sb.substring(0, sb.length() - 5) + ")");
                    continue;
                }
                if (searchDTO.getSearchType() == BaseDataOption.SearchType.IN) {
                    sb = new StringBuilder();
                    for (String key : searchDTO.getValues()) {
                        sb.append("upper(" + colNameUpper + ") = '" + FormatValidationUtil.checkInjection(key.toUpperCase()) + "' or ");
                    }
                    sql.WHERE("(" + sb.substring(0, sb.length() - 4) + ")");
                    continue;
                }
                if (searchDTO.getSearchType() != BaseDataOption.SearchType.BETWEEN) continue;
                if (searchDTO.getBetweenDateValues() != null) {
                    if (searchDTO.getBetweenDateValues()[0] != null) {
                        basedata.put(colNameLower + "BetweenStart", (Object)searchDTO.getBetweenDateValues()[0]);
                        sql.WHERE(colNameUpper + " >= #{" + colNameLower + "BetweenStart,jdbcType=TIMESTAMP}");
                    }
                    if (searchDTO.getBetweenDateValues()[1] == null) continue;
                    basedata.put(colNameLower + "BetweenEnd", (Object)searchDTO.getBetweenDateValues()[1]);
                    sql.WHERE(colNameUpper + " <= #{" + colNameLower + "BetweenEnd,jdbcType=TIMESTAMP}");
                    continue;
                }
                if (searchDTO.getBetweenNumericValues() == null) continue;
                if (searchDTO.getBetweenNumericValues()[0] != null) {
                    basedata.put(colNameLower + "BetweenStart", (Object)searchDTO.getBetweenNumericValues()[0]);
                    sql.WHERE(colNameUpper + " >= #{" + colNameLower + "BetweenStart,jdbcType=NUMERIC}");
                }
                if (searchDTO.getBetweenNumericValues()[1] == null) continue;
                basedata.put(colNameLower + "BetweenEnd", (Object)searchDTO.getBetweenNumericValues()[1]);
                sql.WHERE(colNameUpper + " <= #{" + colNameLower + "BetweenEnd,jdbcType=NUMERIC}");
            }
        }

        private void multiKeywordSearch(SQL sql, String searchKey) {
            String[] searchKeyArr = searchKey.split(" ");
            StringBuilder likeSql = new StringBuilder();
            for (String key : searchKeyArr) {
                likeSql.append(" or NAME like '%").append(FormatValidationUtil.checkInjection(key)).append("%'");
            }
            sql.WHERE(" (" + likeSql.substring(4) + ")");
        }

        private void setCollectionQueryCondition(SQL sql, BaseDataDTO param) {
            String field = "";
            List list = param.getBaseDataCodes();
            if (list != null) {
                field = "code";
            } else {
                list = param.getBaseDataObjectcodes();
                field = "objectcode";
            }
            if (list == null || list.isEmpty()) {
                return;
            }
            String condi1 = this.getCollectionSql(param, field, list);
            String condi2 = null;
            if ("code".equals(field) && param.getQueryChildrenType() != null) {
                condi2 = this.getCollectionSql(param, "parentcode", list);
            }
            if (condi2 == null) {
                sql.WHERE(" (" + condi1 + ") ");
            } else if (param.getQueryChildrenType() == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                sql.WHERE(" (" + condi1 + " or " + condi2 + ") ");
            } else {
                sql.WHERE(" (" + condi2 + ") ");
            }
        }

        private String getCollectionSql(BaseDataDTO param, String field, List<String> codes) {
            int endIndex = codes.size() - 1;
            StringBuilder sb = new StringBuilder();
            int i = 0;
            String codeIn = null;
            for (String code : codes) {
                codeIn = field + "_" + i;
                param.put(codeIn, (Object)code);
                if (i % 1000 == 0) {
                    sb.append(" or ").append(FormatValidationUtil.checkInjection(field)).append(" in (");
                }
                sb.append("#{").append(codeIn).append(", jdbcType=VARCHAR}");
                if (i % 1000 == 999 || i == endIndex) {
                    sb.append(")");
                } else {
                    sb.append(",");
                }
                ++i;
            }
            return sb.substring(4);
        }

        private void setChildrenCondition(SQL sql, BaseDataDTO param) {
            if (param.getQueryChildrenType() == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN) {
                sql.WHERE("PARENTCODE = #{code, jdbcType=VARCHAR}");
            } else if (param.getQueryChildrenType() == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                sql.WHERE("(CODE = #{code, jdbcType=VARCHAR} or PARENTCODE = #{code, jdbcType=VARCHAR})");
            } else if (param.getQueryChildrenType() == BaseDataOption.QueryChildrenType.ALL_CHILDREN) {
                if (!param.getCode().equals("-")) {
                    sql.WHERE("PARENTS like '" + param.getParents() + "/%'");
                }
            } else if (param.getQueryChildrenType() == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF && !param.getCode().equals("-")) {
                sql.WHERE("(CODE = #{code, jdbcType=VARCHAR} or PARENTS like '" + param.getParents() + "/%')");
            }
        }

        private void setOrderCondition(SQL sql, BaseDataDTO param) {
            List orderBy = param.getOrderBy();
            if (orderBy != null) {
                if (orderBy.isEmpty()) {
                    return;
                }
                for (BaseDataSortDTO baseDataSortDTO : orderBy) {
                    sql.ORDER_BY(FormatValidationUtil.checkInjection(baseDataSortDTO.getColumn()) + " " + baseDataSortDTO.getOrder().toString());
                }
                return;
            }
            Integer defineStructtype = (Integer)param.get((Object)"defineStructtype");
            if (defineStructtype == null) {
                sql.ORDER_BY("ORDINAL asc");
                return;
            }
            if (defineStructtype == 1) {
                if (param.getGroupFieldName() != null) {
                    sql.ORDER_BY(FormatValidationUtil.checkInjection(param.getGroupFieldName()) + " asc,ORDINAL asc");
                } else {
                    sql.ORDER_BY("ORDINAL asc");
                }
                return;
            }
            if (defineStructtype == 3) {
                sql.ORDER_BY("CODE asc");
                return;
            }
            sql.ORDER_BY("ORDINAL asc");
        }

        public String selectByStartVer(BaseDataDTO param) {
            DataModelDO datamodel = this.getDataModel((BaseDataDO)param);
            SQL sql = new SQL();
            sql.SELECT(this.getSelectField(datamodel));
            sql.FROM(datamodel.getName());
            if (param.getQueryStartVer() != null) {
                sql.WHERE("VER >= #{queryStartVer, jdbcType=NUMERIC}");
            } else {
                Collection versionDateList = (Collection)param.getExtInfo("VERSION_DATE_LIST");
                if (versionDateList != null && !versionDateList.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for (Date date : versionDateList) {
                        param.put("versionDate_" + i, (Object)date);
                        sb.append(" or (VALIDTIME <= #{versionDate_").append(i).append(", jdbcType=TIMESTAMP} and INVALIDTIME > #{versionDate_").append(i).append(", jdbcType=TIMESTAMP})");
                        ++i;
                    }
                    sql.WHERE("(" + sb.substring(3) + ")");
                }
            }
            return sql.toString();
        }

        public String selectGreaterVer(BaseDataDTO basedata) {
            DataModelDO datamodel = this.getDataModel((BaseDataDO)basedata);
            SQL sql = new SQL();
            sql.SELECT(this.getSelectField(datamodel));
            sql.FROM(datamodel.getName());
            sql.WHERE("STOPFLAG in(0,1)");
            sql.WHERE("RECOVERYFLAG in(0,1)");
            if (basedata.getQueryStartVer() != null) {
                sql.WHERE("VER > #{queryStartVer, jdbcType=NUMERIC}");
            } else if (basedata.getVer() != null) {
                sql.WHERE("VER > #{ver, jdbcType=NUMERIC}");
            }
            if (basedata.getQueryEndVer() != null) {
                sql.WHERE("VER <= #{queryEndVer, jdbcType=NUMERIC}");
            }
            if (basedata.getVersionDate() != null) {
                sql.WHERE("VALIDTIME <= #{versionDate, jdbcType=TIMESTAMP}");
                sql.WHERE("INVALIDTIME > #{versionDate, jdbcType=TIMESTAMP}");
            }
            if (basedata.isPagination() && basedata.getLimit() > 0) {
                sql.ORDER_BY(new String[]{"VER", "OBJECTCODE", "VALIDTIME"});
            }
            return sql.toString();
        }

        private String getSelectField(DataModelDO datamodel) {
            StringBuilder sb = new StringBuilder();
            if (TenantUtil.isMultiTenant()) {
                sb.append("'").append(FormatValidationUtil.checkInjection(datamodel.getTenantName())).append("' as \"tenantname\",");
            }
            sb.append("'").append(datamodel.getName()).append("' as \"tablename\",");
            for (DataModelColumn dataModelColumn : datamodel.getColumns()) {
                sb.append(dataModelColumn.getColumnName()).append(" as \"").append(dataModelColumn.getColumnName().toLowerCase()).append("\",");
            }
            return sb.substring(0, sb.length() - 1);
        }

        public String add(BaseDataDTO basedata) {
            DataModelDO datamodel = this.getDataModel((BaseDataDO)basedata);
            SQL sql = new SQL();
            sql.INSERT_INTO(datamodel.getName());
            String colNameLower = null;
            String colNameUpper = null;
            Object value = null;
            for (DataModelColumn column : datamodel.getColumns()) {
                colNameLower = column.getColumnName().toLowerCase();
                colNameUpper = column.getColumnName().toUpperCase();
                if (!basedata.containsKey((Object)colNameLower)) continue;
                value = basedata.get((Object)colNameLower);
                if (column.getMappingType() != null && column.getMappingType() == 1 && value instanceof ArrayList) {
                    basedata.put("hasMultiValues", (Object)true);
                    continue;
                }
                sql.INTO_COLUMNS(new String[]{colNameUpper});
                sql.INTO_VALUES(new String[]{this.getSqlValStr(colNameLower, column.getColumnType(), basedata)});
            }
            return sql.toString();
        }

        public String update(BaseDataDTO basedata) {
            DataModelDO datamodel = this.getDataModel((BaseDataDO)basedata);
            SQL sql = new SQL();
            sql.UPDATE(datamodel.getName());
            String colNameLower = null;
            String colNameUpper = null;
            Object value = null;
            for (DataModelColumn column : datamodel.getColumns()) {
                colNameLower = column.getColumnName().toLowerCase();
                colNameUpper = column.getColumnName().toUpperCase();
                if (!basedata.containsKey((Object)colNameLower) && !basedata.containsKey((Object)(colNameLower + "_show")) || "id".equals(colNameLower)) continue;
                value = basedata.get((Object)colNameLower);
                if (column.getMappingType() != null && column.getMappingType() == 1 && value instanceof ArrayList) {
                    basedata.put("hasMultiValues", (Object)true);
                    continue;
                }
                sql.SET(colNameUpper + "=" + this.getSqlValStr(colNameLower, column.getColumnType(), basedata));
            }
            sql.WHERE("ID=#{id}");
            return sql.toString();
        }

        private DataModelDO getDataModel(BaseDataDO basedata) {
            if (basedata.containsKey((Object)"hasDataModel")) {
                return (DataModelDO)basedata.get((Object)"hasDataModel");
            }
            DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setTenantName(basedata.getTenantName());
            dataModelDTO.setName(basedata.getTableName());
            dataModelDTO.setDeepClone(Boolean.valueOf(false));
            DataModelDO dataModel = dataModelClient.get(dataModelDTO);
            if (dataModel == null) {
                throw new RuntimeException("\u5efa\u6a21\u4fe1\u606f\u4e0d\u5b58\u5728");
            }
            dataModel.setTenantName(basedata.getTenantName());
            return dataModel;
        }

        private String getSqlValStr(String colName, DataModelType.ColumnType type, BaseDataDTO basedata) {
            String valStr = null;
            Object val = null;
            if (type.equals((Object)DataModelType.ColumnType.NVARCHAR) || type.equals((Object)DataModelType.ColumnType.UUID)) {
                valStr = "#{" + colName + ", jdbcType=VARCHAR}";
            } else if (type.equals((Object)DataModelType.ColumnType.TIMESTAMP)) {
                valStr = "#{" + colName + ", jdbcType=TIMESTAMP}";
            } else if (type.equals((Object)DataModelType.ColumnType.DATE)) {
                valStr = "#{" + colName + ", jdbcType=DATE}";
            } else if (type.equals((Object)DataModelType.ColumnType.NUMERIC) || type.equals((Object)DataModelType.ColumnType.INTEGER)) {
                valStr = "#{" + colName + ", jdbcType=NUMERIC}";
            } else if (type.equals((Object)DataModelType.ColumnType.CLOB)) {
                valStr = "#{" + colName + ", jdbcType=CLOB}";
            }
            if ("".equals(basedata.get((Object)colName))) {
                basedata.put(colName, null);
            }
            if ((val = basedata.get((Object)colName)) != null && type.equals((Object)DataModelType.ColumnType.NUMERIC)) {
                if (val instanceof String) {
                    basedata.put(colName, (Object)new BigDecimal((String)val));
                } else if (!(val instanceof BigDecimal)) {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(0);
                    nf.setMaximumFractionDigits(20);
                    nf.setGroupingUsed(false);
                    basedata.put(colName, (Object)new BigDecimal(nf.format(val)));
                }
            }
            if (val != null && (type.equals((Object)DataModelType.ColumnType.TIMESTAMP) || type.equals((Object)DataModelType.ColumnType.DATE))) {
                if (val instanceof Number) {
                    basedata.put(colName, (Object)new Date(((Number)val).longValue()));
                } else if (val instanceof String) {
                    String date = val.toString().replace("Z", " UTC");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                    try {
                        basedata.put(colName, (Object)format.parse(date));
                    }
                    catch (ParseException e) {
                        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            basedata.put(colName, (Object)format.parse(date));
                        }
                        catch (ParseException e1) {
                            format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                basedata.put(colName, (Object)format.parse(date));
                            }
                            catch (ParseException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }
            return valStr;
        }

        public String listDummy(BaseDataDummyDTO param) {
            String sql = "";
            try {
                sql = URLDecoder.decode(param.getSqlDefine(), "UTF-8").replaceAll("(\r\n|\r|\n|\n\r)", " ");
                String tmpSql = sql.trim().toLowerCase();
                int fromIndex = tmpSql.indexOf(" from ");
                int fromIndex2 = tmpSql.indexOf(" from(");
                if (fromIndex2 != -1 && fromIndex2 < fromIndex) {
                    fromIndex = fromIndex2;
                }
                String sqlColumnStr = sql.substring(7, fromIndex).trim().toLowerCase();
                StringBuilder colsb = new StringBuilder();
                String column = null;
                for (String colAs : sqlColumnStr.split("\\,")) {
                    column = colAs.split(" as ")[1].trim();
                    colsb.append("t9797.").append(column).append(" as \"").append(column).append("\",");
                }
                String colSql = colsb.substring(0, colsb.length() - 1);
                sql = "select " + colSql + " from (" + sql + ") t9797";
                if (param.getExtInfo("objectcode") != null) {
                    sql = sql + " where t9797.objectcode=#{extInfo.objectcode, jdbcType=VARCHAR}";
                } else if (param.getExtInfo("code") != null) {
                    sql = sql + " where t9797.code=#{extInfo.code, jdbcType=VARCHAR}";
                }
            }
            catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
            return sql;
        }

        public String selectAllId(BaseDataDTO param) {
            DataModelDO datamodel = this.getDataModel((BaseDataDO)param);
            SQL sql = new SQL();
            sql.SELECT("id");
            sql.FROM(datamodel.getName());
            return sql.toString();
        }

        public String selectUniqueInfo(BaseDataDTO param) {
            List sharefields;
            List objectcodes;
            DataModelDO datamodel = this.getDataModel((BaseDataDO)param);
            SQL sql = new SQL();
            sql.SELECT("id,objectcode,validtime");
            sql.FROM(datamodel.getName());
            if (param.getVersionDate() != null) {
                sql.WHERE("VALIDTIME <= #{versionDate, jdbcType=TIMESTAMP}");
                sql.WHERE("INVALIDTIME > #{versionDate, jdbcType=TIMESTAMP}");
            }
            if (param.getObjectcode() != null) {
                sql.WHERE("OBJECTCODE = #{objectcode, jdbcType=VARCHAR}");
            }
            if ((objectcodes = param.getBaseDataObjectcodes()) != null) {
                sql.WHERE(" (" + this.getCollectionSql(param, "objectcode", objectcodes) + ") ");
            }
            if ((sharefields = (List)param.get((Object)"sharefields")) != null) {
                for (String field : sharefields) {
                    if (param.get((Object)field) == null) continue;
                    sql.WHERE(field + " = #{" + field + ", jdbcType=VARCHAR}");
                }
            }
            return sql.toString();
        }

        public String removeSub(BaseDataDTO param) {
            DataModelDO datamodel = this.getDataModel((BaseDataDO)param);
            SQL sql = new SQL();
            sql.DELETE_FROM(datamodel.getName() + "_SUBLIST");
            if (param.getId() != null) {
                sql.WHERE("MASTERID=#{id, jdbcType=VARCHAR}");
            } else {
                sql.WHERE("MASTERID not in(select ID from " + datamodel.getName() + ")");
            }
            return sql.toString();
        }
    }
}

