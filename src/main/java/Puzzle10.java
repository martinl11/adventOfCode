import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle10 {

    private static String VALUE_PATTERN = "value (\\d+) goes to bot (\\d+)";
    private static String BOT_PATTERN = "bot (?<bot>\\d+) gives (low|high) to (bot (?<lowBot>\\d+)|output (?<lowOutput>\\d+)) and (low|high) to (bot (?<highBot>\\d+)|output (?<highOutput>\\d+))";

    private static int finalReceiverBotId = -1;

    public static int[] run(String input) {

        Map<Integer, Bot> bots = new HashMap<>();
        Map<Integer, Integer> outputs = new HashMap<>();

        String[] lines = input.split("\n");

        for(String line : lines) {
            Pattern pattern;
            Matcher matcher;
            int chip;
            int bot;

            if(line.contains("value")){
                pattern = Pattern.compile(VALUE_PATTERN);
                matcher = pattern.matcher(line);

                if(matcher.find()){
                    chip = Integer.parseInt(matcher.group(1));
                    bot = Integer.parseInt(matcher.group(2));
                    saveChipToBot(bots, chip, bot);
                }
            }

            if(line.contains("gives low")){
                pattern = Pattern.compile(BOT_PATTERN);
                matcher = pattern.matcher(line);

                if(matcher.find()){
                    bot = Integer.parseInt(matcher.group("bot"));
                    saveInstructionToBot(bots, line, bot);
                }
            }
        }

        Bot bot = getInitialBot(bots);

        if(bot != null){
            if(bot.getInstruction() != null){
                processInstruction(bots, outputs, bot);
            }
        }

        int multiplyProduct = -1;

        if(outputs.get(0) != null && outputs.get(1) != null && outputs.get(2) != null) {
            multiplyProduct = outputs.get(0)*outputs.get(1)*outputs.get(2);
        }

        System.out.println("The Number of Bot with (61, 17): " + finalReceiverBotId);
        System.out.println("Multiply Together the Values is: " + multiplyProduct);

        int[] res = new int[]{finalReceiverBotId, multiplyProduct};

        // reset init value
        finalReceiverBotId = -1;

        return res;
    }

    private static void processInstruction(Map<Integer, Bot> bots, Map<Integer, Integer> outputs, Bot bot) {

        Pattern pattern = Pattern.compile(BOT_PATTERN);
        Matcher m = pattern.matcher(bot.getInstruction());

        if(m.find()) {

            if (m.group("lowBot") != null && !m.group("lowBot").isEmpty()) {
                int lowBot = Integer.parseInt(m.group("lowBot"));
                giveChipToBot(lowBot, bot.getLow(), bots, outputs);
                bot.setLow(0);
            }

            if (m.group("lowOutput") != null && !m.group("lowOutput").isEmpty()) {
                int lowOutput = Integer.parseInt(m.group("lowOutput"));
                outputs.put(lowOutput, bot.getLow());
                bot.setLow(0);
            }

            if (m.group("highBot") != null && !m.group("highBot").isEmpty()) {
                int highBot = Integer.parseInt(m.group("highBot"));
                giveChipToBot(highBot, bot.getHigh(), bots, outputs);
                bot.setHigh(0);
            }

            if (m.group("highOutput") != null && !m.group("highOutput").isEmpty()) {
                int highOutput = Integer.parseInt(m.group("highOutput"));
                outputs.put(highOutput, bot.getHigh());
                bot.setHigh(0);
            }
        }

    }

    private static Bot getInitialBot(Map<Integer, Bot> bots) {

        int initialBot = 0;
        for (Map.Entry<Integer, Bot> bot:bots.entrySet()) {
            if(bot.getValue().getLow() > 0 && bot.getValue().getHigh() > 0){
                initialBot = bot.getKey();
                break;
            }
        }

        return bots.get(initialBot);
    }

    private static void saveInstructionToBot(Map<Integer, Bot> bots, String line, int botValue) {

        Bot bot = bots.get(botValue);
        if(bot != null){
            bot.setInstruction(line);
        } else {
            bot = new Bot();
            bot.setInstruction(line);
            bots.put(botValue, bot);
        }
    }

    private static void saveChipToBot(Map<Integer, Bot> bots, int chipValue, int botValue) {

        Bot bot = bots.get(botValue);
        if(bot != null){
            bot.receiveChip(chipValue);
        } else {
            bot = new Bot();
            bot.receiveChip(chipValue);
            bots.put(botValue, bot);
        }
    }

    private static void giveChipToBot(int receiverBotId, int value, Map<Integer, Bot> botMap, Map<Integer, Integer> outputs){

        Bot bot = botMap.get(receiverBotId);
        if(bot != null){
            bot.receiveChip(value);

            if(bot.getLow() == 17 && bot.getHigh() == 61){
                finalReceiverBotId = receiverBotId;
            }

            if(bot.getInstruction() != null && bot.getLow() > 0 && bot.getHigh() > 0){
                processInstruction(botMap, outputs, bot);
            }
        }
    }

}

class Bot {

    private int low;
    private int high;
    private String instruction;

    public void receiveChip(int value){

        if(this.low == 0 && this.high == 0){
            this.low = value;
        } else if(this.high == 0 && value > this.low){
            this.high = value;
        } else if(this.high == 0 && value < this.low){
            this.high = this.low;
            this.low = value;
        } else if(this.low == 0 && value > this.high){
            this.low = this.high;
            this.high = value;
        } else if(this.low == 0 && value < this.high){
            this.low = value;
        }

    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
