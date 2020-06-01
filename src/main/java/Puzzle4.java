import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle4 {

    private static String PATTERN = "(?<name>[\\D-]+)(?<sector>\\d+)(?<checksum>[\\[\\w\\]]+)";

    private static String NORTH_POLE = "northpoleobjectstorage";

    public static void main(String[] args) {
        run(Puzzle4Test.inputLvl2);
    }

    public static void run(String input) {

        Map<String, Integer> letters = new HashMap<>();
        int res = 0;
        int northPoleId = 0;

        String[] lines = input.split("\n");

        for (String line : lines) {

            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String name = matcher.group("name");
                name = name.replace("-", "");

                String sector = matcher.group("sector");

                String checksum = matcher.group("checksum");
                checksum = checksum.replace("[", "").replace("]", "");

                for (int i = 0; i < name.length(); i++) {
                    String letter = name.substring(i, i + 1);

                    letters.put(letter, letters.getOrDefault(letter, 0) + 1);
                }

                String commonLetters = commonLetters(letters);

                if (commonLetters.equals(checksum)) {
                    res += Integer.parseInt(sector);

                    // No need to rotate to full id, just rotate to modulo 26
                    int rotation = Integer.parseInt(sector) % 26;

                    StringBuilder decrypted = new StringBuilder(name);

                    for (int i = 0; i < name.length() ; i++) {
                        char currentChar = name.charAt(i);
                        char rotatedChar;

                        // Char cannot be bigger than 122
                        if(currentChar + rotation > 122){
                            rotatedChar = (char) (currentChar + rotation - 26);
                        } else {
                            rotatedChar = (char) (currentChar + rotation);
                        }

                        decrypted.setCharAt(i, rotatedChar);
                    }
                    if(decrypted.toString().equals(NORTH_POLE)){
                        northPoleId = Integer.parseInt(sector);
                    }
                }
            }
            letters.clear();
        }

        System.out.println("The sum of the sector IDs: " + res);
        System.out.println("The sector ID of North Pole: " + northPoleId);
    }

    private static String commonLetters(Map<String, Integer> letters) {

        String[] sortedKeys = new String[letters.size()];

        List<Map.Entry<String, Integer>> list = new LinkedList<>(letters.entrySet());

        // sort list as ASC
        list.sort((o1, o2) -> {
            int v1 = o1.getValue();
            int v2 = o2.getValue();
            return v1 == v2 ? o1.getKey().equals(o2.getKey()) ? 0 : -1 : v1 - v2;
        });

        // Invert the list to have DESC
        int i = list.size() - 1;
        for (Map.Entry<String, Integer> entry : list) {
            sortedKeys[i] = entry.getKey();
            i--;
        }

        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(key);
        }

        return sb.toString().substring(0, 5);
    }
}
