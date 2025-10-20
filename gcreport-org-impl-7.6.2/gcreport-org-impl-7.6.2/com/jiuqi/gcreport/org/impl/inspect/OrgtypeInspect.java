/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateCommonFormatEnum
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateCommonFormatEnum;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgMangerCenterTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrgtypeInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private GcOrgBaseTool getBaseOrgTool() {
        return GcOrgBaseTool.getInstance();
    }

    private GcOrgVerTool getOrgVerTool() {
        return GcOrgVerTool.getInstance();
    }

    private GcOrgMangerCenterTool getCenterTool(String orgType, String org_ver) {
        return GcOrgMangerCenterTool.getInstance(orgType, org_ver);
    }

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-orgtype";
    }

    public String getTitle() {
        return "\u5355\u4f4d\u7c7b\u578b";
    }

    public InspectResultVO executeInspect(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgType = (String)params.get("orgType");
        String orgVersionName = (String)params.get("orgVersion");
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)orgType)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)orgVersionName)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        }
        OrgTypeVO typeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType);
        if (typeVO == null) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u5b58\u5728");
        }
        if (!orgType.toUpperCase().startsWith("MD_ORG")) {
            return InspectResultVO.exceptionResult((String)("\u975e\u6cd5\u7684\u53c2\u6570\uff1a" + orgType));
        }
        if (!this.checkEnableAutoCalc(params)) {
            return InspectResultVO.unSupportResult((String)"\u8be5\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u81ea\u52a8\u8ba1\u7b97");
        }
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByCode(orgType, orgVersionName);
        String formatSQLDate = InspectOrgUtils.getDateFormat(version.getValidTime());
        ArrayList<Map> cols = new ArrayList<Map>();
        List<Object> rows = new ArrayList();
        cols.add(new JSONObject().put("title", (Object)"\u4ee3\u7801").put("key", (Object)"CODE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6807\u9898").put("key", (Object)"NAME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5355\u4f4d\u7c7b\u578b").put("key", (Object)"ORGTYPEID").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u751f\u6548\u65f6\u95f4").put("key", (Object)"VALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5931\u6548\u65f6\u95f4").put("key", (Object)"INVALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u8be6\u60c5").put("key", (Object)"INFO").toMap());
        String hbUnit = "SELECT T.CODE FROM " + orgType + " T WHERE exists (SELECT 1 FROM " + orgType + " O WHERE O.PARENTCODE = T.CODE and " + InspectOrgUtils.getVersionSql("O", formatSQLDate) + ")";
        String diffUnit = "SELECT D.DIFFUNITID FROM " + orgType + " D WHERE " + InspectOrgUtils.getVersionSql("D", formatSQLDate) + " and D.DIFFUNITID is not null and D.DIFFUNITID <> '' ";
        String splitUnit = "SELECT S.SPLITID FROM " + orgType + " S WHERE " + InspectOrgUtils.getVersionSql("S", formatSQLDate) + " and S.SPLITID is not null and S.SPLITID <> '' ";
        StringBuffer sql = new StringBuffer();
        sql.append(" select \r\n").append(" A.ID as ID, \r\n").append(" A.CODE as CODE, \r\n").append(" A.NAME as NAME, \r\n").append(" A.ORGTYPEID as ORGTYPEID, \r\n").append(" A.VALIDTIME as VALIDTIME, \r\n").append(" A.INVALIDTIME as INVALIDTIME \r\n").append(" from ").append(orgType).append(" A \r\n").append(" where 1=1 \r\n");
        StringBuffer conditions = new StringBuffer();
        if (orgType.equalsIgnoreCase("MD_ORG_CORPORATE")) {
            conditions.append(sql);
            conditions.append(" and a.orgtypeid <> 'MD_ORG_CORPORATE' and ");
            conditions.append(InspectOrgUtils.getVersionSql("A", formatSQLDate));
            rows = this.jdbcTemplate.queryForList(conditions.toString()).stream().map(map -> {
                map.put("INFO", "\u5355\u4f4d\u7c7b\u578b\u5e94\u4e3aMD_ORG_CORPORATE");
                map.put("rightValue", "MD_ORG_CORPORATE");
                return map;
            }).collect(Collectors.toList());
        } else {
            StringBuilder newConditions = new StringBuilder();
            StringBuilder newConditions2 = new StringBuilder();
            StringBuilder newConditions3 = new StringBuilder();
            conditions.append(sql);
            newConditions.append(conditions);
            newConditions2.append(conditions);
            newConditions3.append(conditions);
            conditions.append(" and a.orgtypeid <> 'MD_ORG_CORPORATE' ").append(" and a.code in ").append("(").append(hbUnit).append(") and ").append(InspectOrgUtils.getVersionSql("A", formatSQLDate));
            rows = this.jdbcTemplate.queryForList(conditions.toString()).stream().map(map -> {
                map.put("INFO", "\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u5e94\u4e3aMD_ORG_CORPORATE");
                map.put("rightValue", "MD_ORG_CORPORATE");
                return map;
            }).collect(Collectors.toList());
            newConditions.append(" and A.orgtypeid <> 'MD_ORG_CORPORATE' ").append(" and A.code in ( " + diffUnit + " ) and ").append(InspectOrgUtils.getVersionSql("A", formatSQLDate));
            rows.addAll(this.jdbcTemplate.queryForList(newConditions.toString()).stream().map(map -> {
                map.put("INFO", "\u5dee\u989d\u5355\u4f4d\u7c7b\u578b\u5e94\u4e3aMD_ORG_CORPORATE");
                map.put("rightValue", "MD_ORG_CORPORATE");
                return map;
            }).collect(Collectors.toList()));
            newConditions2.append(" and a.orgtypeid <> 'MD_ORG_CORPORATE' ").append(" and A.code in ( " + splitUnit + " ) and ").append(InspectOrgUtils.getVersionSql("A", formatSQLDate));
            rows.addAll(this.jdbcTemplate.queryForList(newConditions2.toString()).stream().map(map -> {
                map.put("INFO", "\u62c6\u5206\u5355\u4f4d\u7c7b\u578b\u5e94\u4e3aMD_ORG_CORPORATE");
                map.put("rightValue", "MD_ORG_CORPORATE");
                return map;
            }).collect(Collectors.toList()));
            newConditions3.append(" and a.orgtypeid <> '" + orgType + "' ").append(" and A.code not in ( " + hbUnit + " ").append(" union " + splitUnit + " ").append(" union " + diffUnit + " ) and ").append(InspectOrgUtils.getVersionSql("A", formatSQLDate));
            rows.addAll(this.jdbcTemplate.queryForList(newConditions3.toString()).stream().map(map -> {
                map.put("INFO", "\u672b\u7ea7\u5355\u4f4d\u7c7b\u578b\u5e94\u4e3a" + orgType);
                map.put("rightValue", orgType);
                return map;
            }).collect(Collectors.toList()));
        }
        HashMap result = new HashMap();
        result.put("rows", rows);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty(rows) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }

    @Transactional(rollbackFor={Exception.class})
    public InspectResultVO executeFix(Map<String, Object> params) {
        if (!this.checkEnableAutoCalc(params)) {
            return InspectResultVO.unSupportResult((String)"\u8be5\u5355\u4f4d\u7c7b\u578b\u4e0d\u5141\u8bb8\u81ea\u52a8\u8ba1\u7b97");
        }
        InspectResultVO vo = new InspectResultVO();
        InspectResultVO checkResultVO = this.executeInspect(params);
        String orgType = (String)params.get("orgType");
        String orgVersionName = (String)params.get("orgVersion");
        OrgTypeVO typeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType);
        if (typeVO == null) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u5b58\u5728");
        }
        if (!orgType.toUpperCase().startsWith("MD_ORG")) {
            return InspectResultVO.exceptionResult((String)("\u975e\u6cd5\u7684\u53c2\u6570\uff1a" + orgType));
        }
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByCode(orgType, orgVersionName);
        List rows = (List)checkResultVO.getResult().get("rows");
        GcOrgMangerCenterTool centerTool = this.getCenterTool(version.getOrgType().getName(), DateUtils.format((Date)version.getValidTime(), (DateCommonFormatEnum)DateCommonFormatEnum.FULL_DIGIT_BY_NONE));
        int totalCount = 0;
        for (Map row : rows) {
            boolean success = this.updateOrgOrgType(centerTool, row);
            totalCount += success ? 1 : 0;
        }
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("totalCount", totalCount);
        vo.setTaskState(TaskStateEnum.SUCCESS);
        vo.setResult(result);
        return vo;
    }

    private boolean checkEnableAutoCalc(Map<String, Object> params) {
        String orgType = (String)params.get("orgType");
        INvwaSystemOptionService systemOptionsService = (INvwaSystemOptionService)SpringContextUtils.getBean(INvwaSystemOptionService.class);
        String option = systemOptionsService.get("gc_option_org_fieldCalc", "NOT_AUTO_CALC_FIELD");
        if (!(orgType.equalsIgnoreCase("MD_ORG_MANAGEMENT") || orgType.equalsIgnoreCase("MD_ORG_CORPORATE") || StringUtils.isEmpty((String)option))) {
            ArrayList types = CollectionUtils.newArrayList((Object[])option.split(";"));
            return types.contains(orgType);
        }
        return false;
    }

    private boolean updateOrgOrgType(GcOrgMangerCenterTool centerTool, Map<String, Object> row) {
        boolean flag = true;
        OrgToJsonVO byCode = centerTool.getOrgByCode((String)row.get("code"));
        try {
            String orgType = (String)row.get("rightValue");
            byCode.setFieldValue("ORGTYPEID", (Object)orgType);
            centerTool.updateWithAnyPreProcess(byCode);
        }
        catch (Exception e) {
            BusinessLogUtils.operate((String)"\u5408\u5e76\u68c0\u67e5", (String)"\u5355\u4f4d\u7c7b\u578b\u4fee\u590d", (String)(byCode + ": \u5355\u4f4d\u7c7b\u578b\u4fee\u590d\u5931\u8d25"));
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}

