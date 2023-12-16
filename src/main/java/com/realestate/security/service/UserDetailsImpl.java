package com.realestate.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails
{


    // Burada oluşturulan field'lar mimari tasarım gereği oluşturulmultur.
    // Projeden projeye göre değişkenlik gösterebilir. Lakin kullanılması zorunlu olan field'lar
    // her projede kullanılmalıdır.

    private Long id;
    private String name;
    private String email;


    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long id, String email, String firstName, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.name = firstName;
        this.password = password;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role  role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName().name()));
            this.authorities = grantedAuthorities;
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if(o == null || getClass()!=o.getClass())
        {
            return false;
        }


        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
