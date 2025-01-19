package com.soursoft.budgetit.dto.users.userId;

import com.soursoft.budgetit.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateUserAccountResponseDTO {

    private Long accountId;
    private Long ownerId;

    private String name;

    private BigDecimal currentBalance;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private LocalDateTime removalDate;

    public static PostCreateUserAccountResponseDTO fromEntity(UserAccount entity) {
        PostCreateUserAccountResponseDTO responseDTO = new PostCreateUserAccountResponseDTO();
        responseDTO.setAccountId(entity.getAccountId());
        responseDTO.setOwnerId(entity.getOwner().getUserId());
        responseDTO.setName(entity.getName());
        responseDTO.setCurrentBalance(entity.getCurrentBalance());
        responseDTO.setCreationDate(entity.getCreationDate());
        responseDTO.setModificationDate(entity.getModificationDate());
        responseDTO.setRemovalDate(entity.getRemovalDate());
        return responseDTO;
    }

}
