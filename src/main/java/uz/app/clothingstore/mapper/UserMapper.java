package uz.app.clothingstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.req.SignUpReqDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "provider", constant = "LOCAL")
    @Mapping(target = "isEnabled", constant = "false")
    @Mapping(target = "isVerified", constant = "false")
    User toUser(SignUpReqDTO signUpReqDTO);

}
