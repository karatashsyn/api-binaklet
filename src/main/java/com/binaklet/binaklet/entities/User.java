package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.EntityListeners.UserEntityListener;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "user")
@Entity
@EntityListeners(UserEntityListener.class)
@AllArgsConstructor(staticName = "build")
@Builder
@RequiredArgsConstructor

public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Email
    @NotNull
    @Unique
    String email;


    @JsonIgnore
    @NotNull
    String password;

    @NotNull
    String phoneNumber;


    Float rating;

    Integer rateCount;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Item> items = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Order> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    List<Order> disposals = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    List<Item> favourites;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="follower_id"))
    List<User> followers;


    @JsonIgnore
    @ManyToMany(mappedBy = "followers")
    List<User> followings;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "profile_id")
    Profile profile;

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
