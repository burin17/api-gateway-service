package com.gmail.burinigor7.apigatewayservice.sercurity.jwt;

import com.gmail.burinigor7.apigatewayservice.dto.FetchedCredentials;
import com.gmail.burinigor7.apigatewayservice.internalcalls.CrudServiceApiInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final CrudServiceApiInterface crudServiceApiInterface;

    @Autowired
    public JwtUserDetailsService(CrudServiceApiInterface crudServiceApiInterface) {
        this.crudServiceApiInterface = crudServiceApiInterface;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<FetchedCredentials> fetchedCredentials = crudServiceApiInterface.getUserByUsername(username);
        if (fetchedCredentials.isEmpty()) {
            throw new UsernameNotFoundException("User with username = " + username + " not found");
        }
        return new JwtUser(fetchedCredentials.get());
    }
}
