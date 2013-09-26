package base;

public class IntegerId<T> {

    private Integer id;

    public IntegerId(Integer id) {
        this.id = id;
    }

    public Integer get() {
        return id;
    }

    public void set(Integer id){
        this.id = id;

    }

    public boolean equals(Object obj) {
        if(obj instanceof IntegerId) {
            return ((IntegerId) obj).get() == id;
        }
        return false;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public String toString() {
        return id.toString();
    }
}
