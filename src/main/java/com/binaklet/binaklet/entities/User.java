package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.enums.UserRole;
import com.binaklet.binaklet.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.CreatedDate;
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
@AllArgsConstructor(staticName = "build")
@Builder

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull
    String name;

    @Email
    @NotNull
    @Unique
    String email;


    @JsonIgnore
    @NotNull
    String password;

    @NotNull
    String phoneNumber;

    String avatar;

    Float rating;

    Integer rateCount;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Address> addresses;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Item> items;


    @JsonIgnore
    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    List<Order> disposals;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    List<Item> favourites;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    UserStatus status;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @CreatedDate
    Date createdDate;
    @PrePersist
    protected void onCreate()
    {
        status=UserStatus.ACTIVE;
        createdDate=new Date();
        rateCount=0;
        rating=Float.parseFloat("0.00");
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
