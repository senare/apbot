package ao.apbot.codec;

import java.util.ArrayList;
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

	private Pattern regExp = Pattern.compile("(?:^!(?<command>\\S+)|)\\s+(?<params>\\S+)");

	public MsgPacket(short type) {
		super(type);
	}

	public abstract String getMsg();

	private List<String> params = new ArrayList<>();

	private String command = "";

	public String getCommand() {
		log.info("Command was called");
		Matcher m = regExp.matcher(getMsg());
		while (m.find()) {
			if (m.group("command") != null) {
				command = m.group("command");
			}
			if (m.group("params") != null)
				params.add(m.group("params"));
		}
		log.info("Command was " + command + " and had " + params.size() + " params");
		return command;
	}

	public String getParam(int index) {
		return params.get(index - 1);
	}

	public MsgPacket getReply(String msg) {
		if (this instanceof ChannelMessagePacket) {
			return new ChannelMessagePacket(((ChannelMessagePacket) this).getGroupId(), msg);
		} else if (this instanceof PrivateChannelMessagePacket) {
			return new PrivateChannelMessagePacket(((PrivateChannelMessagePacket) this).getGroupId(), msg);
		} else {
			return new PrivateMessagePacket(((PrivateMessagePacket) this).getCharacterId(), msg);
		}
	}
}