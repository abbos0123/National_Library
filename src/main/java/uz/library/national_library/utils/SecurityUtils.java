package uz.library.national_library.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtils {
    public SecurityUtils() {
    }

    public static Optional<String> getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                   if (authentication.getPrincipal() instanceof UserDetails){
                       UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                       return userDetails.getUsername();
                   }else if (authentication.getPrincipal() instanceof String){
                       return (String) authentication.getPrincipal();
                   }

                   return null;
                });
    }
}
