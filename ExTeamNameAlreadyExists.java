public class ExTeamNameAlreadyExists extends Exception {
    public ExTeamNameAlreadyExists() {
        super("Team name already exists!");
    }

    public ExTeamNameAlreadyExists(String message) { 
        super(message);
    }
}