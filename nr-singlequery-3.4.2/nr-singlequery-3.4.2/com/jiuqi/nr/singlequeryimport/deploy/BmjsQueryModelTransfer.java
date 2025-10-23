/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 */
package com.jiuqi.nr.singlequeryimport.deploy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BmjsQueryModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(BmjsQueryModelTransfer.class);
    @Autowired
    QueryModleService queryModleService;
    @Autowired
    QueryModeleDao queryModeleDao;
    @Autowired
    IDesignTimeViewController designTimeViewController;
    @Autowired
    AuthShareService authShareService;
    @Autowired
    IParamLevelManager paramLevelManager;

    public void importModel(IImportContext iImportContext, byte[] bytes) throws TransferException {
        logger.info("\u6a2a\u5411\u8fc7\u5f55\u8868\u67e5\u8be2\u5bfc\u5165");
        int serveLevel = this.paramLevelManager.getLevel().getValue();
        int srcPacketLevel = iImportContext.getSrcPacketLevel();
        try {
            DesignFormSchemeDefine formScheme;
            Boolean adjustLevel = false;
            ObjectMapper objectMapper = new ObjectMapper();
            QueryModel queryModel = (QueryModel)objectMapper.readValue(bytes, QueryModel.class);
            if (serveLevel != 0 && (queryModel.getLevel() == null || queryModel.getLevel() == 0)) {
                adjustLevel = true;
            }
            if ((formScheme = this.designTimeViewController.getFormScheme(queryModel.getFormschemeKey())) != null) {
                if (queryModel.getTaskKey().equals(formScheme.getTaskKey())) {
                    QueryModel modelById = this.queryModeleDao.getModelById(queryModel);
                    if (modelById != null) {
                        modelById.setItem(queryModel.getItem());
                        if (adjustLevel.booleanValue()) {
                            modelById.setLevel(srcPacketLevel);
                        }
                        this.queryModeleDao.update(modelById);
                        logger.info("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u67e5\u8be2\u6a21\u677f\uff0c\u66f4\u65b0\u6a21\u677f---\u300b" + queryModel.getItemTitle());
                    } else {
                        List<QueryModel> modelData = this.queryModeleDao.getModelData(queryModel.getKey());
                        if (adjustLevel.booleanValue()) {
                            queryModel.setLevel(srcPacketLevel);
                        }
                        if (modelData.isEmpty()) {
                            this.queryModeleDao.insert(queryModel);
                            logger.info("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u67e5\u8be2\u6a21\u677f\uff0c\u65b0\u589e\u6a21\u677f---\u300b" + queryModel.getItemTitle());
                        } else {
                            this.queryModeleDao.update(queryModel);
                            logger.info("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u67e5\u8be2\u6a21\u677f\uff0c\u552f\u4e00\u6807\u8bc6\u91cd\u590d\uff0c\u66f4\u65b0\u6a21\u677f---\u300b" + queryModel.getItemTitle());
                        }
                        if (StringUtils.hasText(queryModel.getItemTitle())) {
                            this.authShareService.addCurUserPrivilege(queryModel.getKey(), FinalaccountQueryAuthResourceType.FQ_MODEL_NODE);
                        }
                        if (StringUtils.hasText(queryModel.getGroup())) {
                            this.authShareService.addCurUserGroupPrivilege(queryModel.getFormschemeKey(), queryModel.getGroup(), FinalaccountQueryAuthResourceType.FQ_GROUP);
                        }
                    }
                } else {
                    logger.info("\u62a5\u8868\u65b9\u6848{}\u5728\u5f53\u524d\u7cfb\u7edf\u4e2d\u7684\u4efb\u52a1key{}\u4e0e\u539f\u7cfb\u7edf\u4e2d\u7684\u4efb\u52a1key{}\u4e0d\u4e00\u81f4\uff0c\u65e0\u6cd5\u5bfc\u5165\u67e5\u8be2\u6a21\u677f", queryModel.getFormschemeKey(), formScheme.getKey(), queryModel.getTaskKey());
                }
            } else {
                logger.info("\u62a5\u8868\u65b9\u6848{}\u5728\u5f53\u524d\u7cfb\u7edf\u4e2d\u6ca1\u6709\u627e\u5230\uff0c\u65e0\u6cd5\u5bfc\u5165\u67e5\u8be2\u6a21\u677f", (Object)queryModel.getFormschemeKey());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String s) throws TransferException {
        logger.info("\u6a2a\u5411\u8fc7\u5f55\u8868\u67e5\u8be2\u5bfc\u51fa");
        ObjectMapper objectMapper = new ObjectMapper();
        String[] split = s.split("#");
        try {
            List<QueryModel> modelData = this.queryModeleDao.getModelData(split[split.length - 1]);
            MetaExportModel exportModel = new MetaExportModel();
            exportModel.setData(objectMapper.writeValueAsBytes((Object)modelData.get(0)));
            return exportModel;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

