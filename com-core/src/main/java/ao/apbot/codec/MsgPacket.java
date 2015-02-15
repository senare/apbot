package ao.apbot.codec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ao.apbot.pkg.ChannelMessagePacket;
import ao.apbot.pkg.PrivateChannelMessagePacket;
import ao.apbot.pkg.PrivateMessagePacket;

public abstract class MsgPacket extends Fact {

    public MsgPacket(short type) {
        super(type);
    }

    public abstract int getCharacterId();

    public abstract String getMsg();

    public MsgPacket getReply(String msg) {
        if (this instanceof ChannelMessagePacket) {
            return new ChannelMessagePacket(((ChannelMessagePacket) this).getGroupId(), msg);
        } else if (this instanceof PrivateChannelMessagePacket) {
            return new PrivateChannelMessagePacket(((PrivateChannelMessagePacket) this).getGroupId(), msg);
        } else {
            return new PrivateMessagePacket(((PrivateMessagePacket) this).getCharacterId(), msg);
        }
    }

    private String command = null;

    private int noParams = 0;

    private List<String> params = new ArrayList<>();

    private void split() {
        params.addAll(Arrays.asList(getMsg().split("\\s+")));
        this.command = params.get(0);

        if (this.command.startsWith("!")) {
            this.command = this.command.substring(1);
        }

        this.noParams = params.size();
        if (this.noParams > 1) {
            this.noParams--;
        }
    }

    public String getCommand() {
        if (params.size() < 1)
            this.split();

        return command;
    }

    public String getParam(int index) {
        if (params.size() < 1)
            this.split();

        if (params.isEmpty() || params.size() <= index) {
            return null;
        }
        return params.get(index);
    }

    public int getNoParams() {
        return this.noParams;
    }

    // private Pattern regExp =
    // Pattern.compile("(?:^!(?<command>\\S+)|)\\s+(?<params>\\S+)");
    //
    // private List<String> params = new ArrayList<>();
    //
    // private String command = "";
    //
    // public String getCommand() {
    // log.info("Command was called");
    // Matcher m = regExp.matcher(getMsg());
    // while (m.find()) {
    // if (m.group("command") != null) {
    // command = m.group("command");
    // }
    // if (m.group("params") != null)
    // params.add(m.group("params"));
    // }
    // log.info("Command was " + command + " and had " + params.size() +
    // " params");
    // return command;
    // }
    //
    // public String getParam(int index) {
    // return params.get(index - 1);
    // }
}