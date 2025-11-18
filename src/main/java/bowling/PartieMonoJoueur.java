package bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {

    private static final int NOMBRE_TOURS = 10;
    private final List<Tour> tours;
    private int tourCourantIndex;

    /**
     * Constructeur
     */
    public PartieMonoJoueur() {
        this.tours = new ArrayList<>();
        // Créer les 10 tours
        for (int i = 1; i <= NOMBRE_TOURS; i++) {
            tours.add(new Tour(i, i == NOMBRE_TOURS));
        }
        this.tourCourantIndex = 0;
    }

    /**
     * Cette méthode doit être appelée à chaque lancer de boule
     *
     * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce
     * lancer
     * @throws IllegalStateException si la partie est terminée
     * @return vrai si le joueur doit lancer à nouveau pour continuer son tour,
     * faux sinon
     */
    public boolean enregistreLancer(int nombreDeQuillesAbattues) {
        if (estTerminee()) {
            throw new IllegalStateException("La partie est terminée");
        }

        Tour tourCourant = tours.get(tourCourantIndex);
        boolean tourContinue = tourCourant.ajouterLancer(nombreDeQuillesAbattues);

        // Passer au tour suivant si le tour courant est terminé
        if (!tourContinue && tourCourantIndex < tours.size() - 1) {
            tourCourantIndex++;
        }

        return tourContinue;
    }

    /**
     * Cette méthode donne le score du joueur. Si la partie n'est pas terminée,
     * on considère que les lancers restants abattent 0 quille.
     *
     * @return Le score du joueur
     */
    public int score() {
        int scoreTotal = 0;

        for (int i = 0; i < tours.size(); i++) {
            Tour tour = tours.get(i);
            int scoreTour = tour.getQuillesTour();

            // Bonus pour spare
            if (tour.estSpare() && i < tours.size() - 1) {
                Tour tourSuivant = tours.get(i + 1);
                if (!tourSuivant.getLancers().isEmpty()) {
                    scoreTour += tourSuivant.getLancer(0).getQuillesAbattues();
                }
            }

            // Bonus pour strike
            if (tour.estStrike() && i < tours.size() - 1) {
                Tour tourSuivant = tours.get(i + 1);
                if (tourSuivant.estStrike() && i < tours.size() - 2) {
                    // Double strike
                    Tour tourEncoreSuivant = tours.get(i + 2);
                    scoreTour += tourSuivant.getQuillesTour();
                    if (!tourEncoreSuivant.getLancers().isEmpty()) {
                        scoreTour += tourEncoreSuivant.getLancer(0).getQuillesAbattues();
                    }
                } else {
                    // Strike normal
                    List<Lancer> lancersSuivants = tourSuivant.getLancers();
                    if (lancersSuivants.size() >= 2) {
                        scoreTour += lancersSuivants.get(0).getQuillesAbattues()
                                + lancersSuivants.get(1).getQuillesAbattues();
                    } else if (lancersSuivants.size() == 1) {
                        scoreTour += lancersSuivants.get(0).getQuillesAbattues();
                    }
                }
            }

            scoreTotal += scoreTour;
        }

        return scoreTotal;
    }

    /**
     * @return vrai si la partie est terminée pour ce joueur, faux sinon
     */
    public boolean estTerminee() {
        Tour dernierTour = tours.get(tours.size() - 1);
        return dernierTour.estTermine();
    }

    /**
     * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
     */
    public int numeroTourCourant() {
        if (estTerminee()) {
            return 0;
        }
        return tours.get(tourCourantIndex).getNumero();
    }

    /**
     * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le
     * jeu est fini
     */
    public int numeroProchainLancer() {
        if (estTerminee()) {
            return 0;
        }
        return tours.get(tourCourantIndex).getNumeroProchainLancer();
    }

    // Méthodes utilitaires pour les tests
    Tour getTour(int index) {
        return tours.get(index);
    }
}
