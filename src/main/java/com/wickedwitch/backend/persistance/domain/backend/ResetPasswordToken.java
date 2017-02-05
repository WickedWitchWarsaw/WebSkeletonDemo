package com.wickedwitch.backend.persistance.domain.backend;

import com.wickedwitch.backend.persistance.converters.LocalDateTimeAttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by ZuZ on 2017-02-04.
 */
@Entity
public class ResetPasswordToken implements Serializable {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordToken.class);

    /**The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_TOKEN_LENGTH_IN_MINUTES = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="expiry_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime expiryDate;

    public ResetPasswordToken() {
    }

    public ResetPasswordToken(String token, User user, LocalDateTime creationDateTime, int expirationInMinutes) {
       if ((token == null) || (user == null) || (creationDateTime == null)){
           throw new IllegalArgumentException("token, user & creation date cannot be null");
       }
       if (expirationInMinutes == 0){
           LOG.warn("The token expeiration legnth in minutes is zero. Assigning the default value {}", DEFAULT_TOKEN_LENGTH_IN_MINUTES);
           expirationInMinutes = DEFAULT_TOKEN_LENGTH_IN_MINUTES;
       }

        this.token = token;
        this.user = user;
        expiryDate = creationDateTime.plusMinutes(expirationInMinutes);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResetPasswordToken that = (ResetPasswordToken) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
