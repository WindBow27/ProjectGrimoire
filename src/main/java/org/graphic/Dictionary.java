package org.graphic;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dictionary {
    private final Trie trie = new Trie();
    private final Logger logger = Logger.getLogger(Dictionary.class.getName());
    private final AppConfig appConfig = new AppConfig();
    private final TranslatorAPI translatorAPI = new TranslatorAPI();
    private final String DATABASE_URL = appConfig.getDBUrl();
    private final String target = "word";
    private final String description = "description";
    private final String table = "av";
    private Connection connection = null;

    public Dictionary() {
    }

    public void init() throws SQLException {
        connectToDB();
        ArrayList<String> targets = getAllWordTargets();
        for (String word : targets) {
            trie.insert(word);
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

    public String translateWord(String word, String tl) throws IOException {
        if (tl.equals("vi")) return translatorAPI.translateEnToVi(word);
        return translatorAPI.translateViToEn(word);
    }

    private void connectToDB() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL);
    }

    public String findWord(String word) {
        double startTime = System.currentTimeMillis();
        String html = "html";
        String SQL_QUERY = "SELECT " + html + " FROM " + table + " WHERE " + target + " = " + "?";

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
                        return "Not found";
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

        return "Not found";
    }

    public boolean addWord(String word, String explain) {
        String SQL_QUERY = "INSERT INTO " + table + " (" + target + ", " + description + ") VALUES (?, ?)";

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
        String SQL_QUERY = "DELETE FROM " + table + " WHERE " + target + " = " + "?";

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
        String SQL_QUERY = "UPDATE " + table + " SET " + description + " = " + "?" + " WHERE " + target + " = " + "?";

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
        String SQL_QUERY = "SELECT * FROM " + table;

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);

            return getWordsFromResultSet(ps);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }

        return new ArrayList<>();
    }


    public ArrayList<String> getAllWordTargets() {
        String SQL_QUERY = "SELECT * FROM " + table;

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
        String SQL_QUERY = "SELECT " + target + " FROM " + table + " WHERE " + id + " = " + "?";

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
                        return "Not found";
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

        return "Not found";
    }
}