package et.edu.astu.core.models;

import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public @NonNull String getUsername(){
        return user.getUserId().toString();
    }

    @Override
    public String getPassword(){
        return "";
    }

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (user.getAccount() != null)
            authorities.add(new SimpleGrantedAuthority("connected"));
        return authorities;
    }
}
