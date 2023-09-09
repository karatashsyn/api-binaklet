package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.UserRole;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import jakarta.validation.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    private UserRole role;

    @NotNull
    String firstName;

    String lastName;

    @Email
    @NotNull
    String email;


    @NotNull
    String password;

    @NotNull
    String phoneNumber;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Address> addresses;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Item> items;


    @CreatedDate
    Date createdDate;
    @PrePersist
    protected void onCreate(){
        createdDate=new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
