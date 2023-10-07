package org.graphic;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryManagement {
    private static final String API_KEY = "5ebc593204msh6296a4e6abd5b08p1f5294jsn6956ee6fb566";
    private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private final Dictionary dictionary;
    private final TranslatorAPI translatorAPI;

    public DictionaryManagement() {
        dictionary = new Dictionary();
        translatorAPI = new TranslatorAPI();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public String getDefinition(String word) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(/*API_URL*/ "https://translate.google.com/?sl=en&tl=vi&text=" + word + "&op=translate" /*+ word*/))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Check the response status code
        if (response.statusCode() == 200) {
            // The request was successful
            String data = response.body();

            // Extract the translated text from the response
            Pattern pattern = Pattern.compile("<div class=\"g\">(.+?)</div>");
            Matcher matcher = pattern.matcher(data);

            if (matcher.find()) {
                String translatedText = matcher.group(1);
                System.out.println(translatedText);
            } else {
                System.out.println("Failed to extract translated text from response");
            }
        } else {
            // The request failed
            System.out.printf((response.statusCode()) + "%n");
        }

        return response.body();
    }

    private String buildHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", API_KEY);
        headers.put("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com");
        return headers.toString();
    }

    public String translateWord(String word, String tl) throws IOException, InterruptedException {
        if (tl.equals("vi")) return translatorAPI.translateEnToVi(word);
        else return translatorAPI.translateViToEn(word);
    }

    public void insertFromText(String text) {
        // Split the text using regular expressions to handle different newline characters
        String[] lines = text.split("\\r?\\n");

        for (String line : lines) {
            // Split each line by tab character and trim any leading/trailing whitespace
            String[] wordParts = line.split(" ");
            if (wordParts.length == 2) {
                String wordTarget = wordParts[0].trim();
                String wordExplain = wordParts[1].trim();
                dictionary.addWord(new Word(wordTarget, wordExplain));
            }
        }
    }

    public void showAllWords() {
        System.out.println("No\t|English\t\t|Vietnamese");
        for (int i = 0; i < this.getDictionary().getWords().size(); i++) {
            System.out.println(i + 1
                    + "\t|" + this.getDictionary().getWord(i).getWordTarget()
                    + "\t\t\t|" + this.getDictionary().getWord(i).getWord_explain());
        }
    }

//    public void insertFromCommandline() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter number of words: ");
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        for (int i = 0; i < n; i++) {
//            System.out.println("Enter target word: ");
//            String target = scanner.nextLine();
//            System.out.println("Enter explain word: ");
//            String explain = scanner.nextLine();
//            Word word = new Word(target, explain);
//            dictionary.addWord(word);
//        }
//    }
//
//    //Insert word from file
//    public void insertFromFile() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter file name: ");
//        String fileName = scanner.nextLine();
//        File file = new File(fileName);
//        try {
//            Scanner fileScanner = new Scanner(file);
//            while (fileScanner.hasNextLine()) {
//                String line = fileScanner.nextLine();
//                String[] words = line.split("\t");
//                Word word = new Word(words[0], words[1]);
//                dictionary.addWord(word);
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found!");
//        }
//    }
//
//    //Lookup word
//    public void dictionaryLookup() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter word: ");
//        String target = scanner.nextLine();
//        Word word = dictionary.getWord(target);
//        if (word != null) {
//            System.out.println("Explanation: " + word.getWord_explain());
//        } else {
//            System.out.println("Word not found!");
//        }
//    }
//
//    //Delete word
//    public void deleteWord() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter word: ");
//        String target = scanner.nextLine();
//        Word word = dictionary.getWord(target);
//        if (word != null) {
//            dictionary.getWords().remove(word);
//            System.out.println("Word deleted!");
//        } else {
//            System.out.println("Word not found!");
//        }
//    }
//
//    //Update word
//    public void updateWord() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter word: ");
//        String target = scanner.nextLine();
//        Word word = dictionary.getWord(target);
//        if (word != null) {
//            System.out.println("Enter new explanation: ");
//            String explain = scanner.nextLine();
//            word.setWordExplain(explain);
//            System.out.println("Word updated!");
//        } else {
//            System.out.println("Word not found!");
//        }
//    }
//
//    //dictionary search
//    public void dictionarySearcher() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter word: ");
//        String target = scanner.nextLine();
//        for (Word word : dictionary.getWords()) {
//            //startWith() returns string start
//            if (word.getWordTarget().startsWith(target)) {
//                System.out.println(word.getWordTarget());
//            }
//        }
//    }
//
//    //Export to file
//    public void dictionaryExportToFile() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter file name: ");
//        String fileName = scanner.nextLine();
//        File file = new File(fileName);
//        try {
//            //PrintWriter used to write data to file
//            PrintWriter output = new PrintWriter(file);
//            for (Word word : dictionary.getWords()) {
//                output.println(word.getWordTarget() + "\t" + word.getWord_explain());
//            }
//            output.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found!");
//        }
//    }
}
