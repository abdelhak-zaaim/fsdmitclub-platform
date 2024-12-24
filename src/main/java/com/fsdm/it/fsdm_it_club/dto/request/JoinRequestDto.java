package com.fsdm.it.fsdm_it_club.dto.request;


import jakarta.validation.constraints.*;

public record JoinRequestDto(@NotNull @NotEmpty @Min(4) @Max(50) String fName, @NotNull @NotEmpty @Min(10) @Max(14) String phone, @Email String email,@Min(5) @Max(50) String degreeAndMajor,@Max(500) String message) {
}
