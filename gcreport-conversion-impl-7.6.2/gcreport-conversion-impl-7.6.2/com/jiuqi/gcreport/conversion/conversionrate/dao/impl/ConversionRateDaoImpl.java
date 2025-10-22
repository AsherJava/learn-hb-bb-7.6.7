/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.conversion.conversionrate.dao.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionrate.dao.ConversionRateDao;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateEO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ConversionRateDaoImpl
extends GcDbSqlGenericDAO<ConversionRateEO, String>
implements ConversionRateDao {
    public ConversionRateDaoImpl() {
        super(ConversionRateEO.class);
    }

    @Override
    public List<ConversionRateEO> getRateEO(String periodId, String systemId, String sourceCurrencyCode, String targetCurrencyCode, String dataTime, String rateTypeCode) {
        String sql;
        List mapList;
        StringBuffer whereSql = new StringBuffer();
        if (periodId != null && periodId.trim().length() > 0) {
            whereSql.append("   and g.periodId ='" + periodId + "'  \n");
        }
        if (rateTypeCode != null && rateTypeCode.trim().length() > 0) {
            whereSql.append("   and v.rateTypeCode ='" + rateTypeCode + "'  \n");
        }
        if ((mapList = this.selectMap(sql = this.getSqlColumns() + "  where   1 = 1\n  and   g.systemId = ?\n  and   t.sourceCurrencyCode = ? \n  and   t.targetCurrencyCode = ? \n  and   v.datatime = ? \n" + whereSql.toString(), new Object[]{systemId, sourceCurrencyCode, targetCurrencyCode, dataTime})) != null && mapList.size() > 0) {
            return this.convertMapList2Eos(mapList, "all".equals(sourceCurrencyCode));
        }
        return Collections.emptyList();
    }

    @Override
    public Boolean updateRate(ConversionRateEO item) {
        String sql = "  update GC_CONV_RATE_V  scheme \n   set rateValue = ?, dataTime = ?, \n   updateTime = ?\n  where   scheme.nodeId = ? \n  and   scheme.rowDataId = ? \n  and   scheme.rateTypeCode = ? \n";
        Integer result = this.execute(sql, new Object[]{item.getRateValue(), item.getDataTime(), item.getUpdateTime(), item.getNodeId(), item.getRowDataId(), item.getRateTypeCode()});
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<ConversionRateEO> queryByNodeId(String nodeId) {
        String sql = "  select " + ConversionRateEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_V" + "   scheme  \n  where  scheme.nodeId =?  \n";
        return this.selectEntity(sql, new Object[]{nodeId});
    }

    @Override
    public List<ConversionRateEO> queryByRowId(String rowId) {
        String sql = "  select " + ConversionRateEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_V" + "   scheme  \n  where  scheme.rowDataId =?  \n";
        return this.selectEntity(sql, new Object[]{rowId});
    }

    @Override
    public ConversionRateEO queryByRowId(String rowDataId, String rateTypeCode) {
        String sql = "  select " + ConversionRateEO.getAllFieldSql("scheme") + "  from  " + "GC_CONV_RATE_V" + "   scheme  \n  where  scheme.rowDataId =?  \n  and   scheme.rateTypeCode = ? \n";
        List dataList = this.selectEntity(sql, new Object[]{rowDataId, rateTypeCode});
        if (dataList != null && dataList.size() > 0) {
            return (ConversionRateEO)((Object)dataList.get(0));
        }
        return null;
    }

    @Override
    public void deleteByRowId(String rowId) {
        String sql = "  delete from GC_CONV_RATE_V   \n  where  rowDataId =?  \n";
        this.execute(sql, new Object[]{rowId});
    }

    @Override
    public void deleteByNodeId(String nodeId) {
        String sql = "  delete from GC_CONV_RATE_V   \n  where  nodeId =?  \n";
        this.execute(sql, new Object[]{nodeId});
    }

    @Override
    public List<ConversionRateEO> queryByPeriod(String id, String systemId) {
        String sql = this.getSqlColumns() + "  where  g.periodId =?  \n  and  g.systemId =?  \n" + this.getOrderySql();
        List mapList = this.selectMap(sql, new Object[]{id, systemId});
        if (mapList != null && mapList.size() > 0) {
            return this.convertMapList2Eos(mapList, false);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ConversionRateEO> queryByGroup(String groupId) {
        String sql = this.getSqlColumns() + "  where  t.rateGroupId =?  \n" + this.getOrderySql();
        List mapList = this.selectMap(sql, new Object[]{groupId});
        if (mapList != null && mapList.size() > 0) {
            return this.convertMapList2Eos(mapList, false);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ConversionRateEO> queryByNode(String nodeId) {
        String sql = this.getSqlColumns() + "  where  v.nodeId =?  \n" + this.getOrderySql();
        List mapList = this.selectMap(sql, new Object[]{nodeId});
        if (mapList != null && mapList.size() > 0) {
            return this.convertMapList2Eos(mapList, false);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ConversionRateEO> queryAll(String systemId) {
        String sql = this.getSqlColumns() + "  where  g.systemId =?  \n" + this.getOrderySql();
        List mapList = this.selectMap(sql, new Object[]{systemId});
        if (mapList != null && mapList.size() > 0) {
            return this.convertMapList2Eos(mapList, false);
        }
        return Collections.emptyList();
    }

    private String getSqlColumns() {
        return "select v.id as id, g.periodId as periodId,g.id as groupId,g.systemId as systemId,g.groupName as groupName,v.nodeId as nodeId,v.dataTime as  dataTime,t.sourceCurrencyCode as sourceCurrencyCode,t.targetCurrencyCode as targetCurrencyCode,v.rowDataId as rowDataId,v.rateTypeCode as rateTypeCode,v.rateValue as rateValue  \nfrom GC_CONV_RATE_V   v  \nleft join GC_CONV_RATE_T  t on v.nodeid=t.id  \nleft join GC_CONV_RATE_G   g on t.rategroupid=g.id  \n";
    }

    private String getOrderySql() {
        return " order by v.dataTime desc,t.sourceCurrencyCode,t.targetCurrencyCode,g.periodId,g.id,v.nodeId,v.createTime \n";
    }

    @Override
    public List<ConversionRateEO> getRateInfoList(String periodId, String dataTimeStart, String dataTimeEnd, String sourceCurrencyCode, String targetCurrencyCode, String systemId, String rateTypeCode) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> params = new ArrayList<String>();
        StringBuilder whereSql = new StringBuilder("  where  1 = 1 \n");
        whereSql.append(" and  g.systemId = ? \n");
        params.add(systemId);
        if (!StringUtils.isEmpty((String)rateTypeCode)) {
            whereSql.append(" and  v.ratetypecode = ? \n");
            params.add(rateTypeCode);
        }
        if (periodId != null && periodId.trim().length() > 0 && !periodId.trim().equals("all")) {
            whereSql.append(" and  g.periodId = ? \n");
            params.add(periodId);
        }
        if (dataTimeStart != null && dataTimeStart.trim().length() == 9) {
            whereSql.append(" and v.datatime>= ?   \n");
            params.add(dataTimeStart);
        }
        if (dataTimeEnd != null && dataTimeEnd.trim().length() == 9) {
            whereSql.append(" and v.dataTime<= ?  \n");
            params.add(dataTimeEnd);
        }
        if (sourceCurrencyCode != null && sourceCurrencyCode.trim().length() > 0 && !sourceCurrencyCode.trim().equals("all")) {
            whereSql.append(" and  t.sourceCurrencyCode = ? \n");
            params.add(sourceCurrencyCode);
        }
        if (targetCurrencyCode != null && targetCurrencyCode.trim().length() > 0 && !targetCurrencyCode.trim().equals("all")) {
            whereSql.append(" and  t.targetCurrencyCode = ? \n");
            params.add(targetCurrencyCode);
        }
        sql.append(this.getSqlColumns());
        sql.append((CharSequence)whereSql);
        sql.append(this.getOrderySql());
        List mapList = this.selectMap(sql.toString(), params.toArray());
        if (mapList != null && mapList.size() > 0) {
            return this.convertMapList2Eos(mapList, "all".equals(sourceCurrencyCode));
        }
        return Collections.emptyList();
    }

    @Override
    public List<ConversionRateEO> getSumAvgRateInfoList(String periodId, String dataTime, String sourceCurrencyCode, String targetCurrencyCode, String systemId, String rateTypeCode) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> params = new ArrayList<String>();
        StringBuilder whereSql = new StringBuilder("  where  1 = 1 \n");
        whereSql.append(" and  g.systemId = ? \n");
        params.add(systemId);
        whereSql.append(" and  v.ratetypecode = ? \n");
        params.add(rateTypeCode);
        if (periodId != null && periodId.trim().length() > 0 && !periodId.trim().equals("all")) {
            whereSql.append(" and  g.periodId = ? \n");
            params.add(periodId);
        }
        if (dataTime != null && dataTime.trim().length() > 0 && !dataTime.trim().equals("all")) {
            whereSql.append(" and  v.dataTime<= ? \n");
            params.add(dataTime);
            whereSql.append(" and  v.dataTime>= ? \n");
            params.add(dataTime.substring(0, 5) + "0000");
        }
        if (sourceCurrencyCode != null && sourceCurrencyCode.trim().length() > 0 && !sourceCurrencyCode.trim().equals("all")) {
            whereSql.append(" and  t.sourceCurrencyCode = ? \n");
            params.add(sourceCurrencyCode);
        }
        if (targetCurrencyCode != null && targetCurrencyCode.trim().length() > 0 && !targetCurrencyCode.trim().equals("all")) {
            whereSql.append(" and  t.targetCurrencyCode = ? \n");
            params.add(targetCurrencyCode);
        }
        sql.append(this.getSqlColumns());
        sql.append((CharSequence)whereSql);
        sql.append(this.getOrderySql());
        List mapList = this.selectMap(sql.toString(), params.toArray());
        if (mapList != null && mapList.size() > 0) {
            return this.convertMapList2Eos(mapList, "all".equals(sourceCurrencyCode));
        }
        return Collections.emptyList();
    }

    private List<ConversionRateEO> convertMapList2Eos(List<Map<String, Object>> eoMapList, boolean isFirstSortByCurrency) {
        List<ConversionRateEO> sortEos = eoMapList.stream().map(rowData -> {
            ConversionRateEO eo = new ConversionRateEO();
            eo.setId(ConverterUtils.getAsString(rowData.get("ID")));
            eo.setRecver(ConverterUtils.getAsLong(rowData.get("RECVER")));
            eo.setRowDataId(ConverterUtils.getAsString(rowData.get("ROWDATAID")));
            eo.setNodeId(ConverterUtils.getAsString(rowData.get("NODEID")));
            eo.setDataTime(ConverterUtils.getAsString(rowData.get("DATATIME")));
            eo.setRateTypeCode(ConverterUtils.getAsString(rowData.get("RATETYPECODE")));
            eo.setRateValue(ConverterUtils.getAsDouble(rowData.get("RATEVALUE")));
            eo.setCreator(ConverterUtils.getAsString(rowData.get("CREATOR")));
            eo.setGroupId(ConverterUtils.getAsString(rowData.get("GROUPID")));
            eo.setGroupName(ConverterUtils.getAsString(rowData.get("GROUPNAME")));
            eo.setPeriodId(ConverterUtils.getAsString(rowData.get("PERIODID")));
            eo.setSourceCurrencyCode(ConverterUtils.getAsString(rowData.get("SOURCECURRENCYCODE")));
            eo.setTargetCurrencyCode(ConverterUtils.getAsString(rowData.get("TARGETCURRENCYCODE")));
            return eo;
        }).sorted((o1, o2) -> {
            int periodIdAscValue;
            if (isFirstSortByCurrency) {
                int sourceCurrencyCodeAscValue = String.valueOf(o1.getSourceCurrencyCode()).compareTo(String.valueOf(o2.getSourceCurrencyCode()));
                if (sourceCurrencyCodeAscValue != 0) {
                    return sourceCurrencyCodeAscValue;
                }
                int targetCurrencyCodeAscValue = String.valueOf(o1.getTargetCurrencyCode()).compareTo(String.valueOf(o2.getTargetCurrencyCode()));
                if (targetCurrencyCodeAscValue != 0) {
                    return targetCurrencyCodeAscValue;
                }
            }
            if (o2.getDataTime() != null && o1.getDataTime() != null) {
                int dataTimeDesc = o1.getDataTime().substring(4, 5).compareTo(o2.getDataTime().substring(4, 5));
                if (dataTimeDesc != 0) {
                    return dataTimeDesc;
                }
                int dataTimeDescValue = o1.getDataTime().substring(5).compareTo(o2.getDataTime().substring(5));
                if (dataTimeDescValue != 0) {
                    return dataTimeDescValue;
                }
            }
            if ((periodIdAscValue = String.valueOf(o1.getPeriodId()).compareTo(String.valueOf(o2.getPeriodId()))) != 0) {
                return periodIdAscValue;
            }
            int groupIdAscValue = String.valueOf(o1.getGroupId()).compareTo(String.valueOf(o2.getGroupId()));
            if (groupIdAscValue != 0) {
                return groupIdAscValue;
            }
            int nodeIdAscValue = String.valueOf(o1.getNodeId()).compareTo(String.valueOf(o2.getNodeId()));
            if (nodeIdAscValue != 0) {
                return nodeIdAscValue;
            }
            return 0;
        }).collect(Collectors.toList());
        return sortEos;
    }
}

