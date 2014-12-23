package ao.apbot.codec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ao.apbot.pkg.ChannelMessagePacket;
import ao.apbot.pkg.PrivateChannelMessagePacket;
import ao.apbot.pkg.PrivateMessagePacket;

public abstract class MsgPacket extends Fact {

	private static Logger log = LoggerFactory.getLogger(MsgPacket.class);

	public MsgPacket(short type) {
		super(type);
	}

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

	private List<String> params = new ArrayList<>();

	private void split() {
		params.addAll(Arrays.asList(getMsg().split("\\s+")));
	}

	public String getCommand() {
		if (params.size() < 1)
			this.split();

		return params.get(0).substring(1);
	}

	public String getParam(int index) {
		if (params.size() < 1)
			this.split();

		return params.get(index);
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