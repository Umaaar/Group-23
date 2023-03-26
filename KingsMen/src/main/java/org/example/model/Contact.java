import javax.persistence.*;
import java.time.LocalDateTime;
package org.example.model;

@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "enquiry", nullable = false)
    private String enquiry;

    @Column(name = "orderID")
    private String  order;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and setters
}