public class Saluti {

    public static void main(String[] args) {
        // Controlla se sono stati forniti esattamente due argomenti
        if (args.length != 2) {
            System.out.println("Errore, gli argomenti devono essere due");
            return;
        }

        String nome = args[0];
        String cognome = args[1];

        System.out.println("Saluti, " + nome + " " + cognome + "!");
    }
}