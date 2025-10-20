/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gc.inspector.common.TaskStateEnum
 *  com.jiuqi.gc.inspector.domain.InspectResultVO
 *  com.jiuqi.gc.inspector.intf.InspectBaseItem
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.organization.service.impl.OrgDataServiceImpl
 *  com.jiuqi.va.organization.service.impl.help.OrgDataQueryService
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.inspect;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gc.inspector.common.TaskStateEnum;
import com.jiuqi.gc.inspector.domain.InspectResultVO;
import com.jiuqi.gc.inspector.intf.InspectBaseItem;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgQueryDao;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.organization.service.impl.OrgDataServiceImpl;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class OrgGcParentsInspect
implements InspectBaseItem {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrgDataServiceImpl orgDataService;
    @Autowired
    private OrgDataQueryService orgDataQueryService;
    @Autowired
    private FGcOrgQueryDao queryDao;

    public String getGroup() {
        return "merge-unit";
    }

    public String getName() {
        return "merge-gcparents";
    }

    public String getTitle() {
        return "\u7ec4\u7ec7\u673a\u6784\u7236\u7ea7\u4ee3\u7801(gc)\u68c0\u67e5";
    }

    @Transactional(rollbackFor={Exception.class})
    public InspectResultVO executeInspect(Map<String, Object> params) {
        InspectResultVO vo = new InspectResultVO();
        String orgType = (String)params.get("orgType");
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)orgType)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        OrgTypeVO typeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType);
        if (typeVO == null) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u5b58\u5728");
        }
        if (!orgType.toUpperCase().startsWith("MD_ORG")) {
            return InspectResultVO.exceptionResult((String)("\u975e\u6cd5\u7684\u53c2\u6570\uff1a" + orgType));
        }
        ArrayList<Map> cols = new ArrayList<Map>();
        cols.add(new JSONObject().put("title", (Object)"\u4ee3\u7801").put("key", (Object)"CODE").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6807\u9898").put("key", (Object)"NAME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u751f\u6548\u65f6\u95f4").put("key", (Object)"VALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5931\u6548\u65f6\u95f4").put("key", (Object)"INVALIDTIME").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u539f\u59cb\u8def\u5f84(parents)").put("key", (Object)"PARENTS").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u5408\u5e76\u8def\u5f84(gcparents)").put("key", (Object)"GCPARENTS").toMap());
        cols.add(new JSONObject().put("title", (Object)"\u6b63\u786e\u5408\u5e76\u8def\u5f84").put("key", (Object)"CORRECTGCPARENTS").toMap());
        String sql = "select m.id,m.code,m.name,m.parentcode,m.parents,m.gcparents,m.validtime,m.invalidtime from " + orgType + " m";
        List rows = this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            HashMap<String, String> org = new HashMap<String, String>();
            org.put("ID", rs.getString(1));
            org.put("CODE", rs.getString(2));
            org.put("NAME", rs.getString(3));
            org.put("PARENTCODE", rs.getString(4));
            org.put("PARENTS", rs.getString(5));
            org.put("GCPARENTS", rs.getString(6));
            org.put("VALIDTIME", DateUtils.format((Date)rs.getDate(7)));
            org.put("INVALIDTIME", DateUtils.format((Date)rs.getDate(8)));
            return org;
        });
        GcOrgCodeConfig gcOrgCodeConfig = ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
        ArrayList resultList = new ArrayList();
        rows.forEach(objectMap -> {
            String parents = objectMap.get("PARENTS") == null ? "-" : String.valueOf(objectMap.get("PARENTS"));
            String oldGcParents = objectMap.get("GCPARENTS") == null ? "" : String.valueOf(objectMap.get("GCPARENTS"));
            String gcParents = InspectOrgUtils.getGcParentsByOldParents(parents, gcOrgCodeConfig);
            if (!gcParents.equalsIgnoreCase(oldGcParents)) {
                objectMap.put("CORRECTGCPARENTS", gcParents);
                resultList.add(objectMap);
            }
        });
        HashMap result = new HashMap();
        result.put("rows", resultList);
        result.put("cols", cols);
        vo.setResult(result);
        vo.setTaskState(CollectionUtils.isEmpty(resultList) ? TaskStateEnum.SUCCESS : TaskStateEnum.FAILED);
        return vo;
    }

    public InspectResultVO executeFix(Map<String, Object> params) {
        String orgType = (String)params.get("orgType");
        if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)orgType)) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        OrgTypeVO typeVO = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType);
        if (typeVO == null) {
            return InspectResultVO.exceptionResult((String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u5b58\u5728");
        }
        if (!orgType.toUpperCase().startsWith("MD_ORG")) {
            return InspectResultVO.exceptionResult((String)("\u975e\u6cd5\u7684\u53c2\u6570\uff1a" + orgType));
        }
        GcOrgCodeConfig gcOrgCodeConfig = ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
        List mapList = this.jdbcTemplate.queryForList("select id, parents, gcparents from " + orgType);
        ArrayList updateList = new ArrayList();
        mapList.forEach(org -> {
            String newGcParents;
            String oldGcParents;
            String id = (String)org.get("id");
            String parents = org.get("PARENTS") == null ? "-" : String.valueOf(org.get("PARENTS"));
            String string = oldGcParents = org.get("GCPARENTS") == null ? "" : String.valueOf(org.get("GCPARENTS"));
            if (StringUtils.hasText(parents) && !(newGcParents = InspectOrgUtils.getGcParentsByOldParents(parents, gcOrgCodeConfig)).equalsIgnoreCase(oldGcParents)) {
                Object[] args = new Object[]{newGcParents, id};
                updateList.add(args);
            }
        });
        this.jdbcTemplate.batchUpdate("update " + orgType + " set gcparents = ? where id = ?", updateList);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u68c0\u67e5", (String)("\u4fee\u590d-GCPARENTS\u6570\u636e-\u673a\u6784\u7c7b\u578b" + orgType), (String)"");
        InspectOrgUtils.clearOrgCache(orgType);
        return InspectResultVO.successResult();
    }
}

