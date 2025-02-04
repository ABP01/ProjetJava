package dao;

import models.Souscription;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SouscriptionDAO {
    public void addSouscription(Souscription souscription) throws SQLException {
        String query = "INSERT INTO souscription (id_abonne, id_abonnement, date_debut) VALUES (?, ?, ?)";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, souscription.getIdAbonne());
            stmt.setInt(2, souscription.getIdAbonnement());
            stmt.setDate(3, new java.sql.Date(souscription.getDateDebut().getTime()));
            stmt.executeUpdate();
        }
    }

    public void renewSouscription(int idAbonne, int idAbonnement) throws SQLException {
        String query = "UPDATE souscription SET id_abonnement = ?, date_debut = NOW() WHERE id_abonne = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAbonnement);
            stmt.setInt(2, idAbonne);
            stmt.executeUpdate();
        }
    }

    public void cancelSouscription(int idAbonne) throws SQLException {
        String query = "DELETE FROM souscription WHERE id_abonne = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAbonne);
            stmt.executeUpdate();
        }
    }
}