package org.graphic;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dictionary {
    private final List<Word> words;
    private final Trie trie = new Trie();
    private final Logger logger = Logger.getLogger(Dictionary.class.getName());
    AppConfig appConfig = new AppConfig();
    private final String HOST_NAME = appConfig.getDBHost();
    private final String DB_NAME = appConfig.getDBName();
    private final String USERNAME = appConfig.getDBUser();
    private final String PASSWORD = appConfig.getDBPass();
    private final String PORT = appConfig.getDBPort();
    //private final String MYSQL_URL = String.format("jdbc:mysql://%s:%s/%s", HOST_NAME, PORT, DB_NAME) + "?useSSL=false&allowPublicKeyRetrieval=true";
    private final String MYDB_URL = "jdbc:sqlite:dict_hh.db";
    private Connection connection = null;

    public Dictionary() {
        words = new ArrayList<>();
    }

    public void init() throws SQLException, IOException {
        connectToDB();
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
        //connection = DriverManager.getConnection(MYSQL_URL, USERNAME, PASSWORD);
        connection = DriverManager.getConnection(MYDB_URL);
    }

    public String findWord(String word) {
        double startTime = System.currentTimeMillis();
        String SQL_QUERY = "SELECT html FROM av WHERE word = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word);

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    if (rs.next()) {
                        double endTime = System.currentTimeMillis();
                        System.out.println(endTime - startTime);
                        return rs.getString("html");
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

    public boolean addWord(String word, String explain) {
        String SQL_QUERY = "INSERT INTO av (word, description) VALUES (?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word);
            ps.setString(2, explain);

            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
            } finally {
                close(ps);
            }

            trie.insert(word);

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
    }


    public boolean deleteWord(String word) {
        String SQL_QUERY = "DELETE FROM av WHERE word = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word);

            try {
                int deletedRow = ps.executeUpdate();

                if (deletedRow == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }

            trie.delete(word);

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
    }


    public boolean updateWord(String word, String explain) {
        String SQL_QUERY = "UPDATE av SET description = ? WHERE word = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word);
            ps.setString(2, explain);

            try {
                int updatedRow = ps.executeUpdate();

                if (updatedRow == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }

            trie.insert(word);

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
        String SQL_QUERY = "SELECT * FROM av";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);

            return getWordsFromResultSet(ps);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }

        return new ArrayList<>();
    }


    public ArrayList<String> getAllWordTargets() {
        String SQL_QUERY = "SELECT * FROM av";

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

    public String findWordByID(int id, int limitLength) {
        double startTime = System.currentTimeMillis();
        String SQL_QUERY = "SELECT word FROM av WHERE id = ? AND LENGTH(word) <= " + limitLength;

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, String.valueOf(id));

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    if (rs.next()) {
                        double endTime = System.currentTimeMillis();
                        System.out.println(endTime - startTime);
                        return rs.getString("word");
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
}