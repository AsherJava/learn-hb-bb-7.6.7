/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.archive.api.scheme.GCArchiveClient
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveLogVO
 *  com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam
 *  com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData
 *  com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.archive.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.archive.api.scheme.GCArchiveClient;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveLogVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO;
import com.jiuqi.gcreport.archive.plugin.ArchivePlugin;
import com.jiuqi.gcreport.archive.plugin.ArchivePluginProvider;
import com.jiuqi.gcreport.archive.service.GcArchiveService;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@ResponseBody
@Primary
public class GCArchiveController
implements GCArchiveClient {
    private Logger logger = LoggerFactory.getLogger(GCArchiveController.class);
    @Autowired
    private GcArchiveService gcArchiveService;
    @Autowired
    private ArchivePluginProvider archivePluginProvider;

    public BusinessResponseEntity<FormTree> queryFormTree(String schemeId, String dataTime) {
        FormTree formTree = this.gcArchiveService.queryFormTree(schemeId, dataTime);
        return BusinessResponseEntity.ok((Object)formTree);
    }

    public BusinessResponseEntity<List<EFSResponseData>> cancelArchive(SendArchiveVO sendArchiveVO) {
        this.logger.info("\u53d6\u6d88\u5f52\u6863\u53c2\u6570\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)sendArchiveVO));
        return BusinessResponseEntity.ok(this.gcArchiveService.cancelArchive(sendArchiveVO));
    }

    public BusinessResponseEntity<Object> batchDoActionStart(String id) {
        this.gcArchiveService.batchDoActionStart(id);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<PageInfo<ArchiveLogVO>> batchLogQuery(ArchiveQueryParam param) {
        return BusinessResponseEntity.ok(this.gcArchiveService.batchLogQuery(param));
    }

    public BusinessResponseEntity<PageInfo<ArchiveInfoVO>> detailsLogQuery(ArchiveQueryParam param) {
        return BusinessResponseEntity.ok(this.gcArchiveService.detailsLogQuery(param));
    }

    public BusinessResponseEntity<PageInfo<ArchiveInfoVO>> detailsLogDelete(List<String> ids) {
        this.gcArchiveService.detailsLogDelete(ids);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<Object> batchDoActionSave(ArchiveContext context) {
        return BusinessResponseEntity.ok((Object)this.gcArchiveService.batchDoActionSave(context));
    }

    public BusinessResponseEntity<List<ArchiveConfigVO>> getArchiveConfig(String taskId) {
        return BusinessResponseEntity.ok(this.gcArchiveService.getArchiveConfig(taskId));
    }

    public BusinessResponseEntity<List<ArchiveConfigVO>> getArchiveConfigWithOrgType(String taskId, String orgType) {
        return BusinessResponseEntity.ok(this.gcArchiveService.getArchiveConfigWithOrgType(taskId, orgType));
    }

    public BusinessResponseEntity<String> saveArchiveConfig(String taskId, List<ArchiveConfigVO> archiveConfigVOs) {
        this.gcArchiveService.saveArchiveConfig(taskId, archiveConfigVOs);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<List<Map<String, String>>> listArchivePlugin() {
        List<ArchivePlugin> archivePluginList = this.archivePluginProvider.listArchivePlugin();
        if (CollectionUtils.isEmpty(archivePluginList)) {
            return BusinessResponseEntity.ok(Collections.emptyList());
        }
        ArrayList result = new ArrayList(archivePluginList.size());
        for (ArchivePlugin archivePlugin : archivePluginList) {
            HashMap<String, String> plugin = new HashMap<String, String>();
            plugin.put("value", archivePlugin.getPluginCode());
            plugin.put("label", archivePlugin.getPluginName());
            result.add(plugin);
        }
        return BusinessResponseEntity.ok(result);
    }
}

