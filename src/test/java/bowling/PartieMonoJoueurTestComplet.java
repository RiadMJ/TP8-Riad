package bowling;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests complémentaires pour couvrir tous les cas du bowling
 * Cette classe s'ajoute aux tests existants
 */
class PartieMonoJoueurTestComplet {

    private PartieMonoJoueur partie;

    @BeforeEach
    void setUp() {
        partie = new PartieMonoJoueur();
    }

    @Test
    void testSpareSimple() {
        // Tour 1: Spare (7 + 3)
        partie.enregistreLancer(7);
        partie.enregistreLancer(3);

        // Tour 2: 5 quilles
        partie.enregistreLancer(5);
        partie.enregistreLancer(4);

        // Score: (10 + 5) + (5 + 4) = 15 + 9 = 24
        assertEquals(24, partie.score());
    }

    @Test
    void testStrikeSimple() {
        // Tour 1: Strike
        partie.enregistreLancer(10);

        // Tour 2: 7 + 2
        partie.enregistreLancer(7);
        partie.enregistreLancer(2);

        // Score: (10 + 7 + 2) + (7 + 2) = 19 + 9 = 28
        assertEquals(28, partie.score());
    }

    @Test
    void testDoubleStrike() {
        // Tour 1: Strike
        partie.enregistreLancer(10);

        // Tour 2: Strike
        partie.enregistreLancer(10);

        // Tour 3: 5 + 4
        partie.enregistreLancer(5);
        partie.enregistreLancer(4);

        // Score: (10 + 10 + 5) + (10 + 5 + 4) + (5 + 4) = 25 + 19 + 9 = 53
        assertEquals(53, partie.score());
    }

    @Test
    void testDernierTourSpare() {
        // Jouer 9 tours normaux
        for (int i = 0; i < 9; i++) {
            partie.enregistreLancer(5);
            partie.enregistreLancer(4); // 9 points par tour
        }

        // Dernier tour: Spare + lancer bonus
        partie.enregistreLancer(7);
        partie.enregistreLancer(3); // Spare
        partie.enregistreLancer(5); // Lancer bonus

        assertTrue(partie.estTerminee());
        // Score: 9 tours × 9 points = 81 + (10 + 5) = 96
        assertEquals(96, partie.score());
    }

    @Test
    void testDernierTourStrike() {
        // Jouer 9 tours normaux
        for (int i = 0; i < 9; i++) {
            partie.enregistreLancer(5);
            partie.enregistreLancer(4); // 9 points par tour
        }

        // Dernier tour: Strike + 2 lancers bonus
        partie.enregistreLancer(10); // Strike
        partie.enregistreLancer(5); // Lancer bonus 1
        partie.enregistreLancer(5); // Lancer bonus 2

        assertTrue(partie.estTerminee());
        // Score: 9 tours × 9 points = 81 + (10 + 5 + 5) = 101
        assertEquals(101, partie.score());
    }

    @Test
    void testPartieParfaite() {
        // 12 strikes (10 tours + 2 lancers bonus)
        for (int i = 0; i < 12; i++) {
            partie.enregistreLancer(10);
        }

        assertTrue(partie.estTerminee());
        assertEquals(300, partie.score());
    }

    @Test
    void testValidationQuilles() {
        assertThrows(IllegalArgumentException.class, () -> {
            partie.enregistreLancer(11); // Nombre de quilles invalide
        });

        assertThrows(IllegalArgumentException.class, () -> {
            partie.enregistreLancer(-1); // Nombre de quilles invalide
        });
    }

    @Test
    void testTransitionTours() {
        assertEquals(1, partie.numeroTourCourant());
        assertEquals(1, partie.numeroProchainLancer());

        // Premier lancer
        partie.enregistreLancer(5);
        assertEquals(1, partie.numeroTourCourant());
        assertEquals(2, partie.numeroProchainLancer());

        // Deuxième lancer - fin du tour
        partie.enregistreLancer(3);
        assertEquals(2, partie.numeroTourCourant());
        assertEquals(1, partie.numeroProchainLancer());
    }

    @Test
    void testTroisStrikesSuccessifs() {
        // 3 strikes de suite
        partie.enregistreLancer(10); // Tour 1: 10 + 10 + 10 = 30
        partie.enregistreLancer(10); // Tour 2: 10 + 10 + 0 = 20
        partie.enregistreLancer(10); // Tour 3: 10 + 0 + 0 = 10
        // Les autres tours: 0
        // Total: 30 + 20 + 10 = 60
        assertEquals(60, partie.score());
    }

    @Test
    void testMelangeStrikesEtSpares() {
        // Tour 1: Strike
        partie.enregistreLancer(10);

        // Tour 2: Spare
        partie.enregistreLancer(7);
        partie.enregistreLancer(3);

        // Tour 3: 8 + 1
        partie.enregistreLancer(8);
        partie.enregistreLancer(1);

        // Score calcul:
        // Tour 1: 10 + 7 + 3 = 20
        // Tour 2: 10 + 8 = 18
        // Tour 3: 8 + 1 = 9
        // Total: 20 + 18 + 9 = 47
        assertEquals(47, partie.score());
    }
}
