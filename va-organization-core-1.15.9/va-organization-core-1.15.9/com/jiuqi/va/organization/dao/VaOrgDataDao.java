/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgCacheDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
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
package com.jiuqi.va.organization.dao;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgCacheDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.organization.common.FormatValidationUtil;
import com.jiuqi.va.organization.domain.OrgDataUniqueDO;
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
import org.springframework.util.StringUtils;

@Mapper
public interface VaOrgDataDao
extends CustomOptMapper {
    @InsertProvider(type=SqlProvider.class, method="add")
    public int add(OrgDTO var1);

    @UpdateProvider(type=SqlProvider.class, method="update")
    public int update(OrgDTO var1);

    @DeleteProvider(type=SqlProvider.class, method="remove")
    public int remove(OrgDTO var1);

    @DeleteProvider(type=SqlProvider.class, method="removeSub")
    public int removeSub(OrgDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=10000)
    @SelectProvider(type=SqlProvider.class, method="findByCondition")
    public LinkedList<OrgCacheDO> list(OrgDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=10000)
    @SelectProvider(type=SqlProvider.class, method="selectByStartVer")
    public LinkedList<OrgCacheDO> selectByStartVer(OrgDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=10000)
    @SelectProvider(type=SqlProvider.class, method="selectGreaterVer")
    public LinkedList<OrgCacheDO> selectGreaterVer(OrgDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=10000)
    @SelectProvider(type=SqlProvider.class, method="selectAllId")
    public Set<UUID> selectAllId(OrgDTO var1);

    @Options(flushCache=Options.FlushCachePolicy.TRUE, resultSetType=ResultSetType.FORWARD_ONLY, fetchSize=10000)
    @SelectProvider(type=SqlProvider.class, method="selectUniqueInfo")
    public List<OrgDataUniqueDO> selectUniqueInfo(OrgDTO var1);

    public static class SqlProvider {
        public String add(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.INSERT_INTO(datamodel.getName());
            String colNameLower = null;
            String colNameUpper = null;
            for (DataModelColumn column : datamodel.getColumns()) {
                colNameLower = column.getColumnName().toLowerCase();
                colNameUpper = column.getColumnName().toUpperCase();
                if (!orgDTO.containsKey((Object)colNameLower)) continue;
                Object value = orgDTO.get((Object)colNameLower);
                if (column.getMappingType() != null && column.getMappingType() == 1 && value instanceof ArrayList) {
                    orgDTO.put("hasMultiValues", (Object)true);
                    continue;
                }
                sql.INTO_COLUMNS(new String[]{colNameUpper});
                sql.INTO_VALUES(new String[]{this.getSqlValStr(colNameLower, column.getColumnType(), orgDTO)});
            }
            return sql.toString();
        }

        public String update(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.UPDATE(datamodel.getName());
            String colNameLower = null;
            String colNameUpper = null;
            for (DataModelColumn column : datamodel.getColumns()) {
                colNameLower = column.getColumnName().toLowerCase();
                colNameUpper = column.getColumnName().toUpperCase();
                if (!orgDTO.containsKey((Object)colNameLower) && !orgDTO.containsKey((Object)(colNameLower + "_show")) || "id".equals(colNameLower)) continue;
                Object value = orgDTO.get((Object)colNameLower);
                if (column.getMappingType() != null && column.getMappingType() == 1 && value instanceof ArrayList) {
                    orgDTO.put("hasMultiValues", (Object)true);
                    continue;
                }
                sql.SET(colNameUpper + "=" + this.getSqlValStr(colNameLower, column.getColumnType(), orgDTO));
            }
            sql.WHERE("ID=#{id, jdbcType=VARCHAR}");
            return sql.toString();
        }

        public String remove(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.DELETE_FROM(datamodel.getName());
            sql.WHERE("ID=#{id, jdbcType=VARCHAR}");
            return sql.toString();
        }

        public String removeSub(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.DELETE_FROM(datamodel.getName() + "_SUBLIST");
            if (orgDTO.getId() != null) {
                sql.WHERE("MASTERID=#{id, jdbcType=VARCHAR}");
            } else {
                sql.WHERE("MASTERID not in(select ID from " + datamodel.getName() + ")");
            }
            return sql.toString();
        }

        public String findByCondition(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.SELECT(this.getSelectField(datamodel));
            sql.FROM(datamodel.getName());
            this.condition(sql, orgDTO);
            return sql.toString();
        }

        public void condition(SQL sql, OrgDTO orgDTO) {
            if (orgDTO.getId() != null) {
                sql.WHERE("ID = #{id, jdbcType=VARCHAR}");
            } else {
                List orgcodes;
                if (StringUtils.hasText(orgDTO.getName())) {
                    sql.WHERE("NAME = #{name, jdbcType=VARCHAR}");
                }
                if (StringUtils.hasText(orgDTO.getParentcode())) {
                    sql.WHERE("PARENTCODE = #{parentcode, jdbcType=VARCHAR}");
                }
                if (StringUtils.hasText(orgDTO.getOrgcode())) {
                    sql.WHERE("ORGCODE = #{orgcode, jdbcType=VARCHAR}");
                }
                if (orgDTO.getQueryChildrenType() != null) {
                    String parentsLike = "PARENTS like concat(#{parents, jdbcType=VARCHAR},'/%')";
                    if (orgDTO.getQueryChildrenType() == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN) {
                        sql.WHERE("PARENTCODE = #{code, jdbcType=VARCHAR}");
                    } else if (orgDTO.getQueryChildrenType() == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                        sql.WHERE("(CODE = #{code, jdbcType=VARCHAR} or PARENTCODE = #{code, jdbcType=VARCHAR})");
                    } else if (orgDTO.getQueryChildrenType() == OrgDataOption.QueryChildrenType.ALL_CHILDREN) {
                        sql.WHERE(parentsLike);
                    } else if (orgDTO.getQueryChildrenType() == OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
                        sql.WHERE("(CODE = #{code, jdbcType=VARCHAR} or " + parentsLike + ")");
                    }
                } else if (StringUtils.hasText(orgDTO.getCode())) {
                    sql.WHERE("CODE = #{code, jdbcType=VARCHAR}");
                }
                List codes = orgDTO.getOrgCodes();
                if (codes != null) {
                    sql.WHERE("(" + this.getCollectionSql(orgDTO, "code", codes) + ")");
                }
                if ((orgcodes = orgDTO.getOrgOrgcodes()) != null) {
                    sql.WHERE("(" + this.getCollectionSql(orgDTO, "orgcode", orgcodes) + ")");
                }
                if (orgDTO.getVersionDate() != null) {
                    sql.WHERE("VALIDTIME <= #{versionDate, jdbcType=TIMESTAMP}");
                    sql.WHERE("INVALIDTIME > #{versionDate, jdbcType=TIMESTAMP}");
                }
                if (orgDTO.getStopflag() != null) {
                    if (orgDTO.getStopflag() != -1) {
                        sql.WHERE("STOPFLAG = #{stopflag}");
                    }
                } else {
                    sql.WHERE("STOPFLAG = 0");
                }
                if (orgDTO.getRecoveryflag() != null) {
                    if (orgDTO.getRecoveryflag() != -1) {
                        sql.WHERE("RECOVERYFLAG = #{recoveryflag}");
                    }
                } else {
                    sql.WHERE("RECOVERYFLAG = 0");
                }
                if (orgDTO.getQueryStartVer() != null) {
                    sql.WHERE("VER > #{queryStartVer, jdbcType=NUMERIC}");
                } else if (orgDTO.getVer() != null) {
                    sql.WHERE("VER > #{ver, jdbcType=NUMERIC}");
                }
                if (orgDTO.getQueryEndVer() != null) {
                    sql.WHERE("VER <= #{queryEndVer, jdbcType=NUMERIC}");
                }
            }
            sql.ORDER_BY("ORDINAL,NAME");
        }

        private String getSelectField(DataModelDO datamodel) {
            List cols = datamodel.getColumns();
            StringBuilder sb = new StringBuilder();
            if (TenantUtil.isMultiTenant()) {
                sb.append("'").append(FormatValidationUtil.checkInjection(datamodel.getTenantName())).append("' as \"tenantname\",");
            }
            sb.append("'").append(datamodel.getName()).append("' as \"categoryname\",");
            for (DataModelColumn dataModelColumn : cols) {
                sb.append(dataModelColumn.getColumnName()).append(" as \"").append(dataModelColumn.getColumnName().toLowerCase()).append("\",");
            }
            return sb.substring(0, sb.length() - 1);
        }

        private DataModelDO getDataModel(OrgDTO orgDTO) {
            if (orgDTO.containsKey((Object)"hasDataModel")) {
                return (DataModelDO)orgDTO.get((Object)"hasDataModel");
            }
            DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setTenantName(orgDTO.getTenantName());
            dataModelDTO.setName(orgDTO.getCategoryname());
            dataModelDTO.setDeepClone(Boolean.valueOf(false));
            DataModelDO dataModel = dataModelClient.get(dataModelDTO);
            if (dataModel == null) {
                throw new RuntimeException(orgDTO.getCategoryname() + "\u5efa\u6a21\u4fe1\u606f\u4e0d\u5b58\u5728");
            }
            dataModel.setTenantName(orgDTO.getTenantName());
            return dataModel;
        }

        private String getSqlValStr(String colName, DataModelType.ColumnType type, OrgDTO orgDTO) {
            Object val;
            String valStr = null;
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
            if ("".equals(orgDTO.get((Object)colName))) {
                orgDTO.put(colName, null);
            }
            if ((val = orgDTO.get((Object)colName)) != null && (type.equals((Object)DataModelType.ColumnType.TIMESTAMP) || type.equals((Object)DataModelType.ColumnType.DATE))) {
                if (val instanceof Number) {
                    orgDTO.put(colName, (Object)new Date(((Number)val).longValue()));
                } else if (val instanceof String) {
                    String date = val.toString().replace("Z", " UTC");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                    try {
                        orgDTO.put(colName, (Object)format.parse(date));
                    }
                    catch (ParseException e) {
                        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            orgDTO.put(colName, (Object)format.parse(date));
                        }
                        catch (ParseException e1) {
                            format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                orgDTO.put(colName, (Object)format.parse(date));
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

        public String selectByStartVer(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.SELECT(this.getSelectField(datamodel));
            sql.FROM(datamodel.getName());
            if (orgDTO.getQueryStartVer() != null) {
                sql.WHERE("VER >= #{queryStartVer, jdbcType=NUMERIC}");
            } else {
                Collection versionDateList = (Collection)orgDTO.getExtInfo("VERSION_DATE_LIST");
                if (versionDateList != null && !versionDateList.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for (Date date : versionDateList) {
                        orgDTO.put("versionDate_" + i, (Object)date);
                        sb.append(" or (VALIDTIME <= #{versionDate_").append(i).append(", jdbcType=TIMESTAMP} and INVALIDTIME > #{versionDate_").append(i).append(", jdbcType=TIMESTAMP})");
                        ++i;
                    }
                    sql.WHERE("(" + sb.substring(3) + ")");
                }
            }
            return sql.toString();
        }

        public String selectGreaterVer(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.SELECT(this.getSelectField(datamodel));
            sql.FROM(datamodel.getName());
            if (orgDTO.getQueryStartVer() != null) {
                sql.WHERE("VER > #{queryStartVer, jdbcType=NUMERIC}");
            } else if (orgDTO.getVer() != null) {
                sql.WHERE("VER > #{ver, jdbcType=NUMERIC}");
            }
            if (orgDTO.getQueryEndVer() != null) {
                sql.WHERE("VER <= #{queryEndVer, jdbcType=NUMERIC}");
            }
            if (orgDTO.getVersionDate() != null) {
                sql.WHERE("VALIDTIME <= #{versionDate, jdbcType=TIMESTAMP}");
                sql.WHERE("INVALIDTIME > #{versionDate, jdbcType=TIMESTAMP}");
            }
            if (orgDTO.isPagination() && orgDTO.getLimit() > 0) {
                sql.ORDER_BY(new String[]{"VER", "CODE", "VALIDTIME"});
            }
            return sql.toString();
        }

        public String selectAllId(OrgDTO orgDTO) {
            DataModelDO datamodel = this.getDataModel(orgDTO);
            SQL sql = new SQL();
            sql.SELECT("id");
            sql.FROM(datamodel.getName());
            return sql.toString();
        }

        public String selectUniqueInfo(OrgDTO param) {
            List codes;
            DataModelDO datamodel = this.getDataModel(param);
            SQL sql = new SQL();
            sql.SELECT("id,code,validtime");
            sql.FROM(datamodel.getName());
            if (param.getVersionDate() != null) {
                sql.WHERE("VALIDTIME <= #{versionDate, jdbcType=TIMESTAMP}");
                sql.WHERE("INVALIDTIME > #{versionDate, jdbcType=TIMESTAMP}");
            }
            if (param.getCode() != null) {
                sql.WHERE("CODE = #{code, jdbcType=VARCHAR}");
            }
            if ((codes = param.getOrgCodes()) != null) {
                sql.WHERE("(" + this.getCollectionSql(param, "code", codes) + ")");
            }
            return sql.toString();
        }

        private String getCollectionSql(OrgDTO param, String field, List<String> codes) {
            int endIndex = codes.size() - 1;
            StringBuilder sb = new StringBuilder();
            int i = 0;
            String codeIn = null;
            for (String code : codes) {
                codeIn = field + "_" + i;
                param.put(codeIn, (Object)code);
                if (i % 1000 == 0) {
                    sb.append(" or ").append(field).append(" in (");
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
    }
}

