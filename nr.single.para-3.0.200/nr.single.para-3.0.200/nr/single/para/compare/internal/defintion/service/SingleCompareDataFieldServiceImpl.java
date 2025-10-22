/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.compare.internal.defintion.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.ISingleCompareDataFieldService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataFieldDO;
import nr.single.para.compare.internal.defintion.dao.ICompareDataFieldDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareDataFieldServiceImpl
implements ISingleCompareDataFieldService {
    @Autowired
    private ICompareDataFieldDao compareDataDao;
    private final Function<CompareDataFieldDO, CompareDataFieldDTO> toDto = Convert::cdf2Do;
    private final Function<List<CompareDataFieldDO>, List<CompareDataFieldDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareDataFieldDTO> list(CompareDataFieldDTO compareDataDTO) {
        ArrayList<CompareDataFieldDTO> list = new ArrayList<CompareDataFieldDTO>();
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            CompareDataFieldDO obj = (CompareDataFieldDO)this.compareDataDao.get(compareDataDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            if (StringUtils.isNotEmpty((String)compareDataDTO.getFormCompareKey()) && compareDataDTO.getSingleFloatingId() != null) {
                List<CompareDataFieldDO> list2 = this.compareDataDao.getByFloatingId(compareDataDTO.getInfoKey(), compareDataDTO.getFormCompareKey(), compareDataDTO.getSingleFloatingId());
                list.addAll((Collection<CompareDataFieldDTO>)this.list2Dto.apply(list2));
            } else if (StringUtils.isNotEmpty((String)compareDataDTO.getFormCompareKey())) {
                List list2 = this.compareDataDao.getByParentInInfo(compareDataDTO.getInfoKey(), compareDataDTO.getFormCompareKey());
                list.addAll((Collection<CompareDataFieldDTO>)this.list2Dto.apply(list2));
            } else if (compareDataDTO.getUpdateType() != null) {
                List<CompareDataFieldDO> list2 = this.compareDataDao.getByUpdateType(compareDataDTO.getInfoKey(), compareDataDTO.getUpdateType());
                list.addAll((Collection<CompareDataFieldDTO>)this.list2Dto.apply(list2));
            } else if (StringUtils.isNotEmpty((String)compareDataDTO.getNetCode())) {
                List<CompareDataFieldDO> list2 = this.compareDataDao.getByNetCode(compareDataDTO.getInfoKey(), compareDataDTO.getNetCode());
                list.addAll((Collection<CompareDataFieldDTO>)this.list2Dto.apply(list2));
            } else {
                List list2 = this.compareDataDao.getByInfoKey(compareDataDTO.getInfoKey());
                list.addAll((Collection<CompareDataFieldDTO>)this.list2Dto.apply(list2));
            }
        }
        return list;
    }

    @Override
    public void add(CompareDataFieldDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.insert(compareDataDTO);
    }

    @Override
    public void update(CompareDataFieldDTO compareDataDTO) throws SingleCompareException {
        this.compareDataDao.update(compareDataDTO);
    }

    @Override
    public void delete(CompareDataFieldDTO compareDataDTO) throws SingleCompareException {
        if (StringUtils.isNotEmpty((String)compareDataDTO.getKey())) {
            this.compareDataDao.delete(compareDataDTO.getKey());
        } else if (StringUtils.isNotEmpty((String)compareDataDTO.getInfoKey())) {
            this.compareDataDao.deleteByInfoKey(compareDataDTO.getInfoKey());
        }
    }

    @Override
    public void batchAdd(List<CompareDataFieldDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFieldDTO> list2 = new ArrayList<CompareDataFieldDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareDataFieldDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<CompareDataFieldDTO> list2 = new ArrayList<CompareDataFieldDTO>();
        list2.addAll(compareDataDTOs);
        this.compareDataDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareDataFieldDTO> compareDataDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareDataFieldDTO field : compareDataDTOs) {
            list2.add(field.getKey());
        }
        this.compareDataDao.batchDelete(list2);
    }
}

