package bowling;

/**
 * Représente un lancer de boule au bowling
 */
public class Lancer {
    private final int quillesAbattues;
    private final int numero;

    public Lancer(int quillesAbattues, int numero) {
        if (quillesAbattues < 0 || quillesAbattues > 10) {
            throw new IllegalArgumentException("Nombre de quilles invalide: " + quillesAbattues);
        }
        this.quillesAbattues = quillesAbattues;
        this.numero = numero;
    }

    public int getQuillesAbattues() {
        return quillesAbattues;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Lancer{" + numero + ": " + quillesAbattues + " quilles}";
    }
}
