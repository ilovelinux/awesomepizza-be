package com.awesomepizza.be.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class AbstractGenericModel {
    @Version
    Long version;

    @CreatedDate
    @PastOrPresent
    LocalDateTime creationDate;

    @LastModifiedDate
    @PastOrPresent
    LocalDateTime lastModifiedDate;
}
