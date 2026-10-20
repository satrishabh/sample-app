package app;

/** Tiny self-check run during the Docker build (no test framework needed). */
public class MainTest {
    public static void main(String[] args) {
        String g = Main.greeting("AIO");
        if (!"Hello from AIO CI/CD".equals(g)) {
            throw new AssertionError("unexpected greeting: " + g);
        }
        System.out.println("tests passed");
    }
}
