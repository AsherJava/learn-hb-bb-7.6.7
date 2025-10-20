/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl
 */
package com.jiuqi.gcreport.reportparam.init.executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupDefineImpl;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class GcReportTaskGroupInitExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        ClassPathResource userResource = new ClassPathResource("report-param-init/initTaskGroup.json");
        String sourceJson = "";
        try (InputStream is = userResource.getInputStream();){
            sourceJson = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u8bfb\u53d6\u6587\u4ef6initTaskGroup.json\u5931\u8d25", (Throwable)e);
        }
        List designTaskGroupDefineList = (List)JsonUtils.readValue((String)sourceJson, (TypeReference)new TypeReference<List<DesignTaskGroupDefineImpl>>(){});
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringContextUtils.getBean(IDesignTimeViewController.class);
        for (DesignTaskGroupDefineImpl define : designTaskGroupDefineList) {
            designTimeViewController.insertTaskGroupDefine((DesignTaskGroupDefine)define);
        }
    }
}

