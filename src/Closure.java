import java.util.function.Function;

// Beispiel Capturing in Java
public class Closure {
    public static void main(String[] args) {
        int lunchTime = 12;

        int finalLunchTime = lunchTime;
        Function<Integer, Boolean> isLunchTime = (hour) -> hour == finalLunchTime;

        lunchTime += 1;

        System.out.println((isLunchTime.apply(12)));
    }
}
