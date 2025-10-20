/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDimensionFilter
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.web.rest.param.DimensionFilterPM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
public class DimensionFilterController {
    private final IDesignTimeViewController designTimeViewController;

    public DimensionFilterController(IDesignTimeViewController designTimeViewController) {
        this.designTimeViewController = designTimeViewController;
    }

    @GetMapping(value={"dimension/filter/{taskKey}/{entityId}"})
    public List<String> getDimensionFilters(@PathVariable String taskKey, @PathVariable String entityId) {
        DesignDimensionFilter designDimensionFilter;
        if (StringUtils.hasLength(taskKey) && StringUtils.hasLength(entityId) && (designDimensionFilter = this.designTimeViewController.getDimensionFilterByTaskKey(taskKey, entityId)) != null) {
            return designDimensionFilter.getList();
        }
        return Collections.emptyList();
    }

    @PostMapping(value={"/dimension/filter/save"})
    public void saveDimensionFilters(@RequestBody DimensionFilterPM filterPM) throws JQException {
        Map valueMap;
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<String, ArrayList<String>>> ref = new TypeReference<HashMap<String, ArrayList<String>>>(){};
        try {
            valueMap = (Map)objectMapper.readValue(filterPM.getValues(), (TypeReference)ref);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_168, (Throwable)e);
        }
        ArrayList<DesignDimensionFilter> list = new ArrayList<DesignDimensionFilter>();
        for (Map.Entry entry : valueMap.entrySet()) {
            DesignDimensionFilter dimensionFilter = this.designTimeViewController.createDesignDimensionFilter();
            dimensionFilter.setTaskKey(filterPM.getTaskId());
            dimensionFilter.setEntityId((String)entry.getKey());
            dimensionFilter.setList((List)entry.getValue());
            list.add(dimensionFilter);
        }
        this.designTimeViewController.saveDimensionFiltersByTaskKey(filterPM.getTaskId(), list);
    }
}

