package org.mbr.authservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mbr.authservice.entities.UserInfo;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
@Data
public class UserInfoDto extends UserInfo {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;

}
