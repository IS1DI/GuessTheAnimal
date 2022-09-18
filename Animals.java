package animals;

import java.security.PublicKey;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Animals {
    public static final String[] articles = {
            "a",
            "an",
            "the"
    };
    public static String addArticle(String input){
        if(needToReplaceArticle(input)){
            String[] inputt = input.trim().split(" ");
            if(isGlasnaya(inputt[1].toLowerCase(Locale.ROOT).charAt(0))){
                input = "an";
                for(int i = 1;i< inputt.length;i++){
                    input+=" " +inputt[i];
                }
                return input;
            }else{
                input = "a";
                for(int i = 1;i< inputt.length;i++){
                    input+=" " +inputt[i];
                }
                return input;
            }
        }else
        if(!needToAddArticle(input)){
            return input.trim();
        }else{
            String[] inputt = input.trim().split(" ");
            if(isGlasnaya(inputt[0].toLowerCase(Locale.ROOT).charAt(0))){
                input = "an";
                for(int i = 0;i< inputt.length;i++){
                    input+=" " +inputt[i];
                }
                return input;
            }else{
                input = "a";
                for(int i = 0;i< inputt.length;i++){
                    input+=" " +inputt[i];
                }
                return input;
            }
        }
    }

    public static int isCanHasIs(String input){
        Pattern pattern = Pattern.compile("It (can|has|is)",Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(input);
        if(match.find()){
            String g = match.group().toLowerCase();
            if(g.equals(ItCanHasIs.CAN.getValue().toLowerCase())){
                return ItCanHasIs.CAN.getValueInt();
            }else if(g.equals(ItCanHasIs.HAS.getValue().toLowerCase())){
                return ItCanHasIs.HAS.getValueInt();
            }else if(g.equals(ItCanHasIs.IS.getValue().toLowerCase())){
                return ItCanHasIs.IS.getValueInt();
            }else{
                return ItCanHasIs.ERROR.getValueInt();
            }
        }else {
            return -1;
        }
    }

    public static int isCanHasIsQuestion(String input){
        Pattern pattern = Pattern.compile("^((can|is)\\s+it|(does\\s+it\\s+have))",Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(input);
        if(match.find()){
            String g = match.group().toLowerCase();
            if(g.equals(ItCanHasIs.CAN.getQuestion().toLowerCase())){
                return ItCanHasIs.CAN.getValueInt();
            }else if(g.equals(ItCanHasIs.HAS.getQuestion().toLowerCase())){
                return ItCanHasIs.HAS.getValueInt();
            }else if(g.equals(ItCanHasIs.IS.getQuestion().toLowerCase())){
                return ItCanHasIs.IS.getValueInt();
            }else{
                return ItCanHasIs.ERROR.getValueInt();
            }
        }else {
            return -1;
        }
    }

    public static boolean isGlasnaya(char i){
        if(i == 'a' || i == 'e' || i == 'i' || i == 'o' || i == 'u' || i =='y'){
            return true;
        }
        return false;
    }
    public static boolean needToAddArticle(String inputt){
        String[] input = inputt.trim().split(" ");
        if(input[0].toLowerCase(Locale.ROOT).equals(articles[0]) || input[0].toLowerCase(Locale.ROOT).equals(articles[1])){
            return false;
        }
        return true;

    }
    public static boolean needToReplaceArticle(String inputt){
        String[] input = inputt.trim().split(" ");
        if(input[0].toLowerCase(Locale.ROOT).equals(articles[2])){
            return true;
        }
        return false;
    }
    public static String deleteArticle(String inputt){
        String input = inputt.trim().toLowerCase();
        Pattern pattern = Pattern.compile("^(a|an|the)\\s+.+",Pattern.CASE_INSENSITIVE);
        if(pattern.matcher(input).matches()) {
            input = input.replaceFirst("^(a|an|the)\\s+", "");
        }
        return input.trim();
    }
    public static String deleteItCanHasIs(String inputt){
        String input = inputt.trim().toLowerCase();
        return input.replaceFirst("^(it is|it can|it has)","");
    }
    public static String replaceCanHasIsOnQue(String inputt){
        String input = inputt.trim().toLowerCase();
        int status = Animals.isCanHasIsQuestion(input);
        return ItCanHasIs.getPositiveByValueInt(status,"normal") + input.replaceFirst("^(is\\s+it|can\\s+it|does\\s+it\\s+have)","").replaceFirst("[?]$","");

    }
    public static String replaceCanHasIsOnQue(String inputt,String v){
        String input = inputt.trim().toLowerCase();
        int status = Animals.isCanHasIsQuestion(input);
        return "It "+ItCanHasIs.getPositiveByValueInt(status,v) + input.replaceFirst("^(is\\s+it|can\\s+it|does\\s+it\\s+have)","").replaceFirst("[?]$","");

    }
    public static String deleteDote(String inputt){
        String input = inputt.trim();
        if(input.charAt(input.length()-1) == '.'){
            inputt = input;
            input = "";
            for(int i = 0; i <inputt.length()-1;i++){
                input += Character.toString(inputt.charAt(i));
            }
        }
        return input;
    }
    public static boolean hasArticle(String inputt){
        String input = inputt.trim();
        return input.matches("^(a|an)\\s+.+");
    }
}
enum ItCanHasIs{
    CAN("It can","Can it","can","can't",1),
    HAS("It has","Does it have","has","doesn't have",2),
    IS("It is","Is it","is","isn't",3),
    ERROR("error","error","error","error",-1);
    private String value;
    private String question;
    private String utver;
    private String otric;
    private int valueInt;

    ItCanHasIs(String value,String question,String utver,String otric,int valueInt){
        this.value = value;
        this.question = question;
        this.otric = otric;
        this.utver = utver;
        this.valueInt = valueInt;
    }
    public static String getPositiveByValueInt(int valueInt,String type /*'positive';"negative";"question";"normal"*/){
        for(ItCanHasIs val:ItCanHasIs.values()){
            if(val.valueInt==valueInt){
                if (type.equals("positive")) {
                    return val.utver;
                }else if(type.equals("negative")){
                    return val.otric;
                }else if(type.equals("question")){
                    return val.question;
                }else if(type.equals("normal")){
                    return val.value;
                }
            }
        }
        return ItCanHasIs.ERROR.utver;
    }

    public String getValue() {
        return value;
    }

    public String getQuestion() {
        return question;
    }

    public int getValueInt() {
        return valueInt;
    }
}
