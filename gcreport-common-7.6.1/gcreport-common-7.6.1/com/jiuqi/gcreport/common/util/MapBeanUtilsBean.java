/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.beanutils.BeanUtilsBean
 *  org.apache.commons.beanutils.ContextClassLoaderLocal
 */
package com.jiuqi.gcreport.common.util;

import java.util.Map;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ContextClassLoaderLocal;

class MapBeanUtilsBean
extends BeanUtilsBean {
    private static final ContextClassLoaderLocal<MapBeanUtilsBean> BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal<MapBeanUtilsBean>(){

        protected MapBeanUtilsBean initialValue() {
            return new MapBeanUtilsBean();
        }
    };

    MapBeanUtilsBean() {
    }

    public static MapBeanUtilsBean getInstance() {
        return (MapBeanUtilsBean)((Object)BEANS_BY_CLASSLOADER.get());
    }

    public void populate(Object bean, Map<String, ?> properties) {
        if (bean == null || properties == null) {
            return;
        }
        for (Map.Entry<String, ?> entry : properties.entrySet()) {
            String name = entry.getKey();
            if (name == null) continue;
            try {
                this.setProperty(bean, name, entry.getValue());
            }
            catch (Exception exception) {}
        }
    }
}

