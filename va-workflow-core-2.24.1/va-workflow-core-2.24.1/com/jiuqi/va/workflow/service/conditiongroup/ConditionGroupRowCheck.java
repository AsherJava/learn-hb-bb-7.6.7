/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.workflow.business.ConditionGroupRow
 *  com.jiuqi.va.domain.workflow.business.ConditionRowAttr
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.workflow.service.conditiongroup;

import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.workflow.business.ConditionGroupRow;
import com.jiuqi.va.domain.workflow.business.ConditionRowAttr;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionCheck;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionGroupContext;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class ConditionGroupRowCheck
implements ConditionCheck {
    private static final Logger log = LoggerFactory.getLogger(ConditionGroupRowCheck.class);
    private BaseDataClient baseDataClient;
    private BaseDataDefineClient baseDataDefineClient;
    private OrgDataClient orgDataClient;
    private ConditionGroupRow conditionGroupRow;
    private ConditionGroupContext conditionGroupContext;
    private static final String SELF = "SELF";
    private static final String DIRECT_CHILDREN = "DIRECT_CHILDREN";
    private static final String ALL_CHILDREN = "ALL_CHILDREN";
    private static final String DIRECT_CHILDREN_WITH_SELF = "DIRECT_CHILDREN_WITH_SELF";
    private static final String ALL_CHILDREN_WITH_SELF = "ALL_CHILDREN_WITH_SELF";
    public static final List<String> TYPE_STRING = new ArrayList<String>(Arrays.asList("UUID", "NVARCHAR", "CLOB", "IDENTIFY", "STRING"));
    public static final List<String> TYPE_DATE = new ArrayList<String>(Arrays.asList("DATE", "TIMESTAMP", "DATETIME"));
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_NUMERIC = "NUMERIC";
    public static final String TYPE_LONG = "LONG";
    public static final String TYPE_DECIMAL = "DECIMAL";
    public static final String TYPE_BOOLEAN = "BOOLEAN";

    private ConditionGroupRowCheck() {
    }

    public ConditionGroupRowCheck(ConditionGroupRow conditionGroupRow, ConditionGroupContext context) {
        this.conditionGroupRow = conditionGroupRow;
        this.conditionGroupContext = context;
        this.init();
    }

    private void init() {
        this.baseDataClient = this.conditionGroupContext.getBaseDataClient();
        this.baseDataDefineClient = this.conditionGroupContext.getBaseDataDefineClient();
        this.orgDataClient = this.conditionGroupContext.getOrgDataClient();
    }

    @Override
    public boolean check(Object data) {
        ConditionRowAttr bizField;
        HashMap bizFieldMap = (HashMap)data;
        if (bizFieldMap == null) {
            bizFieldMap = new HashMap();
        }
        if ((bizField = this.conditionGroupRow.getBizField()) == null) {
            return true;
        }
        String bizFieldName = bizField.getName();
        if (!StringUtils.hasText(bizFieldName)) {
            return true;
        }
        Object bizValueObj = bizFieldMap.get(bizFieldName);
        BigDecimal mappingType = bizField.getMappingType();
        Object validValueObj = this.conditionGroupRow.getValidValue();
        if (bizValueObj == null) {
            if (validValueObj instanceof List) {
                return CollectionUtils.isEmpty((List)validValueObj);
            }
            return validValueObj == null;
        }
        if (mappingType == null) {
            return this.handleOtherParamType(bizField, bizValueObj, validValueObj);
        }
        int mappingTypeInt = mappingType.intValue();
        switch (mappingTypeInt) {
            case 0: {
                return this.handleBooleanData(bizValueObj);
            }
            case 1: {
                return this.handleBaseData(bizValueObj);
            }
            case 2: {
                return this.handleEnumData(bizValueObj);
            }
            case 4: {
                return this.handleOrgData(bizValueObj);
            }
        }
        return true;
    }

    private boolean handleBooleanData(Object bizValueObj) {
        return this.handleOtherParamType(this.conditionGroupRow.getBizField(), bizValueObj, this.conditionGroupRow.getValidValue());
    }

    private boolean handleOtherParamType(ConditionRowAttr bizField, Object bizValueObj, Object validValueObj) {
        Object excludeValueObj;
        String paramType = bizField.getParamType();
        Object excludeValue = this.handleParamType(paramType, excludeValueObj = this.conditionGroupRow.getExcludeValue());
        if (Objects.equals(excludeValue, bizValueObj)) {
            return false;
        }
        if (validValueObj == null) {
            return true;
        }
        if (validValueObj instanceof String ? !StringUtils.hasText((String)validValueObj) : (validValueObj instanceof List ? CollectionUtils.isEmpty((List)validValueObj) : validValueObj instanceof Map && CollectionUtils.isEmpty((Map)validValueObj))) {
            return true;
        }
        Object validValue = this.handleParamType(paramType, validValueObj);
        return Objects.equals(validValue, bizValueObj);
    }

    private boolean handleEnumData(Object bizValueObj) {
        ArrayList<String> bizValues;
        ConditionRowAttr bizField = this.conditionGroupRow.getBizField();
        String mapping = bizField.getMapping();
        if (!StringUtils.hasText(mapping)) {
            return true;
        }
        if (bizValueObj instanceof List) {
            bizValues = (ArrayList<String>)bizValueObj;
        } else {
            bizValues = new ArrayList<String>();
            bizValues.add((String)bizValueObj);
        }
        List validValueMaps = (List)this.conditionGroupRow.getValidValue();
        if (bizValues.isEmpty()) {
            return validValueMaps == null || validValueMaps.isEmpty();
        }
        List excludeValueMaps = (List)this.conditionGroupRow.getExcludeValue();
        if (!CollectionUtils.isEmpty(excludeValueMaps)) {
            List excludeValues = excludeValueMaps.stream().map(excludeMap -> (String)excludeMap.get("code")).collect(Collectors.toList());
            boolean isExcludeFlag = bizValues.stream().anyMatch(excludeValues::contains);
            if (isExcludeFlag) {
                return false;
            }
        }
        if (CollectionUtils.isEmpty(validValueMaps)) {
            return true;
        }
        List validValues = validValueMaps.stream().map(validMap -> (String)validMap.get("code")).collect(Collectors.toList());
        return new HashSet(validValues).containsAll(bizValues);
    }

    private boolean handleOrgData(Object bizValueObj) {
        Object validAttrData;
        ConditionRowAttr bizField = this.conditionGroupRow.getBizField();
        String mapping = bizField.getMapping();
        if (!StringUtils.hasText(mapping)) {
            return true;
        }
        ConditionRowAttr validAttr = this.conditionGroupRow.getValidAttr();
        if (validAttr == null) {
            ArrayList<String> bizValues;
            if (bizValueObj instanceof List) {
                bizValues = (ArrayList<String>)bizValueObj;
            } else {
                bizValues = new ArrayList<String>();
                bizValues.add((String)bizValueObj);
            }
            List excludeValueMaps = (List)this.conditionGroupRow.getExcludeValue();
            ArrayList<String> excludeValues = new ArrayList();
            if (!CollectionUtils.isEmpty(excludeValueMaps)) {
                excludeValues = excludeValueMaps.stream().map(excludeValue -> (String)excludeValue.get("code")).collect(Collectors.toList());
            }
            String excludeLevel = this.conditionGroupRow.getExcludeLevel();
            ArrayList validValueMaps = (ArrayList)this.conditionGroupRow.getValidValue();
            if (bizValues.isEmpty()) {
                return validValueMaps == null || validValueMaps.isEmpty();
            }
            if (validValueMaps == null) {
                validValueMaps = new ArrayList();
            }
            List<String> validValues = validValueMaps.stream().map(validMap -> (String)validMap.get("code")).collect(Collectors.toList());
            String validLevel = this.conditionGroupRow.getValidLevel();
            return this.handleOrgDataMapping(mapping, excludeValues, excludeLevel, bizValues, validValues, validLevel);
        }
        String attrMapping = validAttr.getMapping();
        BigDecimal attrMappingType = validAttr.getMappingType();
        if (StringUtils.hasText(attrMapping) && attrMappingType != null) {
            List<String> bizValues = this.getBizValues(bizValueObj, validAttr);
            List excludeValuesMap = (List)this.conditionGroupRow.getExcludeValue();
            ArrayList<String> excludeValues = new ArrayList();
            if (!CollectionUtils.isEmpty(excludeValuesMap)) {
                excludeValues = excludeValuesMap.stream().map(excludeValue -> (String)excludeValue.get("code")).collect(Collectors.toList());
            }
            String excludeLevel = this.conditionGroupRow.getExcludeLevel();
            ArrayList validValueMaps = (ArrayList)this.conditionGroupRow.getValidValue();
            if (bizValues.isEmpty()) {
                return validValueMaps == null || validValueMaps.isEmpty();
            }
            if (validValueMaps == null) {
                validValueMaps = new ArrayList();
            }
            List<String> validValues = validValueMaps.stream().map(validMap -> (String)validMap.get("code")).collect(Collectors.toList());
            String validLevel = this.conditionGroupRow.getValidLevel();
            return this.handleAttrMapping(excludeValues, excludeLevel, validValues, validLevel, bizValues, attrMappingType, attrMapping);
        }
        if (bizValueObj instanceof List) {
            return false;
        }
        OrgDO orgData = VaWorkFlowDataUtils.getOrgData(ShiroUtil.getTenantName(), (String)bizValueObj);
        Object excludeValueObj = this.conditionGroupRow.getExcludeValue();
        Object validValueObj = this.conditionGroupRow.getValidValue();
        if (orgData == null) {
            return validValueObj == null;
        }
        String paramType = bizField.getParamType();
        Object excludeValue2 = this.handleParamType(paramType, excludeValueObj);
        if (Objects.equals(excludeValue2, validAttrData = orgData.get((Object)validAttr.getName().toLowerCase()))) {
            return false;
        }
        if (validValueObj == null) {
            return true;
        }
        Object validValue = this.handleParamType(paramType, validValueObj);
        return Objects.equals(validValue, validAttrData);
    }

    private List<String> getBizValues(Object bizValueObj, ConditionRowAttr validAttr) {
        List<String> bizValues;
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        if (bizValueObj instanceof List) {
            orgDTO.setOrgCodes((List)bizValueObj);
        } else {
            orgDTO.setCode((String)bizValueObj);
        }
        PageVO orgPage = this.orgDataClient.list(orgDTO);
        if (orgPage != null && orgPage.getTotal() > 0) {
            List orgList = orgPage.getRows();
            bizValues = orgList.stream().map(data -> (String)data.get((Object)validAttr.getName().toLowerCase())).collect(Collectors.toList());
        } else {
            bizValues = new ArrayList<String>();
        }
        return bizValues;
    }

    private boolean handleBaseData(Object bizValueObj) {
        Object validAttrData;
        ConditionRowAttr bizField = this.conditionGroupRow.getBizField();
        String mapping = bizField.getMapping();
        if (!StringUtils.hasText(mapping)) {
            return true;
        }
        ConditionRowAttr validAttr = this.conditionGroupRow.getValidAttr();
        if (validAttr == null) {
            ArrayList<String> bizValues;
            if (bizValueObj instanceof List) {
                bizValues = (ArrayList<String>)bizValueObj;
            } else {
                bizValues = new ArrayList<String>();
                bizValues.add((String)bizValueObj);
            }
            ArrayList<String> excludeValues = new ArrayList();
            List excludeValueMaps = (List)this.conditionGroupRow.getExcludeValue();
            if (!CollectionUtils.isEmpty(excludeValueMaps)) {
                excludeValues = excludeValueMaps.stream().map(excludeValue -> (String)excludeValue.get("code")).collect(Collectors.toList());
            }
            String excludeLevel = this.conditionGroupRow.getExcludeLevel();
            ArrayList validValueMaps = (ArrayList)this.conditionGroupRow.getValidValue();
            if (bizValues.isEmpty()) {
                return validValueMaps == null || validValueMaps.isEmpty();
            }
            if (validValueMaps == null) {
                validValueMaps = new ArrayList();
            }
            List<String> validValues = validValueMaps.stream().map(validMap -> (String)validMap.get("code")).collect(Collectors.toList());
            String validLevel = this.conditionGroupRow.getValidLevel();
            return this.handleBaseDataMapping(mapping, excludeValues, excludeLevel, bizValues, validValues, validLevel);
        }
        String attrMapping = validAttr.getMapping();
        BigDecimal mappingType = validAttr.getMappingType();
        if (StringUtils.hasText(attrMapping) && mappingType != null) {
            List<String> bizValues = this.getBizValues(bizValueObj, mapping, validAttr);
            ArrayList<String> excludeValues = new ArrayList();
            List excludeValueMaps = (List)this.conditionGroupRow.getExcludeValue();
            if (!CollectionUtils.isEmpty(excludeValueMaps)) {
                excludeValues = excludeValueMaps.stream().map(excludeValue -> (String)excludeValue.get("code")).collect(Collectors.toList());
            }
            ArrayList validValueMaps = (ArrayList)this.conditionGroupRow.getValidValue();
            if (bizValues.isEmpty()) {
                return validValueMaps == null || validValueMaps.isEmpty();
            }
            if (validValueMaps == null) {
                validValueMaps = new ArrayList();
            }
            List<String> validValues = validValueMaps.stream().map(validMap -> (String)validMap.get("code")).collect(Collectors.toList());
            String excludeLevel = this.conditionGroupRow.getExcludeLevel();
            String validLevel = this.conditionGroupRow.getValidLevel();
            return this.handleAttrMapping(excludeValues, excludeLevel, validValues, validLevel, bizValues, mappingType, attrMapping);
        }
        if (bizValueObj instanceof List) {
            return false;
        }
        BaseDataDO baseDataDO = null;
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setObjectcode((String)bizValueObj);
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        basedataDTO.setTableName(mapping);
        basedataDTO.setStopflag(Integer.valueOf(-1));
        basedataDTO.setRecoveryflag(Integer.valueOf(-1));
        PageVO bizBaseDataPage = this.baseDataClient.list(basedataDTO);
        if (bizBaseDataPage != null && bizBaseDataPage.getTotal() > 0) {
            List bizBaseDataRows = bizBaseDataPage.getRows();
            baseDataDO = (BaseDataDO)bizBaseDataRows.get(0);
        }
        Object excludeValueObj = this.conditionGroupRow.getExcludeValue();
        Object validValueObj = this.conditionGroupRow.getValidValue();
        if (baseDataDO == null) {
            return validValueObj == null;
        }
        String paramType = bizField.getParamType();
        Object excludeValue2 = this.handleParamType(paramType, excludeValueObj);
        if (Objects.equals(excludeValue2, validAttrData = baseDataDO.get((Object)validAttr.getName().toLowerCase()))) {
            return false;
        }
        if (validValueObj == null) {
            return true;
        }
        Object validValue = this.handleParamType(paramType, validValueObj);
        return Objects.equals(validValue, validAttrData);
    }

    private List<String> getBizValues(Object bizValueObj, String mapping, ConditionRowAttr validAttr) {
        List<String> bizValues;
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(mapping);
        basedataDTO.setStopflag(Integer.valueOf(-1));
        basedataDTO.setRecoveryflag(Integer.valueOf(-1));
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        if (bizValueObj instanceof List) {
            basedataDTO.setBaseDataObjectcodes((List)bizValueObj);
        } else {
            basedataDTO.setObjectcode((String)bizValueObj);
        }
        PageVO bizBaseDataPage = this.baseDataClient.list(basedataDTO);
        if (bizBaseDataPage != null && bizBaseDataPage.getTotal() > 0) {
            List bizBaseDataRows = bizBaseDataPage.getRows();
            String attrName = validAttr.getName();
            bizValues = bizBaseDataRows.stream().map(data -> (String)data.get((Object)attrName.toLowerCase())).collect(Collectors.toList());
        } else {
            bizValues = new ArrayList<String>();
        }
        return bizValues;
    }

    private boolean handleAttrMapping(List<String> excludeValues, String excludeLevel, List<String> validValues, String validLevel, List<String> bizValues, BigDecimal mappingType, String attrMapping) {
        int mappingTypeInt = mappingType.intValue();
        switch (mappingTypeInt) {
            case 1: {
                return this.handleBaseDataMapping(attrMapping, excludeValues, excludeLevel, bizValues, validValues, validLevel);
            }
            case 2: {
                if (!CollectionUtils.isEmpty(excludeValues)) {
                    boolean isExcludeExits = bizValues.stream().anyMatch(excludeValues::contains);
                    if (isExcludeExits) {
                        return false;
                    }
                }
                if (CollectionUtils.isEmpty(validValues)) {
                    return true;
                }
                return new HashSet<String>(validValues).containsAll(bizValues);
            }
            case 3: {
                if (!CollectionUtils.isEmpty(excludeValues)) {
                    boolean isExcludeExits = bizValues.stream().anyMatch(excludeValues::contains);
                    if (isExcludeExits) {
                        return false;
                    }
                }
                if (CollectionUtils.isEmpty(validValues)) {
                    return true;
                }
                return new HashSet<String>(validValues).containsAll(bizValues);
            }
            case 4: {
                return this.handleOrgDataMapping(attrMapping, excludeValues, excludeLevel, bizValues, validValues, validLevel);
            }
        }
        return false;
    }

    private boolean handleOrgDataMapping(String mapping, List<String> excludeValues, String excludeLevel, List<String> bizValues, List<String> validValues, String validLevel) {
        if (!CollectionUtils.isEmpty(excludeValues)) {
            boolean isExistsExclude;
            if (!StringUtils.hasText(excludeLevel)) {
                excludeLevel = SELF;
            }
            switch (excludeLevel) {
                case "SELF": {
                    isExistsExclude = bizValues.stream().anyMatch(excludeValues::contains);
                    break;
                }
                case "DIRECT_CHILDREN": {
                    isExistsExclude = this.isExistsOrg(excludeValues, mapping, OrgDataOption.QueryChildrenType.DIRECT_CHILDREN, bizValues);
                    break;
                }
                case "ALL_CHILDREN": {
                    isExistsExclude = this.isExistsOrg(excludeValues, mapping, OrgDataOption.QueryChildrenType.ALL_CHILDREN, bizValues);
                    break;
                }
                case "DIRECT_CHILDREN_WITH_SELF": {
                    isExistsExclude = this.isExistsOrg(excludeValues, mapping, OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF, bizValues);
                    break;
                }
                case "ALL_CHILDREN_WITH_SELF": {
                    isExistsExclude = this.isExistsOrg(excludeValues, mapping, OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF, bizValues);
                    break;
                }
                default: {
                    isExistsExclude = false;
                }
            }
            if (isExistsExclude) {
                return false;
            }
        }
        if (CollectionUtils.isEmpty(validValues)) {
            return true;
        }
        if (!StringUtils.hasText(validLevel)) {
            validLevel = SELF;
        }
        switch (validLevel) {
            case "SELF": {
                return new HashSet<String>(validValues).containsAll(bizValues);
            }
            case "DIRECT_CHILDREN": {
                return this.isValidOrg(validValues, mapping, OrgDataOption.QueryChildrenType.DIRECT_CHILDREN, bizValues);
            }
            case "ALL_CHILDREN": {
                return this.isValidOrg(validValues, mapping, OrgDataOption.QueryChildrenType.ALL_CHILDREN, bizValues);
            }
            case "DIRECT_CHILDREN_WITH_SELF": {
                return this.isValidOrg(validValues, mapping, OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF, bizValues);
            }
            case "ALL_CHILDREN_WITH_SELF": {
                return this.isValidOrg(validValues, mapping, OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF, bizValues);
            }
        }
        return false;
    }

    private boolean isValidOrg(List<String> validValues, String mapping, OrgDataOption.QueryChildrenType queryType, List<String> bizValues) {
        ArrayList allOrgCodes = new ArrayList();
        for (String value : validValues) {
            PageVO<OrgDO> orgPageVO = this.getOrgPage(mapping, queryType, value);
            if (orgPageVO == null || orgPageVO.getTotal() <= 0) continue;
            List orgCodes = orgPageVO.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList());
            allOrgCodes.addAll(orgCodes);
            boolean contained = new HashSet(allOrgCodes).containsAll(bizValues);
            if (!contained) continue;
            return true;
        }
        return false;
    }

    private boolean isExistsOrg(List<String> values, String mapping, OrgDataOption.QueryChildrenType queryType, List<String> bizValues) {
        for (String value : values) {
            PageVO<OrgDO> orgPageVO = this.getOrgPage(mapping, queryType, value);
            if (orgPageVO == null || orgPageVO.getTotal() <= 0) continue;
            List orgCodes = orgPageVO.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList());
            boolean exists = bizValues.stream().anyMatch(orgCodes::contains);
            if (!exists) continue;
            return true;
        }
        return false;
    }

    private PageVO<OrgDO> getOrgPage(String mapping, OrgDataOption.QueryChildrenType queryType, String value) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setCategoryname(mapping);
        orgDTO.setQueryChildrenType(queryType);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setCode(value);
        return this.orgDataClient.list(orgDTO);
    }

    private boolean handleBaseDataMapping(String mapping, List<String> excludeValues, String excludeLevel, List<String> bizValues, List<String> validValues, String validLevel) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(mapping);
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(param);
        Integer structtype = baseDataDefineDO == null ? -1 : baseDataDefineDO.getStructtype();
        if (BaseDataConsts.BASEDATA_STRUCTTYPE_TREE.equals(structtype) || BaseDataConsts.BASEDATA_STRUCTTYPE_LEVELTREE.equals(structtype)) {
            if (!CollectionUtils.isEmpty(excludeValues)) {
                boolean isExistsExclude;
                if (!StringUtils.hasText(excludeLevel)) {
                    excludeLevel = SELF;
                }
                switch (excludeLevel) {
                    case "SELF": {
                        isExistsExclude = bizValues.stream().anyMatch(excludeValues::contains);
                        break;
                    }
                    case "DIRECT_CHILDREN": {
                        isExistsExclude = this.isExistsBaseData(excludeValues, mapping, BaseDataOption.QueryChildrenType.DIRECT_CHILDREN, bizValues);
                        break;
                    }
                    case "ALL_CHILDREN": {
                        isExistsExclude = this.isExistsBaseData(excludeValues, mapping, BaseDataOption.QueryChildrenType.ALL_CHILDREN, bizValues);
                        break;
                    }
                    case "DIRECT_CHILDREN_WITH_SELF": {
                        isExistsExclude = this.isExistsBaseData(excludeValues, mapping, BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF, bizValues);
                        break;
                    }
                    case "ALL_CHILDREN_WITH_SELF": {
                        isExistsExclude = this.isExistsBaseData(excludeValues, mapping, BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF, bizValues);
                        break;
                    }
                    default: {
                        isExistsExclude = false;
                    }
                }
                if (isExistsExclude) {
                    return false;
                }
            }
            if (CollectionUtils.isEmpty(validValues)) {
                return true;
            }
            if (!StringUtils.hasText(validLevel)) {
                validLevel = SELF;
            }
            switch (validLevel) {
                case "SELF": {
                    return new HashSet<String>(validValues).containsAll(bizValues);
                }
                case "DIRECT_CHILDREN": {
                    return this.isValidBaseData(mapping, bizValues, validValues, BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
                }
                case "ALL_CHILDREN": {
                    return this.isValidBaseData(mapping, bizValues, validValues, BaseDataOption.QueryChildrenType.ALL_CHILDREN);
                }
                case "DIRECT_CHILDREN_WITH_SELF": {
                    return this.isValidBaseData(mapping, bizValues, validValues, BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF);
                }
                case "ALL_CHILDREN_WITH_SELF": {
                    return this.isValidBaseData(mapping, bizValues, validValues, BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
                }
            }
            return false;
        }
        if (!CollectionUtils.isEmpty(excludeValues)) {
            boolean excludeFlag = bizValues.stream().anyMatch(excludeValues::contains);
            if (excludeFlag) {
                return false;
            }
        }
        if (CollectionUtils.isEmpty(validValues)) {
            return true;
        }
        return new HashSet<String>(validValues).containsAll(bizValues);
    }

    private boolean isValidBaseData(String mapping, List<String> bizValues, List<String> validValues, BaseDataOption.QueryChildrenType queryType) {
        ArrayList allObjectCodes = new ArrayList();
        for (String validValue : validValues) {
            PageVO<BaseDataDO> baseDataList = this.getBaseDataPage(mapping, queryType, validValue);
            if (baseDataList == null || baseDataList.getTotal() <= 0) continue;
            List objectCodes = baseDataList.getRows().stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
            allObjectCodes.addAll(objectCodes);
            boolean contained = new HashSet(allObjectCodes).containsAll(bizValues);
            if (!contained) continue;
            return true;
        }
        return false;
    }

    private boolean isExistsBaseData(List<String> values, String mapping, BaseDataOption.QueryChildrenType queryType, List<String> bizValues) {
        for (String value : values) {
            PageVO<BaseDataDO> baseDataList = this.getBaseDataPage(mapping, queryType, value);
            if (baseDataList == null || baseDataList.getTotal() <= 0) continue;
            List baseDataObjectCodes = baseDataList.getRows().stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList());
            boolean exists = bizValues.stream().anyMatch(baseDataObjectCodes::contains);
            if (!exists) continue;
            return true;
        }
        return false;
    }

    private PageVO<BaseDataDO> getBaseDataPage(String mapping, BaseDataOption.QueryChildrenType queryType, String validValue) {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setStopflag(Integer.valueOf(-1));
        basedataDTO.setRecoveryflag(Integer.valueOf(-1));
        basedataDTO.setTableName(mapping);
        basedataDTO.setQueryChildrenType(queryType);
        basedataDTO.setObjectcode(validValue);
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        return this.baseDataClient.list(basedataDTO);
    }

    public Object handleParamType(Object paramTypeObj, Object paramValueObj) {
        if (paramValueObj == null) {
            return null;
        }
        String paramType = (String)paramTypeObj;
        if (paramType == null) {
            paramType = "default";
        }
        String finalParamType = paramType;
        try {
            if (TYPE_STRING.stream().anyMatch(s -> s.equalsIgnoreCase(finalParamType)) || "default".equals(paramType)) {
                return String.valueOf(paramValueObj);
            }
            if (!StringUtils.hasText(paramValueObj.toString())) {
                return null;
            }
            if (TYPE_DATE.stream().anyMatch(s -> s.equalsIgnoreCase(finalParamType))) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dateFormat.parse(paramValueObj.toString());
            }
            if (TYPE_INTEGER.equalsIgnoreCase(paramType)) {
                return Integer.parseInt(paramValueObj.toString());
            }
            if (TYPE_LONG.equalsIgnoreCase(paramType)) {
                return Long.parseLong(paramValueObj.toString());
            }
            if (TYPE_BOOLEAN.equalsIgnoreCase(paramType)) {
                return Boolean.parseBoolean(paramValueObj.toString());
            }
            if (TYPE_NUMERIC.equalsIgnoreCase(paramType) || TYPE_DECIMAL.equalsIgnoreCase(paramType)) {
                return new BigDecimal(paramValueObj.toString());
            }
        }
        catch (NumberFormatException e) {
            log.error("\u53c2\u6570\u6570\u636e\u7c7b\u578b\u8f93\u5165\u9519\u8bef:{}", (Object)e.getMessage());
        }
        catch (ParseException e) {
            log.error("\u53c2\u6570\u65e5\u671f\u8f93\u5165\u9519\u8bef:{}", (Object)e.getMessage());
        }
        return paramValueObj;
    }
}

