/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse
 *  com.jiuqi.nr.nrdx.adapter.param.common.ParamGuid
 */
package com.jiuqi.nr.nrdx.param.task.service;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxGuidParse;
import com.jiuqi.nr.nrdx.adapter.param.common.ParamGuid;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import com.jiuqi.nr.nrdx.param.task.AbstractParamTransfer;
import com.jiuqi.nr.nrdx.param.task.service.ITransParamService;
import com.jiuqi.nr.nrdx.param.task.service.ITransferModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TransParamServiceImpl
implements ITransParamService {
    private static Logger logger = LoggerFactory.getLogger(TransParamServiceImpl.class);
    private List<AbstractParamTransfer> abstractParamTransfers;
    private Map<String, AbstractParamTransfer> abstractParamTransferMap;

    @Autowired
    private void setGatherList(List<AbstractParamTransfer> abstractParamTransferList) {
        this.abstractParamTransfers = abstractParamTransferList;
        this.abstractParamTransferMap = abstractParamTransferList.stream().collect(Collectors.toMap(ITransferModel::code, a -> a, (k1, k2) -> k1));
    }

    @Override
    public List<ResItem> getRelatedBusiness(List<ResItem> items) {
        ArrayList<ResItem> resultList = new ArrayList<ResItem>();
        HashSet<String> guidSet = new HashSet<String>();
        for (int i = 0; i < items.size(); ++i) {
            ResItem resItem = items.get(i);
            List<ResItem> relatedBusiness = this.getRelatedBusinessForOne(guidSet, resItem.getGuid());
            if (CollectionUtils.isEmpty(relatedBusiness)) continue;
            items.addAll(relatedBusiness);
            resultList.addAll(relatedBusiness);
        }
        ArrayList<ResItem> result = new ArrayList<ResItem>();
        HashSet<String> resultSet = new HashSet<String>();
        for (ResItem resItem : resultList) {
            if (!resultSet.add(resItem.getGuid())) continue;
            result.add(resItem);
        }
        return result;
    }

    private List<ResItem> getRelatedBusinessForOne(Set<String> guidSet, String guid) {
        ArrayList<ResItem> thisResItems = new ArrayList<ResItem>();
        if (StringUtils.isEmpty((String)guid)) {
            return Collections.emptyList();
        }
        if (guidSet.add(guid)) {
            return Collections.emptyList();
        }
        ParamGuid parse = NrdxGuidParse.parse((String)guid);
        if (parse == null) {
            return Collections.emptyList();
        }
        AbstractParamTransfer abstractParamTransfer = this.abstractParamTransferMap.get(parse.getNrdxParamNodeType().name());
        if (abstractParamTransfer == null) {
            return Collections.emptyList();
        }
        switch (parse.getNrdxParamNodeType()) {
            case TASKGROUP: {
                break;
            }
            case TASK: {
                break;
            }
            case FORMSCHEME: {
                ParamDTO param = new ParamDTO("", parse.getKey());
                DepResource depResource = abstractParamTransfer.depResource(param, null, null);
                if (depResource == null || !CollectionUtils.isEmpty(depResource.getResItems())) break;
                thisResItems.addAll(depResource.getResItems());
                break;
            }
            case FORMGROUP: {
                break;
            }
            case FORM: {
                break;
            }
            case FORMULASCHEME: {
                break;
            }
            case FORMULAFORM: {
                break;
            }
            case PRINTSCHEME: {
                break;
            }
            case PRINTTEMPLATE: {
                break;
            }
        }
        return thisResItems;
    }
}

