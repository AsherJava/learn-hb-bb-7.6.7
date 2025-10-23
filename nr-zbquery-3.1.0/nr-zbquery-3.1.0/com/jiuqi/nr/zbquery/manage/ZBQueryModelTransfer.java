/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.nvwa.dataanalyze.dao.ResourceTreeNodeDao
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.manage;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nr.zbquery.util.SerializeUtils;
import com.jiuqi.nvwa.dataanalyze.dao.ResourceTreeNodeDao;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ZBQueryModelTransfer
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryModelTransfer.class);
    private static final String KEY_ZBQEURYINFO = "zbqueryinfo";
    private static final String KEY_ZBQEURYDATA = "zbquerydata";
    @Autowired
    private ResourceTreeNodeDao resourceTreeNodeDao;
    @Autowired
    private ZBQueryInfoService zbQueryInfoService;
    @Autowired
    private IParamLevelManager paramLevelManager;

    public void importModel(IImportContext cxt, byte[] bytes) throws TransferException {
        try {
            this.doImport(bytes, cxt);
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }

    public MetaExportModel exportModel(IExportContext iExportContext, String guid) throws TransferException {
        try {
            byte[] bytes = this.doExport(guid);
            MetaExportModel exportModel = new MetaExportModel();
            exportModel.setData(bytes);
            return exportModel;
        }
        catch (Exception e) {
            throw new TransferException((Throwable)e);
        }
    }

    private void doImport(byte[] types, IImportContext cxt) throws Exception {
        String folderGuid = cxt.getFolderGuid();
        String targetGuid = cxt.getTargetGuid();
        int srcPacketLevel = cxt.getSrcPacketLevel();
        boolean openParamLevel = this.paramLevelManager.isOpenParamLevel();
        JSONObject json_zbqueryInfo = new JSONObject(new String(types, "UTF-8"));
        ZBQueryInfo info = new ZBQueryInfo();
        info.fromJson(json_zbqueryInfo.getJSONObject(KEY_ZBQEURYINFO));
        if (info != null) {
            ResourceTreeNode node = new ResourceTreeNode();
            node.setGuid(info.getId());
            node.setTitle(info.getTitle());
            node.setType("com.jiuqi.nr.zbquery.manage");
            node.setFolder(false);
            node.setParent(folderGuid);
            node.setModifyTime(String.valueOf(info.getModifyTime()));
            if (openParamLevel && (!StringUtils.hasLength(info.getLevel()) || info.getLevel().equals("0"))) {
                info.setLevel(String.valueOf(srcPacketLevel));
            }
            if (StringUtils.hasText(targetGuid) && this.zbQueryInfoService.getQueryInfoById(targetGuid) != null) {
                info.setId(targetGuid);
                node.setGuid(info.getId());
                this.zbQueryInfoService.modifyQueryInfo(info);
                this.resourceTreeNodeDao.update(node);
            } else {
                this.zbQueryInfoService.addQueryInfo(info);
                this.resourceTreeNodeDao.insert(node);
            }
            byte[] modelDataByte = json_zbqueryInfo.getString(KEY_ZBQEURYDATA).getBytes(StandardCharsets.UTF_8);
            ZBQueryModel model = SerializeUtils.jsonDeserialize(modelDataByte);
            logger.info("\u5bfc\u5165\u7684model=" + new String(SerializeUtils.jsonSerializeToByte(model), StandardCharsets.UTF_8));
            if (model != null) {
                this.zbQueryInfoService.saveQueryInfoData(model, info.getId());
            }
        }
    }

    private byte[] doExport(String guid) throws Exception {
        JSONObject json_zbqueryInfo = new JSONObject();
        ZBQueryInfo info = this.zbQueryInfoService.getQueryInfoById(guid);
        if (info != null) {
            json_zbqueryInfo.put(KEY_ZBQEURYINFO, (Object)new JSONObject((Object)info));
            ZBQueryModel model = this.zbQueryInfoService.getQueryInfoData(guid);
            byte[] data = SerializeUtils.jsonSerializeToByte(model);
            json_zbqueryInfo.put(KEY_ZBQEURYDATA, (Object)new String(data, StandardCharsets.UTF_8));
        }
        return json_zbqueryInfo.toString().getBytes(StandardCharsets.UTF_8);
    }
}

