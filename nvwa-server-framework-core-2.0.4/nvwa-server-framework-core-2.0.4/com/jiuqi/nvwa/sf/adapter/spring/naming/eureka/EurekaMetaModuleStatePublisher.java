/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.netflix.appinfo.ApplicationInfoManager
 *  com.netflix.discovery.DiscoveryClient
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.eureka;

import com.jiuqi.bi.core.nodekeeper.ServiceNodeStateHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.naming.IMetaModuleStatePublisher;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.DiscoveryClient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class EurekaMetaModuleStatePublisher
implements IMetaModuleStatePublisher {
    @Override
    public void publish() {
        HashMap<String, String> metadata = new HashMap<String, String>();
        metadata.put("module-state", ServiceNodeStateHolder.getState().name());
        ApplicationInfoManager.getInstance().registerAppMetadata(metadata);
        DiscoveryClient discoveryClient = SpringBeanUtils.getApplicationContext().getBean(DiscoveryClient.class);
        Method refreshInstanceInfo = null;
        Method register = null;
        Method refreshRegistry = null;
        try {
            for (Method declaredMethod : discoveryClient.getClass().getSuperclass().getDeclaredMethods()) {
                if (declaredMethod.getName().equals("register")) {
                    register = declaredMethod;
                }
                if (declaredMethod.getName().equals("refreshInstanceInfo")) {
                    refreshInstanceInfo = declaredMethod;
                }
                if (!declaredMethod.getName().equals("refreshRegistry")) continue;
                refreshRegistry = declaredMethod;
            }
            assert (refreshInstanceInfo != null);
            refreshInstanceInfo.setAccessible(true);
            refreshInstanceInfo.invoke(discoveryClient, new Object[0]);
            assert (register != null);
            register.setAccessible(true);
            register.invoke(discoveryClient, new Object[0]);
            assert (refreshRegistry != null);
            refreshRegistry.setAccessible(true);
            refreshRegistry.invoke(discoveryClient, new Object[0]);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

