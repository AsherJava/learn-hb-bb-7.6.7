/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountRollBackTableUtil {
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AccountRollBackTableUtil.class);
    public static final String TN_ACCOUNT_RPT_SUFFIX = "_RPT";
    public static final String TN_ACCOUNT_HIS_SUFFIX = "_HIS";
    private static final String MD_ORG_START = "MD_ORG";
    private static final String TMP_ACCOUNT_TABLE = "TMP_ACCOUNT_TABLE";
    private static final String DW_ID = "ID";
    private static final String EXE_ID = "EXE_ID";

    public String getAccHiTableName(String acccTable) {
        return acccTable.concat(TN_ACCOUNT_HIS_SUFFIX);
    }

    public String getAccRptTableName(String acccTable) {
        return acccTable.concat(TN_ACCOUNT_RPT_SUFFIX);
    }

    public String getAccTableName(String formKey) {
        String accTableName = null;
        List region = this.controller.getAllRegionsInForm(formKey);
        if ((region = region.stream().filter(e -> !DataRegionKind.DATA_REGION_SIMPLE.equals((Object)e.getRegionKind())).collect(Collectors.toList())).isEmpty()) {
            return accTableName;
        }
        List fieldKeys = this.controller.getFieldKeysInRegion(((DataRegionDefine)region.get(0)).getKey());
        String[] fieldKeyAry = fieldKeys.toArray(new String[fieldKeys.size()]);
        List dataFieldDeployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(fieldKeyAry);
        Set<String> tableNames = dataFieldDeployInfos.stream().map(e -> e.getTableName()).collect(Collectors.toSet());
        Optional<String> accTable = this.findAcctTableName(tableNames);
        if (accTable.isPresent()) {
            accTableName = accTable.get();
        }
        return accTableName;
    }

    public Optional<String> findAcctTableName(Set<String> tableNames) {
        Optional<String> tableName = tableNames.stream().filter(e -> !e.endsWith(TN_ACCOUNT_RPT_SUFFIX) && !e.endsWith(TN_ACCOUNT_HIS_SUFFIX)).findFirst();
        return tableName;
    }

    public DataTable getAcctDataTable(String tableName) {
        return this.runtimeDataSchemeService.getDataTableByCode(tableName);
    }

    public List<DataField> getAllDataFields(String tableKey) {
        return this.runtimeDataSchemeService.getDataFieldByTable(tableKey);
    }

    public static void close(Statement prep) {
        try {
            if (prep != null) {
                prep.close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String parseDimField(String dimension) {
        if (StringUtils.isEmpty((String)dimension)) {
            return null;
        }
        if (dimension.startsWith(MD_ORG_START)) {
            return "MDCODE";
        }
        if (dimension.equals("DATATIME")) {
            return "DATATIME";
        }
        if (dimension.equals("RECORDKEY")) {
            return "SBID";
        }
        return dimension;
    }

    public static String periodToString(PeriodWrapper pWrapper) {
        StringBuffer sb = new StringBuffer();
        int offset = pWrapper.getPeriod();
        switch (pWrapper.getType()) {
            case 1: {
                sb.append(pWrapper.getYear()).append("\u5e74");
                break;
            }
            case 2: {
                sb.append(pWrapper.getYear()).append("\u5e74").append(pWrapper.getPeriod() == 1 ? "\u4e0a\u534a\u5e74" : "\u4e0b\u534a\u5e74");
                break;
            }
            case 3: {
                sb.append(pWrapper.getYear()).append("\u5e74");
                if (offset == 1) {
                    sb.append("\u7b2c\u4e00\u5b63\u5ea6");
                    break;
                }
                if (offset == 2) {
                    sb.append("\u7b2c\u4e8c\u5b63\u5ea6");
                    break;
                }
                if (offset == 3) {
                    sb.append("\u7b2c\u4e09\u5b63\u5ea6");
                    break;
                }
                sb.append("\u7b2c\u56db\u5b63\u5ea6");
                break;
            }
            case 4: {
                sb.append(pWrapper.getYear()).append("\u5e74").append(offset).append("\u6708");
                break;
            }
            case 5: {
                sb.append(pWrapper.getYear()).append("\u5e74");
                double m = Math.floor(offset / 3) + 1.0;
                int x = offset / 3;
                if (x == 1) {
                    sb.append(m).append("\u6708\u4e0a\u65ec");
                    break;
                }
                if (x == 2) {
                    sb.append(m).append("\u6708\u4e2d\u65ec");
                    break;
                }
                sb.append(m).append("\u6708\u4e0b\u65ec");
                break;
            }
            case 6: {
                sb.append(pWrapper.getYear()).append("\u5e74").append(offset).append("\u65e5");
                break;
            }
            default: {
                sb.append(pWrapper.toString());
            }
        }
        return sb.toString();
    }
}

