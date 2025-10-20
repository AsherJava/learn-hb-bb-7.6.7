/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.va.biz.impl.ref;

import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.utils.BaseDataUtils;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BaseAllDataProvider {
    @Autowired
    private BaseDataClient baseDataClient;

    public RefTableDataMap getRefTableDataMap(final String tableName, final Map<String, Object> dimValues) {
        String unitCode = (String)dimValues.get("UNITCODE");
        final Boolean leafFlag = dimValues.containsKey("LEAFFLAG") ? (Boolean)dimValues.get("LEAFFLAG") : false;
        if (Utils.isEmpty(unitCode)) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.mustsetorg"));
        }
        final Date bizDate = (Date)dimValues.get("BIZDATE");
        if (bizDate == null) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.mustsetdate"));
        }
        return new RefTableDataMap(){
            private Map<String, Map<String, Object>> objectCodeMap = new HashMap<String, Map<String, Object>>();
            private Map<String, Map<String, Object>> codeMap = new HashMap<String, Map<String, Object>>();
            private boolean all;

            @Override
            public String getTitleFieldName() {
                return "name";
            }

            @Override
            public Map<String, Map<String, Object>> list() {
                if (!this.all) {
                    this.objectCodeMap = this.list(null);
                    this.codeMap = new HashMap<String, Map<String, Object>>();
                    for (Map<String, Object> values : this.objectCodeMap.values()) {
                        this.codeMap.put((String)values.get("code"), values);
                    }
                    this.all = true;
                }
                return this.objectCodeMap;
            }

            @Override
            public Map<String, Object> find(String id) {
                if (Utils.isEmpty(id)) {
                    return null;
                }
                Map<String, Object> values = this.objectCodeMap.get(id);
                if (values == null) {
                    values = this.codeMap.get(id);
                }
                if (values == null && !this.all) {
                    Map<String, Map<String, Object>> map = this.list(id);
                    if (map == RefTableDataMap.EMPTY_MAP) {
                        values = RefTableDataMap.EMPTY_VALUE;
                    } else if (map == RefTableDataMap.ERROR_MAP) {
                        values = RefTableDataMap.ERROR_VALUE;
                    } else {
                        values = map.values().iterator().next();
                        this.codeMap.put((String)values.get("code"), values);
                    }
                    this.objectCodeMap.put(id, values);
                }
                if (values == RefTableDataMap.ERROR_VALUE) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.orgdataprovider.matchingmultiple", new Object[]{tableName}) + id);
                }
                if (values == RefTableDataMap.EMPTY_VALUE) {
                    return null;
                }
                return values;
            }

            @Override
            public Map<String, Object> findByName(String name) {
                if (Utils.isEmpty(name)) {
                    return null;
                }
                BaseDataDTO basedataDTO = new BaseDataDTO();
                basedataDTO.put("onlyMarkAuth", (Object)true);
                basedataDTO.setName(name);
                basedataDTO.setTableName(tableName);
                basedataDTO.setStopflag(Integer.valueOf(-1));
                basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                for (Map.Entry entry : dimValues.entrySet()) {
                    if (((String)entry.getKey()).equals("BIZDATE")) continue;
                    basedataDTO.put(((String)entry.getKey()).toLowerCase(), entry.getValue());
                }
                basedataDTO.setVersionDate(bizDate);
                basedataDTO.setLeafFlag(leafFlag);
                if (Utils.isEmpty(basedataDTO.getTenantName())) {
                    basedataDTO.setTenantName(Env.getTenantName());
                }
                PageVO pageVO = BaseAllDataProvider.this.baseDataClient.list(basedataDTO);
                if (Utils.isNotEmpty(name)) {
                    if (pageVO.getRows().size() == 0) {
                        return RefTableDataMap.EMPTY_VALUE;
                    }
                    if (pageVO.getRows().size() > 1) {
                        return RefTableDataMap.EMPTY_VALUE;
                    }
                }
                HashMap map = new HashMap();
                pageVO.getRows().forEach(o -> {
                    Map<String, Object> values = this.cast((BaseDataDO)o);
                    map.put(o.getObjectcode(), values);
                });
                return (Map)map.values().iterator().next();
            }

            @Override
            public Map<String, Object> toViewValue(String showType, Map<String, Object> values) {
                if (values == null) {
                    return null;
                }
                HashMap<String, Object> resultValues = new HashMap<String, Object>();
                resultValues.put("name", values.get("objectcode"));
                Object title = values.get("localizedName") != null ? values.get("localizedName") : values.get("name");
                resultValues.put("title", title);
                if (StringUtils.hasText(showType)) {
                    if ("CODE".equals(showType)) {
                        resultValues.put("showTitle", values.get("code"));
                    } else if ("NAME".equals(showType)) {
                        resultValues.put("showTitle", title);
                    } else if ("CODE&NAME".equals(showType)) {
                        resultValues.put("showTitle", values.get("code") + " " + title);
                    }
                } else {
                    Object showTitle = values.get("showTitle");
                    if (!Objects.equals(title, showTitle)) {
                        resultValues.put("showTitle", showTitle);
                    }
                }
                return resultValues;
            }

            @Override
            public List<Map<String, Object>> filter(Map<String, Object> filterMap) {
                Objects.requireNonNull(filterMap);
                if (this.all) {
                    return BaseDataUtils.findRefObject(filterMap, this.objectCodeMap);
                }
                StringBuilder espression = new StringBuilder(32);
                filterMap.forEach((key, value) -> {
                    BaseDataUtils.appendFilterNode(espression, key, value);
                    espression.append(" and ");
                });
                int expressionLength = espression.length();
                if (expressionLength > 0) {
                    return this.filter(espression.substring(0, espression.length() - 5));
                }
                return new ArrayList<Map<String, Object>>(this.list().values());
            }

            private Map<String, Map<String, Object>> list(String id) {
                BaseDataDTO basedataDTO = new BaseDataDTO();
                BaseAllDataProvider.this.setBaseDataDTO(tableName, dimValues, bizDate, id, basedataDTO, leafFlag);
                PageVO pageVO = BaseAllDataProvider.this.baseDataClient.list(basedataDTO);
                if (pageVO.getRows().size() == 0) {
                    basedataDTO = new BaseDataDTO();
                    BaseAllDataProvider.this.setBaseDataDTO(tableName, dimValues, bizDate, id, basedataDTO, leafFlag);
                    basedataDTO.setObjectcode(null);
                    basedataDTO.setCode(id);
                    pageVO = BaseAllDataProvider.this.baseDataClient.list(basedataDTO);
                }
                if (Utils.isNotEmpty(id)) {
                    if (pageVO.getRows().size() == 0) {
                        return RefTableDataMap.EMPTY_MAP;
                    }
                    if (pageVO.getRows().size() > 1) {
                        return RefTableDataMap.ERROR_MAP;
                    }
                }
                HashMap<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                pageVO.getRows().forEach(o -> {
                    Map<String, Object> values = this.cast((BaseDataDO)o);
                    map.put(o.getObjectcode(), values);
                });
                return map;
            }

            @Override
            public List<Map<String, Object>> filter(String expression) {
                if (Utils.isEmpty(expression)) {
                    return new ArrayList<Map<String, Object>>(this.list().values());
                }
                BaseDataDTO basedataDTO = new BaseDataDTO();
                basedataDTO.put("onlyMarkAuth", (Object)true);
                basedataDTO.setTableName(tableName);
                basedataDTO.setStopflag(Integer.valueOf(-1));
                basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                for (Map.Entry entry : dimValues.entrySet()) {
                    if (((String)entry.getKey()).equals("BIZDATE")) continue;
                    basedataDTO.put(((String)entry.getKey()).toLowerCase(), entry.getValue());
                }
                basedataDTO.setVersionDate(bizDate);
                if (Utils.isEmpty(basedataDTO.getTenantName())) {
                    basedataDTO.setTenantName(Env.getTenantName());
                }
                basedataDTO.setExpression(expression);
                basedataDTO.put("vaBizFormula", (Object)true);
                PageVO pageVO = BaseAllDataProvider.this.baseDataClient.list(basedataDTO);
                ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                pageVO.getRows().forEach(o -> {
                    Map<String, Object> values = this.cast((BaseDataDO)o);
                    list.add(values);
                    this.codeMap.put(o.getCode(), values);
                    this.objectCodeMap.put(o.getObjectcode(), values);
                });
                return list;
            }

            private Map<String, Object> cast(BaseDataDO o) {
                HashMap map = new HashMap(o);
                return Collections.unmodifiableMap(map);
            }
        };
    }

    private void setBaseDataDTO(String tableName, Map<String, Object> dimValues, Date bizDate, String id, BaseDataDTO basedataDTO, Boolean leafFlag) {
        basedataDTO.put("onlyMarkAuth", (Object)true);
        if (Utils.isNotEmpty(id)) {
            basedataDTO.setObjectcode(id);
        }
        basedataDTO.setTableName(tableName);
        basedataDTO.setStopflag(Integer.valueOf(-1));
        basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
            if (entry.getKey().equals("BIZDATE")) continue;
            basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        basedataDTO.setVersionDate(bizDate);
        basedataDTO.setLeafFlag(leafFlag);
        if (Utils.isEmpty(basedataDTO.getTenantName())) {
            basedataDTO.setTenantName(Env.getTenantName());
        }
    }
}

