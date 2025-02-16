package dao;

import models.Abonne;
import models.Abonnement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonneDAO {
    public static void ajouterAbonne(Abonne abonne) {
        String sql = "INSERT INTO abonne (nom, prenom, date_inscription, numero_telephone, statut_souscription) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, abonne.getNom());
            stmt.setString(2, abonne.getPrenom());
            stmt.setDate(3, new java.sql.Date(abonne.getDateInscription().getTime()));
            stmt.setString(4, abonne.getNumeroTelephone());
            stmt.setBoolean(5, abonne.getAbonnementActif());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Abonne> getAbonnes() {
        List<Abonne> abonnes = new ArrayList<>();
        String sql = "SELECT * FROM abonne";
        try (Connection conn = dbconn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                abonnes.add(new Abonne(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_inscription"),
                        rs.getString("numero_telephone"),
                        rs.getBoolean("statut_souscription")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abonnes;
    }

    public Abonne searchAbonne(String nom) {
        Abonne abonne = null;
        String sql = "SELECT * FROM abonne WHERE nom = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                abonne = new Abonne(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_inscription"),
                        rs.getString("numero_telephone"),
                        rs.getBoolean("statut_souscription")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abonne;
    }

    public Abonne getAbonneWithAbonnement(int id) {
        Abonne abonne = null;
        String sql = "SELECT a.*, s.id_abonnement, ab.libelle, ab.duree_mois, ab.prix_mensuel " +
                     "FROM abonne a " +
                     "LEFT JOIN souscription s ON a.id = s.id_abonne " +
                     "LEFT JOIN abonnement ab ON s.id_abonnement = ab.id " +
                     "WHERE a.id = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                abonne = new Abonne(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_inscription"),
                        rs.getString("numero_telephone"),
                        rs.getBoolean("statut_souscription")
                );
                Abonnement abonnement = new Abonnement(
                        rs.getInt("id_abonnement"),
                        rs.getString("libelle"),
                        rs.getInt("duree_mois"),
                        rs.getFloat("prix_mensuel")
                );
                abonne.setAbonnement(abonnement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abonne;
    }

    public static void updateAbonne(Abonne abonne) {
        String sql = "UPDATE abonne SET nom = ?, prenom = ?, date_inscription = ?, numero_telephone = ?, statut_souscription = ? WHERE id = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, abonne.getNom());
            stmt.setString(2, abonne.getPrenom());
            stmt.setDate(3, new java.sql.Date(abonne.getDateInscription().getTime()));
            stmt.setString(4, abonne.getNumeroTelephone());
            stmt.setBoolean(5, abonne.getAbonnementActif());
            stmt.setInt(6, abonne.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAbonne(int id) {
        String sql = "DELETE FROM abonne WHERE id = ?";
        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getActiveAbonneCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM abonne WHERE statut_souscription = true";
        try (Connection conn = dbconn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void subscribeAbonne(int id, int id2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subscribeAbonne'");
    }
}