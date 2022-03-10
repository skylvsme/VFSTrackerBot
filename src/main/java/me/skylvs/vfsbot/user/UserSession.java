package me.skylvs.vfsbot.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "session")
@Entity
@Getter
@Setter
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "birthdate")
    private String birthDate;

    @Column(name = "application_status")
    private String applicationStatus;

    @Column(name = "stage")
    @Enumerated(EnumType.STRING)
    private UserSessionState stage;

}