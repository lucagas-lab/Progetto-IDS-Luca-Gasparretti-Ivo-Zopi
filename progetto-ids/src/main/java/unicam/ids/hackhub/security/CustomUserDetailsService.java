package unicam.ids.hackhub.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRep;

    public CustomUserDetailsService(UtenteRepository utenteRep) {
        this.utenteRep = utenteRep;
    }

    /**
     * Metodo chiamato in automatico durante la fase di login.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRep.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Attenzione: Utente '" + username + "' non trovato nel database"));

        String ruoloSpring = "ROLE_" + utente.getRuolo().name();

        return new org.springframework.security.core.userdetails.User(
                utente.getUsername(),
                utente.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(ruoloSpring))
        );
    }
}