/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.definition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nr.single.para.compare.definition.CompareDataDTO;
import org.springframework.util.CollectionUtils;

public class BatchCompareDataDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CompareDataDTO> dataList;

    public List<CompareDataDTO> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<CompareDataDTO> dataList) {
        this.dataList = dataList;
    }

    public void addFmdmUpdateDTO(CompareDataDTO compareDataDTO) {
        if (CollectionUtils.isEmpty(this.dataList)) {
            this.dataList = new ArrayList<CompareDataDTO>();
        }
        this.dataList.add(compareDataDTO);
    }
}

