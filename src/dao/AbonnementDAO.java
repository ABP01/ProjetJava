package dao;

import models.Abonnement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbonnementDAO {
    public void addAbonnement(Abonnement abonnement) throws SQLException {
        String query = "INSERT INTO abonnement (libelle, duree_mois, prix_mensuel) VALUES (?, ?, ?)";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, abonnement.getLibelleOffre());
            stmt.setInt(2, abonnement.getDureeMois());
            stmt.setFloat(3, abonnement.getPrixMensuel());
            stmt.executeUpdate();
        }
    }

    public List<Abonnement> getAllAbonnements() throws SQLException {
        List<Abonnement> abonnements = new ArrayList<>();
        String query = "SELECT * FROM abonnement";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Abonnement abonnement = new Abonnement(
                        rs.getInt("id"),
                        rs.getString("libelle"),
                        rs.getInt("duree_mois"),
                        rs.getFloat("prix_mensuel")
                );
                abonnements.add(abonnement);
            }
        }
        return abonnements;
    }

    // Other methods for updating and deleting an abonnement can be added here
}