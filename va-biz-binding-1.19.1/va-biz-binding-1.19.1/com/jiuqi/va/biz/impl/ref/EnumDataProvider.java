/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.feign.client.EnumDataClient
 */
package com.jiuqi.va.biz.impl.ref;

import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.ref.RefTableDataProvider;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.feign.client.EnumDataClient;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnumDataProvider
implements RefTableDataProvider {
    @Autowired
    private EnumDataClient enumDataClient;

    @Override
    public int getRefTableType() {
        return 2;
    }

    @Override
    public RefTableDataMap getRefTableDataMap(String tableName, Map<String, Object> dimValues) {
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setTenantName(Env.getTenantName());
        enumDataDTO.setBiztype(tableName);
        enumDataDTO.addExtInfo("languageTransFlag", (Object)true);
        List enumDatas = this.enumDataClient.list(enumDataDTO);
        final HashMap enumMap = new HashMap();
        enumDatas.forEach(o -> {
            PropertyDescriptor[] descriptors;
            HashMap<String, Object> values = new HashMap<String, Object>();
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(o);
            for (PropertyDescriptor descriptor : descriptors = beanWrapper.getPropertyDescriptors()) {
                if (descriptor.getReadMethod() == null || "class".equals(descriptor.getName())) continue;
                Object value = beanWrapper.getPropertyValue(descriptor.getName());
                values.put(descriptor.getName(), value);
            }
            enumMap.put(o.getVal(), Collections.unmodifiableMap(values));
        });
        return new RefTableDataMap(){
            Map<String, Map<String, Object>> map;
            {
                this.map = Collections.unmodifiableMap(enumMap);
            }

            @Override
            public String getTitleFieldName() {
                return "title";
            }

            @Override
            public Map<String, Object> find(String id) {
                return this.map.get(id);
            }

            @Override
            public Map<String, Map<String, Object>> list() {
                return this.map;
            }

            @Override
            public Map<String, Object> toViewValue(String showType, Map<String, Object> values) {
                if (values == null) {
                    return null;
                }
                HashMap<String, Object> resultValues = new HashMap<String, Object>();
                resultValues.put("name", values.get("val"));
                resultValues.put("title", values.get("title"));
                return resultValues;
            }
        };
    }
}

