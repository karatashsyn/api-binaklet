package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.UserRole;
import com.binaklet.binaklet.repositories.CartRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import jakarta.validation.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Data
@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<UserRole> roles = new HashSet<>();

    @NotNull
    String firstName;


    String lastName;

    @Email
    @NotNull
    @Unique
    String email;


    @NotNull
    String password;

    @NotNull
    String phoneNumber;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Address> addresses;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Item> items;


    @OneToOne
    @JoinColumn(name = "cart_id")
    Cart cart;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @CreatedDate
    Date createdDate;
    @PrePersist
    protected void onCreate()
    {
        createdDate=new Date();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (var role: this.roles) {
            var grantedAuthorization = new SimpleGrantedAuthority(role.name());
            authorities.add(grantedAuthorization);
        }
        return authorities;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
