package org.mbr.authservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.mbr.authservice.entities.UserInfo;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo {
    private String userName;
    private String lastname;
    private String email;
    private Long phoneNumber;

}
