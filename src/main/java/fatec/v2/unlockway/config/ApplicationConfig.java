package fatec.v2.unlockway.config;

import fatec.v2.unlockway.domain.entity.patient.PatientModel;
import fatec.v2.unlockway.domain.repositories.nutritionist.NutritionistRepository;
import fatec.v2.unlockway.domain.repositories.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final PatientRepository patientRepository;
    private final NutritionistRepository nutritionistRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<PatientModel> patient = patientRepository.findByEmail(username);

            if(patient.isPresent()) return patient.get();

            return nutritionistRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEnconder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }
}


