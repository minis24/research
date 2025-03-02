package stream.usage.stream_slicing;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public enum Type {
        MEAT, FISH, OTHER
    }

    public Dish(String name, boolean vegetarian,int calories,Type type){
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;

    }


     public int getCalories (){
        return this.calories;
     }


     public String getName(){
        return this.name;
     }
     
     public boolean isVegetarian (){
         return this.vegetarian;
      }  
     
     public Type getType() {
    	 return type;
    }
     
     @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return this.name;
    }
}
