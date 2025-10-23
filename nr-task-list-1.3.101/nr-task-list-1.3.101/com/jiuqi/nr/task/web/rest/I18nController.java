/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.task.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.vo.I18nExportVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nFormSaveVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitExtendResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nInitVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nQueryVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nResultVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nTreeLoactedVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nTreeSearchVO;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import com.jiuqi.nr.task.i18n.exception.I18nExceptionEnum;
import com.jiuqi.nr.task.i18n.service.I18nIOService;
import com.jiuqi.nr.task.i18n.service.I18nService;
import com.jiuqi.nr.task.i18n.service.I18nTreeService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/task/i18n"})
public class I18nController {
    private static final Logger logger = LoggerFactory.getLogger(I18nController.class);
    @Autowired
    private I18nService i18nService;
    @Autowired
    private I18nTreeService treeService;
    @Autowired
    private I18nIOService i18nIOService;

    @ApiOperation(value="\u521d\u59cb\u5316I18n\u914d\u7f6e")
    @GetMapping(value={"/init"})
    public I18nInitVO init() {
        return this.i18nService.init();
    }

    @ApiOperation(value="\u6811\u7684\u641c\u7d22")
    @PostMapping(value={"/tree/search"})
    public List<I18nBaseObj> treeSearch(@RequestBody I18nTreeSearchVO searchVO) {
        return this.treeService.treeSearch(searchVO);
    }

    @ApiOperation(value="\u6811\u7684\u5b9a\u4f4d")
    @PostMapping(value={"/tree/loacted"})
    public List<UITreeNode<TreeData>> treeLocated(@RequestBody I18nTreeLoactedVO loactedVO) {
        return this.treeService.treeLocated(loactedVO);
    }

    @ApiOperation(value="\u9012\u8fdb\u67e5\u8be2\u6761\u4ef6\u914d\u7f6e\u63a5\u53e3")
    @PostMapping(value={"/init/extend"})
    public I18nInitExtendResultVO initExtend(@RequestBody I18nInitExtendQueryVO conditionQueryVO) {
        return this.i18nService.initExtend(conditionQueryVO);
    }

    @ApiOperation(value="\u67e5\u8be2I18n\u914d\u7f6e")
    @PostMapping(value={"/query"})
    public I18nResultVO query(@RequestBody I18nQueryVO queryVO) throws JQException {
        try {
            return this.i18nService.query(queryVO);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5931\u8d25", e);
            throw new JQException((ErrorEnum)I18nExceptionEnum.I18N_QUERY_EXCEPTION, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fdd\u5b58I18n\u914d\u7f6e")
    @PostMapping(value={"/save"})
    public void save(@RequestBody I18nResultVO resultVO) throws JQException {
        try {
            this.i18nService.save(resultVO);
        }
        catch (I18nException e) {
            logger.error("\u4fdd\u5b58\u5931\u8d25", e);
            throw new JQException((ErrorEnum)I18nExceptionEnum.I18N_SAVE_EXCEPTION, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fdd\u5b58I18n\u8868\u6837\u914d\u7f6e")
    @PostMapping(value={"/style/save"})
    public void styleSave(@RequestBody @SFDecrypt I18nFormSaveVO saveVO) throws JQException {
        try {
            this.i18nService.styleSave(saveVO);
        }
        catch (I18nException e) {
            logger.error("\u4fdd\u5b58\u5931\u8d25", e);
            throw new JQException((ErrorEnum)I18nExceptionEnum.I18N_SAVE_EXCEPTION, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bfc\u5165I18n\u914d\u7f6e")
    @PostMapping(value={"/upload"})
    public String uploadI18n(@RequestParam(value="file") MultipartFile file) throws JQException {
        try {
            return this.i18nIOService.i18nUpload(file);
        }
        catch (I18nException e) {
            logger.error("\u5bfc\u5165\u5931\u8d25", e);
            throw new JQException((ErrorEnum)I18nExceptionEnum.I18N_IMPORT_EXCEPTION, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bfc\u5165I18n\u914d\u7f6e")
    @GetMapping(value={"/import/{fileKey}"})
    public void importI18n(@PathVariable String fileKey) throws JQException {
        try {
            this.i18nIOService.i18nImport(fileKey);
        }
        catch (I18nException e) {
            logger.error("\u5bfc\u5165\u5931\u8d25", e);
            throw new JQException((ErrorEnum)I18nExceptionEnum.I18N_IMPORT_EXCEPTION, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bfc\u51faI18n\u914d\u7f6e")
    @PostMapping(value={"/export"})
    public void exportI18n(@RequestBody I18nExportVO exportVO, HttpServletResponse res) throws JQException {
        try {
            this.i18nIOService.i18nExport(exportVO, res);
        }
        catch (I18nException e) {
            logger.error("\u5bfc\u51fa\u5931\u8d25", e);
            throw new JQException((ErrorEnum)I18nExceptionEnum.I18N_EXPORT_EXCEPTION, e.getMessage());
        }
    }

    @ApiOperation(value="\u591a\u8bed\u8a00\u53d1\u5e03")
    @GetMapping(value={"/deploy"})
    public void deploy() throws JQException {
        try {
            this.i18nService.deploy();
        }
        catch (I18nException e) {
            logger.error("\u53d1\u5e03\u5931\u8d25", e);
            throw new JQException((ErrorEnum)I18nExceptionEnum.I18N_DEPLOY_EXCEPTION, e.getMessage());
        }
    }
}

