/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
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
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUserProvider
implements RefTableDataProvider {
    @Autowired
    private AuthUserClient authUserClient;

    @Override
    public int getRefTableType() {
        return 3;
    }

    @Override
    public RefTableDataMap getRefTableDataMap(final String tableName, Map<String, Object> dimValues) {
        return new RefTableDataMap(){
            private Map<String, Map<String, Object>> idMap = new HashMap<String, Map<String, Object>>();
            private Map<String, Map<String, Object>> codeMap = new HashMap<String, Map<String, Object>>();
            private boolean all;

            @Override
            public String getTitleFieldName() {
                return "name";
            }

            @Override
            public Map<String, Map<String, Object>> list() {
                if (!this.all) {
                    this.idMap = this.list(null);
                    this.codeMap = new HashMap<String, Map<String, Object>>();
                    for (Map<String, Object> values : this.idMap.values()) {
                        this.codeMap.put((String)values.get("username"), values);
                    }
                    this.all = true;
                }
                return this.idMap;
            }

            @Override
            public Map<String, Object> find(String id) {
                if (Utils.isEmpty(id)) {
                    return null;
                }
                Map<String, Object> values = this.idMap.get(id);
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
                        this.codeMap.put((String)values.get("username"), values);
                    }
                    this.idMap.put(id, values);
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
            public Map<String, Object> toViewValue(String showType, Map<String, Object> values) {
                if (values == null) {
                    return null;
                }
                HashMap<String, Object> resultValues = new HashMap<String, Object>();
                Object id = values.get("id");
                String title = (String)values.get("name");
                resultValues.put("id", id);
                if (Utils.isEmpty(title)) {
                    resultValues.put("title", id);
                } else {
                    resultValues.put("title", title);
                }
                return resultValues;
            }

            private Map<String, Map<String, Object>> list(String id) {
                UserDTO userDTO = new UserDTO();
                if (Utils.isNotEmpty(id)) {
                    userDTO.setUsername(id);
                }
                userDTO.setTenantName(Env.getTenantName());
                PageVO pageVO = AuthUserProvider.this.authUserClient.list(userDTO);
                if (pageVO.getRows().size() == 0 && Utils.isNotEmpty(id)) {
                    userDTO.setUsername(null);
                    userDTO.setId(id);
                    pageVO = AuthUserProvider.this.authUserClient.list(userDTO);
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
                    Map values = AuthUserProvider.this.cast(o);
                    map.put(Convert.cast(o.getId(), String.class), values);
                });
                return map;
            }
        };
    }

    private Map<String, Object> cast(UserDO o) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(o);
        for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
            String name = pd.getName();
            if (pd.getReadMethod() == null || "class".equals(name)) continue;
            values.put(name, beanWrapper.getPropertyValue(name));
        }
        return Collections.unmodifiableMap(values);
    }
}

