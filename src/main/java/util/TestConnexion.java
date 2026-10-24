
package util;

import java.sql.Connection;

public class TestConnexion {
    public static void main(String[] args) {
        try (Connection con = Connexion.getConnection()) {
            System.out.println("Connexion MySQL réussie !");
        } catch (Exception e) {
            System.out.println("Échec de la connexion MySQL.");
            e.printStackTrace();
        }
    }
}
