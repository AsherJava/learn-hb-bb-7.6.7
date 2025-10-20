/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorArgument
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 *  com.jiuqi.gcreport.monitor.api.inf.RouterRedirect
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityImpl
 *  com.jiuqi.nr.bpm.businesskey.MasterEntityInfo
 *  com.jiuqi.nr.bpm.businesskey.MasterEntitySet
 *  com.jiuqi.nr.bpm.businesskey.MasterEntitySetImpl
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.monitor.impl.scene;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorArgument;
import com.jiuqi.gcreport.monitor.api.inf.MonitorScene;
import com.jiuqi.gcreport.monitor.api.inf.RouterRedirect;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetImpl;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SubmittedScene
implements MonitorScene {
    private static Logger LOGGER = LoggerFactory.getLogger(SubmittedScene.class);

    public MonitorSceneEnum getMonitorScene() {
        return MonitorSceneEnum.NODE_SUBMITTED;
    }

    public MonitorStateEnum getState(MonitorArgument argument) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return MonitorStateEnum.UPLOAD_IS;
        }
        return MonitorStateEnum.UPLOAD_NOT;
    }

    public Map<String, MonitorStateEnum> getStates(MonitorArgument argument) {
        IBatchQueryUploadStateService uploadStateService = (IBatchQueryUploadStateService)SpringContextUtils.getBean(IBatchQueryUploadStateService.class);
        DimensionValueSet dimSet = new DimensionValueSet();
        dimSet.setValue("DATATIME", (Object)argument.getPeriodStr());
        String adjustCode = StringUtils.isEmpty((String)argument.getAdjustCode()) ? "0" : argument.getAdjustCode();
        dimSet.setValue("ADJUST", (Object)adjustCode);
        String tableName = argument.getOrgType();
        LOGGER.info("\u5355\u4f4d\u7c7b\u578b\uff1a" + tableName + "\u65f6\u671f" + argument.getPeriodStr() + "\n");
        dimSet.setValue(tableName, (Object)argument.getOrgIds());
        IRunTimeViewController authViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = null;
        try {
            formScheme = authViewController.getFormScheme(argument.getSchemeId());
        }
        catch (Exception e1) {
            e1.printStackTrace();
            throw new RuntimeException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5931\u8d25:" + e1.getMessage());
        }
        LOGGER.info("\u62a5\u8868\u65b9\u6848id\uff1a" + formScheme.getKey() + "\n");
        Map uploadStateMap = uploadStateService.queryUploadStates(dimSet, new ArrayList(), new ArrayList(), formScheme);
        Optional first = uploadStateMap.entrySet().stream().findFirst();
        boolean present = first.isPresent();
        String name = "";
        if (present) {
            int size = ((DimensionValueSet)((Map.Entry)first.get()).getKey()).size();
            for (int i = 0; i < size; ++i) {
                boolean mdOrg = ((DimensionValueSet)((Map.Entry)first.get()).getKey()).getName(i).startsWith("MD_ORG");
                if (!mdOrg) continue;
                name = ((DimensionValueSet)((Map.Entry)first.get()).getKey()).getName(i);
                break;
            }
        }
        String finalName = name;
        HashMap stateMap = new HashMap();
        for (DimensionValueSet dimension : uploadStateMap.keySet()) {
            LOGGER.info("\u7ef4\u5ea6\u4e3a\uff1a" + dimension + "\n");
            String orgCode = StringUtils.toViewString((Object)dimension.getValue(finalName));
            if (stateMap.containsKey(orgCode)) {
                LOGGER.info("\u5f53\u524d\u7ef4\u5ea6\u5bf9\u5e94\u5355\u4f4d\u4ee3\u7801\u91cd\u590d");
                if (null == dimension.getValue("FORMID") || !dimension.getValue("FORMID").equals("11111111-1111-1111-1111-111111111111")) continue;
                stateMap.put(orgCode, uploadStateMap.get(dimension));
                continue;
            }
            stateMap.put(orgCode, uploadStateMap.get(dimension));
        }
        try {
            Map<String, MonitorStateEnum> stateEnumMap = argument.getOrgIds().stream().collect(Collectors.toMap(orgid -> orgid, s -> {
                String code = s;
                UploadState uploadState = stateMap.get(code) == null ? UploadState.ORIGINAL : UploadState.valueOf((String)((ActionStateBean)stateMap.get(code)).getCode());
                boolean submit = uploadState.equals((Object)UploadState.UPLOADED) || uploadState.equals((Object)UploadState.CONFIRMED) || uploadState.equals((Object)UploadState.SUBMITED);
                boolean returned = uploadState.equals((Object)UploadState.RETURNED);
                boolean rejected = uploadState.equals((Object)UploadState.REJECTED);
                if (submit) {
                    return MonitorStateEnum.SUBMITTED_IS;
                }
                if (returned) {
                    return MonitorStateEnum.RETURNED_NOT_IS;
                }
                if (rejected) {
                    return MonitorStateEnum.REJECT_NOT_IS;
                }
                return MonitorStateEnum.SUBMITTED_NOT;
            }));
            return stateEnumMap;
        }
        catch (IllegalStateException e) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u91cd\u590d");
        }
    }

    public RouterRedirect getURL(MonitorArgument argument) {
        return null;
    }

    private MasterEntitySet buildMasterEntitySet(MonitorArgument argument) {
        MasterEntitySetImpl masterEntitySet = new MasterEntitySetImpl();
        argument.getOrgIds().forEach(arg_0 -> SubmittedScene.lambda$buildMasterEntitySet$2(argument, (MasterEntitySet)masterEntitySet, arg_0));
        return masterEntitySet;
    }

    public int getOrder() {
        return 2;
    }

    private static /* synthetic */ void lambda$buildMasterEntitySet$2(MonitorArgument argument, MasterEntitySet masterEntitySet, String s) {
        MasterEntityImpl masterEntity = new MasterEntityImpl();
        masterEntity.setMasterEntityDimessionValue("MD_ORG_" + argument.getOrgType(), s);
        masterEntitySet.addMasterEntity((MasterEntityInfo)masterEntity);
    }
}

