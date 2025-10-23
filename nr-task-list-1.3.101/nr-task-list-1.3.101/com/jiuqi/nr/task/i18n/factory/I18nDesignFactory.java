/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 */
package com.jiuqi.nr.task.i18n.factory;

import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.i18n.register.I18nAbstractDesigner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
@Lazy(value=false)
public class I18nDesignFactory {
    private static final Map<Integer, ComponentDefine> registerMap = new HashMap<Integer, ComponentDefine>();

    @Autowired
    public I18nDesignFactory(List<I18nAbstractDesigner> designers) {
        I18nDesignFactory.register(designers);
    }

    public static ComponentDefine getDesign(Integer formType) {
        ComponentDefine formDesign = registerMap.get(formType);
        if (formDesign != null) {
            return formDesign;
        }
        return null;
    }

    public static void register(List<I18nAbstractDesigner> registers) {
        for (I18nAbstractDesigner register : registers) {
            Set<Integer> types = register.type();
            for (Integer type : types) {
                if (registerMap.get(type) != null) continue;
                registerMap.put(type, register.component());
            }
        }
    }
}

