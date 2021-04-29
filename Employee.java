public class Employee implements Comparable<Employee>{
    private String name;

    public Employee(String n) {
        this.name=n;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public int compareTo(Employee e2){
        return this.name.compareTo(e2.name);
    }
}