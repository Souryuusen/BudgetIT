package com.soursoft.budgetit.dto.users.userId;

import com.soursoft.budgetit.entities.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PostCreateUserAccountResponseDTO {

    private Long ownerId;

    private String name;

    private BigDecimal currentBalance;

    public static PostCreateUserAccountResponseDTO fromEntity(UserAccount entity) {
        PostCreateUserAccountResponseDTO responseDTO = new PostCreateUserAccountResponseDTO();
        responseDTO.setOwnerId(entity.getOwner().getUserId());
        responseDTO.setName(entity.getName());
        responseDTO.setCurrentBalance(entity.getCurrentBalance());
        return responseDTO;
    }

}
