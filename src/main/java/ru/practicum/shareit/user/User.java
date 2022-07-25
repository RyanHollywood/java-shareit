package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */

@Data
@AllArgsConstructor
public class User {
    @NotNull
    long id;

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @Email
    String email;
}
