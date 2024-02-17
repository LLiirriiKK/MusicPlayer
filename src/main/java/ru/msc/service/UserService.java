package ru.msc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.msc.model.ERole;
import ru.msc.model.User;
import ru.msc.repository.UserRepository;
import ru.msc.request.UserLoginRq;
import ru.msc.request.UserRegisterRq;
import ru.msc.response.AuthResponse;
import ru.msc.response.MessageResponse;
import ru.msc.security.UserDetailsImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public ResponseEntity<?> registerUser(UserRegisterRq userRegisterRq){

        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userRegisterRq.getEmail());
        if(!matcher.matches()){
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid email format"));
        }

        if(!userRegisterRq.getPassword().equals(userRegisterRq.getConfirmPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Passwords dont match"));
        }

        if (userRepository.existsByEmail(userRegisterRq.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Your email should be unique"));
        }

        if (userRepository.existsByLogin(userRegisterRq.getLogin())){
            return ResponseEntity.badRequest().body(new MessageResponse("Your login should be unique"));
        }


        if (userRegisterRq.getLogin().length() < 3) {
            return ResponseEntity.badRequest().body(new MessageResponse("Login should be longer then 3 symbols"));
        }

        if (userRegisterRq.getPassword().length() < 5) {
            return ResponseEntity.badRequest().body(new MessageResponse("Password should be longer then 5 symbols"));
        }


        User user = new User();
        user.setEmail(userRegisterRq.getEmail());
        user.setLogin(userRegisterRq.getLogin());
        user.setPassword(new BCryptPasswordEncoder().encode(userRegisterRq.getPassword()));
        user.setERole(ERole.USER);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Registration success"));
    }

    public ResponseEntity<?> authUser(UserLoginRq userLoginRq) throws UsernameNotFoundException{
        if (!userRepository.existsByEmail(userLoginRq.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("No user with this login"));
        }

        if (userRepository.findUserByEmail(userLoginRq.getEmail()).isPresent() &&
                !(new BCryptPasswordEncoder().matches(userLoginRq.getPassword(), userRepository.findUserByEmail(userLoginRq.getEmail()).get().getPassword()))){
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid password"));
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRq.getEmail(), userLoginRq.getPassword()));
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(userLoginRq.getEmail()), userDetails.getEmail(), userDetails.getLogin(), "Login success"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid data"));
        }

    }

}
