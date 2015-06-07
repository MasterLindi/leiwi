package business.service;

import commons.utils.JavaToScalaConverter;
import view.model.IndexDetailVM;
import view.model.IndexResultVM;
import view.model.IndexVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cli on 01/06/15.
 */
public class IndexCalculationServiceImpl implements IndexCalculationService {
    @Override
    public IndexResultVM calculateIndex(IndexVM indexVM) {
        if (indexVM.family()) {
            List<IndexDetailVM> detailVMList = new ArrayList<>();
            detailVMList.add(new IndexDetailVM("Luftgüte", 3.0, 16.4534, 48.7689, 1.32));
            detailVMList.add(new IndexDetailVM("Krankenversorung", 4.0, 16.4534, 48.7689, 3.2));
            return new IndexResultVM(1.3, new JavaToScalaConverter().convertList(detailVMList));
        } else if (indexVM.students()) {
            List<IndexDetailVM> detailVMList = new ArrayList<>();
            detailVMList.add(new IndexDetailVM("Luftgüte", 2.0, 16.4534, 48.7689, 2.12));
            detailVMList.add(new IndexDetailVM("Krankenversorung", 7.0, 16.4534, 48.7689, 0.32));
            return new IndexResultVM(2.3, new JavaToScalaConverter().convertList(detailVMList));
        } else {
            List<IndexDetailVM> detailVMList = new ArrayList<>();
            detailVMList.add(new IndexDetailVM("Luftgüte", 8.0, 16.4534, 48.7689, 1.11));
            detailVMList.add(new IndexDetailVM("Krankenversorung", 2.0, 16.4534, 48.7689, 0.32));
            return new IndexResultVM(4.3, new JavaToScalaConverter().convertList(detailVMList));
        }
    }
}
