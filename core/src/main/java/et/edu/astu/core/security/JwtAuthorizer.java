package et.edu.astu.core.security;

import et.edu.astu.core.services.EmployeeService;
import et.edu.astu.core.services.JwtService;
import et.edu.astu.core.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthorizer extends OncePerRequestFilter {
    private final JwtService service;
    private final EmployeeService employeeService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (!Objects.isNull(authHeader)){
            String[] strings  = authHeader.split(" ");
            String authType = strings[0];
            String token = strings[1];

            UserDetails details = null;

            if ("BANK".equals(authType)) {
                if (service.validate(token)) {
                    String employeeId = service.getSubject(token);
                    details = employeeService.findCustom(employeeId);
                    request.setAttribute("employeeId", employeeId);
                }
            } else if ("USER".equals(authType)){
                Long userId = Long.parseLong(token);

                if (!userService.exists(userId))
                    userService.insert(userId);

                var userD = userService.getCustomUser(userId);

                request.setAttribute("userId", userId);
                request.setAttribute("account", userD.getAccountNumber());

                details = userD;
            }

            if (details != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        details,
                        null,
                        details.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request, response);
    }
}
