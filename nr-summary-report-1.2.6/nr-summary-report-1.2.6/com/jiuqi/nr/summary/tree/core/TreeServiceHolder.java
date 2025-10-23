/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.summary.tree.core;

import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;
import com.jiuqi.nr.summary.tree.core.ITreeService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TreeServiceHolder {
    private static final Logger logger = LoggerFactory.getLogger(TreeServiceHolder.class);
    @Autowired
    private List<ITreeService> treeServiceList;

    public ITreeService getTreeService(@NotNull String id) throws SummaryCommonException {
        if (!CollectionUtils.isEmpty(this.treeServiceList)) {
            List matchTreeServices = this.treeServiceList.stream().filter(treeService -> id.equals(treeService.getId())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(matchTreeServices)) {
                logger.error(String.format("\u6ca1\u6709id\u4e3a\u3010%s\u3011\u7684\u6811\u5f62\u670d\u52a1\uff0c\u8bf7\u68c0\u67e5", id));
                throw new SummaryCommonException(SummaryErrorEnum.TREE_LOAD_FAILED);
            }
            if (matchTreeServices.size() > 1) {
                logger.error(String.format("\u5b58\u5728\u91cd\u590d\u7684\u6811\u5f62\u670d\u52a1id\u3010%s\u3011\uff0c\u8bf7\u68c0\u67e5", id));
                throw new SummaryCommonException(SummaryErrorEnum.TREE_LOAD_FAILED);
            }
            return (ITreeService)matchTreeServices.get(0);
        }
        logger.error("\u6ca1\u6709\u6811\u5f62\u670d\u52a1\uff0c\u8bf7\u68c0\u67e5");
        throw new SummaryCommonException(SummaryErrorEnum.TREE_LOAD_FAILED);
    }
}

