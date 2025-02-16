package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Souscription;

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

    public static List<Souscription> getAllSouscriptions() throws SQLException {
        List<Souscription> souscriptions = new ArrayList<>();
        String query = "SELECT * FROM souscription";
        try (Connection conn = dbconn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Souscription souscription = new Souscription(
                        rs.getInt("id"),
                        rs.getInt("id_abonne"),
                        rs.getInt("id_abonnement"),
                        rs.getDate("date_debut"));
                souscriptions.add(souscription);
            }
        }
        return souscriptions;
    }

    public static void updateSouscription(Souscription updatedSouscription) throws SQLException {
        String query = "UPDATE souscription SET id_abonne = ?, id_abonnement = ?, date_debut = ? WHERE id = ?";
        try (Connection conn = dbconn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, updatedSouscription.getIdAbonne());
            stmt.setInt(2, updatedSouscription.getIdAbonnement());
            stmt.setDate(3, new java.sql.Date(updatedSouscription.getDateDebut().getTime()));
            stmt.setInt(4, updatedSouscription.getId());
            stmt.executeUpdate();
        }
    }

    public static void deleteSouscription(int id) throws SQLException {
        String query = "DELETE FROM souscription WHERE id = ?";
        try (Connection conn = dbconn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}