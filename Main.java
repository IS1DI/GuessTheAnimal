package animals;

import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println(Regulars.getRandomHello());
        BinarySearchTree animalsAndFacts;
        String type = parseArgs(args);
        SearchTree file = new SearchTree(type);
        String animal;
        if (file.loadKnowledge() == null) {
            System.out.print("I want to learn about animals.\n" +
                    "Which animal do you like most?\n>");
            animal = scan.nextLine().trim().toLowerCase();
            if (!Animals.hasArticle(animal)) {
                animal = Animals.addArticle(animal);
            }
            animalsAndFacts = new BinarySearchTree(animal);
            file.saveKnowledge(animalsAndFacts.getRoot());

        } else {
            animalsAndFacts = new BinarySearchTree(file.loadKnowledge());
            animal = animalsAndFacts.getCurrentValue();
        }
        int menuNum;
        do {
            menuPrint();
            do {
                menuNum = scan.nextInt();
            }while(menuNum<0||menuNum>5);
            switch (menuNum){
                case 0:
                    break;
                case 1:
                    scan.nextLine();
                    menu1(scan,animalsAndFacts,animal,file);
                    break;
                case 2:
                    SortedSet<String> set = new TreeSet<>();
                    set = BinarySearchTree.allLeafs(set,animalsAndFacts.getRoot());
                    System.out.print("Here are the animals I know:");
                    set.forEach(x -> System.out.printf("\n - %s",x));
                    System.out.print("\n");
                    break;
                case 3:
                    System.out.print("Enter the animal:\n");
                    scan.nextLine();
                    String findAnimal = Animals.deleteArticle(scan.nextLine());
                    Pattern pattern = Pattern.compile(String.format("(a|an|the){0,1}\\s+%s",findAnimal));
                    if(BinarySearchTree.isValueInTree(animalsAndFacts.getRoot(),pattern)){
                        System.out.printf("Facts about the %s:",findAnimal);
                        Stack<String> facts = new Stack<>();
                        facts = BinarySearchTree.factsAboutAnimal(animalsAndFacts.getRoot(),pattern,facts);
                        Stack<String> nf = new Stack<>();
                        while(!facts.isEmpty()){
                            nf.push(facts.pop());
                        }
                        while(!nf.isEmpty()){
                            System.out.printf("\n - %s",nf.pop());
                        }

                    }else{
                        System.out.printf("No facts about the %s.\n",findAnimal);
                    }
                    System.out.print("\n");
                    break;
                case 4:
                    SortedMap<String,Integer> map = new TreeMap<>();
                    map = BinarySearchTree.animalDepths(map,animalsAndFacts.getRoot(),0);
                    float avg = 0;
                    int minimum = map.get(map.firstKey());
                    for (int n :
                            map.values()) {
                        avg += n;
                        minimum = Math.min(n,minimum);
                    }
                    avg /= map.size();
                    System.out.printf("The Knowledge Tree stats\n" +
                            "\n" +
                            "- root node                    %s.\n" +
                            "- total number of nodes        %d\n" +
                            "- total number of animals      %d\n" +
                            "- total number of statements   %d\n" +
                            "- height of the tree           %d\n" +
                            "- minimum animal's depth       %d\n" +
                            "- average animal's depth       %.1f\n",Animals.replaceCanHasIsOnQue(animalsAndFacts.getRoot().value),
                            BinarySearchTree.numberOfNodes(animalsAndFacts.getRoot()),
                            BinarySearchTree.numberOfAnimals(animalsAndFacts.getRoot()),
                            BinarySearchTree.numberOfNodes(animalsAndFacts.getRoot())-BinarySearchTree.numberOfAnimals(animalsAndFacts.getRoot()),
                            BinarySearchTree.heightOfTree(animalsAndFacts.getRoot())-1,
                            minimum,avg)   ;
                    break;
                case 5:
                    BinarySearchTree.printTree(animalsAndFacts.getRoot());
                    System.out.print("\n");
                    break;

            }
            file.saveKnowledge(animalsAndFacts.getRoot());
        }while(menuNum!=0);
        System.out.printf("\n%s", Regulars.getRandomBye());
    }

    public static String parseArgs(String... args) {
        return Arrays.stream(args)
                .dropWhile(str -> !str.equals("-type"))
                .skip(1)
                .map(String::toLowerCase)
                .findFirst()
                .orElse("json");
    }

    public static void menuPrint() {
        System.out.print("Welcome to the animal expert system!\n" +
                "\n" +
                "What do you want to do:\n" +
                "\n" +
                "1. Play the guessing game\n" +
                "2. List of all animals\n" +
                "3. Search for an animal\n" +
                "4. Calculate statistics\n" +
                "5. Print the Knowledge Tree\n" +
                "0. Exit\n" +
                "> ");
    }

    public static void menu1(Scanner scan, BinarySearchTree animalsAndFacts, String animal, SearchTree file) {
        System.out.printf("Let's play a game!\n" +
                "You think of an animal, and I guess it.\n" +
                "Press enter when you're ready.\n>");
        scan.nextLine();


        String output = animalsAndFacts.getCurrentValue();
        if (Animals.isCanHasIsQuestion(output) != -1) {
            System.out.printf("%s\n>", output);
        } else {
            System.out.printf("%s %s?\n>", ItCanHasIs.IS.getQuestion(), output);
        }
        boolean exit = false;
        String askk;
        do {
            askk = scan.nextLine().toLowerCase().trim();
            if (Regulars.isPositive(askk) || Regulars.isNegative(askk)) {


                if (Regulars.isNegative(askk) && !animalsAndFacts.hasNext()) {
                    System.out.printf("I give up. What animal do you have in mind?\n>");
                    String new_animal = scan.nextLine().trim().toLowerCase();
                    if (!Animals.hasArticle(new_animal)) {
                        new_animal = Animals.addArticle(Animals.deleteArticle(new_animal));
                    }
                    System.out.printf("Specify a fact that distinguishes %s from %s. \nThe sentence should be of the format: 'It can/has/is ...'.\n>", Animals.addArticle(animal), Animals.addArticle(new_animal));


                    String question = scan.nextLine();
                    int status = Animals.isCanHasIs(question);
                    while (status == -1) {
                        System.out.printf("The examples of a statement:\n" +
                                " - It can fly\n" +
                                " - It has horn\n" +
                                " - It is a mammal\n" +
                                "Specify a fact that distinguishes %s from %s\n" +
                                "The sentence should be of the format: 'It can/has/is ...'.\n>", animal, new_animal);
                        question = scan.nextLine();
                        status = Animals.isCanHasIs(question);
                    }
                    question = Animals.deleteDote(question);
                    System.out.printf("Is the statement correct for %s?\n>", new_animal);
                    String ask;
                    do {
                        ask = scan.nextLine().toLowerCase().trim();
                        if (Regulars.isPositive(ask) || Regulars.isNegative(ask)) {
                            System.out.printf("I learned the following facts about animals:\n - The %s %s%s.\n - The %s %s%s.", Animals.deleteArticle(animal), ItCanHasIs.getPositiveByValueInt(status, Regulars.isNegative(ask) ? "positive" : "negative"), Animals.deleteItCanHasIs(question),
                                    /*надо добавить в дерево*/                      Animals.deleteArticle(new_animal), ItCanHasIs.getPositiveByValueInt(status, Regulars.isPositive(ask) ? "positive" : "negative"), Animals.deleteItCanHasIs(question));
                            animalsAndFacts.add(String.format("%s%s?", ItCanHasIs.getPositiveByValueInt(status, "question"), Animals.deleteItCanHasIs(question)), animal, new_animal, Regulars.isNegative(ask) ? true : false);
                            System.out.printf("\nI can distinguish these animals by asking the question:\n - %s%s?", ItCanHasIs.getPositiveByValueInt(status, "question"), Animals.deleteItCanHasIs(question));
                        } else {
                            System.out.printf("%s \n>", Regulars.getRandomQuestion());
                        }
                    } while (!Regulars.isNegative(ask) && !Regulars.isPositive(ask));


                    System.out.printf("\nNice! I've learned so much about animals!\nWould you like to play again?\n>");
                    String askkk;
                    do {
                        askkk = scan.nextLine().toLowerCase().trim();
                        if (Regulars.isPositive(askkk) || Regulars.isNegative(askkk)) {
                            if (Regulars.isNegative(askkk)) {
                                exit = true;
                                break;
                            } else {
                                animalsAndFacts.rerun();
                                System.out.printf("Let's play a game!\n" +
                                        "You think of an animal, and I guess it.\n" +
                                        "Press enter when you're ready.\n>");
                                scan.nextLine();

                                String outputt = animalsAndFacts.getCurrentValue();
                                if (Animals.isCanHasIsQuestion(outputt) != -1) {
                                    System.out.printf("%s\n>", outputt);
                                } else {
                                    System.out.printf("%s %s?\n>", ItCanHasIs.IS.getQuestion(), outputt);
                                }
                            }
                        } else {
                            System.out.printf("%s \n>", Regulars.getRandomQuestion());
                        }
                    } while (!Regulars.isNegative(askkk) && !Regulars.isPositive(askkk));
                } else if (animalsAndFacts.hasNext()) {

                    animal = animalsAndFacts.getNext(Regulars.isPositive(askk));
                    if (Animals.isCanHasIsQuestion(animal) != -1) {
                        System.out.printf("%s\n>", animal);
                    } else {
                        System.out.printf("%s %s?\n>", ItCanHasIs.IS.getQuestion(), animal);
                    }
                } else {


                    if (Animals.isCanHasIs(animalsAndFacts.getCurrentValue()) == -1) {
                        System.out.printf("Congrats!\nWould you like to play again?\n>");
                        String askkk;
                        do {
                            askkk = scan.nextLine().toLowerCase().trim();
                            if (Regulars.isPositive(askkk) || Regulars.isNegative(askkk)) {
                                if (Regulars.isNegative(askkk)) {
                                    exit = true;
                                    break;
                                } else {
                                    animalsAndFacts.rerun();
                                    System.out.printf("Let's play a game!\n" +
                                            "You think of an animal, and I guess it.\n" +
                                            "Press enter when you're ready.\n>");
                                    scan.nextLine();

                                    String outputt = animalsAndFacts.getCurrentValue();
                                    if (Animals.isCanHasIsQuestion(outputt) != -1) {
                                        System.out.printf("%s\n>", outputt);
                                    } else {
                                        System.out.printf("%s %s?\n>", ItCanHasIs.IS.getQuestion(), outputt);
                                    }
                                }
                            } else {
                                System.out.printf("%s \n>", Regulars.getRandomQuestion());
                            }
                        } while (!Regulars.isNegative(askkk) && !Regulars.isPositive(askkk));
                    } else {
                        if (Regulars.isNegative(askk) && !animalsAndFacts.hasNext()) {
                            System.out.printf("I give up. What animal do you have in mind?\n>");
                            String new_animal = scan.nextLine().trim().toLowerCase();
                            if (!Animals.hasArticle(new_animal)) {
                                new_animal = Animals.addArticle(Animals.deleteArticle(new_animal));
                            }
                            System.out.printf("Specify a fact that distinguishes %s from %s. \nThe sentence should be of the format: 'It can/has/is ...'.\n>", Animals.addArticle(animal), Animals.addArticle(new_animal));


                            String question = scan.nextLine();
                            int status = Animals.isCanHasIs(question);
                            while (status == -1) {
                                System.out.printf("The examples of a statement:\n" +
                                        " - It can fly\n" +
                                        " - It has horn\n" +
                                        " - It is a mammal\n" +
                                        "Specify a fact that distinguishes %s from %s\n" +
                                        "The sentence should be of the format: 'It can/has/is ...'.\n>", animal, new_animal);
                                question = scan.nextLine();
                                status = Animals.isCanHasIs(question);
                            }
                            question = Animals.deleteDote(question);
                            System.out.printf("Is the statement correct for %s?\n>", new_animal);
                            String ask;
                            do {
                                ask = scan.nextLine().toLowerCase().trim();
                                if (Regulars.isPositive(ask) || Regulars.isNegative(ask)) {
                                    System.out.printf("I learned the following facts about animals:\n - The %s %s%s.\n - The %s %s%s.", Animals.deleteArticle(animal), ItCanHasIs.getPositiveByValueInt(status, Regulars.isNegative(ask) ? "positive" : "negative"), Animals.deleteItCanHasIs(question),
                                            /*надо добавить в дерево*/                      Animals.deleteArticle(new_animal), ItCanHasIs.getPositiveByValueInt(status, Regulars.isPositive(ask) ? "positive" : "negative"), Animals.deleteItCanHasIs(question));
                                    animalsAndFacts.add(String.format("%s%s?", ItCanHasIs.getPositiveByValueInt(status, "question"), Animals.deleteItCanHasIs(question)), Animals.deleteArticle(animal), Animals.deleteArticle(new_animal), Regulars.isNegative(ask) ? true : false);
                                    System.out.printf("\nI can distinguish these animals by asking the question:\n - %s%s?", ItCanHasIs.getPositiveByValueInt(status, "question"), Animals.deleteItCanHasIs(question));
                                } else {
                                    System.out.printf("%s \n>", Regulars.getRandomQuestion());
                                }
                            } while (!Regulars.isNegative(ask) && !Regulars.isPositive(ask));


                            System.out.printf("\nNice! I've learned so much about animals!\nWould you like to play again?\n>");
                            String askkk;
                            do {
                                askkk = scan.nextLine().toLowerCase().trim();
                                if (Regulars.isPositive(askkk) || Regulars.isNegative(askkk)) {
                                    if (Regulars.isNegative(askkk)) {
                                        exit = true;
                                        break;
                                    } else {
                                        animalsAndFacts.rerun();
                                        System.out.printf("Let's play a game!\n" +
                                                "You think of an animal, and I guess it.\n" +
                                                "Press enter when you're ready.\n>");
                                        scan.nextLine();


                                        String outputt = animalsAndFacts.getCurrentValue();
                                        if (Animals.isCanHasIsQuestion(outputt) != -1) {
                                            System.out.printf("%s\n>", outputt);
                                        } else {
                                            System.out.printf("%s %s?\n>", ItCanHasIs.IS.getQuestion(), outputt);
                                        }
                                    }
                                } else {
                                    System.out.printf("%s \n>", Regulars.getRandomQuestion());
                                }
                            } while (!Regulars.isNegative(askkk) && !Regulars.isPositive(askkk));


                        }
                    }
                }
            } else {
                System.out.printf("%s \n>", Regulars.getRandomQuestion());
            }
        } while (!exit);
        animalsAndFacts.rerun();
        file.saveKnowledge(animalsAndFacts.getRoot());
    }
}
