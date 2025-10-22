/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.web.facade.EntityLinkageObject;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveEntityLinkageHelper {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;

    public void saveEntityLinkageObject(List<EntityTables> hashEntityTable) throws JQException {
        DesignEntityLinkageDefine entityLinkageDefine = this.nrDesignTimeController.createEntityLinkageDefine();
        if (hashEntityTable != null) {
            for (EntityTables entityObject : hashEntityTable) {
                EntityLinkageObject entityLinkageObject = entityObject.getEntityLinkageObject();
                if (entityLinkageObject.isIsNew()) {
                    entityLinkageDefine.setKey(entityObject.getID());
                    entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
                    entityLinkageDefine.setSlaveEntityKey(entityLinkageObject.getSlaveEntityKey());
                    entityLinkageDefine.setMasterEntityKey(entityLinkageObject.getMasterEntityKey());
                    entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
                    entityLinkageDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
                    this.nrDesignTimeController.insertDesignerEntityeLinkageDefine(entityLinkageDefine);
                }
                if (!entityLinkageObject.isIsDirty()) continue;
                entityLinkageDefine.setKey(entityObject.getID());
                entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
                entityLinkageDefine.setSlaveEntityKey(entityLinkageObject.getSlaveEntityKey());
                entityLinkageDefine.setMasterEntityKey(entityLinkageObject.getMasterEntityKey());
                entityLinkageDefine.setLinkageCondition(entityLinkageObject.getLinkageCondition());
                if (this.nrDesignTimeController.queryDesignEntityLinkageDefineByKey(entityObject.getID()) == null) {
                    this.nrDesignTimeController.insertDesignerEntityeLinkageDefine(entityLinkageDefine);
                    continue;
                }
                this.nrDesignTimeController.updateEntityLinkageDefine(entityLinkageDefine);
            }
        }
    }
}

