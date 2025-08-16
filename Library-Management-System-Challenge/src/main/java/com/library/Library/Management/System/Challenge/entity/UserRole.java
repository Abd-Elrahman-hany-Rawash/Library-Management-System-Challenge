package com.library.Library.Management.System.Challenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public enum UserRole {
    ADMINISTRATOR,
    LIBRARIAN,
    STAFF
}
