/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import com.irrigation.iotserver.Security.PasswordHasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author brune
 */
public class PasswordHasherTest {
    
    
    @Test
    public void successIfHashEqualsPassword(){
        String password = "12345";
        String hash = PasswordHasher.getHash(password);
        
        Assertions.assertTrue(PasswordHasher.compareIfPassowrdMatchesWithStoredHash(password, hash));
    }
    
}
