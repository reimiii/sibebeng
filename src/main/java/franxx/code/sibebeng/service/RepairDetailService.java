package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.repairdetail.request.CreateRepairDetailRequest;
import franxx.code.sibebeng.dto.repairdetail.response.RepairDetailResponse;
import franxx.code.sibebeng.entity.RepairDetail;
import franxx.code.sibebeng.entity.StatusPayment;
import franxx.code.sibebeng.repository.RepairDetailRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class RepairDetailService {
  private final ValidationService validationService;
  private final EntityFinderService entityFinderService;
  private final RepairDetailRepository repairDetailRepository;

  private static RepairDetailResponse toRepairDetailResponse(RepairDetail detail) {
    return RepairDetailResponse.builder()
        .id(detail.getId())
        .issueDescription(detail.getIssueDescription())
        .repairAction(detail.getRepairAction())
        .status(detail.getStatusPayment().getDescription())
        .price(detail.getPrice())
        .build();
  }

  public RepairDetailResponse createRepairDetail(CreateRepairDetailRequest request) {

    validationService.validateRequest(request);

    var repair = entityFinderService.findRepair(
        request.getCustomerId(), request.getVehicleId(), request.getRepairId()
    );

    var detail = new RepairDetail();
    detail.setRepair(repair);

    detail.setIssueDescription(request.getIssueDescription());
    detail.setRepairAction(request.getRepairAction());
    detail.setStatusPayment(StatusPayment.valueOf(request.getStatus().toUpperCase()));

    detail.setPrice(request.getPrice());

    repairDetailRepository.save(detail);

    return toRepairDetailResponse(detail);
  }
}
