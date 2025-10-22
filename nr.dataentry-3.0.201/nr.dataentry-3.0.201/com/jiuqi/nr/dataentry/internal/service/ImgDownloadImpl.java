/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.excel.extend.ImgDownload
 *  com.jiuqi.nr.data.excel.obj.ExcelExportEnv
 *  com.jiuqi.nr.data.excel.obj.FileData
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.FileUtil
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.excel.extend.ImgDownload;
import com.jiuqi.nr.data.excel.obj.ExcelExportEnv;
import com.jiuqi.nr.data.excel.obj.FileData;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FileUtil;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ImgDownloadImpl
implements ImgDownload {
    public FileData getImg(String imgFieldData, ExcelExportEnv env) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)env.getDimensionCombination().toDimensionValueSet()));
        jtableContext.setTaskKey(env.getTaskKey());
        jtableContext.setFormSchemeKey(env.getFormSchemeKey());
        List fileInfos = FileUtil.getFileInfos((JtableContext)jtableContext, (String)imgFieldData);
        if (!CollectionUtils.isEmpty(fileInfos)) {
            FileInfo fileInfo = (FileInfo)fileInfos.get(0);
            FileData fileData = new FileData();
            byte[] bytes = FileUtil.downFile((String)fileInfo.getKey(), (String)jtableContext.getTaskKey());
            fileData.setData(bytes);
            fileData.setFileInfo(fileInfo);
            return fileData;
        }
        return null;
    }
}

