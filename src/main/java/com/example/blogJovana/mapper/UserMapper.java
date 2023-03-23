package com.example.blogJovana.mapper;

import com.example.blogJovana.model.User;
import com.example.blogJovana.request.AuthenticationRequest;
import com.example.blogJovana.request.RegistrationRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Mapper(uses = PasswordMapper.class, imports = {BCryptPasswordEncoder.class, LocalDateTime.class}, componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "role", expression = "java(User.Role.USER)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    User toUser(RegistrationRequest request);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password", qualifiedBy = EncodedMapping.class)
    User toUser(AuthenticationRequest request);

    /*default LocalDate getCurrentDate() {
        return LocalDate.now();
    }*/
}
