package com.backend.employee.entity;

import java.time.LocalDate;
import java.util.List;

import com.backend.employee.Enum.AccessStatus;
import com.backend.employee.Enum.Designation;
import com.backend.employee.Enum.Gender;
import com.backend.employee.Enum.Salutation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_path")
    private String photoPath; 

    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    private String firstName;
    private String middleName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String nationality;

    private LocalDate dob;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_detail_id", referencedColumnName = "id")
    private BankDetail bankDetail;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    private String education;
    private Integer experience;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    @Enumerated(EnumType.STRING)
    private AccessStatus accessStatus;
}
