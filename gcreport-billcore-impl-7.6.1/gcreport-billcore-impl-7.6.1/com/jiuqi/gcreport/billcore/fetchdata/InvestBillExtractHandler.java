/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleDTO
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleParam
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData
 *  com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandler
 *  com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.service.OrgDataService
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.gcreport.billcore.fetchdata;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleCtx;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleDTO;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractHandleParam;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveContext;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.BillExtractSaveData;
import com.jiuqi.gcreport.bde.bill.extract.client.intf.IBillExtractHandler;
import com.jiuqi.gcreport.billcore.fetchdata.GcBillFetchDataMainProcessor;
import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class InvestBillExtractHandler
implements IBillExtractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvestBillExtractHandler.class);
    public static final String FN_EXTRACT_BBLX = "0";
    public static final String FD_BBLX = "BBLX";
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private InvestBillExtractDao billExtractDao;

    public String getModelCode() {
        return "InvestBillModel";
    }

    public BillExtractHandleParam parse(BillExtractHandleDTO dto) {
        BillExtractHandleParam handleParam = new BillExtractHandleParam();
        Map extInfo = dto.getExtInfo();
        int year = ConverterUtils.getAsIntValue(extInfo.get("year"));
        int period = ConverterUtils.getAsIntValue(extInfo.get("period"));
        Date date = DateUtils.lastDateOf((int)year, (int)period);
        dto.setEndDateStr(DateUtils.format((Date)date, (String)DateUtils.DEFAULT_DATE_FORMAT));
        handleParam.setStartDateStr(dto.getEndDateStr());
        handleParam.setEndDateStr(dto.getEndDateStr());
        OrgDTO orgCondi = new OrgDTO();
        orgCondi.setCategoryname(dto.getRpUnitType());
        orgCondi.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgCondi.setVersionDate(DateUtils.parse((String)handleParam.getEndDateStr()));
        Map<String, OrgDO> queryOrgMap = this.orgDataService.list(orgCondi).getRows().stream().filter(item -> FN_EXTRACT_BBLX.equals(item.getValueOf(FD_BBLX))).collect(Collectors.toMap(OrgDO::getCode, item -> item, (k1, k2) -> k2));
        if (Boolean.TRUE.equals(dto.getExtractAllUnit())) {
            handleParam.setUnitCodes(queryOrgMap.keySet().stream().collect(Collectors.toList()));
        } else {
            ArrayList<String> unitCodeList = new ArrayList<String>(dto.getUnitCodes().size());
            for (String unitCode : dto.getUnitCodes()) {
                if (queryOrgMap.get(unitCode) == null) {
                    LOGGER.warn("\u5355\u636e\u53d6\u6570\u6267\u884c\u4e2d\u5355\u4f4d\u7c7b\u578b{}\u65f6\u95f4{}\u5355\u4f4d\u4ee3\u7801{}\u4e0d\u662f\u5355\u6237\u5355\u4f4d\u6216\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879\uff0c\u81ea\u52a8\u8df3\u8fc7", dto.getRpUnitType(), dto.getEndDateStr(), unitCode);
                }
                unitCodeList.add(unitCode);
            }
            handleParam.setUnitCodes(unitCodeList);
        }
        return handleParam;
    }

    public void doCheck(BillExtractHandleCtx dto) {
    }

    public List<Map<String, Object>> listBills(BillExtractHandleCtx ctx) {
        return this.billExtractDao.selectBills(ctx.getMasterTableName(), new BillExtractLisDTO(ctx.getUnitCode(), ctx.getStartDateStr(), ctx.getEndDateStr()));
    }

    public void doSave(BillExtractSaveContext saveCtx, BillExtractSaveData saveData) {
        new GcBillFetchDataMainProcessor(saveCtx, saveData).saveData();
    }

    @Repository
    public class InvestBillExtractDao {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        public List<Map<String, Object>> selectBills(String masterTableName, BillExtractLisDTO queryCondi) {
            String SQL = "SELECT * FROM %1$s WHERE 1=1 AND UNITCODE = ? AND ACCTYEAR = ? AND PERIOD = ? %2$s";
            String billCodeSql = "";
            if (!CollectionUtils.isEmpty((Collection)queryCondi.getBillCodeList())) {
                billCodeSql = "AND " + SqlBuildUtil.getStrInCondi((String)"BILLCDE", (List)queryCondi.getBillCodeList());
            }
            String querySql = String.format("SELECT * FROM %1$s WHERE 1=1 AND UNITCODE = ? AND ACCTYEAR = ? AND PERIOD = ? %2$s", masterTableName, billCodeSql);
            return (List)this.jdbcTemplate.query(querySql, (ResultSetExtractor)new ResultSetExtractor<List<Map<String, Object>>>(){

                public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    ArrayList result = CollectionUtils.newArrayList();
                    HashMap<String, Object> rowData = null;
                    ArrayList<String> columns = new ArrayList<String>(rs.getMetaData().getColumnCount());
                    for (int colIdx = 1; colIdx <= rs.getMetaData().getColumnCount(); ++colIdx) {
                        columns.add(rs.getMetaData().getColumnLabel(colIdx).toUpperCase());
                    }
                    while (rs.next()) {
                        rowData = new HashMap<String, Object>(columns.size());
                        String column = "";
                        for (int idx = 1; idx <= columns.size(); ++idx) {
                            column = (String)columns.get(idx - 1);
                            if (rs.getObject(idx) == null) continue;
                            if (rs.getObject(idx) instanceof BigDecimal) {
                                rowData.put(column, new BigDecimal(rs.getObject(idx).toString()));
                                continue;
                            }
                            if (rs.getObject(idx) instanceof Integer) {
                                rowData.put(column, Integer.valueOf(rs.getObject(idx).toString()));
                                continue;
                            }
                            rowData.put(column, rs.getObject(idx).toString());
                        }
                        result.add(rowData);
                    }
                    return result;
                }
            }, new Object[]{queryCondi.getUnitCode(), DateUtils.getDateFieldValue((Date)DateUtils.parse((String)queryCondi.getEndDate()), (int)1), DateUtils.getDateFieldValue((Date)DateUtils.parse((String)queryCondi.getEndDate()), (int)2)});
        }
    }
}

