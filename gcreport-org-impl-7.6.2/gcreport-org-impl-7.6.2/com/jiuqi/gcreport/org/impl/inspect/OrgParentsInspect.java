/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.service.impl.OrgDataServiceImpl
 *  com.jiuqi.va.organization.service.impl.help.OrgDataQueryService
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.impl.OrgDataServiceImpl;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.util.StringUtils;

@Component
public class OrgParentsInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrgDataServiceImpl orgDataService;
    @Autowired
    private OrgDataQueryService orgDataQueryService;

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-parents";
    }

    public String getTitle() {
        return "\u7ec4\u7ec7\u673a\u6784\u7236\u7ea7\u4ee3\u7801\u68c0\u67e5";
    }

    @Transactional(rollbackFor={Exception.class})
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
        ArrayList<Map> cols = new ArrayList<Map>();
        cols.add(new JSONObject().put("title", (Object)"\u4ee3\u7801").put("key", (Object)"code").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6807\u9898").put("key", (Object)"name").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u7236\u7ea7\u4ee3\u7801").put("key", (Object)"parentcode").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u539f\u59cb\u8def\u5f84").put("key", (Object)"parents").toMap());
        String sql = "select m.id,m.code,m.name,m.parentcode,m.parents,m.validtime,m.invalidtime from " + orgType + "  m where m.parentcode is null or m.parentcode = '' or m.parents is null or m.parents = ''";
        List rows = this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            HashMap<String, String> org = new HashMap<String, String>();
            org.put("id", rs.getString(1));
            org.put("code", rs.getString(2));
            org.put("name", rs.getString(3));
            org.put("parentcode", rs.getString(4));
            org.put("parents", rs.getString(5));
            org.put("validtime", DateUtils.format((Date)rs.getDate(6)));
            org.put("invalidtime", DateUtils.format((Date)rs.getDate(7)));
            org.put("info", "\u7236\u7ea7\u4ee3\u7801\u6216\u8005\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a");
            return org;
        });
        if (!CollectionUtils.isEmpty((Collection)rows)) {
            cols.add(new JSONObject().put("title", (Object)"\u751f\u6548\u65f6\u95f4").put("key", (Object)"validtime").toMap());
            cols.add(new JSONObject().put("title", (Object)"\u5931\u6548\u65f6\u95f4").put("key", (Object)"invalidtime").toMap());
            cols.add(new JSONObject().put("title", (Object)"\u8be6\u60c5").put("key", (Object)"info").toMap());
            HashMap<String, List> result = new HashMap<String, List>();
            result.put("rows", rows);
            result.put("cols", cols);
            vo.setResult(result);
            vo.setTaskState(CollectionUtils.isEmpty((Collection)rows) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
            return vo;
        }
        cols.add(new JSONObject().put("title", (Object)"\u6b63\u786e\u7684\u7236\u7ea7\u4ee3\u7801").put("key", (Object)"correctParents").toMap());
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByCode(orgType, orgVersionName);
        OrgDTO allOrgParam = new OrgDTO();
        allOrgParam.setCategoryname(orgType);
        allOrgParam.setVersionDate(version.getValidTime());
        allOrgParam.setStopflag(Integer.valueOf(-1));
        allOrgParam.setAuthType(OrgDataOption.AuthType.NONE);
        PageVO pageVO = this.orgDataQueryService.list(allOrgParam);
        HashMap<String, String> codeMap = new HashMap<String, String>();
        ArrayList<OrgDO> list = new ArrayList<OrgDO>();
        codeMap.put("-", "");
        for (Object orgDO2 : pageVO.getRows()) {
            codeMap.put(orgDO2.getCode(), orgDO2.getParents());
            list.add(new OrgDO((Map)orgDO2));
        }
        HashMap<String, List<OrgDO>> fatherSonMap = new HashMap<String, List<OrgDO>>();
        for (OrgDO orgDO2 : list) {
            ArrayList<OrgDO> children;
            String parentcode = orgDO2.getParentcode();
            if (StringUtils.isEmpty(parentcode) || !codeMap.containsKey(parentcode)) {
                parentcode = "-";
            }
            if ((children = (ArrayList<OrgDO>)fatherSonMap.get(parentcode)) == null) {
                children = new ArrayList<OrgDO>();
            }
            children.add(orgDO2);
            fatherSonMap.put(parentcode, children);
        }
        List topNodes = (List)fatherSonMap.get("-");
        Assert.isNotEmpty((Collection)topNodes, (String)"\u65e0\u6839\u8282\u70b9\u6570\u636e", (Object[])new Object[0]);
        for (OrgDO orgDO4 : topNodes) {
            orgDO4.setParentcode("-");
            orgDO4.setParents(orgDO4.getCode());
            this.resetparents(orgDO4, fatherSonMap);
        }
        List list2 = list.stream().filter(orgDO -> {
            String new_parents;
            String old = (String)codeMap.get(orgDO.getCode());
            boolean equal = old.equalsIgnoreCase(new_parents = orgDO.getParents());
            return !equal;
        }).map(orgDO -> {
            orgDO.put("correctParents", (Object)orgDO.getParents());
            orgDO.put("parents", codeMap.get(orgDO.getCode()));
            return orgDO;
        }).collect(Collectors.toList());
        HashMap<String, List<Object>> result = new HashMap<String, List<Object>>();
        result.put("rows", list2);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty(list2) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }

    public InspectResultVO executeFix(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgType = (String)params.get("orgType");
        String orgVersionName = (String)params.get("orgVersion");
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)orgType)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)orgVersionName)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        }
        OrgVersionVO version = GcOrgVerTool.getInstance().getOrgVersionByCode(orgType, orgVersionName);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(orgType);
        orgDTO.setVersionDate(version.getValidTime());
        R r = this.orgDataService.resetParents(orgDTO);
        if (r.getCode() == R.ok().getCode()) {
            vo.setTaskState(TaskStateEnum.SUCCESS);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u68c0\u67e5", (String)("\u4fee\u590d-parents-\u673a\u6784\u7c7b\u578b" + orgType), (String)"");
        } else {
            vo.setTaskState(TaskStateEnum.FAILED);
            vo.setMessage(r.getMsg());
            LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u68c0\u67e5", (String)("\u4fee\u590d-parents-\u673a\u6784\u7c7b\u578b" + orgType + "\u5f02\u5e38"), (String)r.getMsg());
        }
        InspectOrgUtils.clearOrgCache(orgType);
        return vo;
    }

    private void resetparents(OrgDO father, Map<String, List<OrgDO>> fatherSonMap) {
        if (father == null || fatherSonMap == null || fatherSonMap.size() == 0) {
            return;
        }
        List<OrgDO> children = fatherSonMap.get(father.getCode());
        if (children == null || children.size() == 0) {
            return;
        }
        for (OrgDO child : children) {
            String grandParents = father.getParents();
            grandParents = !StringUtils.isEmpty(grandParents) ? grandParents + "/" : "";
            child.setParents(grandParents + child.getCode());
            this.resetparents(child, fatherSonMap);
        }
    }
}

