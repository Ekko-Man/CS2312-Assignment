public class ExProjectIsNotFound extends Exception {
    public ExProjectIsNotFound() {
        super("Project is not found!");
    }

    public ExProjectIsNotFound(String message) {
        super(message); // Project P003 is not found!
    }
}
