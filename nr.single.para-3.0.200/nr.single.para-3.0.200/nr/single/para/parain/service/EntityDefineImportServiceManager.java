/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package nr.single.para.parain.service;

import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.internal.service3.entity.EntityDefineImportBaseDataService;
import nr.single.para.parain.internal.service3.entity.EntityDefineImportOrgDataService;
import nr.single.para.parain.service.IEntityDefineImportService;

public class EntityDefineImportServiceManager {
    private static final EntityDefineImportServiceManager instance = new EntityDefineImportServiceManager();
    private Map<String, IEntityDefineImportService> factoryMap = new HashMap<String, IEntityDefineImportService>();

    public static final EntityDefineImportServiceManager getInstance() {
        return instance;
    }

    public void regService(IEntityDefineImportService factory) {
        if (factory == null || factory.getType() == null) {
            throw new NullPointerException();
        }
        this.factoryMap.put(factory.getType().toUpperCase(), factory);
    }

    public IEntityDefineImportService findService(String type) {
        if (type == null) {
            throw new NullPointerException();
        }
        IEntityDefineImportService intfObj = this.factoryMap.get(type.toUpperCase());
        if (intfObj == null) {
            if ("ORG".equalsIgnoreCase(type)) {
                intfObj = (IEntityDefineImportService)ApplicationContextRegister.getBean(EntityDefineImportOrgDataService.class);
                if (intfObj != null) {
                    this.factoryMap.put("ORG", intfObj);
                }
            } else if ("BASE".equalsIgnoreCase(type) && (intfObj = (IEntityDefineImportService)ApplicationContextRegister.getBean(EntityDefineImportBaseDataService.class)) != null) {
                this.factoryMap.put("BASE", intfObj);
            }
        }
        return intfObj;
    }

    public List<IEntityDefineImportService> getAllServices() {
        return new ArrayList<IEntityDefineImportService>(this.factoryMap.values());
    }
}

