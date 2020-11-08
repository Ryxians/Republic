package homebrew.republic;

import homebrew.republic.interfaces.Electable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Election {
    // Candidate
    private class Candidate {
        UUID id;
        Electable electable;
        int votes = 0;
        Candidate(UUID id) {
            this.id = id;
        }
    }


    // Config access
    ConfigAccessor configAccessor;

    // Population of candidates
    int pop = 0;

    // Max population of candidates
    int size = 9;

    // Collection of political candidates
    Candidate[] candidates = new Candidate[size];

    public Election(JavaPlugin plugin) {
        configAccessor = new ConfigAccessor(plugin, "Election.yml");
    }

    public boolean register(Electable electable) {
        Candidate candidate = new Candidate(electable.getUniqueId());
        candidate.electable = electable;
        return insert(candidate);
    }

    private boolean insert(Candidate candidate) {
        boolean rc = false;
        if (pop < size) {
            candidates[pop] = candidate;
            pop++;
            rc = true;
        }

        return rc;
    }

    private int find(UUID id) {
        int rc = -1;
        for (int i = 0; i < pop; i++) {
            if (candidates[i].id == id) {
                rc = i;
                break;
            }
        }

        return rc;
    }

    public boolean vote(Electable electable) {
        // Get unique id
        UUID id = electable.getUniqueId();

        // Find candidate in the array
        int c = find(id);
        boolean rc = false;

        // Increase their votes
        // Sort
        if (c > -1) {
            candidates[c].votes++;
            sort(c);
            rc = true;
        }

        // If returning false
        // Candidate doesn't exist
        return rc;
    }

    private void sort(int index) {
        // if index is greater than 0 and index-1 is smaller, swap.
        if (index > 0)
        if (candidates[index].votes > candidates[index-1].votes) {
            Candidate temp = candidates[index];
            candidates[index] = candidates[index -1];
            candidates[index - 1] = temp;
            sort(index--);
        }
    }

    public UUID getBest() {
        return candidates[0].id;
    }

}
