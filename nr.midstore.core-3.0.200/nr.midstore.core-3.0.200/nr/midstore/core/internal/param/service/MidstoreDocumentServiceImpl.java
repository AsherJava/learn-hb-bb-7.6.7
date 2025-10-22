/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.Config
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEExtInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider
 *  com.jiuqi.bi.core.midstore.export.DataExchangeExporter
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package nr.midstore.core.internal.param.service;

import com.jiuqi.bi.core.midstore.dataexchange.Config;
import com.jiuqi.bi.core.midstore.dataexchange.model.DEExtInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider;
import com.jiuqi.bi.core.midstore.export.DataExchangeExporter;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.common.StorageModeType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.publish.service.MidstorePublishTaskServiceImpl;
import nr.midstore.core.param.service.IMidstoreDocumentService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.core.util.IMidstoreEncryptedFieldService;
import nr.midstore.core.util.IMidstoreFileServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreDocumentServiceImpl
implements IMidstoreDocumentService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishTaskServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;
    @Autowired
    private IMidstoreFileServcie fileServcie;
    @Autowired
    private IMidstoreEncryptedFieldService encryptedFieldService;

    @Override
    public MidstoreResultObject exportDocument(String schemeKey, AsyncTaskMonitor monitor) throws MidstoreException {
        if (monitor != null) {
            monitor.progressAndMessage(0.1, "\u4e0b\u8f7d\u5f00\u59cb");
        }
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        MidstoreSchemeDTO midstoreScheme = this.midstoreSchemeSevice.getByKey(schemeKey);
        IDataExchangeProvider priovider = this.exchangeTaskService.getExchangeProvider(midstoreScheme);
        if (priovider == null) {
            result.setMessage("\u83b7\u53d6\u4e2d\u95f4\u5e93\u63d0\u4f9b\u5931\u8d25");
            throw new MidstoreException("\u83b7\u53d6\u4e2d\u95f4\u5e93\u63d0\u4f9b\u5931\u8d25");
        }
        DataExchangeExporter exporter = new DataExchangeExporter();
        try {
            OutputStream stream = null;
            if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_DATABASE) {
                String infoText = this.encryptedFieldService.getExportEncrypeText(midstoreScheme);
                if (StringUtils.isNotEmpty((String)infoText)) {
                    ArrayList<DEExtInfo> extInfos = new ArrayList<DEExtInfo>();
                    DEExtInfo info = new DEExtInfo();
                    info.setText(infoText);
                    extInfos.add(info);
                    stream = exporter.exportDBMetaData(midstoreScheme.getTablePrefix(), priovider, extInfos);
                } else {
                    stream = exporter.exportDBMetaData(midstoreScheme.getTablePrefix(), priovider);
                }
            } else if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_FILE) {
                stream = exporter.exportFileMetaData(midstoreScheme.getTablePrefix(), midstoreScheme.getStorageInfo());
            } else {
                throw new MidstoreException("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a\u5b58\u50a8\u683c\u5f0f\u9519\u8bef");
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(stream.toString().getBytes("UTF-8"));
            String fileKey = this.fileServcie.uploadFile(midstoreScheme.getTitle() + "-\u89c4\u8303\u6587\u6863" + OrderGenerator.newOrder() + ".docx", bais);
            result.setResultKey(fileKey);
            MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoSevice.getBySchemeKey(schemeKey);
            if (schemeInfo != null) {
                schemeInfo.setDocumentKey(fileKey);
                this.schemeInfoSevice.update(schemeInfo);
            }
            logger.info("\u5bfc\u51fa\u6587\u6863\u5b8c\u6210");
            result.setSuccess(true);
            if (monitor != null) {
                monitor.progressAndMessage(0.98, "\u4e0b\u8f7d\u5b8c\u6210");
            }
        }
        catch (Exception e) {
            result.setMessage("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a" + e.getMessage());
            logger.error("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a" + e.getMessage(), e);
            throw new MidstoreException("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return result;
    }

    public static void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    @Override
    public MidstoreResultObject exportDocument(String schemeKey, OutputStream outputStream, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        MidstoreSchemeDTO midstoreScheme = this.midstoreSchemeSevice.getByKey(schemeKey);
        IDataExchangeProvider priovider = this.exchangeTaskService.getExchangeProvider(midstoreScheme);
        if (priovider == null) {
            result.setMessage("\u83b7\u53d6\u4e2d\u95f4\u5e93\u63d0\u4f9b\u5931\u8d25");
            throw new MidstoreException("\u83b7\u53d6\u4e2d\u95f4\u5e93\u63d0\u4f9b\u5931\u8d25");
        }
        Config config = this.exchangeTaskService.getExchangeConfig(midstoreScheme);
        DataExchangeExporter exporter = new DataExchangeExporter();
        try {
            OutputStream stream = null;
            if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_DATABASE) {
                String infoText = this.encryptedFieldService.getExportEncrypeText(midstoreScheme);
                if (StringUtils.isNotEmpty((String)infoText)) {
                    ArrayList<DEExtInfo> extInfos = new ArrayList<DEExtInfo>();
                    DEExtInfo info = new DEExtInfo();
                    info.setText(infoText);
                    extInfos.add(info);
                    stream = exporter.exportDBMetaData(midstoreScheme.getTablePrefix(), priovider, extInfos);
                } else {
                    stream = exporter.exportDBMetaData(midstoreScheme.getTablePrefix(), priovider);
                }
            } else if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_FILE) {
                stream = exporter.exportFileMetaData(midstoreScheme.getTablePrefix(), midstoreScheme.getStorageInfo());
            } else {
                throw new MidstoreException("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a\u5b58\u50a8\u683c\u5f0f\u9519\u8bef");
            }
            ByteArrayOutputStream baio = (ByteArrayOutputStream)stream;
            outputStream.write(baio.toByteArray());
            result.setSuccess(true);
            if (monitor != null) {
                monitor.progressAndMessage(0.98, "\u4e0b\u8f7d\u5b8c\u6210");
            }
        }
        catch (Exception e) {
            result.setMessage("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a" + e.getMessage());
            logger.error("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a" + e.getMessage(), e);
            throw new MidstoreException("\u5bfc\u51fa\u6587\u6863\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return result;
    }
}

