package com.soursoft.budgetit.dto.users.userId;

import com.soursoft.budgetit.entities.UserAccount;
import com.soursoft.budgetit.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class GetUserByIdResponseDTO {

    private Boolean active;

    private Long userId;

    private String username;

    private BigDecimal totalBalance;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private LocalDateTime removalDate;

    List<UserAccount> accounts;

    public static GetUserByIdResponseDTO fromEntity(UserEntity entity) {
        GetUserByIdResponseDTO responseDTO = new GetUserByIdResponseDTO();
        responseDTO.setUserId(entity.getUserId());
        responseDTO.setActive(entity.getActive());
        responseDTO.setUsername(entity.getUsername());
        responseDTO.setTotalBalance(entity.getTotalBalance());
        responseDTO.setCreationDate(entity.getCreationDate());
        responseDTO.setModificationDate(entity.getModificationDate());
        responseDTO.setRemovalDate(entity.getRemovalDate());
        responseDTO.setAccounts(entity.getAccounts());

        return responseDTO;
    }

}
