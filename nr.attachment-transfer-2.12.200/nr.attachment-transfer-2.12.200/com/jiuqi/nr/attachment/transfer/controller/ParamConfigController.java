/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.itree.ITree
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.attachment.transfer.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.attachment.transfer.dto.TaskParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.TransferTreeDTO;
import com.jiuqi.nr.attachment.transfer.dto.WorkSpaceDTO;
import com.jiuqi.nr.attachment.transfer.exception.ConfigException;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.service.IParamQueryService;
import com.jiuqi.nr.attachment.transfer.service.IWorkSpaceService;
import com.jiuqi.nr.common.itree.ITree;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"/api/v1/attachment-transfer/config"})
public class ParamConfigController {
    @Autowired
    private IWorkSpaceService workSpaceService;
    @Autowired
    private IParamQueryService paramQueryService;

    @PostMapping(value={"/save"})
    public void save(@RequestBody WorkSpaceDTO workSpaceDTO) throws JQException {
        LogHelper.info((String)"\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa", (String)"\u4fdd\u5b58\u914d\u7f6e", (String)String.format("\u914d\u7f6e\u4fe1\u606f: \u6700\u5927\u7ebf\u7a0b\u6570\uff1a%s, \u8def\u5f84\uff1a%s, \u7a7a\u95f4\u5927\u5c0f:%s, \u7c7b\u578b:%s, url:%s", workSpaceDTO.getThread(), workSpaceDTO.getFilePath(), workSpaceDTO.getSpaceSize(), workSpaceDTO.getType(), workSpaceDTO.getUrl()));
        try {
            this.workSpaceService.save(workSpaceDTO);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ConfigException.FAILED_SAVE, e.getMessage());
        }
    }

    @PostMapping(value={"/update"})
    public void update(@RequestBody WorkSpaceDTO workSpaceDTO) throws JQException {
        LogHelper.info((String)"\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa", (String)"\u66f4\u65b0\u914d\u7f6e", (String)String.format("\u914d\u7f6e\u4fe1\u606f: \u6700\u5927\u7ebf\u7a0b\u6570\uff1a%s, \u8def\u5f84\uff1a%s, \u7a7a\u95f4\u5927\u5c0f:%s, \u7c7b\u578b:%s, url:%s", workSpaceDTO.getThread(), workSpaceDTO.getFilePath(), workSpaceDTO.getSpaceSize(), workSpaceDTO.getType(), workSpaceDTO.getUrl()));
        try {
            this.workSpaceService.update(workSpaceDTO);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ConfigException.FAILED_UPDATE, e.getMessage());
        }
    }

    @GetMapping(value={"/getGenerate"})
    public WorkSpaceDTO getGenerate() {
        return this.workSpaceService.getConfig(1);
    }

    @GetMapping(value={"/getImport"})
    public WorkSpaceDTO getImport() {
        return this.workSpaceService.getConfig(2);
    }

    @GetMapping(value={"/task/{taskKey}"})
    public List<ITree<TransferTreeDTO>> getTask(@PathVariable String taskKey) {
        return this.paramQueryService.getTask(taskKey);
    }

    @GetMapping(value={"/mapping/{formScheme}"})
    public List<ITree<TransferTreeDTO>> getMapping(@PathVariable String formScheme, @RequestParam String mapping) {
        return this.paramQueryService.getMapping(formScheme, mapping);
    }

    @GetMapping(value={"/init/{task}"})
    public TaskParamDTO initParam(@PathVariable String task, @RequestParam String period) {
        return this.paramQueryService.getTaskParam(task, period);
    }
}

