import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.print("Please input the file pathname: ");
        String filepathname = in.nextLine();
        Scanner inFile = new Scanner(new File(filepathname));

        Company company = Company.getInstance();

        // Set the system date
        // try {
        String cmdLine1 = inFile.nextLine();
        String[] cmdLine1Parts = cmdLine1.split("\\|");
        System.out.println("\n> " + cmdLine1);
        SystemDate.createTheInstance(cmdLine1Parts[1]);
        // } catch () {
        // }

        while (inFile.hasNext()) {
            String cmdLine = inFile.nextLine();

            // Blank lines exist in data file as separators. Skip them.
            if (cmdLine.equals(""))
                continue;

            System.out.println("\n> " + cmdLine);

            String[] cmdParts = cmdLine.split("\\|");
            for (int i = 0; i < cmdParts.length; i++)
                cmdParts[i] = cmdParts[i].trim();

            if (cmdParts[0].equals("undo"))
                RecordedCommand.undoOneCommand();
            else if (cmdParts[0].equals("redo"))
                RecordedCommand.redoOneCommand();
            else if (cmdParts[0].equals("startNewDay"))
                (new CmdStartNewDay()).execute(cmdParts);
            else if (cmdParts[0].equals("hire"))
                (new CmdHire()).execute(cmdParts);
            else if (cmdParts[0].equals("setupTeam"))
                (new CmdSetupTeam()).execute(cmdParts);
            else if (cmdParts[0].equals("joinTeam"))
                (new CmdJoinTeam()).execute(cmdParts);
            else if (cmdParts[0].equals("createProject"))
                (new CmdCreateProject()).execute(cmdParts);
            else if (cmdParts[0].equals("createExtensionProject"))
                (new CmdCreateExtensionProject()).execute(cmdParts);
            else if (cmdParts[0].equals("assignProject"))
                (new CmdAssignProject()).execute(cmdParts);
            else if (cmdParts[0].equals("markCompletion"))
                (new CmdMarkCompletion()).execute(cmdParts);
            else if (cmdParts[0].equals("listEmployees"))
                (new CmdListEmployees()).execute(cmdParts);
            else if (cmdParts[0].equals("giveAssignmentSuggestions"))
                (new CmdGiveAssignmentSuggestions()).execute(cmdParts);
            else if (cmdParts[0].equals("listTeams"))
                (new CmdListTeams()).execute(cmdParts);
            else if (cmdParts[0].equals("listProjects"))
                (new CmdListProjects()).execute(cmdParts);
            else if (cmdParts[0].equals("listProjectStaff"))
                (new CmdListProjectStaff()).execute(cmdParts);
            else if (cmdParts[0].equals("listTeamProjects"))
                (new CmdListTeamProjects()).execute(cmdParts);
            else if (cmdParts[0].equals("listStaffParticipations"))
                (new CmdListStaffParticipations()).execute(cmdParts);
            else
                System.out.println("Error");
            // throw new ExWrongCommand();
        }

        inFile.close();
        in.close();
    }
}