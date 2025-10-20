/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.InsertProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bill.bd.bill.dao;

import com.jiuqi.va.bill.bd.bill.domain.CreateBillExceptionDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface MaintainBillExceptionDao
extends CustomOptMapper {
    public static final String TABLENAME = "CREATEBILL_EXCEPTION";

    @InsertProvider(type=ApplyRegExceptionProvider.class, method="insertData")
    public int insertData(CreateBillExceptionDTO var1);

    @SelectProvider(type=ApplyRegExceptionProvider.class, method="queryData")
    public List<CreateBillExceptionDTO> queryData(CreateBillExceptionDTO var1);

    @SelectProvider(type=ApplyRegExceptionProvider.class, method="queryExceptionData")
    public List<CreateBillExceptionDTO> queryExceptionData(CreateBillExceptionDTO var1);

    @UpdateProvider(type=ApplyRegExceptionProvider.class, method="updateData")
    public int updateData(CreateBillExceptionDTO var1);

    @SelectProvider(type=ApplyRegExceptionProvider.class, method="queryLatestData")
    public List<CreateBillExceptionDTO> queryLatestData(CreateBillExceptionDTO var1);

    public static class ApplyRegExceptionProvider {
        public String queryLatestData(CreateBillExceptionDTO requestDTO) {
            SQL sql = new SQL(){
                {
                    this.SELECT("*");
                    this.FROM(MaintainBillExceptionDao.TABLENAME);
                }
            };
            sql.WHERE(" 1 = 1 ");
            if (null != requestDTO.getSrcbillcode()) {
                ((SQL)sql.AND()).WHERE("srcbillcode=#{srcbillcode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getSrcdefinecode()) {
                ((SQL)sql.AND()).WHERE("srcdefinecode=#{srcdefinecode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getBillcode()) {
                ((SQL)sql.AND()).WHERE("billcode=#{billcode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getDefinecode()) {
                ((SQL)sql.AND()).WHERE("definecode=#{definecode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getConfigname()) {
                ((SQL)sql.AND()).WHERE("configname=#{configname,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getCreatetype()) {
                ((SQL)sql.AND()).WHERE("createtype=#{createtype,jdbcType=NUMERIC}");
            }
            if (null != requestDTO.getSrcmasterid()) {
                ((SQL)sql.AND()).WHERE("srcmasterid=#{srcmasterid,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getSrcdetailbillid()) {
                ((SQL)sql.AND()).WHERE("srcdetailbillid=#{srcdetailbillid,jdbcType=VARCHAR}");
            }
            sql.ORDER_BY("ver");
            return sql.toString() + "  desc";
        }

        public String queryData(CreateBillExceptionDTO requestDTO) {
            SQL sql = new SQL(){
                {
                    this.SELECT("*");
                    this.FROM(MaintainBillExceptionDao.TABLENAME);
                }
            };
            sql.WHERE(" 1 = 1 ");
            if (null != requestDTO.getSrcbillcode()) {
                ((SQL)sql.AND()).WHERE("srcbillcode=#{srcbillcode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getSrcdefinecode()) {
                ((SQL)sql.AND()).WHERE("srcdefinecode=#{srcdefinecode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getBillcode()) {
                ((SQL)sql.AND()).WHERE("billcode=#{billcode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getDefinecode()) {
                ((SQL)sql.AND()).WHERE("definecode=#{definecode,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getConfigname()) {
                ((SQL)sql.AND()).WHERE("configname=#{configname,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getCreatetype()) {
                ((SQL)sql.AND()).WHERE("createtype=#{createtype,jdbcType=NUMERIC}");
            }
            if (null != requestDTO.getSrcmasterid()) {
                ((SQL)sql.AND()).WHERE("srcmasterid=#{srcmasterid,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getSrcdetailbillid()) {
                ((SQL)sql.AND()).WHERE("srcdetailbillid=#{srcdetailbillid,jdbcType=VARCHAR}");
            }
            if (null != requestDTO.getId()) {
                ((SQL)sql.AND()).WHERE("id=#{id,jdbcType=VARCHAR}");
            }
            sql.ORDER_BY("ORDINAL");
            return sql.toString();
        }

        public String insertData(CreateBillExceptionDTO requestDTO) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            SQL sql = new SQL();
            sql.INSERT_INTO(MaintainBillExceptionDao.TABLENAME);
            Map<String, DataModelType.ColumnType> fields = this.getInnerParamValue(data, requestDTO);
            StringBuilder str = new StringBuilder();
            for (String field : fields.keySet()) {
                Object value = data.get(field);
                if (value == null || "".equals(value.toString())) continue;
                sql.INTO_COLUMNS(new String[]{field});
                DataModelType.ColumnType type = fields.get(field);
                if (type.equals((Object)DataModelType.ColumnType.NVARCHAR) || type.equals((Object)DataModelType.ColumnType.UUID)) {
                    str.append("#{").append(field).append(",jdbcType=VARCHAR},");
                    continue;
                }
                if (type.equals((Object)DataModelType.ColumnType.DATE) || type.equals((Object)DataModelType.ColumnType.TIMESTAMP)) {
                    str.append("#{").append(field).append(", jdbcType=TIMESTAMP},");
                    continue;
                }
                if (type.equals((Object)DataModelType.ColumnType.NUMERIC) || type.equals((Object)DataModelType.ColumnType.INTEGER)) {
                    str.append("#{").append(field).append(",jdbcType=NUMERIC},");
                    continue;
                }
                if (!type.equals((Object)DataModelType.ColumnType.CLOB)) continue;
                str.append("#{").append(field).append(",jdbcType=CLOB},");
            }
            String sqlparam = str.substring(0, str.length() - 1);
            sql.INTO_VALUES(new String[]{sqlparam});
            return sql.toString();
        }

        public String updateData(CreateBillExceptionDTO requestDTO) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            SQL sql = new SQL();
            sql.UPDATE(MaintainBillExceptionDao.TABLENAME);
            Map<String, DataModelType.ColumnType> fields = this.getInnerParamValue(data, requestDTO);
            StringBuilder str = new StringBuilder();
            for (String field : fields.keySet()) {
                if (field.equals("ID".toLowerCase())) continue;
                DataModelType.ColumnType type = fields.get(field);
                if (type.equals((Object)DataModelType.ColumnType.NVARCHAR) || type.equals((Object)DataModelType.ColumnType.UUID)) {
                    str.append(field).append("=#{").append(field).append(",jdbcType=VARCHAR},");
                    continue;
                }
                if (type.equals((Object)DataModelType.ColumnType.DATE) || type.equals((Object)DataModelType.ColumnType.TIMESTAMP)) {
                    str.append(field).append("=#{").append(field).append(",jdbcType=TIMESTAMP},");
                    continue;
                }
                if (type.equals((Object)DataModelType.ColumnType.NUMERIC) || type.equals((Object)DataModelType.ColumnType.INTEGER)) {
                    str.append(field).append("=#{").append(field).append(",jdbcType=NUMERIC},");
                    continue;
                }
                if (!type.equals((Object)DataModelType.ColumnType.CLOB)) continue;
                str.append(field).append("=#{").append(field).append(",jdbcType=CLOB},");
            }
            String sqlparam = str.substring(0, str.length() - 1);
            sql.SET(sqlparam);
            sql.WHERE("1=1 \n");
            ((SQL)sql.AND()).WHERE("id=#{id,jdbcType=VARCHAR}");
            return sql.toString();
        }

        public String queryExceptionData(CreateBillExceptionDTO requestDTO) {
            SQL sql = new SQL(){
                {
                    this.SELECT("*");
                    this.FROM(MaintainBillExceptionDao.TABLENAME);
                }
            };
            sql.WHERE(" 1 = 1 ");
            if (null != requestDTO.getCreatebillstate()) {
                ((SQL)sql.AND()).WHERE("createbillstate in (1,3,4,5)");
            }
            sql.ORDER_BY("ORDINAL");
            return sql.toString();
        }

        public Map<String, DataModelType.ColumnType> getInnerParamValue(Map<String, Object> data, CreateBillExceptionDTO requestDTO) {
            HashMap<String, DataModelType.ColumnType> list = new HashMap<String, DataModelType.ColumnType>();
            list.put("ID".toLowerCase(), DataModelType.ColumnType.UUID);
            data.put("ID".toLowerCase(), requestDTO.getId());
            list.put("VER".toLowerCase(), DataModelType.ColumnType.NUMERIC);
            data.put("VER".toLowerCase(), requestDTO.getVer());
            list.put("ORDINAL".toLowerCase(), DataModelType.ColumnType.NUMERIC);
            data.put("ORDINAL".toLowerCase(), requestDTO.getOrdinal());
            list.put("CREATETIME".toLowerCase(), DataModelType.ColumnType.TIMESTAMP);
            data.put("CREATETIME".toLowerCase(), requestDTO.getCreatetime());
            list.put("CREATETYPE".toLowerCase(), DataModelType.ColumnType.INTEGER);
            data.put("CREATETYPE".toLowerCase(), requestDTO.getCreatetype());
            list.put("CONFIGNAME".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("CONFIGNAME".toLowerCase(), requestDTO.getConfigname());
            list.put("SRCDEFINENAME".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("SRCDEFINENAME".toLowerCase(), requestDTO.getSrcdefinename());
            list.put("SRCDEFINECODE".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("SRCDEFINECODE".toLowerCase(), requestDTO.getSrcdefinecode());
            list.put("SRCBILLCODE".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("SRCBILLCODE".toLowerCase(), requestDTO.getSrcbillcode());
            list.put("SRCMASTERID".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("SRCMASTERID".toLowerCase(), requestDTO.getSrcmasterid());
            list.put("SRCDETAILBILLID".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("SRCDETAILBILLID".toLowerCase(), requestDTO.getSrcdetailbillid());
            list.put("DEFINENAME".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("DEFINENAME".toLowerCase(), requestDTO.getDefinename());
            list.put("DEFINECODE".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("DEFINECODE".toLowerCase(), requestDTO.getDefinecode());
            list.put("BILLCODE".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("BILLCODE".toLowerCase(), requestDTO.getBillcode());
            list.put("MASTERID".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("MASTERID".toLowerCase(), requestDTO.getMasterid());
            list.put("CREATEBILLSTATE".toLowerCase(), DataModelType.ColumnType.INTEGER);
            data.put("CREATEBILLSTATE".toLowerCase(), requestDTO.getCreatebillstate());
            list.put("CACHESYNCDISABLE".toLowerCase(), DataModelType.ColumnType.INTEGER);
            data.put("CACHESYNCDISABLE".toLowerCase(), requestDTO.getCachesyncdisable());
            list.put("MEMO".toLowerCase(), DataModelType.ColumnType.NVARCHAR);
            data.put("MEMO".toLowerCase(), requestDTO.getMemo());
            return list;
        }
    }
}

