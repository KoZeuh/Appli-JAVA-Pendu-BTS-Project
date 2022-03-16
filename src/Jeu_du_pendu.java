import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import java.net.*;
import java.io.*;

public class Jeu_du_pendu {

    public static int RandomInt(int min,int max){
        Random random = new Random();
        return random.nextInt(max + min) + min;
    }

    public static char[] initMot(String motRandom) {
        return motRandom.toCharArray();
    }

    public static boolean[] initReponses(int TailleduMot){
        // Renvoie un tableau de booléen FAUX ayant comme taille le nbr de lettre(s) du mot généré
        // Il permet d’initialiser les réponses avant le début de la partie

        return new boolean[TailleduMot];
    }

    public static boolean MotTrouve(boolean reponses[]){
        // Indique si le mot recherché a été trouvé ou non
        boolean motTrouve = true;

        for (int i=0; i < reponses.length; i++){
            if (!reponses[i]){
                motTrouve = false;
                break;
            }
        }

        return motTrouve;
    }

    public static void AffichageDuMot(char motCache[],boolean reponses[]){
        // Affiche les lettres trouvées OU
        // affiche les lettres non trouvées remplacées par des *

        for (int i=0; i < motCache.length; i++){
            if (!reponses[i]){
                System.out.print("*");
            }else {
                System.out.print(motCache[i]);
            }
        }
        System.out.println(" ");
        System.out.println("Veuillez saisir une lettre OU le mot entier si vous l'avez trouvé : ");
    }

    public static boolean[] joueUnTour(String proposition,char motCache[],boolean reponses[]){
        // Permet à l’utilisateur de saisir une lettre OU le mot
        // et met à jour la structure de données contenant les réponses
        // La fonction renvoie le tableau de booléen (reponses) mis à jour

        int TaillePropo = proposition.length();

        if(TaillePropo == 1)  {
            for (int i=0; i < motCache.length; i++){
                if (Character.compare(proposition.toLowerCase().charAt(0), motCache[i]) == 0){
                    reponses[i] = true;
                }
            }
        } else if(TaillePropo == motCache.length) {
            for (int i=0; i < motCache.length; i++){
                if (Character.compare(proposition.toLowerCase().charAt(i), motCache[i]) == 0) {
                    reponses[i] = true;
                }
            }
        }

        return reponses;
    }

    public static void main(String[] args) throws Exception{
        char motCache[];
        boolean reponses[];
        int nbEssaisMax = 10;
        int nbEssais = nbEssaisMax;

        Scanner lecteurClavier = new Scanner(System.in);

        //File doc = new File("D:/SIO/mots.txt"); // Location du fichier avec le contenu des mots à changer
        URL file = new URL("https://www.kozeuh-dev.fr/liste_mots");
        Scanner obj = new Scanner(file.openStream());

        //Scanner obj = new Scanner(doc);
        ArrayList<String> MotsList = new ArrayList<String>();

        while (obj.hasNextLine()){
            MotsList.add(obj.nextLine().trim());
        }

        int randomIndex = RandomInt(0, MotsList.size());
        String motRandom = MotsList.get(randomIndex).toLowerCase();

        motCache = initMot(motRandom);
        reponses = initReponses(motCache.length);

        System.out.println("A vous de jouer !");
        System.out.println("Le mot à trouver est composé de ["+motCache.length+"] lettre(s)");

        while (!MotTrouve(reponses) && nbEssais > 0){
            AffichageDuMot(motCache, reponses);

            boolean oldReponses[] = reponses;
            reponses = joueUnTour(lecteurClavier.nextLine(),motCache, reponses);

            if (Arrays.equals(oldReponses, reponses)){
                nbEssais = nbEssais-1;
            }
        }

        if (MotTrouve(reponses)){
            System.out.println("Bravo, vous avez réussi en "+(nbEssaisMax-nbEssais)+" coup(s) !");
            System.out.println("Le mot a trouvé était : ["+motRandom+"]");
        }else {
            System.out.println("Vous avez échoué !");
            System.out.println("Le mot a trouvé était : ["+motRandom+"]");
        }
    }

}
