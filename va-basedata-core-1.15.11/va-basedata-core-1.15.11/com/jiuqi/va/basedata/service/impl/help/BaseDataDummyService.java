/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.DummyObjType;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.domain.BaseDataDummyDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataFilterService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataOrderService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataDummyService")
public class BaseDataDummyService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataDummyService.class);
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private VaBaseDataDao baseDataDao;
    @Autowired
    private BaseDataFilterService baseDataFilterService;
    @Autowired
    private BaseDataOrderService baseDataOrderService;
    private static List<String> defineSpeCol = new ArrayList<String>();
    private BaseDataQueryService baseDataQueryService;

    public BaseDataQueryService getBaseDataQueryService() {
        if (this.baseDataQueryService == null) {
            this.baseDataQueryService = (BaseDataQueryService)ApplicationContextRegister.getBean(BaseDataQueryService.class);
        }
        return this.baseDataQueryService;
    }

    public R checkSQLDefine(BaseDataDummyDTO dummyBasedata) {
        LinkedList<BaseDataDO> baseDataDOList;
        String sqlColumnStr;
        if (!StringUtils.hasText(dummyBasedata.getSqlDefine())) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.sql.empty", new Object[0]));
        }
        String sqlDefine = dummyBasedata.getSqlDefine();
        try {
            sqlDefine = URLDecoder.decode(sqlDefine, "UTF-8").replaceAll("(\r\n|\r|\n|\n\r)", " ");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.sql.encoding.failed", new Object[0]));
        }
        if (sqlDefine.contains(";")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.sql.check.inclusion", new Object[0]));
        }
        if ((sqlDefine = sqlDefine.trim().toUpperCase()).length() < 20 || !"SELECT ".equals(sqlDefine.substring(0, 7)) || !sqlDefine.contains(" FROM")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.not.standard.sql", new Object[0]));
        }
        int fromIndex = sqlDefine.indexOf(" FROM ");
        int fromIndex2 = sqlDefine.indexOf(" FROM(");
        if (fromIndex2 != -1 && fromIndex2 < fromIndex) {
            fromIndex = fromIndex2;
        }
        if ((sqlColumnStr = sqlDefine.substring(6, fromIndex).trim()).contains("*") || sqlColumnStr.contains("(")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.not.standard.sql", new Object[0]));
        }
        ArrayList<String> columnList = new ArrayList<String>();
        String[] sqlColumnArr = sqlColumnStr.split("\\,");
        String column = "";
        for (String columnStr : sqlColumnArr) {
            if (!columnStr.contains(" AS ")) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.not.standard.sql", new Object[0]));
            }
            if (columnStr.split(" AS ").length != 2) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.not.standard.sql", new Object[0]));
            }
            column = columnStr.split(" AS ")[1].trim();
            Pattern pattern = Pattern.compile("^[A-Z][A-Z0-9_]*$");
            Matcher matcher = pattern.matcher(column);
            if (!matcher.find()) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.sql.check.column.rule", new Object[0]));
            }
            columnList.add(column);
        }
        if (!columnList.containsAll(defineSpeCol)) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.fields.must.contain", new Object[0]));
        }
        if (dummyBasedata.getStructtype() == 2 && !columnList.contains("PARENTCODE")) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.dummy.must.contain.parentcode", new Object[0]));
        }
        try {
            baseDataDOList = this.baseDataDao.listDummy(dummyBasedata);
        }
        catch (Throwable e) {
            logger.error(e.getCause().getMessage(), e);
            return R.error((String)e.getCause().getMessage());
        }
        BaseDataDummyDTO baseDataDummyDTO = new BaseDataDummyDTO();
        baseDataDummyDTO.setBasedataList(baseDataDOList);
        HashMap<String, BaseDataDummyDTO> map = new HashMap<String, BaseDataDummyDTO>();
        map.put("data", baseDataDummyDTO);
        return R.ok(map);
    }

    public PageVO<BaseDataDO> list(BaseDataDTO param) {
        PageVO page = new PageVO(true);
        if (param.get((Object)"defineStr") == null) {
            return page;
        }
        String defineStr = (String)param.get((Object)"defineStr");
        ObjectNode defineJson = JSONUtil.parseObject((String)defineStr);
        if (!defineJson.has("dummyObj") || defineJson.get("dummyObj") == null) {
            return page;
        }
        Integer dummyObj = defineJson.get("dummyObj").asInt();
        if (dummyObj.intValue() == DummyObjType.ORG.getCode()) {
            return this.listOrgData(param, defineJson.get("orgCategory").asText());
        }
        if (dummyObj.intValue() == DummyObjType.BASEDATA.getCode()) {
            String basedataDefineCode = null;
            String filterColumnCode = null;
            String filterCondition = null;
            String filterValue = null;
            JsonNode node = defineJson.get("basedataDefineCode");
            if (node != null) {
                basedataDefineCode = node.asText();
            }
            if ((node = defineJson.get("filterColumnCode")) != null) {
                filterColumnCode = node.asText();
            }
            if ((node = defineJson.get("filterCondition")) != null) {
                filterCondition = node.asText();
            }
            if ((node = defineJson.get("filterValue")) != null) {
                filterValue = node.asText();
            }
            return this.listBaseData(param, basedataDefineCode, filterColumnCode, filterCondition, filterValue);
        }
        if (dummyObj.intValue() == DummyObjType.SQLDEFINE.getCode()) {
            param.put("dummyflag", (Object)1);
            return this.listSqlData(param, defineJson);
        }
        return page;
    }

    private PageVO<BaseDataDO> listOrgData(BaseDataDTO param, String orgCategory) {
        PageVO orgDOPageVO;
        List orgDOList;
        OrgDTO orgDTO = (OrgDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)param), OrgDTO.class);
        orgDTO.setCategoryname(orgCategory);
        orgDTO.remove((Object)"defineStr");
        if (param.getObjectcode() != null) {
            orgDTO.setCode(param.getObjectcode());
        }
        if (param.getBaseDataObjectcodes() != null) {
            orgDTO.setOrgCodes(param.getBaseDataObjectcodes());
        }
        if (param.getBaseDataCodes() != null) {
            orgDTO.setOrgCodes(param.getBaseDataCodes());
        }
        if ((orgDOList = (orgDOPageVO = this.orgDataClient.list(orgDTO)).getRows()) == null || orgDOList.isEmpty()) {
            return new PageVO(true);
        }
        ArrayList<BaseDataDO> baseDataDOList = new ArrayList<BaseDataDO>();
        BaseDataDO baseDataDO = null;
        for (OrgDO orgDO : orgDOList) {
            baseDataDO = new BaseDataDO((Map)orgDO);
            baseDataDO.setObjectcode(orgDO.getCode());
            baseDataDOList.add(baseDataDO);
        }
        return new PageVO(baseDataDOList, orgDOPageVO.getTotal());
    }

    private PageVO<BaseDataDO> listBaseData(BaseDataDTO param, String basedataDefineCode, String filterColumnCode, String filterCondition, String filterValue) {
        BaseDataDTO baseDataDTO = (BaseDataDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)param), BaseDataDTO.class);
        baseDataDTO.setTableName(basedataDefineCode);
        baseDataDTO.remove((Object)"defineStr");
        if (StringUtils.hasText(filterColumnCode) && StringUtils.hasText(filterCondition)) {
            baseDataDTO.put("dummyBaseDataFilter", (Object)true);
            baseDataDTO.put("dummyColumnCode", (Object)filterColumnCode.toLowerCase());
            baseDataDTO.put("dummyFilterCondition", (Object)filterCondition);
            baseDataDTO.put("dummyFilterValue", (Object)filterValue);
        }
        return this.getBaseDataQueryService().list(baseDataDTO);
    }

    private PageVO<BaseDataDO> listSqlData(BaseDataDTO param, ObjectNode defineJson) {
        LinkedList<BaseDataDO> baseDataDOList;
        String objectcodeParam;
        BaseDataDummyDTO baseDataDummyDTO = new BaseDataDummyDTO();
        baseDataDummyDTO.setTenantName(param.getTenantName());
        baseDataDummyDTO.setPagination(false);
        String sql = defineJson.get("sqlDefine").asText();
        baseDataDummyDTO.setSqlDefine(sql);
        ArrayNode columnsArr = defineJson.withArray("columns");
        List cols = JSONUtil.parseArray((String)columnsArr.toString(), DataModelColumn.class);
        HashSet<String> modelCols = new HashSet<String>();
        for (DataModelColumn column : cols) {
            modelCols.add(column.getColumnName().toLowerCase());
        }
        BaseDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        String codeParam = param.getCode();
        if (codeParam != null && queryChildrenType == null) {
            baseDataDummyDTO.addExtInfo("code", param.getCode());
        }
        if ((objectcodeParam = param.getObjectcode()) != null && queryChildrenType == null) {
            if (modelCols.contains("objectcode")) {
                baseDataDummyDTO.addExtInfo("objectcode", param.getObjectcode());
            } else {
                baseDataDummyDTO.addExtInfo("code", param.getObjectcode());
            }
        }
        if ((baseDataDOList = this.baseDataDao.listDummy(baseDataDummyDTO)) == null || baseDataDOList.isEmpty()) {
            return new PageVO(true);
        }
        HashSet objcodesSets = null;
        List baseDataObjectcodes = param.getBaseDataObjectcodes();
        if (baseDataObjectcodes != null && !baseDataObjectcodes.isEmpty()) {
            objcodesSets = new HashSet();
            objcodesSets.addAll(baseDataObjectcodes);
        }
        boolean needFillCode = queryChildrenType != null && codeParam == null && objectcodeParam != null;
        BaseDataDTO param2 = new BaseDataDTO();
        param2.putAll((Map)param);
        LinkedList<BaseDataDO> cacheList = new LinkedList<BaseDataDO>();
        for (BaseDataDO baseDataDO : baseDataDOList) {
            if (baseDataDO.getObjectcode() == null) {
                baseDataDO.setObjectcode(baseDataDO.getCode());
            }
            if (needFillCode && param2.getCode() == null && objectcodeParam.equals(baseDataDO.getObjectcode())) {
                param2.setCode(baseDataDO.getCode());
            }
            if (objcodesSets != null && !objcodesSets.contains(baseDataDO.getObjectcode())) continue;
            cacheList.add(baseDataDO);
        }
        this.baseDataFilterService.filterList(cacheList, param2, modelCols, false);
        if (modelCols.contains("ordinal") || param.getOrderBy() != null) {
            this.baseDataOrderService.orderList(cacheList, param2);
        }
        PageVO page = new PageVO();
        page.setRs(R.ok());
        int tatal = cacheList.size();
        page.setRs(R.ok());
        page.setTotal(tatal);
        if (param.isPagination() && param.getLimit() > 0) {
            if (param.getOffset() < tatal) {
                int endNum = param.getOffset() + param.getLimit();
                endNum = endNum > tatal ? tatal : endNum;
                page.setRows(cacheList.subList(param.getOffset(), endNum));
            } else {
                page.setRows(new ArrayList());
            }
        } else {
            page.setRows(cacheList);
        }
        return page;
    }

    static {
        defineSpeCol.add("CODE");
        defineSpeCol.add("NAME");
    }
}

