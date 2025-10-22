/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.AccessParam
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessResult;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessParam;
import com.jiuqi.nr.dataentry.service.IReadWriteAccessService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadWriteAccessServiceImpl
implements IReadWriteAccessService {
    private static final Logger logger = LoggerFactory.getLogger(ReadWriteAccessServiceImpl.class);
    @Autowired
    private List<IDataExtendAccessItemService> readWriteAccesss;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;

    @Override
    public ReadWriteAccessResult getReadWriteAccess(ReadWriteAccessParam param) {
        ReadWriteAccessResult result = new ReadWriteAccessResult();
        HashMap<String, Object> status = new HashMap<String, Object>();
        boolean readable = true;
        boolean writeable = true;
        String unreadableDesc = "";
        String unwriteableDesc = "";
        Consts.ReadWriteAccessLevel type = Consts.ReadWriteAccessLevel.TASK;
        String name = "";
        try {
            for (IDataExtendAccessItemService access : this.readWriteAccesss) {
                if (!access.isServerAccess()) continue;
                ReadWriteAccessItem item = new ReadWriteAccessItem();
                item.setName(access.name());
                item.setIsReal(false);
                item.setParams(param.getContext());
                item.setReadable(true);
                item.setWriteable(true);
                item.setType(Consts.ReadWriteAccessLevel.FORM);
                param.getItems().add(item);
            }
            JtableContext jtableContext = param.getContext();
            Map dimensionSet = jtableContext.getDimensionSet();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
            DimensionCollection dimeCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((DimensionValueSet)dimensionValueSet, (String)jtableContext.getFormSchemeKey());
            AccessParam accessParam = this.initParam(param);
            IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
            DimensionCombination collection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)dimensionValueSet, (String)jtableContext.getFormSchemeKey());
            IAccessResult accessResult = dataAccessService.visible(accessParam, collection, jtableContext.getFormKey());
            result.setStatus(dataAccessService.getExtendResult(accessParam, collection, jtableContext.getFormKey()));
            if (!accessResult.haveAccess()) {
                result.setReadable(false);
                result.setWriteable(false);
                result.setUnreadableDesc(accessResult.getMessage());
                return result;
            }
            accessResult = dataAccessService.readable(accessParam, collection, jtableContext.getFormKey());
            if (!accessResult.haveAccess()) {
                result.setReadable(false);
                result.setWriteable(false);
                result.setUnreadableDesc(accessResult.getMessage());
                return result;
            }
            result.setReadable(true);
            accessResult = dataAccessService.writeable(accessParam, collection, jtableContext.getFormKey());
            if (!accessResult.haveAccess()) {
                result.setWriteable(false);
                result.setUnwriteableDesc(accessResult.getMessage());
                return result;
            }
            result.setWriteable(true);
            return result;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            result.setStatus(status);
            result.setReadable(false);
            result.setWriteable(false);
            result.setType(type);
            if (e instanceof JTableException) {
                String[] datas = ((JTableException)e).getDatas();
                if (datas.length > 0) {
                    result.setUnreadableDesc(datas[0]);
                    result.setUnwriteableDesc(datas[0]);
                }
            } else {
                result.setUnreadableDesc(e.getMessage());
                result.setUnwriteableDesc(e.getMessage());
            }
            result.setName(name);
            return result;
        }
    }

    private AccessParam initParam(ReadWriteAccessParam param) {
        AccessParam accessParam = new AccessParam();
        List<ReadWriteAccessItem> items = param.getItems();
        Optional<ReadWriteAccessItem> workFlowItem = items.stream().filter(e -> e.getName().equals("upload")).findAny();
        if (workFlowItem.isPresent()) {
            Map map = (Map)workFlowItem.get().getParams();
            map.put("context", param.getContext());
            accessParam.getItems().add(new AccessItem("uploadTimeSetting", (Object)param.getContext()));
        }
        items.stream().forEach(item -> accessParam.getItems().add(new AccessItem(item.getName(), item.getParams() == null ? param.getContext() : item.getParams())));
        return accessParam;
    }
}

