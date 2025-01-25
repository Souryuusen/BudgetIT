package com.soursoft.budgetit.dto.accounts;

import com.soursoft.budgetit.dto.users.userId.PostCreateUserAccountResponseDTO;
import com.soursoft.budgetit.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateAccountResponseDTO {

    private Long accountId;
    private Long ownerId;

    private Boolean mainAccount;

    private String name;

    private BigDecimal currentBalance;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private LocalDateTime removalDate;

    public static PostCreateAccountResponseDTO fromEntity(UserAccount entity) {
        PostCreateAccountResponseDTO responseDTO = new PostCreateAccountResponseDTO();

        responseDTO.setAccountId(entity.getAccountId());
        responseDTO.setOwnerId(entity.getOwner().getUserId());
        responseDTO.setMainAccount(entity.getMainAccount());
        responseDTO.setName(entity.getName());
        responseDTO.setCurrentBalance(entity.getCurrentBalance());
        responseDTO.setCreationDate(entity.getCreationDate());
        responseDTO.setModificationDate(entity.getModificationDate());
        responseDTO.setRemovalDate(entity.getRemovalDate());

        return responseDTO;
    }


}
