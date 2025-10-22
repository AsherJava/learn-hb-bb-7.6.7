/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package nr.single.data.datain.service;

import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.data.datain.internal.service.fmdm.TaskFileImportEntityFMDMServiceImpl;
import nr.single.data.datain.internal.service.org.TaskFileImportEntityOrgServiceImpl;
import nr.single.data.datain.service.ITaskFileImportEntityService;

public class TaskFileImportEntityServiceManager {
    private static final TaskFileImportEntityServiceManager instance = new TaskFileImportEntityServiceManager();
    private Map<String, ITaskFileImportEntityService> factoryMap = new HashMap<String, ITaskFileImportEntityService>();

    public static final TaskFileImportEntityServiceManager getInstance() {
        return instance;
    }

    public void regService(ITaskFileImportEntityService factory) {
        if (factory == null || factory.getType() == null) {
            throw new NullPointerException();
        }
        this.factoryMap.put(factory.getType().toUpperCase(), factory);
    }

    public ITaskFileImportEntityService findService(String type) {
        if (type == null) {
            throw new NullPointerException();
        }
        ITaskFileImportEntityService intfObj = this.factoryMap.get(type.toUpperCase());
        if (intfObj == null) {
            if ("FMDM".equalsIgnoreCase(type)) {
                intfObj = (ITaskFileImportEntityService)ApplicationContextRegister.getBean(TaskFileImportEntityFMDMServiceImpl.class);
                if (intfObj != null) {
                    this.factoryMap.put("FMDM", intfObj);
                }
            } else if ("ORG".equalsIgnoreCase(type) && (intfObj = (ITaskFileImportEntityService)ApplicationContextRegister.getBean(TaskFileImportEntityOrgServiceImpl.class)) != null) {
                this.factoryMap.put("ORG", intfObj);
            }
        }
        return intfObj;
    }

    public List<ITaskFileImportEntityService> getAllServices() {
        return new ArrayList<ITaskFileImportEntityService>(this.factoryMap.values());
    }
}

