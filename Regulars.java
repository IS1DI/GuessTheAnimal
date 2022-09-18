package animals;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.Locale;
import java.util.Random;

public class Regulars {
    public static final String[] helloMorning ={
            "Good morning!",
            "Hi, Early Bird"
    };
    public static final String[] helloAfternoon ={
            "Good afternoon!"
    };
    public static final String[] helloEvening ={
            "Good evening!"
    };
    public static final String[] helloNight ={
            "Good night!",
            "Hi, Night Owl!"
    };
    public static final String[] bye ={
            "Have a nice day!",
            "See you soon!",
            "Bye!",
            "Good Bye!"
    };
    public static final String[] positive = {
            "y",
            "yes",
            "yeah",
            "yep",
            "sure",
            "right",
            "affirmative",
            "correct",
            "indeed",
            "you bet",
            "exactly",
            "you said it"
    };
    public static final String[] negative = {
            "n",
            "no",
            "no way",
            "nah",
            "nope",
            "negative",
            "i don't think so",
            "yeah no"
    };
    public static final String[] question = {
            "I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?",
            "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?",
            "Oh, no, don't try to confuse me: say yes or no."
    };
    public static String getRandomHello(){
        Random rand = new Random();
        LocalTime now = LocalTime.now();
        if(isMorning(now)){
            return helloMorning[rand.nextInt(helloMorning.length)];
        }else if (isEvening(now)){
            return helloEvening[rand.nextInt(helloEvening.length)];
        }else if (isAfternoon(now)){
            return helloAfternoon[rand.nextInt(helloAfternoon.length)];
        }else{
            return helloNight[rand.nextInt(helloNight.length)];
        }
    }
    public static String getRandomBye(){
        Random rand = new Random();
        return bye[rand.nextInt(bye.length)];

    }
    public static String getRandomQuestion(){
        Random rand = new Random();
        return question[rand.nextInt(question.length)];

    }
    public static boolean isMorning(LocalTime now){
        if(now.isAfter(LocalTime.of(5,0,0))&&now.isBefore(LocalTime.of(12,0,0))){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isAfternoon(LocalTime now){
        if(now.isAfter(LocalTime.of(12,0,0))&&now.isBefore(LocalTime.of(18,0,0))){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isEvening(LocalTime now){
        if(now.isAfter(LocalTime.of(18,0,0))&&now.isBefore(LocalTime.of(22,0,0))){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isNight(LocalTime now){
        if(now.isAfter(LocalTime.of(22,0,0))||now.isBefore(LocalTime.of(5,0,0))){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isPositive(String input){
        for(String string :positive){
            if(string.toLowerCase(Locale.ROOT).equals(input)){
                return true;
            }else {
                String f = string +".";
                if (f.toLowerCase(Locale.ROOT).equals(input)) {
                    return true;
                }else {
                    f = string +"!";
                    if (f.toLowerCase(Locale.ROOT).equals(input)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isNegative(String input) {
        for (String string : negative) {
            if (string.toLowerCase(Locale.ROOT).equals(input)) {
                return true;
            }else {
                String f = string +".";
                if (f.toLowerCase(Locale.ROOT).equals(input)) {
                    return true;
                }else {
                    f = string +"!";
                    if (f.toLowerCase(Locale.ROOT).equals(input)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

enum Days{
    MORNING(0,"morning"),
    AFTERNOON(1,"afternoon"),
    EVENING(2,"evening"),
    NIGHT(3,"night");

    private int type;
    private String stringType;
    Days(int type, String stringType){
        this.type = type;
        this.stringType = stringType;
    }


    public int getType() {
        return type;
    }

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }

    public void setType(int type) {
        this.type = type;
    }
}
class Node {
    String value;
    Node left;
    Node right;

    Node(@JsonProperty("field_name") String value) {
        this.value = value;
        right = null;
        left = null;
    }

}
