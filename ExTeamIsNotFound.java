public class ExTeamIsNotFound extends Exception {
    public ExTeamIsNotFound() {
        super("The Team was not found!");
    }

    public ExTeamIsNotFound(String message) {
        super(message); // Team SW Experts is not found!
    }
}
