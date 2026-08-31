package com.emp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeProfileUpdateDTO {

	 @NotBlank
	    private String firstName;

	    @NotBlank
	    private String lastName;

	    @NotBlank
	    @Email
	    private String email;

	    @NotBlank
	    @Pattern(regexp = "\\d{10}")
	    private String phone;
}
