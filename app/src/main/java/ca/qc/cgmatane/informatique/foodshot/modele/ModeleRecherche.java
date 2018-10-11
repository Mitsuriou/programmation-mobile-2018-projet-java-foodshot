package ca.qc.cgmatane.informatique.foodshot.modele;

import java.util.List;

public class ModeleRecherche {

    private boolean statut;
    private List<ModeleUtilisateur> listeUtilisateurs;
    private List<String> messages;

    public ModeleRecherche(boolean statut,
                           List<ModeleUtilisateur> listeUtilisateurs,
                           List<String> messages) {
        this.statut = statut;
        this.listeUtilisateurs.addAll(listeUtilisateurs);
        this.messages.addAll(messages);
    }

    public boolean isStatut() {
        return statut;
    }

    public List<ModeleUtilisateur> getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public List<String> getMessages() {
        return messages;
    }
}
