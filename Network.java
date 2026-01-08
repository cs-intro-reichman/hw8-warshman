/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i].getName().equals(name)) {
                return this.users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (this.users.length == this.getUserCount()) {
            return false;
        }
        if (this.getUser(name) != null) {
            return false;
        }

        User newUser = new User(name);
        this.users[this.getUserCount()] = newUser;
        this.userCount++;

        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        //first check if the names exist
        if (name1 == null | name2 == null) {
            return false;
        }
        //then check if the users exists
        if (this.getUser(name1) == null | this.getUser(name2) == null) {
            return false;
        }
        
        //then make sure you're not adding someone already followed or self following 
        if (this.getUser(name1).follows(name2) | name1.equals(name2) ) {
            return false;
        }

        //if all is smooth sailing add the follower
        boolean result = this.getUser(name1).addFollowee(name2);
        return result;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        int mostMutuals = 0;
        String recommended = null;
        User user = this.getUser(name);

        //iterate through every user thats not "user"
        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i].getName().equals(name)) continue;

            int mutualCount = 0;
            User other = this.users[i];
            String [] otherFollows = other.getfFollows();
            for (int j = 0; j < other.getfCount(); j++) {
                if (user.follows(otherFollows[j])) {
                    mutualCount++;
                }
            }

            if (mutualCount > mostMutuals) {
                mostMutuals = mutualCount;
                recommended = other.getName();
            }

            
        }


        return recommended;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int mostFollowers = 0;
        String mostFollowed = "";

        for (int i = 0; i < this.userCount; i++) {
            User user = this.users[i];
            int follows = followeeCount(user.getName());
            if (follows > mostFollowers) {
                mostFollowed = user.getName();
                mostFollowers = follows;
            }

        }
        return mostFollowed == "" ? null : mostFollowed;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int follows = 0;
        for (int i = 0; i < this.userCount; i++) {
            User user = this.users[i];
                if (user.follows(name)) {
                follows++;
            }
                
            }
        return follows;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String res = "Network:";
        for (int i = 0; i < this.userCount; i++) {
            res += "\n";
            User user = this.users[i];
            res += this.users[i].getName() + " -> ";
            for (int j = 0; j < user.getfCount(); j++) {
                res += user.getfFollows()[j] + " ";
            }
        }

        return res;
    }
}
