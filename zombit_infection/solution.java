/**
 *
 * @author richard.denson
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class solution {
    public static List infectArea(int[][] world, int startRow, int startCol, int compare){
        boolean validLocation = false;
        int colLoc = startCol;
        int rowLoc = startRow;
        List<HashMap> infectedNeighbors = new ArrayList<>();
        
        //down
        rowLoc++;
        /*
        * inspect location below start (down)
        * down is valid
        * AND
        * down is not already infected
        * AND
        * down can be infected
        */
        validLocation = rowLoc > -1 && rowLoc < world.length && colLoc > -1 && colLoc < world[rowLoc].length;
        if( validLocation && world[rowLoc][colLoc] != -1 &&  compare >= world[rowLoc][colLoc] ){
            //infect cell
            world[rowLoc][colLoc] = -1;
            //note location
            HashMap south = new HashMap(2);
            south.put("x", rowLoc);
            south.put("y", colLoc);
            infectedNeighbors.add(south);
        }
        //right
        rowLoc = startRow;
        colLoc++;
        validLocation = rowLoc > -1 && rowLoc < world.length && colLoc > -1 && colLoc < world[rowLoc].length;
        if( validLocation && world[rowLoc][colLoc] != -1 && compare >= world[rowLoc][colLoc] ){
            world[rowLoc][colLoc] = -1;
            HashMap east = new HashMap(2);
            east.put("x", rowLoc);
            east.put("y", colLoc);
            infectedNeighbors.add(east);
        }
        //up
        colLoc = startCol;
        rowLoc--;
        validLocation = rowLoc > -1 && rowLoc < world.length && colLoc > -1 && colLoc < world[rowLoc].length;
        if( validLocation && world[rowLoc][colLoc] != -1 && compare >= world[rowLoc][colLoc] ){
            world[rowLoc][colLoc] = -1;
            HashMap north = new HashMap(2);
            north.put("x", rowLoc);
            north.put("y", colLoc);
            infectedNeighbors.add(north);
        }
        //left
        rowLoc = startRow;
        colLoc--;
        validLocation = rowLoc > -1 && rowLoc < world.length && colLoc > -1 && colLoc < world[rowLoc].length;
        if( validLocation && world[rowLoc][colLoc] != -1 && compare >= world[rowLoc][colLoc] ){
            world[rowLoc][colLoc] = -1;
            HashMap west = new HashMap(2);
            west.put("x", rowLoc);
            west.put("y", colLoc);
            infectedNeighbors.add(west);
        }
        
        return infectedNeighbors;
    }
    public static int[][] answer(int[][] population, int x, int y, int strength){
        boolean rowValid = x > -1 && x < population.length;
        boolean columnValid = rowValid && y > -1 && y < population[x].length;
        Stack<List> toInfect = new Stack<>();
        
        //initial infection
        if( rowValid && columnValid && strength >= population[x][y] ){
            population[x][y] = -1;
            //cell infected! let's see who else we can infect...
            List<HashMap> initialCoords = infectArea(population, x, y, strength);
            
            //did the affected area yield more zombits?
            if( initialCoords.size() > 0 ){
                toInfect.push(initialCoords);
            }
        }
        
        //subsequent infections
        while(!toInfect.empty()){
            List<HashMap> currCoords = toInfect.pop();
            for(HashMap coord : currCoords){
                List<HashMap> newCoords = infectArea(population, (int)coord.get("x"), (int)coord.get("y"), strength);
                if( newCoords.size() > 0 ){
                    //accrue locations to attempt to infect
                    toInfect.push(newCoords);
                }
            }
        }
        
        return copy2D(population);
    }
    public static int[][] copy2D(int target[][]){
        int clone[][] = new int[target.length][];
        
        for(int i=0; i<target.length; i++){
            clone[i] = Arrays.copyOf(target[i], target[i].length);
        }
        
        return clone;
    }
    public static void print(int[][] target){
        for(int i=0; i<target.length; i++){
            for(int j=0; j<target[i].length; j++){
                if(target[i][j] >= 0){
                    System.out.print(" ");
                }
                System.out.print(target[i][j] + "  ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args){
        //int pop[][] = {{1,2,3},{2,3,4},{3,2,1}};
        //int pop_res[][] = {{-1,-1,3},{-1,3,4},{3,2,1}};
        //int pop[][] = {{6,7,2,7,6},{6,3,1,4,7},{0,2,4,1,10},{8,1,1,4,9},{8,7,4,9,9}};
        //int pop_res[][] = {{6,7,-1,7,6},{6,-1,-1,-1,7},{-1,-1,-1,-1,10},{8,-1,-1,-1,9},{8,7,-1,9,9}};
        int pop[][] = {{9,3,4,5,4},{1,6,5,4,3},{2,3,7,3,2},{3,4,5,8,1},{4,5,4,3,9}};
        int pop_res[][] = {{9,3,4,5,4},{-1,6,5,4,3},{-1,-1,7,3,2},{-1,-1,-1,8,1},{-1,-1,-1,-1,9}};
        
        System.out.println("-----------");
        print(pop);
        System.out.println("\r\nafter...");
        print(pop_res);
        System.out.println("-----------\r\n");
        
        
        //answer(pop, 0, 0, 2);
        answer(pop, 1, 1, 5);
        print(pop);
        /*int[][] copy = answer(pop, 2, 1, 5);
        System.out.println("\r\n.clone() test...");
        copy[0][0] = 0;
        print(copy);
        System.out.println("-----------");
        print(pop);*/
    }
}
