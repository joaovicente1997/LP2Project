package edu.ufp.esocial.api.util.graphs;

import edu.ufp.esocial.api.model.Follower;
import edu.ufp.esocial.api.service.Database;

public class FollowersUtil {

    public Follower findFollower(int realId, String type, Database database) {
        for (Integer id : database.getFollowerST().keys()) {
            if (database.getFollowerST().get(id).getType().equals(type) && database.getFollowerST().get(id).getRealId() == realId) {
                return database.getFollowerST().get(id);
            }
        }
        return null;
    }
}
