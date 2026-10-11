package com.chesedbank.account.mapper;

import com.chesedbank.account.dto.AccountCreateRequest;
import com.chesedbank.account.dto.AccountResponse;
import com.chesedbank.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AccountMapper {

    @Mapping(target = "idAccount", ignore = true)
    @Mapping(target = "publicIdAccount", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Account toEntity(AccountCreateRequest request);

    AccountResponse toResponse(Account account);
}
