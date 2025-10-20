/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.context.AnalysisContext
 *  com.alibaba.excel.event.AnalysisEventListener
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.common.systemparam.executor.orgdata;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntOrgDataImpExcelListener
extends AnalysisEventListener<LinkedHashMap<Integer, String>> {
    private Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    private Map<String, List<OrgDTO>> impParseData = new HashMap<String, List<OrgDTO>>();
    private Map<String, Map<String, Integer>> orgTypeToColumnToIndexMap = new HashMap<String, Map<String, Integer>>();
    private Map<String, String> orgTypeNameToOrgTypeCodeMap;
    private Map<String, String> orgNameToOrgCodeMap = new HashMap<String, String>();

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        String orgType;
        if (this.orgTypeNameToOrgTypeCodeMap == null || this.orgTypeNameToOrgTypeCodeMap.isEmpty()) {
            OrgCategoryService orgCategoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
            List orgCategoryDOList = orgCategoryService.list(new OrgCategoryDO()).getRows();
            if (CollectionUtils.isEmpty((Collection)orgCategoryDOList)) {
                return;
            }
            this.orgTypeNameToOrgTypeCodeMap = orgCategoryDOList.stream().collect(Collectors.toMap(OrgCategoryDO::getTitle, OrgCategoryDO::getName, (o1, o2) -> o1));
        }
        if (StringUtils.isEmpty((String)(orgType = this.orgTypeNameToOrgTypeCodeMap.get(context.readSheetHolder().getSheetName().trim())))) {
            this.logger.info("\u672a\u627e\u5230\u673a\u6784\u7c7b\u578b\u3010" + context.readSheetHolder().getSheetName().trim() + "\u3011\u8df3\u8fc7\u5bf9\u5e94\u673a\u6784\u6570\u636e\u521d\u59cb\u5316");
            return;
        }
        HashMap<String, Integer> columnToIndexMap = new HashMap<String, Integer>();
        for (Integer index : headMap.keySet()) {
            columnToIndexMap.put(headMap.get(index).trim(), index);
        }
        this.orgTypeToColumnToIndexMap.put(orgType, columnToIndexMap);
    }

    public void invoke(LinkedHashMap<Integer, String> data, AnalysisContext context) {
        String orgType = this.orgTypeNameToOrgTypeCodeMap.get(context.readSheetHolder().getSheetName().trim());
        if (StringUtils.isEmpty((String)orgType)) {
            return;
        }
        OrgDTO row = this.parseRowData(orgType, data);
        if (row == null) {
            return;
        }
        if (!this.impParseData.containsKey(orgType)) {
            this.impParseData.put(orgType, new ArrayList());
        }
        this.impParseData.get(orgType).add(row);
    }

    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    private OrgDTO parseRowData(String orgType, LinkedHashMap<Integer, String> data) {
        Map<String, Integer> columnToIndexMap = this.orgTypeToColumnToIndexMap.get(orgType);
        OrgDTO orgDTO = new OrgDTO();
        String parentCode = data.get(columnToIndexMap.get("\u7236\u7ea7|PARENTCODE"));
        parentCode = StringUtils.isEmpty((String)parentCode) ? "" : parentCode.split("\\|")[0];
        String diffUnitId = data.get(columnToIndexMap.get("\u672c\u90e8\u5355\u4f4d|BASEUNITID"));
        diffUnitId = StringUtils.isEmpty((String)diffUnitId) ? "" : diffUnitId.split("\\|")[0];
        String baseUnitId = data.get(columnToIndexMap.get("\u5dee\u989d\u5355\u4f4d|DIFFUNITID"));
        baseUnitId = StringUtils.isEmpty((String)baseUnitId) ? "" : baseUnitId.split("\\|")[0];
        orgDTO.setCode(data.get(columnToIndexMap.get("\u4ee3\u7801|CODE")));
        orgDTO.setOrgcode(data.get(columnToIndexMap.get("\u4ee3\u7801|CODE")));
        orgDTO.setName(data.get(columnToIndexMap.get("\u540d\u79f0|NAME")));
        orgDTO.setShortname(data.get(columnToIndexMap.get("\u7b80\u79f0|SHORTNAME")));
        orgDTO.setVersionDate(new Date());
        orgDTO.setCategoryname(orgType);
        orgDTO.put("currencyid", (Object)"CNY");
        orgDTO.put("currencyids", Arrays.asList("CNY"));
        orgDTO.setParentcode(parentCode);
        orgDTO.put("bblx", (Object)this.getBblx(data.get(columnToIndexMap.get("\u62a5\u8868\u7c7b\u578b|BBLX"))));
        orgDTO.put("baseunitid", (Object)baseUnitId);
        orgDTO.put("diffunitid", (Object)diffUnitId);
        orgDTO.put("orgtypeid", (Object)this.orgTypeNameToOrgTypeCodeMap.get(data.get(columnToIndexMap.get("\u5408\u5e76\u5355\u4f4d\u7c7b\u578b|ORGTYPEID"))));
        orgDTO.put("ignoreCategoryAdd", (Object)true);
        if (!"MD_ORG".equals(orgType)) {
            orgDTO.setOrgCodes(Arrays.asList(orgDTO.getCode()));
        }
        this.orgNameToOrgCodeMap.put(orgDTO.getName(), orgDTO.getCode());
        return orgDTO;
    }

    private String getBblx(String bblx) {
        if ("\u96c6\u56e2\u5408\u5e76\u8868".equals(bblx)) {
            return "9";
        }
        if ("\u96c6\u56e2\u5dee\u989d\u8868".equals(bblx)) {
            return "1";
        }
        if ("\u5355\u6237\u8868".equals(bblx)) {
            return "0";
        }
        return "";
    }

    public Map<String, List<OrgDTO>> getImpParseData() {
        return this.impParseData;
    }
}

