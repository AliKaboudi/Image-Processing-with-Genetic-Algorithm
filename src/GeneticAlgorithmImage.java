
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ali
 */

public class GeneticAlgorithmImage {

    public final static double mutationPourcentage = 0.1;

    public static void Sort(String[] B, double[] A) {
        double aux = 0;
        String aux1 = "";
        for (int i = 0; i < A.length - 1; i++) {
            for (int j = i + 1; j < A.length; j++) {
                if (A[i] < A[j]) {
                    aux = A[i];
                    aux1 = B[i];
                    A[i] = A[j];
                    B[i] = B[j];
                    A[j] = aux;
                    B[j] = aux1;

                }

            }

        }

    }

    // Croissement 
    public static void Croissement(String a[]) {
        int j = 0;
        for (int i = 0; i < 2; i++) {
            int indexOfCroisement = (int) Math.floor(Math.random() * (a[0].length() - 1));
            System.out.println(indexOfCroisement);
            String st1 = a[j].substring(indexOfCroisement + 1, a[0].length());
            String st2 = a[j + 1].substring(indexOfCroisement + 1, a[0].length());
            a[j] = a[j].substring(0, indexOfCroisement + 1) + st2;
            a[j + 1] = a[j + 1].substring(0, indexOfCroisement + 1) + st1;
            j += 2;

        }

    }

