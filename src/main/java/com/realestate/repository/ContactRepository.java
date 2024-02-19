package com.realestate.repository;
import com.realestate.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.DoubleStream;

public interface ContactRepository extends JpaRepository<Contact, Long> {


    //J01 contactMessageCreated
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Contact c WHERE c.email = :email AND DATE(c.createAt) = :today")
    boolean existsByEmailEqualsAndCreateAtEquals(@Param("email") String email, @Param("today") LocalDate today);


}