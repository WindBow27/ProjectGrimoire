package org.graphic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dictionary {
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "dictionary";
    private static final String USERNAME = "root";
    private static final String PASSWORD = null;
    private static final String PORT = "3306";
    private static final String MYSQL_URL = String.format("jdbc:mysql://%s:%s/%s", HOST_NAME, PORT, DB_NAME);
    private final List<Word> words;
    private final Trie trie = new Trie();
    private final Logger logger = Logger.getLogger(Dictionary.class.getName());

    private Connection connection = null;

    public Dictionary() {
        words = new ArrayList<>();
    }

    public void init() throws SQLException, IOException {
        // Read the SQL script from the file.
//        StringBuilder sqlScript = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/graphic/dictionary.sql"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sqlScript.append(line).append("\n");
//            }
//        }
        connectToDB();
//        // Create a statement object.
//        Statement statement = connection.createStatement();
//
//        // Execute the SQL script using the statement object.
//        statement.execute(sqlScript.toString());

        ArrayList<String> targets = getAllWordTargets();

        for (String word : targets) {
            trie.insert(word);
        }
    }

    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }
    }

    private void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }
    }

    private void close(ResultSet ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }
    }

    //Add word
    public void addWord(Word word) {
        words.add(word);
    }

    //Get all words
    public List<Word> getWords() {
        return words;
    }

    //Get word by index
    public Word getWord(int index) {
        return words.get(index);
    }

    //Get word by target
    public Word getWord(String target) {
        for (Word word : words) {
            if (word.getWordTarget().equals(target)) {
                return word;
            }
        }
        return null;
    }

    private void connectToDB() throws SQLException {
        connection = DriverManager.getConnection(MYSQL_URL, USERNAME, PASSWORD);
    }

    public String findWord(String target) {
        double startTime = System.currentTimeMillis();
        String SQL_QUERY = "SELECT definition FROM dictionary WHERE target = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    if (rs.next()) {
                        double endTime = System.currentTimeMillis();
                        System.out.println(endTime - startTime);
                        return rs.getString("definition");
                    } else {
                        return "404";
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }

        return "404";
    }

    public boolean addWord(String target, String explain) {
        String SQL_QUERY = "INSERT INTO dictionary (target, definition) VALUES (?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            ps.setString(2, explain);

            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
            } finally {
                close(ps);
            }

            trie.insert(target);

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
    }


    public boolean deleteWord(String target) {
        String SQL_QUERY = "DELETE FROM dictionary WHERE target = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);

            try {
                int deletedRow = ps.executeUpdate();

                if (deletedRow == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }

            trie.delete(target);

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
    }


    public boolean updateWord(String target, String explain) {
        String SQL_QUERY = "UPDATE dictionary SET definition = ? WHERE target = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, target);
            ps.setString(2, explain);

            try {
                int updatedRow = ps.executeUpdate();

                if (updatedRow == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }

            trie.insert(target);

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
    }

    private ArrayList<Word> getWordsFromResultSet(PreparedStatement ps) throws SQLException {
        try {
            ResultSet rs = ps.executeQuery();

            try {
                ArrayList<Word> res = new ArrayList<>();

                while (rs.next()) {
                    res.add(new Word(rs.getString(2), rs.getString(3)));
                }

                return res;
            } finally {
                close(rs);
            }
        } finally {
            close(ps);
        }
    }


    public ArrayList<Word> getAllWords() {
        String SQL_QUERY = "SELECT * FROM dictionary";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);

            return getWordsFromResultSet(ps);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }

        return new ArrayList<>();
    }


    public ArrayList<String> getAllWordTargets() {
        String SQL_QUERY = "SELECT * FROM dictionary";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    ArrayList<String> res = new ArrayList<>();

                    while (rs.next()) {
                        res.add(rs.getString(2));
                    }

                    return res;
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }

        return new ArrayList<>();
    }
}