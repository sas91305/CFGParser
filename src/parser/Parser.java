package parser;
/*
*set -> { list }
*list -> head tail | ε
*head -> number | set
*tail -> , head tail | ε
*
*Write a recursive descent parser for this CFG.  Use Java and submit your answer
*as a Netbeans or IntelliJ project to the O: drive dropbox for this class.
*
*Note: when you write your parser, you can assume that a scanner has already
*tokenized the input.  For simplicity, assume that it deletes white space and
*replaces each number with the letter n.  For example, when the set is 
*{ {1, 2}, 3}, the input to your scanner is the string {{n,n},n}
*
*Author: Seth Schaller
*/
import java.util.Scanner;
public class Parser {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Input a context free grammer string with numbers "
                + "replaced with 'n', enter q when finished.");
        String input = reader.nextLine();
        
        while(!input.equals("q")){ //Program runs until user enters q
            boolean status = set(input);
            if(status == true)
                System.out.println("Valid");
            else
                System.out.println("Invalid");
            
            System.out.println("Input a context free grammer string with numbers "
                + "replaced with 'n', enter q when finished.");
            input = reader.nextLine();
        }
    }
    
    static boolean set(String input){
        if(input.charAt(0) == '{' || input.charAt(input.length()-1) == '}'){
            input = input.substring(1, input.length()-1); //takes away front and back braces
            return list(input);
        }
        return false;
    }   
    
    static boolean list(String input){
        if(input.length() == 0)//if no contents
            return true;
        else{
            boolean endlist = true;//checks for back brace or end of a set            
            for(int i = 0; i < input.length(); i++){
                if(input.charAt(i) == '{')
                    endlist = false;
                else if(input.charAt(i) == '}')
                    endlist = true;
                
                if(input.charAt(i) == ',' && endlist){
                    String head = input.substring(0, i);
                    input = input.substring(i);
                    
                    boolean headcheck = head(head);//calls head method
                    if(!headcheck)
                        return false;
                    boolean tailcheck = tail(input);//calls tail method
                    if(tailcheck)
                        return true;
                }
            }
            return false;
        }
    }   
    
    static boolean head(String head){       
        if(head.length() == 0)//no contents
            return true;
        if(head.charAt(0) == 'n')//is a number
            return set(head.substring(1, head.length()));
        if(head.charAt(0) == '{' && head.charAt(head.length()-1) == '}')//is a set
            return set(head);
        return false;
    }   
    
    static boolean tail(String tail){
        if(tail.length() == 0)//no contents
            return true;
        
        if(tail.length() >= 2){ //checks if tail is too small
            tail = tail.substring(1);
            if(tail.length() >= 3) //checks if tail is more sets
                return list(tail) || head(tail);
            
            if(!tail.contains(",") && !tail.contains("{") && !tail.contains("}"))//checks epsilon
                return true;
        }
        return false;
    }    
}
