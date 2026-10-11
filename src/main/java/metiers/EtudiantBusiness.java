package metiers;

import entities.Option;
import entities.Etudiant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EtudiantBusiness {
    private static final OptionBusiness optionBusiness = new OptionBusiness();
    private static List<Etudiant> etudiants = new ArrayList<>();

    static {
        etudiants.add(new Etudiant("I001", "Doe", "Jean", optionBusiness.getOptionByCode(1), 2023, "jean.doe@example.com"));
        etudiants.add(new Etudiant("I002", "Smith", "Alice", optionBusiness.getOptionByCode(1), 2022, "alice.smith@example.com"));
        etudiants.add(new Etudiant("I003", "Durand", "Pierre", optionBusiness.getOptionByCode(2), 2023, "pierre.durand@example.com"));
    }

    // Ajouter un etudiant
    public boolean addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null || etudiant.getIdentifiant() == null)
            return false;
        if (getEtudiantByIdentifiant(etudiant.getIdentifiant()) != null)
            return false; // duplicate id

        Option option = optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption());
        if (option != null) {
            etudiant.setOption(option);
            return etudiants.add(etudiant);
        }
        return false;
    }

    // Récupérer un etudiant par son identifiant
    public Etudiant getEtudiantByIdentifiant(String identifiant) {
        for (Etudiant e : etudiants) {
            if (e != null && e.getIdentifiant() != null && e.getIdentifiant().equals(identifiant)) {
                return e;
            }
        }
        return null;
    }

    // Récupérer les etudiants par option
    public List<Etudiant> getEtudiantsByOption(Option option) {
        List<Etudiant> result = new ArrayList<>();
        if (option == null) return result;
        for (Etudiant e : etudiants) {
            if (e != null && e.getOption() != null
                    && e.getOption().getCodeOption() == option.getCodeOption()) {
                result.add(e);
            }
        }
        return result;
    }

    // Récupérer tous les etudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiants;
    }

    // Mettre à jour un etudiant
    public boolean updateEtudiant(String identifiant, Etudiant updatedEtudiant) {
        if (updatedEtudiant == null) return false;
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant current = etudiants.get(i);
            if (current != null && current.getIdentifiant() != null
                    && current.getIdentifiant().equals(identifiant)) {
                if (updatedEtudiant.getOption() != null) {
                    Option option = optionBusiness.getOptionByCode(updatedEtudiant.getOption().getCodeOption());
                    if (option == null) return false;
                    updatedEtudiant.setOption(option);
                }
                updatedEtudiant.setIdentifiant(identifiant); // keep the id from the URL
                etudiants.set(i, updatedEtudiant);
                return true;
            }
        }
        return false;
    }

    // Supprimer un etudiant
    public boolean deleteEtudiant(String identifiant) {
        Iterator<Etudiant> iterator = etudiants.iterator();
        while (iterator.hasNext()) {
            Etudiant e = iterator.next();
            if (e != null && e.getIdentifiant() != null && e.getIdentifiant().equals(identifiant)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}