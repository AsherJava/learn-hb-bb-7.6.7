/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.des.LevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.zb.scheme.common.VersionStatus
 *  com.jiuqi.nr.zb.scheme.core.ZbScheme
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  io.swagger.annotations.Api
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check2.zbScheme;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.LevelLoader;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.system.check2.zbScheme.ZBErrorEnum;
import com.jiuqi.nr.system.check2.zbScheme.ZbSchemeReverseParam;
import com.jiuqi.nr.system.check2.zbScheme.ZbSchemeReverseVO;
import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import io.swagger.annotations.Api;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system_check2/zb_scheme"})
@Api(tags={"\u53c2\u6570\u68c0\u67e5"})
public class ZbSchemeReverseController {
    private static final Logger log = LoggerFactory.getLogger(ZbSchemeReverseController.class);
    private static final ErrorEnum ERROR = new ZBErrorEnum("");
    private static final ErrorEnum TREE_ERROR = new ZBErrorEnum("\u83b7\u53d6\u6307\u6807\u4f53\u7cfb\u6811\u5f62\u5931\u8d25");
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private LevelLoader levelLoader;
    @Autowired
    private SchemeNodeVisitor<ZbSchemeReverseParam> reverseVisitor;
    private static final int tableType = DataTableType.TABLE.getValue() | DataTableType.MD_INFO.getValue();

    @GetMapping(value={"/reverse/{zbSchemeKey}/{dataSchemeKey}"})
    @Transactional(rollbackFor={Exception.class})
    public void reverse(@PathVariable String zbSchemeKey, @PathVariable String dataSchemeKey) throws JQException {
        log.info("zbScheme:{},dataScheme:{}", (Object)zbSchemeKey, (Object)dataSchemeKey);
        Assert.notNull((Object)zbSchemeKey, "zbScheme is null");
        Assert.notNull((Object)dataSchemeKey, "dataScheme is null");
        List versions = this.zbSchemeService.listZbSchemeVersionByScheme(zbSchemeKey);
        ZbSchemeVersion zbSchemeVersion = (ZbSchemeVersion)versions.get(versions.size() - 1);
        this.check(dataSchemeKey, zbSchemeVersion);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        dataScheme.setUpdateTime(Instant.now());
        dataScheme.setZbSchemeKey(zbSchemeKey);
        dataScheme.setZbSchemeVersion(zbSchemeVersion.getKey());
        SchemeNode root = new SchemeNode(dataSchemeKey, NodeType.SCHEME.getValue());
        String rootKey = UUID.randomUUID().toString();
        ZbSchemeReverseParam zbSchemeReverseParam = new ZbSchemeReverseParam();
        zbSchemeReverseParam.setZbSchemeKey(zbSchemeKey);
        zbSchemeReverseParam.setDataSchemeKey(dataSchemeKey);
        zbSchemeReverseParam.setZbSchemeVersionKey(zbSchemeVersion.getKey());
        zbSchemeReverseParam.setKey(rootKey);
        root.setOther((Object)zbSchemeReverseParam);
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            this.levelLoader.walkDataSchemeTree(root, this.reverseVisitor);
            this.zbSchemeService.clearEmptyGroup(rootKey);
            this.designDataSchemeService.updateDataScheme(dataScheme);
            stopWatch.stop();
            log.info("\u6307\u6807\u4f53\u7cfb\u9006\u5411\u751f\u6210\u8017\u65f6\uff1a{}ms", (Object)stopWatch.getTotalTimeMillis());
        }
        catch (Exception e) {
            throw new JQException(ERROR, (Throwable)e);
        }
    }

    private void check(String dataScheme, ZbSchemeVersion zbSchemeVersion) throws JQException {
        if (zbSchemeVersion.getStatus() == VersionStatus.PUBLISHED) {
            throw new JQException(ERROR, "\u6307\u6807\u4f53\u7cfb\u5df2\u7ecf\u53d1\u5e03\uff0c\u4e0d\u652f\u6301\u9006\u5411\u751f\u6210");
        }
        List zbInfos = this.zbSchemeService.listZbInfoByVersion(zbSchemeVersion.getKey());
        if (!zbInfos.isEmpty()) {
            throw new JQException(ERROR, "\u6307\u6807\u4f53\u7cfb\u5b58\u5728\u6570\u636e\uff0c\u4e0d\u652f\u6301\u9006\u5411\u751f\u6210");
        }
        List dataTables = this.designDataSchemeService.getDataTableByScheme(dataScheme);
        for (DesignDataTable dataTable : dataTables) {
            if (dataTable.getDataTableType() == DataTableType.TABLE || dataTable.getDataTableType() == DataTableType.MD_INFO) continue;
            throw new JQException(ERROR, "\u6570\u636e\u65b9\u6848\u9006\u5411\u751f\u6210\u4ec5\u652f\u6301\u6307\u6807\u8868\u548c\u5355\u4f4d\u4fe1\u606f\u8868");
        }
    }

    @GetMapping(value={"/reverse/query_scheme_tree"})
    public ZbSchemeReverseVO querySchemeTree() {
        ZbSchemeReverseVO zbSchemeReverseVO = new ZbSchemeReverseVO();
        List zbSchemes = this.zbSchemeService.listAllZbScheme();
        zbSchemeReverseVO.setZbSchemes(zbSchemes);
        HashSet<String> filterZbSchemes = new HashSet<String>();
        HashSet<String> filterDataSchemes = new HashSet<String>();
        zbSchemeReverseVO.setFilterDataSchemes(filterDataSchemes);
        zbSchemeReverseVO.setFilterZbSchemes(filterZbSchemes);
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        block0: for (DesignDataScheme scheme : allDataScheme) {
            if (null != scheme.getZbSchemeKey()) {
                filterZbSchemes.add(scheme.getZbSchemeKey());
                filterDataSchemes.add(scheme.getKey());
            }
            List tables = this.designDataSchemeService.getAllDataTable(scheme.getKey());
            for (DesignDataTable table : tables) {
                if ((table.getDataTableType().getValue() & tableType) != 0) continue;
                log.info("scheme:{},table:{}", (Object)scheme.getTitle(), (Object)table.getDataTableType().getTitle());
                filterDataSchemes.add(scheme.getKey());
                continue block0;
            }
        }
        for (ZbScheme zbScheme : zbSchemes) {
            if (this.zbSchemeService.countZbByScheme(zbScheme.getKey()) <= 0) continue;
            filterZbSchemes.add(zbScheme.getKey());
        }
        return zbSchemeReverseVO;
    }
}

