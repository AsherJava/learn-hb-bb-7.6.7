/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  nr.single.map.data.TaskDataContext
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.client.internal.service.upload.notice;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import java.util.Map;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.bean.UploadJioNoticeParam;
import nr.single.client.service.upload.extend.IUploadJioNoticeService;
import nr.single.client.service.upload.notice.IUploadJioNoticeManageService;
import nr.single.map.data.TaskDataContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadJioNoticeManageServiceImpl
implements IUploadJioNoticeManageService {
    private static final Logger log = LoggerFactory.getLogger(UploadJioNoticeManageServiceImpl.class);
    @Autowired(required=false)
    private Map<String, IUploadJioNoticeService> uploadNoticeServiceMap;
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private IRunTimeViewController runtimeView;

    @Override
    public void doBeforeImportNotice(String taskKey, String formSchemeKey, UploadParam param) {
        if (this.uploadNoticeServiceMap == null || this.uploadNoticeServiceMap.isEmpty()) {
            return;
        }
        log.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u5f00\u59cb\uff0c\u901a\u77e5\u4e2a\u6570:" + this.uploadNoticeServiceMap.size());
        for (Map.Entry<String, IUploadJioNoticeService> entry : this.uploadNoticeServiceMap.entrySet()) {
            IUploadJioNoticeService noticeService = entry.getValue();
            int kind = noticeService.getNotitykind();
            String serviceInfo = "\u5bfc\u5165JIO\u6570\u636e\uff1a\u5f00\u59cb\uff0c\u901a\u77e5\uff1a" + entry.getKey() + ",\u901a\u77e5\u5bf9\u8c61\uff1a" + noticeService.getNoticeName() + "\uff0c\u57f7\u884c\u65b9\u5f0f:" + kind;
            log.info(serviceInfo);
            UploadJioNoticeParam noticeParam = new UploadJioNoticeParam();
            noticeParam.setFormSchemeKey(formSchemeKey);
            noticeParam.setTaskKey(taskKey);
            noticeParam.setConfigKey(param.getConfigKey());
            noticeParam.setSuccess(true);
            noticeParam.setState(0);
            if (noticeService.getNotitykind() == 1) {
                noticeService.beforeImportNotice(noticeParam);
                continue;
            }
            this.noticeAsync(noticeService, noticeParam, 0);
        }
    }

    @Override
    public void doAfterImportNotice(TaskDataContext context, JIOImportResultObject res, List<String> netImportPeriods) {
        if (this.uploadNoticeServiceMap == null || this.uploadNoticeServiceMap.isEmpty()) {
            return;
        }
        log.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u7ed3\u675f\uff0c\u901a\u77e5\u4e2a\u6570:" + this.uploadNoticeServiceMap.size());
        for (Map.Entry<String, IUploadJioNoticeService> entry : this.uploadNoticeServiceMap.entrySet()) {
            IUploadJioNoticeService noticeService = entry.getValue();
            int kind = noticeService.getNotitykind();
            String serviceInfo = "\u5bfc\u5165JIO\u6570\u636e\uff1a\u7ed3\u675f\uff0c\u901a\u77e5\uff1a" + entry.getKey() + ",\u901a\u77e5\u5bf9\u8c61\uff1a" + noticeService.getNoticeName() + "\uff0c\u57f7\u884c\u65b9\u5f0f:" + kind + "\uff0c\u65f6\u671f\uff1a" + netImportPeriods != null ? netImportPeriods.toString() : "";
            log.info(serviceInfo);
            UploadJioNoticeParam noticeParam = new UploadJioNoticeParam();
            noticeParam.setFormSchemeKey(context.getFormSchemeKey());
            noticeParam.setTaskKey(context.getTaskKey());
            noticeParam.setConfigKey(context.getConfigKey());
            noticeParam.setSuccess(res.isSuccess());
            noticeParam.setMessage(res.getMessage());
            noticeParam.setState(1);
            noticeParam.setOrgDefineCode(this.getOrgDefineCode(context.getTaskKey(), context.getFormSchemeKey()));
            if (netImportPeriods != null && !netImportPeriods.isEmpty()) {
                noticeParam.getPeriodCodes().addAll(netImportPeriods);
            }
            if (noticeService.getNotitykind() == 1) {
                noticeService.aftterImportNotice(noticeParam);
                continue;
            }
            this.noticeAsync(noticeService, noticeParam, 1);
        }
    }

    public void noticeAsync(IUploadJioNoticeService noticeService, UploadJioNoticeParam noticeParam, int noticeType) {
        this.npApplication.asyncRun(() -> {
            try {
                if (noticeType == 0) {
                    noticeService.beforeImportNotice(noticeParam);
                } else if (noticeType == 1) {
                    noticeService.aftterImportNotice(noticeParam);
                } else {
                    noticeService.importNotice(noticeParam);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    private String getOrgDefineCode(String taskKey, String formSchemeKey) {
        String result = null;
        String queryTaskKey = taskKey;
        if (StringUtils.isEmpty((CharSequence)queryTaskKey)) {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
            queryTaskKey = formScheme.getTaskKey();
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(queryTaskKey);
        String orgCode = EntityUtils.getId((String)taskDefine.getDw());
        String idType = EntityUtils.getCategory((String)taskDefine.getDw());
        if ("ORG".equalsIgnoreCase(idType)) {
            result = orgCode;
        }
        return result;
    }
}

