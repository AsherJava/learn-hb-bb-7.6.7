/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.biz.impl.ref;

import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.ref.RefTableDataProvider;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgDataProvider
implements RefTableDataProvider {
    @Autowired
    private OrgDataClient orgDataClient;

    @Override
    public int getRefTableType() {
        return 4;
    }

    @Override
    public Map<String, Object> convertDimValues(String tableName, Map<String, Object> dimValues) {
        Date bizDate = (Date)dimValues.get("BIZDATE");
        if (bizDate == null) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.orgdataprovider.mustsetbusinessdate"));
        }
        if (dimValues.size() == 1) {
            return dimValues;
        }
        if (dimValues.containsKey("LEAFFLAG")) {
            return Utils.makeMap("BIZDATE", bizDate, "LEAFFLAG", true);
        }
        return Utils.makeMap("BIZDATE", bizDate);
    }

    @Override
    public RefTableDataMap getRefTableDataMap(final String tableName, Map<String, Object> dimValues) {
        final Date bizDate = (Date)dimValues.get("BIZDATE");
        if (bizDate == null) {
            throw new ModelException();
        }
        final Boolean leafFlag = dimValues.containsKey("LEAFFLAG") ? (Boolean)dimValues.get("LEAFFLAG") : false;
        return new RefTableDataMap(){
            private Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
            private boolean all;

            @Override
            public String getTitleFieldName() {
                return "name";
            }

            @Override
            public Map<String, Map<String, Object>> list() {
                if (!this.all) {
                    this.map = this.list(null);
                    this.all = true;
                }
                return this.map;
            }

            @Override
            public Map<String, Object> find(String id) {
                if (Utils.isEmpty(id)) {
                    return null;
                }
                if (this.all) {
                    return this.map.get(id);
                }
                Map<String, Object> values = this.map.get(id);
                if (values == null) {
                    Map<String, Map<String, Object>> map = this.list(id);
                    values = map == RefTableDataMap.EMPTY_MAP ? RefTableDataMap.EMPTY_VALUE : (map == RefTableDataMap.ERROR_MAP ? RefTableDataMap.ERROR_VALUE : map.values().iterator().next());
                    this.map.put(Convert.cast(values.get("code"), String.class), values);
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
                OrgDTO orgDTO = new OrgDTO();
                orgDTO.setCategoryname(tableName);
                orgDTO.setStopflag(Integer.valueOf(-1));
                orgDTO.setTenantName(Env.getTenantName());
                orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
                orgDTO.setVersionDate(bizDate);
                orgDTO.setLeafFlag(leafFlag);
                orgDTO.setName(name);
                PageVO pageVO = OrgDataProvider.this.orgDataClient.list(orgDTO);
                if (pageVO.getRows().size() == 0) {
                    return RefTableDataMap.EMPTY_VALUE;
                }
                if (pageVO.getRows().size() > 1) {
                    return RefTableDataMap.EMPTY_VALUE;
                }
                HashMap map = new HashMap();
                pageVO.getRows().forEach(o -> {
                    Map<String, Object> values = this.cast((OrgDO)o);
                    map.put(o.getCode(), values);
                });
                return (Map)map.values().iterator().next();
            }

            @Override
            public Map<String, Object> toViewValue(String showType, Map<String, Object> values) {
                if (values == null) {
                    return null;
                }
                HashMap<String, Object> resultValues = new HashMap<String, Object>();
                resultValues.put("name", values.get("code"));
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

            private Map<String, Map<String, Object>> list(String id) {
                OrgDTO orgDTO = new OrgDTO();
                OrgDataProvider.this.setOrgDTO(tableName, bizDate, id, orgDTO, leafFlag);
                PageVO pageVO = OrgDataProvider.this.orgDataClient.list(orgDTO);
                if (Utils.isNotEmpty(id)) {
                    Boolean authMark = null;
                    if (pageVO.getRows().size() == 0) {
                        orgDTO = new OrgDTO();
                        OrgDataProvider.this.setOrgDTO(tableName, bizDate, id, orgDTO, leafFlag);
                        authMark = false;
                        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
                        pageVO = OrgDataProvider.this.orgDataClient.list(orgDTO);
                    }
                    if (pageVO.getRows().size() == 0) {
                        return RefTableDataMap.EMPTY_MAP;
                    }
                    if (pageVO.getRows().size() > 1) {
                        return RefTableDataMap.ERROR_MAP;
                    }
                    OrgDO result = new OrgDO();
                    result.putAll((Map)pageVO.getRows().get(0));
                    result.put("authMark", (Object)authMark);
                    HashMap<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                    Map<String, Object> values = this.cast(result);
                    map.put(result.getCode(), values);
                    return map;
                }
                HashMap<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                pageVO.getRows().forEach(o -> {
                    Map<String, Object> values = this.cast((OrgDO)o);
                    map.put(o.getCode(), values);
                });
                return map;
            }

            private Map<String, Object> cast(OrgDO o) {
                return Collections.unmodifiableMap(o);
            }
        };
    }

    private void setOrgDTO(String tableName, Date bizDate, String id, OrgDTO orgDTO, Boolean leafFlag) {
        if (Utils.isNotEmpty(id)) {
            orgDTO.setCode(id);
        }
        orgDTO.setCategoryname(tableName);
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setTenantName(Env.getTenantName());
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setVersionDate(bizDate);
        orgDTO.setLeafFlag(leafFlag);
    }
}

