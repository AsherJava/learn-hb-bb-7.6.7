/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.single.core.para.LinkTaskBean
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  nr.single.map.param.service.SingleParamFileService
 */
package nr.single.para.compare.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.single.core.para.LinkTaskBean;
import com.jiuqi.nr.single.core.para.ParaInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nr.single.map.param.service.SingleParamFileService;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.definition.CompareDataTaskLinkDTO;
import nr.single.para.compare.definition.ISingleCompareDataTaskLinkService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.util.CompareTypeMan;
import nr.single.para.compare.service.TaskLinkDefineCompareService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskLinkDefineCompareServiceImpl
implements TaskLinkDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(TaskLinkDefineCompareServiceImpl.class);
    @Autowired
    private ISingleCompareDataTaskLinkService taskLinkDataService;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private SingleParamFileService singleParamService;
    @Autowired
    private IParaImportCommonService paraCommonService;

    @Override
    public boolean compareTaskLinkDefine(ParaCompareContext compareContext) throws Exception {
        CompareDataTaskLinkDTO taskLinkQueryParam = new CompareDataTaskLinkDTO();
        taskLinkQueryParam.setInfoKey(compareContext.getComapreResult().getCompareId());
        taskLinkQueryParam.setDataType(CompareDataType.DATA_TASKLINK);
        List<CompareDataTaskLinkDTO> oldTaskLinkList = this.taskLinkDataService.list(taskLinkQueryParam);
        HashMap<String, CompareDataTaskLinkDTO> oldTaskLinkDic = new HashMap<String, CompareDataTaskLinkDTO>();
        for (CompareDataTaskLinkDTO oldData : oldTaskLinkList) {
            oldTaskLinkDic.put(oldData.getSingleLinkAlias(), oldData);
        }
        List netList = this.viewController.queryLinksByCurrentFormScheme(compareContext.getFormSchemeKey());
        CompareTypeMan compareLinkMan = new CompareTypeMan();
        if (netList != null) {
            for (DesignTaskLinkDefine data : netList) {
                CompareDataDO cData = compareLinkMan.addNetItem(data.getLinkAlias(), data.getTitle(), data.getKey());
                cData.setObjectValue("taskLinkDefine", data);
            }
        }
        List formShemes = this.viewController.queryAllFormSchemeDefine();
        ArrayList<CompareDataTaskLinkDTO> addItems = new ArrayList<CompareDataTaskLinkDTO>();
        ArrayList<CompareDataTaskLinkDTO> updateItems = new ArrayList<CompareDataTaskLinkDTO>();
        List taskLinkList = compareContext.getParaInfo().getTaskLinkList();
        for (LinkTaskBean singleLink : taskLinkList) {
            ParaInfo paraInfo;
            DesignTaskLinkDefine netTasklink = null;
            if (compareLinkMan.getNetCodeItems().containsKey(singleLink.getLinkNumber())) {
                netTasklink = (DesignTaskLinkDefine)compareLinkMan.getNetCodeItems().get(singleLink.getLinkNumber()).getObjectValue("taskLinkDefine");
            }
            CompareDataTaskLinkDTO dataItem = null;
            if (oldTaskLinkDic.containsKey(singleLink.getLinkNumber())) {
                dataItem = (CompareDataTaskLinkDTO)oldTaskLinkDic.get(singleLink.getLinkNumber());
                updateItems.add(dataItem);
            } else {
                dataItem = new CompareDataTaskLinkDTO();
                dataItem.setKey(UUID.randomUUID().toString());
                dataItem.setInfoKey(compareContext.getComapreResult().getCompareId());
                dataItem.setDataType(CompareDataType.DATA_TASKLINK);
                addItems.add(dataItem);
            }
            boolean isNew = netTasklink == null;
            dataItem.setSingleCode(singleLink.getLinkTaskFlag());
            dataItem.setSingleTitle(null);
            dataItem.setSingleLinkAlias(singleLink.getLinkNumber());
            dataItem.setSingleLinkExp(singleLink.getLinkFomula());
            dataItem.setSingleCurrentExp(singleLink.getCurFomula());
            dataItem.setOrder(OrderGenerator.newOrder());
            if (netTasklink != null) {
                dataItem.setNetKey(netTasklink.getKey());
                dataItem.setNetCode(netTasklink.getRelatedTaskCode());
                dataItem.setNetCurrentExp(netTasklink.getCurrentFormula());
                dataItem.setNetLinkExp(netTasklink.getRelatedFormula());
                dataItem.setNetTaskKey(netTasklink.getRelatedTaskKey());
                dataItem.setNetLinkAlias(netTasklink.getLinkAlias());
                dataItem.setNetFormSchemeKey(netTasklink.getRelatedFormSchemeKey());
                dataItem.setMatchKey(dataItem.getNetKey());
                dataItem.setUpdateType(CompareUpdateType.UPDATE_OVER);
                dataItem.setChangeType(CompareChangeType.CHANGE_FLAGSAME);
            } else {
                dataItem.setUpdateType(CompareUpdateType.UPDATE_NEW);
                dataItem.setChangeType(CompareChangeType.CHANGE_NOEXIST);
            }
            if (StringUtils.isEmpty((String)dataItem.getNetFormSchemeKey())) {
                ArrayList<DesignFormSchemeDefine> findFormSchemeList = new ArrayList<DesignFormSchemeDefine>();
                for (DesignFormSchemeDefine formScheme : formShemes) {
                    if (!StringUtils.isNotEmpty((String)singleLink.getLinkTaskFlag()) || !singleLink.getLinkTaskFlag().equalsIgnoreCase(formScheme.getTaskPrefix())) continue;
                    findFormSchemeList.add(formScheme);
                }
                if (findFormSchemeList.size() > 0) {
                    DesignFormSchemeDefine formScheme = (DesignFormSchemeDefine)findFormSchemeList.get(0);
                    dataItem.setNetTaskKey(formScheme.getTaskKey());
                    dataItem.setNetFormSchemeKey(formScheme.getKey());
                }
            }
            if (!StringUtils.isNotEmpty((String)dataItem.getNetTaskKey()) || !StringUtils.isNotEmpty((String)dataItem.getNetFormSchemeKey()) || (paraInfo = this.singleParamService.getSingleTaskInfo(dataItem.getNetTaskKey(), dataItem.getNetFormSchemeKey())) == null) continue;
            PeriodType periodType = this.paraCommonService.getTaskPeriod(paraInfo.getTaskType());
            dataItem.setSingleTaskType(periodType);
            dataItem.setSingleTaskYear(paraInfo.getTaskYear());
            dataItem.setSingleTitle(paraInfo.getTaskName());
        }
        if (addItems.size() > 0) {
            this.taskLinkDataService.batchAdd(addItems);
        }
        if (updateItems.size() > 0) {
            this.taskLinkDataService.batchUpdate(updateItems);
        }
        return true;
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        CompareDataTaskLinkDTO compareDataDTO = new CompareDataTaskLinkDTO();
        compareDataDTO.setInfoKey(compareKey);
        this.taskLinkDataService.delete(compareDataDTO);
    }
}

