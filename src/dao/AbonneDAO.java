package dao;

import models.Abonne;

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
}