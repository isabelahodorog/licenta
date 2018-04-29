package com.isabela.v1.service;

import com.isabela.exception.EntityAlreadyExistsException;
import com.isabela.exception.IncorrectDataException;
import com.isabela.v1.core.model.AccountOwner;
import com.isabela.v1.core.repository.AccountOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@Service
@Transactional
public class AccountOwnerService {

    @Autowired
    private AccountOwnerRepository accountOwnerRepository;

    private static final String SALT = "hard-to-crack";

    /**
     * Creates a new entity of type AccountOwner
     *
     * @param name
     * @param email
     * @param password
     * @return
     */
    public AccountOwner createOwner(String name, String email, String password){

        if(accountOwnerRepository.findByEmail(email) != null) {
            throw new EntityAlreadyExistsException("User with email " + email + " already registered");
        }
        AccountOwner accountOwner = new AccountOwner();
        accountOwner.setName(name);
        accountOwner.setEmail(email);

        //encript the password ad only after save it into database
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);
        accountOwner.setPassword(hashedPassword);

        accountOwnerRepository.save(accountOwner);
        return accountOwner;
    }

    /**
     * Checks the credentials of a user
     *
     * @param email
     * @param password
     * @return
     */
    public Boolean login(String email, String password) {

        //encript the password entered by the user to match it with the password stored in the database
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        AccountOwner accountOwner = accountOwnerRepository.findByEmail(email);
        if (accountOwner == null) {
            throw new IncorrectDataException("Account with email " + email + " does not exist!");
        }

        if (hashedPassword.equals(accountOwner.getPassword())) {
            return true;
        } else {
            throw new IncorrectDataException("Password is incorrect!");
        }

    }

    /**
     * Generates a hash of the concatenation of the password and the SALT
     * Used as an encription of the password
     *
     * @param input
     * @return
     */
    private static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            for (int i = 0; i<hashedBytes.length; ++i) {
                byte b = hashedBytes[i];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }
}
