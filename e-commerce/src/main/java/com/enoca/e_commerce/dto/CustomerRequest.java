package com.enoca.e_commerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
