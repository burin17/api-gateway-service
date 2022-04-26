package com.gmail.burinigor7.apigatewayservice.internalcalls;

import com.gmail.burinigor7.apigatewayservice.dto.FetchedCredentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "crud-service")
@Service
public interface CrudServiceApiInterface {
    @GetMapping("/api/users")
    Optional<FetchedCredentials> getUserByUsername(@RequestParam String username);
}
