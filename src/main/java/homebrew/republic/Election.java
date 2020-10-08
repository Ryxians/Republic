package homebrew.republic;

import java.util.UUID;

public class Election {
    private class Candidate {
        UUID player;
        int votes;

        Candidate next;
    }

    Candidate[] candidates = new Candidate[9];

}
