package com.realestate.contactmessage;

import com.realestate.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByEmailEqualsAndCreateAtEquals(String email, LocalDate now);
}
