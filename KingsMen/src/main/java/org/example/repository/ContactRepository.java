package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;



import org.example.model.Contact;


public interface ContactRepository extends JpaRepository<Contact, Long> {
}
