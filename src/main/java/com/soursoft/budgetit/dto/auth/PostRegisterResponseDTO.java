package com.soursoft.budgetit.dto.auth;

import com.soursoft.budgetit.entities.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostRegisterResponseDTO {

    private Long userId;
    private Boolean active;

    private String username;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private LocalDateTime removalDate;

    public static PostRegisterResponseDTO fromEntity(UserEntity entity) {
        PostRegisterResponseDTO response = new PostRegisterResponseDTO();

        response.setUserId(entity.getUserId());
        response.setActive(entity.getActive());
        response.setUsername(entity.getUsername());
        response.setCreationDate(entity.getCreationDate());
        response.setModificationDate(entity.getModificationDate());
        response.setRemovalDate(entity.getRemovalDate());

        return response;
    }

}
