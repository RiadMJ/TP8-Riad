package bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un tour de bowling (2 lancers normaux, 3 pour le dernier tour)
 */
public class Tour {
    private final int numero;
    private final List<Lancer> lancers;
    private boolean termine;
    private final boolean estDernierTour;

    public Tour(int numero, boolean estDernierTour) {
        this.numero = numero;
        this.lancers = new ArrayList<>();
        this.termine = false;
        this.estDernierTour = estDernierTour;
    }

    /**
     * Ajoute un lancer au tour
     * 
     * @param quillesAbattues nombre de quilles abattues
     * @return true si le tour continue, false si le tour est terminé
     */
    public boolean ajouterLancer(int quillesAbattues) {
        if (termine) {
            throw new IllegalStateException("Tour déjà terminé");
        }

        Lancer lancer = new Lancer(quillesAbattues, lancers.size() + 1);
        lancers.add(lancer);

        // Logique de fin de tour
        if (estDernierTour) {
            // Dernier tour : règles spéciales
            if (lancers.size() == 3) {
                termine = true;
                return false;
            }
            if (lancers.size() == 2 && !estStrike() && !estSpare()) {
                termine = true;
                return false;
            }
        } else {
            // Tours normaux
            if (lancers.size() == 2 || estStrike()) {
                termine = true;
                return false;
            }
        }

        return true;
    }

    public boolean estTermine() {
        return termine;
    }

    public boolean estStrike() {
        return !lancers.isEmpty() && lancers.get(0).getQuillesAbattues() == 10;
    }

    public boolean estSpare() {
        if (lancers.size() < 2)
            return false;
        return lancers.get(0).getQuillesAbattues() + lancers.get(1).getQuillesAbattues() == 10;
    }

    public int getNumero() {
        return numero;
    }

    public List<Lancer> getLancers() {
        return new ArrayList<>(lancers);
    }

    public int getQuillesTour() {
        return lancers.stream().mapToInt(Lancer::getQuillesAbattues).sum();
    }

    public int getNombreLancers() {
        return lancers.size();
    }

    public Lancer getLancer(int index) {
        if (index < 0 || index >= lancers.size()) {
            return null;
        }
        return lancers.get(index);
    }

    public boolean estDernierTour() {
        return estDernierTour;
    }

    public int getNumeroProchainLancer() {
        if (termine)
            return 0;
        return lancers.size() + 1;
    }
}
