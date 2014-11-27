package ao.apbot.codec;

public abstract class MsgPacket extends Fact {

	public MsgPacket(short type) {
		super(type);
	}

	public abstract String getMsg();

	public abstract MsgPacket getReply(String msg);
}