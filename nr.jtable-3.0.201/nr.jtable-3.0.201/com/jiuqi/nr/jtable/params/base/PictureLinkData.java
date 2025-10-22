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
 *  com.jiuqi.nr.attachment.utils.FileStatus
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.impl.FileInfoImpl
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.file.impl.FileInfoImpl;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PictureLinkData
extends LinkData {
    private static final Logger logger = LoggerFactory.getLogger(PictureLinkData.class);

    public PictureLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_PICTURE.getValue();
        this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, fieldDefine);
    }

    @Override
    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData;
        }
        String groupFileKey = data.getAsString();
        Map<String, List<com.jiuqi.nr.file.FileInfo>> fileDataMap = dataFormaterCache.getFileDataMap();
        Map<String, List<byte[]>> imgDataMap = dataFormaterCache.getImgDataMap();
        if (fileDataMap != null && null != imgDataMap && StringUtils.isNotEmpty((String)groupFileKey)) {
            ArrayList<FileInfoImpl> fileInfos;
            block26: {
                FileOperationService fileOperationService = (FileOperationService)BeanUtil.getBean(FileOperationService.class);
                IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
                ISecretLevelService secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
                JtableContext jtableContext = dataFormaterCache.getJtableContext();
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
                CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
                commonParamsDTO.setDataSchemeKey(taskDefine.getDataScheme());
                commonParamsDTO.setTaskKey(taskDefine.getKey());
                boolean secretEnable = secretLevelService.secretLevelEnable(dataFormaterCache.getJtableContext().getTaskKey());
                SecretLevelInfo secretLevel = null;
                if (secretEnable) {
                    secretLevel = secretLevelService.getSecretLevel(dataFormaterCache.getJtableContext());
                }
                List files = fileOperationService.getFileInfoByGroup(groupFileKey, commonParamsDTO);
                fileInfos = new ArrayList<FileInfoImpl>();
                try {
                    if (files == null || files.size() <= 0) break block26;
                    for (FileInfo fileInfo : files) {
                        boolean canAccess = true;
                        if (StringUtils.isNotEmpty((String)fileInfo.getSecretlevel()) && secretEnable && secretLevel != null) {
                            SecretLevelItem secretLevelItem = secretLevelService.getSecretLevelItem(fileInfo.getSecretlevel());
                            boolean bl = canAccess = secretLevelService.canAccess(secretLevelItem) && secretLevelService.compareSercetLevel(secretLevel.getSecretLevelItem(), secretLevelItem);
                        }
                        if (!canAccess) continue;
                        String path = fileOperationService.getFilePath(fileInfo.getKey(), NpContextHolder.getContext().getTenant(), commonParamsDTO);
                        byte[] textByte = path.getBytes("UTF-8");
                        FileInfoImpl ff = (FileInfoImpl)FileInfoBuilder.newFileInfo((String)fileInfo.getKey(), (String)fileInfo.getArea(), (String)fileInfo.getName(), (String)fileInfo.getExtension(), (long)fileInfo.getSize(), null, (String)fileInfo.getCreater(), (Date)fileInfo.getCreateTime(), (String)fileInfo.getLastModifier(), (Date)fileInfo.getLastModifyTime(), (int)fileInfo.getVersion(), (String)fileInfo.getFileGroupKey(), (String)Base64.getEncoder().encodeToString(textByte));
                        ff.setSecretlevel(fileInfo.getSecretlevel());
                        if (com.jiuqi.nr.attachment.utils.FileStatus.UNKNOW.equals((Object)fileInfo.getStatus())) {
                            ff.setStatus(FileStatus.UNKNOW);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.AVAILABLE.equals((Object)fileInfo.getStatus())) {
                            ff.setStatus(FileStatus.AVAILABLE);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.RECOVERABLE.equals((Object)fileInfo.getStatus())) {
                            ff.setStatus(FileStatus.RECOVERABLE);
                        } else if (com.jiuqi.nr.attachment.utils.FileStatus.DELETED.equals((Object)fileInfo.getStatus())) {
                            ff.setStatus(FileStatus.DELETED);
                        }
                        fileInfos.add(ff);
                        ArrayList<byte[]> imgDatas = new ArrayList<byte[]>();
                        try {
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            Throwable throwable = null;
                            try {
                                fileOperationService.downLoadThumbnail(fileInfo.getKey(), (OutputStream)os, commonParamsDTO);
                                byte[] downFile = os.toByteArray();
                                imgDatas.add(downFile);
                                imgDataMap.put(groupFileKey, imgDatas);
                            }
                            catch (Throwable throwable2) {
                                throwable = throwable2;
                                throw throwable2;
                            }
                            finally {
                                if (os == null) continue;
                                if (throwable != null) {
                                    try {
                                        os.close();
                                    }
                                    catch (Throwable throwable3) {
                                        throwable.addSuppressed(throwable3);
                                    }
                                    continue;
                                }
                                os.close();
                            }
                        }
                        catch (IOException e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                    }
                }
                catch (UnsupportedEncodingException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
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

