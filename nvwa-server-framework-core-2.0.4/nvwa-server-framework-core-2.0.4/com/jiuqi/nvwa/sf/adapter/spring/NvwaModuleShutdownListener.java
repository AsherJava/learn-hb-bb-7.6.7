/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.springadapter.servlet.NvwaServletContextListener
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.models.ModuleDescriptor;
import com.jiuqi.nvwa.springadapter.servlet.NvwaServletContextListener;
import java.lang.reflect.Method;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NvwaModuleShutdownListener
implements NvwaServletContextListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void contextDestroyed() {
        this.logger.info("NvwaModuleShutdownListener.onApplicationEvent");
        List<ModuleDescriptor> list = Framework.getInstance().getModules();
        for (ModuleDescriptor desc : list) {
            Class<?> clazz;
            ModuleWrapper wrapper = Framework.getInstance().getModuleWrappers().get(desc.getId());
            ModuleDescriptor module = wrapper.getModule();
            String className = module.getInitiatorClassName();
            if (className == null || className.length() == 0) {
                this.logger.debug("\u6a21\u5757[{}]\u672a\u5b9a\u4e49\u521d\u59cb\u5316\u7c7b\uff0c\u76f4\u63a5\u8df3\u8fc7\u6a21\u5757\u9500\u6bc1\u8fc7\u7a0b", (Object)module);
                return;
            }
            try {
                clazz = Class.forName(className);
            }
            catch (Exception e) {
                this.logger.error("\u6a21\u5757[{}]\u521d\u59cb\u5316\u5931\u8d25\uff0c\u65e0\u6cd5\u521b\u5efa\u6a21\u5757\u9500\u6bc1\u7c7b[{}]", module, className, e);
                continue;
            }
            try {
                Method destroy = clazz.getMethod("destroy", new Class[0]);
                this.destroyMethod(destroy, clazz);
            }
            catch (Exception exception) {}
        }
        DistributionManager.getInstance().stop();
    }

    private void destroyMethod(Method destroy, Class<?> clazz) throws Exception {
        Object bean;
        try {
            bean = SpringBeanUtils.getApplicationContext().getBean(clazz);
        }
        catch (Exception e) {
            try {
                bean = clazz.newInstance();
            }
            catch (Exception e2) {
                this.logger.error("\u65e0\u6cd5\u521b\u5efa\u6a21\u5757\u9500\u6bc1\u7c7b[{}]", (Object)clazz.getName());
                this.logger.error("", e);
                this.logger.error("", e2);
                throw e2;
            }
        }
        destroy.invoke(bean, new Object[0]);
    }
}

