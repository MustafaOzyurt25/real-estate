package com.realestate.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails
{

    // Burada oluşturulan field'lar mimari tasarım gereği oluşturulmultur.
    // Projeden projeye göre değişkenlik gösterebilir. Lakin kullanılması zorunlu olan field'lar
    // her projede kullanılmalıdır.
    private Long id; //şart

    private String username; //şart

    private String name; //zorunlu değil

    private Boolean isAdvisor; //şart gibi

    @JsonIgnore
    private String password; //şart

    private Collection<? extends GrantedAuthority> authorities; //şart


    public UserDetailsImpl(Long id, String username, String name, Boolean isAdvisor, String password, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.isAdvisor = isAdvisor;
        this.password = password;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        this.authorities = grantedAuthorities;
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
        return username;
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

    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        // sınıf türü ile karşılaştırma
        if(o == null || getClass()!=o.getClass())
        {
            return false;
        }

        // id ile karşılaştırma
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id,user.getId());
    }

}
