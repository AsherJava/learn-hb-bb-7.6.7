/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.org.OrgDO
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.formulaschemeconfig.init.service;

import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.impl.utils.BillExtractUtil;
import com.jiuqi.gcreport.formulaschemeconfig.init.service.FormulaSchemeConfigBillFetchInitService;
import com.jiuqi.gcreport.formulaschemeconfig.utils.FormulaSchemeConfigUtils;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.org.OrgDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormulaSchemeConfigBillBblxInitService {
    private Logger logger = LoggerFactory.getLogger(FormulaSchemeConfigBillFetchInitService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BillExtractSettingClient settingClient;

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void doInit() {
        this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u5355\u636e\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u5f00\u59cb\u6267\u884c");
        String sql = "SELECT BILLID , ORGID FROM GC_FORMULASCHEMECONFIG WHERE CATEGORY  = 'billFetch' AND BBLX IS NULL OR BBLX = '' ";
        Map billOrgMap = (Map)this.jdbcTemplate.query(sql, (ResultSetExtractor)new ResultSetExtractor<Map<String, List<String>>>(){

            public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                HashMap<String, List<String>> resultMap = new HashMap<String, List<String>>();
                while (rs.next()) {
                    String billId = rs.getString(1);
                    String orgId = rs.getString(2);
                    if (!resultMap.containsKey(billId)) {
                        resultMap.put(billId, new ArrayList());
                    }
                    ((List)resultMap.get(billId)).add(orgId);
                }
                return resultMap;
            }
        }, new Object[0]);
        if (billOrgMap == null || billOrgMap.isEmpty()) {
            this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u5355\u636e\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u6570\u636e\u5904\u7406-\u65e0\u9700\u6267\u884c");
            return;
        }
        for (Map.Entry entry : billOrgMap.entrySet()) {
            try {
                String billId = (String)entry.getKey();
                List orgIds = (List)entry.getValue();
                String masterTableName = (String)FormulaSchemeConfigUtils.parseResponse(this.settingClient.getMasterTableName(billId));
                DataModelColumn column = (DataModelColumn)FormulaSchemeConfigUtils.parseResponse(this.settingClient.getDataModelColumn(masterTableName, "UNITCODE"));
                String orgType = BillExtractUtil.queryOrgTypeByColumn((DataModelColumn)column);
                for (String orgId : orgIds) {
                    List<OrgDO> orgDOList = FormulaSchemeConfigUtils.queryOrgDO(orgType, orgId);
                    if (orgDOList == null || orgDOList.isEmpty()) continue;
                    OrgDO orgDO = orgDOList.get(0);
                    Boolean isLeaf = (Boolean)orgDO.get((Object)"isLeaf");
                    String bblx = isLeaf != false ? "unitSetting" : "strategySetting";
                    String updateBblxSql = " UPDATE GC_FORMULASCHEMECONFIG SET BBLX = ? WHERE CATEGORY  = 'billFetch' AND BILLID = ? AND ORGID = ? ";
                    this.jdbcTemplate.update(updateBblxSql, new Object[]{bblx, billId, orgId});
                }
                this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u5355\u636e{}\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u6570\u636e\u5904\u7406\u6267\u884c\u5b8c\u6210,\u66f4\u65b0{}\u6761\u6570\u636e", (Object)billId, (Object)orgIds.size());
            }
            catch (Exception e) {
                this.logger.info("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u914d\u7f6e\u6a21\u5757-\u6570\u636e\u5347\u7ea7-\u5355\u636e{}\u62a5\u8868\u7c7b\u578b\u5b57\u6bb5\u4fee\u590d\u51fa\u73b0\u9519\u8bef", entry.getKey(), (Object)e);
            }
        }
    }
}

