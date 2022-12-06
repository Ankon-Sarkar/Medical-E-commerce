package MedicalEcommerce.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();



    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1,
                                        Authentication authentication) throws IOException, ServletException {
//        System.out.println("****************************");

        boolean hasCustomerRole = false;
        boolean hasSellerRole = false;
        boolean hasAdminRole=false;
        boolean hasDeliveryManRole=false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_SELLER")) {
                hasSellerRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_CUSTOMER")) {
                hasCustomerRole = true;
                break;
            }
            else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                hasAdminRole = true;
                break;
            }
            else if (grantedAuthority.getAuthority().equals("ROLE_DELIVERYMAN")) {
                hasDeliveryManRole = true;
                break;
            }
        }

        if (hasCustomerRole) {
//            System.out.println(hasCustomerRole);
            redirectStrategy.sendRedirect(arg0, arg1, "/Customerwelcome");
        }
        else if (hasSellerRole) {
//            System.out.println(hasSellerRole);
            redirectStrategy.sendRedirect(arg0, arg1, "/Sellerwelcome");
        }
        else if (hasAdminRole) {
//            System.out.println(hasSellerRole);
            redirectStrategy.sendRedirect(arg0, arg1, "/Adminwelcome");
        }
        else if (hasDeliveryManRole) {
            redirectStrategy.sendRedirect(arg0, arg1, "/DeliveryManWelcome");
        }

        else {
            throw new IllegalStateException();
        }
    }

}