package ru.pronichev.phonebook.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "notes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "name"})}
)
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;
}