    //Mutation
    public static void Mutation(String a[]) {

        int totalGenes = a[0].length() * 4;
        double mutationRate = totalGenes * mutationPourcentage;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < a[i].length(); j++) {
                if ((double) Math.floor(Math.random() * (totalGenes)) + 1 < mutationRate) {
                    StringBuffer aux = new StringBuffer(a[i]);
                    if (a[i].charAt(j) == '0') {
                        aux.setCharAt(j, '1');
                    } else {
                        aux.setCharAt(j, '0');
                    }
                    a[i] = aux.toString();

                }

            }

        }
    }

    // Calcul fitness of each indivudal
    public static int fitnessCalcul(String ch, String a[], int b[]) {
        int sumfitness = 0;

        for (int i = 0; i < a.length; i++) {
            int fitness = 0;
            for (int j = 0; j < ch.length(); j++) {
                if (ch.charAt(j) == a[i].charAt(j)) {
                    fitness++;
                }
            }
            sumfitness += fitness;
            b[i] = fitness;
        }
        return sumfitness;
    }

    public static void ChanceOfreproduction(String ch, String initpop[], double a[], int b[]) {
        for (int i = 0; i < b.length; i++) {
            a[i] = (double) b[i] / fitnessCalcul(ch, initpop, b);
        }
    }

    public static BufferedImage resizingImage(BufferedImage image, int index) {
        int newImageWidth = image.getWidth() * index;
        int newImageHeight = image.getHeight() * index;
        BufferedImage resizedImage = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newImageWidth, newImageHeight, null);
        g.dispose();
        return resizedImage;
    }

    public static String createAnInitialPopulation(int mat[][], String InitialPopulation[], int ligne) {
        String chaine = "";
        String ch1 = "";
        String ch2 = "";
        String ch3 = "";
        String ch4 = "";
        for (int i = 0; i < mat.length; i++) {
            chaine += mat[ligne][i];
        }
        System.out.println("Individual origine : " + chaine);

        // Creating Individuals in population
        for (int i = 0; i < chaine.length(); i++) {
            ch1 = ch1 + (int) Math.floor(Math.random() * 2);
            ch2 = ch2 + (int) Math.floor(Math.random() * 2);
            ch3 = ch3 + (int) Math.floor(Math.random() * 2);
            ch4 = ch4 + (int) Math.floor(Math.random() * 2);
        }

        // List of Initial population
        InitialPopulation[0] = ch1;
        InitialPopulation[1] = ch2;
        InitialPopulation[2] = ch3;
        InitialPopulation[3] = ch4;

        //Affichage Initial Population
        System.out.println("Initial population : ");
        for (int i = 0; i < InitialPopulation.length; i++) {
            System.out.println(InitialPopulation[i]);
        }
        return chaine;
    }

    public static BufferedImage convertMatToImage(int mat1[][]) {
        // After Genetic Algorithm Matrix image result
        BufferedImage ImageDest = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        System.out.println("Matrice of image after GA :");
        for (int j = 0; j < mat1.length; j++) {
            for (int k = 0; k < mat1.length; k++) {
                if (mat1[j][k] == 0) {

                    int rgb = new Color(0, 0, 0).getRGB();
                    ImageDest.setRGB(k, j, rgb);

                } else {
                    int rgb = new Color(255, 255, 255).getRGB();
                    ImageDest.setRGB(k, j, rgb);
                }
                System.out.print(ImageDest.getRGB(j, k) + " ");

            }
            System.out.println();

        }
        return ImageDest;
    }

    public static void codage(String path, int mat[][]) {
        File input = new File(path);
        try {

            BufferedImage image = ImageIO.read(input);
            int width = image.getWidth();
            int height = image.getHeight();

            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    if (c.getRed() < 32 && c.getBlue() < 32 && c.getGreen() < 32) {
                        mat[i][j] = 0;
                    } else if (c.getRed() > 223 && c.getBlue() > 223 && c.getGreen() > 223) {
                        mat[i][j] = 1;
                    }

                }

            }

            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    System.out.print(mat[j][k] + " ");

                }
                System.out.println();

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        try {
            int mat[][] = new int[10][10];
            int mat1[][] = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
            codage("image.jpg", mat);
            for (int t = 0; t < mat.length; t++) {
                String[] InitialPopulation = new String[4];
                int[] fitnessArray = new int[4];
                double[] chanceOfReproduction = new double[4];
                //Create an Initial population for each ligne of mat
                String chaine = createAnInitialPopulation(mat, InitialPopulation, t);

                while (fitnessArray[0] != InitialPopulation[0].length() && fitnessArray[1] != InitialPopulation[0].length() && fitnessArray[2] != InitialPopulation[0].length() && fitnessArray[3] != InitialPopulation[0].length()) {

                    //Affichage of each indivudal
                    int sumfitness = fitnessCalcul(chaine, InitialPopulation, fitnessArray);
                    for (int i = 0; i < fitnessArray.length; i++) {
                        System.out.println(fitnessArray[i]);
                    }

                    ChanceOfreproduction(chaine, InitialPopulation, chanceOfReproduction, fitnessArray);
                    // Affichage Chance Of reproduction of each individual
                    for (int i = 0; i < chanceOfReproduction.length; i++) {
                        System.out.println(chanceOfReproduction[i]);
                    }
                    System.out.println();
                    // Affichage Popution after sort with chance of reproduction
                    Sort(InitialPopulation, chanceOfReproduction);

                    System.out.println("Sort");
                    for (int i = 0; i < chanceOfReproduction.length; i++) {
                        System.out.println(chanceOfReproduction[i] + " " + InitialPopulation[i]);

                    }

                    // Croissement
                    Croissement(InitialPopulation);
                    System.out.println("After croissement");
                    for (int i = 0; i < InitialPopulation.length; i++) {
                        System.out.println(InitialPopulation[i]);
                    }
                    // Mutation
                    Mutation(InitialPopulation);
                    System.out.println("After Mutation");
                    for (int i = 0; i < InitialPopulation.length; i++) {
                        System.out.println(InitialPopulation[i]);

                    }
                    int test = fitnessCalcul(chaine, InitialPopulation, fitnessArray);
                }

                // final image of individuals (after AG )
                if (InitialPopulation[0].equals(chaine)) {
                    for (int i = 0; i < InitialPopulation[0].length(); i++) {
                        mat1[t][i] = (int) InitialPopulation[0].charAt(i) - 48;

                    }
                }

                if (InitialPopulation[1].equals(chaine)) {
                    for (int i = 0; i < InitialPopulation[1].length(); i++) {
                        mat1[t][i] = (int) InitialPopulation[1].charAt(i) - 48;

                    }
                }

                // After Genetic Algorithm Matrix image result
                BufferedImage ImageDest = convertMatToImage(mat1);
                // ImageDest zoom (*index)
                BufferedImage resizedImage = resizingImage(ImageDest, 30);

                //JFrame
                JFrame frame = new JFrame("Algorithm Genetic");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.validate();
                ImageIcon image1 = new ImageIcon(resizedImage);
                frame.setSize(image1.getIconWidth(), image1.getIconHeight());
                // Draw the Image data into the BufferedImage
                JLabel label1 = new JLabel(" ", image1, JLabel.CENTER);
                frame.getContentPane().add(label1);
                frame.setSize(500, 500);
                frame.setVisible(true);
                JLabel label = new JLabel("Ligne"+(int)(t+1));
                frame.add(label);
            }
        } catch (Exception e) {
            System.out.println("error " + e);
        }

    }
}
