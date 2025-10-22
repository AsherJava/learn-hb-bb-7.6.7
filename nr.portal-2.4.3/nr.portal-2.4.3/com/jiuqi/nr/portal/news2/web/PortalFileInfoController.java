/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.framework.nros.extend.log.LogProvider
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.portal.news2.web;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.service.IPortalFileService;
import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import com.jiuqi.nr.portal.news2.vo.ResultObject;
import com.jiuqi.nvwa.framework.nros.extend.log.LogProvider;
import io.swagger.annotations.Api;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"\u9996\u9875\u6587\u4ef6"})
@RestController
@RequestMapping(value={"/api/portal/file-info"})
public class PortalFileInfoController {
    private static final Logger logger = LoggerFactory.getLogger(PortalFileInfoController.class);
    @Autowired
    private IPortalFileService service;
    @Autowired
    private LogProvider logProvider;
    @Autowired
    public IQueryReadDao queryReadDao;
    public static final String MODULE_TITLE = "\u9996\u9875\u6587\u4ef6";

    @PostMapping(value={"save-file"})
    public ResultObject upLoadFile(@RequestBody FileImpl impl) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = this.service.saveFile(impl, true);
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage("\u4fdd\u5b58\u5931\u8d25");
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"modify-file"})
    public ResultObject modifyFile(@RequestBody FileImpl impl) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = this.service.modifyFile(impl);
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"addDownloadCount"})
    public Boolean addDownloadCount(String id) {
        try {
            FileImpl file = this.service.queryFileByid(id);
            if (file == null) {
                return false;
            }
            file.setDownLoadCount(file.getDownLoadCount() + 1);
            this.service.modifyFileRunning(file);
            this.logProvider.info(MODULE_TITLE, "\u4e0b\u8f7d\u6587\u4ef6", "\u6587\u4ef6\u540d\u79f0:" + file.getTitle());
            this.queryReadDao.addReadItem(NpContextHolder.getContext().getIdentityId(), file.getId(), "file");
            Boolean aBoolean = this.service.modifyFile(file);
            this.service.updateCache(file, "running");
            return aBoolean;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @PostMapping(value={"delete-file"})
    public ResultObject deleteFile(String id) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = this.service.deleteFileByFileId(id);
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"delete-group"})
    public ResultObject deleteFilesByGroup(String mid, String portalId, String type) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = this.service.deleteFileByMid(mid, portalId, type);
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"publish-group"})
    public ResultObject publishFilesByGroup(String mid, String portalId) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = this.service.publishFiles(mid, portalId);
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u64cd\u4f5c\u5931\u8d25\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @GetMapping(value={"query-files"})
    public List<FileImpl> queryFilesByGroup(String mid, String portalId, String type) {
        return this.service.queryFileByMidAndPortalId(mid, portalId, type);
    }

    @PostMapping(value={"modify-order"})
    public ResultObject modifyFileOrder(String id, Integer newOrder) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = this.service.modifyFileOrder(id, newOrder);
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u8bf7\u68c0\u67e5id\u662f\u5426\u5b58\u5728\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @PostMapping(value={"change-order"})
    public ResultObject changeFileOrder(String id1, String id2) {
        ResultObject result = new ResultObject();
        try {
            Boolean saveFile = false;
            FileImpl file1 = this.service.queryFileByid(id1);
            FileImpl file2 = this.service.queryFileByid(id2);
            if (file1 == null || file2 == null) {
                result.setMessage("\u8bf7\u68c0\u67e5id\u662f\u5426\u5b58\u5728\uff01");
                result.setState(saveFile);
                return result;
            }
            Integer order1 = file1.getOrder();
            Integer order2 = file2.getOrder();
            Boolean modifyFileOrder = this.service.modifyFileOrder(id1, order2);
            Boolean modifyFileOrder2 = this.service.modifyFileOrder(id2, order1);
            saveFile = modifyFileOrder != false && modifyFileOrder2 != false;
            result.setState(saveFile);
            if (!saveFile.booleanValue()) {
                result.setMessage("\u8bf7\u68c0\u67e5id\u662f\u5426\u5b58\u5728\uff01");
            }
        }
        catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }

    @GetMapping(value={"isExist"})
    public ResultObject isExistNews(String id) {
        ResultObject result = new ResultObject();
        try {
            Boolean isExist = this.service.queryFileByid(id) != null;
            result.setState(isExist);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setMessage(e.getMessage());
            result.setState(false);
        }
        return result;
    }
}

