/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.attachment.utils.FileStatus
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.util.AttachObj
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.util.AttachObj;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileLinkData
extends LinkData {
    private static final Logger logger = LoggerFactory.getLogger(FileLinkData.class);
    private List<String> types;
    private float size;
    private float minSize;
    private int num;
    private String templateKey;

    public FileLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_FILE.getValue();
        this.initDataLinkDefine(dataLinkDefine);
        this.style = DataLinkStyleUtil.getFileLinkStyle(dataLinkDefine, fieldDefine);
    }

    public FileLinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        super(dataLinkDefine, columnModelDefine);
        this.type = LinkType.LINK_TYPE_FILE.getValue();
        this.initDataLinkDefine(dataLinkDefine);
        this.style = DataLinkStyleUtil.getFileLinkStyle(dataLinkDefine, columnModelDefine);
    }

    private void initDataLinkDefine(DataLinkDefine dataLinkDefine) {
        try {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            AttachObj attachmentObj = runTimeViewController.getAttachment(this.key);
            this.types = new ArrayList<String>();
            if (attachmentObj != null) {
                this.templateKey = attachmentObj.getGroupKey();
                this.types.addAll(attachmentObj.getType());
                if (this.types.size() == 0) {
                    this.types.add("*");
                }
                this.size = StringUtils.isEmpty((String)attachmentObj.getMaxSize()) ? -1.0f : Float.parseFloat(attachmentObj.getMaxSize());
                this.minSize = StringUtils.isEmpty((String)attachmentObj.getMinSize()) ? -1.0f : Float.parseFloat(attachmentObj.getMinSize());
                this.num = StringUtils.isEmpty((String)attachmentObj.getMaxNumber()) ? -1 : Integer.parseInt(attachmentObj.getMaxNumber());
            } else {
                this.types.add("*");
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public float getSize() {
        return this.size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getMinSize() {
        return this.minSize;
    }

    public void setMinSize(float minSize) {
        this.minSize = minSize;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTemplateKey() {
        return this.templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    @Override
    public String getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData.toString();
        }
        String groupFileKey = data.getAsString();
        Map<String, List<com.jiuqi.nr.file.FileInfo>> fileDataMap = dataFormaterCache.getFileDataMap();
        if (fileDataMap != null && StringUtils.isNotEmpty((String)groupFileKey)) {
            FileOperationService fileOperationService = (FileOperationService)BeanUtil.getBean(FileOperationService.class);
            ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            FilePoolService filePoolService = (FilePoolService)BeanUtil.getBean(FilePoolService.class);
            TaskDefine taskDefine = runTimeViewController.queryTaskDefine(dataFormaterCache.getJtableContext().getTaskKey());
            CommonParamsDTO params = new CommonParamsDTO();
            params.setDataSchemeKey(taskDefine.getDataScheme());
            params.setTaskKey(taskDefine.getKey());
            List fileInfoByGroupAboutFilepool = filePoolService.getFileInfoByGroup(groupFileKey, false, params);
            ArrayList files = new ArrayList(fileInfoByGroupAboutFilepool);
            ArrayList<FileInfoImpl> fileInfos = new ArrayList<FileInfoImpl>();
            SecretLevelInfo secretLevel = null;
            boolean secretLevelEnable = secretLevelService.secretLevelEnable(dataFormaterCache.getJtableContext().getTaskKey());
            if (secretLevelEnable) {
                secretLevel = secretLevelService.getSecretLevel(dataFormaterCache.getJtableContext());
            }
            try {
                if (files.size() > 0) {
                    for (FileInfo file : files) {
                        boolean canAccess = true;
                        if (StringUtils.isNotEmpty((String)file.getSecretlevel()) && secretLevelEnable && secretLevel != null) {
                            SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(file.getSecretlevel());
                            boolean bl = canAccess = secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem) && secretLevelService.canAccess(secretLevelItem);
                        }
                        if (!canAccess) continue;
                        String path = fileOperationService.getFilePath(file.getKey(), NpContextHolder.getContext().getTenant(), params);
                        byte[] textByte = path.getBytes("UTF-8");
                        FileInfoImpl ff = (FileInfoImpl)FileInfoBuilder.newFileInfo((String)file.getKey(), (String)file.getArea(), (String)file.getName(), (String)file.getExtension(), (long)file.getSize(), null, (String)file.getCreater(), (Date)file.getCreateTime(), (String)file.getLastModifier(), (Date)file.getLastModifyTime(), (int)file.getVersion(), (String)file.getFileGroupKey(), (String)Base64.getEncoder().encodeToString(textByte));
                        ff.setSecretlevel(file.getSecretlevel());
                        if (com.jiuqi.nr.attachment.utils.FileStatus.UNKNOW.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.UNKNOW);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.AVAILABLE.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.AVAILABLE);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.RECOVERABLE.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.RECOVERABLE);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.DELETED.equals((Object)file.getStatus())) {
                            ff.setStatus(FileStatus.DELETED);
                        }
                        fileInfos.add(ff);
                    }
                }
            }
            catch (UnsupportedEncodingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            fileDataMap.put(groupFileKey, fileInfos);
        }
        return groupFileKey;
    }

    @Override
    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo) {
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        super.getData(value, dataFormaterCache, saveErrorDataInfo);
        if (value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) {
            return null;
        }
        return value.toString();
    }
}

