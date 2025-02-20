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

    public static Abonne searchAbonne(String nom) {
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

    public static Abonne getAbonneWithAbonnement(int id) {
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

    public static int getActiveAbonneCount() {
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

    public static void souscrireAbonnement(int abonneId, int abonnementId) {
        String sqlCheck = "SELECT COUNT(*) FROM souscription WHERE id_abonne = ? AND date_fin IS NULL";
        String sqlInsert = "INSERT INTO souscription (id_abonne, id_abonnement, date_debut) VALUES (?, ?, ?)";
        String sqlUpdateAbonne = "UPDATE abonne SET statut_souscription = true WHERE id = ?";
        String updateSql = "UPDATE Abonnement SET statut = 'expiré' WHERE date_fin < NOW() AND statut = 'actif'";
        String sql = "SELECT COUNT(*) FROM Abonnement WHERE abonne_id = ? AND statut = 'actif'";


        try (Connection conn = dbconn.getConnection()) {
            conn.setAutoCommit(false); // Début de transaction

            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
                stmtCheck.setInt(1, abonneId);
                try (ResultSet rs = stmtCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("L'abonné a déjà un abonnement actif.");
                    }
                }
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setInt(1, abonneId);
                stmtInsert.setInt(2, abonnementId);
                stmtInsert.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                stmtInsert.executeUpdate();
            }

            try (PreparedStatement stmtUpdateAbonne = conn.prepareStatement(sqlUpdateAbonne)) {
                stmtUpdateAbonne.setInt(1, abonneId);
                stmtUpdateAbonne.executeUpdate();
            }

            conn.commit(); // Valider la transaction
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la souscription : " + e.getMessage());
        }
    }


    public static void renouvelerAbonnement(int abonneId) {
        String sqlCheck = "SELECT id_abonnement FROM souscription WHERE id_abonne = ? AND date_fin IS NULL";
        String sqlUpdate = "UPDATE souscription SET date_debut = ? WHERE id_abonne = ? AND date_fin IS NULL";

        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {

            // Vérifier si l'abonné a un abonnement actif
            stmtCheck.setInt(1, abonneId);
            ResultSet rs = stmtCheck.executeQuery();
            if (!rs.next()) {
                throw new SQLException("L'abonné n'a pas d'abonnement actif à renouveler.");
            }

            // Renouveler l'abonnement
            stmtUpdate.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            stmtUpdate.setInt(2, abonneId);
            int updatedRows = stmtUpdate.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("Aucun abonnement actif trouvé pour cet abonné.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du renouvellement de l'abonnement: " + e.getMessage());
        }
    }

    public static void resilierAbonnement(int abonneId) {
        String sqlUpdate = "UPDATE souscription SET date_fin = ? WHERE id_abonne = ? AND date_fin IS NULL";
        String sqlUpdateAbonne = "UPDATE abonne SET statut_souscription = false WHERE id = ?";

        try (Connection conn = dbconn.getConnection();
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
             PreparedStatement stmtUpdateAbonne = conn.prepareStatement(sqlUpdateAbonne)) {

            conn.setAutoCommit(false);

            // Résilier l'abonnement
            stmtUpdate.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            stmtUpdate.setInt(2, abonneId);
            int updatedRows = stmtUpdate.executeUpdate();

            if (updatedRows == 0) {
                throw new SQLException("Aucun abonnement actif trouvé pour cet abonné.");
            }

            // Mettre à jour le statut de l'abonné
            stmtUpdateAbonne.setInt(1, abonneId);
            stmtUpdateAbonne.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la résiliation de l'abonnement: " + e.getMessage());
        }
    }
}