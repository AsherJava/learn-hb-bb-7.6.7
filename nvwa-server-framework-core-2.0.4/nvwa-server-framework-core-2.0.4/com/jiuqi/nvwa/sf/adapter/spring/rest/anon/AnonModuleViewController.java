/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.Version
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.Version;
import com.jiuqi.nvwa.sf.ModuleWrapper;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.RemoteServiceException;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFRemoteResourceManage;
import com.jiuqi.nvwa.sf.legacy.LegacyModule;
import com.jiuqi.nvwa.sf.models.Dependence;
import com.jiuqi.nvwa.sf.models.SQLFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class AnonModuleViewController {
    Logger logger = LoggerFactory.getLogger(AnonModuleViewController.class);
    @Autowired
    private SFRemoteResourceManage remoteResourceManage;

    @GetMapping(value={"/module/view"})
    public Response getModuleSql(String moduleId, String serviceName, String oldVersion) {
        ModuleWrapper wrapper;
        IServiceManager target = this.remoteResourceManage.getServiceManagerResource(serviceName);
        if (target == null) {
            return Response.error("\u672a\u83b7\u53d6\u5230\u540d\u4e3a[" + serviceName + "]\u7684\u670d\u52a1");
        }
        Version version = StringUtils.isEmpty((String)oldVersion) ? new Version("0.0.0") : new Version(oldVersion);
        try {
            wrapper = target.getModuleWrapper(moduleId);
        }
        catch (RemoteServiceException e) {
            this.logger.error(e.getMessage(), e);
            return Response.error(e.getMessage());
        }
        try {
            JSONObject data = new JSONObject();
            data.put("name", (Object)wrapper.getModule().getName());
            data.put("status", (Object)wrapper.getStatus());
            data.put("moduleVersion", (Object)wrapper.getModuleVersion());
            data.put("dbVersion", (Object)wrapper.getDbVersion());
            List<Dependence> dependencies = wrapper.getModule().getDependencies();
            JSONArray objects = new JSONArray();
            for (Dependence dependency : dependencies) {
                objects.put((Object)dependency.id);
            }
            data.put("dependencies", (Object)objects);
            List<SQLFile> sqlFiles = new ArrayList<SQLFile>();
            for (LegacyModule legacy : wrapper.getLegacies()) {
                sqlFiles.addAll(legacy.getSqlFiles());
            }
            sqlFiles.addAll(wrapper.getSqlFiles());
            sqlFiles = sqlFiles.stream().filter(sqlFile -> sqlFile.getVersion().compareTo(version) > 0).collect(Collectors.toList());
            data.put("SQLFile", sqlFiles);
            return Response.ok(data.toString());
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u6a21\u5757\u6267\u884cSQL\u6587\u4ef6\u5931\u8d25", e);
            return Response.error(e.getMessage());
        }
    }
}

