/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.intermediatelibrary.api.IntermediateLibraryApi
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILTreeVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILVO
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerEntity
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.intermediatelibrary.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.intermediatelibrary.api.IntermediateLibraryApi;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition;
import com.jiuqi.gcreport.intermediatelibrary.enums.IntermediateLibraryEnums;
import com.jiuqi.gcreport.intermediatelibrary.job.IntermediateLibraryFactory;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryBulkService;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryTaskService;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryUnitService;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateProgrammeService;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldPageVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILTreeVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.ZbPickerEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class IntermediateLibraryController
implements IntermediateLibraryApi {
    @Autowired
    private IntermediateProgrammeService iPService;
    @Autowired
    private IntermediateLibraryTaskService iLTaskService;
    @Autowired
    private IntermediateLibraryUnitService iLUnitService;

    public BusinessResponseEntity<List<ILVO>> getAllProgramme() {
        return BusinessResponseEntity.ok(this.iPService.getAllProgramme());
    }

    public BusinessResponseEntity<ILVO> getProgrammeForId(ILCondition iLCondition) {
        return BusinessResponseEntity.ok((Object)this.iPService.getProgrammeForId(iLCondition));
    }

    public BusinessResponseEntity<Object> addProgramme(ILCondition intermediateLibraryCondition) {
        return BusinessResponseEntity.ok((Object)this.iPService.addProgramme(intermediateLibraryCondition));
    }

    public BusinessResponseEntity<Object> getProgrammeOfName(ILCondition iLCondition) {
        return BusinessResponseEntity.ok((Object)this.iPService.getProgrammeOfName(iLCondition));
    }

    public BusinessResponseEntity<Object> updateProgramme(ILCondition iLCondition) {
        this.iPService.updateProgramme(iLCondition);
        return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f");
    }

    public BusinessResponseEntity<Object> deleteProgramme(ILCondition iLCondition) {
        this.iPService.deleteProgramme(iLCondition);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<ILFieldPageVO> getFieldOfProgrammeId(ILFieldCondition iLFieldCondition) {
        ILFieldPageVO fieldPageVo = this.iPService.getFieldOfProgrammeId(iLFieldCondition);
        return BusinessResponseEntity.ok((Object)fieldPageVo);
    }

    public BusinessResponseEntity<Object> addProgrammeOfField(ILCondition intermediateLibraryCondition) {
        this.iPService.addProgrammeOfField(intermediateLibraryCondition, true);
        return BusinessResponseEntity.ok((Object)"\u65b0\u589e\u6307\u6807\u6210\u529f");
    }

    public BusinessResponseEntity<Object> deleteProgrammeOfField(ILCondition intermediateLibraryCondition) {
        this.iPService.deleteProgrammeOfField(intermediateLibraryCondition);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6307\u6807\u6210\u529f");
    }

    public BusinessResponseEntity<Object> deleteAllProgrammeOfField(ILCondition intermediateLibraryCondition) {
        this.iPService.deleteAllProgrammeOfField(intermediateLibraryCondition);
        return BusinessResponseEntity.ok((Object)"\u5168\u5220\u6307\u6807\u6210\u529f");
    }

    public BusinessResponseEntity<Object> programmeAsync(@RequestBody String paramJson) {
        return BusinessResponseEntity.ok((Object)this.iLTaskService.asyncTask(paramJson));
    }

    public BusinessResponseEntity<List<ILTreeVO>> getFieldGroupMessage(ILFieldCondition iLFieldCondition) {
        return BusinessResponseEntity.ok(this.iPService.getFieldGroupMessage(iLFieldCondition));
    }

    public BusinessResponseEntity<ILFieldPageVO> getFieldForGroup(ILFieldCondition iLFieldCondition) {
        return BusinessResponseEntity.ok((Object)this.iPService.getFieldForGroup(iLFieldCondition));
    }

    public BusinessResponseEntity<List<ZbPickerEntity>> getZbPickerList(ILFieldCondition iLFieldCondition) {
        return BusinessResponseEntity.ok(this.iPService.getZbPickerList(iLFieldCondition));
    }

    public BusinessResponseEntity<Map<String, Object>> getTaskInfo(@RequestParam String taskKey) {
        return BusinessResponseEntity.ok(this.iLUnitService.getTaskInfo(taskKey));
    }

    public BusinessResponseEntity<List<Map<String, String>>> getAllSourceType() {
        ArrayList sourceTypeList = new ArrayList();
        HashMap<String, String> libraryMap = new HashMap<String, String>();
        libraryMap.put("value", IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getValue());
        libraryMap.put("label", IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_SOURCE_TYPE.getLabel());
        sourceTypeList.add(libraryMap);
        IntermediateLibraryBulkService service = IntermediateLibraryFactory.getInstance().getIntermediateLibraryBulkService();
        if (service != null) {
            HashMap<String, String> otherLibraryMap = new HashMap<String, String>();
            otherLibraryMap.put("value", IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_OTHER_TYPE.getValue());
            otherLibraryMap.put("label", IntermediateLibraryEnums.INTERMEDIATE_LIBRARY_OTHER_TYPE.getLabel());
            sourceTypeList.add(otherLibraryMap);
        }
        return BusinessResponseEntity.ok(sourceTypeList);
    }
}

