package dao;

import models.Abonnement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static List<Abonnement> getAllAbonnements() throws SQLException {
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

    public void updateAbonnement(Abonnement abonnement) throws SQLException {
        String query = "UPDATE abonnement SET libelle = ?, duree_mois = ?, prix_mensuel = ? WHERE id = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, abonnement.getLibelleOffre());
            stmt.setInt(2, abonnement.getDureeMois());
            stmt.setFloat(3, abonnement.getPrixMensuel());
            stmt.setInt(4, abonnement.getId());
            stmt.executeUpdate();
        }
    }

    public static void deleteAbonnement(int id) throws SQLException {
        String query = "DELETE FROM abonnement WHERE id = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public float getMonthlyRevenue() {
        float total = 0;
        String sql = "SELECT SUM(ab.prix_mensuel) AS total FROM souscription s " +
                     "JOIN abonnement ab ON s.id_abonnement = ab.id " +
                     "JOIN abonne a ON s.id_abonne = a.id " +
                     "WHERE a.statut_souscription = true";
        try (Connection conn = dbconn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getFloat("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}