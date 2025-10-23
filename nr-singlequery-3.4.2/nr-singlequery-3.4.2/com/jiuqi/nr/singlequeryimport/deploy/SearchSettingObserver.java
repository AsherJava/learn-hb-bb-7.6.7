/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 */
package com.jiuqi.nr.singlequeryimport.deploy;

import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
public class SearchSettingObserver
implements Observer,
DeployPrepareEventListener,
IParamDeployFinishListener {
    Logger logger = LoggerFactory.getLogger(SearchSettingObserver.class);
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    private QueryModeleDao queryModeleDao;

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u5f00\u59cb\u5220\u9664\u6a2a\u5411\u8fc7\u5f55\u67e5\u8be2\u6a21\u677f");
        try {
            this.queryModeleDao.deleteScheme(define.getTaskKey());
            this.logger.info("\u62a5\u8868\u65b9\u6848{},\u6240\u5173\u8054\u7684\u6a2a\u5411\u8fc7\u5f55\u67e5\u8be2\u6a21\u677f\u5220\u9664\u6210\u529f", (Object)define.getTaskKey());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void onDeploy(DeployParams deployParams) {
        DeployItem formSchemeDeploy = deployParams.getFormScheme();
        if (formSchemeDeploy == null) {
            return;
        }
        Set runTimeKeys = formSchemeDeploy.getRunTimeKeys();
        Set designTimeKeys = formSchemeDeploy.getDesignTimeKeys();
        HashSet runKeysCopy = new HashSet(runTimeKeys);
        runKeysCopy.removeAll(designTimeKeys);
        if (CollectionUtils.isEmpty(runKeysCopy)) {
            return;
        }
        for (String schemeKey : runKeysCopy) {
            try {
                this.queryModeleDao.deleteScheme(schemeKey);
                this.logger.info("\u62a5\u8868\u65b9\u6848{},\u6240\u5173\u8054\u7684\u6a2a\u5411\u8fc7\u5f55\u67e5\u8be2\u6a21\u677f\u5220\u9664\u6210\u529f", (Object)schemeKey);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    public boolean isAsyn() {
        return false;
    }

    public void excute(Object[] objs) throws Exception {
    }

    public String getName() {
        return super.getName();
    }
}

