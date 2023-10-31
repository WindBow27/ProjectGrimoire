package org.graphic.dictionary;

import org.graphic.app.AppConfig;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
    private final String error = appConfig.getDBError();
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

    public String translateWord(String word, String tl) throws IOException, ExecutionException, InterruptedException {
        if (tl.equals("vi")) return translatorAPI.translateEnToVi(word);
        return translatorAPI.translateViToEn(word);
    }

    private void connectToDB() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL);
    }

    public String findWordHTML(Word word) {
        String html = "html";
        String SQL_QUERY = "SELECT " + html + " FROM " + table + " WHERE " + target + " = " + "?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word.getWordTarget());

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    if (rs.next()) {
                        return rs.getString("html");
                    } else {
                        return error;
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
        return error;
    }

    public String findWord(Word word) {
        String SQL_QUERY = "SELECT " + description + " FROM " + table + " WHERE " + target + " = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word.getWordTarget());

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    if (rs.next()) {
                        return rs.getString(description);
                    } else {
                        return error;
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
        return error;
    }

    public boolean addWord(Word word) {
        String SQL_QUERY = "INSERT INTO " + table + " (" + target + ", " + description + ") VALUES (?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word.getWordTarget());
            ps.setString(2, word.getWordExplain());

            try {
                int addedRow = ps.executeUpdate();

                if (addedRow == 0) {
                    return false;
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
            } finally {
                close(ps);
            }

            trie.insert(word.getWordTarget());

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
    }


    public boolean deleteWord(Word word) {
        String SQL_QUERY = "DELETE FROM " + table + " WHERE " + target + " = " + "?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word.getWordTarget());

            try {
                int deletedRow = ps.executeUpdate();

                if (deletedRow == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }

            trie.delete(word.getWordTarget());

            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
    }


    public boolean updateWord(Word word) {
        String SQL_QUERY = "UPDATE " + table + " SET " + description + " = " + "?" + " WHERE " + target + " = " + "?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, word.getWordTarget());
            ps.setString(2, word.getWordExplain());

            try {
                int updatedRow = ps.executeUpdate();

                if (updatedRow == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }

            trie.insert(word.getWordTarget());
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
            return false;
        }
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

    public String findWordByID(int id) {
        String SQL_QUERY = "SELECT " + target + " FROM " + table + " WHERE " + id + " = " + "?";

        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, String.valueOf(id));

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    if (rs.next()) {
                        return rs.getString("word");
                    } else {
                        return error;
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

        return error;
    }

    public List<String> suggestWords(String query) {
        List<String> suggestions = new ArrayList<>();
        String SQL_QUERY = "SELECT " + target + " FROM " + table + " WHERE " + target + " LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY);
            ps.setString(1, query + "%");

            try {
                ResultSet rs = ps.executeQuery();

                try {
                    while (rs.next()) {
                        suggestions.add(rs.getString(target));
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
        return suggestions;
    }
}