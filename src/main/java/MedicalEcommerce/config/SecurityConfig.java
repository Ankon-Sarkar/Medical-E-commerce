package MedicalEcommerce.config;


import MedicalEcommerce.handler.UserAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthenticationSuccessHandler successHandler;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getDaoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/Sellerwelcome/**", "/addMedicine/**","/saveMedicine/**","/ViewSellerStock/**","/editMedicine/{id}/**",
                        "/updateMedicine/**","/deleteMedicine/{id}/**","/ViewMedicineDetails/{id}/**","/resetUserInfo/**","/reset/**","/viewNewOrderReq/**",
                        "/markAsShipped/{id}/**","/viewPreviousSalesRecord/**","/ViewMedicineDetails/{id}/**")
                .hasRole("SELLER")


                .antMatchers("/Customerwelcome/**","/addcart/{id}/**","/resetUserInfo/**","/reset/**","/ViewAllMedicine/**",
                        "/Customerside_ViewMedicineDetails/{id}/**","/viewMyOrders/**","/proceedToBuy/{id}/**","/buyNow/**","/paymentSuccess/**",
                        "/pay/**").hasRole("CUSTOMER")


                .antMatchers("/Adminwelcome/**","/viewAllCustomer/**","/viewAllSeller/**","/edit/{id}/**","/update/**","/delete/{id}/**",
                        "/admin_product_view/**","/admin_side_product_details/{id}/**","/remove_sell_post/{id}/**","/addDeliveryMan/**",
                        "/saveDeliveryMan/**")
                .hasRole("ADMIN")

                .antMatchers("/view-pending-deliveryItems/**","/markAsDelivered/{id}/**","/view-deliveredItems/**").
                hasRole("DELIVERYMAN")


                .antMatchers("/**").permitAll().and().formLogin().loginPage("/login").loginProcessingUrl("/login").successHandler(successHandler).and() .logout() .logoutUrl("/logout")
                .logoutSuccessUrl("/login");


    }

}