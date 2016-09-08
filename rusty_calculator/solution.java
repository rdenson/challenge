/**
 *
 * @author richard.denson
 */
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class solution {
    /*public static boolean hasTooManyNumbers(String eq, int threshold){
        Pattern regex = Pattern.compile("[0-9]");
        Matcher matches = regex.matcher(eq);
        int digitsFound = 0;
        
        while(matches.find()){
            digitsFound++;
        }
        
        return digitsFound > threshold;
    }
    public static boolean hasValidContent(String eq){
        return eq.matches("^[0-9+*]+$");
    }*/
    public static String answer(String str) throws Exception{
        Stack<Character> opPool = new Stack<>();
        String res = "";
        
        /*if( hasTooManyNumbers(str, 100) ){
            throw new Exception("too many digits");
        }
        
        if( !hasValidContent(str) ){
            throw new Exception("equation does not conform to standard");
        }*/
        
        //parse input
        for(int i = 0; i<str.length(); i++){
            char curr = str.charAt(i);
            
            //handle numbers
            if( (int)curr >= 48 && (int)curr <= 57 ){
                res += Character.toString(curr);
            }
            //handle operators
            else{
                if( opPool.isEmpty() || (int)curr <= (int)opPool.peek() ){
                    opPool.push(curr);
                /*} else if( (int)curr <= (int)opPool.peek() ){
                        opPool.push(curr);*/
                } else{
                    while( !opPool.isEmpty() && (int)curr > (int)opPool.peek() ){
                        res += opPool.pop();
                    }
                    opPool.push(curr);
                }
            }
        }
        
        while(!opPool.empty()){
            res += opPool.pop();
        }
        
        return res;
    }
    
    public static void main(String[] args){
        try{
            //String ret = answer("2+3*2");
            String ret = answer("2*4*3+9*3+5");
            //String ret = answer("5+1+2*4+3");
            System.out.println(ret);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
